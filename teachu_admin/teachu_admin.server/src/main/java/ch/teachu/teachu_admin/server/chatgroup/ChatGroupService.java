package ch.teachu.teachu_admin.server.chatgroup;

import ch.teachu.teachu_admin.shared.AccessHelper;
import ch.teachu.teachu_admin.shared.chatgroup.ChatGroupFormData;
import ch.teachu.teachu_admin.shared.chatgroup.ChatGroupTablePageData;
import ch.teachu.teachu_admin.shared.chatgroup.IChatGroupService;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.holders.ITableBeanRowHolder;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.server.jdbc.SQL;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ChatGroupService implements IChatGroupService {
  @Override
  public ChatGroupTablePageData getChatGroupTableData(SearchFilter filter) {
    ChatGroupTablePageData pageData = new ChatGroupTablePageData();
    SQL.selectInto("SELECT BIN_TO_UUID(chat.id), title, CONCAT(c.first_name, ' ', c.last_name) " +
      "FROM chat " +
      "LEFT JOIN user c ON (c.id = creator_id) " +
      "JOIN user v ON (v.id = UUID_TO_BIN(:viewerId)) " +
      "WHERE v.role = 'admin' OR " +
      "v.id IN (" +
      "SELECT chat_user.user_id FROM chat_user WHERE chat_user.chat_id = chat.id) OR " +
      "v.id = c.id  " +
      "INTO :id, :title, :creator", pageData, new NVPair("viewerId", BEANS.get(AccessHelper.class).getCurrentUserId()));
    return pageData;
  }

  @Override
  public ChatGroupFormData prepareCreate(ChatGroupFormData formData) {
    if (formData.getMembers().getRows().length == 0) {
      ChatGroupFormData.Members.MembersRowData rowData = new ChatGroupFormData.Members.MembersRowData();
      rowData.setName(BEANS.get(AccessHelper.class).getCurrentUserId());
      formData.getMembers().addRow(rowData);
    }
    return formData;
  }

  @Override
  public ChatGroupFormData create(ChatGroupFormData formData) {
    formData.setId(UUID.randomUUID().toString());
    SQL.insert("INSERT INTO chat(id, title, description, creator_id) " +
        "VALUES (UUID_TO_BIN(:id), :title, :description, UUID_TO_BIN(:creatorId)) ",
      formData, new NVPair("creatorId", BEANS.get(AccessHelper.class).getCurrentUserId()));

    updateChatUsers(formData);
    return formData;
  }

  private void updateChatUsers(ChatGroupFormData formData) {
    SQL.delete("DELETE FROM chat_user WHERE chat_id = UUID_TO_BIN(:id)", formData);

    StringBuilder sql = new StringBuilder("INSERT INTO chat_user(user_id, chat_id) VALUES");
    List<NVPair> ids = new ArrayList<>();
    ids.add(new NVPair("chatId", formData.getId()));
    boolean firstTime = true;
    for (int i = 0; i < formData.getMembers().getRows().length; i++) {
      ChatGroupFormData.Members.MembersRowData row = formData.getMembers().getRows()[i];
      if (row.getRowState() == ITableBeanRowHolder.STATUS_DELETED || row.getName() == null || ids.stream().anyMatch(id -> id.getValue().equals(row.getName()))) {
        continue;
      }
      if (firstTime) {
        firstTime = false;
      } else {
        sql.append(", ");
      }
      sql.append(" (UUID_TO_BIN(:id").append(i).append("), UUID_TO_BIN(:chatId))");
      ids.add(new NVPair("id" + i, row.getName()));
    }

    if (ids.size() > 1) {
      SQL.insert(sql.toString(), ids.toArray());
    }
  }

  @Override
  public ChatGroupFormData load(ChatGroupFormData formData) {
    SQL.selectInto("SELECT title, description " +
      "FROM chat " +
      "WHERE chat.id = UUID_TO_BIN(:id) " +
      "INTO :title, :description ", formData);

    String currentUserId = BEANS.get(AccessHelper.class).getCurrentUserId();
    Object[][] members = SQL.select("SELECT BIN_TO_UUID(user_id) FROM chat_user WHERE chat_id = UUID_TO_BIN(:id)", formData);
    for (Object[] member : members) {
      String memberStr = (String) member[0];
      ChatGroupFormData.Members.MembersRowData row = new ChatGroupFormData.Members.MembersRowData();
      row.setName(memberStr);
      formData.getMembers().addRow(row);
    }

    return formData;
  }

  @Override
  public ChatGroupFormData store(ChatGroupFormData formData) {
    SQL.update("UPDATE chat " +
      "SET title = :title, " +
      "description = :description " +
      "WHERE id = UUID_TO_BIN(:id)", formData);

    updateChatUsers(formData);
    return formData;
  }

  @Override
  public void delete(String id) {
    SQL.delete("DELETE FROM chat_user WHERE chat_id=UUID_TO_BIN(:id)", new NVPair("id", id));
    SQL.delete("DELETE FROM chat WHERE id=UUID_TO_BIN(:id)", new NVPair("id", id));
  }
}
