package ch.teachu.teachu_admin.client.schoolclass;

import ch.teachu.teachu_admin.client.chatgroup.ChatGroupForm;
import ch.teachu.teachu_admin.client.schoolclass.SchoolClassTablePage.Table;
import ch.teachu.teachu_admin.client.shared.AbstractCreateMenu;
import ch.teachu.teachu_admin.client.shared.AbstractDeleteMenu;
import ch.teachu.teachu_admin.client.shared.AbstractEditMenu;
import ch.teachu.teachu_admin.client.shared.AbstractTablePage;
import ch.teachu.teachu_admin.shared.AdminPermission;
import ch.teachu.teachu_admin.shared.schoolclass.ISchoolClassService;
import ch.teachu.teachu_admin.shared.schoolclass.SchoolClassTablePageData;
import org.eclipse.scout.rt.client.dto.Data;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.action.menu.IMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.TableMenuType;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.basic.table.ITableRow;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.messagebox.IMessageBox;
import org.eclipse.scout.rt.client.ui.messagebox.MessageBoxes;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.security.ACCESS;

import java.util.List;
import java.util.Set;

@Data(SchoolClassTablePageData.class)
public class SchoolClassTablePage extends AbstractTablePage<Table> {

  @Override
  protected boolean getConfiguredLeaf() {
    return false;
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("SchoolClasses");
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
      protected int getConfiguredWidth() {
        return 300;
      }
    }

    @Order(2000)
    public class ClassTeacherFirstNameColumn extends AbstractStringColumn {
      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("ClassTeacherFirstName");
      }

      @Override
      protected int getConfiguredWidth() {
        return 200;
      }
    }

    @Order(3000)
    public class ClassTeacherLastNameColumn extends AbstractStringColumn {
      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("ClassTeacherLastName");
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
        SchoolClassForm schoolClassForm = new SchoolClassForm();
        schoolClassForm.startNew();
        schoolClassForm.waitFor();
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
        SchoolClassForm schoolClassForm = new SchoolClassForm();
        schoolClassForm.setId(getIdColumn().getValue(getSelectedRow()));
        schoolClassForm.startModify();
        schoolClassForm.waitFor();
        reloadPage();
      }
    }

    @Order(3000)
    public class DeleteMenu extends AbstractDeleteMenu {

      @Override
      protected void execAction() {
        List<String> titles = getNameColumn().getValues(getSelectedRows());
        if (MessageBoxes.createDeleteConfirmationMessage(titles).show() == IMessageBox.YES_OPTION) {
          getIdColumn().getValues(getSelectedRows()).forEach(BEANS.get(ISchoolClassService.class)::delete);
          getSelectedRows().forEach(ITableRow::delete);
        }
      }

      @Override
      protected boolean getConfiguredVisible() {
        return ACCESS.check(new AdminPermission());
      }
    }

    @Order(4000)
    public class CreateChatGroupMenu extends AbstractMenu {
      @Override
      protected String getConfiguredText() {
        return TEXTS.get("CreateChatGroup");
      }

      @Override
      protected Set<? extends IMenuType> getConfiguredMenuTypes() {
        return CollectionUtility.hashSet(CollectionUtility.hashSet(TableMenuType.SingleSelection));
      }

      @Override
      protected void execAction() {
        new ChatGroupForm().startNew(BEANS.get(ISchoolClassService.class).getCreateChatGroupFormData(getIdColumn().getValue(getSelectedRow())));
      }
    }
  }
}
