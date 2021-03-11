package com.cs.sigm.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.cs.sigm.domain.fixed.Role;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	@Qualifier("userDetailsService")
	private UserDetailsService userDetailsService;

	@Autowired
	private AuthUserDetailsPasswordService userDetailsPasswordService;

	@Bean(name = BeanIds.AUTHENTICATION_MANAGER)
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationProvider authProvider() {
		final DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setUserDetailsPasswordService(userDetailsPasswordService);
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authProvider());
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource(@Autowired CmsConfig config) {
		final List<String> allowedOriginsDev = Arrays.asList("*");

		// TODO: list here the allowed sources before put in prod
		final List<String> allowedOriginsProd = Arrays.asList("http://localhost:4200");

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

	@Override
	protected void configure(final HttpSecurity httpSecurity) throws Exception {
		//@formatter:off
		httpSecurity
			.csrf().disable()
			.cors()
			.and()
			.authorizeRequests()
				// public endpoints
				.antMatchers("/").permitAll()
				.antMatchers("/accounts/login").permitAll()
				.antMatchers("/accounts/signup").permitAll()
				.antMatchers("/accounts/reset/*").permitAll()
				.antMatchers(HttpMethod.GET, "/contents").permitAll()
				.antMatchers(HttpMethod.GET, "/contents/*").permitAll()
				// admin only endpoints
				.antMatchers(HttpMethod.POST, "/users").hasAuthority(Role.ADMINISTRATOR.getKey())
				.antMatchers(HttpMethod.POST, "/users/delete/*").hasAuthority(Role.ADMINISTRATOR.getKey())
				// all others (authenticated with any role)
				.anyRequest().authenticated()
			.and()
				.httpBasic()
			.and()
				.logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.invalidateHttpSession(true)
				.deleteCookies("JSESSIONID")
			;
		//@formatter:on
	}

}
