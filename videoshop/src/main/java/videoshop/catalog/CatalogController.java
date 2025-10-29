/*
 * Copyright 2013-2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package videoshop.catalog;

import videoshop.catalog.Disc.DiscType;

import java.time.LocalDateTime;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

import org.springframework.data.jpa.domain.Specification;    //new

import org.salespointframework.inventory.InventoryItem;
import org.salespointframework.inventory.UniqueInventory;
import org.salespointframework.inventory.UniqueInventoryItem;
import org.salespointframework.quantity.Quantity;
import org.salespointframework.time.BusinessTime;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestParam;  // new
import org.springframework.web.bind.annotation.RequestHeader; // new


@Controller
class CatalogController {

	private static final Quantity NONE = Quantity.of(0);

	private final VideoCatalog catalog;
	private final UniqueInventory<UniqueInventoryItem> inventory;
	private final BusinessTime businessTime;

	CatalogController(VideoCatalog videoCatalog, UniqueInventory<UniqueInventoryItem> inventory,
			BusinessTime businessTime) {

		this.catalog = videoCatalog;
		this.inventory = inventory;
		this.businessTime = businessTime;
	}

	@GetMapping("/dvds")
	String dvdCatalog(Model model) {

		model.addAttribute("catalog", catalog.findByType(DiscType.DVD));
		model.addAttribute("title", "catalog.dvd.title");

		return "catalog";
	}

	@GetMapping("/blurays")
	String blurayCatalog(Model model) {

		model.addAttribute("catalog", catalog.findByType(DiscType.BLURAY));
		model.addAttribute("title", "catalog.bluray.title");

		return "catalog";
	}

	// (｡◕‿◕｡)
	// Befindet sich die angesurfte Url in der Form /foo/5 statt /foo?bar=5 so muss man @PathVariable benutzen
	// Lektüre: http://spring.io/blog/2009/03/08/rest-in-spring-3-mvc/
	@GetMapping("/disc/{disc}")
	String detail(@PathVariable Disc disc, Model model, CommentAndRating form) {

		var quantity = inventory.findByProductIdentifier(disc.getId()) //
				.map(InventoryItem::getQuantity) //
				.orElse(NONE);

		model.addAttribute("disc", disc);
		model.addAttribute("quantity", quantity);
		model.addAttribute("orderable", quantity.isGreaterThan(NONE));

		return "detail";
	}

    @GetMapping("/search")
    String search(@RequestParam("q") String q,
                  Model model,
                  @RequestHeader(value = "HX-Request", required = false) String htmx) {

        Specification<Disc> byName = (root, query, cb) ->
                cb.like(cb.lower(root.get("name")), "%" + q.toLowerCase() + "%");
        Specification<Disc> byGenre = (root, query, cb) ->
                cb.like(cb.lower(root.get("genre")), "%" + q.toLowerCase() + "%");

        var results = catalog.findAll(Specification.where(byName).or(byGenre));

        model.addAttribute("catalog", results);
        model.addAttribute("title", "Search");
        model.addAttribute("query", q);

        return (htmx != null) ? "catalog :: #catalogList" : "catalog";
    }

	@GetMapping("/filter")
	String filter(@RequestParam(required = false) String type,
				@RequestParam(required = false) Double minPrice,
				@RequestParam(required = false) Double maxPrice,
				@RequestParam(required = false) String genre,
				Model model,
				@RequestHeader(value = "HX-Request", required = false) String htmx) {

		Specification<Disc> spec = Specification.where((root, query, cb) -> cb.conjunction());

		if (type != null && !type.isBlank()) {
			var t = Disc.DiscType.valueOf(type.toUpperCase());
			spec = spec.and((root, query, cb) -> cb.equal(root.get("type"), t));
			model.addAttribute("selectedType", type);
		}

		if (minPrice != null || maxPrice != null) {
			Double low  = (minPrice == null) ? 0.0 : minPrice;
			Double high = (maxPrice == null) ? Double.MAX_VALUE : maxPrice;
			spec = spec.and((root, query, cb) -> cb.between(root.get("price"), low, high));
			model.addAttribute("minPrice", minPrice);
			model.addAttribute("maxPrice", maxPrice);
		}

		if (genre != null && !genre.isBlank()) {
			var like = "%" + genre.toLowerCase() + "%";
			spec = spec.and((root, query, cb) -> cb.like(cb.lower(root.get("genre")), like));
			model.addAttribute("genre", genre);
		}

		var results = catalog.findAll(spec);
		model.addAttribute("catalog", results);
		model.addAttribute("title", "Filter");

		return (htmx != null) ? "catalog :: #catalogList" : "catalog";
	}

	

	@PostMapping("/disc/{disc}/comments")
	public String comment(@PathVariable Disc disc, @Valid CommentAndRating form, Errors errors) {
		if (errors.hasErrors()) {
			return "detail";
		}

		disc.addComment(form.toComment(businessTime.getTime()));
		catalog.save(disc);

		return "redirect:/disc/" + disc.getId();
	}

	/**
	 * Describes the payload to be expected to add a comment.
	 *
	 * @author Oliver Gierke
	 */
	interface CommentAndRating {

		@NotEmpty
		String getComment();

		@Range(min = 1, max = 5)
		Integer getRating();

		default Comment toComment(LocalDateTime time) {
			return new Comment(getComment(), getRating(), time);
		}
	}
}
