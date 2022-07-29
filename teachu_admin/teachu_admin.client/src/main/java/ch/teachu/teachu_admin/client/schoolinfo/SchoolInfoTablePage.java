package ch.teachu.teachu_admin.client.schoolinfo;

import ch.teachu.teachu_admin.client.schoolinfo.SchoolInfoTablePage.Table;
import ch.teachu.teachu_admin.shared.schoolinfo.ISchoolInfoService;
import ch.teachu.teachu_admin.shared.schoolinfo.SchoolInfoTablePageData;
import org.eclipse.scout.rt.client.dto.Data;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithTable;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

@Data(SchoolInfoTablePageData.class)
public class SchoolInfoTablePage extends AbstractPageWithTable<Table> {
  @Override
  protected boolean getConfiguredLeaf() {
    return true;
  }

  @Override
  protected void execLoadData(SearchFilter filter) {
    importPageData(BEANS.get(ISchoolInfoService.class).getSchoolInfoTableData(filter));
  }

  @Override
  protected String getConfiguredTitle() {
// TODO [rbr] verify translation
    return TEXTS.get("SchoolInfoTablePage");
  }

  public class Table extends AbstractTable {

  }
}
