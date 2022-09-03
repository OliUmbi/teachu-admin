package ch.teachu.teachu_admin.server.grade;

import ch.teachu.teachu_admin.shared.AccessHelper;
import ch.teachu.teachu_admin.shared.grade.GradeFormData;
import ch.teachu.teachu_admin.shared.grade.GradeTablePageData;
import ch.teachu.teachu_admin.shared.grade.IGradeService;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.server.jdbc.SQL;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

import java.util.UUID;

public class GradeService implements IGradeService {
  @Override
  public GradeTablePageData getGradeTableData(SearchFilter filter, String examId) {
    BEANS.get(AccessHelper.class).ensureTeacher();
    GradeTablePageData pageData = new GradeTablePageData();
    SQL.selectInto("SELECT BIN_TO_UUID(grade.id), first_name, last_name, mark " +
      "FROM grade " +
      "JOIN user ON (student_id = user.id) " +
      "WHERE exam_id = UUID_TO_BIN(:examId) " +
      "INTO :id, :studentFirstName, :studentLastName, :mark", pageData, new NVPair("examId", examId));
    return pageData;
  }

  @Override
  public GradeFormData prepareCreate(GradeFormData formData) {
    BEANS.get(AccessHelper.class).ensureTeacher();
    return formData;
  }

  @Override
  public GradeFormData create(GradeFormData formData) {
    BEANS.get(AccessHelper.class).ensureTeacher();
    formData.setId(UUID.randomUUID().toString());
    SQL.insert("INSERT INTO grade(id, student_id, exam_id, mark, note) " +
      "VALUES(UUID_TO_BIN(:id), UUID_TO_BIN(:student), UUID_TO_BIN(:examId), :mark, :notes)", formData);
    return formData;
  }

  @Override
  public GradeFormData load(GradeFormData formData) {
    BEANS.get(AccessHelper.class).ensureTeacher();
    SQL.selectInto("SELECT BIN_TO_UUID(student_id), mark, note " +
      "FROM grade " +
      "WHERE id = UUID_TO_BIN(:id) " +
      "INTO :student, :mark, :notes", formData);
    return formData;
  }

  @Override
  public GradeFormData store(GradeFormData formData) {
    BEANS.get(AccessHelper.class).ensureTeacher();
    SQL.update("UPDATE grade " +
        "SET student_id = UUID_TO_BIN(:student), " +
        "mark = :mark, " +
        "note = :notes " +
        "WHERE id = UUID_TO_BIN(:id)",
      formData);
    return formData;
  }

  @Override
  public void delete(String id) {
    BEANS.get(AccessHelper.class).ensureTeacher();
    SQL.delete("DELETE FROM grade WHERE id=UUID_TO_BIN(:id)", new NVPair("id", id));
  }
}
