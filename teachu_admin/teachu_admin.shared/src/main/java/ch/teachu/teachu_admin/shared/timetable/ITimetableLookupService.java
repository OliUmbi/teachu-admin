package ch.teachu.teachu_admin.shared.timetable;

import org.eclipse.scout.rt.shared.TunnelToServer;
import org.eclipse.scout.rt.shared.services.lookup.ILookupService;

@TunnelToServer
public interface ITimetableLookupService extends ILookupService<String> {

}
