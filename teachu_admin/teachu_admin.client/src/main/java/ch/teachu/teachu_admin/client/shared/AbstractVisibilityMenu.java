package ch.teachu.teachu_admin.client.shared;

import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;

public abstract class AbstractVisibilityMenu extends AbstractMenu {

  public void updateVisible(boolean visible) {
    setVisible(getConfiguredVisible() && visible);
  }
}
