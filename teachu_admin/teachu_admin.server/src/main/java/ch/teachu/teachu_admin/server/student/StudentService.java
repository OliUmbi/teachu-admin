package ch.teachu.teachu_admin.server.student;

import ch.teachu.teachu_admin.shared.student.IStudentService;
import ch.teachu.teachu_admin.shared.student.StudentTablePageData;
import org.eclipse.scout.rt.server.jdbc.SQL;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

public class StudentService implements IStudentService {
  @Override
  public StudentTablePageData getStudentTableData(SearchFilter filter) {
    StudentTablePageData pageData = new StudentTablePageData();
    SQL.selectInto("SELECT BIN_TO_UUID(id), first_name, last_name FROM user WHERE role = 'student' INTO :id, :firstName, :lastName", pageData);
    return pageData;
  }
}
