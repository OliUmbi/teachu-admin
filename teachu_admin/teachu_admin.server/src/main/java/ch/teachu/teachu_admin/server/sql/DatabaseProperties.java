package ch.teachu.teachu_admin.server.sql;

import org.eclipse.scout.rt.platform.config.AbstractStringConfigProperty;

public class DatabaseProperties {

  public static class DatabaseUrlProperty extends AbstractStringConfigProperty {

    @Override
    public String getDefaultValue() {
      return "jdbc:mysql://localhost:3306/teachu";
    }

    @Override
    public String getKey() {
      return "teachu.database.url";
    }

    @Override
    public String description() {
      return "";
    }
  }

  public static class DatabaseUsernameProperty extends AbstractStringConfigProperty {

    @Override
    public String getDefaultValue() {
      return "admin";
    }

    @Override
    public String getKey() {
      return "teachu.database.username";
    }

    @Override
    public String description() {
      return "";
    }
  }

  public static class DatabasePasswordProperty extends AbstractStringConfigProperty {

    @Override
    public String getDefaultValue() {
      return "admin";
    }

    @Override
    public String getKey() {
      return "teachu.database.password";
    }

    @Override
    public String description() {
      return "";
    }
  }

}
