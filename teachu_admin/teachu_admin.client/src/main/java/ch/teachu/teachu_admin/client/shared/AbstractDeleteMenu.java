package ch.teachu.teachu_admin.client.shared;

import org.eclipse.scout.rt.client.ui.action.menu.IMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.TableMenuType;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.CollectionUtility;

import java.util.Set;

public abstract class AbstractDeleteMenu extends AbstractVisibilityMenu {
  @Override
  protected String getConfiguredText() {
    return TEXTS.get("DeleteMenu");
  }

  @Override
  protected Set<? extends IMenuType> getConfiguredMenuTypes() {
    return CollectionUtility.hashSet(TableMenuType.SingleSelection, TableMenuType.MultiSelection);
  }
}
