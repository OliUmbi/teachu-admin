package ch.teachu.teachu_admin.shared.lesson;

import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

import java.util.Date;

@TunnelToServer
public interface ILessonService extends IService {
  LessonTablePageData getLessonTableData(SearchFilter filter, String schoolClassId);

  LessonFormData prepareCreate(LessonFormData formData);

  LessonFormData create(LessonFormData formData);

  LessonFormData load(LessonFormData formData);

  LessonFormData store(LessonFormData formData);

  void delete(String id);

  boolean overlapsWithOtherLessons(String id, Date from, Date to, String weekday, String schoolClassId);
}
