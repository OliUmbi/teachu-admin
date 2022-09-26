package ch.teachu.teachu_admin.server.lesson;

import ch.teachu.teachu_admin.shared.timetable.ICheckTimetableDeletable;
import org.eclipse.scout.rt.platform.text.TEXTS;

public class CheckTimetableLessonDeletable implements ICheckTimetableDeletable {
  @Override
  public String getCategoryName() {
    return TEXTS.get("Lesson");
  }

  @Override
  public String getSqlSelectReferences() {
    return "SELECT CONCAT(school_class.name, ' ', subject.name) " +
      "FROM lesson " +
      "JOIN school_class_subject ON (school_class_subject_id = school_class_subject.id) " +
      "JOIN school_class ON (school_class_id = school_class.id) " +
      "WHERE BIN_TO_UUID(timetable_id) IN :ids";
  }
}
