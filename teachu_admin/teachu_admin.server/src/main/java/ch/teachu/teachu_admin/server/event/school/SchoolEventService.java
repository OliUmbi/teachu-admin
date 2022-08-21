package ch.teachu.teachu_admin.server.event.school;

import ch.teachu.teachu_admin.shared.AccessHelper;
import ch.teachu.teachu_admin.shared.event.SchoolEventTablePageData;
import ch.teachu.teachu_admin.shared.event.school.ISchoolEventService;
import ch.teachu.teachu_admin.shared.event.school.SchoolEventFormData;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.server.jdbc.SQL;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

import java.util.UUID;

public class SchoolEventService implements ISchoolEventService {
  @Override
  public SchoolEventTablePageData getEventTableData(SearchFilter filter) {
    BEANS.get(AccessHelper.class).ensureAdmin();
    SchoolEventTablePageData pageData = new SchoolEventTablePageData();
    SQL.selectInto("SELECT BIN_TO_UUID(id), date_from, date_to, title, school_event_type " +
      "FROM school_event " +
      "INTO :id, :from, :to, :title, :type", pageData);
    return pageData;
  }

  @Override
  public SchoolEventFormData prepareCreate(SchoolEventFormData formData) {
    BEANS.get(AccessHelper.class).ensureAdmin();
    return formData;
  }

  @Override
  public SchoolEventFormData create(SchoolEventFormData formData) {
    BEANS.get(AccessHelper.class).ensureAdmin();
    formData.setId(UUID.randomUUID().toString());
    SQL.insert("INSERT INTO school_event(id, date_from, date_to, title, description, school_event_type) " +
      "VALUES(UUID_TO_BIN(:id), :from, :to, :title, description, :schoolEventType)", formData);
    return formData;
  }

  @Override
  public SchoolEventFormData load(SchoolEventFormData formData) {
    BEANS.get(AccessHelper.class).ensureAdmin();
    SQL.selectInto("SELECT date_from, date_to, title, description, school_event_type " +
      "FROM school_event " +
      "WHERE id = UUID_TO_BIN(:id) " +
      "INTO :from, :to, :title, :description, :schoolEventType", formData);
    return formData;
  }

  @Override
  public SchoolEventFormData store(SchoolEventFormData formData) {
    BEANS.get(AccessHelper.class).ensureAdmin();
    SQL.update("UPDATE school_event " +
        "SET date_from = :from, " +
        "date_to = :to, " +
        "title = :title, " +
        "description = :description, " +
        "school_event_type = :schoolEventType " +
        "WHERE id = UUID_TO_BIN(:id)",
      formData);
    return formData;
  }

  @Override
  public void delete(String id) {
    BEANS.get(AccessHelper.class).ensureAdmin();
    SQL.delete("DELETE FROM school_event WHERE id=UUID_TO_BIN(:id)", new NVPair("id", id));
  }
}
