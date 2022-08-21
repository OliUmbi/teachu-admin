package ch.teachu.teachu_admin.shared;

import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;

@TunnelToServer
public interface IAccessService extends IService {
  String getCurrentUserId();
}
