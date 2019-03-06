package at.technikumwien.lernbegleiter.configuration;

import at.technikumwien.lernbegleiter.data.UserAuthentication;
import at.technikumwien.lernbegleiter.services.LoginService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SecretSecurityFilter implements Filter {
  private final LoginService loginService;

  public SecretSecurityFilter(LoginService loginService) {
    this.loginService = loginService;
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    HttpServletRequest hsr = (HttpServletRequest) request;

    try {
      String authorization = hsr.getHeader("Authorization");

      if (authorization != null) {
        UserAuthentication ua = loginService.getAuthenticationForSecretOrThrow(authorization);
        SecurityContextHolder.getContext().setAuthentication(ua);
      }

      chain.doFilter(request, response);
    } catch (ResponseStatusException ex) {
      HttpServletResponse hsres = (HttpServletResponse) response;
      hsres.setStatus(HttpStatus.UNAUTHORIZED.value());
      hsres.getWriter().write(ex.getMessage()); // TODO improve
    } finally {
      SecurityContextHolder.getContext().setAuthentication(null);
    }
  }
}
