package at.technikumwien.lernbegleiter.data;

import lombok.*;
import lombok.experimental.*;
import org.springframework.security.core.*;
import org.springframework.security.core.authority.*;

import java.util.*;

@Accessors(chain = true)
@Data
public class UserAuthentication implements Authentication {

  private String uuid;
  private Set<String> rights;
  private Collection<SimpleGrantedAuthority> sgas;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    if (sgas != null) {
      return sgas;
    }

    sgas = new ArrayList<>(rights.size());

    if (rights == null) {
      return sgas;
    }

    for (String right : rights) {
      sgas.add(new SimpleGrantedAuthority(right));
    }

    return sgas;
  }

  @Override
  public Object getCredentials() {
    throw new RuntimeException("Not implemented.");
  }

  @Override
  public Object getDetails() {
    throw new RuntimeException("Not implemented.");
  }

  @Override
  public Object getPrincipal() {
    return uuid;
  }

  @Override
  public boolean isAuthenticated() {
    return true;
  }

  @Override
  public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
    throw new RuntimeException("Not implemented.");
  }

  @Override
  public String getName() {
    return uuid;
  }
}
