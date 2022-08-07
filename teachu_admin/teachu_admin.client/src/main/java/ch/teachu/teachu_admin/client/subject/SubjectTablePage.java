package ch.teachu.teachu_admin.client.subject;

import ch.teachu.teachu_admin.client.subject.SubjectTablePage.Table;
import ch.teachu.teachu_admin.shared.subject.ISubjectService;
import ch.teachu.teachu_admin.shared.subject.SubjectTablePageData;
import org.eclipse.scout.rt.client.dto.Data;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithTable;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

@Data(SubjectTablePageData.class)
public class SubjectTablePage extends AbstractPageWithTable<Table> {
  @Override
  protected boolean getConfiguredLeaf() {
    return true;
  }

  @Override
  protected void execLoadData(SearchFilter filter) {
    importPageData(BEANS.get(ISubjectService.class).getSubjectTableData(filter));
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("Subject");
  }

  public class Table extends AbstractTable {

  }
}
