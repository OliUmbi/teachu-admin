package ch.teachu.teachu_admin.server.sql;

import org.eclipse.scout.rt.platform.ApplicationScoped;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.UUID;

@ApplicationScoped
public class UuidConverter {
  // from https://stackoverflow.com/questions/17726682/read-mysql-binary16-uuid-with-java
  public UUID uuid(byte[] bytes) {
    if (bytes == null) {
      return null;
    }

    int i = 0;
    long msl = 0;
    for (; i < 8; i++) {
      msl = (msl << 8) | (bytes[i] & 0xFF);
    }
    long lsl = 0;
    for (; i < 16; i++) {
      lsl = (lsl << 8) | (bytes[i] & 0xFF);
    }
    return new UUID(msl, lsl);
  }

  // from https://stackoverflow.com/questions/40297600/how-to-save-a-uuid-as-binary16-in-java
  public byte[] byteArray(UUID uuid) {
    if (uuid == null) {
      return null;
    }

    byte[] uuidBytes = new byte[16];
    ByteBuffer.wrap(uuidBytes)
      .order(ByteOrder.BIG_ENDIAN)
      .putLong(uuid.getMostSignificantBits())
      .putLong(uuid.getLeastSignificantBits());
    return uuidBytes;
  }
}
