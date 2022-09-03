package ch.teachu.teachu_admin.server.grade;

import ch.teachu.teachu_admin.shared.grade.IGradeStudentLookupService;
import org.eclipse.scout.rt.server.jdbc.lookup.AbstractSqlLookupService;

public class GradeStudentLookupService extends AbstractSqlLookupService<String> implements IGradeStudentLookupService {
  @Override
  protected String getConfiguredSqlSelect() {
    return "SELECT BIN_TO_UUID(user.id), CONCAT(first_name, ' ', last_name) " +
      "FROM user " +
      "    LEFT JOIN grade ON (student_id = user.id AND exam_id = UUID_TO_BIN(:examId)) " +
      "    WHERE (grade.id IS NULL OR grade.id = UUID_TO_BIN(:gradeId)) " +
      "    AND user.id IN (SELECT school_class_user.user_id " +
      "                    FROM school_class_user " +
      "                             JOIN school_class_subject scs on school_class_user.school_class_id = scs.school_class_id " +
      "                             JOIN exam e on scs.id = e.school_class_subject_id " +
      "                    WHERE e.id = UUID_TO_BIN(:examId)) " +
      "<key> AND user.id = UUID_TO_BIN(:key)</key> " +
      "<text> AND UPPER(CONCAT(first_name, ' ', last_name)) LIKE UPPER(:text||'%') </text> " +
      "<all></all> ";
  }
}
