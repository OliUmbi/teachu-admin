package ch.teachu.teachu_admin.server.schoolinfo;

import ch.teachu.teachu_admin.shared.schoolinfo.ISchoolInfoService;
import ch.teachu.teachu_admin.shared.schoolinfo.SchoolInfoTablePageData;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

public class SchoolInfoService implements ISchoolInfoService {
  @Override
  public SchoolInfoTablePageData getSchoolInfoTableData(SearchFilter filter) {
    SchoolInfoTablePageData pageData = new SchoolInfoTablePageData();
// TODO [rbr] fill pageData.
    return pageData;
  }
}
