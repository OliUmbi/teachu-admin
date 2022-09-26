package ch.teachu.teachu_admin.shared.timetable;

import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

@TunnelToServer
public interface ITimetableService extends IService {
  TimetableTablePageData getTimetableTableData(SearchFilter filter);

  TimetableFormData prepareCreate(TimetableFormData formData);

  TimetableFormData create(TimetableFormData formData);

  TimetableFormData load(TimetableFormData formData);

  TimetableFormData store(TimetableFormData formData);

  void delete(String id);
}
