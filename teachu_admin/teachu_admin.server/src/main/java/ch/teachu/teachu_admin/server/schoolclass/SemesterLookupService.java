package ch.teachu.teachu_admin.server.schoolclass;

import ch.teachu.teachu_admin.shared.schoolclass.ISemesterLookupService;
import org.eclipse.scout.rt.server.jdbc.lookup.AbstractSqlLookupService;

public class SemesterLookupService extends AbstractSqlLookupService<String> implements ISemesterLookupService {
  @Override
  protected String getConfiguredSqlSelect() {
    return "SELECT BIN_TO_UUID(id), name " +
      "FROM semester " +
      "WHERE 1 = 1 " +
      "<key> AND id = UUID_TO_BIN(:key)</key> " +
      "<text> AND UPPER(name) LIKE UPPER(:text||'%') </text> " +
      "<all></all> ";
  }
}
