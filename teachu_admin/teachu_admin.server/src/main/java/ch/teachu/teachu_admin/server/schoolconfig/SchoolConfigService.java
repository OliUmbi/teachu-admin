package ch.teachu.teachu_admin.server.schoolconfig;

import ch.teachu.teachu_admin.server.AccessHelper;
import ch.teachu.teachu_admin.shared.schoolconfig.ISchoolConfigService;
import ch.teachu.teachu_admin.shared.schoolconfig.SchoolConfigFormData;
import ch.teachu.teachu_admin.shared.schoolconfig.SchoolConfigTablePageData;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.server.jdbc.SQL;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

public class SchoolConfigService implements ISchoolConfigService {
  @Override
  public SchoolConfigTablePageData getSchoolConfigTableData(SearchFilter filter) {
    BEANS.get(AccessHelper.class).ensureAdmin();
    SchoolConfigTablePageData pageData = new SchoolConfigTablePageData();
    SQL.selectInto("SELECT name, value, code_type FROM school_config INTO :configName, :value, :codeType", pageData);
    return pageData;
  }

  @Override
  public SchoolConfigFormData store(SchoolConfigFormData formData) {
    BEANS.get(AccessHelper.class).ensureAdmin();
    SQL.update("UPDATE school_config SET value = :value WHERE name = :configName", formData);
    return formData;
  }

  @Override
  public String getConfig(String configName) {
    BEANS.get(AccessHelper.class).ensureAdmin();
    return (String) SQL.select("SELECT value " +
      "FROM school_config " +
      "WHERE name = :name", new NVPair("name", configName))[0][0];
  }
}
