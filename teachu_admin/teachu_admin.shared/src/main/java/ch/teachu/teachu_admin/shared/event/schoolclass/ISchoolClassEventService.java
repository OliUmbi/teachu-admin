package ch.teachu.teachu_admin.shared.event.schoolclass;

import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

@TunnelToServer
public interface ISchoolClassEventService extends IService {
  SchoolClassEventTablePageData getSchoolClassEventTableData(SearchFilter filter, String schoolClassId);

  SchoolClassEventFormData prepareCreate(SchoolClassEventFormData formData);

  SchoolClassEventFormData create(SchoolClassEventFormData formData);

  SchoolClassEventFormData load(SchoolClassEventFormData formData);

  SchoolClassEventFormData store(SchoolClassEventFormData formData);

  void delete(String id);
}
