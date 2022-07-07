package ch.teachu.teachu_admin.server;

import ch.teachu.teachu_admin.shared.AdminPermission;
import ch.teachu.teachu_admin.shared.TeacherPermission;
import org.eclipse.scout.rt.platform.ApplicationScoped;
import org.eclipse.scout.rt.security.ACCESS;

@ApplicationScoped
public class AccessHelper {

  public void ensureAdmin() {
    ACCESS.checkAndThrow(new AdminPermission());
  }

  public void ensureTeacher() {
    ACCESS.checkAndThrow(new TeacherPermission());
  }
}
