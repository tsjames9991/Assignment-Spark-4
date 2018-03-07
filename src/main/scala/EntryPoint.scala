import function._

object EntryPoint extends App {
  val twitterObject = new TwitterOperations
  twitterObject.getTweets()
  twitterObject.countHashTags()
}
