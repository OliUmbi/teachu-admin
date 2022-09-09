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

    String imageId = formData.getImage() == null ? null : UUID.randomUUID().toString();
    if (imageId != null) {
      createImage(formData, imageId);
    }

    formData.setId(UUID.randomUUID().toString());
    SQL.insert("INSERT INTO school_info(id, title, message, date, user_id, important, pinned, active, img) " +
        "VALUES(UUID_TO_BIN(:id), :title, :message, CURDATE(), UUID_TO_BIN(:userId), :important, :pinned, :active, UUID_TO_BIN(:imageId))", formData,
      new NVPair("userId", BEANS.get(AccessHelper.class).getCurrentUserId()),
      new NVPair("imageId", imageId));

    return formData;
  }

  private void createImage(SchoolInfoFormData formData, String imageId) {
    SQL.insert("INSERT INTO image(id, image) VALUES (UUID_TO_BIN(:id), :image)",
      new NVPair("id", imageId),
      new NVPair("image", formData.getImage()));
  }

  @Override
  public SchoolInfoFormData load(SchoolInfoFormData formData) {
    BEANS.get(AccessHelper.class).ensureAdmin();
    SQL.selectInto("SELECT active, important, message, pinned, title, image " +
      "FROM school_info " +
      "LEFT JOIN image ON (img = image.id) " +
      "WHERE school_info.id = UUID_TO_BIN(:id) " +
      "INTO :active, :important, :message, :pinned, :title, :image", formData);
    return formData;
  }

  @Override
  public SchoolInfoFormData store(SchoolInfoFormData formData) {
    BEANS.get(AccessHelper.class).ensureAdmin();

    boolean newImage = false;
    String imageId = (String) SQL.select("SELECT BIN_TO_UUID(img) FROM school_info WHERE id=UUID_TO_BIN(:id)", formData)[0][0];
    if (imageId == null && formData.getImage() != null) {
      newImage = true;
      imageId = UUID.randomUUID().toString();
    }

    SQL.update("UPDATE school_info " +
        "SET title = :title, " +
        "message = :message, " +
        "date = CURDATE(), " +
        "user_id = UUID_TO_BIN(:userId), " +
        "important = :important, " +
        "pinned = :pinned, " +
        "active = :active, " +
        "img = UUID_TO_BIN(:imageId) " +
        "WHERE id = UUID_TO_BIN(:id)",
      formData,
      new NVPair("userId", BEANS.get(AccessHelper.class).getCurrentUserId()),
      new NVPair("imageId", imageId));

    if (formData.getImage() != null) {
      if (newImage) {
        createImage(formData, imageId);
      } else {
        SQL.update("UPDATE image " +
            "SET image = :image " +
            "WHERE id = UUID_TO_BIN(:imageId)",
          new NVPair("imageId", imageId), formData);
      }
    }

    return formData;
  }

  @Override
  public void delete(String id) {
    BEANS.get(AccessHelper.class).ensureAdmin();
    SQL.delete("DELETE FROM image WHERE image.id=(SELECT school_info.img FROM school_info WHERE id=UUID_TO_BIN(:id))", new NVPair("id", id));
    SQL.delete("DELETE FROM school_info WHERE id=UUID_TO_BIN(:id)", new NVPair("id", id));
  }
}
