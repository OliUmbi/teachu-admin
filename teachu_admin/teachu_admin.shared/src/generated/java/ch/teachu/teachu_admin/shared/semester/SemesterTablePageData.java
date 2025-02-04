package ch.teachu.teachu_admin.shared.semester;

import org.eclipse.scout.rt.shared.data.basic.table.AbstractTableRowData;
import org.eclipse.scout.rt.shared.data.page.AbstractTablePageData;

import javax.annotation.Generated;
import java.util.Date;

/**
 * <b>NOTE:</b><br>
 * This class is auto generated by the Scout SDK. No manual modifications recommended.
 */
@Generated(value = "ch.teachu.teachu_admin.client.semester.SemesterTablePage", comments = "This class is auto generated by the Scout SDK. No manual modifications recommended.")
public class SemesterTablePageData extends AbstractTablePageData {
  private static final long serialVersionUID = 1L;

  @Override
  public SemesterTableRowData addRow() {
    return (SemesterTableRowData) super.addRow();
  }

  @Override
  public SemesterTableRowData addRow(int rowState) {
    return (SemesterTableRowData) super.addRow(rowState);
  }

  @Override
  public SemesterTableRowData createRow() {
    return new SemesterTableRowData();
  }

  @Override
  public Class<? extends AbstractTableRowData> getRowType() {
    return SemesterTableRowData.class;
  }

  @Override
  public SemesterTableRowData[] getRows() {
    return (SemesterTableRowData[]) super.getRows();
  }

  @Override
  public SemesterTableRowData rowAt(int index) {
    return (SemesterTableRowData) super.rowAt(index);
  }

  public void setRows(SemesterTableRowData[] rows) {
    super.setRows(rows);
  }

  public static class SemesterTableRowData extends AbstractTableRowData {
    private static final long serialVersionUID = 1L;
    public static final String id = "id";
    public static final String name = "name";
    public static final String from = "from";
    public static final String to = "to";
    private String m_id;
    private String m_name;
    private Date m_from;
    private Date m_to;

    public String getId() {
      return m_id;
    }

    public void setId(String newId) {
      m_id = newId;
    }

    public String getName() {
      return m_name;
    }

    public void setName(String newName) {
      m_name = newName;
    }

    public Date getFrom() {
      return m_from;
    }

    public void setFrom(Date newFrom) {
      m_from = newFrom;
    }

    public Date getTo() {
      return m_to;
    }

    public void setTo(Date newTo) {
      m_to = newTo;
    }
  }
}
