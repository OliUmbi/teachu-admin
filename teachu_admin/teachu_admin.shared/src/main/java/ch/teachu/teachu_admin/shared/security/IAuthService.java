package ch.teachu.teachu_admin.shared.security;

import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;

@TunnelToServer
public interface IAuthService extends IService {
  int login(String email, char[] password);
}
