package ch.teachu.teachu_admin.server;

import ch.teachu.teachu_admin.shared.AdminPermission;
import ch.teachu.teachu_admin.shared.TeacherPermission;
import org.eclipse.scout.rt.platform.ApplicationScoped;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.security.ACCESS;
import org.eclipse.scout.rt.security.IAccessControlService;
import org.eclipse.scout.rt.server.jdbc.SQL;

@ApplicationScoped
public class AccessHelper {

  public void ensureAdmin() {
    ACCESS.checkAndThrow(new AdminPermission());
  }

  public void ensureTeacher() {
    ACCESS.checkAndThrow(new TeacherPermission());
  }

  public String getCurrentUserId() {
    return (String) SQL.select("SELECT BIN_TO_UUID(id) FROM user where email = :email",
      new NVPair("email", BEANS.get(IAccessControlService.class).getUserIdOfCurrentSubject()))[0][0];
  }
}
