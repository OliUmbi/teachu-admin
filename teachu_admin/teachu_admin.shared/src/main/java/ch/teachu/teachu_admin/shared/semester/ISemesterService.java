package ch.teachu.teachu_admin.shared.semester;

import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

import java.util.Date;

@TunnelToServer
public interface ISemesterService extends IService {
  SemesterTablePageData getSemesterTableData(SearchFilter filter);

  SemesterFormData prepareCreate(SemesterFormData formData);

  SemesterFormData create(SemesterFormData formData);

  SemesterFormData load(SemesterFormData formData);

  SemesterFormData store(SemesterFormData formData);

  void delete(String id);

  boolean overlapsWithOtherSemesters(String id, Date from, Date to);
}
