package ch.teachu.teachu_admin.server.timetable;

import ch.teachu.teachu_admin.shared.timetable.ITimetableLookupService;
import org.eclipse.scout.rt.server.jdbc.lookup.AbstractSqlLookupService;

public class TimetableLookupService extends AbstractSqlLookupService<String> implements ITimetableLookupService {
  @Override
  protected String getConfiguredSqlSelect() {
    return "SELECT BIN_TO_UUID(id), CONCAT(TIME_FORMAT(start_time, '%H:%i'), ' - ', TIME_FORMAT(end_time, '%H:%i')) " +
      "FROM timetable " +
      "WHERE 1 = 1 " +
      "<key> AND id = UUID_TO_BIN(:key)</key> " +
      "<text> AND UPPER(CONCAT(from, ' - ', to)) LIKE UPPER(:text||'%') </text> " +
      "<all></all> ";
  }
}
