package com.tansha.library.bookshelf.login.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

import com.tansha.library.bookshelf.login.configurations.MySimpleUrlAuthenticationSuccessHandler;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private DataSource dataSource;

    @Value("${spring.queries.users-query}")
    private String usersQuery;

    @Autowired
    private UserDetailsService userDetailsService;

  /*  @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {
    	
        auth.
                jdbcAuthentication()
                .usersByUsernameQuery(usersQuery)
                //.authoritiesByUsernameQuery(rolesQuery)
                .dataSource(dataSource)
                .passwordEncoder(bCryptPasswordEncoder);
    }*/
    
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.
                authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/error").permitAll()
                .antMatchers("/admin/**").permitAll()
                .antMatchers("/index").permitAll()
                .antMatchers("/terms").permitAll()
                .antMatchers("/howitworks").permitAll()
                .antMatchers("/pricing/**").permitAll()
                .antMatchers("/pricing_new/**").permitAll()
                .antMatchers("/pricing_cash/**").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/forgotpassword").permitAll()
                .antMatchers("/resetpassword").permitAll()
                .antMatchers("/reset").permitAll()
                .antMatchers("/registration").permitAll()
                .antMatchers("/addtocart/**").permitAll()
                .antMatchers("/addtocartbookdtails/**").permitAll()
                .antMatchers("/quickReadingLevelSearch/**").permitAll()
                .antMatchers("/quickSearch").permitAll()
                .antMatchers("/quickCategorySearch/**").permitAll()
                .antMatchers("/signin").permitAll()
                .antMatchers("/pricing/getSelectedSubscriptionDetails").permitAll()
                .antMatchers("/pricing_cash/getSelectedSubscriptionDetails").permitAll()
                .antMatchers("/initialRegistration/**").permitAll()
                .antMatchers("/search").permitAll()
                .antMatchers("/*Wrap*").permitAll()
                .antMatchers("/bookdetails/**").permitAll()
                .antMatchers("/getCartNumber/**").permitAll()
                .antMatchers("/selectSearch/**").permitAll()
                .antMatchers("/user/**").permitAll()
                .antMatchers("/user/**").hasAuthority("ROLE_USER").anyRequest()
                .authenticated().and().csrf().disable().formLogin()
                .loginPage("/login").failureUrl("/login?error=true")
                //.successHandler(myAuthenticationSuccessHandler())
                .defaultSuccessUrl("/dashboard")
                .usernameParameter("email")
                .passwordParameter("password")
                .and().logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/").and().exceptionHandling()
                .accessDeniedPage("/access-denied");
    }
    
    @Bean("authenticationManager")
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
            return super.authenticationManagerBean();
    }
    @Bean
    public AuthenticationSuccessHandler myAuthenticationSuccessHandler(){
        return new MySimpleUrlAuthenticationSuccessHandler();
    }
    
    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/resources/**", "/static/**", "/css/**","/css/pricing/styles/**", "/jQueryUI/**","/jQuery/**", "/fonts/**", "/js/**","/webfonts/**","/bookimages/**", "/images/**");
    }

}
