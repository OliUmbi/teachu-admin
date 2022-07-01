package ch.teachu.teachu_admin.client.schoolconfig;

import ch.teachu.teachu_admin.client.schoolconfig.SchoolConfigTablePage.Table;
import ch.teachu.teachu_admin.shared.schoolconfig.ISchoolConfigService;
import ch.teachu.teachu_admin.shared.schoolconfig.SchoolConfigCodeType;
import ch.teachu.teachu_admin.shared.schoolconfig.SchoolConfigTablePageData;
import org.eclipse.scout.rt.client.dto.Data;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.action.menu.IMenu;
import org.eclipse.scout.rt.client.ui.action.menu.IMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.TableMenuType;
import org.eclipse.scout.rt.client.ui.basic.cell.Cell;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.basic.table.ITableRow;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithTable;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.shared.services.common.code.AbstractCodeType;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

import java.lang.reflect.InvocationTargetException;
import java.util.Set;

@Data(SchoolConfigTablePageData.class)
public class SchoolConfigTablePage extends AbstractPageWithTable<Table> {
  @Override
  protected boolean getConfiguredLeaf() {
    return true;
  }

  @Override
  protected void execLoadData(SearchFilter filter) {
    importPageData(BEANS.get(ISchoolConfigService.class).getSchoolConfigTableData(filter));
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("Config");
  }

  public class Table extends AbstractTable {

    @Override
    protected Class<? extends IMenu> getConfiguredDefaultMenu() {
      return EditMenu.class;
    }

    public CodeTypeColumn getCodeTypeColumn() {
      return getColumnSet().getColumnByClass(CodeTypeColumn.class);
    }

    public ConfigNameColumn getConfigNameColumn() {
      return getColumnSet().getColumnByClass(ConfigNameColumn.class);
    }

    public ValueColumn getValueColumn() {
      return getColumnSet().getColumnByClass(ValueColumn.class);
    }

    @Order(0)
    public class CodeTypeColumn extends AbstractStringColumn {
      @Override
      protected boolean getConfiguredDisplayable() {
        return false;
      }
    }

    @Order(1000)
    public class ConfigNameColumn extends AbstractStringColumn {
      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("ConfigName");
      }

      @Override
      protected boolean getConfiguredAutoOptimizeWidth() {
        return true;
      }

      @Override
      protected void execDecorateCell(Cell cell, ITableRow row) {
        cell.setText(TEXTS.get(cell.getText()));
      }
    }

    @Order(2000)
    public class ValueColumn extends AbstractStringColumn {
      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("Value");
      }

      @Override
      protected boolean getConfiguredAutoOptimizeWidth() {
        return true;
      }

      @Override
      protected void execDecorateCell(Cell cell, ITableRow row) {
        String codeTypeName = getCodeTypeColumn().getValue(row);
        if (codeTypeName != null) {
          Class<? extends AbstractCodeType<String, String>> codeTypeClass = SchoolConfigCodeType.valueOf(codeTypeName.toUpperCase()).getCodeType();
          try {
            AbstractCodeType<String, String> codeType = codeTypeClass.getConstructor().newInstance();
            String newCellText = codeType.getCode(cell.getText()).getText();
            cell.setText(newCellText);
          } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                   NoSuchMethodException e) {
            throw new RuntimeException(e);
          }
        }
      }
    }

    @Order(1000)
    public class EditMenu extends AbstractMenu {
      @Override
      protected String getConfiguredText() {
        return TEXTS.get("Edit");
      }

      @Override
      protected Set<? extends IMenuType> getConfiguredMenuTypes() {
        return CollectionUtility.hashSet(TableMenuType.SingleSelection);
      }

      @Override
      protected void execAction() {
        SchoolConfigForm form = new SchoolConfigForm();
        form.getConfigNameField().setValue(getConfigNameColumn().getValue(getSelectedRow()));
        form.setCodeType(getCodeTypeColumn().getValue(getSelectedRow()));
        form.setValue(getValueColumn().getValue(getSelectedRow()));
        form.startModify();
        form.waitFor();
        reloadPage();
      }
    }
  }
}
