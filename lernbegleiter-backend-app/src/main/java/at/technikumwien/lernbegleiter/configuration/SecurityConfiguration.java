package at.technikumwien.lernbegleiter.configuration;

import at.technikumwien.lernbegleiter.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, proxyTargetClass = true)
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
  @Autowired
  private LoginService loginService;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.addFilterBefore(new SecretSecurityFilter(loginService), AnonymousAuthenticationFilter.class);
    http
        .csrf().disable()
        .sessionManagement().disable()
        .formLogin().disable()
        .logout().disable()
        .httpBasic().disable()
        .logout().disable()
        .authorizeRequests()
        .antMatchers("/api/login").permitAll()
        .antMatchers("/**").authenticated()
    ;
  }

  @Bean
  GrantedAuthorityDefaults grantedAuthorityDefaults() {
    return new GrantedAuthorityDefaults(""); // Remove the ROLE_ prefix
  }
}
