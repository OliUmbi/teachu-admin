package ch.teachu.teachu_admin.server.semester;

import ch.teachu.teachu_admin.server.AccessHelper;
import ch.teachu.teachu_admin.shared.semester.ISemesterService;
import ch.teachu.teachu_admin.shared.semester.SemesterFormData;
import ch.teachu.teachu_admin.shared.semester.SemesterTablePageData;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.server.jdbc.SQL;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

import java.util.Date;
import java.util.UUID;

public class SemesterService implements ISemesterService {
  @Override
  public SemesterTablePageData getSemesterTableData(SearchFilter filter) {
    BEANS.get(AccessHelper.class).ensureAdmin();
    SemesterTablePageData pageData = new SemesterTablePageData();
    SQL.selectInto("SELECT BIN_TO_UUID(id), name, date_from, date_to " +
      "FROM semester " +
      "INTO :id, :name, :from, :to", pageData);
    return pageData;
  }

  @Override
  public SemesterFormData prepareCreate(SemesterFormData formData) {
    BEANS.get(AccessHelper.class).ensureAdmin();
    return formData;
  }

  @Override
  public SemesterFormData create(SemesterFormData formData) {
    BEANS.get(AccessHelper.class).ensureAdmin();
    formData.setId(UUID.randomUUID().toString());
    SQL.insert("INSERT INTO semester(id, name, date_from, date_to) " +
      "VALUES(UUID_TO_BIN(:id), :name, :from, :to)", formData);
    return formData;
  }

  @Override
  public SemesterFormData load(SemesterFormData formData) {
    BEANS.get(AccessHelper.class).ensureAdmin();
    SQL.selectInto("SELECT name, date_from, date_to " +
      "FROM semester " +
      "WHERE id = UUID_TO_BIN(:id) " +
      "INTO :name, :from, :to", formData);
    return formData;
  }

  @Override
  public SemesterFormData store(SemesterFormData formData) {
    BEANS.get(AccessHelper.class).ensureAdmin();
    SQL.update("UPDATE semester " +
        "SET name = :name, " +
        "date_from = :from, " +
        "date_to = :to " +
        "WHERE id = UUID_TO_BIN(:id)",
      formData);
    return formData;
  }

  @Override
  public void delete(String id) {
    BEANS.get(AccessHelper.class).ensureAdmin();
    SQL.delete("DELETE FROM semester WHERE id=UUID_TO_BIN(:id)", new NVPair("id", id));
  }

  @Override
  public boolean overlapsWithOtherSemesters(String id, Date from, Date to) {
    StringBuilder sql = new StringBuilder("SELECT count(*) " +
      "FROM semester " +
      "WHERE date_to > DATE_ADD(:from, INTERVAL 1 SECOND) AND date_from < DATE_SUB(:to, INTERVAL 1 SECOND)");
    if (id != null) {
      sql.append(" AND NOT id = UUID_TO_BIN(:id)");
    }

    long overlaps = (long) SQL.select(sql.toString(),
      new NVPair("from", from), new NVPair("to", to), new NVPair("id", id))[0][0];
    return overlaps > 0;
  }
}
