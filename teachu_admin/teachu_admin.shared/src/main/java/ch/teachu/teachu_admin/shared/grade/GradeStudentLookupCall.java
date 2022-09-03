package ch.teachu.teachu_admin.shared.grade;

import org.eclipse.scout.rt.shared.services.lookup.ILookupService;
import org.eclipse.scout.rt.shared.services.lookup.LookupCall;

public class GradeStudentLookupCall extends LookupCall<String> {
  private static final long serialVersionUID = 1L;

  private String examId;
  private String gradeId;

  @Override
  protected Class<? extends ILookupService<String>> getConfiguredService() {
    return IGradeStudentLookupService.class;
  }

  public String getExamId() {
    return examId;
  }

  public void setExamId(String examId) {
    this.examId = examId;
  }

  public String getGradeId() {
    return gradeId;
  }

  public void setGradeId(String gradeId) {
    this.gradeId = gradeId;
  }
}
