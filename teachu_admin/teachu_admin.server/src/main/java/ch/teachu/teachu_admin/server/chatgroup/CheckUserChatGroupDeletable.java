package ch.teachu.teachu_admin.server.chatgroup;

import ch.teachu.teachu_admin.shared.user.IUserCheckDeletable;
import org.eclipse.scout.rt.platform.text.TEXTS;

public class CheckUserChatGroupDeletable implements IUserCheckDeletable {
  @Override
  public String getCategoryName() {
    return TEXTS.get("ChatGroup");
  }

  @Override
  public String getSqlSelectReferences() {
    return "SELECT chat.title " +
      "FROM chat " +
      "JOIN chat_user ON (chat_user.chat_id = chat.id) " +
      "WHERE BIN_TO_UUID(chat_user.user_id) IN :ids";
  }
}
