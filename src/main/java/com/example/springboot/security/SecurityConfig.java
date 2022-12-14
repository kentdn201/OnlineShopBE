package com.example.springboot.security;

import com.example.springboot.config.JWTAuthenticationFilter;
import com.example.springboot.config.JWTTokenHelper;
import com.example.springboot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserService userService;

    @Autowired
    private JWTTokenHelper jwtTokenHelper;

    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//      In Memory
        auth.inMemoryAuthentication().withUser("admin1").password(passwordEncoder().encode("admin1")).authorities("USER", "ADMIN");

//      In Database
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .and()
                .authorizeRequests(
                        (request) -> request
                                .antMatchers(
                                        "/api/v1/auth/login/**",
                                        "/product/all/**",
                                        "/product/{slug}/**",
                                        "/category/all/**",
                                        "/category/{slug}/**")
                                .permitAll()
                                .antMatchers(GET, "/api/v1/auth/userinfo/**", "/order/{userId}/**", "/order/don-hang/{id}/**")
                                .hasAnyAuthority("USER")
                                .antMatchers(POST, "/order/create/{userId}")
                                .hasAnyAuthority("USER")
                                .antMatchers("/**")
                                .hasAnyAuthority("ADMIN")
                                .anyRequest()
                                .authenticated()
                )
                .addFilterBefore(new JWTAuthenticationFilter(userService, jwtTokenHelper), UsernamePasswordAuthenticationFilter.class);

        http.csrf().disable().cors().and().headers().frameOptions().disable();
    }

    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}



