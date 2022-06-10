package com.sac.backend.security;

import com.sac.backend.appuser.AppUserRole;
import com.sac.backend.security.filter.CustomAuthenticationFilter;
import com.sac.backend.security.filter.CustomAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final Environment environment;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManagerBean(), getSecret());
        customAuthenticationFilter.setFilterProcessesUrl("/api/login");
        http
                .cors().and()
                .csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                    .antMatchers("/api/login/**").permitAll()
                    .antMatchers("/files/get/**").permitAll()
                    .antMatchers("/api/token/refresh").permitAll()
                    .antMatchers(GET, "/api/user/**").permitAll()
                    .antMatchers(GET, "/agenda/status").permitAll()
                    .antMatchers(GET, "/agenda/**").hasAnyAuthority(AppUserRole.ADMIN.name())
                    .antMatchers(GET, "/agenda/").hasAnyAuthority(AppUserRole.ADMIN.name())
                    .antMatchers(PUT, "/agenda").hasAnyAuthority(AppUserRole.ADMIN.name())
                    .antMatchers(POST, "/agenda").hasAnyAuthority(AppUserRole.ADMIN.name())
                    .antMatchers(DELETE, "/agenda/**").hasAnyAuthority(AppUserRole.ADMIN.name())
                    .antMatchers(GET, "/agendamento/").hasAnyAuthority(AppUserRole.ADMIN.name())
                    .antMatchers(GET, "/agendamento/**").hasAnyAuthority(AppUserRole.ADMIN.name())
                    .antMatchers(POST, "/agendamento/").permitAll()
                    .antMatchers(PUT, "/agendamento/").hasAnyAuthority(AppUserRole.ADMIN.name())
                    .antMatchers(DELETE, "/agendamento/").hasAnyAuthority(AppUserRole.ADMIN.name())
                    .antMatchers(GET, "/post/todos/**").hasAnyAuthority(AppUserRole.ADMIN.name())
                    .antMatchers(GET, "/post/").permitAll()
                    .antMatchers(GET, "/post/admin/**").hasAnyAuthority(AppUserRole.ADMIN.name())
                    .antMatchers(GET, "/post/**").permitAll()
                    .antMatchers(POST, "/post/").permitAll()
                    .antMatchers(PUT, "/post/**").hasAnyAuthority(AppUserRole.ADMIN.name())
                    .antMatchers(DELETE, "/post/**").hasAnyAuthority(AppUserRole.ADMIN.name())
                    .antMatchers(POST, "/api/user/save/**").hasAnyAuthority(AppUserRole.ADMIN.name())
                .anyRequest().authenticated().and()
                .addFilter(customAuthenticationFilter)
                .addFilterBefore(new CustomAuthorizationFilter(getSecret()), UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    private String getSecret() {
        return environment.getProperty("jwt.secret");
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("authorization", "content-type", "x-auth-token"));
        configuration.setExposedHeaders(Arrays.asList("x-auth-token"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
