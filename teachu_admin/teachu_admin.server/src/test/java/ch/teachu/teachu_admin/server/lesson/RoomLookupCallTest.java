package ch.teachu.teachu_admin.server.lesson;

import ch.teachu.teachu_admin.shared.lesson.RoomLookupCall;
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
public class RoomLookupCallTest {
// TODO [rbr] add test cases

  protected RoomLookupCall createLookupCall() {
    return new RoomLookupCall();
  }

  @Test
  public void testGetDataByAll() {
    RoomLookupCall call = createLookupCall();
// TODO [rbr] fill call
    List<? extends ILookupRow<Long>> data = call.getDataByAll();
// TODO [rbr] verify data
  }

  @Test
  public void testGetDataByKey() {
    RoomLookupCall call = createLookupCall();
// TODO [rbr] fill call
    List<? extends ILookupRow<Long>> data = call.getDataByKey();
// TODO [rbr] verify data
  }

  @Test
  public void testGetDataByText() {
    RoomLookupCall call = createLookupCall();
// TODO [rbr] fill call
    List<? extends ILookupRow<Long>> data = call.getDataByText();
// TODO [rbr] verify data
  }
}
