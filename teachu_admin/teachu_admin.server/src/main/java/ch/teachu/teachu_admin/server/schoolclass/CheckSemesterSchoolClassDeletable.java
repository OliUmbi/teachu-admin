package ch.teachu.teachu_admin.server.schoolclass;

import ch.teachu.teachu_admin.shared.semester.ISemesterCheckDeletable;
import org.eclipse.scout.rt.platform.text.TEXTS;

public class CheckSemesterSchoolClassDeletable implements ISemesterCheckDeletable {
  @Override
  public String getCategoryName() {
    return TEXTS.get("SchoolClass");
  }

  @Override
  public String getSqlSelectReferences() {
    return "SELECT name " +
      "FROM school_class " +
      "JOIN school_class_semester ON (school_class.id = school_class_id) " +
      "WHERE BIN_TO_UUID(semester_id) IN :ids";
  }
}
