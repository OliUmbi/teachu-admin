package ch.teachu.teachu_admin.shared.event.user;

import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.shared.services.common.code.AbstractCode;
import org.eclipse.scout.rt.shared.services.common.code.AbstractCodeType;

public class UserEventStateCodeType extends AbstractCodeType<String, String> {
  private static final long serialVersionUID = 1L;
  public static final String ID = "UserEventState";

  @Override
  public String getId() {
    return ID;
  }

  @Order(1000)
  public static class PendingCode extends AbstractCode<String> {
    private static final long serialVersionUID = 1L;
    public static final String ID = "pending";

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("PendingCode");
    }

    @Override
    public String getId() {
      return ID;
    }
  }

  @Order(2000)
  public static class VerifiedCode extends AbstractCode<String> {
    private static final long serialVersionUID = 1L;
    public static final String ID = "verified";

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("Verified");
    }

    @Override
    public String getId() {
      return ID;
    }
  }

  @Order(3000)
  public static class ExcusedCode extends AbstractCode<String> {
    private static final long serialVersionUID = 1L;
    public static final String ID = "excused";

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("Excused");
    }

    @Override
    public String getId() {
      return ID;
    }
  }

  @Order(4000)
  public static class UnexcusedCode extends AbstractCode<String> {
    private static final long serialVersionUID = 1L;
    public static final String ID = "unexcused";

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("Unexcused");
    }

    @Override
    public String getId() {
      return ID;
    }
  }
}
