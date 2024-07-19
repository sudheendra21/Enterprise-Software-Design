package com.csye6220.Elearning.securingweb;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/", "/home").permitAll()
                        .anyRequest().authenticated()


                )
                .formLogin((form) -> form
                        .loginPage("/login")
                        .successHandler(myAuthenticationSuccessHandler())
                        .permitAll()
                )
                .logout((logout) -> logout.permitAll())

                .csrf(csrf -> csrf.disable());



        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user =
                User.withDefaultPasswordEncoder()
                        .username("user")
                        .password("password")
                        .roles("USER")
                        .build();
        UserDetails user2 =
                User.withDefaultPasswordEncoder()
                        .username("salman")
                        .password("virat")
                        .roles("ADMIN")
                        .build();

        UserDetails user3 =
                User.withDefaultPasswordEncoder()
                        .username("Sankeerthana")
                        .password("youmylove")
                        .roles("USER")
                        .build();
        UserDetails user4 =
                User.withDefaultPasswordEncoder()
                        .username("Sudheendra")
                        .password("Sabnavisu")
                        .roles("USER")
                        .build();
        UserDetails user5 =
                User.withDefaultPasswordEncoder()
                        .username("Sudheer")
                        .password("Muppavarapu")
                        .roles("USER")
                        .build();

        UserDetails user6 =
                User.withDefaultPasswordEncoder()
                        .username("Lakshmi")
                        .password("Kumar")
                        .roles("USER")
                        .build();

        UserDetails user7 =
                User.withDefaultPasswordEncoder()
                        .username("Admin")
                        .password("admin")
                        .roles("ADMIN")
                        .build();

        return new InMemoryUserDetailsManager(user,user2,user3,user4,user5,user6,user7);
    }



    @Bean
    public AuthenticationSuccessHandler myAuthenticationSuccessHandler(){
        return new MySimpleUrlAuthenticationSuccessHandler();
    }
}