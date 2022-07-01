package ch.teachu.teachu_admin.shared.schoolconfig;

import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.shared.services.common.code.AbstractCode;
import org.eclipse.scout.rt.shared.services.common.code.AbstractCodeType;

public class LanguageCodeType extends AbstractCodeType<String, String> {
  private static final long serialVersionUID = 1L;
  public static final String ID = "language";

  @Override
  public String getId() {
    return ID;
  }

  @Order(1000)
  public static class GermanCode extends AbstractCode<String> {
    private static final long serialVersionUID = 1L;
    public static final String ID = "german";

    @Override
    protected String getConfiguredText() {
      return "Deutsch";
    }

    @Override
    public String getId() {
      return ID;
    }
  }

  @Order(2000)
  public static class FrenchCode extends AbstractCode<String> {
    private static final long serialVersionUID = 1L;
    public static final String ID = "french";

    @Override
    protected String getConfiguredText() {
      return "Français";
    }

    @Override
    public String getId() {
      return ID;
    }
  }

  @Order(3000)
  public static class EnglishCode extends AbstractCode<String> {
    private static final long serialVersionUID = 1L;
    public static final String ID = "english";

    @Override
    protected String getConfiguredText() {
      return "English";
    }

    @Override
    public String getId() {
      return ID;
    }
  }
}
