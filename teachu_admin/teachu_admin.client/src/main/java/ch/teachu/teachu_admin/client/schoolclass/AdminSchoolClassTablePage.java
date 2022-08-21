package ch.teachu.teachu_admin.client.schoolclass;

import ch.teachu.teachu_admin.shared.schoolclass.ISchoolClassService;
import org.eclipse.scout.rt.client.ui.basic.table.ITableRow;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.IPage;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

public class AdminSchoolClassTablePage extends SchoolClassTablePage {

  @Override
  protected void execLoadData(SearchFilter filter) {
    importPageData(BEANS.get(ISchoolClassService.class).getSchoolClassTableData(filter, null));
  }

  @Override
  protected IPage<?> execCreateChildPage(ITableRow row) {
    return new AdminSchoolClassNodePage(getTable().getIdColumn().getValue(row));
  }
}
