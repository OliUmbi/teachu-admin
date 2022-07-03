package ch.teachu.teachu_admin.shared.user;

import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

import java.util.UUID;

@TunnelToServer
public interface IUserService extends IService {
  UserTablePageData getUserTableData(SearchFilter filter);

  UserFormData prepareCreate(UserFormData formData);

  UserFormData create(UserFormData formData);

  UserFormData load(UserFormData formData);

  UserFormData store(UserFormData formData);

  void delete(UUID id);
}
