package ch.teachu.teachu_admin.client.schoolinfo;

import ch.teachu.teachu_admin.client.schoolinfo.SchoolInfoTablePage.Table;
import ch.teachu.teachu_admin.client.shared.AbstractCreateMenu;
import ch.teachu.teachu_admin.client.shared.AbstractDeleteMenu;
import ch.teachu.teachu_admin.client.shared.AbstractEditMenu;
import ch.teachu.teachu_admin.client.shared.AbstractTablePage;
import ch.teachu.teachu_admin.shared.schoolinfo.ISchoolInfoService;
import ch.teachu.teachu_admin.shared.schoolinfo.SchoolInfoTablePageData;
import org.eclipse.scout.rt.client.dto.Data;
import org.eclipse.scout.rt.client.ui.action.menu.IMenu;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.basic.table.ITableRow;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractDateColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.messagebox.IMessageBox;
import org.eclipse.scout.rt.client.ui.messagebox.MessageBoxes;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

import java.util.List;

@Data(SchoolInfoTablePageData.class)
public class SchoolInfoTablePage extends AbstractTablePage<Table> {
  @Override
  protected boolean getConfiguredLeaf() {
    return true;
  }

  @Override
  protected void execLoadData(SearchFilter filter) {
    importPageData(BEANS.get(ISchoolInfoService.class).getSchoolInfoTableData(filter));
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("SchoolInfo");
  }

  public class Table extends AbstractTable {

    public CreatorColumn getCreatorColumn() {
      return getColumnSet().getColumnByClass(CreatorColumn.class);
    }

    public IdColumn getIdColumn() {
      return getColumnSet().getColumnByClass(IdColumn.class);
    }

    public TitleColumn getTitleColumn() {
      return getColumnSet().getColumnByClass(TitleColumn.class);
    }

    @Override
    protected Class<? extends IMenu> getConfiguredDefaultMenu() {
      return EditMenu.class;
    }

    @Order(0)
    public class IdColumn extends AbstractStringColumn {
      @Override
      protected boolean getConfiguredDisplayable() {
        return false;
      }
    }

    @Order(1000)
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

    @Order(2000)
    public class DateColumn extends AbstractDateColumn {
      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("Date");
      }

      @Override
      protected int getConfiguredWidth() {
        return 100;
      }
    }

    @Order(3000)
    public class CreatorColumn extends AbstractStringColumn {
      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("Creator");
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
        SchoolInfoForm schoolInfoForm = new SchoolInfoForm();
        schoolInfoForm.startNew();
        schoolInfoForm.waitFor();
        reloadPage();
      }
    }

    @Order(2000)
    public class EditMenu extends AbstractEditMenu {
      @Override
      protected void execAction() {
        SchoolInfoForm schoolInfoForm = new SchoolInfoForm();
        schoolInfoForm.setId(getIdColumn().getValue(getSelectedRow()));
        schoolInfoForm.startModify();
        schoolInfoForm.waitFor();
        reloadPage();
      }
    }

    @Order(3000)
    public class DeleteMenu extends AbstractDeleteMenu {

      @Override
      protected void execAction() {
        List<String> titles = getTitleColumn().getValues(getSelectedRows());
        if (MessageBoxes.createDeleteConfirmationMessage(titles).show() == IMessageBox.YES_OPTION) {
          getIdColumn().getValues(getSelectedRows()).forEach(BEANS.get(ISchoolInfoService.class)::delete);
          getSelectedRows().forEach(ITableRow::delete);
        }
      }
    }
  }
}
