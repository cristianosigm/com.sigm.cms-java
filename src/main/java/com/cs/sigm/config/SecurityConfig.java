package com.cs.sigm.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	@Qualifier("userDetailsService")
	UserDetailsService userDetailsService;

	@Autowired
	public void configureGlobal(final AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
	}

	@Bean(name = BeanIds.AUTHENTICATION_MANAGER)
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(final HttpSecurity httpSecurity) throws Exception {
		//@formatter:off
		httpSecurity
			.csrf().disable()
			.cors()
			.and()
			.authorizeRequests()
				.antMatchers("/login").permitAll()
				.antMatchers("/user/signup").permitAll()
				.antMatchers("/user/reset/*").permitAll()
				.antMatchers("/content/public").permitAll()
				.anyRequest().authenticated()
			.and()
				.httpBasic()
			.and()
				.logout()
			;
		//@formatter:on
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource(@Autowired CmsConfig config) {
		final List<String> allowedOriginsDev = Arrays.asList("*");

		//@formatter:off
		final List<String> allowedOriginsProd = Arrays.asList(
			"http://localhost:4200",
			"https://q-ca134.webspace.bosch.com", 
			"https://www1.bosch.com.br"
		);
		//@formatter:on

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
