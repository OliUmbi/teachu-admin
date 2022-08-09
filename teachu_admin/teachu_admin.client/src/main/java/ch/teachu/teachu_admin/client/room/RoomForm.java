package ch.teachu.teachu_admin.client.room;

import ch.teachu.teachu_admin.client.room.RoomForm.MainBox.CancelButton;
import ch.teachu.teachu_admin.client.room.RoomForm.MainBox.GroupBox;
import ch.teachu.teachu_admin.client.room.RoomForm.MainBox.OkButton;
import ch.teachu.teachu_admin.shared.room.IRoomService;
import ch.teachu.teachu_admin.shared.room.RoomFormData;
import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCancelButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.TEXTS;

@FormData(value = RoomFormData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class RoomForm extends AbstractForm {

  private String id;

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("Room");
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

  public GroupBox.NameField getNameField() {
    return getFieldByClass(GroupBox.NameField.class);
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
      public class NameField extends AbstractStringField {
        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Name");
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
      RoomFormData formData = new RoomFormData();
      exportFormData(formData);
      formData = BEANS.get(IRoomService.class).prepareCreate(formData);
      importFormData(formData);
    }

    @Override
    protected void execStore() {
      RoomFormData formData = new RoomFormData();
      exportFormData(formData);
      formData = BEANS.get(IRoomService.class).create(formData);
      importFormData(formData);
    }
  }

  public class ModifyHandler extends AbstractFormHandler {
    @Override
    protected void execLoad() {
      RoomFormData formData = new RoomFormData();
      exportFormData(formData);
      formData.setId(id);
      formData = BEANS.get(IRoomService.class).load(formData);
      importFormData(formData);
    }

    @Override
    protected void execStore() {
      RoomFormData formData = new RoomFormData();
      exportFormData(formData);
      formData.setId(id);
      formData = BEANS.get(IRoomService.class).store(formData);
      importFormData(formData);
    }
  }
}
