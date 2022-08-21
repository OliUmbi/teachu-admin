package ch.teachu.teachu_admin.shared.lesson;

import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.shared.services.common.code.AbstractCode;
import org.eclipse.scout.rt.shared.services.common.code.AbstractCodeType;

public class WeekdayCodeType extends AbstractCodeType<String, String> {
  private static final long serialVersionUID = 1L;
  public static final String ID = "weekday";

  @Override
  public String getId() {
    return ID;
  }

  @Order(1000)
  public static class MondayCode extends AbstractCode<String> {
    private static final long serialVersionUID = 1L;
    public static final String ID = "monday";

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("Monday");
    }

    @Override
    public String getId() {
      return ID;
    }
  }

  @Order(2000)
  public static class ThuesdayCode extends AbstractCode<String> {
    private static final long serialVersionUID = 1L;
    public static final String ID = "thuesday";

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("Thuesday");
    }

    @Override
    public String getId() {
      return ID;
    }
  }

  @Order(3000)
  public static class WednesdayCode extends AbstractCode<String> {
    private static final long serialVersionUID = 1L;
    public static final String ID = "wednesday";

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("Wednesday");
    }

    @Override
    public String getId() {
      return ID;
    }
  }

  @Order(4000)
  public static class ThursdayCode extends AbstractCode<String> {
    private static final long serialVersionUID = 1L;
    public static final String ID = "thursday";

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("Thursday");
    }

    @Override
    public String getId() {
      return ID;
    }
  }

  @Order(5000)
  public static class FridayCode extends AbstractCode<String> {
    private static final long serialVersionUID = 1L;
    public static final String ID = "friday";

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("Friday");
    }

    @Override
    public String getId() {
      return ID;
    }
  }

  @Order(6000)
  public static class SaturdayCode extends AbstractCode<String> {
    private static final long serialVersionUID = 1L;
    public static final String ID = "saturday";

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("Saturday");
    }

    @Override
    public String getId() {
      return ID;
    }
  }

  @Order(7000)
  public static class SundayCode extends AbstractCode<String> {
    private static final long serialVersionUID = 1L;
    public static final String ID = "sunday";

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("Sunday");
    }

    @Override
    public String getId() {
      return ID;
    }
  }
}
