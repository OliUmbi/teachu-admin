package ch.teachu.teachu_admin.server.helloworld;

import ch.teachu.teachu_admin.server.ServerSession;
import ch.teachu.teachu_admin.shared.helloworld.HelloWorldFormData;
import ch.teachu.teachu_admin.shared.helloworld.IHelloWorldService;
import org.eclipse.scout.rt.server.jdbc.SQL;

public class HelloWorldService implements IHelloWorldService {

  @Override
  public HelloWorldFormData load(HelloWorldFormData input) {
    StringBuilder msg = new StringBuilder();
    msg.append("Hello ").append(ServerSession.get().getUserId()).append('!');
    input.getMessage().setValue(msg.toString());

    Object r = SQL.select("select * from user");
    System.out.println(r);
    return input;
  }
}
