package ch.teachu.teachu_admin.server.subject;

import ch.teachu.teachu_admin.shared.AccessHelper;
import ch.teachu.teachu_admin.shared.subject.ISubjectService;
import ch.teachu.teachu_admin.shared.subject.SubjectFormData;
import ch.teachu.teachu_admin.shared.subject.SubjectTablePageData;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.server.jdbc.SQL;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

import java.math.BigDecimal;
import java.util.UUID;

public class SubjectService implements ISubjectService {
  @Override
  public SubjectTablePageData getSubjectTableData(SearchFilter filter) {
    BEANS.get(AccessHelper.class).ensureAdmin();
    SubjectTablePageData pageData = new SubjectTablePageData();
    SQL.selectInto("SELECT BIN_TO_UUID(id), name, weight " +
      "FROM subject " +
      "INTO :id, :subject, :weight", pageData);
    return pageData;
  }

  @Override
  public SubjectFormData prepareCreate(SubjectFormData formData) {
    BEANS.get(AccessHelper.class).ensureAdmin();
    formData.getWeight().setValue(new BigDecimal("1"));
    return formData;
  }

  @Override
  public SubjectFormData create(SubjectFormData formData) {
    BEANS.get(AccessHelper.class).ensureAdmin();
    formData.setId(UUID.randomUUID().toString());
    SQL.insert("INSERT INTO subject(id, name, weight) " +
      "VALUES(UUID_TO_BIN(:id), :subject, :weight)", formData);
    return formData;
  }

  @Override
  public SubjectFormData load(SubjectFormData formData) {
    BEANS.get(AccessHelper.class).ensureAdmin();
    SQL.selectInto("SELECT name, weight " +
      "FROM subject " +
      "WHERE id = UUID_TO_BIN(:id) " +
      "INTO :subject, :weight", formData);
    return formData;
  }

  @Override
  public SubjectFormData store(SubjectFormData formData) {
    BEANS.get(AccessHelper.class).ensureAdmin();
    SQL.update("UPDATE subject " +
        "SET name = :subject, " +
        "weight = :weight " +
        "WHERE id = UUID_TO_BIN(:id)",
      formData);
    return formData;
  }

  @Override
  public void delete(String id) {
    BEANS.get(AccessHelper.class).ensureAdmin();
    SQL.delete("DELETE FROM subject WHERE id=UUID_TO_BIN(:id)", new NVPair("id", id));
  }
}
