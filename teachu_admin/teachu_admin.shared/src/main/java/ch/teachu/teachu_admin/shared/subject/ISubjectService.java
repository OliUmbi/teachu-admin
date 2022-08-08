package ch.teachu.teachu_admin.shared.subject;

import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

@TunnelToServer
public interface ISubjectService extends IService {
  SubjectTablePageData getSubjectTableData(SearchFilter filter);

  SubjectFormData prepareCreate(SubjectFormData formData);

  SubjectFormData create(SubjectFormData formData);

  SubjectFormData load(SubjectFormData formData);

  SubjectFormData store(SubjectFormData formData);

  void delete(String id);
}
