import jade.core.Runtime;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;

public class Main {
  public static void main(String[] args) {
    Runtime rt = Runtime.instance();
    Profile p = new ProfileImpl();
    ContainerController cc = rt.createMainContainer(p);

    try {
      AgentController ac1 = cc.createNewAgent("Agent1", "SimpleAgent", null);
      ac1.start();
      AgentController ac2 = cc.createNewAgent("Agent2", "SimpleAgent", null);
      ac2.start();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}