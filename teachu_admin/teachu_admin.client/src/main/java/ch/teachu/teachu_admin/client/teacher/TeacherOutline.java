package ch.teachu.teachu_admin.client.teacher;

import ch.teachu.teachu_admin.client.chatgroup.ChatGroupTablePage;
import ch.teachu.teachu_admin.client.exam.ExamTablePage;
import ch.teachu.teachu_admin.client.lesson.LessonTablePage;
import ch.teachu.teachu_admin.client.schoolclass.TeacherSchoolClassTablePage;
import ch.teachu.teachu_admin.shared.Icons;
import ch.teachu.teachu_admin.shared.TeacherPermission;
import org.eclipse.scout.rt.client.ui.desktop.outline.AbstractOutline;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.IPage;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.security.ACCESS;

import java.util.List;

public class TeacherOutline extends AbstractOutline {

  @Override
  protected void execCreateChildPages(List<IPage<?>> pageList) {
    super.execCreateChildPages(pageList);
    pageList.add(new TeacherSchoolClassTablePage());
    pageList.add(new ExamTablePage());
    pageList.add(new LessonTablePage());
    pageList.add(new ChatGroupTablePage());
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("Teacher");
  }

  @Override
  protected String getConfiguredIconId() {
    return Icons.Pencil;
  }

  @Override
  protected boolean getConfiguredVisible() {
    return ACCESS.check(new TeacherPermission());
  }
}
