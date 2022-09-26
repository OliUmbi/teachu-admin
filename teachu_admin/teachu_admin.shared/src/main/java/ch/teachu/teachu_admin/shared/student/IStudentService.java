package ch.teachu.teachu_admin.shared.student;

import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

@TunnelToServer
public interface IStudentService extends IService {
  StudentTablePageData getStudentTableData(SearchFilter filter);
}
