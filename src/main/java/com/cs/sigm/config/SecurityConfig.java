package com.cs.sigm.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(final HttpSecurity httpSecurity) throws Exception {
		//@formatter:off
		httpSecurity
			.csrf().disable()
			.cors()
			.and()
			.authorizeRequests()
				.anyRequest().permitAll()
			;
		//@formatter:on
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource(@Autowired CmsConfig config) {

		final List<String> allowedOriginsDev = Arrays.asList("*");

		// @formatter: off
		final List<String> allowedOriginsProd = Arrays.asList(
				"http://localhost:4200",
				"https://q-ca134.webspace.bosch.com",
				"https://www1.bosch.com.br"
		);
		// @formatter:on

		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(config.getProduction() ? allowedOriginsDev : allowedOriginsProd);

		configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD"));
		configuration.setAllowedHeaders(Arrays.asList("*"));
		configuration.setAllowCredentials(true);
		configuration.setMaxAge(3600L);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

}
