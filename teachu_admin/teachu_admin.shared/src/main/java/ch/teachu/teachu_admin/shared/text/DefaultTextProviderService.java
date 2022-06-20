package ch.teachu.teachu_admin.shared.text;

import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.AbstractDynamicNlsTextProviderService;

@Order(-2000)
public class DefaultTextProviderService extends AbstractDynamicNlsTextProviderService {
  @Override
  public String getDynamicNlsBaseName() {
    return "ch.teachu.teachu_admin.shared.texts.Texts";
  }
}
