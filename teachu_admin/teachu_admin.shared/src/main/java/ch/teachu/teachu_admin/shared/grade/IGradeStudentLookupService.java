package ch.teachu.teachu_admin.shared.grade;

import org.eclipse.scout.rt.shared.TunnelToServer;
import org.eclipse.scout.rt.shared.services.lookup.ILookupService;

@TunnelToServer
public interface IGradeStudentLookupService extends ILookupService<String> {

}
