package ch.teachu.teachu_admin.shared.lesson;

import org.eclipse.scout.rt.shared.TunnelToServer;
import org.eclipse.scout.rt.shared.services.lookup.ILookupService;

@TunnelToServer
public interface IRoomLookupService extends ILookupService<String> {

}
