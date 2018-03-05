package function

import configuration._
import org.apache.spark.sql.{DataFrame, Row}
import org.apache.spark.streaming.dstream.ReceiverInputDStream
import org.apache.spark.streaming.twitter._
import twitter4j.auth.AccessToken
import twitter4j.{Status, TwitterFactory}

class Twitter {

  val twitter = new TwitterFactory().getInstance()
  twitter.setOAuthConsumer(CONSUMERKEY, CONSUMERSECRET)
  twitter.setOAuthAccessToken(new AccessToken(ACCESSTOKEN, ACCESSTOKENKEY))
  val tweets:ReceiverInputDStream[Status] = TwitterUtils.createStream(ssc, Some(twitter.getAuthorization()))

  def getTweets(): Unit = {
    val statuses = tweets.map(status => status.getText())
    statuses.foreachRDD(tweet => Log.info("\n" + tweet))
    ssc.start()
    ssc.awaitTermination()
  }

  def countHashTags(): Unit = {
    var num = 1
    val row: List[Row] = List()
    val hashTagStream = tweets.flatMap(status => status.getText.split(" ").filter(_.startsWith("#")))
    val hashTagCountStream = hashTagStream.map((_, 1)).reduceByKeyAndWindow((x: Int, y: Int) => x + y, WINDOWLENGTH, SLIDEITNERVAL)
    hashTagCountStream.foreachRDD(hashTagCount => {
      val topHashTags: TweetCount = hashTagCount.top(3)(Comparision).toList.asInstanceOf[TweetCount]
      for (instance: TweetCount <- topHashTags) {
        Row("WindowID" -> num, "Tweet" -> instance.tweet, "Count" -> instance.count) :: row
      }
      writeToPostgres(spark.sqlContext.createDataFrame(context.parallelize(row), struct))
      Log.info(s"\nTOP HASHTAGS For window ${num}")
      for (instance: TweetCount <- topHashTags) {
        Log.info(s"\nTweet : ${instance.tweet}, Count : ${instance.count}")
      }
      num = num + 1
    })
    ssc.start()
    ssc.awaitTermination()
  }

  def writeToPostgres(frame: DataFrame): Unit = {
    frame.write.mode("append").jdbc(URL, table, prop)
  }

  object Comparision extends Ordering[(String, Int)] {
    def compare(a: (String, Int), b: (String, Int)): Int = {
      a._2 compare b._2
    }
  }
}
