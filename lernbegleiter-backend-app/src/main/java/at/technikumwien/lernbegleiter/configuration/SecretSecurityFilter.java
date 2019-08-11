package at.technikumwien.lernbegleiter.configuration;

import at.technikumwien.lernbegleiter.components.AuthHelper;
import at.technikumwien.lernbegleiter.data.UserAuthentication;
import at.technikumwien.lernbegleiter.services.user.LoginService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SecretSecurityFilter extends GenericFilterBean {
    private final LoginService loginService;
    private final AuthHelper authHelper;

    public SecretSecurityFilter(LoginService loginService, AuthHelper authHelper) {
        this.loginService = loginService;
        this.authHelper = authHelper;
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

