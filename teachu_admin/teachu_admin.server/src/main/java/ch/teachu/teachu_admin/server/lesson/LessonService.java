package ch.teachu.teachu_admin.server.lesson;

import ch.teachu.teachu_admin.shared.AccessHelper;
import ch.teachu.teachu_admin.shared.lesson.ILessonService;
import ch.teachu.teachu_admin.shared.lesson.LessonFormData;
import ch.teachu.teachu_admin.shared.lesson.LessonTablePageData;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.server.jdbc.SQL;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

import java.util.Date;
import java.util.UUID;

public class LessonService implements ILessonService {
  @Override
  public LessonTablePageData getLessonTableData(SearchFilter filter, String schoolClassId) {
    LessonTablePageData pageData = new LessonTablePageData();
    StringBuilder sql = new StringBuilder("SELECT BIN_TO_UUID(lesson.id), BIN_TO_UUID(school_class_subject_id), start_time, end_time, weekday, BIN_TO_UUID(room_id), BIN_TO_UUID(school_class_id) " +
      "FROM lesson " +
      "LEFT JOIN timetable ON (timetable_id = timetable.id) " +
      "LEFT JOIN school_class_subject ON (school_class_subject_id = school_class_subject.id) ");

    if (schoolClassId != null) {
      sql.append("WHERE school_class_id = UUID_TO_BIN(:inSchoolClassId) ");
    }
    sql.append("INTO :id, :subject, :startTime, :endTime, :weekday, :room, :schoolClass");
    SQL.selectInto(sql.toString(), new NVPair("inSchoolClassId", schoolClassId), pageData);
    return pageData;
  }

  @Override
  public LessonFormData prepareCreate(LessonFormData formData) {
    BEANS.get(AccessHelper.class).ensureAdmin();
    return formData;
  }

  @Override
  public LessonFormData create(LessonFormData formData) {
    BEANS.get(AccessHelper.class).ensureAdmin();

    String timetableId = UUID.randomUUID().toString();
    SQL.insert("INSERT INTO timetable(id, start_time, end_time) " +
      "VALUES (UUID_TO_BIN(:timetableId), :from, :to)", formData, new NVPair("timetableId", timetableId));

    formData.setId(UUID.randomUUID().toString());
    SQL.insert("INSERT INTO lesson(id, school_class_subject_id, timetable_id, weekday, room_id) " +
        "VALUES(UUID_TO_BIN(:id), UUID_TO_BIN(:subject), UUID_TO_BIN(:timetableId), :weekday, UUID_TO_BIN(:room))", formData,
      new NVPair("userId", BEANS.get(AccessHelper.class).getCurrentUserId()),
      new NVPair("timetableId", timetableId));

    return formData;
  }

  @Override
  public LessonFormData load(LessonFormData formData) {
    SQL.selectInto("SELECT start_time, end_time, BIN_TO_UUID(school_class_subject_id), weekday, BIN_TO_UUID(room_id) " +
      "FROM lesson " +
      "JOIN timetable ON (timetable.id = timetable_id) " +
      "WHERE lesson.id = UUID_TO_BIN(:id) " +
      "INTO :from, :to, :subject, :weekday, :room", formData);
    return formData;
  }

  @Override
  public LessonFormData store(LessonFormData formData) {
    BEANS.get(AccessHelper.class).ensureAdmin();
    SQL.update("UPDATE timetable " +
        "SET start_time = :from, " +
        "end_time = :to " +
        "WHERE (SELECT timetable_id FROM lesson WHERE lesson.id = UUID_TO_BIN(:id)) = timetable.id",
      formData);

    SQL.update("UPDATE lesson " +
        "SET school_class_subject_id = UUID_TO_BIN(:subject), " +
        "weekday = :weekday, " +
        "room_id = UUID_TO_BIN(:room) " +
        "WHERE id = UUID_TO_BIN(:id)",
      formData);
    return formData;
  }

  @Override
  public void delete(String id) {
    BEANS.get(AccessHelper.class).ensureAdmin();
    SQL.delete("DELETE FROM timetable " +
        "WHERE (SELECT timetable_id FROM lesson WHERE lesson.id = UUID_TO_BIN(:id)) = timetable.id",
      new NVPair("id", id));
    SQL.delete("DELETE FROM lesson WHERE id=UUID_TO_BIN(:id)", new NVPair("id", id));
  }

  @Override
  public boolean overlapsWithOtherLessons(String id, Date from, Date to, String weekday, String schoolClassId) {
    StringBuilder sql = new StringBuilder("SELECT count(*) " +
      "FROM lesson " +
      "JOIN timetable ON (lesson.timetable_id = timetable.id) " +
      "JOIN school_class_subject ON (school_class_subject_id = school_class_subject.id) " +
      "WHERE end_time > DATE_ADD(:from, INTERVAL 1 SECOND) " +
      "AND start_time < DATE_SUB(:to, INTERVAL 1 SECOND) " +
      "AND weekday = :weekday " +
      "AND school_class_id = UUID_TO_BIN(:schoolClassId)");
    if (id != null) {
      sql.append(" AND NOT lesson.id = UUID_TO_BIN(:id)");
    }

    long overlaps = (long) SQL.select(sql.toString(),
      new NVPair("from", from), new NVPair("to", to),
      new NVPair("id", id),
      new NVPair("weekday", weekday),
      new NVPair("schoolClassId", schoolClassId))[0][0];
    return overlaps > 0;
  }
}
