package ch.teachu.teachu_admin.server.schoolclass;

import ch.teachu.teachu_admin.shared.user.IUserCheckDeletable;
import org.eclipse.scout.rt.platform.text.TEXTS;

public class CheckUserSchoolClassDeletable implements IUserCheckDeletable {
  @Override
  public String getCategoryName() {
    return TEXTS.get("SchoolClass");
  }

  @Override
  public String getSqlSelectReferences() {
    return "SELECT name FROM school_class WHERE BIN_TO_UUID(school_class.teacher_id) IN :ids " +
      "UNION " +
      "SELECT name FROM school_class_user JOIN school_class ON (school_class_id = id) WHERE BIN_TO_UUID(user_id) IN :ids";
  }
}
