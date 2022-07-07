package ch.teachu.teachu_admin.shared;

import java.security.BasicPermission;

public class TeacherPermission extends BasicPermission {

  private static final long serialVersionUID = 1L;

  public TeacherPermission() {
    super(TeacherPermission.class.getName());
  }
}
