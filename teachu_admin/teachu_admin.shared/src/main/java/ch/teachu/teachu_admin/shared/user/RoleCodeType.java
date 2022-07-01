package ch.teachu.teachu_admin.shared.user;

import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.shared.services.common.code.AbstractCode;
import org.eclipse.scout.rt.shared.services.common.code.AbstractCodeType;

public class RoleCodeType extends AbstractCodeType<String, String> {
  private static final long serialVersionUID = 1L;
  public static final String ID = "Role";

  @Override
  public String getId() {
    return ID;
  }

  @Order(1000)
  public static class ParentCode extends AbstractCode<String> {
    private static final long serialVersionUID = 1L;
    public static final String ID = "parent";

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("Parent");
    }

    @Override
    public String getId() {
      return ID;
    }
  }

  @Order(2000)
  public static class StudentCode extends AbstractCode<String> {
    private static final long serialVersionUID = 1L;
    public static final String ID = "student";

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("Student");
    }

    @Override
    public String getId() {
      return ID;
    }
  }

  @Order(3000)
  public static class TeacherCode extends AbstractCode<String> {
    private static final long serialVersionUID = 1L;
    public static final String ID = "teacher";

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("Teacher");
    }

    @Override
    public String getId() {
      return ID;
    }
  }

  @Order(4000)
  public static class AdminCode extends AbstractCode<String> {
    private static final long serialVersionUID = 1L;
    public static final String ID = "admin";

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("Administrator");
    }

    @Override
    public String getId() {
      return ID;
    }
  }
}
