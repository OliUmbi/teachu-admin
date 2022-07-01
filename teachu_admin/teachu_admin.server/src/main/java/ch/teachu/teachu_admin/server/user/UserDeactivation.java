package ch.teachu.teachu_admin.server.user;

import org.eclipse.scout.rt.platform.ApplicationScoped;
import org.eclipse.scout.rt.platform.context.RunContexts;
import org.eclipse.scout.rt.platform.job.FixedDelayScheduleBuilder;
import org.eclipse.scout.rt.platform.job.Jobs;
import org.eclipse.scout.rt.platform.security.SimplePrincipal;
import org.eclipse.scout.rt.server.jdbc.SQL;

import javax.security.auth.Subject;
import java.util.concurrent.TimeUnit;

@ApplicationScoped
public class UserDeactivation {

  public static final int DELAY_IN_MINUTES = 5;

  public UserDeactivation() {
    Jobs.schedule(this::deactivateUsers, Jobs.newInput()
      .withName("UserDeactivation")
      .withRunContext(RunContexts.empty()
        .withSubject(createSubject()))
      .withExecutionTrigger(Jobs.newExecutionTrigger()
        .withSchedule(FixedDelayScheduleBuilder
          .repeatForever(DELAY_IN_MINUTES, TimeUnit.MINUTES))));
  }

  private Subject createSubject() {
    Subject subject = new Subject();
    subject.getPrincipals().add(new SimplePrincipal("_userdeactivation"));
    subject.setReadOnly();
    return subject;
  }

  private void deactivateUsers() {
    SQL.delete("UPDATE user SET ACTIVE = FALSE WHERE termination_date < current_timestamp");
  }
}
