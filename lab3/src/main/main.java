import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.core.Runtime;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;

public class SpelunkerAgent extends Agent {
	protected void setup() {
		System.out.println("Spelunker agent " + getLocalName() + " is ready.");
		addBehaviour(new SendEnvironmentInformationBehaviour(this));
	}

	private class SendEnvironmentInformationBehaviour extends CyclicBehaviour {
		public SendEnvironmentInformationBehaviour(Agent a) {
			super(a);
		}

		public void action() {
			// Code to get information about the environment
			String environmentInformation = "I am in a dark and damp cave. I can hear the sound of water dripping.";

			// Create a message with the information
			ACLMessage message = new ACLMessage(ACLMessage.INFORM);
			message.setContent(environmentInformation);
			message.addReceiver(getAID("NavigatorAgent"));

			// Send the message
			send(message);
		}
	}
}

public class NavigatorAgent extends Agent {
	protected void setup() {
		System.out.println("Navigator agent " + getLocalName() + " is ready.");
		addBehaviour(new ReceiveEnvironmentInformationBehaviour(this));
	}

	private class ReceiveEnvironmentInformationBehaviour extends CyclicBehaviour {
		public ReceiveEnvironmentInformationBehaviour(Agent a) {
			super(a);
		}

		public void action() {
			// Receive the message from the spelunker agent
			ACLMessage message = receive();
			if (message != null) {
				String environmentInformation = message.getContent();
				System.out.println("Received environment information: " + environmentInformation);
				// Process the incoming information
				String decision = "Proceed to the left";

				// Create a message with the decision
				ACLMessage reply = message.createReply();
				reply.setContent(decision);
				reply.setPerformative(ACLMessage.INFORM);

				// Send the message
				send(reply);
			}
		}
	}
}

public class Main {
	public static void main(String[] args) {
// Get the JADE runtime
		Runtime runtime = Runtime.instance();
// Create a profile for the main container
		Profile profile = new ProfileImpl();
		profile.setParameter(Profile.MAIN_HOST, "localhost");
		profile.setParameter(Profile.MAIN_PORT, "1099");

// Create the main container
		ContainerController container = runtime.createMainContainer(profile);

// Create the spelunker agent
		try {
			AgentController spelunkerAgent = container.createNewAgent("SpelunkerAgent", "SpelunkerAgent",
					new Object[] {});
			spelunkerAgent.start();
		} catch (Exception e) {
			e.printStackTrace();
		}

// Create the navigator agent
		try {
			AgentController navigatorAgent = container.createNewAgent("NavigatorAgent", "NavigatorAgent",
					new Object[] {});
			navigatorAgent.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}