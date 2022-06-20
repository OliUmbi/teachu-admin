package ch.teachu.teachu_admin.server.helloworld;

import ch.teachu.teachu_admin.server.ServerSession;
import ch.teachu.teachu_admin.shared.helloworld.HelloWorldFormData;
import ch.teachu.teachu_admin.shared.helloworld.IHelloWorldService;

/**
 * @author rbr
 */
public class HelloWorldService implements IHelloWorldService {

  @Override
  public HelloWorldFormData load(HelloWorldFormData input) {
    StringBuilder msg = new StringBuilder();
    msg.append("Hello ").append(ServerSession.get().getUserId()).append('!');
    input.getMessage().setValue(msg.toString());
    return input;
  }
}
