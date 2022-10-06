package ch.teachu.teachu_admin.client.event.lesson;

import ch.teachu.teachu_admin.client.event.lesson.LessonEventForm.MainBox.CancelButton;
import ch.teachu.teachu_admin.client.event.lesson.LessonEventForm.MainBox.GroupBox;
import ch.teachu.teachu_admin.client.event.lesson.LessonEventForm.MainBox.OkButton;
import ch.teachu.teachu_admin.shared.event.lesson.ILessonEventService;
import ch.teachu.teachu_admin.shared.event.lesson.LessonEventCodeType;
import ch.teachu.teachu_admin.shared.event.lesson.LessonEventFormData;
import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCancelButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.datefield.AbstractDateField;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractSmartField;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.shared.services.common.code.ICodeType;

@FormData(value = LessonEventFormData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class LessonEventForm extends AbstractForm {

  private String id;
  private String lessonId;

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("LessonEvent");
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

  public GroupBox.DateField getDateField() {
    return getFieldByClass(GroupBox.DateField.class);
  }

  public GroupBox.DescriptionField getDescriptionField() {
    return getFieldByClass(GroupBox.DescriptionField.class);
  }

  public GroupBox.TitleField getTitleField() {
    return getFieldByClass(GroupBox.TitleField.class);
  }

  public GroupBox.TypeField getTypeField() {
    return getFieldByClass(GroupBox.TypeField.class);
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
  public String getLessonId() {
    return lessonId;
  }

  @FormData
  public void setLessonId(String lessonId) {
    this.lessonId = lessonId;
  }

  @Order(1000)
  public class MainBox extends AbstractGroupBox {
    @Order(1000)
    public class GroupBox extends AbstractGroupBox {
      @Order(1000)
      public class TitleField extends AbstractStringField {
        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Title");
        }

        @Override
        protected int getConfiguredMaxLength() {
          return 255;
        }

        @Override
        protected boolean getConfiguredMandatory() {
          return true;
        }
      }

      @Order(2000)
      public class TypeField extends AbstractSmartField<String> {
        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Type");
        }

        @Override
        protected Class<? extends ICodeType<?, String>> getConfiguredCodeType() {
          return LessonEventCodeType.class;
        }

        @Override
        protected boolean getConfiguredMandatory() {
          return true;
        }
      }

      @Order(3000)
      public class DateField extends AbstractDateField {
        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Date");
        }

        @Override
        protected boolean getConfiguredMandatory() {
          return true;
        }
      }

      @Order(4000)
      public class DescriptionField extends AbstractStringField {
        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Description");
        }

        @Override
        protected int getConfiguredMaxLength() {
          return 4096;
        }

        @Override
        protected int getConfiguredGridH() {
          return 3;
        }

        @Override
        protected int getConfiguredGridW() {
          return 2;
        }

        @Override
        protected boolean getConfiguredMultilineText() {
          return true;
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
      LessonEventFormData formData = new LessonEventFormData();
      exportFormData(formData);
      formData = BEANS.get(ILessonEventService.class).prepareCreate(formData);
      importFormData(formData);
    }

    @Override
    protected void execStore() {
      LessonEventFormData formData = new LessonEventFormData();
      exportFormData(formData);
      formData = BEANS.get(ILessonEventService.class).create(formData);
      importFormData(formData);
    }
  }

  public class ModifyHandler extends AbstractFormHandler {
    @Override
    protected void execLoad() {
      LessonEventFormData formData = new LessonEventFormData();
      exportFormData(formData);
      formData.setId(id);
      formData.setLessonId(lessonId);
      formData = BEANS.get(ILessonEventService.class).load(formData);
      importFormData(formData);
    }

    @Override
    protected void execStore() {
      LessonEventFormData formData = new LessonEventFormData();
      exportFormData(formData);
      formData.setId(id);
      formData.setLessonId(lessonId);
      formData = BEANS.get(ILessonEventService.class).store(formData);
      importFormData(formData);
    }
  }
}
