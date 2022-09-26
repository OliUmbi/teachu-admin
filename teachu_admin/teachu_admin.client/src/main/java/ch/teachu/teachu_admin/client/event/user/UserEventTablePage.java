package ch.teachu.teachu_admin.client.event.user;

import ch.teachu.teachu_admin.client.event.user.UserEventTablePage.Table;
import ch.teachu.teachu_admin.client.shared.AbstractCreateMenu;
import ch.teachu.teachu_admin.client.shared.AbstractDeleteMenu;
import ch.teachu.teachu_admin.client.shared.AbstractEditMenu;
import ch.teachu.teachu_admin.shared.event.user.IUserEventService;
import ch.teachu.teachu_admin.shared.event.user.UserEventStateCodeType;
import ch.teachu.teachu_admin.shared.event.user.UserEventTablePageData;
import ch.teachu.teachu_admin.shared.event.user.UserEventTypeCodeType;
import org.eclipse.scout.rt.client.dto.Data;
import org.eclipse.scout.rt.client.ui.action.menu.IMenu;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.basic.table.ITableRow;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractDateTimeColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractSmartColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithTable;
import org.eclipse.scout.rt.client.ui.messagebox.IMessageBox;
import org.eclipse.scout.rt.client.ui.messagebox.MessageBoxes;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.shared.services.common.code.ICodeType;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

import java.util.List;

@Data(UserEventTablePageData.class)
public class UserEventTablePage extends AbstractPageWithTable<Table> {

  private final String userId;

  public UserEventTablePage(String userId) {
    this.userId = userId;
  }

  @Override
  protected boolean getConfiguredLeaf() {
    return true;
  }

  @Override
  protected void execLoadData(SearchFilter filter) {
    importPageData(BEANS.get(IUserEventService.class).getUserEventTableData(filter, userId));
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("UserEvent");
  }

  public class Table extends AbstractTable {

    public EventStateColumn getEventStateColumn() {
      return getColumnSet().getColumnByClass(EventStateColumn.class);
    }

    public EventTypeColumn getEventTypeColumn() {
      return getColumnSet().getColumnByClass(EventTypeColumn.class);
    }

    public IdColumn getIdColumn() {
      return getColumnSet().getColumnByClass(IdColumn.class);
    }

    public TitleColumn getTitleColumn() {
      return getColumnSet().getColumnByClass(TitleColumn.class);
    }

    public ToColumn getToColumn() {
      return getColumnSet().getColumnByClass(ToColumn.class);
    }

    @Override
    protected Class<? extends IMenu> getConfiguredDefaultMenu() {
      return EditMenu.class;
    }

    @Order(1000)
    public class IdColumn extends AbstractStringColumn {
      @Override
      protected boolean getConfiguredDisplayable() {
        return false;
      }
    }

    @Order(2000)
    public class TitleColumn extends AbstractStringColumn {
      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("Title");
      }

      @Override
      protected int getConfiguredWidth() {
        return 300;
      }
    }

    @Order(3000)
    public class EventStateColumn extends AbstractSmartColumn<String> {
      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("State");
      }

      @Override
      protected int getConfiguredWidth() {
        return 100;
      }

      @Override
      protected Class<? extends ICodeType<?, String>> getConfiguredCodeType() {
        return UserEventStateCodeType.class;
      }
    }

    @Order(4000)
    public class EventTypeColumn extends AbstractSmartColumn<String> {
      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("Type");
      }

      @Override
      protected int getConfiguredWidth() {
        return 100;
      }

      @Override
      protected Class<? extends ICodeType<?, String>> getConfiguredCodeType() {
        return UserEventTypeCodeType.class;
      }
    }

    @Order(5000)
    public class FromColumn extends AbstractDateTimeColumn {
      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("FromDate");
      }

      @Override
      protected int getConfiguredWidth() {
        return 200;
      }
    }

    @Order(6000)
    public class ToColumn extends AbstractDateTimeColumn {
      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("ToDate");
      }

      @Override
      protected int getConfiguredWidth() {
        return 200;
      }
    }

    @Order(1000)
    public class CreateMenu extends AbstractCreateMenu {

      @Override
      protected void execAction() {
        UserEventForm userEventForm = new UserEventForm();
        userEventForm.setUserId(userId);
        userEventForm.startNew();
        userEventForm.waitFor();
        reloadPage();
      }
    }

    @Order(2000)
    public class EditMenu extends AbstractEditMenu {
      @Override
      protected void execAction() {
        UserEventForm userEventForm = new UserEventForm();
        userEventForm.setId(getIdColumn().getValue(getSelectedRow()));
        userEventForm.setUserId(userId);
        userEventForm.startModify();
        userEventForm.waitFor();
        reloadPage();
      }
    }

    @Order(3000)
    public class DeleteMenu extends AbstractDeleteMenu {

      @Override
      protected void execAction() {
        List<String> titles = getTitleColumn().getValues(getSelectedRows());
        if (MessageBoxes.createDeleteConfirmationMessage(titles).show() == IMessageBox.YES_OPTION) {
          getIdColumn().getValues(getSelectedRows()).forEach(BEANS.get(IUserEventService.class)::delete);
          getSelectedRows().forEach(ITableRow::delete);
        }
      }
    }
  }
}
