package at.technikumwien.lernbegleiter.configuration;

import at.technikumwien.lernbegleiter.components.*;
import at.technikumwien.lernbegleiter.services.user.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.method.configuration.*;
import org.springframework.security.config.annotation.web.builders.*;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.config.core.*;
import org.springframework.security.config.http.*;
import org.springframework.security.web.authentication.*;

@EnableWebSecurity(debug = false)
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, proxyTargetClass = true)
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
  @Autowired
  private LoginService loginService;
  @Autowired
  private AuthHelper authHelper;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.addFilterBefore(new SecretSecurityFilter(loginService, authHelper), AnonymousAuthenticationFilter.class);
    http
      .csrf().disable()
      // .anonymous().disable()
      .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER).and()
      .formLogin().disable()
      .logout().disable()
      .httpBasic().disable()
      .authorizeRequests()
      .antMatchers("/api/login").permitAll()
      .antMatchers("/api/image/**").permitAll()
      .antMatchers("/unsecured-api/**").permitAll()
      .antMatchers("/**").authenticated()
    ;
  }

  @Bean
  GrantedAuthorityDefaults grantedAuthorityDefaults() {
    return new GrantedAuthorityDefaults(""); // Remove the ROLE_ prefix
  }
}
