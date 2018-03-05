import configuration._
import function._

object EntryPoint extends App {
  val twitterObject = new Twitter
  twitterObject.getTweets()
}
