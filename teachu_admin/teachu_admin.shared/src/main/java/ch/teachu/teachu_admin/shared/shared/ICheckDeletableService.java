package ch.teachu.teachu_admin.shared.shared;

import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;

import java.util.List;

@TunnelToServer
public interface ICheckDeletableService extends IService {
  List<String> checkDeletable(Class<? extends ICheckDeletable> deletable, List<String> ids);
}
