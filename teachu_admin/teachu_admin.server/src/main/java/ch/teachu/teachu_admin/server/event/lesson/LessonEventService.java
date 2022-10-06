package ch.teachu.teachu_admin.server.event.lesson;

import ch.teachu.teachu_admin.shared.AccessHelper;
import ch.teachu.teachu_admin.shared.event.lesson.ILessonEventService;
import ch.teachu.teachu_admin.shared.event.lesson.LessonEventFormData;
import ch.teachu.teachu_admin.shared.event.lesson.LessonEventTablePageData;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.server.jdbc.SQL;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

import java.util.UUID;

public class LessonEventService implements ILessonEventService {
  @Override
  public LessonEventTablePageData getLessonEventTableData(SearchFilter filter, String lessonId) {
    BEANS.get(AccessHelper.class).ensureTeacher();
    LessonEventTablePageData pageData = new LessonEventTablePageData();
    SQL.selectInto("SELECT BIN_TO_UUID(id), date, title, lesson_event_type " +
      "FROM lesson_event " +
      "WHERE lesson_id = UUID_TO_BIN(:lessonId)" +
      "INTO :id, :date, :title, :lessonEventType", pageData, new NVPair("lessonId", lessonId));
    return pageData;
  }

  @Override
  public LessonEventFormData prepareCreate(LessonEventFormData formData) {
    BEANS.get(AccessHelper.class).ensureTeacher();
    return formData;
  }

  @Override
  public LessonEventFormData create(LessonEventFormData formData) {
    BEANS.get(AccessHelper.class).ensureTeacher();
    formData.setId(UUID.randomUUID().toString());
    SQL.insert("INSERT INTO lesson_event(id, date, title, description, lesson_event_type, lesson_id) " +
      "VALUES(UUID_TO_BIN(:id), :date, :title, description, :type, UUID_TO_BIN(:lessonId))", formData);
    return formData;
  }

  @Override
  public LessonEventFormData load(LessonEventFormData formData) {
    BEANS.get(AccessHelper.class).ensureTeacher();
    SQL.selectInto("SELECT date, title, description, lesson_event_type " +
      "FROM lesson_event " +
      "WHERE id = UUID_TO_BIN(:id) " +
      "INTO :date, :title, :description, :type", formData);
    return formData;
  }

  @Override
  public LessonEventFormData store(LessonEventFormData formData) {
    BEANS.get(AccessHelper.class).ensureTeacher();
    SQL.update("UPDATE lesson_event " +
        "SET date = :date, " +
        "title = :title, " +
        "description = :description, " +
        "lesson_event_type = :type " +
        "WHERE id = UUID_TO_BIN(:id)",
      formData);
    return formData;
  }

  @Override
  public void delete(String id) {
    BEANS.get(AccessHelper.class).ensureTeacher();
    SQL.delete("DELETE FROM lesson_event WHERE id=UUID_TO_BIN(:id)", new NVPair("id", id));
  }
}
