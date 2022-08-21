package ch.teachu.teachu_admin.server.user;

import ch.teachu.teachu_admin.shared.AccessHelper;
import ch.teachu.teachu_admin.shared.schoolconfig.ISchoolConfigService;
import ch.teachu.teachu_admin.shared.user.IUserService;
import ch.teachu.teachu_admin.shared.user.RoleCodeType;
import ch.teachu.teachu_admin.shared.user.UserFormData;
import ch.teachu.teachu_admin.shared.user.UserTablePageData;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.holders.ITableBeanRowHolder;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.server.jdbc.SQL;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class UserService implements IUserService {

  public static final String LANGUAGE_CONFIG = "LANGUAGE";

  @Override
  public UserTablePageData getUserTableData(SearchFilter filter) {
    BEANS.get(AccessHelper.class).ensureAdmin();
    UserTablePageData pageData = new UserTablePageData();
    SQL.selectInto("SELECT BIN_TO_UUID(id), first_name, last_name, email, active, role FROM user INTO :id, :firstName, :lastName, :email, :active, :role", pageData);
    return pageData;
  }

  @Override
  public UserFormData prepareCreate(UserFormData formData) {
    BEANS.get(AccessHelper.class).ensureAdmin();
    return formData;
  }

  @Override
  public UserFormData create(UserFormData formData) {
    BEANS.get(AccessHelper.class).ensureAdmin();
    formData.getPassword().setValue(new BCryptPasswordEncoder().encode(formData.getPassword().getValue()));
    if (formData.getId() == null) {
      formData.setId(UUID.randomUUID().toString());
    }

    String defaultLanguage = BEANS.get(ISchoolConfigService.class).getConfig(LANGUAGE_CONFIG);

    SQL.insert("INSERT INTO user(id, email, password, role, first_name, last_name, birthday, sex, language," +
        "dark_theme, city, postal_code, street, phone, notes, creation_date, termination_date, active)" +
        "VALUES (UUID_TO_BIN(:id), :email, :password, :role, :firstName, :lastName, :birthday, :sex, :language, 0, :city, :postalCode," +
        ":street, :phone, :notes, :creationDate, :termination, :active)",
      formData, new NVPair("creationDate", new Date()), new NVPair("language", defaultLanguage));

    updateParentsChildren(formData);
    return formData;
  }

  @Override
  public UserFormData load(UserFormData formData) {
    BEANS.get(AccessHelper.class).ensureAdmin();
    SQL.selectInto("SELECT email, role, first_name, last_name, birthday, sex, city, postal_code, street, phone," +
      "notes, termination_date, active FROM user WHERE id = UUID_TO_BIN(:id) INTO :email, :role, :firstName, :lastName, :birthday," +
      ":sex, :city, :postalCode, :street, :phone, :notes, :termination, :active", formData);

    switch (formData.getRole().getValue()) {
      case RoleCodeType.StudentCode.ID:
        loadParentsChildren("SELECT BIN_TO_UUID(parent_id) FROM parent_student WHERE student_id = UUID_TO_BIN(:id)", formData);
        break;
      case RoleCodeType.ParentCode.ID:
        loadParentsChildren("SELECT BIN_TO_UUID(student_id) FROM parent_student WHERE parent_id = UUID_TO_BIN(:id)", formData);
    }

    return formData;
  }

  private void loadParentsChildren(String sql, UserFormData formData) {
    Object[][] parentsOrChildren = SQL.select(sql, formData);
    for (Object[] parent : parentsOrChildren) {
      UserFormData.ParentChild.ParentChildRowData parentChildRowData = new UserFormData.ParentChild.ParentChildRowData();
      parentChildRowData.setName((String) parent[0]);
      formData.getParentChild().addRow(parentChildRowData);
    }
  }

  @Override
  public UserFormData store(UserFormData formData) {
    BEANS.get(AccessHelper.class).ensureAdmin();
    StringBuilder sql = new StringBuilder("UPDATE user SET email = :email, role=:role, first_name = :firstName," +
      "last_name = :lastName, birthday = :birthday, sex = :sex, city = :city, postal_code = :postalCode, street = :street," +
      "phone = :phone, notes = :notes, termination_date = :termination, active = :active");

    if (formData.getPassword().getValue() != null) {
      formData.getPassword().setValue(new BCryptPasswordEncoder().encode(formData.getPassword().getValue()));
      sql.append(", password = :password");
    }
    sql.append(" WHERE id=UUID_TO_BIN(:id)");
    SQL.update(sql.toString(), formData);

    updateParentsChildren(formData);
    return formData;
  }

  private void updateParentsChildren(UserFormData formData) {
    boolean isStudent = RoleCodeType.StudentCode.ID.equals(formData.getRole().getValue());
    SQL.delete("DELETE FROM parent_student WHERE " + (isStudent ? "student_id" : "parent_id") + " = UUID_TO_BIN(:id)", formData);
    StringBuilder sql = new StringBuilder("INSERT INTO parent_student(parent_id, student_id) VALUES");
    List<NVPair> ids = new ArrayList<>();
    ids.add(new NVPair("userId", formData.getId()));
    boolean firstTime = true;
    for (int i = 0; i < formData.getParentChild().getRows().length; i++) {
      UserFormData.ParentChild.ParentChildRowData row = formData.getParentChild().getRows()[i];
      if (row.getRowState() == ITableBeanRowHolder.STATUS_DELETED || row.getName() == null || ids.stream().anyMatch(id -> id.getValue().equals(row.getName()))) {
        continue;
      }
      if (firstTime) {
        firstTime = false;
      } else {
        sql.append(", ");
      }
      sql.append(isStudent ? " (UUID_TO_BIN(:id" + i + "), UUID_TO_BIN(:userId))" :
        " (UUID_TO_BIN(:userId), UUID_TO_BIN(:id" + i + "))");
      ids.add(new NVPair("id" + i, row.getName()));
    }

    if (ids.size() > 1) {
      SQL.insert(sql.toString(), ids.toArray());
    }
  }

  @Override
  public void delete(String id) {
    BEANS.get(AccessHelper.class).ensureAdmin();
    NVPair idPair = new NVPair("id", id);
    SQL.delete("DELETE FROM user WHERE id=UUID_TO_BIN(:id)", idPair);
    SQL.delete("DELETE FROM parent_student WHERE student_id = UUID_TO_BIN(:id) || parent_id = UUID_TO_BIN(:id)", idPair);
  }
}
