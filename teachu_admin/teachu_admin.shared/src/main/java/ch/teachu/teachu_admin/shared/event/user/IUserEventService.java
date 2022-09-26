package ch.teachu.teachu_admin.shared.event.user;

import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

@TunnelToServer
public interface IUserEventService extends IService {
  UserEventTablePageData getUserEventTableData(SearchFilter filter, String userId);

  UserEventFormData prepareCreate(UserEventFormData formData);

  UserEventFormData create(UserEventFormData formData);

  UserEventFormData load(UserEventFormData formData);

  UserEventFormData store(UserEventFormData formData);

  void delete(String userEventId);
}
