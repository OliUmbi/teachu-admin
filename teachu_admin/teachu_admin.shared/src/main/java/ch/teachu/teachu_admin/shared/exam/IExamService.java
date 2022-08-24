package ch.teachu.teachu_admin.shared.exam;

import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

@TunnelToServer
public interface IExamService extends IService {
  ExamTablePageData getExamTableData(SearchFilter filter, String schoolClassSubjectId);

  ExamFormData prepareCreate(ExamFormData formData);

  ExamFormData create(ExamFormData formData);

  ExamFormData load(ExamFormData formData);

  ExamFormData store(ExamFormData formData);

  void delete(String id);
}
