package ch.teachu.teachu_admin.server.schoolclass.subject;

import ch.teachu.teachu_admin.shared.AccessHelper;
import ch.teachu.teachu_admin.shared.schoolclass.subject.ISchoolClassSubjectService;
import ch.teachu.teachu_admin.shared.schoolclass.subject.SchoolClassSubjectFormData;
import ch.teachu.teachu_admin.shared.schoolclass.subject.SchoolClassSubjectTablePageData;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.server.jdbc.SQL;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

import java.util.UUID;

public class SchoolClassSubjectService implements ISchoolClassSubjectService {
  @Override
  public SchoolClassSubjectTablePageData getSchoolClassSubjectTableData(SearchFilter filter, String schoolClassId) {
    SchoolClassSubjectTablePageData pageData = new SchoolClassSubjectTablePageData();
    SQL.selectInto("SELECT BIN_TO_UUID(school_class_subject.id), BIN_TO_UUID(subject_id), first_name, last_name, start_date, end_date " +
        "FROM school_class_subject " +
        "LEFT JOIN user ON (teacher_id = user.id) " +
        "WHERE school_class_id = UUID_TO_BIN(:schoolClassId) " +
        "INTO :id, :subject, :teacherFirstName, :teacherLastName, :startDate, :endDate", pageData,
      new NVPair("schoolClassId", schoolClassId));
    return pageData;
  }

  @Override
  public SchoolClassSubjectFormData prepareCreate(SchoolClassSubjectFormData formData) {
    BEANS.get(AccessHelper.class).ensureAdmin();
    return formData;
  }

  @Override
  public SchoolClassSubjectFormData create(SchoolClassSubjectFormData formData) {
    BEANS.get(AccessHelper.class).ensureAdmin();

    formData.setId(UUID.randomUUID().toString());
    SQL.insert("INSERT INTO school_class_subject(id, school_class_id, subject_id, teacher_id, start_date, end_date, note) " +
      "VALUES(UUID_TO_BIN(:id), UUID_TO_BIN(:schoolClassId), UUID_TO_BIN(:subject), UUID_TO_BIN(:teacher), :startDate, :endDate, :notes)", formData);
    return formData;
  }

  @Override
  public SchoolClassSubjectFormData load(SchoolClassSubjectFormData formData) {
    SQL.selectInto("SELECT BIN_TO_UUID(subject_id), BIN_TO_UUID(teacher_id), start_date, end_date, note " +
      "FROM school_class_subject " +
      "WHERE id = UUID_TO_BIN(:id) " +
      "INTO :subject, :teacher, :startDate, :endDate, :notes", formData);
    return formData;
  }

  @Override
  public SchoolClassSubjectFormData store(SchoolClassSubjectFormData formData) {
    BEANS.get(AccessHelper.class).ensureAdmin();

    SQL.update("UPDATE school_class_subject " +
        "SET subject_id = UUID_TO_BIN(:subject), " +
        "teacher_id = UUID_TO_BIN(:teacher), " +
        "start_date = :startDate, " +
        "end_date = :endDate, " +
        "note = :notes " +
        "WHERE id = UUID_TO_BIN(:id)",
      formData);
    return formData;
  }

  @Override
  public void delete(String id) {
    BEANS.get(AccessHelper.class).ensureAdmin();
    SQL.delete("DELETE FROM school_class_subject WHERE id=UUID_TO_BIN(:id)", new NVPair("id", id));
  }

  @Override
  public boolean usedSubject(String id, String schoolClassId, String subjectId) {
    StringBuilder sql = new StringBuilder("SELECT count(*) " +
      "FROM school_class_subject " +
      "WHERE subject_id = UUID_TO_BIN(:subjectId) " +
      "AND school_class_id = UUID_TO_BIN(:schoolClassId)");
    if (id != null) {
      sql.append(" AND NOT school_class_subject.id = UUID_TO_BIN(:id)");
    }

    long usedSubject = (long) SQL.select(sql.toString(),
      new NVPair("subjectId", subjectId),
      new NVPair("id", id),
      new NVPair("schoolClassId", schoolClassId))[0][0];
    return usedSubject > 0;
  }
}
