package ch.teachu.teachu_admin.shared.lesson;

import org.eclipse.scout.rt.security.AbstractPermission;

public class ReadLessonPermission extends AbstractPermission {
  private static final long serialVersionUID = 1L;

  public ReadLessonPermission() {
    super("ReadLessonPermission");
  }
}
