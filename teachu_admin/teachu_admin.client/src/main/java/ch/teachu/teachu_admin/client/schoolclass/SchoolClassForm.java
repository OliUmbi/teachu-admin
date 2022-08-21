package ch.teachu.teachu_admin.client.schoolclass;

import ch.teachu.teachu_admin.client.schoolclass.SchoolClassForm.MainBox.CancelButton;
import ch.teachu.teachu_admin.client.schoolclass.SchoolClassForm.MainBox.GroupBox;
import ch.teachu.teachu_admin.client.schoolclass.SchoolClassForm.MainBox.OkButton;
import ch.teachu.teachu_admin.client.user.AbstractCrudTable;
import ch.teachu.teachu_admin.shared.AdminPermission;
import ch.teachu.teachu_admin.shared.schoolclass.ISchoolClassService;
import ch.teachu.teachu_admin.shared.schoolclass.SchoolClassFormData;
import ch.teachu.teachu_admin.shared.schoolclass.SemesterLookupCall;
import ch.teachu.teachu_admin.shared.user.RoleCodeType;
import ch.teachu.teachu_admin.shared.user.UserLookupCall;
import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.ui.basic.table.ITableRow;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractSmartColumn;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCancelButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractSmartField;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.client.ui.form.fields.tabbox.AbstractTabBox;
import org.eclipse.scout.rt.client.ui.form.fields.tablefield.AbstractTableField;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.security.ACCESS;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;

@FormData(value = SchoolClassFormData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class SchoolClassForm extends AbstractForm {

  private String id;

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("SchoolClass");
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

  public GroupBox.GeneralBox.ClassTeacherField getClassTeacherField() {
    return getFieldByClass(GroupBox.GeneralBox.ClassTeacherField.class);
  }

  public GroupBox.GeneralBox getGeneralBox() {
    return getFieldByClass(GroupBox.GeneralBox.class);
  }

  public GroupBox.GeneralBox.ListsBox getListsBox() {
    return getFieldByClass(GroupBox.GeneralBox.ListsBox.class);
  }

  public GroupBox.GeneralBox.NameField getNameField() {
    return getFieldByClass(GroupBox.GeneralBox.NameField.class);
  }

  public GroupBox.GeneralBox.ListsBox.SemestersBox getSemestersBox() {
    return getFieldByClass(GroupBox.GeneralBox.ListsBox.SemestersBox.class);
  }

  public GroupBox.GeneralBox.ListsBox.SemestersBox.SemestersField getSemestersField() {
    return getFieldByClass(GroupBox.GeneralBox.ListsBox.SemestersBox.SemestersField.class);
  }

  public GroupBox.GeneralBox.ListsBox.StudentsBox getStudentsBox() {
    return getFieldByClass(GroupBox.GeneralBox.ListsBox.StudentsBox.class);
  }

  public GroupBox.GeneralBox.ListsBox.StudentsBox.StudentsField getStudentsField() {
    return getFieldByClass(GroupBox.GeneralBox.ListsBox.StudentsBox.StudentsField.class);
  }

  @FormData
  public String getId() {
    return id;
  }

  @FormData
  public void setId(String id) {
    this.id = id;
  }

  @Override
  protected int getConfiguredDisplayHint() {
    return DISPLAY_HINT_VIEW;
  }

  @Order(1000)
  public class MainBox extends AbstractGroupBox {
    @Order(1000)
    public class GroupBox extends AbstractGroupBox {
      @Order(1000)
      public class GeneralBox extends AbstractGroupBox {
        @Override
        protected boolean getConfiguredLabelVisible() {
          return false;
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
        public class ClassTeacherField extends AbstractSmartField<String> {
          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("ClassTeacher");
          }

          @Override
          protected boolean getConfiguredMandatory() {
            return true;
          }

          @Override
          protected Class<? extends ILookupCall<String>> getConfiguredLookupCall() {
            return UserLookupCall.class;
          }

          @Override
          protected void execPrepareLookup(ILookupCall<String> call) {
            ((UserLookupCall) call).setRole(RoleCodeType.TeacherCode.ID);
          }
        }

        @Order(2500)
        public class ListsBox extends AbstractTabBox {
          @Order(1000)
          public class StudentsBox extends AbstractGroupBox {
            @Override
            protected String getConfiguredLabel() {
              return TEXTS.get("Students");
            }

            @Order(3000)
            public class StudentsField extends AbstractTableField<StudentsField.Table> {

              @Override
              protected boolean getConfiguredLabelVisible() {
                return false;
              }

              @Override
              protected int getConfiguredGridH() {
                return 6;
              }

              @ClassId("ed8f34e5-f8a9-4d96-bc2a-2f363e5974f5")
              public class Table extends AbstractCrudTable {
                @Order(1000)
                public class NameColumn extends AbstractSmartColumn<String> {
                  @Override
                  protected String getConfiguredHeaderText() {
                    return TEXTS.get("Name");
                  }

                  @Override
                  protected int getConfiguredWidth() {
                    return 500;
                  }

                  @Override
                  protected boolean getConfiguredEditable() {
                    return true;
                  }

                  @Override
                  protected Class<? extends ILookupCall<String>> getConfiguredLookupCall() {
                    return UserLookupCall.class;
                  }

                  @Override
                  protected void execPrepareLookup(ILookupCall<String> call, ITableRow row) {
                    ((UserLookupCall) call).setRole(RoleCodeType.StudentCode.ID);
                  }
                }
              }
            }
          }

          @Order(2000)
          public class SemestersBox extends AbstractGroupBox {
            @Override
            protected String getConfiguredLabel() {
              return TEXTS.get("Semesters");
            }

            @Order(1000)
            public class SemestersField extends AbstractTableField<SemestersField.Table> {

              @Override
              protected boolean getConfiguredLabelVisible() {
                return false;
              }

              @Override
              protected int getConfiguredGridH() {
                return 6;
              }

              @ClassId("71d80301-4f40-487d-8175-9c3192b48d2c")
              public class Table extends AbstractCrudTable {
                @Order(1000)
                public class NameColumn extends AbstractSmartColumn<String> {
                  @Override
                  protected String getConfiguredHeaderText() {
                    return TEXTS.get("Name");
                  }

                  @Override
                  protected int getConfiguredWidth() {
                    return 500;
                  }

                  @Override
                  protected boolean getConfiguredEditable() {
                    return true;
                  }

                  @Override
                  protected Class<? extends ILookupCall<String>> getConfiguredLookupCall() {
                    return SemesterLookupCall.class;
                  }
                }
              }
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
      SchoolClassFormData formData = new SchoolClassFormData();
      exportFormData(formData);
      formData = BEANS.get(ISchoolClassService.class).prepareCreate(formData);
      importFormData(formData);
    }

    @Override
    protected void execStore() {
      SchoolClassFormData formData = new SchoolClassFormData();
      exportFormData(formData);
      formData = BEANS.get(ISchoolClassService.class).create(formData);
      importFormData(formData);
    }
  }

  public class ModifyHandler extends AbstractFormHandler {
    @Override
    protected void execLoad() {
      SchoolClassFormData formData = new SchoolClassFormData();
      exportFormData(formData);
      formData.setId(id);
      formData = BEANS.get(ISchoolClassService.class).load(formData);
      setEnabledGranted(ACCESS.check(new AdminPermission()));
      importFormData(formData);
    }

    @Override
    protected void execStore() {
      SchoolClassFormData formData = new SchoolClassFormData();
      exportFormData(formData);
      formData.setId(id);
      formData = BEANS.get(ISchoolClassService.class).store(formData);
      importFormData(formData);
    }
  }
}
