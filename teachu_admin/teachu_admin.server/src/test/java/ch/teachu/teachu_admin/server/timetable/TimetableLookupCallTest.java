package ch.teachu.teachu_admin.server.timetable;

import ch.teachu.teachu_admin.shared.timetable.TimetableLookupCall;
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
public class TimetableLookupCallTest {
// TODO [rbr] add test cases

  protected TimetableLookupCall createLookupCall() {
    return new TimetableLookupCall();
  }

  @Test
  public void testGetDataByAll() {
    TimetableLookupCall call = createLookupCall();
// TODO [rbr] fill call
    List<? extends ILookupRow<Long>> data = call.getDataByAll();
// TODO [rbr] verify data
  }

  @Test
  public void testGetDataByKey() {
    TimetableLookupCall call = createLookupCall();
// TODO [rbr] fill call
    List<? extends ILookupRow<Long>> data = call.getDataByKey();
// TODO [rbr] verify data
  }

  @Test
  public void testGetDataByText() {
    TimetableLookupCall call = createLookupCall();
// TODO [rbr] fill call
    List<? extends ILookupRow<Long>> data = call.getDataByText();
// TODO [rbr] verify data
  }
}
