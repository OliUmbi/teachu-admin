package ch.teachu.teachu_admin.shared.event.lesson;

import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.shared.services.common.code.AbstractCode;
import org.eclipse.scout.rt.shared.services.common.code.AbstractCodeType;

public class LessonEventCodeType extends AbstractCodeType<String, String> {
  private static final long serialVersionUID = 1L;
  public static final String ID = "lessonEvent";

  @Override
  public String getId() {
    return ID;
  }

  @Order(1000)
  public static class ExamCode extends AbstractCode<String> {
    private static final long serialVersionUID = 1L;
    public static final String ID = "exam";

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("Exam");
    }

    @Override
    public String getId() {
      return ID;
    }
  }

  @Order(2000)
  public static class HomeworkCode extends AbstractCode<String> {
    private static final long serialVersionUID = 1L;
    public static final String ID = "homework";

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("Homework");
    }

    @Override
    public String getId() {
      return ID;
    }
  }

  @Order(3000)
  public static class NoteCode extends AbstractCode<String> {
    private static final long serialVersionUID = 1L;
    public static final String ID = "note";

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("Note");
    }

    @Override
    public String getId() {
      return ID;
    }
  }
}
