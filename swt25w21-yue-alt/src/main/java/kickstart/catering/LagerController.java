package kickstart.catering;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LagerController {  

    private final StorageManagement storageManagement;


    public LagerController(StorageManagement storageManagement) {
        this.storageManagement = storageManagement;
    }

    @GetMapping("/lager")
    public String lager(Model model) {
        var allLebensmittel = storageManagement.findAll();
        Map<String, ?> stockMap = storageManagement.getStockMap();

        model.addAttribute("lager", allLebensmittel);
        model.addAttribute("stockMap", stockMap);
        return "lager";
    }

    @PostMapping("/lager/lebensmittelhinzufügen")
    public String Lebensmittelhinzufügen(@RequestParam("Lebensmittelart") String lebensmittelart, 
        @RequestParam("Lebensmittelanzahl") int lebensmittelanzahl, 
        @RequestParam("Lebensmittelpreis") int lebensmittelpreis) {           
        storageManagement.Lebensmittelhinzufügen(lebensmittelart, lebensmittelanzahl, lebensmittelpreis);
        return "redirect:/lager";
    }
}