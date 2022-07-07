package ch.teachu.teachu_admin.shared;

import java.security.BasicPermission;

public class AdminPermission extends BasicPermission {

  private static final long serialVersionUID = 1L;

  public AdminPermission() {
    super(AdminPermission.class.getName());
  }
}
