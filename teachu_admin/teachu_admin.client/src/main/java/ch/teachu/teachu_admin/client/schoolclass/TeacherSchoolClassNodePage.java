package ch.teachu.teachu_admin.client.schoolclass;

import ch.teachu.teachu_admin.client.event.schoolclass.SchoolClassEventTablePage;
import ch.teachu.teachu_admin.client.lesson.LessonTablePage;
import ch.teachu.teachu_admin.client.schoolclass.subject.SchoolClassSubjectTablePage;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithNodes;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.IPage;

import java.util.List;

public class TeacherSchoolClassNodePage extends AbstractPageWithNodes {

  private final String schoolClassId;

  public TeacherSchoolClassNodePage(String schoolClassId) {
    this.schoolClassId = schoolClassId;
  }

  @Override
  protected void execCreateChildPages(List<IPage<?>> pageList) {
    pageList.add(new SchoolClassSubjectTablePage(schoolClassId));
    pageList.add(new LessonTablePage(schoolClassId));
    pageList.add(new SchoolClassEventTablePage(schoolClassId));
  }
}
