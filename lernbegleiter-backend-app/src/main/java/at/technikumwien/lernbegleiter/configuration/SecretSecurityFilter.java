package at.technikumwien.lernbegleiter.configuration;

import at.technikumwien.lernbegleiter.components.*;
import at.technikumwien.lernbegleiter.data.*;
import at.technikumwien.lernbegleiter.services.user.*;
import org.springframework.http.*;
import org.springframework.security.core.context.*;
import org.springframework.web.filter.*;
import org.springframework.web.server.*;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.concurrent.*;

public class SecretSecurityFilter extends GenericFilterBean {
  private final LoginService loginService;
  private final AuthHelper authHelper;

  public SecretSecurityFilter(LoginService loginService, AuthHelper authHelper) {
    this.loginService = loginService;
    this.authHelper = authHelper;
  }

  @Override
  public void doFilter(
    ServletRequest request,
    ServletResponse response,
    FilterChain chain) throws IOException, ServletException {
    HttpServletRequest hsr = (HttpServletRequest) request;

    try {
      String authorization = hsr.getHeader("Authorization");

      if (authorization != null) {
        UserAuthentication ua = loginService.getAuthenticationForSecretOrThrowCached(authorization);
        SecurityContextHolder.getContext().setAuthentication(ua);
      }

      chain.doFilter(request, response);
    } catch (ResponseStatusException ex) {
      HttpServletResponse hsres = (HttpServletResponse) response;
      hsres.setStatus(HttpStatus.UNAUTHORIZED.value());
      hsres.getWriter().write(ex.getMessage()); // TODO improve
    } catch (ExecutionException e) {
      HttpServletResponse hsres = (HttpServletResponse) response;
      hsres.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
      hsres.getWriter().write(e.getMessage()); // TODO improve
    } finally {
      SecurityContextHolder.getContext().setAuthentication(null);
    }
  }
}

