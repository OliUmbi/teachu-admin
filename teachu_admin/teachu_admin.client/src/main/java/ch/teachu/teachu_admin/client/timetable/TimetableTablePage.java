package ch.teachu.teachu_admin.client.timetable;

import ch.teachu.teachu_admin.client.shared.AbstractCreateMenu;
import ch.teachu.teachu_admin.client.shared.AbstractDeleteMenu;
import ch.teachu.teachu_admin.client.shared.AbstractEditMenu;
import ch.teachu.teachu_admin.client.timetable.TimetableTablePage.Table;
import ch.teachu.teachu_admin.shared.timetable.ITimetableService;
import ch.teachu.teachu_admin.shared.timetable.TimetableTablePageData;
import org.eclipse.scout.rt.client.dto.Data;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.basic.table.ITableRow;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractTimeColumn;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithTable;
import org.eclipse.scout.rt.client.ui.messagebox.IMessageBox;
import org.eclipse.scout.rt.client.ui.messagebox.MessageBoxes;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data(TimetableTablePageData.class)
public class TimetableTablePage extends AbstractPageWithTable<Table> {
  @Override
  protected boolean getConfiguredLeaf() {
    return true;
  }

  @Override
  protected void execLoadData(SearchFilter filter) {
    importPageData(BEANS.get(ITimetableService.class).getTimetableTableData(filter));
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("Timetable");
  }

  public class Table extends AbstractTable {
    public FromColumn getFromColumn() {
      return getColumnSet().getColumnByClass(FromColumn.class);
    }

    public IdColumn getIdColumn() {
      return getColumnSet().getColumnByClass(IdColumn.class);
    }

    public ToColumn getToColumn() {
      return getColumnSet().getColumnByClass(ToColumn.class);
    }

    @Order(1000)
    public class IdColumn extends AbstractStringColumn {
      @Override
      protected boolean getConfiguredDisplayable() {
        return false;
      }
    }

    @Order(2000)
    public class FromColumn extends AbstractTimeColumn {
      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("FromDate");
      }

      @Override
      protected int getConfiguredWidth() {
        return 100;
      }
    }

    @Order(3000)
    public class ToColumn extends AbstractTimeColumn {
      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("ToDate");
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
        TimetableForm timetableForm = new TimetableForm();
        timetableForm.startNew();
        timetableForm.waitFor();
        reloadPage();
      }
    }

    @Order(2000)
    public class EditMenu extends AbstractEditMenu {
      @Override
      protected void execAction() {
        TimetableForm timetableForm = new TimetableForm();
        timetableForm.setId(getIdColumn().getValue(getSelectedRow()));
        timetableForm.startModify();
        timetableForm.waitFor();
        reloadPage();
      }
    }

    @Order(3000)
    public class DeleteMenu extends AbstractDeleteMenu {

      @Override
      protected void execAction() {
        List<String> titles = getTitles();
        if (MessageBoxes.createDeleteConfirmationMessage(titles).show() == IMessageBox.YES_OPTION) {
          getIdColumn().getValues(getSelectedRows()).forEach(BEANS.get(ITimetableService.class)::delete);
          getSelectedRows().forEach(ITableRow::delete);
        }
      }
    }

    private List<String> getTitles() {
      return getSelectedRows().stream()
        .map(row -> formatTime(getFromColumn().getValue(row)) + ' ' + formatTime(getToColumn().getValue(row)))
        .collect(Collectors.toList());
    }

    private String formatTime(Date time) {
      LocalTime localTime = LocalTime.ofInstant(time.toInstant(), ZoneId.systemDefault());
      return localTime.format(DateTimeFormatter.ofPattern("HH:mm"));
    }
  }
}
