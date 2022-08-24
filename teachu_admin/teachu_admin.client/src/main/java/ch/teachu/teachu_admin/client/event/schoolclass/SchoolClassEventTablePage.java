package ch.teachu.teachu_admin.client.event.schoolclass;

import ch.teachu.teachu_admin.client.event.schoolclass.SchoolClassEventTablePage.Table;
import ch.teachu.teachu_admin.client.shared.AbstractCreateMenu;
import ch.teachu.teachu_admin.client.shared.AbstractDeleteMenu;
import ch.teachu.teachu_admin.client.shared.AbstractEditMenu;
import ch.teachu.teachu_admin.shared.event.schoolclass.ISchoolClassEventService;
import ch.teachu.teachu_admin.shared.event.schoolclass.SchoolClassEventCodeType;
import ch.teachu.teachu_admin.shared.event.schoolclass.SchoolClassEventTablePageData;
import org.eclipse.scout.rt.client.dto.Data;
import org.eclipse.scout.rt.client.ui.action.menu.IMenu;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.basic.table.ITableRow;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractDateColumn;
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

@Data(SchoolClassEventTablePageData.class)
public class SchoolClassEventTablePage extends AbstractPageWithTable<Table> {

  private final String schoolClassId;

  public SchoolClassEventTablePage(String schoolClassId) {
    this.schoolClassId = schoolClassId;
  }

  @Override
  protected boolean getConfiguredLeaf() {
    return true;
  }

  @Override
  protected void execLoadData(SearchFilter filter) {
    importPageData(BEANS.get(ISchoolClassEventService.class).getSchoolClassEventTableData(filter, schoolClassId));
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("SchoolClassEvent");
  }

  public class Table extends AbstractTable {
    public DateFromColumn getDateFromColumn() {
      return getColumnSet().getColumnByClass(DateFromColumn.class);
    }

    public DateToColumn getDateToColumn() {
      return getColumnSet().getColumnByClass(DateToColumn.class);
    }

    public IdColumn getIdColumn() {
      return getColumnSet().getColumnByClass(IdColumn.class);
    }

    public TitleColumn getTitleColumn() {
      return getColumnSet().getColumnByClass(TitleColumn.class);
    }

    public TypeColumn getTypeColumn() {
      return getColumnSet().getColumnByClass(TypeColumn.class);
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
    public class TypeColumn extends AbstractSmartColumn<String> {
      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("Type");
      }

      @Override
      protected int getConfiguredWidth() {
        return 200;
      }

      @Override
      public Class<? extends ICodeType<?, String>> getConfiguredCodeType() {
        return SchoolClassEventCodeType.class;
      }
    }

    @Order(4000)
    public class DateFromColumn extends AbstractDateColumn {
      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("FromDate");
      }

      @Override
      protected int getConfiguredWidth() {
        return 200;
      }
    }

    @Order(5000)
    public class DateToColumn extends AbstractDateColumn {
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
        SchoolClassEventForm schoolClassEventForm = new SchoolClassEventForm();
        schoolClassEventForm.setSchoolClassId(schoolClassId);
        schoolClassEventForm.startNew();
        schoolClassEventForm.waitFor();
        reloadPage();
      }
    }

    @Order(2000)
    public class EditMenu extends AbstractEditMenu {
      @Override
      protected void execAction() {
        SchoolClassEventForm schoolClassEventForm = new SchoolClassEventForm();
        schoolClassEventForm.setId(getIdColumn().getValue(getSelectedRow()));
        schoolClassEventForm.setSchoolClassId(schoolClassId);
        schoolClassEventForm.startModify();
        schoolClassEventForm.waitFor();
        reloadPage();
      }
    }

    @Order(3000)
    public class DeleteMenu extends AbstractDeleteMenu {

      @Override
      protected void execAction() {
        List<String> titles = getTitleColumn().getValues(getSelectedRows());
        if (MessageBoxes.createDeleteConfirmationMessage(titles).show() == IMessageBox.YES_OPTION) {
          getIdColumn().getValues(getSelectedRows()).forEach(BEANS.get(ISchoolClassEventService.class)::delete);
          getSelectedRows().forEach(ITableRow::delete);
        }
      }
    }
  }
}
