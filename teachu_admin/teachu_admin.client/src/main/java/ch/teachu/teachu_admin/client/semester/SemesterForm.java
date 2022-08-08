package ch.teachu.teachu_admin.client.semester;

import ch.teachu.teachu_admin.client.semester.SemesterForm.MainBox.CancelButton;
import ch.teachu.teachu_admin.client.semester.SemesterForm.MainBox.GroupBox;
import ch.teachu.teachu_admin.client.semester.SemesterForm.MainBox.OkButton;
import ch.teachu.teachu_admin.shared.semester.ISemesterService;
import ch.teachu.teachu_admin.shared.semester.SemesterFormData;
import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCancelButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.datefield.AbstractDateField;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.exception.VetoException;
import org.eclipse.scout.rt.platform.text.TEXTS;

import java.util.Date;

@FormData(value = SemesterFormData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class SemesterForm extends AbstractForm {

  private String id;

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("Semester");
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

  public GroupBox.NameField getNameField() {
    return getFieldByClass(GroupBox.NameField.class);
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
        protected int getConfiguredGridW() {
          return 2;
        }

        @Override
        protected boolean getConfiguredMandatory() {
          return true;
        }
      }

      @Order(2000)
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
          if (BEANS.get(ISemesterService.class).overlapsWithOtherSemesters(id, rawValue, getToField().getValue())) {
            throw new VetoException(TEXTS.get("OverlapsWithOtherSemesters"));
          }
          return rawValue;
        }
      }

      @Order(3000)
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
          if (BEANS.get(ISemesterService.class).overlapsWithOtherSemesters(id, getFromField().getValue(), rawValue)) {
            throw new VetoException(TEXTS.get("OverlapsWithOtherSemesters"));
          }
          return rawValue;
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
      SemesterFormData formData = new SemesterFormData();
      exportFormData(formData);
      formData = BEANS.get(ISemesterService.class).prepareCreate(formData);
      importFormData(formData);
    }

    @Override
    protected void execStore() {
      SemesterFormData formData = new SemesterFormData();
      exportFormData(formData);
      formData = BEANS.get(ISemesterService.class).create(formData);
      importFormData(formData);
    }
  }

  public class ModifyHandler extends AbstractFormHandler {
    @Override
    protected void execLoad() {
      SemesterFormData formData = new SemesterFormData();
      exportFormData(formData);
      formData.setId(id);
      formData = BEANS.get(ISemesterService.class).load(formData);
      importFormData(formData);
    }

    @Override
    protected void execStore() {
      SemesterFormData formData = new SemesterFormData();
      exportFormData(formData);
      formData.setId(id);
      formData = BEANS.get(ISemesterService.class).store(formData);
      importFormData(formData);
    }
  }
}
