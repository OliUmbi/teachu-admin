package ch.teachu.teachu_admin.server.sql;

import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.config.CONFIG;
import org.eclipse.scout.rt.server.jdbc.mysql.AbstractMySqlSqlService;

@Order(1950)
public class MySqlService extends AbstractMySqlSqlService {

  @Override
  protected String getConfiguredJdbcMappingName() {
    return CONFIG.getPropertyValue(DatabaseProperties.DatabaseUrlProperty.class);
  }

  @Override
  protected String getConfiguredUsername() {
    return CONFIG.getPropertyValue(DatabaseProperties.DatabaseUsernameProperty.class);
  }

  @Override
  protected String getConfiguredPassword() {
    return CONFIG.getPropertyValue(DatabaseProperties.DatabasePasswordProperty.class);
  }
}
