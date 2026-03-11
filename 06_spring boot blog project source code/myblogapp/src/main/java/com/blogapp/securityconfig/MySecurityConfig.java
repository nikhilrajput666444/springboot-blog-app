package com.blogapp.securityconfig;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class MySecurityConfig {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests((requests) -> requests.requestMatchers("/auth/**").permitAll()
				.requestMatchers("/dashboard/admin").hasAuthority("ADMIN")
				.requestMatchers("/dashboard/guest").hasAuthority("GUEST")
				.requestMatchers("/admin/category/**").hasAuthority("ADMIN")
				.requestMatchers("/post/allposts","/post/newpost","/post/savepost","/post/editpostdata"
						,"/post/updatepost","/post/deletepost","/post/viewpostdata").hasAnyAuthority("ADMIN","GUEST")
				.requestMatchers("/post/admin/approvePost/**","/post/admin/rejectPost/**"
						,"/post/admin/viewallcomments/**","/post/admin/delete-comment/**").hasAuthority("ADMIN")
				
				
				.requestMatchers("/", "/category/**", "/blogpost/**", "/searchblogpost", "/addcomment", "/postfiles/**","/error")
				.permitAll());

		http.formLogin(form -> form.loginPage("/auth/customlogin").loginProcessingUrl("/login").defaultSuccessUrl("/")
				.permitAll());

		http.httpBasic(withDefaults());
		return http.build();
	}

}
