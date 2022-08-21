package ch.teachu.teachu_admin.server.schoolinfo;

import ch.teachu.teachu_admin.shared.AccessHelper;
import ch.teachu.teachu_admin.shared.schoolinfo.ISchoolInfoService;
import ch.teachu.teachu_admin.shared.schoolinfo.SchoolInfoFormData;
import ch.teachu.teachu_admin.shared.schoolinfo.SchoolInfoTablePageData;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.server.jdbc.SQL;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

import java.util.UUID;

public class SchoolInfoService implements ISchoolInfoService {
  @Override
  public SchoolInfoTablePageData getSchoolInfoTableData(SearchFilter filter) {
    BEANS.get(AccessHelper.class).ensureAdmin();
    SchoolInfoTablePageData pageData = new SchoolInfoTablePageData();
    SQL.selectInto("SELECT BIN_TO_UUID(school_info.id), title, date, CONCAT(first_name, ' ', last_name) " +
      "FROM school_info " +
      "LEFT JOIN user ON (user_id = user.id)" +
      "INTO :id, :title, :date, :creator", pageData);
    return pageData;
  }

  @Override
  public SchoolInfoFormData prepareCreate(SchoolInfoFormData formData) {
    BEANS.get(AccessHelper.class).ensureAdmin();
    formData.getActive().setValue(true);
    return formData;
  }

  @Override
  public SchoolInfoFormData create(SchoolInfoFormData formData) {
    BEANS.get(AccessHelper.class).ensureAdmin();

    formData.setId(UUID.randomUUID().toString());
    SQL.insert("INSERT INTO school_info(id, title, message, date, user_id, important, pinned, active) " +
        "VALUES(UUID_TO_BIN(:id), :title, :message, CURDATE(), UUID_TO_BIN(:userId), :important, :pinned, :active)", formData,
      new NVPair("userId", BEANS.get(AccessHelper.class).getCurrentUserId()));
    return formData;
  }

  @Override
  public SchoolInfoFormData load(SchoolInfoFormData formData) {
    BEANS.get(AccessHelper.class).ensureAdmin();
    SQL.selectInto("SELECT active, important, message, pinned, title " +
      "FROM school_info " +
      "WHERE id = UUID_TO_BIN(:id) " +
      "INTO :active, :important, :message, :pinned, :title", formData);
    return formData;
  }

  @Override
  public SchoolInfoFormData store(SchoolInfoFormData formData) {
    BEANS.get(AccessHelper.class).ensureAdmin();

    SQL.update("UPDATE school_info " +
        "SET title = :title, " +
        "message = :message, " +
        "date = CURDATE(), " +
        "user_id = UUID_TO_BIN(:userId), " +
        "important = :important, " +
        "pinned = :pinned, " +
        "active = :active " +
        "WHERE id = UUID_TO_BIN(:id)",
      formData,
      new NVPair("userId", BEANS.get(AccessHelper.class).getCurrentUserId()));
    return formData;
  }

  @Override
  public void delete(String id) {
    BEANS.get(AccessHelper.class).ensureAdmin();
    SQL.delete("DELETE FROM school_info WHERE id=UUID_TO_BIN(:id)", new NVPair("id", id));
  }
}
