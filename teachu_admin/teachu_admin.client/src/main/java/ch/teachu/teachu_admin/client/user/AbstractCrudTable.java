package ch.teachu.teachu_admin.client.user;

import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.action.menu.IMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.TableMenuType;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.CollectionUtility;

import java.util.Set;

public abstract class AbstractCrudTable extends AbstractTable {

  @Order(1000)
  public class AddMenu extends AbstractMenu {
    @Override
    protected String getConfiguredText() {
      return TEXTS.get("Add");
    }

    @Override
    protected Set<? extends IMenuType> getConfiguredMenuTypes() {
      return org.eclipse.scout.rt.platform.util.CollectionUtility.hashSet(TableMenuType.EmptySpace);
    }

    @Override
    protected void execAction() {
      addRow();
    }
  }

  @Order(2000)
  public class RemoveMenu extends AbstractMenu {
    @Override
    protected String getConfiguredText() {
      return TEXTS.get("Remove");
    }

    @Override
    protected Set<? extends IMenuType> getConfiguredMenuTypes() {
      return CollectionUtility.hashSet(TableMenuType.SingleSelection, TableMenuType.MultiSelection);
    }

    @Override
    protected void execAction() {
      deleteRows(getSelectedRows());
    }
  }
}
