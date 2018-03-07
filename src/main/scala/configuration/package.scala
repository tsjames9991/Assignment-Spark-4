import java.util.Properties

import org.apache.log4j.Logger
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types.{LongType, StringType, StructField, StructType}
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Configuration File of the Twitter account.
  */

package object configuration {
  val INITIALTIME = 1
  val FINALTIME = 5
  val SLIDEITNERVAL = Seconds(INITIALTIME)
  val WINDOWLENGTH = Seconds(FINALTIME)
  val Log = Logger.getLogger(this.getClass)
  val URL = s"jdbc:postgresql://localhost:5432/knoldus"
  val table = "hashcounts"
  val sc = new SparkConf().setAppName("TwitterApp").setMaster("local")
  val spark = SparkSession
    .builder()
    .config(sc)
    .getOrCreate()
  val ssc = new StreamingContext(sc, SLIDEITNERVAL)
  val context = new SparkContext("local", "Operations")
  val prop = new Properties
  prop.setProperty("driver", "com.postgresql.jdbc.Driver")
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
