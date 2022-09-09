package ch.teachu.teachu_admin.client.schoolinfo;

import ch.teachu.teachu_admin.client.schoolinfo.SchoolInfoForm.MainBox.CancelButton;
import ch.teachu.teachu_admin.client.schoolinfo.SchoolInfoForm.MainBox.GroupBox;
import ch.teachu.teachu_admin.client.schoolinfo.SchoolInfoForm.MainBox.OkButton;
import ch.teachu.teachu_admin.shared.schoolinfo.ISchoolInfoService;
import ch.teachu.teachu_admin.shared.schoolinfo.SchoolInfoFormData;
import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.booleanfield.AbstractBooleanField;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCancelButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.filechooserbutton.AbstractFileChooserButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.imagefield.AbstractImageField;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.client.ui.form.fields.tabbox.AbstractTabBox;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.TEXTS;

import java.util.Arrays;
import java.util.List;

@FormData(value = SchoolInfoFormData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class SchoolInfoForm extends AbstractForm {

  private String id;
  private byte[] image;

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("SchoolInfo");
  }

  public GroupBox.ActiveField getActiveField() {
    return getFieldByClass(GroupBox.ActiveField.class);
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

  public GroupBox getCheckBoxGroupBox() {
    return getFieldByClass(GroupBox.class);
  }

  public GroupBox.InfoBox.ImageGroupBox.ImageField getImageField() {
    return getFieldByClass(GroupBox.InfoBox.ImageGroupBox.ImageField.class);
  }

  public GroupBox.InfoBox.ImageGroupBox getImageGroupBox() {
    return getFieldByClass(GroupBox.InfoBox.ImageGroupBox.class);
  }

  public GroupBox.ImportantField getImportantField() {
    return getFieldByClass(GroupBox.ImportantField.class);
  }

  public GroupBox.InfoBox getInfoBox() {
    return getFieldByClass(GroupBox.InfoBox.class);
  }

  public GroupBox.InfoBox.MessageBox getMessageBox() {
    return getFieldByClass(GroupBox.InfoBox.MessageBox.class);
  }

  public GroupBox.InfoBox.MessageBox.MessageField getMessageField() {
    return getFieldByClass(GroupBox.InfoBox.MessageBox.MessageField.class);
  }

  public GroupBox.PinnedField getPinnedField() {
    return getFieldByClass(GroupBox.PinnedField.class);
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

  @FormData
  public byte[] getImage() {
    return image;
  }

  @FormData
  public void setImage(byte[] image) {
    this.image = image;
  }

  @Override
  protected int getConfiguredDisplayHint() {
    return DISPLAY_HINT_VIEW;
  }

  @Order(1000)
  public class MainBox extends AbstractGroupBox {
    @Order(1000)
    public class GroupBox extends AbstractGroupBox {

      @Override
      protected int getConfiguredGridColumnCount() {
        return 2;
      }

      @Order(500)
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

      @Order(3125)
      public class InfoBox extends AbstractTabBox {
        @Order(1000)
        public class ImageGroupBox extends AbstractGroupBox {
          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Image");
          }

          @Override
          protected int getConfiguredGridColumnCount() {
            return 1;
          }

          @Order(2250)
          public class ImageField extends AbstractImageField {

            @Override
            protected boolean getConfiguredAutoFit() {
              return true;
            }

            @Override
            protected boolean getConfiguredLabelVisible() {
              return false;
            }

            @Override
            protected int getConfiguredGridH() {
              return 3;
            }
          }

          @Order(2150)
          public class UploadButton extends AbstractFileChooserButton {
            @Override
            protected String getConfiguredLabel() {
              return TEXTS.get("UploadImage");
            }

            @Override
            protected void execChangedValue() {
              getImageField().setImage(getValue().getContent());
            }

            @Override
            protected List<String> getConfiguredFileExtensions() {
              return Arrays.asList("png", "jpg", "jpeg", "gif");
            }
          }
        }

        @Order(2000)
        public class MessageBox extends AbstractGroupBox {
          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Message");
          }

          @Order(2000)
          public class MessageField extends AbstractStringField {

            @Override
            protected boolean getConfiguredLabelVisible() {
              return false;
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
      formData.setImage((byte[]) getImageField().getImage());
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
      getImageField().setImage(formData.getImage());
    }

    @Override
    protected void execStore() {
      SchoolInfoFormData formData = new SchoolInfoFormData();
      exportFormData(formData);
      formData.setId(id);
      formData.setImage((byte[]) getImageField().getImage());
      formData = BEANS.get(ISchoolInfoService.class).store(formData);
      importFormData(formData);
    }
  }
}
