/*
 * Copyright 2014-2023 the original author or authors.
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
package kickstart;

import kickstart.account.*;

import org.salespointframework.EnableSalespoint;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer.FrameOptionsConfig;

import org.springframework.security.access.hierarchicalroles.*;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;

import org.springframework.security.web.SecurityFilterChain;


/**
 * The main application class.
 */
@SpringBootApplication
@EnableSalespoint
public class Application {

	/**
	 * The main application method
	 *
	 * @param args application arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);




	}

	@Configuration
	static class WebSecurityConfiguration {

		@Bean
		SecurityFilterChain videoShopSecurity(HttpSecurity http) throws Exception {

			return http
				.headers(headers -> headers.frameOptions(FrameOptionsConfig::sameOrigin))
				.csrf(csrf -> csrf.disable())
				.authorizeHttpRequests(auth -> auth
					.requestMatchers("/festival/**").authenticated()
					.requestMatchers("/createfestival/**").authenticated()
					.anyRequest().permitAll()
				)
				// .formLogin(login -> login.loginProcessingUrl("/login"))
				.formLogin(login -> login
					.loginPage("/login")
					.loginProcessingUrl("/login")
					.defaultSuccessUrl("/Logged", true)
					.failureUrl("/login?error")
					.permitAll()
				)
				// .logout(logout -> logout.logoutUrl("/logout").logoutSuccessUrl("/"))
				.logout(logout -> logout
					.logoutUrl("/logout")
					.logoutSuccessUrl("/login?logout")
					.permitAll()
				)
				.build();
		}


		@Bean
		RoleHierarchy roleHierarchy() {
			RoleHierarchyImpl hierarchy = new RoleHierarchyImpl();
			hierarchy.setHierarchy("""
            Manager > Veranstalter
            Veranstalter > Festivalleiter
        """);
			return hierarchy;
		}

		@Bean
		DefaultWebSecurityExpressionHandler webExpressionHandler(RoleHierarchy roleHierarchy) {
			DefaultWebSecurityExpressionHandler handler = new DefaultWebSecurityExpressionHandler();
			handler.setRoleHierarchy(roleHierarchy);
			return handler;
		}
	}
}