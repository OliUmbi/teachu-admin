package ch.teachu.teachu_admin.server.lesson;

import ch.teachu.teachu_admin.shared.room.IRoomCheckDeletable;
import org.eclipse.scout.rt.platform.text.TEXTS;

public class CheckRoomLessonDeletable implements IRoomCheckDeletable {
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
      "JOIN subject ON (subject.id = subject_id) " +
      "WHERE BIN_TO_UUID(room_id) IN :ids";
  }
}
