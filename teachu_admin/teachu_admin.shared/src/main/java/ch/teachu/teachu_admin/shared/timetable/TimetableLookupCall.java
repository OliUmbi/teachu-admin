package ch.teachu.teachu_admin.shared.timetable;

import org.eclipse.scout.rt.shared.services.lookup.ILookupService;
import org.eclipse.scout.rt.shared.services.lookup.LookupCall;

public class TimetableLookupCall extends LookupCall<String> {
  private static final long serialVersionUID = 1L;

  @Override
  protected Class<? extends ILookupService<String>> getConfiguredService() {
    return ITimetableLookupService.class;
  }
}
