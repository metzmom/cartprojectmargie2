package com.metzm.cartprojectmargie2.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(encoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers("/admin/**").hasAnyRole("ADMIN")//only let admin change pages
            //   .antMatchers("/cart/**").hasAnyRole("USER", "ADMIN" )//only let admin change pages
            //    .antMatchers("/cart_view/**").hasAnyRole("USER", "ADMIN" )
                       .antMatchers("/").permitAll()
                            .and()
                                .formLogin()
                                    .loginPage("/login")
                            .and()
                                .logout()
                                    .logoutSuccessUrl("/")
                            .and()
                                    .exceptionHandling()
                                        .accessDeniedPage("/");
        // .antMatchers("/**").hasAnyRole("USER");

        // http
        //     .authorizeRequests()
        //         .antMatchers("/categories/**").access("hasRole('ROLE_USER')")
        //         .antMatchers("/").access("permitAll");
    }

}
//import org.springframework.beans.factory.annotation.Autowired;
//        import org.springframework.context.annotation.Bean;
//        import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//        import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//        import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//        import org.springframework.security.core.userdetails.UserDetailsService;
//        import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//        import org.springframework.security.crypto.password.PasswordEncoder;
//        import org.springframework.stereotype.Service;
//
//@Service
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//    @Autowired
//    private UserDetailsService userDetailsService;
//
//    @Bean
//    public PasswordEncoder encoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Override//looking up data on user
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth
//                .userDetailsService(userDetailsService)
//                .passwordEncoder(encoder());
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .authorizeRequests()
//            //   .antMatchers("/category/**").hasAnyRole("USER", "ADMIN")
//                .antMatchers("/pages/**").hasAnyRole( "ADMIN")
//            //  .antMatchers("/admin/**").hasAnyRole("ADMIN")
//                .antMatchers("/").permitAll()
//                     .and()
//                         .formLogin()
//                         .loginPage("/login")
//                              .and()
//                                 .logout()
//                                 .logoutSuccessUrl("/")
//                              .and()
//                                  .exceptionHandling()
//                                      .accessDeniedPage("/");
//        // .antMatchers("/**").hasAnyRole("USER");
//
//        // http
//        //     .authorizeRequests()
//        //         .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
//        //         .antMatchers("/").access("permitAll");
//    }
//
//}