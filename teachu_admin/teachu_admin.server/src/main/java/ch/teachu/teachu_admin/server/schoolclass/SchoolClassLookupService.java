package ch.teachu.teachu_admin.server.schoolclass;

import ch.teachu.teachu_admin.shared.schoolclass.ISchoolClassLookupService;
import org.eclipse.scout.rt.server.jdbc.lookup.AbstractSqlLookupService;

public class SchoolClassLookupService extends AbstractSqlLookupService<String> implements ISchoolClassLookupService {
  @Override
  protected String getConfiguredSqlSelect() {
    return "SELECT BIN_TO_UUID(id), name " +
      "FROM school_class " +
      "WHERE 1 = 1 " +
      "<key> AND id = UUID_TO_BIN(:key)</key> " +
      "<text> AND UPPER(school_class) LIKE UPPER(:text||'%') </text> " +
      "<all></all>";
  }
}
