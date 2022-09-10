package ch.teachu.teachu_admin.server.user;

import ch.teachu.teachu_admin.shared.user.IUserLookupService;
import org.eclipse.scout.rt.server.jdbc.lookup.AbstractSqlLookupService;

public class UserLookupService extends AbstractSqlLookupService<String> implements IUserLookupService {
  @Override
  protected String getConfiguredSqlSelect() {
    return "SELECT BIN_TO_UUID(id), CONCAT(first_name, ' ', last_name) " +
      "FROM user " +
      "WHERE 1 = 1 " +
      "<key> AND id = UUID_TO_BIN(:key)</key> " +
      "<text> AND UPPER(CONCAT(first_name, ' ', last_name)) LIKE UPPER(:text||'%') </text> " +
      "<all></all> " +
      "AND (:role IS NULL OR role = :role) ";
  }
}
