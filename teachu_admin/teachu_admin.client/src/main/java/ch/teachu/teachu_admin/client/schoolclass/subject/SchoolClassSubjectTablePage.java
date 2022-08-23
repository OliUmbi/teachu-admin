package ch.teachu.teachu_admin.client.schoolclass.subject;

import ch.teachu.teachu_admin.client.schoolclass.subject.SchoolClassSubjectTablePage.Table;
import ch.teachu.teachu_admin.client.shared.*;
import ch.teachu.teachu_admin.shared.AdminPermission;
import ch.teachu.teachu_admin.shared.lesson.SubjectLookupCall;
import ch.teachu.teachu_admin.shared.schoolclass.subject.ISchoolClassSubjectCheckDeletable;
import ch.teachu.teachu_admin.shared.schoolclass.subject.ISchoolClassSubjectService;
import ch.teachu.teachu_admin.shared.schoolclass.subject.SchoolClassSubjectTablePageData;
import org.eclipse.scout.rt.client.dto.Data;
import org.eclipse.scout.rt.client.ui.action.menu.IMenu;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.basic.table.ITableRow;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractDateColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractSmartColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.messagebox.IMessageBox;
import org.eclipse.scout.rt.client.ui.messagebox.MessageBoxes;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.security.ACCESS;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;

import java.util.List;

@Data(SchoolClassSubjectTablePageData.class)
public class SchoolClassSubjectTablePage extends AbstractTablePage<Table> {

  private final String schoolClassId;

  public SchoolClassSubjectTablePage(String schoolClassId) {
    this.schoolClassId = schoolClassId;
  }

  @Override
  protected boolean getConfiguredLeaf() {
    return true;
  }

  @Override
  protected void execLoadData(SearchFilter filter) {
    importPageData(BEANS.get(ISchoolClassSubjectService.class).getSchoolClassSubjectTableData(filter, schoolClassId));
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("SchoolClassSubject");
  }

  public class Table extends AbstractTable {
    public EndDateColumn getEndDateColumn() {
      return getColumnSet().getColumnByClass(EndDateColumn.class);
    }

    public IdColumn getIdColumn() {
      return getColumnSet().getColumnByClass(IdColumn.class);
    }

    public StartDateColumn getStartDateColumn() {
      return getColumnSet().getColumnByClass(StartDateColumn.class);
    }

    public SubjectColumn getSubjectColumn() {
      return getColumnSet().getColumnByClass(SubjectColumn.class);
    }

    public TeacherFirstNameColumn getTeacherFirstNameColumn() {
      return getColumnSet().getColumnByClass(TeacherFirstNameColumn.class);
    }

    public TeacherLastNameColumn getTeacherLastNameColumn() {
      return getColumnSet().getColumnByClass(TeacherLastNameColumn.class);
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
    public class SubjectColumn extends AbstractSmartColumn<String> {
      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("ClassSubject");
      }

      @Override
      protected int getConfiguredWidth() {
        return 200;
      }

      @Override
      protected Class<? extends ILookupCall<String>> getConfiguredLookupCall() {
        return SubjectLookupCall.class;
      }
    }

    @Order(3000)
    public class TeacherFirstNameColumn extends AbstractStringColumn {
      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("TeacherFirstName");
      }

      @Override
      protected int getConfiguredWidth() {
        return 300;
      }
    }

    @Order(4000)
    public class TeacherLastNameColumn extends AbstractStringColumn {
      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("TeacherLastName");
      }

      @Override
      protected int getConfiguredWidth() {
        return 300;
      }
    }

    @Order(5000)
    public class StartDateColumn extends AbstractDateColumn {
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
    public class EndDateColumn extends AbstractDateColumn {
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
        SchoolClassSubjectForm schoolClassSubjectForm = new SchoolClassSubjectForm();
        schoolClassSubjectForm.setSchoolClassId(schoolClassId);
        schoolClassSubjectForm.startNew();
        schoolClassSubjectForm.waitFor();
        reloadPage();
      }

      @Override
      protected boolean getConfiguredVisible() {
        return ACCESS.check(new AdminPermission());
      }
    }

    @Order(2000)
    public class EditMenu extends AbstractEditMenu {
      @Override
      protected void execAction() {
        SchoolClassSubjectForm schoolClassSubjectForm = new SchoolClassSubjectForm();
        schoolClassSubjectForm.setId(getIdColumn().getValue(getSelectedRow()));
        schoolClassSubjectForm.setSchoolClassId(getIdColumn().getValue(getSelectedRow()));
        schoolClassSubjectForm.startModify();
        schoolClassSubjectForm.waitFor();
        reloadPage();
      }
    }

    @Order(3000)
    public class DeleteMenu extends AbstractDeleteMenu {

      @Override
      protected void execAction() {
        List<String> ids = getIdColumn().getValues(getSelectedRows());
        if (BEANS.get(DeletableMessageBoxHelper.class).checkAndShowMessageBox(ISchoolClassSubjectCheckDeletable.class, ids)) {
          List<String> titles = getSubjectColumn().getSelectedDisplayTexts();
          if (MessageBoxes.createDeleteConfirmationMessage(titles).show() == IMessageBox.YES_OPTION) {
            ids.forEach(BEANS.get(ISchoolClassSubjectService.class)::delete);
            getSelectedRows().forEach(ITableRow::delete);
          }
        }
      }

      @Override
      protected boolean getConfiguredVisible() {
        return ACCESS.check(new AdminPermission());
      }
    }
  }
}
