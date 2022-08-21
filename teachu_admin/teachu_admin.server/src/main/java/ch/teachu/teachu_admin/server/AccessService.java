package ch.teachu.teachu_admin.server;

import ch.teachu.teachu_admin.shared.IAccessService;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.security.IAccessControlService;
import org.eclipse.scout.rt.server.jdbc.SQL;

public class AccessService implements IAccessService {
  @Override
  public String getCurrentUserId() {
    Object[][] result = SQL.select("SELECT BIN_TO_UUID(id) FROM user where email = :email",
      new NVPair("email", BEANS.get(IAccessControlService.class).getUserIdOfCurrentSubject()));
    if (result.length == 0) {
      return null;
    }
    return (String) result[0][0];
  }
}
