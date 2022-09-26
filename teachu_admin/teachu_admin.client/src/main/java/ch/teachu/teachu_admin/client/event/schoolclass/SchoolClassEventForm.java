package ch.teachu.teachu_admin.client.event.schoolclass;

import ch.teachu.teachu_admin.client.event.schoolclass.SchoolClassEventForm.MainBox.CancelButton;
import ch.teachu.teachu_admin.client.event.schoolclass.SchoolClassEventForm.MainBox.GroupBox;
import ch.teachu.teachu_admin.client.event.schoolclass.SchoolClassEventForm.MainBox.OkButton;
import ch.teachu.teachu_admin.shared.event.schoolclass.ISchoolClassEventService;
import ch.teachu.teachu_admin.shared.event.schoolclass.SchoolClassEventCodeType;
import ch.teachu.teachu_admin.shared.event.schoolclass.SchoolClassEventFormData;
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
import org.eclipse.scout.rt.platform.exception.VetoException;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.shared.services.common.code.ICodeType;

import java.util.Date;

@FormData(value = SchoolClassEventFormData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class SchoolClassEventForm extends AbstractForm {

  private String id;
  private String schoolClassId;

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("SchoolClassEvent");
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

  public GroupBox.DescriptionField getDescriptionField() {
    return getFieldByClass(GroupBox.DescriptionField.class);
  }

  public GroupBox.FromField getFromField() {
    return getFieldByClass(GroupBox.FromField.class);
  }

  public GroupBox.TitleField getTitleField() {
    return getFieldByClass(GroupBox.TitleField.class);
  }

  public GroupBox.ToField getToField() {
    return getFieldByClass(GroupBox.ToField.class);
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
        public Class<? extends ICodeType<?, String>> getConfiguredCodeType() {
          return SchoolClassEventCodeType.class;
        }

        @Override
        protected boolean getConfiguredMandatory() {
          return true;
        }
      }

      @Order(3000)
      public class FromField extends AbstractDateField {
        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("FromDate");
        }

        @Override
        protected Date execValidateValue(Date rawValue) {
          if (getToField().getValue() != null && !rawValue.equals(getFromField().getValue()) && !getToField().getValue().after(rawValue)) {
            throw new VetoException(TEXTS.get("ToNotAfterFrom"));
          }

          getToField().clearErrorStatus();
          return rawValue;
        }

        @Override
        protected boolean getConfiguredMandatory() {
          return true;
        }
      }

      @Order(4000)
      public class ToField extends AbstractDateField {
        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("ToDate");
        }

        @Override
        protected Date execValidateValue(Date rawValue) {
          if (getFromField().getValue() != null && !rawValue.equals(getFromField().getValue()) && !rawValue.after(getFromField().getValue())) {
            throw new VetoException(TEXTS.get("ToNotAfterFrom"));
          }

          getFromField().clearErrorStatus();
          return rawValue;
        }

        @Override
        protected boolean getConfiguredMandatory() {
          return true;
        }
      }

      @Order(5000)
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
      SchoolClassEventFormData formData = new SchoolClassEventFormData();
      exportFormData(formData);
      formData = BEANS.get(ISchoolClassEventService.class).prepareCreate(formData);
      importFormData(formData);
    }

    @Override
    protected void execStore() {
      SchoolClassEventFormData formData = new SchoolClassEventFormData();
      exportFormData(formData);
      formData = BEANS.get(ISchoolClassEventService.class).create(formData);
      importFormData(formData);
    }
  }

  public class ModifyHandler extends AbstractFormHandler {
    @Override
    protected void execLoad() {
      SchoolClassEventFormData formData = new SchoolClassEventFormData();
      exportFormData(formData);
      formData.setId(id);
      formData.setSchoolClassId(schoolClassId);
      formData = BEANS.get(ISchoolClassEventService.class).load(formData);
      importFormData(formData);
    }

    @Override
    protected void execStore() {
      SchoolClassEventFormData formData = new SchoolClassEventFormData();
      exportFormData(formData);
      formData.setId(id);
      formData.setSchoolClassId(schoolClassId);
      formData = BEANS.get(ISchoolClassEventService.class).store(formData);
      importFormData(formData);
    }
  }
}
