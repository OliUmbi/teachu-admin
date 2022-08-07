package ch.teachu.teachu_admin.shared.event.school;

import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.shared.services.common.code.AbstractCode;
import org.eclipse.scout.rt.shared.services.common.code.AbstractCodeType;

public class SchoolEventCodeType extends AbstractCodeType<String, String> {
  private static final long serialVersionUID = 1L;
  public static final String ID = "SchoolEvent";

  @Override
  public String getId() {
    return ID;
  }

  @Order(1000)
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

  @Order(2000)
  public static class ActivityCode extends AbstractCode<String> {
    private static final long serialVersionUID = 1L;
    public static final String ID = "activity";

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("Activity");
    }

    @Override
    public String getId() {
      return ID;
    }
  }

  @Order(3000)
  public static class NoteCode extends AbstractCode<String> {
    private static final long serialVersionUID = 1L;
    public static final String ID = "note";

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("Note");
    }

    @Override
    public String getId() {
      return ID;
    }
  }
}
