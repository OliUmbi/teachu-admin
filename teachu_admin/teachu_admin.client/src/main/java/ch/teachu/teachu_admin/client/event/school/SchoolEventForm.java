package ch.teachu.teachu_admin.client.event.school;

import ch.teachu.teachu_admin.client.event.school.SchoolEventForm.MainBox.CancelButton;
import ch.teachu.teachu_admin.client.event.school.SchoolEventForm.MainBox.GroupBox;
import ch.teachu.teachu_admin.client.event.school.SchoolEventForm.MainBox.OkButton;
import ch.teachu.teachu_admin.shared.event.school.ISchoolEventService;
import ch.teachu.teachu_admin.shared.event.school.SchoolEventCodeType;
import ch.teachu.teachu_admin.shared.event.school.SchoolEventFormData;
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

@FormData(value = SchoolEventFormData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class SchoolEventForm extends AbstractForm {

  private String id;

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("SchoolEvent");
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

  public GroupBox.MetaInfoBox.FromField getFromField() {
    return getFieldByClass(GroupBox.MetaInfoBox.FromField.class);
  }

  public GroupBox.MetaInfoBox.ToField getToField() {
    return getFieldByClass(GroupBox.MetaInfoBox.ToField.class);
  }

  @FormData
  public String getId() {
    return id;
  }

  @FormData
  public void setId(String id) {
    this.id = id;
  }

  @Order(1000)
  public class MainBox extends AbstractGroupBox {

    @Order(1000)
    public class GroupBox extends AbstractGroupBox {

      @Override
      protected int getConfiguredGridColumnCount() {
        return 1;
      }

      @Order(0)
      public class MetaInfoBox extends AbstractGroupBox {

        @Override
        protected int getConfiguredGridColumnCount() {
          return 2;
        }

        @Override
        protected boolean getConfiguredLabelVisible() {
          return false;
        }

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

        @Order(3000)
        public class SchoolEventTypeField extends AbstractSmartField<String> {
          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Type");
          }

          @Override
          protected Class<? extends ICodeType<?, String>> getConfiguredCodeType() {
            return SchoolEventCodeType.class;
          }

          @Override
          protected boolean getConfiguredMandatory() {
            return true;
          }
        }

        @Order(4000)
        public class FromField extends AbstractDateField {
          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("FromDate");
          }

          @Override
          protected boolean getConfiguredMandatory() {
            return true;
          }

          @Override
          protected Date execValidateValue(Date rawValue) {
            if (getToField().getValue() != null && !getToField().getValue().after(rawValue)) {
              throw new VetoException(TEXTS.get("ToNotAfterFrom"));
            }
            return rawValue;
          }
        }

        @Order(5000)
        public class ToField extends AbstractDateField {
          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("ToDate");
          }

          @Override
          protected boolean getConfiguredMandatory() {
            return true;
          }

          @Override
          protected Date execValidateValue(Date rawValue) {
            if (getFromField().getValue() != null && !rawValue.after(getFromField().getValue())) {
              throw new VetoException(TEXTS.get("ToNotAfterFrom"));
            }
            return rawValue;
          }
        }
      }

      @Order(1000)
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
        protected boolean getConfiguredMultilineText() {
          return true;
        }

        @Override
        protected int getConfiguredGridH() {
          return 3;
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
      SchoolEventFormData formData = new SchoolEventFormData();
      exportFormData(formData);
      formData = BEANS.get(ISchoolEventService.class).prepareCreate(formData);
      importFormData(formData);
    }

    @Override
    protected void execStore() {
      SchoolEventFormData formData = new SchoolEventFormData();
      exportFormData(formData);
      formData = BEANS.get(ISchoolEventService.class).create(formData);
      importFormData(formData);
    }
  }

  public class ModifyHandler extends AbstractFormHandler {
    @Override
    protected void execLoad() {
      SchoolEventFormData formData = new SchoolEventFormData();
      exportFormData(formData);
      formData.setId(id);
      formData = BEANS.get(ISchoolEventService.class).load(formData);
      importFormData(formData);
    }

    @Override
    protected void execStore() {
      SchoolEventFormData formData = new SchoolEventFormData();
      exportFormData(formData);
      formData.setId(id);
      formData = BEANS.get(ISchoolEventService.class).store(formData);
      importFormData(formData);
    }
  }
}
