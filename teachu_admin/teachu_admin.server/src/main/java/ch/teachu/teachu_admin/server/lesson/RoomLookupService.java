package ch.teachu.teachu_admin.server.lesson;

import ch.teachu.teachu_admin.shared.lesson.IRoomLookupService;
import org.eclipse.scout.rt.server.jdbc.lookup.AbstractSqlLookupService;

public class RoomLookupService extends AbstractSqlLookupService<String> implements IRoomLookupService {
  @Override
  protected String getConfiguredSqlSelect() {
    return "SELECT BIN_TO_UUID(id), name " +
      "FROM room " +
      "WHERE 1 = 1 " +
      "<key> AND id = UUID_TO_BIN(:key)</key> " +
      "<text> AND UPPER(name) LIKE UPPER(:text||'%') </text> " +
      "<all></all> ";
  }
}
