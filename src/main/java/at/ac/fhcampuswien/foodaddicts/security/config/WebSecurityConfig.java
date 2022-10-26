package at.ac.fhcampuswien.foodaddicts.security.config;

import at.ac.fhcampuswien.foodaddicts.model.AppUserRole;
//import at.ac.fhcampuswien.foodaddicts.security.CustomAccessDeniedHandler;
import at.ac.fhcampuswien.foodaddicts.security.filter.JwtRequestFilter;
import at.ac.fhcampuswien.foodaddicts.service.AppUserService;
import at.ac.fhcampuswien.foodaddicts.util.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@AllArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final AppUserService appUserService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final JwtUtil jwtUtil;

    private final UserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .httpBasic()
                .and()
                .authorizeRequests()
                .antMatchers("/", "/api/v*/registration", "/api/v*/login", "index", "/css/*", "/js/*").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v*/**").permitAll()
                .antMatchers(HttpMethod.OPTIONS, "/api/v*/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/**").hasAnyAuthority(AppUserRole.USER.name(), AppUserRole.ADMIN.name())
                .antMatchers(HttpMethod.PUT, "/api/**").hasAnyAuthority(AppUserRole.USER.name(), AppUserRole.ADMIN.name())
                .antMatchers(HttpMethod.DELETE, "/api/**").hasAuthority(AppUserRole.ADMIN.name())
                .anyRequest()
                .authenticated()
                .and()
                .addFilterBefore(jwtRequestFilter(), BasicAuthenticationFilter.class);
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Bean
    public JwtRequestFilter jwtRequestFilter() {
        return new JwtRequestFilter(jwtUtil, userDetailsService);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(bCryptPasswordEncoder);
        provider.setUserDetailsService(appUserService);
        return provider;
    }

//    @Bean
//    public AuthenticationFailureHandler authenticationFailureHandler() {
//        return new CustomAuthenticationFailureHandler();
//    }
}