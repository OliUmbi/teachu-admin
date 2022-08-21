package ch.teachu.teachu_admin.server.schoolclass.subject;

import ch.teachu.teachu_admin.shared.schoolclass.subject.SchoolClassSubjectLookupCall;
import org.eclipse.scout.rt.server.IServerSession;
import org.eclipse.scout.rt.shared.services.lookup.ILookupRow;
import org.eclipse.scout.rt.testing.platform.runner.RunWithSubject;
import org.eclipse.scout.rt.testing.server.runner.RunWithServerSession;
import org.eclipse.scout.rt.testing.server.runner.ServerTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

@RunWithSubject("anonymous")
@RunWith(ServerTestRunner.class)
@RunWithServerSession(IServerSession.class)
public class SchoolClassSubjectLookupCallTest {
// TODO [rbr] add test cases

  protected SchoolClassSubjectLookupCall createLookupCall() {
    return new SchoolClassSubjectLookupCall();
  }

  @Test
  public void testGetDataByAll() {
    SchoolClassSubjectLookupCall call = createLookupCall();
// TODO [rbr] fill call
    List<? extends ILookupRow<Long>> data = call.getDataByAll();
// TODO [rbr] verify data
  }

  @Test
  public void testGetDataByKey() {
    SchoolClassSubjectLookupCall call = createLookupCall();
// TODO [rbr] fill call
    List<? extends ILookupRow<Long>> data = call.getDataByKey();
// TODO [rbr] verify data
  }

  @Test
  public void testGetDataByText() {
    SchoolClassSubjectLookupCall call = createLookupCall();
// TODO [rbr] fill call
    List<? extends ILookupRow<Long>> data = call.getDataByText();
// TODO [rbr] verify data
  }
}
