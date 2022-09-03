package ch.teachu.teachu_admin.client.exam;

import ch.teachu.teachu_admin.client.exam.ExamTablePage.Table;
import ch.teachu.teachu_admin.client.grade.GradeTablePage;
import ch.teachu.teachu_admin.client.shared.AbstractCreateMenu;
import ch.teachu.teachu_admin.client.shared.AbstractDeleteMenu;
import ch.teachu.teachu_admin.client.shared.AbstractEditMenu;
import ch.teachu.teachu_admin.client.shared.AbstractTablePage;
import ch.teachu.teachu_admin.shared.exam.ExamTablePageData;
import ch.teachu.teachu_admin.shared.exam.IExamService;
import ch.teachu.teachu_admin.shared.schoolclass.SchoolClassLookupCall;
import ch.teachu.teachu_admin.shared.schoolclass.subject.SchoolClassSubjectLookupCall;
import org.eclipse.scout.rt.client.dto.Data;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.basic.table.ITableRow;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractBigDecimalColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractDateColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractSmartColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.IPage;
import org.eclipse.scout.rt.client.ui.messagebox.IMessageBox;
import org.eclipse.scout.rt.client.ui.messagebox.MessageBoxes;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;

import java.util.List;

@Data(ExamTablePageData.class)
public class ExamTablePage extends AbstractTablePage<Table> {

  private final String schoolClassSubjectId;

  public ExamTablePage() {
    this(null);
  }

  public ExamTablePage(String schoolClassSubjectId) {
    this.schoolClassSubjectId = schoolClassSubjectId;
  }

  @Override
  protected void execLoadData(SearchFilter filter) {
    importPageData(BEANS.get(IExamService.class).getExamTableData(filter, schoolClassSubjectId));
  }

  @Override
  protected IPage<?> execCreateChildPage(ITableRow row) {
    return new GradeTablePage(getTable().getIdColumn().getValue(row));
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("Exam");
  }

  public class Table extends AbstractTable {

    public DateColumn getDateColumn() {
      return getColumnSet().getColumnByClass(DateColumn.class);
    }

    public IdColumn getIdColumn() {
      return getColumnSet().getColumnByClass(IdColumn.class);
    }

    public NameColumn getNameColumn() {
      return getColumnSet().getColumnByClass(NameColumn.class);
    }

    public SchoolClassColumn getSchoolClassColumn() {
      return getColumnSet().getColumnByClass(SchoolClassColumn.class);
    }

    public SchoolClassSubjectColumn getSchoolClassSubjectColumn() {
      return getColumnSet().getColumnByClass(SchoolClassSubjectColumn.class);
    }

    public ViewDateColumn getViewDateColumn() {
      return getColumnSet().getColumnByClass(ViewDateColumn.class);
    }

    public WeightColumn getWeightColumn() {
      return getColumnSet().getColumnByClass(WeightColumn.class);
    }

    @Order(0)
    public class IdColumn extends AbstractStringColumn {
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
      protected int getConfiguredWidth() {
        return 300;
      }
    }

    @Order(2000)
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

    @Order(3000)
    public class DateColumn extends AbstractDateColumn {
      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("Date");
      }

      @Override
      protected int getConfiguredWidth() {
        return 200;
      }
    }

    @Order(4000)
    public class ViewDateColumn extends AbstractDateColumn {
      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("ViewDate");
      }

      @Override
      protected int getConfiguredWidth() {
        return 200;
      }
    }

    @Order(5000)
    public class SchoolClassColumn extends AbstractSmartColumn<String> {
      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("SchoolClass");
      }

      @Override
      protected int getConfiguredWidth() {
        return 300;
      }

      @Override
      protected boolean getConfiguredVisible() {
        return schoolClassSubjectId == null;
      }

      @Override
      protected Class<? extends ILookupCall<String>> getConfiguredLookupCall() {
        return SchoolClassLookupCall.class;
      }
    }

    @Order(6000)
    public class SchoolClassSubjectColumn extends AbstractSmartColumn<String> {
      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("SchoolClassSubject");
      }

      @Override
      protected int getConfiguredWidth() {
        return 300;
      }

      @Override
      protected boolean getConfiguredVisible() {
        return schoolClassSubjectId == null;
      }

      @Override
      protected Class<? extends ILookupCall<String>> getConfiguredLookupCall() {
        return SchoolClassSubjectLookupCall.class;
      }

      @Override
      protected void execPrepareLookup(ILookupCall<String> call, ITableRow row) {
        ((SchoolClassSubjectLookupCall) call).setSchoolClassId(getSchoolClassColumn().getValue(row));
      }
    }

    @Order(1000)
    public class CreateMenu extends AbstractCreateMenu {

      @Override
      protected void execAction() {
        ExamForm examForm = new ExamForm();
        examForm.setSchoolClassSubjectId(schoolClassSubjectId);
        examForm.startNew();
        examForm.waitFor();
        reloadPage();
      }
    }

    @Order(2000)
    public class EditMenu extends AbstractEditMenu {
      @Override
      protected void execAction() {
        ExamForm examForm = new ExamForm();
        examForm.setId(getIdColumn().getValue(getSelectedRow()));
        examForm.setSchoolClassSubjectId(schoolClassSubjectId);
        examForm.startModify();
        examForm.waitFor();
        reloadPage();
      }
    }

    @Order(3000)
    public class DeleteMenu extends AbstractDeleteMenu {

      @Override
      protected void execAction() {
        List<String> titles = getNameColumn().getValues(getSelectedRows());
        if (MessageBoxes.createDeleteConfirmationMessage(titles).show() == IMessageBox.YES_OPTION) {
          getIdColumn().getValues(getSelectedRows()).forEach(BEANS.get(IExamService.class)::delete);
          getSelectedRows().forEach(ITableRow::delete);
        }
      }
    }
  }
}
