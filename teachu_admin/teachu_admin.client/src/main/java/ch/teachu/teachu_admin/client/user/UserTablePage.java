package ch.teachu.teachu_admin.client.user;

import ch.teachu.teachu_admin.client.shared.*;
import ch.teachu.teachu_admin.client.user.UserTablePage.Table;
import ch.teachu.teachu_admin.shared.user.IUserCheckDeletable;
import ch.teachu.teachu_admin.shared.user.IUserService;
import ch.teachu.teachu_admin.shared.user.UserTablePageData;
import org.eclipse.scout.rt.client.dto.Data;
import org.eclipse.scout.rt.client.ui.action.menu.IMenu;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.basic.table.ITableRow;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractBooleanColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.messagebox.IMessageBox;
import org.eclipse.scout.rt.client.ui.messagebox.MessageBoxes;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

import java.util.List;
import java.util.stream.Collectors;

@Data(UserTablePageData.class)
public class UserTablePage extends AbstractTablePage<Table> {
  @Override
  protected boolean getConfiguredLeaf() {
    return true;
  }

  @Override
  protected void execLoadData(SearchFilter filter) {
    importPageData(BEANS.get(IUserService.class).getUserTableData(filter));
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("Users");
  }

  public class Table extends AbstractTable {

    public ActiveColumn getActiveColumn() {
      return getColumnSet().getColumnByClass(ActiveColumn.class);
    }

    public EmailColumn getEmailColumn() {
      return getColumnSet().getColumnByClass(EmailColumn.class);
    }

    public FirstNameColumn getFirstNameColumn() {
      return getColumnSet().getColumnByClass(FirstNameColumn.class);
    }

    public IdColumn getIdColumn() {
      return getColumnSet().getColumnByClass(IdColumn.class);
    }

    public LastNameColumn getLastNameColumn() {
      return getColumnSet().getColumnByClass(LastNameColumn.class);
    }

    public RoleColumn getRoleColumn() {
      return getColumnSet().getColumnByClass(RoleColumn.class);
    }

    @Override
    protected Class<? extends IMenu> getConfiguredDefaultMenu() {
      return EditMenu.class;
    }

    @Order(0)
    public class IdColumn extends AbstractColumn<String> {
      @Override
      protected boolean getConfiguredDisplayable() {
        return false;
      }
    }

    @Order(1000)
    public class FirstNameColumn extends AbstractStringColumn {
      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("FirstName");
      }

      @Override
      protected int getConfiguredWidth() {
        return 200;
      }
    }

    @Order(1500)
    public class LastNameColumn extends AbstractStringColumn {
      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("Lastname");
      }

      @Override
      protected int getConfiguredWidth() {
        return 200;
      }
    }

    @Order(1750)
    public class EmailColumn extends AbstractStringColumn {
      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("Email");
      }

      @Override
      protected int getConfiguredWidth() {
        return 300;
      }
    }

    @Order(1875)
    public class RoleColumn extends AbstractStringColumn {
      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("Role");
      }

      @Override
      protected int getConfiguredWidth() {
        return 80;
      }

      @Override
      protected String execParseValue(ITableRow row, Object rawValue) {
        String val = (String) rawValue;
        return TEXTS.get(val.substring(0, 1).toUpperCase() + val.substring(1));
      }
    }

    @Order(2000)
    public class ActiveColumn extends AbstractBooleanColumn {
      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("Active");
      }

      @Override
      protected int getConfiguredWidth() {
        return 75;
      }
    }

    @Order(1000)
    public class CreateMenu extends AbstractCreateMenu {

      @Override
      protected void execAction() {
        UserForm userForm = new UserForm();
        userForm.startNew();
        userForm.waitFor();
        reloadPage();
      }
    }

    @Order(2000)
    public class EditMenu extends AbstractEditMenu {
      @Override
      protected void execAction() {
        UserForm userForm = new UserForm();
        userForm.setId(getIdColumn().getValue(getSelectedRow()));
        userForm.startModify();
        userForm.waitFor();
        reloadPage();
      }
    }

    @Order(3000)
    public class DeleteMenu extends AbstractDeleteMenu {

      @Override
      protected void execAction() {
        List<String> ids = getIdColumn().getValues(getSelectedRows());
        if (BEANS.get(DeletableMessageBoxHelper.class).checkAndShowMessageBox(IUserCheckDeletable.class, ids)) {
          List<String> names = getNames();
          if (MessageBoxes.createDeleteConfirmationMessage(names).show() == IMessageBox.YES_OPTION) {
            ids.forEach(BEANS.get(IUserService.class)::delete);
            getSelectedRows().forEach(ITableRow::delete);
          }
        }
      }

      private List<String> getNames() {
        return getSelectedRows().stream()
          .map(row -> getFirstNameColumn().getValue(row) + ' ' + getLastNameColumn().getValue(row))
          .collect(Collectors.toList());
      }
    }
  }
}
