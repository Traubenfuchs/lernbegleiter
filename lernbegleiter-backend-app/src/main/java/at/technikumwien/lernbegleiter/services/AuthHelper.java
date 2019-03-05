package at.technikumwien.lernbegleiter.services;

import at.technikumwien.lernbegleiter.data.UserAuthentication;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;
import java.util.Set;

@Component
public class AuthHelper {
  public static UserAuthentication getAuth() {
    return (UserAuthentication) SecurityContextHolder.getContext().getAuthentication();
  }

  public boolean hasAnyRole(String... roles) {
    Set<String> rights = getAuth().getRights();

    for (String role : roles) {
      if (rights.contains(role)) {
        return true;
      }
    }
    return false;
  }

  public void hasAnyRoleOrThrow(String... roles) {
    if (!hasAnyRole(roles)) {
      throwResponseStatusException();
    }
  }

  public boolean hasRole(String role) {
    return getAuth().getRights().contains(role);
  }

  public void hasRoleOrThrow(String role) {
    if (!hasRole(role)) {
      throwResponseStatusException();
    }
  }

  public void currentUserHasUuidOrThrow(String uuid) {
    if (!Objects.equals(getAuth().getUuid(), uuid)) {
      throwResponseStatusException();
    }
  }

  public void throwResponseStatusException() {
    throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You lack the required rights.");
  }
}
