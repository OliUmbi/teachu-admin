package ch.teachu.teachu_admin.ui.html;

import ch.teachu.teachu_admin.shared.security.IAuthService;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Bean;
import org.eclipse.scout.rt.platform.context.RunContext;
import org.eclipse.scout.rt.platform.context.RunContextProducer;
import org.eclipse.scout.rt.platform.security.ICredentialVerifier;
import org.eclipse.scout.rt.platform.security.SimplePrincipal;

import javax.security.auth.Subject;

@Bean
public class DatabaseCredentialVerifier implements ICredentialVerifier {
  @Override
  public int verify(String username, char[] password) {
    Subject subject = new Subject();
    subject.getPrincipals().add(new SimplePrincipal("system"));
    subject.setReadOnly();
    RunContext.CURRENT.set(new RunContextProducer().produce(subject));

    return BEANS.get(IAuthService.class).login(username, password);
  }
}
