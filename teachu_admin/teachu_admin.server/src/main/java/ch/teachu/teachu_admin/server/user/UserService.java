package ch.teachu.teachu_admin.server.user;

import ch.teachu.teachu_admin.server.AccessHelper;
import ch.teachu.teachu_admin.server.sql.UuidConverter;
import ch.teachu.teachu_admin.shared.schoolconfig.ISchoolConfigService;
import ch.teachu.teachu_admin.shared.user.IUserService;
import ch.teachu.teachu_admin.shared.user.UserFormData;
import ch.teachu.teachu_admin.shared.user.UserTablePageData;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.server.jdbc.SQL;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

public class UserService implements IUserService {

  public static final String LANGUAGE_CONFIG = "LANGUAGE";

  @Override
  public UserTablePageData getUserTableData(SearchFilter filter) {
    BEANS.get(AccessHelper.class).ensureAdmin();
    UserTablePageData pageData = new UserTablePageData();
    SQL.selectInto("SELECT id, first_name, last_name, email, active, role FROM user INTO :id, :firstName, :lastName, :email, :active, :role", pageData);
    Arrays.stream(pageData.getRows()).forEach(row -> row.setId(BEANS.get(UuidConverter.class).uuid((byte[]) row.getId())));
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
      formData.setId(BEANS.get(UuidConverter.class).byteArray(UUID.randomUUID()));
    }

    String defaultLanguage = BEANS.get(ISchoolConfigService.class).getConfig(LANGUAGE_CONFIG);

    SQL.insert("INSERT INTO user(id, email, password, role, first_name, last_name, birthday, sex, language," +
        "dark_theme, city, postal_code, street, phone, notes, creation_date, termination_date, active)" +
        "VALUES (:id, :email, :password, :role, :firstName, :lastName, :birthday, :sex, :language, 0, :city, :postalCode," +
        ":street, :phone, :notes, :creationDate, :termination, :active)",
      formData, new NVPair("creationDate", new Date()), new NVPair("language", defaultLanguage));
    return formData;
  }

  @Override
  public UserFormData load(UserFormData formData) {
    BEANS.get(AccessHelper.class).ensureAdmin();
    formData.setId(BEANS.get(UuidConverter.class).byteArray((UUID) formData.getId()));
    SQL.selectInto("SELECT email, role, first_name, last_name, birthday, sex, city, postal_code, street, phone," +
      "notes, termination_date, active FROM user WHERE id = :id INTO :email, :role, :firstName, :lastName, :birthday," +
      ":sex, :city, :postalCode, :street, :phone, :notes, :termination, :active", formData);
    return formData;
  }

  @Override
  public UserFormData store(UserFormData formData) {
    BEANS.get(AccessHelper.class).ensureAdmin();
    formData.setId(BEANS.get(UuidConverter.class).byteArray((UUID) formData.getId()));

    StringBuilder sql = new StringBuilder("UPDATE user SET email = :email, role=:role, first_name = :firstName," +
      "last_name = :lastName, birthday = :birthday, sex = :sex, city = :city, postal_code = :postalCode, street = :street," +
      "phone = :phone, notes = :notes, termination_date = :termination, active = :active");

    if (formData.getPassword().getValue() != null) {
      formData.getPassword().setValue(new BCryptPasswordEncoder().encode(formData.getPassword().getValue()));
      sql.append(", password = :password");
    }
    sql.append(" WHERE id=:id");

    SQL.update(sql.toString(), formData);
    return formData;
  }

  @Override
  public void delete(UUID id) {
    BEANS.get(AccessHelper.class).ensureAdmin();
    byte[] byteId = BEANS.get(UuidConverter.class).byteArray(id);
    SQL.delete("DELETE FROM user WHERE id=:id", new NVPair("id", byteId));
  }
}
