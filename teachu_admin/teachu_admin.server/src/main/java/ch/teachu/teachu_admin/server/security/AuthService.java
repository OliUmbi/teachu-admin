package ch.teachu.teachu_admin.server.security;

import ch.teachu.teachu_admin.shared.security.IAuthService;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.server.jdbc.SQL;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.eclipse.scout.rt.platform.security.ICredentialVerifier.AUTH_FAILED;
import static org.eclipse.scout.rt.platform.security.ICredentialVerifier.AUTH_OK;

public class AuthService implements IAuthService {
  @Override
  public int login(String email, char[] password) {
    Object[][] result = SQL.select("SELECT password FROM user WHERE email=:email AND active=true AND (termination_date IS NULL OR termination_date > current_timestamp) and (role = 'admin' or role = 'teacher')", new NVPair("email", email));
    if (result.length != 0 && new BCryptPasswordEncoder().matches(String.valueOf(password), (String) result[0][0])) {
      return AUTH_OK;
    }
    return AUTH_FAILED;
  }
}
