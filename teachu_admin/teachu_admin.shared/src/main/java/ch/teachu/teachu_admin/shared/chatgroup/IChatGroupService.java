package ch.teachu.teachu_admin.shared.chatgroup;

import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

@TunnelToServer
public interface IChatGroupService extends IService {
  ChatGroupTablePageData getChatGroupTableData(SearchFilter filter);

  ChatGroupFormData prepareCreate(ChatGroupFormData formData);

  ChatGroupFormData create(ChatGroupFormData formData);

  ChatGroupFormData load(ChatGroupFormData formData);

  ChatGroupFormData store(ChatGroupFormData formData);

  void delete(String id);
}
