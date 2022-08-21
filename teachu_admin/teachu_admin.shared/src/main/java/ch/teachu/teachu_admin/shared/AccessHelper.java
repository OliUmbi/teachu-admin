package ch.teachu.teachu_admin.shared;

import org.eclipse.scout.rt.platform.ApplicationScoped;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.security.ACCESS;

@ApplicationScoped
public class AccessHelper {

  public void ensureAdmin() {
    ACCESS.checkAndThrow(new AdminPermission());
  }

  public void ensureTeacher() {
    ACCESS.checkAndThrow(new TeacherPermission());
  }

  public String getCurrentUserId() {
    return BEANS.get(IAccessService.class).getCurrentUserId();
  }
}
