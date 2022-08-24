package ch.teachu.teachu_admin.server.exam;

import ch.teachu.teachu_admin.shared.AccessHelper;
import ch.teachu.teachu_admin.shared.exam.ExamFormData;
import ch.teachu.teachu_admin.shared.exam.ExamTablePageData;
import ch.teachu.teachu_admin.shared.exam.IExamService;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.server.jdbc.SQL;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

import java.math.BigDecimal;
import java.util.UUID;

public class ExamService implements IExamService {
  @Override
  public ExamTablePageData getExamTableData(SearchFilter filter, String schoolClassSubjectId) {
    BEANS.get(AccessHelper.class).ensureTeacher();
    ExamTablePageData pageData = new ExamTablePageData();
    StringBuilder sql = new StringBuilder("SELECT BIN_TO_UUID(exam.id), name, weight, date, view_date, BIN_TO_UUID(school_class_id), BIN_TO_UUID(school_class_subject_id) " +
      "FROM exam " +
      "JOIN school_class_subject ON (school_class_subject_id = school_class_subject.id) " +
      "WHERE teacher_id = UUID_TO_BIN(:teacherId) ");
    if (schoolClassSubjectId != null) {
      sql.append("AND school_class_subject_id = UUID_TO_BIN(:inSchoolClassSubjectId) ");
    }
    sql.append("INTO :id, :name, :weight, :date, :viewDate, :schoolClass, :schoolClassSubject");
    SQL.selectInto(sql.toString(), pageData,
      new NVPair("inSchoolClassSubjectId", schoolClassSubjectId),
      new NVPair("teacherId", BEANS.get(AccessHelper.class).getCurrentUserId()));
    return pageData;
  }

  @Override
  public ExamFormData prepareCreate(ExamFormData formData) {
    BEANS.get(AccessHelper.class).ensureTeacher();
    formData.getWeight().setValue(new BigDecimal("1"));
    return formData;
  }

  @Override
  public ExamFormData create(ExamFormData formData) {
    BEANS.get(AccessHelper.class).ensureTeacher();
    formData.setId(UUID.randomUUID().toString());
    SQL.insert("INSERT INTO exam(id, school_class_subject_id, name, description, weight, date, view_date) " +
      "VALUES(UUID_TO_BIN(:id), UUID_TO_BIN(:subject), :name, :description, :weight, :date, :viewDate)", formData);
    return formData;
  }

  @Override
  public ExamFormData load(ExamFormData formData) {
    BEANS.get(AccessHelper.class).ensureTeacher();
    SQL.selectInto("SELECT BIN_TO_UUID(school_class_subject_id), BIN_TO_UUID(school_class_id), name, description, weight, date, view_date " +
      "FROM exam " +
      "JOIN school_class_subject ON (school_class_subject_id = school_class_subject.id) " +
      "WHERE exam.id = UUID_TO_BIN(:id) " +
      "INTO :subject, :schoolClass, :name, :description, :weight, :date, :viewDate", formData);
    return formData;
  }

  @Override
  public ExamFormData store(ExamFormData formData) {
    BEANS.get(AccessHelper.class).ensureTeacher();
    SQL.update("UPDATE exam " +
        "SET school_class_subject_id = UUID_TO_BIN(:subject), " +
        "name = :name, " +
        "description = :description, " +
        "weight = :weight, " +
        "date = :date, " +
        "view_date = :viewDate " +
        "WHERE id = UUID_TO_BIN(:id)",
      formData);
    return formData;
  }

  @Override
  public void delete(String id) {
    BEANS.get(AccessHelper.class).ensureTeacher();
    SQL.delete("DELETE FROM exam WHERE id=UUID_TO_BIN(:id)", new NVPair("id", id));
    SQL.delete("DELETE FROM grade WHERE exam_id=UUID_TO_BIN(:id)", new NVPair("id", id));
  }
}
