package ch.teachu.teachu_admin.client.schoolclass;

import ch.teachu.teachu_admin.client.schoolclass.SchoolClassTablePage.Table;
import ch.teachu.teachu_admin.shared.schoolclass.ISchoolClassService;
import ch.teachu.teachu_admin.shared.schoolclass.SchoolClassTablePageData;
import org.eclipse.scout.rt.client.dto.Data;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithTable;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

@Data(SchoolClassTablePageData.class)
public class SchoolClassTablePage extends AbstractPageWithTable<Table> {

  @Override
  protected boolean getConfiguredLeaf() {
    return false;
  }

  @Override
  protected void execLoadData(SearchFilter filter) {
    importPageData(BEANS.get(ISchoolClassService.class).getSchoolClassTableData(filter));
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("SchoolClass") + " WIP";
  }

  public class Table extends AbstractTable {

    public ClassTeacherLastNameColumn getClassTeacherLastNameColumn() {
      return getColumnSet().getColumnByClass(ClassTeacherLastNameColumn.class);
    }

    public IdColumn getIdColumn() {
      return getColumnSet().getColumnByClass(IdColumn.class);
    }

    public NameColumn getNameColumn() {
      return getColumnSet().getColumnByClass(NameColumn.class);
    }

    public ClassTeacherFirstNameColumn getTeacherColumn() {
      return getColumnSet().getColumnByClass(ClassTeacherFirstNameColumn.class);
    }

    @Order(0)
    public class IdColumn extends AbstractColumn<String> {
      @Override
      protected boolean getConfiguredDisplayable() {
        return false;
      }
    }

    @Order(1000)
    public class NameColumn extends AbstractStringColumn {
      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("Name");
      }

      @Override
      protected boolean getConfiguredAutoOptimizeWidth() {
        return true;
      }
    }

    @Order(2000)
    public class ClassTeacherFirstNameColumn extends AbstractStringColumn {
      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("ClassTeacherFirstName");
      }

      @Override
      protected boolean getConfiguredAutoOptimizeWidth() {
        return true;
      }
    }

    @Order(3000)
    public class ClassTeacherLastNameColumn extends AbstractStringColumn {
      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("ClassTeacherLastName");
      }

      @Override
      protected boolean getConfiguredAutoOptimizeWidth() {
        return true;
      }
    }
  }
}
