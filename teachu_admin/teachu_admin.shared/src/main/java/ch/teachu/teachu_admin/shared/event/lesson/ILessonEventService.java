package ch.teachu.teachu_admin.shared.event.lesson;

import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

@TunnelToServer
public interface ILessonEventService extends IService {
  LessonEventTablePageData getLessonEventTableData(SearchFilter filter, String lessonId);

  LessonEventFormData prepareCreate(LessonEventFormData formData);

  LessonEventFormData create(LessonEventFormData formData);

  LessonEventFormData load(LessonEventFormData formData);

  LessonEventFormData store(LessonEventFormData formData);

  void delete(String id);
}
