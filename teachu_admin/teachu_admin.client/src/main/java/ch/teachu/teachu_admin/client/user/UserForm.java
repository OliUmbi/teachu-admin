package ch.teachu.teachu_admin.client.user;

import ch.teachu.teachu_admin.client.user.UserForm.MainBox.CancelButton;
import ch.teachu.teachu_admin.client.user.UserForm.MainBox.GeneralBox;
import ch.teachu.teachu_admin.client.user.UserForm.MainBox.OkButton;
import ch.teachu.teachu_admin.shared.schoolconfig.ISchoolConfigService;
import ch.teachu.teachu_admin.shared.user.*;
import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.services.common.clipboard.IClipboardService;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.action.menu.IMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.ValueFieldMenuType;
import org.eclipse.scout.rt.client.ui.basic.table.ITableRow;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractSmartColumn;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.IValueField;
import org.eclipse.scout.rt.client.ui.form.fields.booleanfield.AbstractBooleanField;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCancelButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.datefield.AbstractDateField;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.longfield.AbstractLongField;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractSmartField;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.client.ui.form.fields.tabbox.AbstractTabBox;
import org.eclipse.scout.rt.client.ui.form.fields.tablefield.AbstractTableField;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.exception.VetoException;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.platform.util.ObjectUtility;
import org.eclipse.scout.rt.platform.util.StringUtility;
import org.eclipse.scout.rt.shared.services.common.code.ICodeType;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

@FormData(value = UserFormData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class UserForm extends AbstractForm {

  private String id;
  private String role;
  private final String emailDomain = BEANS.get(ISchoolConfigService.class).getConfig("EmailDomain");

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("User");
  }

  public MainBox.GeneralBox.ActiveField getActiveField() {
    return getFieldByClass(MainBox.GeneralBox.ActiveField.class);
  }

  public MainBox.GeneralBox.BirthdayField getBirthdayField() {
    return getFieldByClass(MainBox.GeneralBox.BirthdayField.class);
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  public GeneralBox getGroupBox() {
    return getFieldByClass(GeneralBox.class);
  }

  public OkButton getOkButton() {
    return getFieldByClass(OkButton.class);
  }

  public CancelButton getCancelButton() {
    return getFieldByClass(CancelButton.class);
  }

  public MainBox.DetailsBox.ParentChildBox getParentChildBox() {
    return getFieldByClass(MainBox.DetailsBox.ParentChildBox.class);
  }

  public MainBox.DetailsBox.ParentChildBox.ParentChildField getParentChildField() {
    return getFieldByClass(MainBox.DetailsBox.ParentChildBox.ParentChildField.class);
  }

  public MainBox.DetailsBox.ContactBox.CityField getCityField() {
    return getFieldByClass(MainBox.DetailsBox.ContactBox.CityField.class);
  }

  public MainBox.DetailsBox.ContactBox getContactBox() {
    return getFieldByClass(MainBox.DetailsBox.ContactBox.class);
  }

  public MainBox.DetailsBox getDetailsBox() {
    return getFieldByClass(MainBox.DetailsBox.class);
  }

  public GeneralBox.EmailField getEmailField() {
    return getFieldByClass(GeneralBox.EmailField.class);
  }

  public MainBox.GeneralBox.FirstNameField getFirstNameField() {
    return getFieldByClass(MainBox.GeneralBox.FirstNameField.class);
  }

  public MainBox.GeneralBox.LastNameField getLastNameField() {
    return getFieldByClass(MainBox.GeneralBox.LastNameField.class);
  }

  public MainBox.DetailsBox.NotesBox getNotesBox() {
    return getFieldByClass(MainBox.DetailsBox.NotesBox.class);
  }

  public MainBox.DetailsBox.NotesBox.NotesField getNotesField() {
    return getFieldByClass(MainBox.DetailsBox.NotesBox.NotesField.class);
  }

  public GeneralBox.PasswordField getPasswordField() {
    return getFieldByClass(GeneralBox.PasswordField.class);
  }

  public MainBox.DetailsBox.ContactBox.PhoneField getPhoneField() {
    return getFieldByClass(MainBox.DetailsBox.ContactBox.PhoneField.class);
  }

  public MainBox.DetailsBox.ContactBox.PostalCodeField getPostalCodeField() {
    return getFieldByClass(MainBox.DetailsBox.ContactBox.PostalCodeField.class);
  }

  public MainBox.GeneralBox.RoleField getRoleField() {
    return getFieldByClass(MainBox.GeneralBox.RoleField.class);
  }

  public MainBox.GeneralBox.SexField getSexField() {
    return getFieldByClass(MainBox.GeneralBox.SexField.class);
  }

  public MainBox.DetailsBox.ContactBox.StreetField getStreetField() {
    return getFieldByClass(MainBox.DetailsBox.ContactBox.StreetField.class);
  }

  public MainBox.GeneralBox.TerminationField getTerminationField() {
    return getFieldByClass(MainBox.GeneralBox.TerminationField.class);
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
    public class GeneralBox extends AbstractGroupBox {

      @Order(500)
      public class FirstNameField extends AbstractStringField {
        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("FirstName");
        }

        @Override
        protected int getConfiguredMaxLength() {
          return 128;
        }

        @Override
        protected boolean getConfiguredMandatory() {
          return true;
        }
      }

      @Order(1000)
      public class LastNameField extends AbstractStringField {
        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Lastname");
        }

        @Override
        protected int getConfiguredMaxLength() {
          return 128;
        }

        @Override
        protected boolean getConfiguredMandatory() {
          return true;
        }
      }

      @Order(1500)
      public class EmailField extends AbstractStringField {

        private static final String EMAIL_PATTERN =
          "^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@" +
            "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Email");
        }

        @Override
        protected int getConfiguredMaxLength() {
          return 64;
        }

        @Override
        protected String execValidateValue(String rawValue) {
          if (rawValue != null && !Pattern.matches(EMAIL_PATTERN, rawValue)) {
            throw new VetoException(TEXTS.get("BadEmailAddress"));
          }
          return rawValue;
        }

        @Override
        protected boolean getConfiguredMandatory() {
          return true;
        }

        @Override
        protected void execInitField() {
          getMenuByClass(GenerateEmailMenu.class).setVisible(emailDomain != null);
        }

        @Order(1000)
        public class GenerateEmailMenu extends AbstractMenu {
          @Override
          protected String getConfiguredText() {
            return TEXTS.get("GenerateEmail");
          }

          @Override
          protected Set<? extends IMenuType> getConfiguredMenuTypes() {
            return CollectionUtility.hashSet(ValueFieldMenuType.Null, ValueFieldMenuType.NotNull);
          }

          @Override
          protected void execAction() {
            final String firstName = getFirstNameField().getValue();
            final String lastName = getLastNameField().getValue();

            String beforeAtSymbol;
            if (StringUtility.isNullOrEmpty(firstName) || StringUtility.isNullOrEmpty(lastName)) {
              beforeAtSymbol = ObjectUtility.nvl(ObjectUtility.nvl(firstName, lastName), "");
            } else {
              beforeAtSymbol = firstName + "." + lastName;
            }

            getEmailField().setValue(beforeAtSymbol + "@" + emailDomain);
          }
        }
      }

      @Order(2000)
      public class PasswordField extends AbstractStringField {
        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Password");
        }

        @Override
        protected int getConfiguredMaxLength() {
          return 128;
        }

        @Override
        protected boolean getConfiguredInputMasked() {
          return true;
        }

        @Override
        protected String execValidateValue(String password) {
          Pattern UpperCasePatten = Pattern.compile("[A-Z ]");
          Pattern lowerCasePatten = Pattern.compile("[a-z ]");
          Pattern digitCasePatten = Pattern.compile("[0-9 ]");

          if (password == null) {
            return null;
          }
          if (password.length() < 8) {
            throw new VetoException(TEXTS.get("PasswordShort"));
          }
          if (!UpperCasePatten.matcher(password).find()) {
            throw new VetoException(TEXTS.get("PasswordNoUppercase"));
          }
          if (!lowerCasePatten.matcher(password).find()) {
            throw new VetoException(TEXTS.get("PasswordNoLowercase"));
          }
          if (!digitCasePatten.matcher(password).find()) {
            throw new VetoException(TEXTS.get("PasswordNoNumber"));
          }
          return password;
        }

        @Order(1000)
        public class GeneratePasswordMenu extends AbstractMenu {

          private static final int GENERATED_PASSWORD_LENGTH = 10;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("GeneratePassword");
          }

          @Override
          protected Set<? extends IMenuType> getConfiguredMenuTypes() {
            return CollectionUtility.hashSet(ValueFieldMenuType.Null, ValueFieldMenuType.NotNull);
          }

          @Override
          protected void execAction() {
            String password = generateRandomPassword();
            getPasswordField().setValue(password);
            BEANS.get(IClipboardService.class).setTextContents(password);
          }

          public String generateRandomPassword() {
            List<CharacterRule> rules = Arrays.asList(
              new CharacterRule(EnglishCharacterData.UpperCase, 1),
              new CharacterRule(EnglishCharacterData.LowerCase, 1),
              new CharacterRule(EnglishCharacterData.Digit, 1));
            return new PasswordGenerator().generatePassword(GENERATED_PASSWORD_LENGTH, rules);
          }
        }
      }

      @Order(2500)
      public class SexField extends AbstractSmartField<String> {
        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Sex");
        }

        @Override
        protected Class<? extends ICodeType<?, String>> getConfiguredCodeType() {
          return SexCodeType.class;
        }
      }

      @Order(3000)
      public class BirthdayField extends AbstractDateField {
        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Birthday");
        }
      }

      @Order(3500)
      public class RoleField extends AbstractSmartField<String> {
        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Role");
        }

        @Override
        protected Class<? extends ICodeType<?, String>> getConfiguredCodeType() {
          return RoleCodeType.class;
        }

        @Override
        protected boolean getConfiguredMandatory() {
          return true;
        }

        @Override
        protected void execChangedValue() {
          super.execChangedValue();
          role = getValue();
        }
      }

      @Order(4000)
      public class TerminationField extends AbstractDateField {
        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("TerminationDate");
        }
      }

      @Order(4500)
      public class ActiveField extends AbstractBooleanField {
        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Active");
        }
      }
    }

    @Order(3000)
    public class DetailsBox extends AbstractTabBox {
      @Order(1000)
      public class ContactBox extends AbstractGroupBox {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Contact");
        }

        @Order(500)
        public class PhoneField extends AbstractStringField {
          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Phone");
          }
        }

        @Order(1000)
        public class StreetField extends AbstractStringField {
          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Street");
          }

          @Override
          protected int getConfiguredMaxLength() {
            return 128;
          }
        }

        @Order(2000)
        public class PostalCodeField extends AbstractLongField {
          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("PostalCode");
          }

          @Override
          protected Long getConfiguredMinValue() {
            return 0L;
          }

          @Override
          protected Long getConfiguredMaxValue() {
            return 999999L;
          }

          @Override
          protected boolean getConfiguredGroupingUsed() {
            return false;
          }
        }

        @Order(3000)
        public class CityField extends AbstractStringField {
          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("City");
          }

          @Override
          protected int getConfiguredMaxLength() {
            return 128;
          }
        }
      }

      @Order(1750)
      public class ParentChildBox extends AbstractGroupBox {

        @Override
        protected void execInitField() {
          super.execInitField();
          getRoleField().addPropertyChangeListener(e -> {
            if (IValueField.PROP_VALUE.equals(e.getPropertyName())) {
              setVisible(ObjectUtility.isOneOf(role, RoleCodeType.StudentCode.ID, RoleCodeType.ParentCode.ID));
              setLabel(RoleCodeType.ParentCode.ID.equals(role) ? TEXTS.get("Children") : TEXTS.get("Parents"));
              getParentChildField().getTable().deleteAllRows();
            }
          });
        }

        @Order(1000)
        public class ParentChildField extends AbstractTableField<ParentChildField.ParentChildTable> {
          @Override
          protected boolean getConfiguredLabelVisible() {
            return false;
          }

          @Override
          protected boolean getConfiguredGridUseUiHeight() {
            return true;
          }

          public class ParentChildTable extends AbstractCrudTable {
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
              protected void execPrepareLookup(ILookupCall call, ITableRow row) {
                ((UserLookupCall) call).setRole(RoleCodeType.StudentCode.ID.equals(role) ? RoleCodeType.ParentCode.ID : RoleCodeType.StudentCode.ID);
              }
            }
          }
        }
      }

      @Order(2000)
      public class NotesBox extends AbstractGroupBox {
        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Notes");
        }

        @Override
        protected int getConfiguredGridColumnCount() {
          return 1;
        }

        @Override
        protected boolean getConfiguredGridUseUiHeight() {
          return true;
        }

        @Order(1000)
        public class NotesField extends AbstractStringField {

          @Override
          protected int getConfiguredMaxLength() {
            return 4096;
          }

          @Override
          protected boolean getConfiguredMultilineText() {
            return true;
          }

          @Override
          protected boolean getConfiguredGridUseUiHeight() {
            return true;
          }

          @Override
          protected boolean getConfiguredLabelVisible() {
            return false;
          }

          @Override
          protected int getConfiguredGridH() {
            return 4;
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
      UserFormData formData = new UserFormData();
      exportFormData(formData);
      formData = BEANS.get(IUserService.class).prepareCreate(formData);
      importFormData(formData);
      getPasswordField().setMandatory(true);
      getActiveField().setValue(true);
    }

    @Override
    protected void execStore() {
      UserFormData formData = new UserFormData();
      exportFormData(formData);
      formData = BEANS.get(IUserService.class).create(formData);
      importFormData(formData);
    }
  }

  public class ModifyHandler extends AbstractFormHandler {
    @Override
    protected void execLoad() {
      UserFormData formData = new UserFormData();
      exportFormData(formData);
      formData.setId(id);
      formData = BEANS.get(IUserService.class).load(formData);
      role = formData.getRole().getValue();
      importFormData(formData);
      getActiveField().setValue(formData.getActive().getValue() == null || formData.getActive().getValue());
      setSubTitle(formData.getFirstName().getValue() + " " + formData.getLastName().getValue());
    }

    @Override
    protected void execStore() {
      UserFormData formData = new UserFormData();
      exportFormData(formData);
      formData.setId(id);
      formData = BEANS.get(IUserService.class).store(formData);
      importFormData(formData);
    }
  }
}
