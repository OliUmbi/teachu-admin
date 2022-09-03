package ch.teachu.teachu_admin.server.schoolclass;

import ch.teachu.teachu_admin.shared.schoolclass.ISchoolClassLookupService;
import org.eclipse.scout.rt.server.jdbc.lookup.AbstractSqlLookupService;

public class SchoolClassLookupService extends AbstractSqlLookupService<String> implements ISchoolClassLookupService {
  @Override
  protected String getConfiguredSqlSelect() {
    return "SELECT BIN_TO_UUID(school_class.id), school_class.name " +
      "FROM school_class " +
      "LEFT JOIN user ON (teacher_id = user.id) " +
      "WHERE (:teacherId IS NULL OR (teacher_id = UUID_TO_BIN(:teacherId) " +
      "OR UUID_TO_BIN(:teacherId) IN " +
      "(SELECT school_class_subject.teacher_id FROM school_class_subject WHERE school_class_id = school_class.id))) " +
      "<key> AND school_class.id = UUID_TO_BIN(:key)</key> " +
      "<text> AND UPPER(school_class) LIKE UPPER(:text||'%') </text> " +
      "<all></all>";
  }
}
