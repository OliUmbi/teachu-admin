package ch.teachu.teachu_admin.shared.schoolinfo;

import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

@TunnelToServer
public interface ISchoolInfoService extends IService {
  SchoolInfoTablePageData getSchoolInfoTableData(SearchFilter filter);

  SchoolInfoFormData prepareCreate(SchoolInfoFormData formData);

  SchoolInfoFormData create(SchoolInfoFormData formData);

  SchoolInfoFormData load(SchoolInfoFormData formData);

  SchoolInfoFormData store(SchoolInfoFormData formData);

  void delete(String id);
}
