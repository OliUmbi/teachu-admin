package ch.teachu.teachu_admin.shared.lesson;

import org.eclipse.scout.rt.shared.services.lookup.ILookupService;
import org.eclipse.scout.rt.shared.services.lookup.LookupCall;

public class RoomLookupCall extends LookupCall<String> {
  private static final long serialVersionUID = 1L;

  @Override
  protected Class<? extends ILookupService<String>> getConfiguredService() {
    return IRoomLookupService.class;
  }
}
