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
		SecurityFilterChain festivalManagerSecurity(HttpSecurity http) throws Exception {

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
	}
}