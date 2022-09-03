package ch.teachu.teachu_admin.server.grade;

import ch.teachu.teachu_admin.shared.grade.GradeStudentLookupCall;
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
public class GradeStudentLookupCallTest {
// TODO [rbr] add test cases

  protected GradeStudentLookupCall createLookupCall() {
    return new GradeStudentLookupCall();
  }

  @Test
  public void testGetDataByAll() {
    GradeStudentLookupCall call = createLookupCall();
// TODO [rbr] fill call
    List<? extends ILookupRow<Long>> data = call.getDataByAll();
// TODO [rbr] verify data
  }

  @Test
  public void testGetDataByKey() {
    GradeStudentLookupCall call = createLookupCall();
// TODO [rbr] fill call
    List<? extends ILookupRow<Long>> data = call.getDataByKey();
// TODO [rbr] verify data
  }

  @Test
  public void testGetDataByText() {
    GradeStudentLookupCall call = createLookupCall();
// TODO [rbr] fill call
    List<? extends ILookupRow<Long>> data = call.getDataByText();
// TODO [rbr] verify data
  }
}
