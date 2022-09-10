package ch.teachu.teachu_admin.client.chatgroup;

import ch.teachu.teachu_admin.client.chatgroup.ChatGroupForm.MainBox.CancelButton;
import ch.teachu.teachu_admin.client.chatgroup.ChatGroupForm.MainBox.GroupBox;
import ch.teachu.teachu_admin.client.chatgroup.ChatGroupForm.MainBox.OkButton;
import ch.teachu.teachu_admin.client.user.AbstractCrudTable;
import ch.teachu.teachu_admin.shared.chatgroup.ChatGroupFormData;
import ch.teachu.teachu_admin.shared.chatgroup.IChatGroupService;
import ch.teachu.teachu_admin.shared.user.UserLookupCall;
import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractSmartColumn;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCancelButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.client.ui.form.fields.tablefield.AbstractTableField;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;

@FormData(value = ChatGroupFormData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class ChatGroupForm extends AbstractForm {

  private String id;

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("ChatGroup");
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

  public GroupBox.MembersField getMembersField() {
    return getFieldByClass(GroupBox.MembersField.class);
  }

  public GroupBox.TitleField getTitleField() {
    return getFieldByClass(GroupBox.TitleField.class);
  }

  @Override
  protected int getConfiguredDisplayHint() {
    return DISPLAY_HINT_VIEW;
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
          return 2;
        }
      }

      @Order(3000)
      public class MembersField extends AbstractTableField<MembersField.Table> {
        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Members");
        }

        @Override
        protected int getConfiguredGridH() {
          return 3;
        }

        @Override
        protected boolean getConfiguredGridUseUiHeight() {
          return true;
        }

        @ClassId("f3fe627e-e977-4ebe-bd5c-84cd4df579b5")
        public class Table extends AbstractCrudTable {
          @Order(1000)
          public class NameColumn extends AbstractSmartColumn<String> {
            @Override
            protected String getConfiguredHeaderText() {
              return TEXTS.get("Name");
            }

            @Override
            protected int getConfiguredWidth() {
              return 400;
            }

            @Override
            protected boolean getConfiguredEditable() {
              return true;
            }

            @Override
            protected Class<? extends ILookupCall<String>> getConfiguredLookupCall() {
              return UserLookupCall.class;
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

  public void startNew(ChatGroupFormData data) {
    startInternal(new NewHandler(data));
  }

  public class NewHandler extends AbstractFormHandler {

    private ChatGroupFormData initialFormData;

    public NewHandler() {
      this(new ChatGroupFormData());
    }

    public NewHandler(ChatGroupFormData initialFormData) {
      this.initialFormData = initialFormData;
    }

    @Override
    protected void execLoad() {
      initialFormData = BEANS.get(IChatGroupService.class).prepareCreate(initialFormData);
      importFormData(initialFormData);
    }

    @Override
    protected void execStore() {
      ChatGroupFormData formData = new ChatGroupFormData();
      exportFormData(formData);
      formData = BEANS.get(IChatGroupService.class).create(formData);
      importFormData(formData);
    }
  }

  public class ModifyHandler extends AbstractFormHandler {
    @Override
    protected void execLoad() {
      ChatGroupFormData formData = new ChatGroupFormData();
      exportFormData(formData);
      formData.setId(id);
      formData = BEANS.get(IChatGroupService.class).load(formData);
      importFormData(formData);
    }

    @Override
    protected void execStore() {
      ChatGroupFormData formData = new ChatGroupFormData();
      exportFormData(formData);
      formData.setId(id);
      formData = BEANS.get(IChatGroupService.class).store(formData);
      importFormData(formData);
    }
  }
}
