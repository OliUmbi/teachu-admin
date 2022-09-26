package ch.teachu.teachu_admin.server.lesson;

import ch.teachu.teachu_admin.shared.AccessHelper;
import ch.teachu.teachu_admin.shared.lesson.ILessonService;
import ch.teachu.teachu_admin.shared.lesson.LessonFormData;
import ch.teachu.teachu_admin.shared.lesson.LessonTablePageData;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.server.jdbc.SQL;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

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
    } else {
      sql.append("WHERE school_class_subject.teacher_id = UUID_TO_BIN(:userId) ");
    }
    sql.append("INTO :id, :subject, :startTime, :endTime, :weekday, :room, :schoolClass");
    SQL.selectInto(sql.toString(), new NVPair("inSchoolClassId", schoolClassId),
      new NVPair("userId", BEANS.get(AccessHelper.class).getCurrentUserId()), pageData);
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

    formData.setId(UUID.randomUUID().toString());
    SQL.insert("INSERT INTO lesson(id, school_class_subject_id, timetable_id, weekday, room_id) " +
        "VALUES(UUID_TO_BIN(:id), UUID_TO_BIN(:subject), UUID_TO_BIN(:timetable), :weekday, UUID_TO_BIN(:room))", formData,
      new NVPair("userId", BEANS.get(AccessHelper.class).getCurrentUserId()));

    return formData;
  }

  @Override
  public LessonFormData load(LessonFormData formData) {
    SQL.selectInto("SELECT BIN_TO_UUID(timetable_id), BIN_TO_UUID(school_class_subject_id), weekday, BIN_TO_UUID(room_id) " +
      "FROM lesson " +
      "WHERE lesson.id = UUID_TO_BIN(:id) " +
      "INTO :timetable, :subject, :weekday, :room", formData);
    return formData;
  }

  @Override
  public LessonFormData store(LessonFormData formData) {
    BEANS.get(AccessHelper.class).ensureAdmin();
    SQL.update("UPDATE lesson " +
        "SET school_class_subject_id = UUID_TO_BIN(:subject), " +
        "weekday = :weekday, " +
        "room_id = UUID_TO_BIN(:room)," +
        "timetable_id = UUID_TO_BIN(:timetable) " +
        "WHERE id = UUID_TO_BIN(:id)",
      formData);
    return formData;
  }

  @Override
  public void delete(String id) {
    BEANS.get(AccessHelper.class).ensureAdmin();
    SQL.delete("DELETE FROM lesson WHERE id=UUID_TO_BIN(:id)", new NVPair("id", id));
  }
}
