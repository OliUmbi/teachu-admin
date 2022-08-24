package ch.teachu.teachu_admin.client.schoolclass.subject;

import ch.teachu.teachu_admin.client.exam.ExamTablePage;
import org.eclipse.scout.rt.client.ui.basic.table.ITableRow;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.IPage;

public class TeacherSchoolClassSubjectTablePage extends SchoolClassSubjectTablePage {
  public TeacherSchoolClassSubjectTablePage(String schoolClassId) {
    super(schoolClassId);
  }

  @Override
  protected boolean getConfiguredLeaf() {
    return false;
  }

  @Override
  protected IPage<?> execCreateChildPage(ITableRow row) {
    return new ExamTablePage(getTable().getIdColumn().getValue(row));
  }
}
