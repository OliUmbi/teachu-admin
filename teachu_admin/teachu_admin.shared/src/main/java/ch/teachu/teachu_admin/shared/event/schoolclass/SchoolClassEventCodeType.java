package ch.teachu.teachu_admin.shared.event.schoolclass;

import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.shared.services.common.code.AbstractCode;
import org.eclipse.scout.rt.shared.services.common.code.AbstractCodeType;

public class SchoolClassEventCodeType extends AbstractCodeType<String, String> {
  private static final long serialVersionUID = 1L;
  public static final String ID = "SchoolClassEvent";

  @Override
  public String getId() {
    return ID;
  }

  @Order(1000)
  public static class TripCode extends AbstractCode<String> {
    private static final long serialVersionUID = 1L;
    public static final String ID = "trip";

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("Trip");
    }

    @Override
    public String getId() {
      return ID;
    }
  }

  @Order(2000)
  public static class SchoolCode extends AbstractCode<String> {
    private static final long serialVersionUID = 1L;
    public static final String ID = "school";

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("School");
    }

    @Override
    public String getId() {
      return ID;
    }
  }
}
