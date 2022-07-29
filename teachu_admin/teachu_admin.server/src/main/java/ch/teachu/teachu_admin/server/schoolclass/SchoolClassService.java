package ch.teachu.teachu_admin.server.schoolclass;

import ch.teachu.teachu_admin.shared.schoolclass.ISchoolClassService;
import ch.teachu.teachu_admin.shared.schoolclass.SchoolClassTablePageData;
import org.eclipse.scout.rt.server.jdbc.SQL;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

public class SchoolClassService implements ISchoolClassService {
  @Override
  public SchoolClassTablePageData getSchoolClassTableData(SearchFilter filter) {
    SchoolClassTablePageData pageData = new SchoolClassTablePageData();
    SQL.selectInto("SELECT BIN_TO_UUID(school_class.id), name, first_name, last_name " +
      "FROM school_class " +
      "JOIN user ON (teacher_id = user.id) INTO :id, :name, :classTeacherFirstName, :classTeacherLastName", pageData);
    return pageData;
  }
}
