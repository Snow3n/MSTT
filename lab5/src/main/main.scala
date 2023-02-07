import akka.actor.Actor
import akka.actor.ActorRef
import akka.actor.ActorSystem
import akka.actor.Props

case class EnvironmentInformation(info: String)
case class Decision(decision: String)

class SpelunkerActor extends Actor {
	  def receive = {
	    case EnvironmentInformation(info) => {
	      // Send the environment information to the NavigatorActor
	      val navigator = context.actorSelection("/user/NavigatorActor")
	      navigator ! info
	    }
	    case Decision(decision) => {
	      // Print the decision received from the NavigatorActor
	      println("Received decision from NavigatorActor: " + decision)
	    }
	  }
	}

class NavigatorActor extends Actor {
	  def receive = {
	    case info: String => {
	      // Print the environment information received from the SpelunkerActor
	      println("Received environment information from SpelunkerActor: " + info)

	      // Process the incoming information and make a decision
	      val decision = "Proceed to the left"

	      // Send the decision to the SpelunkerActor
	      val spelunker = context.actorSelection("/user/SpelunkerActor")
	      spelunker ! Decision(decision)
	    }
	  }
	}

object Main extends App {
	  val system = ActorSystem("WampusWorldSystem")
	  val spelunkerActor = system.actorOf(Props[SpelunkerActor], name = "SpelunkerActor")
	  val navigatorActor = system.actorOf(Props[NavigatorActor], name = "NavigatorActor")

	  // Send the environment information to the SpelunkerActor
	  spelunkerActor ! EnvironmentInformation("There is a room to the left and a smell of Wampus to the right.")

	  // Terminate the ActorSystem
	  system.terminate()
	}
