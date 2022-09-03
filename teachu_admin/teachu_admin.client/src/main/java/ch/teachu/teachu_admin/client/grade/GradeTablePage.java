package ch.teachu.teachu_admin.client.grade;

import ch.teachu.teachu_admin.client.grade.GradeTablePage.Table;
import ch.teachu.teachu_admin.client.shared.AbstractCreateMenu;
import ch.teachu.teachu_admin.client.shared.AbstractDeleteMenu;
import ch.teachu.teachu_admin.client.shared.AbstractEditMenu;
import ch.teachu.teachu_admin.client.shared.AbstractTablePage;
import ch.teachu.teachu_admin.shared.grade.GradeTablePageData;
import ch.teachu.teachu_admin.shared.grade.IGradeService;
import org.eclipse.scout.rt.client.dto.Data;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.basic.table.ITableRow;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractBigDecimalColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.messagebox.IMessageBox;
import org.eclipse.scout.rt.client.ui.messagebox.MessageBoxes;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

import java.util.List;
import java.util.stream.Collectors;

@Data(GradeTablePageData.class)
public class GradeTablePage extends AbstractTablePage<Table> {

  private final String examId;

  public GradeTablePage(String examId) {
    this.examId = examId;
  }

  @Override
  protected boolean getConfiguredLeaf() {
    return true;
  }

  @Override
  protected void execLoadData(SearchFilter filter) {
    importPageData(BEANS.get(IGradeService.class).getGradeTableData(filter, examId));
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("Grades");
  }

  public class Table extends AbstractTable {

    public IdColumn getIdColumn() {
      return getColumnSet().getColumnByClass(IdColumn.class);
    }

    public MarkColumn getMarkColumn() {
      return getColumnSet().getColumnByClass(MarkColumn.class);
    }

    public StudentFirstNameColumn getStudentFirstNameColumn() {
      return getColumnSet().getColumnByClass(StudentFirstNameColumn.class);
    }

    public StudentLastNameColumn getStudentLastNameColumn() {
      return getColumnSet().getColumnByClass(StudentLastNameColumn.class);
    }

    @Order(1000)
    public class IdColumn extends AbstractStringColumn {
      @Override
      protected boolean getConfiguredDisplayable() {
        return false;
      }
    }

    @Order(2000)
    public class StudentFirstNameColumn extends AbstractStringColumn {
      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("StudentFirstName");
      }

      @Override
      protected int getConfiguredWidth() {
        return 300;
      }
    }

    @Order(3000)
    public class StudentLastNameColumn extends AbstractStringColumn {
      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("StudentLastName");
      }

      @Override
      protected int getConfiguredWidth() {
        return 300;
      }
    }

    @Order(4000)
    public class MarkColumn extends AbstractBigDecimalColumn {
      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("Mark");
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
        GradeForm gradeForm = new GradeForm();
        gradeForm.setExamId(examId);
        gradeForm.startNew();
        gradeForm.waitFor();
        reloadPage();
      }
    }

    @Order(2000)
    public class EditMenu extends AbstractEditMenu {
      @Override
      protected void execAction() {
        GradeForm gradeForm = new GradeForm();
        gradeForm.setId(getIdColumn().getValue(getSelectedRow()));
        gradeForm.setExamId(examId);
        gradeForm.startModify();
        gradeForm.waitFor();
        reloadPage();
      }
    }

    @Order(3000)
    public class DeleteMenu extends AbstractDeleteMenu {

      @Override
      protected void execAction() {
        List<String> titles = getNames();
        if (MessageBoxes.createDeleteConfirmationMessage(titles).show() == IMessageBox.YES_OPTION) {
          getIdColumn().getValues(getSelectedRows()).forEach(BEANS.get(IGradeService.class)::delete);
          getSelectedRows().forEach(ITableRow::delete);
        }
      }

      private List<String> getNames() {
        return getSelectedRows().stream()
          .map(row -> getStudentFirstNameColumn().getValue(row) + ' ' + getStudentLastNameColumn().getValue(row))
          .collect(Collectors.toList());
      }
    }
  }
}
