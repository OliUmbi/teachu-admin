package ch.teachu.teachu_admin.client.schoolinfo;

import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.booleanfield.AbstractBooleanField;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCancelButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.TEXTS;

import ch.teachu.teachu_admin.client.schoolinfo.SchoolInfoForm.MainBox.CancelButton;
import ch.teachu.teachu_admin.client.schoolinfo.SchoolInfoForm.MainBox.GroupBox;
import ch.teachu.teachu_admin.client.schoolinfo.SchoolInfoForm.MainBox.OkButton;
import ch.teachu.teachu_admin.shared.schoolinfo.ISchoolInfoService;
import ch.teachu.teachu_admin.shared.schoolinfo.SchoolInfoFormData;

@FormData(value = SchoolInfoFormData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class SchoolInfoForm extends AbstractForm {

  private String id;

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("SchoolInfo");
  }

  public GroupBox.CheckBoxGroupBox.ActiveField getActiveField() {
    return getFieldByClass(GroupBox.CheckBoxGroupBox.ActiveField.class);
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

  public GroupBox.CheckBoxGroupBox getCheckBoxGroupBox() {
    return getFieldByClass(GroupBox.CheckBoxGroupBox.class);
  }

  public GroupBox.CheckBoxGroupBox.ImportantField getImportantField() {
    return getFieldByClass(GroupBox.CheckBoxGroupBox.ImportantField.class);
  }

  public GroupBox.MessageField getMessageField() {
    return getFieldByClass(GroupBox.MessageField.class);
  }

  public GroupBox.CheckBoxGroupBox.PinnedField getPinnedField() {
    return getFieldByClass(GroupBox.CheckBoxGroupBox.PinnedField.class);
  }

  public GroupBox.TitleField getTitleField() {
    return getFieldByClass(GroupBox.TitleField.class);
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
      public class MessageField extends AbstractStringField {
        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Message");
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

        @Override
        protected boolean getConfiguredMandatory() {
          return true;
        }
      }

      @Order(2500)
      public class CheckBoxGroupBox extends AbstractGroupBox {

        @Override
        protected boolean getConfiguredLabelVisible() {
          return false;
        }

        @Override
        protected int getConfiguredGridColumnCount() {
          return 2;
        }

        @Order(1000)
        public class ActiveField extends AbstractBooleanField {
          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Active");
          }
        }

        @Order(3000)
        public class ImportantField extends AbstractBooleanField {
          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Important");
          }
        }

        @Order(4000)
        public class PinnedField extends AbstractBooleanField {
          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Pinned");
          }
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
      SchoolInfoFormData formData = new SchoolInfoFormData();
      exportFormData(formData);
      formData = BEANS.get(ISchoolInfoService.class).prepareCreate(formData);
      importFormData(formData);
    }

    @Override
    protected void execStore() {
      SchoolInfoFormData formData = new SchoolInfoFormData();
      exportFormData(formData);
      formData = BEANS.get(ISchoolInfoService.class).create(formData);
      importFormData(formData);
    }
  }

  public class ModifyHandler extends AbstractFormHandler {
    @Override
    protected void execLoad() {
      SchoolInfoFormData formData = new SchoolInfoFormData();
      exportFormData(formData);
      formData.setId(id);
      formData = BEANS.get(ISchoolInfoService.class).load(formData);
      importFormData(formData);
    }

    @Override
    protected void execStore() {
      SchoolInfoFormData formData = new SchoolInfoFormData();
      exportFormData(formData);
      formData.setId(id);
      formData = BEANS.get(ISchoolInfoService.class).store(formData);
      importFormData(formData);
    }
  }
}
