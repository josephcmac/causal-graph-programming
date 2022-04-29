import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.col
import input.parameters.io._
import process._
import helpers.helpers.findDir

object Main extends App {
  val INPUT = "input.txt"
  val OUTPUT = "Output/"

  println("---------- Welcome to causal graph C ----------")

  val conf = new SparkConf()
        .setAppName("causal-graph-c-app")
        .setMaster("local[*]")

  val sc = new SparkContext(conf)
 
  val spark = SparkSession.builder.config(sc.getConf).getOrCreate()
  import spark.implicits._

  spark.conf.set("spark.sql.parquet.enableVectorizedReader","false") // important to avoir memory overload problems

  val (root, dirInput, relDirInput, extInput) = input.getInputData(INPUT)
  
  val relRoot = findDir(root)

  input.listFiles(dirInput, extInput)
    .toDF("address")
    .withColumn("includes", process.readFiles(root, relRoot, dirInput, relDirInput) ( col("address") ))
    .repartition(1)
    .write.parquet(OUTPUT)
    
  spark.stop()

  print("--- COMPLETED ------ COMPLETED ------ COMPLETED ------ COMPLETED ------ COMPLETED ------ COMPLETED ---")

}