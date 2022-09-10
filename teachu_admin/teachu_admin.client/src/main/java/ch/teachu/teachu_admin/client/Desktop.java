package ch.teachu.teachu_admin.client;

import ch.teachu.teachu_admin.client.Desktop.UserProfileMenu.ThemeMenu.DarkThemeMenu;
import ch.teachu.teachu_admin.client.Desktop.UserProfileMenu.ThemeMenu.DefaultThemeMenu;
import ch.teachu.teachu_admin.client.admin.AdminOutline;
import ch.teachu.teachu_admin.client.chatgroup.ChatGroupForm;
import ch.teachu.teachu_admin.client.event.school.SchoolEventForm;
import ch.teachu.teachu_admin.client.room.RoomForm;
import ch.teachu.teachu_admin.client.schoolclass.SchoolClassForm;
import ch.teachu.teachu_admin.client.schoolinfo.SchoolInfoForm;
import ch.teachu.teachu_admin.client.semester.SemesterForm;
import ch.teachu.teachu_admin.client.subject.SubjectForm;
import ch.teachu.teachu_admin.client.teacher.TeacherOutline;
import ch.teachu.teachu_admin.client.user.UserForm;
import ch.teachu.teachu_admin.shared.AdminPermission;
import ch.teachu.teachu_admin.shared.Icons;
import org.eclipse.scout.rt.client.session.ClientSessionProvider;
import org.eclipse.scout.rt.client.ui.action.keystroke.IKeyStroke;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.action.menu.IMenu;
import org.eclipse.scout.rt.client.ui.desktop.AbstractDesktop;
import org.eclipse.scout.rt.client.ui.desktop.notification.NativeNotificationDefaults;
import org.eclipse.scout.rt.client.ui.desktop.outline.AbstractOutlineViewButton;
import org.eclipse.scout.rt.client.ui.desktop.outline.IOutline;
import org.eclipse.scout.rt.client.ui.form.ScoutInfoForm;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.platform.util.StringUtility;
import org.eclipse.scout.rt.security.ACCESS;
import org.eclipse.scout.rt.security.IAccessControlService;

import java.beans.PropertyChangeEvent;
import java.util.List;

public class Desktop extends AbstractDesktop {

  public Desktop() {
    addPropertyChangeListener(PROP_THEME, this::onThemeChanged);
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("ApplicationTitle");
  }

  @Override
  protected String getConfiguredLogoId() {
    return Icons.AppLogo;
  }

  @Override
  protected NativeNotificationDefaults getConfiguredNativeNotificationDefaults() {
    return super.getConfiguredNativeNotificationDefaults().withIconId("application_logo.png");
  }

  @Override
  protected List<Class<? extends IOutline>> getConfiguredOutlines() {
    return CollectionUtility.arrayList(AdminOutline.class, TeacherOutline.class);
  }

  @Override
  protected void execDefaultView() {
    selectFirstVisibleOutline();
  }

  protected void selectFirstVisibleOutline() {
    for (IOutline outline : getAvailableOutlines()) {
      if (outline.isEnabled() && outline.isVisible()) {
        setOutline(outline.getClass());
        return;
      }
    }
  }

  protected void onThemeChanged(PropertyChangeEvent evt) {
    IMenu darkMenu = getMenuByClass(DarkThemeMenu.class);
    IMenu defaultMenu = getMenuByClass(DefaultThemeMenu.class);
    String newThemeName = (String) evt.getNewValue();
    if (DarkThemeMenu.DARK_THEME.equalsIgnoreCase(newThemeName)) {
      darkMenu.setIconId(Icons.CheckedBold);
      defaultMenu.setIconId(null);
    } else {
      darkMenu.setIconId(null);
      defaultMenu.setIconId(Icons.CheckedBold);
    }
  }

  @Order(500)
  public class CreateMenu extends AbstractMenu {
    @Override
    protected String getConfiguredText() {
      return TEXTS.get("Create");
    }

    @Override
    protected String getConfiguredKeyStroke() {
      return IKeyStroke.F8;
    }

    @Override
    protected String getConfiguredIconId() {
      return Icons.Plus;
    }

    @Order(0)
    public class UserMenu extends AbstractMenu {
      @Override
      protected String getConfiguredText() {
        return TEXTS.get("User");
      }

      @Override
      protected void execAction() {
        new UserForm().startNew();
      }

      @Override
      protected boolean getConfiguredVisible() {
        return ACCESS.check(new AdminPermission());
      }
    }

    @Order(1000)
    public class SchoolInfoMenu extends AbstractMenu {
      @Override
      protected String getConfiguredText() {
        return TEXTS.get("SchoolInfo");
      }

      @Override
      protected void execAction() {
        new SchoolInfoForm().startNew();
      }

      @Override
      protected boolean getConfiguredVisible() {
        return ACCESS.check(new AdminPermission());
      }
    }

    @Order(2000)
    public class SchoolEventMenu extends AbstractMenu {
      @Override
      protected String getConfiguredText() {
        return TEXTS.get("Event");
      }

      @Override
      protected void execAction() {
        new SchoolEventForm().startNew();
      }

      protected boolean getConfiguredVisible() {
        return ACCESS.check(new AdminPermission());
      }
    }

    @Order(3000)
    public class SubjectMenu extends AbstractMenu {
      @Override
      protected String getConfiguredText() {
        return TEXTS.get("ClassSubject");
      }

      @Override
      protected void execAction() {
        new SubjectForm().startNew();
      }

      protected boolean getConfiguredVisible() {
        return ACCESS.check(new AdminPermission());
      }
    }

    @Order(4000)
    public class SemesterMenu extends AbstractMenu {
      @Override
      protected String getConfiguredText() {
        return TEXTS.get("Semester");
      }

      @Override
      protected void execAction() {
        new SemesterForm().startNew();
      }

      protected boolean getConfiguredVisible() {
        return ACCESS.check(new AdminPermission());
      }
    }

    @Order(5000)
    public class RoomMenu extends AbstractMenu {
      @Override
      protected String getConfiguredText() {
        return TEXTS.get("Room");
      }

      @Override
      protected void execAction() {
        new RoomForm().startNew();
      }

      protected boolean getConfiguredVisible() {
        return ACCESS.check(new AdminPermission());
      }
    }

    @Order(6000)
    public class SchoolClassMenu extends AbstractMenu {
      @Override
      protected String getConfiguredText() {
        return TEXTS.get("SchoolClass");
      }

      @Override
      protected void execAction() {
        new SchoolClassForm().startNew();
      }

      protected boolean getConfiguredVisible() {
        return ACCESS.check(new AdminPermission());
      }
    }

    @Order(7000)
    public class ChatGroupMenu extends AbstractMenu {
      @Override
      protected String getConfiguredText() {
        return TEXTS.get("ChatGroup");
      }

      @Override
      protected void execAction() {
        new ChatGroupForm().startNew();
      }
    }
  }

  @Order(1000)
  public class UserProfileMenu extends AbstractMenu {

    @Override
    protected String getConfiguredKeyStroke() {
      return IKeyStroke.F10;
    }

    @Override
    protected String getConfiguredIconId() {
      return Icons.PersonSolid;
    }

    @Override
    protected String getConfiguredText() {
      String userId = BEANS.get(IAccessControlService.class).getUserIdOfCurrentSubject();
      return StringUtility.uppercaseFirst(userId);
    }

    @Order(1000)
    public class AboutMenu extends AbstractMenu {

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("About");
      }

      @Override
      protected void execAction() {
        ScoutInfoForm form = new ScoutInfoForm();
        form.startModify();
      }
    }

    @Order(2000)
    public class ThemeMenu extends AbstractMenu {

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("Theme");
      }

      @Order(1000)
      public class DefaultThemeMenu extends AbstractMenu {

        private static final String DEFAULT_THEME = "Default";

        @Override
        protected String getConfiguredText() {
          return TEXTS.get("DefaultTheme");
        }

        @Override
        protected void execAction() {
          setTheme(DEFAULT_THEME.toLowerCase());
        }
      }

      @Order(2000)
      public class DarkThemeMenu extends AbstractMenu {

        private static final String DARK_THEME = "Dark";

        @Override
        protected String getConfiguredText() {
          return TEXTS.get("DarkTheme");
        }

        @Override
        protected void execAction() {
          setTheme(DARK_THEME.toLowerCase());
        }
      }
    }

    @Order(3000)
    public class LogoutMenu extends AbstractMenu {

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("Logout");
      }

      @Override
      protected void execAction() {
        ClientSessionProvider.currentSession().stop();
      }
    }
  }

  @Order(1000)
  public class AdminOutlineViewButton extends AbstractOutlineViewButton {

    public AdminOutlineViewButton() {
      this(AdminOutline.class);
    }

    protected AdminOutlineViewButton(Class<? extends AdminOutline> outlineClass) {
      super(Desktop.this, outlineClass);
    }

    @Override
    protected String getConfiguredKeyStroke() {
      return IKeyStroke.F2;
    }
  }

  @Order(2000)
  public class TeacherOutlineViewButton extends AbstractOutlineViewButton {

    public TeacherOutlineViewButton() {
      this(TeacherOutline.class);
    }

    protected TeacherOutlineViewButton(Class<? extends TeacherOutline> outlineClass) {
      super(Desktop.this, outlineClass);
    }

    @Override
    protected String getConfiguredKeyStroke() {
      return IKeyStroke.F4;
    }
  }
}
