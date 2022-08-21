package ch.teachu.teachu_admin.server.schoolclass.subject;

import ch.teachu.teachu_admin.shared.subject.ISubjectCheckDeletable;
import org.eclipse.scout.rt.platform.text.TEXTS;

public class CheckSubjectSchoolClassSubjectDeletable implements ISubjectCheckDeletable {
  @Override
  public String getCategoryName() {
    return TEXTS.get("SchoolClassSubject");
  }

  @Override
  public String getSqlSelectReferences() {
    return "SELECT CONCAT(school_class.name, ' ', subject.name) " +
      "FROM school_class_subject " +
      "JOIN school_class ON (school_class_id = school_class.id) " +
      "JOIN subject ON (subject.id = subject_id)" +
      "WHERE BIN_TO_UUID(subject_id) IN :ids";
  }
}
