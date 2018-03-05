package function

import configuration._
import org.apache.spark._
import org.apache.spark.streaming._
import org.apache.spark.streaming.dstream.DStream
import twitter4j.{Status, TwitterFactory}
import twitter4j.auth.AccessToken
import org.apache.spark.streaming.twitter.TwitterUtils

class Twitter {
  val twitter = new TwitterFactory().getInstance()
  twitter.setOAuthConsumer(CONSUMERKEY, CONSUMERSECRET)
  twitter.setOAuthAccessToken(new AccessToken(ACCESSTOKEN, ACCESSTOKENKEY))
  val sc = new SparkConf().setMaster("local")
  val ssc = new StreamingContext(sc, Seconds(1))
  val tweets: DStream[Status] = TwitterUtils.createStream(ssc, None)

  def getTweets() = {

  }

  def countHashTags() = {
    val hashTagStream = tweets.map(_.getText).flatMap(_.split(" ")).filter(_.startsWith("#"))
  }
}
