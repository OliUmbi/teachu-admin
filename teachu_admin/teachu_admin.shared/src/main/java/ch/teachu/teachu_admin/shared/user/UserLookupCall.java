package ch.teachu.teachu_admin.shared.user;

import org.eclipse.scout.rt.shared.services.lookup.ILookupService;
import org.eclipse.scout.rt.shared.services.lookup.LookupCall;

public class UserLookupCall extends LookupCall<String> {
  private static final long serialVersionUID = 1L;

  private String role;

  public void setRole(String role) {
    this.role = role;
  }

  public String getRole() {
    return role;
  }

  @Override
  protected Class<? extends ILookupService<String>> getConfiguredService() {
    return IUserLookupService.class;
  }
}
