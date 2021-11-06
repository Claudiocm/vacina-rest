package br.com.imuniza.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import br.com.imuniza.modelo.Perfil;
import br.com.imuniza.service.UsuarioService;
import br.com.imuniza.util.JWTUtil;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private static final String ADMIN = Perfil.ADMIN.getDescricao();
	private static final String USUARIO = Perfil.USUARIO.getDescricao();

	@Autowired
	private UsuarioService service;
	@Autowired
	private JWTUtil jwtUtil;

	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/v2/api-docs","/configuration/ui","/swagger-resources/**", "/configuration/**", "/swagger-ui.html", "/webjars/**");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable();
		http.authorizeRequests()
				// acessos públicos liberados
				.antMatchers(HttpMethod.GET,"/","/usuarios/**","/expired").permitAll()
				.antMatchers(HttpMethod.POST,"/usuarios/**").permitAll()
				.antMatchers(HttpMethod.GET,"/chikungunyas/**").permitAll()
				.antMatchers(HttpMethod.POST,"/chikungunyas/**").permitAll()
				
				.anyRequest().authenticated();
				// .deleteCookies("JSESSIONID")
				//.and().exceptionHandling().accessDeniedPage("/acesso-negado").and().rememberMe();
		
		// autenticação e autorização
		//http.addFilter(new JWTAuthenticationFilter(authenticationManager(), jwtUtil));
		//http.addFilter(new JWTAuthorizationFilter(authenticationManager(), jwtUtil, service));

		// controle de sessão de usuário
		http.sessionManagement().maximumSessions(1).expiredUrl("/expired").maxSessionsPreventsLogin(false)
				.sessionRegistry(sessionRegistry());

		http.sessionManagement().sessionFixation().newSession().sessionAuthenticationStrategy(sessionAuthStrategy());

	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration().applyPermitDefaultValues();
		configuration.setAllowedMethods(Arrays.asList("POST", "GET", "PUT", "DELETE", "OPTIONS"));
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

	public SessionAuthenticationStrategy sessionAuthStrategy() {
		return new RegisterSessionAuthenticationStrategy(sessionRegistry());
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(service).passwordEncoder(new BCryptPasswordEncoder());
	}

	@Bean
	public SessionRegistry sessionRegistry() {
		return new SessionRegistryImpl();
	}

	/*@Bean
	public ServletListenerRegistrationBean<?> servletListenterRegistrationBean() {
		return new ServletListenerRegistrationBean<>(new HttpSessionEventPublisher());
	}*/
}
