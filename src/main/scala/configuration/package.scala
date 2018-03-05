import java.util.Properties

import org.apache.log4j.Logger
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types.{LongType, StringType, StructField, StructType}
import org.apache.spark.streaming.{Duration, StreamingContext}


/**
  * Configuration File of the Twitter account.
  */

package object configuration {

  val SLIDEITNERVAL = new Duration(1 * 1000)
  val WINDOWLENGTH = new Duration(5 * 1000)
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
  prop.setProperty("driver", "com.mysql.jdbc.Driver")
  prop.getProperty("user", "root")
  prop.getProperty("password", "pw")
  val struct = StructType(
    StructField("WindowID", LongType, nullable = false)::
    StructField("Tweet", StringType, nullable = false)::
    StructField("Count", LongType, nullable = false) :: Nil
  )
  case class TweetCount(
                         tweet: String,
                         count: Int
                       )
}
