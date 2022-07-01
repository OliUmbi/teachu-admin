package ch.teachu.teachu_admin.shared.schoolconfig;

import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

@TunnelToServer
public interface ISchoolConfigService extends IService {

  SchoolConfigTablePageData getSchoolConfigTableData(SearchFilter filter);

  SchoolConfigFormData store(SchoolConfigFormData formData);

  String getConfig(String configName);
}
