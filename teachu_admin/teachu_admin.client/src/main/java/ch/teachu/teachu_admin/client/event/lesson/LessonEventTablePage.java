package ch.teachu.teachu_admin.client.event.lesson;

import ch.teachu.teachu_admin.client.event.lesson.LessonEventTablePage.Table;
import ch.teachu.teachu_admin.client.shared.AbstractCreateMenu;
import ch.teachu.teachu_admin.client.shared.AbstractDeleteMenu;
import ch.teachu.teachu_admin.client.shared.AbstractEditMenu;
import ch.teachu.teachu_admin.client.shared.AbstractTablePage;
import ch.teachu.teachu_admin.shared.event.lesson.ILessonEventService;
import ch.teachu.teachu_admin.shared.event.lesson.LessonEventCodeType;
import ch.teachu.teachu_admin.shared.event.lesson.LessonEventTablePageData;
import org.eclipse.scout.rt.client.dto.Data;
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
import org.eclipse.scout.rt.shared.services.common.code.ICodeType;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

import java.util.List;

@Data(LessonEventTablePageData.class)
public class LessonEventTablePage extends AbstractTablePage<Table> {

  private final String lessonId;

  public LessonEventTablePage(String lessonId) {
    this.lessonId = lessonId;
  }

  @Override
  protected boolean getConfiguredLeaf() {
    return true;
  }

  @Override
  protected void execLoadData(SearchFilter filter) {
    importPageData(BEANS.get(ILessonEventService.class).getLessonEventTableData(filter, lessonId));
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("LessonEvent");
  }

  public class Table extends AbstractTable {

    public DateColumn getDateColumn() {
      return getColumnSet().getColumnByClass(DateColumn.class);
    }

    public IdColumn getIdColumn() {
      return getColumnSet().getColumnByClass(IdColumn.class);
    }

    public LessonEventTypeColumn getLessonEventTypeColumn() {
      return getColumnSet().getColumnByClass(LessonEventTypeColumn.class);
    }

    public TitleColumn getTitleColumn() {
      return getColumnSet().getColumnByClass(TitleColumn.class);
    }

    @Order(1000)
    public class IdColumn extends AbstractStringColumn {
      @Override
      protected boolean getConfiguredDisplayable() {
        return false;
      }
    }

    @Order(1500)
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
        return 200;
      }
    }

    @Order(3000)
    public class LessonEventTypeColumn extends AbstractSmartColumn<String> {
      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("Type");
      }

      @Override
      protected int getConfiguredWidth() {
        return 200;
      }

      @Override
      protected Class<? extends ICodeType<?, String>> getConfiguredCodeType() {
        return LessonEventCodeType.class;
      }
    }

    @Order(1000)
    public class CreateMenu extends AbstractCreateMenu {

      @Override
      protected void execAction() {
        LessonEventForm lessonEventForm = new LessonEventForm();
        lessonEventForm.setLessonId(lessonId);
        lessonEventForm.startNew();
        lessonEventForm.waitFor();
        reloadPage();
      }
    }

    @Order(2000)
    public class EditMenu extends AbstractEditMenu {
      @Override
      protected void execAction() {
        LessonEventForm lessonEventForm = new LessonEventForm();
        lessonEventForm.setId(getIdColumn().getValue(getSelectedRow()));
        lessonEventForm.setLessonId(lessonId);
        lessonEventForm.startModify();
        lessonEventForm.waitFor();
        reloadPage();
      }
    }

    @Order(3000)
    public class DeleteMenu extends AbstractDeleteMenu {

      @Override
      protected void execAction() {
        List<String> titles = getTitleColumn().getValues(getSelectedRows());
        if (MessageBoxes.createDeleteConfirmationMessage(titles).show() == IMessageBox.YES_OPTION) {
          getIdColumn().getValues(getSelectedRows()).forEach(BEANS.get(ILessonEventService.class)::delete);
          getSelectedRows().forEach(ITableRow::delete);
        }
      }
    }
  }
}
