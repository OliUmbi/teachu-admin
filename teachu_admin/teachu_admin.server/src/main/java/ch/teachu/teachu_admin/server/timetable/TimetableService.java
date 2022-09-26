package ch.teachu.teachu_admin.server.timetable;

import ch.teachu.teachu_admin.shared.AccessHelper;
import ch.teachu.teachu_admin.shared.timetable.ITimetableService;
import ch.teachu.teachu_admin.shared.timetable.TimetableFormData;
import ch.teachu.teachu_admin.shared.timetable.TimetableTablePageData;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.server.jdbc.SQL;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

import java.util.UUID;

public class TimetableService implements ITimetableService {
  @Override
  public TimetableTablePageData getTimetableTableData(SearchFilter filter) {
    BEANS.get(AccessHelper.class).ensureAdmin();
    TimetableTablePageData pageData = new TimetableTablePageData();
    SQL.selectInto("SELECT BIN_TO_UUID(id), start_time, end_time " +
      "FROM timetable " +
      "INTO :id, :from, :to", pageData);
    return pageData;
  }

  @Override
  public TimetableFormData prepareCreate(TimetableFormData formData) {
    BEANS.get(AccessHelper.class).ensureAdmin();
    return formData;
  }

  @Override
  public TimetableFormData create(TimetableFormData formData) {
    BEANS.get(AccessHelper.class).ensureAdmin();
    formData.setId(UUID.randomUUID().toString());
    SQL.insert("INSERT INTO timetable(id, start_time, end_time) " +
      "VALUES(UUID_TO_BIN(:id), :from, :to)", formData);
    return formData;
  }

  @Override
  public TimetableFormData load(TimetableFormData formData) {
    BEANS.get(AccessHelper.class).ensureAdmin();
    SQL.selectInto("SELECT start_time, end_time " +
      "FROM timetable " +
      "WHERE id = UUID_TO_BIN(:id) " +
      "INTO :from, :to", formData);
    return formData;
  }

  @Override
  public TimetableFormData store(TimetableFormData formData) {
    BEANS.get(AccessHelper.class).ensureAdmin();
    SQL.update("UPDATE timetable " +
        "SET start_time = :from, " +
        "end_time = :to " +
        "WHERE id = UUID_TO_BIN(:id)",
      formData);
    return formData;
  }

  @Override
  public void delete(String id) {
    BEANS.get(AccessHelper.class).ensureAdmin();
    SQL.delete("DELETE FROM timetable WHERE id=UUID_TO_BIN(:id)", new NVPair("id", id));
  }
}
