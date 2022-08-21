package ch.teachu.teachu_admin.shared.schoolclass;

import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

@TunnelToServer
public interface ISchoolClassService extends IService {
  SchoolClassTablePageData getSchoolClassTableData(SearchFilter filter, String teacherId);

  SchoolClassFormData prepareCreate(SchoolClassFormData formData);

  SchoolClassFormData create(SchoolClassFormData formData);

  SchoolClassFormData load(SchoolClassFormData formData);

  SchoolClassFormData store(SchoolClassFormData formData);

  void delete(String id);
}
