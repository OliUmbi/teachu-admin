package ch.teachu.teachu_admin.server.room;

import ch.teachu.teachu_admin.server.AccessHelper;
import ch.teachu.teachu_admin.shared.room.IRoomService;
import ch.teachu.teachu_admin.shared.room.RoomFormData;
import ch.teachu.teachu_admin.shared.room.RoomTablePageData;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.server.jdbc.SQL;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

import java.util.UUID;

public class RoomService implements IRoomService {
  @Override
  public RoomTablePageData getRoomTableData(SearchFilter filter) {
    BEANS.get(AccessHelper.class).ensureAdmin();
    RoomTablePageData pageData = new RoomTablePageData();
    SQL.selectInto("SELECT BIN_TO_UUID(id), name " +
      "FROM room " +
      "INTO :id, :name", pageData);
    return pageData;
  }

  @Override
  public RoomFormData prepareCreate(RoomFormData formData) {
    BEANS.get(AccessHelper.class).ensureAdmin();
    return formData;
  }

  @Override
  public RoomFormData create(RoomFormData formData) {
    BEANS.get(AccessHelper.class).ensureAdmin();
    formData.setId(UUID.randomUUID().toString());
    SQL.insert("INSERT INTO room(id, name, note) " +
      "VALUES(UUID_TO_BIN(:id), :name, :description)", formData);
    return formData;
  }

  @Override
  public RoomFormData load(RoomFormData formData) {
    BEANS.get(AccessHelper.class).ensureAdmin();
    SQL.selectInto("SELECT name, note " +
      "FROM room " +
      "WHERE id = UUID_TO_BIN(:id) " +
      "INTO :name, :description", formData);
    return formData;
  }

  @Override
  public RoomFormData store(RoomFormData formData) {
    BEANS.get(AccessHelper.class).ensureAdmin();
    SQL.update("UPDATE room " +
        "SET name = :name, " +
        "note = :description " +
        "WHERE id = UUID_TO_BIN(:id)",
      formData);
    return formData;
  }

  @Override
  public void delete(String id) {
    BEANS.get(AccessHelper.class).ensureAdmin();
    SQL.delete("DELETE FROM room WHERE id=UUID_TO_BIN(:id)", new NVPair("id", id));
  }
}
