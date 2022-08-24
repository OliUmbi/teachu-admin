package ch.teachu.teachu_admin.server.schoolclass.subject;

import ch.teachu.teachu_admin.shared.schoolclass.subject.ISchoolClassSubjectLookupService;
import org.eclipse.scout.rt.server.jdbc.lookup.AbstractSqlLookupService;

public class SchoolClassSubjectLookupService extends AbstractSqlLookupService<String> implements ISchoolClassSubjectLookupService {
  @Override
  protected String getConfiguredSqlSelect() {
    return "SELECT BIN_TO_UUID(school_class_subject.id), name " +
      "FROM school_class_subject " +
      "JOIN subject ON (subject.id = subject_id) " +
      "WHERE 1 = 1 " +
      "<key> AND school_class_subject.id = UUID_TO_BIN(:key)</key> " +
      "<text> AND UPPER(name) LIKE UPPER(:text||'%') </text> " +
      "<all></all> " +
      "AND school_class_id = UUID_TO_BIN(:schoolClassId) " +
      "AND (:teacherId IS NULL OR teacher_id = UUID_TO_BIN(:teacherId))";
  }
}
