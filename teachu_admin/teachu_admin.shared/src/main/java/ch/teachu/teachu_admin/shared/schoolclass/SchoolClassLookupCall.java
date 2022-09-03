package ch.teachu.teachu_admin.shared.schoolclass;

import org.eclipse.scout.rt.shared.services.lookup.ILookupService;
import org.eclipse.scout.rt.shared.services.lookup.LookupCall;

public class SchoolClassLookupCall extends LookupCall<String> {
  private static final long serialVersionUID = 1L;

  private String teacherId;

  @Override
  protected Class<? extends ILookupService<String>> getConfiguredService() {
    return ISchoolClassLookupService.class;
  }

  public String getTeacherId() {
    return teacherId;
  }

  public void setTeacherId(String teacherId) {
    this.teacherId = teacherId;
  }
}
