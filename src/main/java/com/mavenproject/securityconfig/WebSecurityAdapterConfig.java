package com.mavenproject.securityconfig;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;

import static org.springframework.security.core.userdetails.User.*;

@Configuration
@EnableWebSecurity
public class WebSecurityAdapterConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        UserBuilder userBuilders= User.withDefaultPasswordEncoder();
        authenticationManagerBuilder.inMemoryAuthentication()
                .withUser(userBuilders.username("Lekan").password("wqa").roles("Employee"))
                .withUser(userBuilders.username("Lanre").password("lsa").roles("Employee", "Manager"))
                .withUser(userBuilders.username("Gafar").password("gfr").roles("Employee", "CEO"))
                .withUser(userBuilders.username("Toke").password("1234").roles("Employee", "Admin"));

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/home-page").permitAll()
                .antMatchers("/Admins").hasRole("Admin")
                .antMatchers("/Leaders/**").hasRole("Manager")
                .antMatchers("/GeneralLeader").hasRole("CEO")
                .antMatchers("/GenLeader").hasRole("CEO")
                .and()
                .formLogin()
                .loginPage("/loginPage")
                .loginProcessingUrl("/loginAuthentication")
                .permitAll()
                .and()
                .logout()
                .permitAll();
    }
}
