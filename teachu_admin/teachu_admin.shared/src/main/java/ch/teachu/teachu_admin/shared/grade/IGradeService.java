package ch.teachu.teachu_admin.shared.grade;

import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

@TunnelToServer
public interface IGradeService extends IService {
  GradeTablePageData getGradeTableData(SearchFilter filter, String examId);

  GradeFormData prepareCreate(GradeFormData formData);

  GradeFormData create(GradeFormData formData);

  GradeFormData load(GradeFormData formData);

  GradeFormData store(GradeFormData formData);

  void delete(String id);
}
