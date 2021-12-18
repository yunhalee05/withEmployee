package com.yunhalee.withEmployee.security;

import com.yunhalee.withEmployee.security.jwt.JwtAuthenticationEntryPoint;
import com.yunhalee.withEmployee.security.jwt.JwtRequestFilter;
import com.yunhalee.withEmployee.security.jwt.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(jwtUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.cors()
                .and()
            .csrf()
                .disable()
            .authorizeRequests()
                .antMatchers("/authenticate", "/user/login","/user/check_email","/user/register")
                    .permitAll()
                .antMatchers("/company/companylist","/team/teamlist","/user/userlist/**" ).hasAnyAuthority("Admin")
                .antMatchers("/company/save","/team/check_name","/team/save","/team/delete","/company/check_name","/companies/{id}","/company/delete/{id}").hasAnyAuthority("CEO","Admin")
                .antMatchers("/user/addTeam","/user/deleteTeam").hasAnyAuthority("CEO","Leader","Admin")
                .antMatchers("/user/addTeam","/user/deleteTeam","/user/save","/user/{id}","/profileUpload","/user/multipart","/conversation/**","/chat/**","/app/**","/message/**","/message").hasAnyAuthority("CEO","Leader","Member","Admin")
                .antMatchers("/chat/**","/join").permitAll()
                .anyRequest()
                    .authenticated()
                .and()
            .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and()
            .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
            .formLogin()
                .disable();

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/profileUploads/**","/messageUploads/**", "/js/**","/webjars/**");
    }
}
