package ch.teachu.teachu_admin.client.timetable;

import ch.teachu.teachu_admin.client.timetable.TimetableForm.MainBox.CancelButton;
import ch.teachu.teachu_admin.client.timetable.TimetableForm.MainBox.GroupBox;
import ch.teachu.teachu_admin.client.timetable.TimetableForm.MainBox.OkButton;
import ch.teachu.teachu_admin.shared.timetable.ITimetableService;
import ch.teachu.teachu_admin.shared.timetable.TimetableFormData;
import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCancelButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.datefield.AbstractTimeField;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.TEXTS;

@FormData(value = TimetableFormData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class TimetableForm extends AbstractForm {

  private String id;

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("Timetable");
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

  public GroupBox.FromField getFromField() {
    return getFieldByClass(GroupBox.FromField.class);
  }

  public GroupBox.ToField getToField() {
    return getFieldByClass(GroupBox.ToField.class);
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
      public class FromField extends AbstractTimeField {
        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("FromDate");
        }

        @Override
        protected boolean getConfiguredMandatory() {
          return true;
        }
      }

      @Order(2000)
      public class ToField extends AbstractTimeField {
        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("ToDate");
        }

        @Override
        protected boolean getConfiguredMandatory() {
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
      TimetableFormData formData = new TimetableFormData();
      exportFormData(formData);
      formData = BEANS.get(ITimetableService.class).prepareCreate(formData);
      importFormData(formData);
    }

    @Override
    protected void execStore() {
      TimetableFormData formData = new TimetableFormData();
      exportFormData(formData);
      formData = BEANS.get(ITimetableService.class).create(formData);
      importFormData(formData);
    }
  }

  public class ModifyHandler extends AbstractFormHandler {
    @Override
    protected void execLoad() {
      TimetableFormData formData = new TimetableFormData();
      exportFormData(formData);
      formData.setId(id);
      formData = BEANS.get(ITimetableService.class).load(formData);
      importFormData(formData);
    }

    @Override
    protected void execStore() {
      TimetableFormData formData = new TimetableFormData();
      exportFormData(formData);
      formData.setId(id);
      formData = BEANS.get(ITimetableService.class).store(formData);
      importFormData(formData);
    }
  }
}
