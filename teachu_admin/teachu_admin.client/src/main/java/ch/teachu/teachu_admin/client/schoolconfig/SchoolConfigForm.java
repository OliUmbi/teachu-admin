package ch.teachu.teachu_admin.client.schoolconfig;

import ch.teachu.teachu_admin.client.schoolconfig.SchoolConfigForm.MainBox.CancelButton;
import ch.teachu.teachu_admin.client.schoolconfig.SchoolConfigForm.MainBox.GroupBox;
import ch.teachu.teachu_admin.client.schoolconfig.SchoolConfigForm.MainBox.OkButton;
import ch.teachu.teachu_admin.shared.schoolconfig.ISchoolConfigService;
import ch.teachu.teachu_admin.shared.schoolconfig.SchoolConfigCodeType;
import ch.teachu.teachu_admin.shared.schoolconfig.SchoolConfigFormData;
import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCancelButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractSmartField;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.TEXTS;

@FormData(value = SchoolConfigFormData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class SchoolConfigForm extends AbstractForm {

  private String codeType;
  private String value;

  @FormData
  public String getCodeType() {
    return codeType;
  }

  @FormData
  public void setCodeType(String codeType) {
    this.codeType = codeType;
  }

  @FormData
  public String getValue() {
    return value;
  }

  @FormData
  public void setValue(String value) {
    this.value = value;
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("Config");
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

  public GroupBox.ConfigNameField getConfigNameField() {
    return getFieldByClass(GroupBox.ConfigNameField.class);
  }

  public GroupBox.ValueSmartField getValueSmartField() {
    return getFieldByClass(GroupBox.ValueSmartField.class);
  }

  public GroupBox.ValueStringField getValueStringField() {
    return getFieldByClass(GroupBox.ValueStringField.class);
  }

  @Order(1000)
  public class MainBox extends AbstractGroupBox {
    @Order(1000)
    public class GroupBox extends AbstractGroupBox {
      @Order(1000)
      public class ConfigNameField extends AbstractStringField {
        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Name");
        }

        @Override
        protected int getConfiguredMaxLength() {
          return 255;
        }

        @Override
        protected boolean getConfiguredEnabled() {
          return false;
        }

        @Override
        protected String execFormatValue(String s) {
          return TEXTS.get(s);
        }
      }

      @Order(2000)
      public class ValueStringField extends AbstractStringField {
        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Value");
        }

        @Override
        protected int getConfiguredMaxLength() {
          return 255;
        }
      }

      @Order(3000)
      public class ValueSmartField extends AbstractSmartField<String> {
        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Value");
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

  public class ModifyHandler extends AbstractFormHandler {
    @Override
    protected void execLoad() {
      SchoolConfigFormData formData = new SchoolConfigFormData();
      exportFormData(formData);
      importFormData(formData);

      setSubTitle(TEXTS.get(formData.getConfigName().getValue()));
      boolean isCodeTypeConfig = formData.getCodeType() != null;
      getValueSmartField().setVisible(isCodeTypeConfig);
      getValueStringField().setVisible(!isCodeTypeConfig);
      if (isCodeTypeConfig) {
        getValueSmartField().setCodeTypeClass(SchoolConfigCodeType.valueOf(formData.getCodeType().toUpperCase()).getCodeType());
        getValueSmartField().setValue(value);
      } else {
        getValueStringField().setValue(value);
      }
    }

    @Override
    protected void execStore() {
      SchoolConfigFormData formData = new SchoolConfigFormData();
      exportFormData(formData);
      formData.setValue(codeType == null ? getValueStringField().getValue() : getValueSmartField().getValue());
      formData = BEANS.get(ISchoolConfigService.class).store(formData);
      importFormData(formData);
    }
  }
}
