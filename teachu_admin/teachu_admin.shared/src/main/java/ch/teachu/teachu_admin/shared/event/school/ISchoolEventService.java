package ch.teachu.teachu_admin.shared.event.school;

import ch.teachu.teachu_admin.shared.event.SchoolEventTablePageData;
import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

@TunnelToServer
public interface ISchoolEventService extends IService {
  SchoolEventTablePageData getEventTableData(SearchFilter filter);

  SchoolEventFormData prepareCreate(SchoolEventFormData formData);

  SchoolEventFormData create(SchoolEventFormData formData);

  SchoolEventFormData load(SchoolEventFormData formData);

  SchoolEventFormData store(SchoolEventFormData formData);

  void delete(String id);
}
