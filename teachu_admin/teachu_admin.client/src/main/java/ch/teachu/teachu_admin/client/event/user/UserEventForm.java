package ch.teachu.teachu_admin.client.event.user;

import ch.teachu.teachu_admin.client.event.user.UserEventForm.MainBox.CancelButton;
import ch.teachu.teachu_admin.client.event.user.UserEventForm.MainBox.GroupBox;
import ch.teachu.teachu_admin.client.event.user.UserEventForm.MainBox.OkButton;
import ch.teachu.teachu_admin.shared.event.user.IUserEventService;
import ch.teachu.teachu_admin.shared.event.user.UserEventFormData;
import ch.teachu.teachu_admin.shared.event.user.UserEventStateCodeType;
import ch.teachu.teachu_admin.shared.event.user.UserEventTypeCodeType;
import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCancelButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.datefield.AbstractDateTimeField;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractSmartField;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.shared.services.common.code.ICodeType;

@FormData(value = UserEventFormData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class UserEventForm extends AbstractForm {

  private String id;
  private String userId;

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("UserEvent");
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

  public GroupBox.UserEventStateField getUserEventStateField() {
    return getFieldByClass(GroupBox.UserEventStateField.class);
  }

  public GroupBox.UserEventTypeField getUserEventTypeField() {
    return getFieldByClass(GroupBox.UserEventTypeField.class);
  }

  @FormData
  public String getUserId() {
    return userId;
  }

  @FormData
  public void setUserId(String userId) {
    this.userId = userId;
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
      public class UserEventStateField extends AbstractSmartField<String> {
        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("State");
        }

        @Override
        protected Class<? extends ICodeType<?, String>> getConfiguredCodeType() {
          return UserEventStateCodeType.class;
        }

        @Override
        protected boolean getConfiguredMandatory() {
          return true;
        }
      }

      @Order(3000)
      public class UserEventTypeField extends AbstractSmartField<String> {
        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Type");
        }

        @Override
        protected Class<? extends ICodeType<?, String>> getConfiguredCodeType() {
          return UserEventTypeCodeType.class;
        }

        @Override
        protected boolean getConfiguredMandatory() {
          return true;
        }
      }

      @Order(4000)
      public class FromField extends AbstractDateTimeField {
        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("FromDate");
        }

        @Override
        protected boolean getConfiguredMandatory() {
          return true;
        }
      }

      @Order(5000)
      public class ToField extends AbstractDateTimeField {
        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("ToDate");
        }

        @Override
        protected boolean getConfiguredMandatory() {
          return true;
        }
      }

      @Order(6000)
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

        @Override
        protected int getConfiguredGridW() {
          return 2;
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
      UserEventFormData formData = new UserEventFormData();
      exportFormData(formData);
      formData = BEANS.get(IUserEventService.class).prepareCreate(formData);
      importFormData(formData);
    }

    @Override
    protected void execStore() {
      UserEventFormData formData = new UserEventFormData();
      exportFormData(formData);
      formData = BEANS.get(IUserEventService.class).create(formData);
      importFormData(formData);
    }
  }

  public class ModifyHandler extends AbstractFormHandler {
    @Override
    protected void execLoad() {
      UserEventFormData formData = new UserEventFormData();
      exportFormData(formData);
      formData.setId(id);
      formData = BEANS.get(IUserEventService.class).load(formData);
      importFormData(formData);
    }

    @Override
    protected void execStore() {
      UserEventFormData formData = new UserEventFormData();
      exportFormData(formData);
      formData.setId(id);
      formData = BEANS.get(IUserEventService.class).store(formData);
      importFormData(formData);
    }
  }
}
