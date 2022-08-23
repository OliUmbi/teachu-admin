package ch.teachu.teachu_admin.client.lesson;

import ch.teachu.teachu_admin.client.lesson.LessonTablePage.Table;
import ch.teachu.teachu_admin.client.shared.AbstractCreateMenu;
import ch.teachu.teachu_admin.client.shared.AbstractDeleteMenu;
import ch.teachu.teachu_admin.client.shared.AbstractEditMenu;
import ch.teachu.teachu_admin.client.shared.AbstractTablePage;
import ch.teachu.teachu_admin.shared.AdminPermission;
import ch.teachu.teachu_admin.shared.lesson.ILessonService;
import ch.teachu.teachu_admin.shared.lesson.LessonTablePageData;
import ch.teachu.teachu_admin.shared.lesson.RoomLookupCall;
import ch.teachu.teachu_admin.shared.lesson.WeekdayCodeType;
import ch.teachu.teachu_admin.shared.schoolclass.subject.SchoolClassSubjectLookupCall;
import org.eclipse.scout.rt.client.dto.Data;
import org.eclipse.scout.rt.client.ui.action.menu.IMenu;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.basic.table.ITableRow;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractSmartColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractTimeColumn;
import org.eclipse.scout.rt.client.ui.messagebox.IMessageBox;
import org.eclipse.scout.rt.client.ui.messagebox.MessageBoxes;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.security.ACCESS;
import org.eclipse.scout.rt.shared.services.common.code.ICodeType;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;

import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data(LessonTablePageData.class)
public class LessonTablePage extends AbstractTablePage<Table> {

  private final String schoolClassId;

  public LessonTablePage(String schoolClassId) {
    this.schoolClassId = schoolClassId;
  }

  @Override
  protected boolean getConfiguredLeaf() {
    return true;
  }

  @Override
  protected void execLoadData(SearchFilter filter) {
    importPageData(BEANS.get(ILessonService.class).getLessonTableData(filter, schoolClassId));
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("Lesson");
  }

  public class Table extends AbstractTable {
    public EndTimeColumn getEndTimeColumn() {
      return getColumnSet().getColumnByClass(EndTimeColumn.class);
    }

    public IdColumn getIdColumn() {
      return getColumnSet().getColumnByClass(IdColumn.class);
    }

    public RoomColumn getRoomColumn() {
      return getColumnSet().getColumnByClass(RoomColumn.class);
    }

    public StartTimeColumn getStartTimeColumn() {
      return getColumnSet().getColumnByClass(StartTimeColumn.class);
    }

    public SubjectColumn getSubjectColumn() {
      return getColumnSet().getColumnByClass(SubjectColumn.class);
    }

    public WeekdayColumn getWeekdayColumn() {
      return getColumnSet().getColumnByClass(WeekdayColumn.class);
    }

    @Override
    protected Class<? extends IMenu> getConfiguredDefaultMenu() {
      return EditMenu.class;
    }

    @Order(0)
    public class IdColumn extends AbstractStringColumn {
      @Override
      protected boolean getConfiguredDisplayable() {
        return false;
      }
    }

    @Order(1000)
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
        return SchoolClassSubjectLookupCall.class;
      }

      @Override
      protected void execPrepareLookup(ILookupCall<String> call, ITableRow row) {
        ((SchoolClassSubjectLookupCall) call).setSchoolClassId(schoolClassId);
      }
    }

    @Order(2000)
    public class WeekdayColumn extends AbstractSmartColumn<String> {
      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("Weekday");
      }

      @Override
      protected int getConfiguredWidth() {
        return 200;
      }

      @Override
      protected Class<? extends ICodeType<?, String>> getConfiguredCodeType() {
        return WeekdayCodeType.class;
      }
    }

    @Order(3000)
    public class StartTimeColumn extends AbstractTimeColumn {
      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("FromDate");
      }

      @Override
      protected int getConfiguredWidth() {
        return 100;
      }
    }

    @Order(4000)
    public class EndTimeColumn extends AbstractTimeColumn {
      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("ToDate");
      }

      @Override
      protected int getConfiguredWidth() {
        return 100;
      }
    }

    @Order(5000)
    public class RoomColumn extends AbstractSmartColumn<String> {
      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("Room");
      }

      @Override
      protected int getConfiguredWidth() {
        return 300;
      }

      @Override
      protected Class<? extends ILookupCall<String>> getConfiguredLookupCall() {
        return RoomLookupCall.class;
      }
    }

    @Order(1000)
    public class CreateMenu extends AbstractCreateMenu {

      @Override
      protected void execAction() {
        LessonForm lessonForm = new LessonForm();
        lessonForm.setSchoolClassId(schoolClassId);
        lessonForm.startNew();
        lessonForm.waitFor();
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
        LessonForm lessonForm = new LessonForm();
        lessonForm.setId(getIdColumn().getValue(getSelectedRow()));
        lessonForm.setSchoolClassId(schoolClassId);
        lessonForm.startModify();
        lessonForm.waitFor();
        reloadPage();
      }
    }

    @Order(3000)
    public class DeleteMenu extends AbstractDeleteMenu {

      @Override
      protected void execAction() {
        if (MessageBoxes.createDeleteConfirmationMessage(getTitles()).show() == IMessageBox.YES_OPTION) {
          getIdColumn().getValues(getSelectedRows()).forEach(BEANS.get(ILessonService.class)::delete);
          getSelectedRows().forEach(ITableRow::delete);
        }
      }

      private List<String> getTitles() {
        return getSelectedRows().stream()
          .map(row -> getSubjectColumn().getDisplayText(row) + " (" +
            formatTime(getStartTimeColumn().getValue(row)) + " - " +
            formatTime(getEndTimeColumn().getValue(row)) + ")")
          .collect(Collectors.toList());
      }

      private String formatTime(Date time) {
        LocalTime localTime = LocalTime.ofInstant(time.toInstant(), ZoneId.systemDefault());
        return localTime.format(DateTimeFormatter.ofPattern("HH:mm"));
      }

      @Override
      protected boolean getConfiguredVisible() {
        return ACCESS.check(new AdminPermission());
      }
    }
  }
}
