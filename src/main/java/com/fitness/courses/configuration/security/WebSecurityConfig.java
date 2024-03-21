package com.fitness.courses.configuration.security;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import com.fitness.courses.configuration.security.jwt.JwtRequestFilter;

/**
 * Конфигурационный класс для настройки авторизации в приложении.
 */
@EnableWebSecurity
@Configuration
public class WebSecurityConfig
{
    private final UserDetailsService userDetailsService;
    private final JwtRequestFilter jwtRequestFilter;

    public WebSecurityConfig(
            UserDetailsService userDetailsService,
            JwtRequestFilter jwtRequestFilter)
    {
        this.userDetailsService = userDetailsService;
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception
    {
        return authenticationConfiguration.getAuthenticationManager();
    }

    // TODO проверить, можно ли убрать
    @Bean
    public UserDetailsService customUserDetailsService()
    {
        return userDetailsService;
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider()
    {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(customUserDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public FilterRegistrationBean corsFilter()
    {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConf = new CorsConfiguration();
        corsConf.setAllowCredentials(Boolean.TRUE);
        corsConf.addAllowedOriginPattern("*");
        corsConf.addAllowedHeader(CorsConfiguration.ALL);
        corsConf.addAllowedMethod(CorsConfiguration.ALL);
        source.registerCorsConfiguration("/**", corsConf);
        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
        bean.setOrder(0);
        return bean;
    }

    /*@Bean
    public CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.setAllowedOrigins(Collections.singletonList("*"));
        config.setAllowedHeaders(Arrays.asList("Origin", "Content-Type", "Accept", "responseType", "Authorization"));
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "OPTIONS", "DELETE", "PATCH"));
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }*/

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, HandlerMappingIntrospector introspector)
            throws Exception
    {
        MvcRequestMatcher.Builder mvcRequestMatcher = new MvcRequestMatcher.Builder(introspector);

        CorsConfiguration corsConfig = new CorsConfiguration().applyPermitDefaultValues();
        corsConfig.addAllowedMethod(HttpMethod.DELETE);
        corsConfig.addAllowedMethod(HttpMethod.PUT);
        corsConfig.addAllowedMethod(HttpMethod.GET);
        corsConfig.addAllowedMethod(HttpMethod.POST);

        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
//                .cors(cors -> cors.configurationSource(request -> corsConfig))
                .headers(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(
                                mvcRequestMatcher.pattern("/auth/**"),
                                mvcRequestMatcher.pattern("/swagger-ui/**"),
                                mvcRequestMatcher.pattern("/v3/**"),
                                mvcRequestMatcher.pattern("/public/course/**"),
                                mvcRequestMatcher.pattern("/coach/**"),
                                toH2Console()).permitAll()
                        .anyRequest().authenticated())
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                        // TODO поменять ошибку на ошибку сервера
                        .accessDeniedHandler(new AccessDeniedHandlerImpl()))
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
