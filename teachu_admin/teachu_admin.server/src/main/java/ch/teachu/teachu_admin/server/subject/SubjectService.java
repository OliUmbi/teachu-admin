package ch.teachu.teachu_admin.server.subject;

import ch.teachu.teachu_admin.shared.subject.ISubjectService;
import ch.teachu.teachu_admin.shared.subject.SubjectTablePageData;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

public class SubjectService implements ISubjectService {
  @Override
  public SubjectTablePageData getSubjectTableData(SearchFilter filter) {
    SubjectTablePageData pageData = new SubjectTablePageData();
// TODO [rbr] fill pageData.
    return pageData;
  }
}
