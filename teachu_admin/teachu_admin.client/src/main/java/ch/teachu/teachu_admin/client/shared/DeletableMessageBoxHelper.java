package ch.teachu.teachu_admin.client.shared;

import ch.teachu.teachu_admin.shared.shared.ICheckDeletable;
import ch.teachu.teachu_admin.shared.shared.ICheckDeletableService;
import org.eclipse.scout.rt.client.ui.messagebox.MessageBoxes;
import org.eclipse.scout.rt.platform.ApplicationScoped;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.html.HTML;
import org.eclipse.scout.rt.platform.html.IHtmlContent;
import org.eclipse.scout.rt.platform.html.IHtmlListElement;
import org.eclipse.scout.rt.platform.text.TEXTS;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class DeletableMessageBoxHelper {

  private static final int MAX_VISIBLE_ITEMS_COUNT = 10;
  private static final int EXCESS_ITEMS_MESSAGE_LINES = 2;

  public boolean checkAndShowMessageBox(Class<? extends ICheckDeletable> deletable, List<String> ids) {
    List<String> references = BEANS.get(ICheckDeletableService.class).checkDeletable(deletable, ids);
    if (references.isEmpty()) {
      return true;
    }
    MessageBoxes.createOk()
      .withHeader(TEXTS.get("CannotDeleteData"))
      .withHtml(createBody(references))
      .show();
    return false;
  }

  private IHtmlContent createBody(List<String> references) {
    final int hiddenItemsCount = references.size() - MAX_VISIBLE_ITEMS_COUNT;
    final boolean showExcessItemsEntry = hiddenItemsCount > EXCESS_ITEMS_MESSAGE_LINES;
    List<IHtmlListElement> elements = references.stream()
      .limit(MAX_VISIBLE_ITEMS_COUNT + (showExcessItemsEntry ? 0 : EXCESS_ITEMS_MESSAGE_LINES))
      .map(HTML::li)
      .collect(Collectors.toList());
    if (showExcessItemsEntry) {
      elements.add(HTML.li("...  "));
      elements.add(HTML.li(TEXTS.get("XAdditional", Integer.toString(hiddenItemsCount))));
    }
    return HTML.ul(elements);
  }
}
