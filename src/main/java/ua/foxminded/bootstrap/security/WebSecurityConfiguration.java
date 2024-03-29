package ua.foxminded.bootstrap.security;

import java.util.Set;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.AuthorityUtils;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import ua.foxminded.bootstrap.service.UserService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration {
    private static final Logger LOGGER = Logger.getLogger(WebSecurityConfiguration.class.getName());
    private static final String LOGIN = "/register/login";
    private static final String[] WHITE_LIST_URLS = {
            LOGIN,
            "/webjars/**",
            "/css/**",
            "/js/**",
            "/images/**"
    };

    @Autowired
    UserService userService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .userDetailsService(userService)
                .authorizeHttpRequests(authorize ->
                        authorize.requestMatchers(WHITE_LIST_URLS).permitAll()
                        .requestMatchers("/courses", "/timetables", "/groups").hasAnyRole("ADMIN", "STAFF", "TEACHER", "STUDENT")
                        .requestMatchers("/update-course", "/add-course", "/timetables/**").hasAnyRole("ADMIN", "STAFF")
                        .requestMatchers("/students/**").hasAnyRole("STUDENT", "ADMIN", "STAFF")
                        .requestMatchers("/admin/**", "/delete-course", "/rooms", "/welcome-admin").hasRole("ADMIN")
                        .requestMatchers("/staff/**", "/welcome-staff").hasRole("STAFF")
                        .requestMatchers("/teachers/**", "/welcome-teacher").hasRole("TEACHER")
                        .requestMatchers("/welcome-student").hasRole("STUDENT")
                        .requestMatchers("/anonymous").anonymous()
                        .anyRequest().authenticated())
                .formLogin(formLogin ->
                        formLogin.passwordParameter("password")
                                 .loginPage(LOGIN)
                                 .defaultSuccessUrl("/", true)
                                 .successHandler(successHandler()))
                .logout(logout ->
                        logout.logoutUrl("/register/logout")
                              .logoutSuccessUrl(LOGIN)
                              .invalidateHttpSession(true)
                              .deleteCookies("JSESSIONID"));

        return http.build();
    }
    
    private AuthenticationSuccessHandler successHandler() {
        return (request, response, authentication) -> {
            Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());

            if (roles.contains("ROLE_ADMIN")) {
                LOGGER.info("Redirecting to /welcome-admin");
                response.sendRedirect("/welcome-admin");
            } else if (roles.contains("ROLE_STUDENT")) {
                LOGGER.info("Redirecting to /welcome-student");
                response.sendRedirect("/welcome-student");
            } else if (roles.contains("ROLE_TEACHER")) {
                LOGGER.info("Redirecting to /welcome-teacher");
                response.sendRedirect("/welcome-teacher");
            } else if (roles.contains("ROLE_STAFF")) {
                LOGGER.info("Redirecting to /welcome-staff");
                response.sendRedirect("/welcome-staff");
            } else {
                response.sendRedirect(LOGIN);
            }
        };
    }
}
