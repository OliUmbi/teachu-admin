package ch.teachu.teachu_admin.client.subject;

import ch.teachu.teachu_admin.client.shared.AbstractCreateMenu;
import ch.teachu.teachu_admin.client.shared.AbstractDeleteMenu;
import ch.teachu.teachu_admin.client.shared.AbstractEditMenu;
import ch.teachu.teachu_admin.client.subject.SubjectTablePage.Table;
import ch.teachu.teachu_admin.shared.subject.ISubjectService;
import ch.teachu.teachu_admin.shared.subject.SubjectTablePageData;
import org.eclipse.scout.rt.client.dto.Data;
import org.eclipse.scout.rt.client.ui.action.menu.IMenu;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.basic.table.ITableRow;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractBigDecimalColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithTable;
import org.eclipse.scout.rt.client.ui.messagebox.IMessageBox;
import org.eclipse.scout.rt.client.ui.messagebox.MessageBoxes;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

import java.util.List;

@Data(SubjectTablePageData.class)
public class SubjectTablePage extends AbstractPageWithTable<Table> {
  @Override
  protected boolean getConfiguredLeaf() {
    return true;
  }

  @Override
  protected void execLoadData(SearchFilter filter) {
    importPageData(BEANS.get(ISubjectService.class).getSubjectTableData(filter));
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("ClassSubjects");
  }

  public class Table extends AbstractTable {

    public IdColumn getIdColumn() {
      return getColumnSet().getColumnByClass(IdColumn.class);
    }

    public SubjectColumn getSubjectColumn() {
      return getColumnSet().getColumnByClass(SubjectColumn.class);
    }

    public WeightColumn getWeightColumn() {
      return getColumnSet().getColumnByClass(WeightColumn.class);
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
    public class SubjectColumn extends AbstractStringColumn {
      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("Name");
      }

      @Override
      protected int getConfiguredWidth() {
        return 300;
      }
    }

    @Order(3000)
    public class WeightColumn extends AbstractBigDecimalColumn {
      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("Weight");
      }

      @Override
      protected int getConfiguredWidth() {
        return 100;
      }
    }

    @Order(1000)
    public class CreateMenu extends AbstractCreateMenu {

      @Override
      protected void execAction() {
        SubjectForm subjectForm = new SubjectForm();
        subjectForm.startNew();
        subjectForm.waitFor();
        reloadPage();
      }
    }

    @Order(2000)
    public class EditMenu extends AbstractEditMenu {
      @Override
      protected void execAction() {
        SubjectForm subjectForm = new SubjectForm();
        subjectForm.setId(getIdColumn().getValue(getSelectedRow()));
        subjectForm.startModify();
        subjectForm.waitFor();
        reloadPage();
      }
    }

    @Order(3000)
    public class DeleteMenu extends AbstractDeleteMenu {

      @Override
      protected void execAction() {
        List<String> titles = getSubjectColumn().getValues(getSelectedRows());
        if (MessageBoxes.createDeleteConfirmationMessage(titles).show() == IMessageBox.YES_OPTION) {
          getIdColumn().getValues(getSelectedRows()).forEach(BEANS.get(ISubjectService.class)::delete);
          getSelectedRows().forEach(ITableRow::delete);
        }
      }
    }
  }
}
