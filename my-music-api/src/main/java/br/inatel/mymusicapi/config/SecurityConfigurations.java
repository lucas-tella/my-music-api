package br.inatel.mymusicapi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import br.inatel.mymusicapi.repository.UserRepository;
import br.inatel.mymusicapi.service.AuthenticationService;
import br.inatel.mymusicapi.service.TokenService;

@EnableWebSecurity
@Configuration
public class SecurityConfigurations extends WebSecurityConfigurerAdapter {
	@Autowired
	private AuthenticationService authenticationService;
	@Autowired
	private TokenService tokenService;
	@Autowired
	private UserRepository userRepository;
	@Override
	@Bean
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(authenticationService).passwordEncoder(new BCryptPasswordEncoder());
	}
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.antMatchers(HttpMethod.POST, "/users").permitAll()
		.antMatchers(HttpMethod.GET, "/users/*").permitAll()
		.antMatchers(HttpMethod.POST, "/login").permitAll()
//        .antMatchers("/v2/api-docs","/swagger-resources/**","/swagger-ui.html","/webjars/**" ,"/swagger.json")
//            .permitAll()
		.anyRequest().authenticated()
		.and().csrf().disable().exceptionHandling()
		.authenticationEntryPoint(new Http401AuthenticationEntryPoint()).and().sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
		.addFilterBefore(new TokenAuthenticationFilter(tokenService, userRepository),
						UsernamePasswordAuthenticationFilter.class);
	}
	@Override
	public void configure(WebSecurity web) throws Exception {
	}
}