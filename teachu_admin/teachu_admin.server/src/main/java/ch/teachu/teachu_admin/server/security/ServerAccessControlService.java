package ch.teachu.teachu_admin.server.security;

import ch.teachu.teachu_admin.shared.AdminPermission;
import ch.teachu.teachu_admin.shared.TeacherPermission;
import ch.teachu.teachu_admin.shared.security.AccessControlService;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Replace;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.security.DefaultPermissionCollection;
import org.eclipse.scout.rt.security.IPermissionCollection;
import org.eclipse.scout.rt.security.PermissionLevel;
import org.eclipse.scout.rt.server.jdbc.SQL;
import org.eclipse.scout.rt.shared.security.RemoteServiceAccessPermission;

@Replace
public class ServerAccessControlService extends AccessControlService {

  @Override
  protected IPermissionCollection execLoadPermissions(String userId) {
    IPermissionCollection permissions = BEANS.get(DefaultPermissionCollection.class);
    Object[][] roleObj = SQL.select("SELECT role FROM user WHERE email=:email", new NVPair("email", userId));
    if (roleObj.length == 1) {
      String role = (String) roleObj[0][0];
      if ("teacher".equals(role)) {
        permissions.add(new TeacherPermission());
      } else if ("admin".equals(role)) {
        permissions.add(new AdminPermission());
      }
    } else {
      permissions.add(new TeacherPermission());
      permissions.add(new AdminPermission());
    }
    permissions.add(new RemoteServiceAccessPermission("*.shared.*", "*"), PermissionLevel.ALL);
    permissions.setReadOnly();
    return permissions;
  }
}
