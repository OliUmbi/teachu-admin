package ch.teachu.teachu_admin.client.event.school;

import ch.teachu.teachu_admin.shared.event.school.ISchoolEventService;
import ch.teachu.teachu_admin.shared.event.school.SchoolEventFormData;
import org.eclipse.scout.rt.client.testenvironment.TestEnvironmentClientSession;
import org.eclipse.scout.rt.testing.client.runner.ClientTestRunner;
import org.eclipse.scout.rt.testing.client.runner.RunWithClientSession;
import org.eclipse.scout.rt.testing.platform.mock.BeanMock;
import org.eclipse.scout.rt.testing.platform.runner.RunWithSubject;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

@RunWithSubject("anonymous")
@RunWith(ClientTestRunner.class)
@RunWithClientSession(TestEnvironmentClientSession.class)
public class SchoolEventFormTest {
  @BeanMock
  private ISchoolEventService m_mockSvc;
// TODO [rbr] add test cases

  @Before
  public void setup() {
    SchoolEventFormData answer = new SchoolEventFormData();
    Mockito.when(m_mockSvc.prepareCreate(ArgumentMatchers.any())).thenReturn(answer);
    Mockito.when(m_mockSvc.create(ArgumentMatchers.any())).thenReturn(answer);
    Mockito.when(m_mockSvc.load(ArgumentMatchers.any())).thenReturn(answer);
    Mockito.when(m_mockSvc.store(ArgumentMatchers.any())).thenReturn(answer);
  }
}
