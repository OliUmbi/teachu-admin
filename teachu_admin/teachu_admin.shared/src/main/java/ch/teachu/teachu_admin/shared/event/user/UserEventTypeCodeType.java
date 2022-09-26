package ch.teachu.teachu_admin.shared.event.user;

import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.shared.services.common.code.AbstractCode;
import org.eclipse.scout.rt.shared.services.common.code.AbstractCodeType;

public class UserEventTypeCodeType extends AbstractCodeType<String, String> {
  private static final long serialVersionUID = 1L;
  public static final String ID = "UserEventType";

  @Override
  public String getId() {
    return ID;
  }

  @Order(1000)
  public static class SickCode extends AbstractCode<String> {
    private static final long serialVersionUID = 1L;
    public static final String ID = "sick";

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("Sick");
    }

    @Override
    public String getId() {
      return ID;
    }
  }

  @Order(2000)
  public static class HolidayCode extends AbstractCode<String> {
    private static final long serialVersionUID = 1L;
    public static final String ID = "holiday";

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("Holiday");
    }

    @Override
    public String getId() {
      return ID;
    }
  }
}
