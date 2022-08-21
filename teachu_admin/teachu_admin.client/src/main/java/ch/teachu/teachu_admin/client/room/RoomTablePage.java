package ch.teachu.teachu_admin.client.room;

import ch.teachu.teachu_admin.client.room.RoomTablePage.Table;
import ch.teachu.teachu_admin.client.shared.*;
import ch.teachu.teachu_admin.shared.room.IRoomCheckDeletable;
import ch.teachu.teachu_admin.shared.room.IRoomService;
import ch.teachu.teachu_admin.shared.room.RoomTablePageData;
import org.eclipse.scout.rt.client.dto.Data;
import org.eclipse.scout.rt.client.ui.action.menu.IMenu;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.basic.table.ITableRow;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.messagebox.IMessageBox;
import org.eclipse.scout.rt.client.ui.messagebox.MessageBoxes;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

import java.util.List;

@Data(RoomTablePageData.class)
public class RoomTablePage extends AbstractTablePage<Table> {
  @Override
  protected boolean getConfiguredLeaf() {
    return true;
  }

  @Override
  protected void execLoadData(SearchFilter filter) {
    importPageData(BEANS.get(IRoomService.class).getRoomTableData(filter));
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("Rooms");
  }

  public class Table extends AbstractTable {
    public IdColumn getIdColumn() {
      return getColumnSet().getColumnByClass(IdColumn.class);
    }

    public NameColumn getNameColumn() {
      return getColumnSet().getColumnByClass(NameColumn.class);
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
    public class NameColumn extends AbstractStringColumn {
      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("Name");
      }

      @Override
      protected int getConfiguredWidth() {
        return 300;
      }
    }

    @Order(1000)
    public class CreateMenu extends AbstractCreateMenu {

      @Override
      protected void execAction() {
        RoomForm roomForm = new RoomForm();
        roomForm.startNew();
        roomForm.waitFor();
        reloadPage();
      }
    }

    @Order(2000)
    public class EditMenu extends AbstractEditMenu {
      @Override
      protected void execAction() {
        RoomForm roomForm = new RoomForm();
        roomForm.setId(getIdColumn().getValue(getSelectedRow()));
        roomForm.startModify();
        roomForm.waitFor();
        reloadPage();
      }
    }

    @Order(3000)
    public class DeleteMenu extends AbstractDeleteMenu {

      @Override
      protected void execAction() {
        List<String> ids = getIdColumn().getValues(getSelectedRows());
        if (BEANS.get(DeletableMessageBoxHelper.class).checkAndShowMessageBox(IRoomCheckDeletable.class, ids)) {
          List<String> titles = getNameColumn().getValues(getSelectedRows());
          if (MessageBoxes.createDeleteConfirmationMessage(titles).show() == IMessageBox.YES_OPTION) {
            ids.forEach(BEANS.get(IRoomService.class)::delete);
            getSelectedRows().forEach(ITableRow::delete);
          }
        }
      }
    }
  }
}
