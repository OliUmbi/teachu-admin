package ch.teachu.teachu_admin.shared.user;

import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.shared.services.common.code.AbstractCode;
import org.eclipse.scout.rt.shared.services.common.code.AbstractCodeType;

public class SexCodeType extends AbstractCodeType<String, String> {
  private static final long serialVersionUID = 1L;
  public static final String ID = "sex";

  @Override
  public String getId() {
    return ID;
  }

  @Order(1000)
  public static class MaleCode extends AbstractCode<String> {
    private static final long serialVersionUID = 1L;
    public static final String ID = "male";

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("Male");
    }

    @Override
    public String getId() {
      return ID;
    }
  }

  @Order(2000)
  public static class FemaleCode extends AbstractCode<String> {
    private static final long serialVersionUID = 1L;
    public static final String ID = "female";

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("Female");
    }

    @Override
    public String getId() {
      return ID;
    }
  }

  @Order(3000)
  public static class OtherCode extends AbstractCode<String> {
    private static final long serialVersionUID = 1L;
    public static final String ID = "other";

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("Other");
    }

    @Override
    public String getId() {
      return ID;
    }
  }
}
