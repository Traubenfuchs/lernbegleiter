package at.technikumwien.lernbegleiter.components;

import at.technikumwien.lernbegleiter.data.*;
import lombok.*;
import org.springframework.http.*;
import org.springframework.security.core.context.*;
import org.springframework.stereotype.*;
import org.springframework.web.server.*;

import java.util.*;

@Component
public class AuthHelper {
  public UserAuthentication getAuth() {
    return (UserAuthentication) SecurityContextHolder.getContext().getAuthentication();
  }

  public static String getCurrentUserUUIDOrThrow() {
    return getAuthOrThrow().getUuid();
  }

  public static UserAuthentication getAuthOrThrow() {
    UserAuthentication result = (UserAuthentication) SecurityContextHolder.getContext().getAuthentication();

    if (result == null) {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No Auth.");
    }

    return result;
  }

  public void isAdminOrTeacherOrCurrentUserUuidOrThrow(@NonNull String userUuid) {
    if (hasAnyRole("ADMIN", "TEACHER")) {
      return;
    }

    currentUserHasUuidOrThrow(userUuid);
  }

  public void isAdminOrTeacherOrThrow() {
    hasAnyRoleOrThrow("ADMIN", "TEACHER");
  }

  public boolean hasAnyRole(String... roles) {
    UserAuthentication ua = getAuthOrThrow();

    Set<String> rights = ua.getRights();

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
    return getAuthOrThrow().getRights().contains(role);
  }

  public void hasRoleOrThrow(String role) {
    if (!hasRole(role)) {
      throwResponseStatusException();
    }
  }

  public void currentUserHasUuidOrThrow(String uuid) {
    if (!Objects.equals(getAuthOrThrow().getUuid(), uuid)) {
      throwResponseStatusException();
    }
  }

  public void throwResponseStatusException() {
    throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You lack the required rights.");
  }

  public boolean isStudent() {
    return hasRole("STUDENT");
  }
}
