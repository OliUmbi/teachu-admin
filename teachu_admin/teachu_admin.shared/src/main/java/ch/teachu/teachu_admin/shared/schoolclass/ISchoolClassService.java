package ch.teachu.teachu_admin.shared.schoolclass;

import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

@TunnelToServer
public interface ISchoolClassService extends IService {
  SchoolClassTablePageData getSchoolClassTableData(SearchFilter filter);
}
