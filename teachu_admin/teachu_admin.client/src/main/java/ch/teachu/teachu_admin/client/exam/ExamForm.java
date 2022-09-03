package ch.teachu.teachu_admin.client.exam;

import ch.teachu.teachu_admin.client.exam.ExamForm.MainBox.CancelButton;
import ch.teachu.teachu_admin.client.exam.ExamForm.MainBox.GroupBox;
import ch.teachu.teachu_admin.client.exam.ExamForm.MainBox.OkButton;
import ch.teachu.teachu_admin.shared.AccessHelper;
import ch.teachu.teachu_admin.shared.exam.ExamFormData;
import ch.teachu.teachu_admin.shared.exam.IExamService;
import ch.teachu.teachu_admin.shared.schoolclass.SchoolClassLookupCall;
import ch.teachu.teachu_admin.shared.schoolclass.subject.SchoolClassSubjectLookupCall;
import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.bigdecimalfield.AbstractBigDecimalField;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCancelButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.datefield.AbstractDateField;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractSmartField;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;

import java.math.BigDecimal;

@FormData(value = ExamFormData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class ExamForm extends AbstractForm {

  private String id;
  private boolean schoolClassSubjectSet = false;

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("Exam");
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

  public GroupBox.DateField getDateField() {
    return getFieldByClass(GroupBox.DateField.class);
  }

  public GroupBox.DescriptionField getDescriptionField() {
    return getFieldByClass(GroupBox.DescriptionField.class);
  }

  public GroupBox.NameField getNameField() {
    return getFieldByClass(GroupBox.NameField.class);
  }

  public GroupBox.SchoolClassField getSchoolClassField() {
    return getFieldByClass(GroupBox.SchoolClassField.class);
  }

  public GroupBox.SubjectField getSubjectField() {
    return getFieldByClass(GroupBox.SubjectField.class);
  }

  public GroupBox.ViewDateField getViewDateField() {
    return getFieldByClass(GroupBox.ViewDateField.class);
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

  @FormData
  public String getSchoolClassSubjectId() {
    return getSubjectField().getValue();
  }

  @FormData
  public void setSchoolClassSubjectId(String schoolClassSubjectId) {
    getSubjectField().setValue(schoolClassSubjectId);
    schoolClassSubjectSet = schoolClassSubjectId != null;
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
          return new BigDecimal("9999");
        }

        @Override
        protected boolean getConfiguredMandatory() {
          return true;
        }
      }

      @Order(3000)
      public class DateField extends AbstractDateField {
        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Date");
        }
      }

      @Order(4000)
      public class ViewDateField extends AbstractDateField {
        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("ViewDate");
        }
      }

      @Order(5000)
      public class SchoolClassField extends AbstractSmartField<String> {

        private boolean firstTime = true;

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("SchoolClass");
        }

        @Override
        protected Class<? extends ILookupCall<String>> getConfiguredLookupCall() {
          return SchoolClassLookupCall.class;
        }

        @Override
        protected void execPrepareLookup(ILookupCall<String> call) {
          ((SchoolClassLookupCall) call).setTeacherId(BEANS.get(AccessHelper.class).getCurrentUserId());
        }

        @Override
        protected boolean getConfiguredMandatory() {
          return true;
        }

        @Override
        protected void execInitField() {
          setVisible(!schoolClassSubjectSet);
          setMandatory(!schoolClassSubjectSet);
        }

        @Override
        protected void execChangedValue() {
          if (firstTime) {
            firstTime = false;
            getSubjectField().setValue(null);
          }
        }
      }

      @Order(6000)
      public class SubjectField extends AbstractSmartField<String> {
        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("ClassSubject");
        }

        @Override
        protected boolean getConfiguredMandatory() {
          return true;
        }

        @Override
        protected void execInitField() {
          setVisible(!schoolClassSubjectSet);
          setMandatory(!schoolClassSubjectSet);
        }

        @Override
        protected Class<? extends ILookupCall<String>> getConfiguredLookupCall() {
          return SchoolClassSubjectLookupCall.class;
        }

        @Override
        protected void execPrepareLookup(ILookupCall<String> call) {
          ((SchoolClassSubjectLookupCall) call).setSchoolClassId(getSchoolClassField().getValue());
          ((SchoolClassSubjectLookupCall) call).setTeacherId(BEANS.get(AccessHelper.class).getCurrentUserId());
        }
      }

      @Order(7000)
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
      ExamFormData formData = new ExamFormData();
      exportFormData(formData);
      formData = BEANS.get(IExamService.class).prepareCreate(formData);
      importFormData(formData);
    }

    @Override
    protected void execStore() {
      ExamFormData formData = new ExamFormData();
      exportFormData(formData);
      formData = BEANS.get(IExamService.class).create(formData);
      importFormData(formData);
    }
  }

  public class ModifyHandler extends AbstractFormHandler {
    @Override
    protected void execLoad() {
      ExamFormData formData = new ExamFormData();
      exportFormData(formData);
      formData.setId(id);
      formData = BEANS.get(IExamService.class).load(formData);
      importFormData(formData);
    }

    @Override
    protected void execStore() {
      ExamFormData formData = new ExamFormData();
      exportFormData(formData);
      formData.setId(id);
      formData = BEANS.get(IExamService.class).store(formData);
      importFormData(formData);
    }
  }
}
