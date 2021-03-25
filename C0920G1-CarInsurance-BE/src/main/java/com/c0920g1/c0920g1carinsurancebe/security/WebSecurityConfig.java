package com.c0920g1.c0920g1carinsurancebe.security;
import com.c0920g1.c0920g1carinsurancebe.security.jwt.AuthEntryPointJwt;
import com.c0920g1.c0920g1carinsurancebe.security.jwt.AuthTokenFilter;
import com.c0920g1.c0920g1carinsurancebe.security.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//Tran Minh Chien
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        prePostEnabled = true
)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    UserDetailsServiceImpl userDetailsService;
    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;
    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }
    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/api/auth/signin", "/api/auth/signup").permitAll()
                .antMatchers("/api/test/**").permitAll()
                .and()
                .authorizeRequests()
                .antMatchers("/api/auth/admin/**",
                        "/api/admin/employee/list/*",
                        "/api/admin/employee/list",
                        "/api/admin/users/*",
                        "/api/admin/position",
                        "/api/admin/employee/update/*",
                        "/api/admin/accident/accidentWaitingApproval",
                        "/api/admin/accident/accidentNotApproval",
                        "/api/admin/accident/accidentWasApproval",
                        "/api/admin/accident/wasRefund/*",
                        "/api/admin/accident/*",
                        "/api/admin/accident",
                        "/api/admin/accident/*"
                ).access("hasRole('ROLE_ADMIN')").and()
                .authorizeRequests()

                .antMatchers("/api/auth/profile/*",
                        "/api/customer-client/*",
                        "/api/customer-client/OTP/*",
                        "/api/customer-client/getPass/*",
                        "/api/questions/*",
                        "/qa/**")
                .access("hasAnyRole('ROLE_ADMIN', 'ROLE_CUSTOMER', 'ROLE_EMPLOYEE')")
                .and()
                .authorizeRequests()
                .antMatchers("/api/employee/detail/*",
                        "/api/employee/users/*",
                        "/api/employee/changepass/*",
                        "api/employee/OTP/*",
                        "api/employee/getPass/*",
                        "/api/employee/product/list-status-true",
                        "/api/employee/product/list-status-false",
                        "/api/employee/product/car-type",
                        "/api/employee/product/product-type",
                        "/api/employee/product/create",
                        "/api/employee/product/*",
                        "/api/employee/product",
                        "/api/employee/product/update/*",
                        "/api/employee/customer-edit/*",
                        "/api/employee/customer-list",
                        "/api/employee/customer-create",
                        "/api/employee/cars-create",
                        "/api/employee/cars-list",
                        "/api/employee/customer-delete/*").access("hasAnyRole('ROLE_EMPLOYEE','ROLE_ADMIN')")
                .and()
                .authorizeRequests()
                .antMatchers("/api/pay/**","/api/bill/**","/api/checkout/paypp","/api/checkout/payment-paypal","/api/checkout/payment-offline").access("hasAnyRole('ROLE_EMPLOYEE', 'ROLE_CUSTOMER','ROLE_ADMIN')")
                .anyRequest().authenticated();
        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}