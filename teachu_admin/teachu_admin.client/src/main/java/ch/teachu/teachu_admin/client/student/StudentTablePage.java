package ch.teachu.teachu_admin.client.student;

import ch.teachu.teachu_admin.client.event.user.UserEventTablePage;
import ch.teachu.teachu_admin.client.student.StudentTablePage.Table;
import ch.teachu.teachu_admin.shared.student.IStudentService;
import ch.teachu.teachu_admin.shared.student.StudentTablePageData;
import org.eclipse.scout.rt.client.dto.Data;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.basic.table.ITableRow;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithTable;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.IPage;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

@Data(StudentTablePageData.class)
public class StudentTablePage extends AbstractPageWithTable<Table> {

  @Override
  protected void execLoadData(SearchFilter filter) {
    importPageData(BEANS.get(IStudentService.class).getStudentTableData(filter));
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("Students");
  }

  @Override
  protected IPage<?> execCreateChildPage(ITableRow row) {
    return new UserEventTablePage(getTable().getIdColumn().getValue(row));
  }

  public class Table extends AbstractTable {
    public FirstNameColumn getFirstNameColumn() {
      return getColumnSet().getColumnByClass(FirstNameColumn.class);
    }

    public IdColumn getIdColumn() {
      return getColumnSet().getColumnByClass(IdColumn.class);
    }

    public LastNameColumn getLastNameColumn() {
      return getColumnSet().getColumnByClass(LastNameColumn.class);
    }

    @Order(1000)
    public class IdColumn extends AbstractStringColumn {
      @Override
      protected boolean getConfiguredDisplayable() {
        return false;
      }
    }

    @Order(2000)
    public class FirstNameColumn extends AbstractStringColumn {
      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("FirstName");
      }

      @Override
      protected int getConfiguredWidth() {
        return 300;
      }
    }

    @Order(3000)
    public class LastNameColumn extends AbstractStringColumn {
      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("Lastname");
      }

      @Override
      protected int getConfiguredWidth() {
        return 300;
      }
    }
  }
}
