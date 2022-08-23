package ch.teachu.teachu_admin.server.event.schoolclass;

import ch.teachu.teachu_admin.shared.AccessHelper;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.exception.VetoException;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.security.ACCESS;
import org.eclipse.scout.rt.server.jdbc.SQL;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

import ch.teachu.teachu_admin.shared.event.schoolclass.ISchoolClassEventService;
import ch.teachu.teachu_admin.shared.event.schoolclass.SchoolClassEventFormData;
import ch.teachu.teachu_admin.shared.event.schoolclass.SchoolClassEventTablePageData;

import java.util.UUID;

public class SchoolClassEventService implements ISchoolClassEventService {
  @Override
  public SchoolClassEventTablePageData getSchoolClassEventTableData(SearchFilter filter, String schoolClassId) {
    BEANS.get(AccessHelper.class).ensureTeacher();
    SchoolClassEventTablePageData pageData = new SchoolClassEventTablePageData();
    SQL.selectInto("SELECT BIN_TO_UUID(id), date_from, date_to, title, school_class_event_type " +
      "FROM school_class_event " +
      "WHERE school_class_id = UUID_TO_BIN(:schoolClassId)" +
      "INTO :id, :dateFrom, :dateTo, :title, :type", pageData, new NVPair("schoolClassId", schoolClassId));
    return pageData;
  }

  @Override
  public SchoolClassEventFormData prepareCreate(SchoolClassEventFormData formData) {
    BEANS.get(AccessHelper.class).ensureTeacher();
    return formData;
  }

  @Override
  public SchoolClassEventFormData create(SchoolClassEventFormData formData) {
    BEANS.get(AccessHelper.class).ensureTeacher();
    formData.setId(UUID.randomUUID().toString());
    SQL.insert("INSERT INTO school_class_event(id, date_from, date_to, title, description, school_class_event_type, school_class_id) " +
      "VALUES(UUID_TO_BIN(:id), :from, :to, :title, description, :type, UUID_TO_BIN(:schoolClassId))", formData);
    return formData;
  }

  @Override
  public SchoolClassEventFormData load(SchoolClassEventFormData formData) {
    BEANS.get(AccessHelper.class).ensureTeacher();
    SQL.selectInto("SELECT date_from, date_to, title, description, school_class_event_type " +
      "FROM school_class_event " +
      "WHERE id = UUID_TO_BIN(:id) " +
      "AND school_class_id = UUID_TO_BIN(:schoolClassId) " +
      "INTO :from, :to, :title, :description, :type", formData);
    return formData;
  }

  @Override
  public SchoolClassEventFormData store(SchoolClassEventFormData formData) {
    BEANS.get(AccessHelper.class).ensureTeacher();
    SQL.update("UPDATE school_class_event " +
        "SET date_from = :from, " +
        "date_to = :to, " +
        "title = :title, " +
        "description = :description, " +
        "school_class_event_type = :type " +
        "WHERE id = UUID_TO_BIN(:id)",
      formData);
    return formData;
  }

  @Override
  public void delete(String id) {
    BEANS.get(AccessHelper.class).ensureTeacher();
    SQL.delete("DELETE FROM school_class_event WHERE id=UUID_TO_BIN(:id)", new NVPair("id", id));
  }
}
