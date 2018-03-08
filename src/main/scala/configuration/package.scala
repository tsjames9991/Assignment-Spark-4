import java.util.Properties

import org.apache.log4j.Logger
import org.apache.spark.sql.types.{LongType, StringType, StructField, StructType}
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Configuration File of the Twitter account.
  */

package object configuration {
  val CONSUMERKEY = "bN5iepekOZwVM4Gp99BXX95d4"
  val CONSUMERSECRET = "DmjyN9zJ9ZMPpPW5uLbCv67ulZx0nCOnklqSnGZqx1h7M6mZpq"
  val ACCESSTOKEN = "83770216-zM4p3zQl7iLxwTozXsQhg7PzE4xQu2sudarGHRF5l"
  val ACCESSTOKENKEY = "PlcZagn97UKzcYZpMdpIz7jEOuKpdJ1pL7L2lO4eDldnB"
  val INITIALTIME = 1
  val FINALTIME = 5
  val SLIDEITNERVAL = Seconds(INITIALTIME)
  val WINDOWLENGTH = Seconds(FINALTIME)
  val Log = Logger.getLogger(this.getClass)
  val URL = s"jdbc:postgresql://localhost:5432/knoldus"
  val table = "hashcounts"
  val sc = new SparkConf().setAppName("TwitterApp").setMaster("local[4]")
  val ssc = new StreamingContext(sc, SLIDEITNERVAL)
  val context = new SparkContext("local", "Operations")
  context.setLogLevel("WARN")
  val prop = new Properties
  prop.setProperty("driver", "org.postgresql.Driver")
  prop.getProperty("user", "knoldus")
  prop.getProperty("password", "knoldus")
  val struct = StructType(
    StructField("WindowID", LongType, nullable = false) ::
      StructField("Tweet", StringType, nullable = false) ::
      StructField("Count", LongType, nullable = false) :: Nil
  )

  case class TweetCount(tweet: String,
                        count: Int
                       ) {
    def filter(p: TweetCount => Boolean): TweetCount = ???

    def foreach[U](f: TweetCount => U): Unit = ???
  }
}
