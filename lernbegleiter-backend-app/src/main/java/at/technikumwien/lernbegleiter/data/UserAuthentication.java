package at.technikumwien.lernbegleiter.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

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
