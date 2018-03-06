import function._

object EntryPoint extends App {
  val twitterObject = new Twitter
  twitterObject.getTweets()
  twitterObject.countHashTags()
}
