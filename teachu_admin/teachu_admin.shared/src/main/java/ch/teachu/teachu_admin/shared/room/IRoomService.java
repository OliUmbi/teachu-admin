package ch.teachu.teachu_admin.shared.room;

import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

@TunnelToServer
public interface IRoomService extends IService {
  RoomTablePageData getRoomTableData(SearchFilter filter);

  RoomFormData prepareCreate(RoomFormData formData);

  RoomFormData create(RoomFormData formData);

  RoomFormData load(RoomFormData formData);

  RoomFormData store(RoomFormData formData);

  void delete(String id);
}
