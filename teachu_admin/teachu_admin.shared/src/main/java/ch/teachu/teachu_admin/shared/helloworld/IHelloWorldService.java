package ch.teachu.teachu_admin.shared.helloworld;

import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;

import ch.teachu.teachu_admin.shared.helloworld.HelloWorldFormData;

@TunnelToServer
public interface IHelloWorldService extends IService {
      HelloWorldFormData load(HelloWorldFormData input);
}
