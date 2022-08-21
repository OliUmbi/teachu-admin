package ch.teachu.teachu_admin.shared.schoolclass.subject;

import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

@TunnelToServer
public interface ISchoolClassSubjectService extends IService {
  SchoolClassSubjectTablePageData getSchoolClassSubjectTableData(SearchFilter filter, String schoolClassId);

  SchoolClassSubjectFormData prepareCreate(SchoolClassSubjectFormData formData);

  SchoolClassSubjectFormData create(SchoolClassSubjectFormData formData);

  SchoolClassSubjectFormData load(SchoolClassSubjectFormData formData);

  SchoolClassSubjectFormData store(SchoolClassSubjectFormData formData);

  void delete(String id);

  boolean usedSubject(String id, String schoolClassId, String subjectId);
}
