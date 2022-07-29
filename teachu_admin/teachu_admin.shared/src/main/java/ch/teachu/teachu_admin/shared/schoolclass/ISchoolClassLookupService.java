package ch.teachu.teachu_admin.shared.schoolclass;

import org.eclipse.scout.rt.shared.TunnelToServer;
import org.eclipse.scout.rt.shared.services.lookup.ILookupService;

@TunnelToServer
public interface ISchoolClassLookupService extends ILookupService<String> {

}
