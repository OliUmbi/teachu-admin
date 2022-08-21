package ch.teachu.teachu_admin.client.shared;

import org.eclipse.scout.rt.client.ui.action.menu.IMenu;
import org.eclipse.scout.rt.client.ui.basic.table.ITable;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithTable;

public abstract class AbstractTablePage<T extends ITable> extends AbstractPageWithTable<T> {

  @Override
  protected void execPageActivated() {
    super.execPageActivated();
    updateMenusVisible(true);

    if (getParentPage() instanceof AbstractTablePage<?>) {
      ((AbstractTablePage<?>) getParentPage()).updateMenusVisible(false);
    }
  }

  private void updateMenusVisible(boolean visible) {
    getTable().getMenus().forEach(menu -> updateMenuVisible(menu, visible));
  }

  private void updateMenuVisible(IMenu menu, boolean visible) {
    if (menu instanceof AbstractVisibilityMenu) {
      ((AbstractVisibilityMenu) menu).updateVisible(visible);
    }
  }
}
