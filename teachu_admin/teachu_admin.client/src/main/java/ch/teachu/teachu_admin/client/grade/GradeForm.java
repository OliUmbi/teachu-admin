package ch.teachu.teachu_admin.client.grade;

import ch.teachu.teachu_admin.client.grade.GradeForm.MainBox.GroupBox;
import ch.teachu.teachu_admin.shared.grade.GradeFormData;
import ch.teachu.teachu_admin.shared.grade.GradeStudentLookupCall;
import ch.teachu.teachu_admin.shared.grade.IGradeService;
import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.bigdecimalfield.AbstractBigDecimalField;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCancelButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractSmartField;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;

import java.math.BigDecimal;

@FormData(value = GradeFormData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class GradeForm extends AbstractForm {

  private String id;
  private String examId;

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("Mark");
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  public GroupBox getGroupBox() {
    return getFieldByClass(GroupBox.class);
  }

  public MainBox.OkButton getOkButton() {
    return getFieldByClass(MainBox.OkButton.class);
  }

  public MainBox.CancelButton getCancelButton() {
    return getFieldByClass(MainBox.CancelButton.class);
  }

  public GroupBox.MarkField getMarkField() {
    return getFieldByClass(GroupBox.MarkField.class);
  }

  public GroupBox.NotesField getNotesField() {
    return getFieldByClass(GroupBox.NotesField.class);
  }

  public GroupBox.StudentField getStudentField() {
    return getFieldByClass(GroupBox.StudentField.class);
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
  public String getExamId() {
    return examId;
  }

  @FormData
  public void setExamId(String examId) {
    this.examId = examId;
  }

  @Order(1000)
  public class MainBox extends AbstractGroupBox {
    @Order(1000)
    public class GroupBox extends AbstractGroupBox {
      @Order(1000)
      public class StudentField extends AbstractSmartField<String> {
        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Student");
        }

        @Override
        protected boolean getConfiguredMandatory() {
          return true;
        }

        @Override
        protected Class<? extends ILookupCall<String>> getConfiguredLookupCall() {
          return GradeStudentLookupCall.class;
        }

        @Override
        protected void execPrepareLookup(ILookupCall<String> call) {
          ((GradeStudentLookupCall) call).setExamId(examId);
          ((GradeStudentLookupCall) call).setGradeId(id);
        }
      }

      @Order(2000)
      public class MarkField extends AbstractBigDecimalField {
        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Mark");
        }

        @Override
        protected BigDecimal getConfiguredMinValue() {
          return new BigDecimal("0");
        }

        @Override
        protected BigDecimal getConfiguredMaxValue() {
          return new BigDecimal("6");
        }

        @Override
        protected boolean getConfiguredMandatory() {
          return true;
        }
      }

      @Order(3000)
      public class NotesField extends AbstractStringField {
        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Notes");
        }

        @Override
        protected int getConfiguredMaxLength() {
          return 4096;
        }

        @Override
        protected int getConfiguredGridW() {
          return 2;
        }

        @Override
        protected int getConfiguredGridH() {
          return 3;
        }

        @Override
        protected boolean getConfiguredMultilineText() {
          return true;
        }
      }
    }

    @Order(4000)
    public class OkButton extends AbstractOkButton {
    }

    @Order(5000)
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
      GradeFormData formData = new GradeFormData();
      exportFormData(formData);
      formData = BEANS.get(IGradeService.class).prepareCreate(formData);
      importFormData(formData);
    }

    @Override
    protected void execStore() {
      GradeFormData formData = new GradeFormData();
      exportFormData(formData);
      formData = BEANS.get(IGradeService.class).create(formData);
      importFormData(formData);
    }
  }

  public class ModifyHandler extends AbstractFormHandler {
    @Override
    protected void execLoad() {
      GradeFormData formData = new GradeFormData();
      exportFormData(formData);
      formData.setId(id);
      formData = BEANS.get(IGradeService.class).load(formData);
      importFormData(formData);
    }

    @Override
    protected void execStore() {
      GradeFormData formData = new GradeFormData();
      exportFormData(formData);
      formData.setId(id);
      formData = BEANS.get(IGradeService.class).store(formData);
      importFormData(formData);
    }
  }
}
