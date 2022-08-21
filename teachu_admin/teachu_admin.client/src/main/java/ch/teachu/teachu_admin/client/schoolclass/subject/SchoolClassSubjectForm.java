package ch.teachu.teachu_admin.client.schoolclass.subject;

import ch.teachu.teachu_admin.client.schoolclass.subject.SchoolClassSubjectForm.MainBox.CancelButton;
import ch.teachu.teachu_admin.client.schoolclass.subject.SchoolClassSubjectForm.MainBox.GroupBox;
import ch.teachu.teachu_admin.client.schoolclass.subject.SchoolClassSubjectForm.MainBox.OkButton;
import ch.teachu.teachu_admin.shared.lesson.SubjectLookupCall;
import ch.teachu.teachu_admin.shared.schoolclass.subject.ISchoolClassSubjectService;
import ch.teachu.teachu_admin.shared.schoolclass.subject.SchoolClassSubjectFormData;
import ch.teachu.teachu_admin.shared.user.RoleCodeType;
import ch.teachu.teachu_admin.shared.user.UserLookupCall;
import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCancelButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.datefield.AbstractDateField;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractSmartField;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.exception.VetoException;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;

import java.util.Date;

@FormData(value = SchoolClassSubjectFormData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class SchoolClassSubjectForm extends AbstractForm {

  private String id;
  private String schoolClassId;

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("SchoolClassSubject");
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

  public GroupBox.EndDateField getEndDateField() {
    return getFieldByClass(GroupBox.EndDateField.class);
  }

  public GroupBox.NotesField getNotesField() {
    return getFieldByClass(GroupBox.NotesField.class);
  }

  public GroupBox.StartDateField getStartDateField() {
    return getFieldByClass(GroupBox.StartDateField.class);
  }

  public GroupBox.SubjectField getSubjectField() {
    return getFieldByClass(GroupBox.SubjectField.class);
  }

  public GroupBox.TeacherField getTeacherField() {
    return getFieldByClass(GroupBox.TeacherField.class);
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
  public String getSchoolClassId() {
    return schoolClassId;
  }

  @FormData
  public void setSchoolClassId(String schoolClassId) {
    this.schoolClassId = schoolClassId;
  }

  @Order(1000)
  public class MainBox extends AbstractGroupBox {
    @Order(1000)
    public class GroupBox extends AbstractGroupBox {

      @Order(1000)
      public class SubjectField extends AbstractSmartField<String> {
        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("ClassSubject");
        }

        @Override
        protected Class<? extends ILookupCall<String>> getConfiguredLookupCall() {
          return SubjectLookupCall.class;
        }

        @Override
        protected boolean getConfiguredMandatory() {
          return true;
        }

        @Override
        protected String execValidateValue(String rawValue) {
          if (BEANS.get(ISchoolClassSubjectService.class).usedSubject(id, schoolClassId, rawValue)) {
            throw new VetoException(TEXTS.get("UsedSubject"));
          }
          return rawValue;
        }
      }

      @Order(2000)
      public class TeacherField extends AbstractSmartField<String> {
        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Teacher");
        }

        @Override
        protected Class<? extends ILookupCall<String>> getConfiguredLookupCall() {
          return UserLookupCall.class;
        }

        @Override
        protected void execPrepareLookup(ILookupCall<String> call) {
          ((UserLookupCall) call).setRole(RoleCodeType.TeacherCode.ID);
        }

        @Override
        protected boolean getConfiguredMandatory() {
          return true;
        }
      }

      @Order(3000)
      public class StartDateField extends AbstractDateField {
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
          if (getEndDateField().getValue() != null && !getEndDateField().getValue().after(rawValue)) {
            throw new VetoException(TEXTS.get("ToNotAfterFrom"));
          }

          getEndDateField().clearErrorStatus();
          return rawValue;
        }
      }

      @Order(4000)
      public class EndDateField extends AbstractDateField {
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
          if (getStartDateField().getValue() != null && !rawValue.after(getStartDateField().getValue())) {
            throw new VetoException(TEXTS.get("ToNotAfterFrom"));
          }

          getStartDateField().clearErrorStatus();
          return rawValue;
        }
      }

      @Order(5000)
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
      SchoolClassSubjectFormData formData = new SchoolClassSubjectFormData();
      exportFormData(formData);
      formData = BEANS.get(ISchoolClassSubjectService.class).prepareCreate(formData);
      importFormData(formData);
    }

    @Override
    protected void execStore() {
      SchoolClassSubjectFormData formData = new SchoolClassSubjectFormData();
      exportFormData(formData);
      formData = BEANS.get(ISchoolClassSubjectService.class).create(formData);
      importFormData(formData);
    }
  }

  public class ModifyHandler extends AbstractFormHandler {
    @Override
    protected void execLoad() {
      SchoolClassSubjectFormData formData = new SchoolClassSubjectFormData();
      exportFormData(formData);
      formData.setId(id);
      formData.setSchoolClassId(schoolClassId);
      formData = BEANS.get(ISchoolClassSubjectService.class).load(formData);
      importFormData(formData);
    }

    @Override
    protected void execStore() {
      SchoolClassSubjectFormData formData = new SchoolClassSubjectFormData();
      exportFormData(formData);
      formData.setId(id);
      formData.setSchoolClassId(schoolClassId);
      formData = BEANS.get(ISchoolClassSubjectService.class).store(formData);
      importFormData(formData);
    }
  }
}
