package ch.teachu.teachu_admin.server.shared;

import ch.teachu.teachu_admin.shared.shared.ICheckDeletable;
import ch.teachu.teachu_admin.shared.shared.ICheckDeletableService;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.server.jdbc.SQL;

import java.util.ArrayList;
import java.util.List;

public class CheckDeletableService implements ICheckDeletableService {
  @Override
  public List<String> checkDeletable(Class<? extends ICheckDeletable> deletable, List<String> ids) {
    List<String> references = new ArrayList<>();
    BEANS.all(deletable).forEach(checkDeletable -> {
      Object[][] result = SQL.select(checkDeletable.getSqlSelectReferences(), new NVPair("ids", ids));
      for (Object[] row : result) {
        references.add(checkDeletable.getCategoryName() + " " + row[0]);
      }
    });
    return references;
  }
}
