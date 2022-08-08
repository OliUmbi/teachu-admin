package ch.teachu.teachu_admin.client.subject;

import ch.teachu.teachu_admin.client.subject.SubjectForm.MainBox.CancelButton;
import ch.teachu.teachu_admin.client.subject.SubjectForm.MainBox.GroupBox;
import ch.teachu.teachu_admin.client.subject.SubjectForm.MainBox.OkButton;
import ch.teachu.teachu_admin.shared.subject.ISubjectService;
import ch.teachu.teachu_admin.shared.subject.SubjectFormData;
import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.bigdecimalfield.AbstractBigDecimalField;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCancelButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.TEXTS;

import java.math.BigDecimal;

@FormData(value = SubjectFormData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class SubjectForm extends AbstractForm {

  private String id;

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("Subject");
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

  public GroupBox.SubjectField getSubjectField() {
    return getFieldByClass(GroupBox.SubjectField.class);
  }

  public GroupBox.WeightField getWeightField() {
    return getFieldByClass(GroupBox.WeightField.class);
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
      public class SubjectField extends AbstractStringField {
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
      public class WeightField extends AbstractBigDecimalField {
        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Weight");
        }

        @Override
        protected BigDecimal getConfiguredMinValue() {
          return new BigDecimal("0");
        }

        @Override
        protected BigDecimal getConfiguredMaxValue() {
          return new BigDecimal("999999999999999999");
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
      SubjectFormData formData = new SubjectFormData();
      exportFormData(formData);
      formData = BEANS.get(ISubjectService.class).prepareCreate(formData);
      importFormData(formData);
    }

    @Override
    protected void execStore() {
      SubjectFormData formData = new SubjectFormData();
      exportFormData(formData);
      formData = BEANS.get(ISubjectService.class).create(formData);
      importFormData(formData);
    }
  }

  public class ModifyHandler extends AbstractFormHandler {
    @Override
    protected void execLoad() {
      SubjectFormData formData = new SubjectFormData();
      exportFormData(formData);
      formData.setId(id);
      formData = BEANS.get(ISubjectService.class).load(formData);
      importFormData(formData);
    }

    @Override
    protected void execStore() {
      SubjectFormData formData = new SubjectFormData();
      exportFormData(formData);
      formData.setId(id);
      formData = BEANS.get(ISubjectService.class).store(formData);
      importFormData(formData);
    }
  }
}
