package ch.teachu.teachu_admin.client.lesson;

import ch.teachu.teachu_admin.client.lesson.LessonForm.MainBox.CancelButton;
import ch.teachu.teachu_admin.client.lesson.LessonForm.MainBox.GroupBox;
import ch.teachu.teachu_admin.client.lesson.LessonForm.MainBox.OkButton;
import ch.teachu.teachu_admin.client.schoolclass.subject.SchoolClassSubjectForm;
import ch.teachu.teachu_admin.shared.AdminPermission;
import ch.teachu.teachu_admin.shared.lesson.ILessonService;
import ch.teachu.teachu_admin.shared.lesson.LessonFormData;
import ch.teachu.teachu_admin.shared.lesson.RoomLookupCall;
import ch.teachu.teachu_admin.shared.lesson.WeekdayCodeType;
import ch.teachu.teachu_admin.shared.schoolclass.subject.SchoolClassSubjectLookupCall;
import ch.teachu.teachu_admin.shared.timetable.TimetableLookupCall;
import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.action.menu.IMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.ValueFieldMenuType;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCancelButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractSmartField;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.security.ACCESS;
import org.eclipse.scout.rt.shared.services.common.code.ICodeType;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;

import java.util.Set;

@FormData(value = LessonFormData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class LessonForm extends AbstractForm {

  private String id;
  private String schoolClassId;

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("Lesson");
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  public GroupBox getGroupBox() {
    return getFieldByClass(GroupBox.class);
  }

  public OkButton getOkButton() {
    return getFieldByClass(OkButton.class);
  }

  public CancelButton getCancelButton() {
    return getFieldByClass(CancelButton.class);
  }

  public GroupBox.RoomField getRoomField() {
    return getFieldByClass(GroupBox.RoomField.class);
  }

  public GroupBox.SubjectField getSubjectField() {
    return getFieldByClass(GroupBox.SubjectField.class);
  }

  public GroupBox.TimetableField getTimetableField() {
    return getFieldByClass(GroupBox.TimetableField.class);
  }

  public GroupBox.WeekdayField getWeekdayField() {
    return getFieldByClass(GroupBox.WeekdayField.class);
  }

  @FormData
  public String getId() {
    return id;
  }

  @FormData
  public void setId(String id) {
    this.id = id;
  }

  @FormData
  public String getSchoolClassId() {
    return schoolClassId;
  }

  @FormData
  public void setSchoolClassId(String schoolClassId) {
    this.schoolClassId = schoolClassId;
  }

  @Order(1000)
  public class MainBox extends AbstractGroupBox {
    @Order(1000)
    public class GroupBox extends AbstractGroupBox {

      @Order(1000)
      public class SubjectField extends AbstractSmartField<String> {
        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("SchoolClassSubject");
        }

        @Override
        protected Class<? extends ILookupCall<String>> getConfiguredLookupCall() {
          return SchoolClassSubjectLookupCall.class;
        }

        @Override
        protected void execPrepareLookup(ILookupCall<String> call) {
          ((SchoolClassSubjectLookupCall) call).setSchoolClassId(schoolClassId);
        }

        @Override
        protected boolean getConfiguredMandatory() {
          return true;
        }

        @Order(1000)
        public class CreateSchoolClassSubjectMenu extends AbstractMenu {
          @Override
          protected String getConfiguredText() {
            return TEXTS.get("New");
          }

          @Override
          protected Set<? extends IMenuType> getConfiguredMenuTypes() {
            return CollectionUtility.hashSet(ValueFieldMenuType.Null);
          }

          @Override
          protected void execAction() {
            SchoolClassSubjectForm form = new SchoolClassSubjectForm();
            form.setSchoolClassId(schoolClassId);
            form.startNew();
            form.waitFor();
            setValue(form.getId());
          }
        }
      }

      @Order(1500)
      public class TimetableField extends AbstractSmartField<String> {
        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Timetable");
        }

        @Override
        protected boolean getConfiguredMandatory() {
          return true;
        }

        @Override
        protected Class<? extends ILookupCall<String>> getConfiguredLookupCall() {
          return TimetableLookupCall.class;
        }
      }

      @Order(2000)
      public class WeekdayField extends AbstractSmartField<String> {
        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Weekday");
        }

        @Override
        protected Class<? extends ICodeType<?, String>> getConfiguredCodeType() {
          return WeekdayCodeType.class;
        }

        @Override
        protected boolean getConfiguredMandatory() {
          return true;
        }
      }

      @Order(2500)
      public class RoomField extends AbstractSmartField<String> {
        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Room");
        }

        @Override
        protected Class<? extends ILookupCall<String>> getConfiguredLookupCall() {
          return RoomLookupCall.class;
        }
      }
    }

    @Order(2000)
    public class OkButton extends AbstractOkButton {
    }

    @Order(3000)
    public class CancelButton extends AbstractCancelButton {
    }
  }

  public void startModify() {
    startInternalExclusive(new ModifyHandler());
  }

  public void startNew() {
    startInternal(new NewHandler());
  }

  public class NewHandler extends AbstractFormHandler {
    @Override
    protected void execLoad() {
      LessonFormData formData = new LessonFormData();
      exportFormData(formData);
      formData = BEANS.get(ILessonService.class).prepareCreate(formData);
      importFormData(formData);
    }

    @Override
    protected void execStore() {
      LessonFormData formData = new LessonFormData();
      exportFormData(formData);
      formData = BEANS.get(ILessonService.class).create(formData);
      importFormData(formData);
    }
  }

  public class ModifyHandler extends AbstractFormHandler {
    @Override
    protected void execLoad() {
      LessonFormData formData = new LessonFormData();
      exportFormData(formData);
      formData.setId(id);
      formData.setSchoolClassId(schoolClassId);
      formData = BEANS.get(ILessonService.class).load(formData);
      setEnabledGranted(ACCESS.check(new AdminPermission()));
      importFormData(formData);
    }

    @Override
    protected void execStore() {
      LessonFormData formData = new LessonFormData();
      exportFormData(formData);
      formData.setId(id);
      formData.setSchoolClassId(schoolClassId);
      formData = BEANS.get(ILessonService.class).store(formData);
      importFormData(formData);
    }
  }
}
