package ch.teachu.teachu_admin.server.event.user;

import ch.teachu.teachu_admin.shared.AccessHelper;
import ch.teachu.teachu_admin.shared.event.user.IUserEventService;
import ch.teachu.teachu_admin.shared.event.user.UserEventFormData;
import ch.teachu.teachu_admin.shared.event.user.UserEventTablePageData;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.server.jdbc.SQL;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

import java.util.UUID;

public class UserEventService implements IUserEventService {
  @Override
  public UserEventTablePageData getUserEventTableData(SearchFilter filter, String userId) {
    BEANS.get(AccessHelper.class).ensureTeacher();
    UserEventTablePageData pageData = new UserEventTablePageData();
    SQL.selectInto("SELECT BIN_TO_UUID(id), date_from, date_to, title, user_event_type, user_event_state " +
      "FROM user_event " +
      "WHERE user_id = UUID_TO_BIN(:userId)" +
      "INTO :id, :from, :to, :title, :eventType, :eventState", pageData, new NVPair("userId", userId));
    return pageData;
  }

  @Override
  public UserEventFormData prepareCreate(UserEventFormData formData) {
    BEANS.get(AccessHelper.class).ensureTeacher();
    return formData;
  }

  @Override
  public UserEventFormData create(UserEventFormData formData) {
    BEANS.get(AccessHelper.class).ensureTeacher();
    formData.setId(UUID.randomUUID().toString());
    SQL.insert("INSERT INTO user_event(id, date_from, date_to, title, description, user_event_type, user_event_state, user_id) " +
      "VALUES(UUID_TO_BIN(:id), :from, :to, :title, description, :userEventType, :userEventState, UUID_TO_BIN(:userId))", formData);
    return formData;
  }

  @Override
  public UserEventFormData load(UserEventFormData formData) {
    BEANS.get(AccessHelper.class).ensureTeacher();
    SQL.selectInto("SELECT date_from, date_to, title, description, user_event_type, user_event_state " +
      "FROM user_event " +
      "WHERE id = UUID_TO_BIN(:id) " +
      "AND user_id = UUID_TO_BIN(:userId) " +
      "INTO :from, :to, :title, :description, :userEventType, :userEventState", formData);
    return formData;
  }

  @Override
  public UserEventFormData store(UserEventFormData formData) {
    BEANS.get(AccessHelper.class).ensureTeacher();
    SQL.update("UPDATE user_event " +
        "SET date_from = :from, " +
        "date_to = :to, " +
        "title = :title, " +
        "description = :description, " +
        "user_event_type = :userEventType, " +
        "user_event_state = :userEventState " +
        "WHERE id = UUID_TO_BIN(:id)",
      formData);
    return formData;
  }

  @Override
  public void delete(String userEventId) {
    BEANS.get(AccessHelper.class).ensureTeacher();
    SQL.delete("DELETE FROM user_event WHERE id=UUID_TO_BIN(:id)", new NVPair("id", userEventId));
  }
}
