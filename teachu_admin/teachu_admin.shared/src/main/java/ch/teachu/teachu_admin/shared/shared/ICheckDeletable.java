package ch.teachu.teachu_admin.shared.shared;

import org.eclipse.scout.rt.platform.ApplicationScoped;

@ApplicationScoped
public interface ICheckDeletable {
  String getCategoryName();

  String getSqlSelectReferences();
}
