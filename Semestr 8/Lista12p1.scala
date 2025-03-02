// Wojciech Dominiak

import akka.actor.typed.{ActorRef, ActorSystem, Behavior, Terminated}
import akka.actor.typed.scaladsl.{ActorContext, Behaviors}

// zadanie 1
object Main:
  
  // zadanie 1a
  final case class StartGuessing(clients: Int, maxNo: Int)

  def apply(): Behavior[StartGuessing] =
    Behaviors.receive[StartGuessing] { (context, message) =>
      context.log.info(s"${context.self.path.name}. started guessing with ${message.clients} clients - max number: ${message.maxNo}")
      val server: ActorRef[Server.ClientGuess] = context.spawn(Server(message.maxNo), "Server")

      for i <- 1 to message.clients do
          context.watch(context.spawn(Client(message.maxNo, server), s"Client$i"))
      watching(message.clients)
    }

  // zadanie 1b
  private def watching(actors: Int): Behavior[StartGuessing] =
    Behaviors.receiveSignal {
      case (context, Terminated(ref)) =>
        context.log.info(s"Client stopped: ${ref.path.name}")
        if actors > 1 then watching(actors - 1)
        else
          context.log.info(s"The guardian '${context.self.path.name}' is stopping")
          Behaviors.stopped
    }

  def main(args: Array[String]): Unit =
    val system: ActorSystem[Main.StartGuessing] = ActorSystem(Main(), "Guardian")
    val numberOfClients = 2
    val maxNumber = 100
    system ! Main.StartGuessing(numberOfClients, maxNumber)

// zadanie 2
object Server:
  private val rand = new scala.util.Random

  final case class ClientGuess(replyTo: ActorRef[Client.ServerInfo], guessedValue: Int)

  def apply(maxNo: Int): Behavior[ClientGuess] =
    Behaviors.setup { context =>
      context.log.info(s"${context.self.path.name}. started")
      context.log.info(s"${context.self.path.name}. Guess my number from the interval [0..$maxNo]")
      val currentNumb = rand.nextInt(maxNo + 1)

      Behaviors.receiveMessage { message =>
        if message.guessedValue == currentNumb then message.replyTo ! Client.ServerInfo.Equal
        else if message.guessedValue < currentNumb then message.replyTo ! Client.ServerInfo.TooSmall
        else message.replyTo ! Client.ServerInfo.TooBig
        Behaviors.same
      }
    }

// zadanie 3
object Client:
  private val rand = new scala.util.Random

  enum ServerInfo:
    case Equal, TooSmall, TooBig

  def apply(upper: Int, server: ActorRef[Server.ClientGuess]): Behavior[ServerInfo] =
    Behaviors.setup { context =>
      context.log.info(s"${context.self.path.name}. started")
      val firstTry = rand.nextInt(upper)
      context.log.info(s"${context.self.path.name}. First random try = $firstTry")

      def guessNumber(min: Int, max: Int, guessed: Int): Behavior[ServerInfo] = {
        server ! Server.ClientGuess(context.self, guessed)
        Behaviors.receiveMessage {
          case ServerInfo.Equal =>
            context.log.info(s"${context.self.path.name}. I guessed it! $guessed")
            Behaviors.stopped
          case ServerInfo.TooBig =>
            val nextTry = (guessed - min) / 2 + min
            context.log.info(s"${context.self.path.name}. Response: too big. I'm trying: $nextTry")
            guessNumber(min, guessed - 1, nextTry)
          case ServerInfo.TooSmall =>
            val nextTry = (max - guessed + 1) / 2 + guessed
            context.log.info(s"${context.self.path.name}. Response: too small. I'm trying: $nextTry")
            guessNumber(guessed + 1, max, nextTry)
        }
      }
      guessNumber(0, upper, firstTry)
    }
