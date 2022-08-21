package ch.teachu.teachu_admin.server.lesson;

import ch.teachu.teachu_admin.shared.lesson.ISubjectLookupService;
import org.eclipse.scout.rt.server.jdbc.lookup.AbstractSqlLookupService;

public class SubjectLookupService extends AbstractSqlLookupService<String> implements ISubjectLookupService {
  @Override
  protected String getConfiguredSqlSelect() {
    return "SELECT BIN_TO_UUID(id), name " +
      "FROM subject " +
      "WHERE 1 = 1 " +
      "<key> AND id = UUID_TO_BIN(:key)</key> " +
      "<text> AND UPPER(name) LIKE UPPER(:text||'%') </text> " +
      "<all></all> ";
  }
}
