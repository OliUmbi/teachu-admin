package ch.teachu.teachu_admin.server.schoolclass;

import ch.teachu.teachu_admin.shared.AccessHelper;
import ch.teachu.teachu_admin.shared.chatgroup.ChatGroupFormData;
import ch.teachu.teachu_admin.shared.schoolclass.ISchoolClassService;
import ch.teachu.teachu_admin.shared.schoolclass.SchoolClassFormData;
import ch.teachu.teachu_admin.shared.schoolclass.SchoolClassTablePageData;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.holders.ITableBeanRowHolder;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.server.jdbc.SQL;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SchoolClassService implements ISchoolClassService {
  @Override
  public SchoolClassTablePageData getSchoolClassTableData(SearchFilter filter, String teacherId) {
    SchoolClassTablePageData pageData = new SchoolClassTablePageData();
    StringBuilder sql = new StringBuilder("SELECT BIN_TO_UUID(school_class.id), name, first_name, last_name " +
      "FROM school_class " +
      "LEFT JOIN user ON (teacher_id = user.id) ");

    if (teacherId != null) {
      sql.append(" WHERE teacher_id = UUID_TO_BIN(:teacherId) " +
        "OR UUID_TO_BIN(:teacherId) IN " +
        "(SELECT school_class_subject.teacher_id FROM school_class_subject WHERE school_class_id = school_class.id) ");
    }

    sql.append("INTO :id, :name, :classTeacherFirstName, :classTeacherLastName");

    SQL.selectInto(sql.toString(), pageData, new NVPair("teacherId", teacherId));

    return pageData;
  }

  @Override
  public SchoolClassFormData prepareCreate(SchoolClassFormData formData) {
    BEANS.get(AccessHelper.class).ensureAdmin();
    return formData;
  }

  @Override
  public SchoolClassFormData create(SchoolClassFormData formData) {
    BEANS.get(AccessHelper.class).ensureAdmin();
    formData.setId(UUID.randomUUID().toString());
    SQL.insert("INSERT INTO school_class(id, name, teacher_id) VALUES (UUID_TO_BIN(:id), :name, UUID_TO_BIN(:classTeacher))", formData);
    updateStudents(formData);
    updateSemesters(formData);
    return formData;
  }

  private void updateStudents(SchoolClassFormData formData) {
    SQL.delete("DELETE FROM school_class_user WHERE school_class_id = UUID_TO_BIN(:id)", formData);
    StringBuilder sql = new StringBuilder("INSERT INTO school_class_user(school_class_id, user_id) VALUES");
    List<NVPair> ids = new ArrayList<>();
    ids.add(new NVPair("schoolClassId", formData.getId()));
    boolean firstTime = true;
    for (int i = 0; i < formData.getStudents().getRows().length; i++) {
      SchoolClassFormData.Students.StudentsRowData row = formData.getStudents().getRows()[i];
      if (row.getRowState() == ITableBeanRowHolder.STATUS_DELETED || row.getName() == null || ids.stream().anyMatch(id -> id.getValue().equals(row.getName()))) {
        continue;
      }
      if (firstTime) {
        firstTime = false;
      } else {
        sql.append(", ");
      }
      sql.append(" (UUID_TO_BIN(:schoolClassId), UUID_TO_BIN(:id").append(i).append("))");
      ids.add(new NVPair("id" + i, row.getName()));
    }

    if (ids.size() > 1) {
      SQL.insert(sql.toString(), ids.toArray());
    }
  }

  private void updateSemesters(SchoolClassFormData formData) {
    SQL.delete("DELETE FROM school_class_semester WHERE school_class_id = UUID_TO_BIN(:id)", formData);
    StringBuilder sql = new StringBuilder("INSERT INTO school_class_semester(school_class_id, semester_id) VALUES");
    List<NVPair> ids = new ArrayList<>();
    ids.add(new NVPair("schoolClassId", formData.getId()));
    boolean firstTime = true;
    for (int i = 0; i < formData.getSemesters().getRows().length; i++) {
      SchoolClassFormData.Semesters.SemestersRowData row = formData.getSemesters().getRows()[i];
      if (row.getRowState() == ITableBeanRowHolder.STATUS_DELETED || row.getName() == null || ids.stream().anyMatch(id -> id.getValue().equals(row.getName()))) {
        continue;
      }
      if (firstTime) {
        firstTime = false;
      } else {
        sql.append(", ");
      }
      sql.append(" (UUID_TO_BIN(:schoolClassId), UUID_TO_BIN(:id").append(i).append("))");
      ids.add(new NVPair("id" + i, row.getName()));
    }

    if (ids.size() > 1) {
      SQL.insert(sql.toString(), ids.toArray());
    }
  }

  @Override
  public SchoolClassFormData load(SchoolClassFormData formData) {
    SQL.selectInto("SELECT name, BIN_TO_UUID(teacher_id) FROM school_class WHERE id = UUID_TO_BIN(:id) INTO :name, :classTeacher", formData);

    loadStudents(formData);
    loadSemesters(formData);
    return formData;
  }

  private void loadStudents(SchoolClassFormData formData) {
    Object[][] students = SQL.select("SELECT BIN_TO_UUID(user_id) FROM school_class_user WHERE school_class_id = UUID_TO_BIN(:id)", formData);
    for (Object[] student : students) {
      SchoolClassFormData.Students.StudentsRowData studentsRowData = new SchoolClassFormData.Students.StudentsRowData();
      studentsRowData.setName((String) student[0]);
      formData.getStudents().addRow(studentsRowData);
    }
  }

  private void loadSemesters(SchoolClassFormData formData) {
    Object[][] semesters = SQL.select("SELECT BIN_TO_UUID(semester_id) FROM school_class_semester WHERE school_class_id = UUID_TO_BIN(:id)", formData);
    for (Object[] semester : semesters) {
      SchoolClassFormData.Semesters.SemestersRowData semestersRowData = new SchoolClassFormData.Semesters.SemestersRowData();
      semestersRowData.setName((String) semester[0]);
      formData.getSemesters().addRow(semestersRowData);
    }
  }

  @Override
  public SchoolClassFormData store(SchoolClassFormData formData) {
    BEANS.get(AccessHelper.class).ensureAdmin();
    SQL.update("UPDATE school_class SET name = :name, teacher_id=UUID_TO_BIN(:classTeacher) WHERE id=UUID_TO_BIN(:id)", formData);

    updateStudents(formData);
    updateSemesters(formData);
    return formData;
  }

  @Override
  public void delete(String id) {
    BEANS.get(AccessHelper.class).ensureAdmin();
    NVPair idPair = new NVPair("id", id);
    SQL.delete("DELETE FROM school_class WHERE id=UUID_TO_BIN(:id)", idPair);
    SQL.delete("DELETE FROM school_class_user WHERE school_class_id = UUID_TO_BIN(:id)", idPair);
    SQL.delete("DELETE FROM school_class_semester WHERE school_class_id = UUID_TO_BIN(:id)", idPair);
    SQL.delete("DELETE FROM lesson WHERE school_class_subject_id IN (SELECT id FROM school_class_subject WHERE school_class_id = UUID_TO_BIN(:id))", idPair);
    SQL.delete("DELETE FROM school_class_subject WHERE school_class_id = UUID_TO_BIN(:id)", idPair);
    SQL.delete("DELETE FROM school_class_event WHERE school_class_id = UUID_TO_BIN(:id)", idPair);
    SQL.delete("DELETE FROM exam WHERE school_class_subject_id IN (SELECT id FROM school_class_subject WHERE school_class_id = UUID_TO_BIN(:id))", idPair);
    SQL.delete("DELETE FROM grade WHERE exam_id IN (SELECT exam.id FROM exam WHERE school_class_subject_id IN (SELECT id FROM school_class_subject WHERE school_class_id = UUID_TO_BIN(:id)))", idPair);
  }

  @Override
  public ChatGroupFormData getCreateChatGroupFormData(String schoolClassId) {
    ChatGroupFormData formData = new ChatGroupFormData();
    SQL.selectInto("SELECT name FROM school_class WHERE id = UUID_TO_BIN(:schoolClassId) INTO :title", formData, new NVPair("schoolClassId", schoolClassId));

    ChatGroupFormData.Members.MembersRowData creatorRow = new ChatGroupFormData.Members.MembersRowData();
    creatorRow.setName(BEANS.get(AccessHelper.class).getCurrentUserId());
    formData.getMembers().addRow(creatorRow);
    Object[][] students = SQL.select("SELECT BIN_TO_UUID(user_id) FROM school_class_user WHERE school_class_id = UUID_TO_BIN(:schoolClassId)", formData, new NVPair("schoolClassId", schoolClassId));
    for (Object[] student : students) {
      ChatGroupFormData.Members.MembersRowData membersRowData = new ChatGroupFormData.Members.MembersRowData();
      membersRowData.setName((String) student[0]);
      formData.getMembers().addRow(membersRowData);
    }
    return formData;
  }
}
