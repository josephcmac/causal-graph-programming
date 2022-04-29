package process

import helpers.helpers.{cleanQuotationMarks, clarifyAddress}
import org.apache.spark.sql.functions.udf
import java.nio.file.Files
import java.nio.file.Path
import java.nio.charset.StandardCharsets

package object process {
    val readFiles = (root: String, relRoot: String, dirInput: String, relDirInput: String) => udf[String, String](name => {
      
      val extractFiles = (currentPath: String, file: String) => {
        val pattern1 = "[#]include.*".r
        val pattern2 = "([#]include)|(\\s+)".r

        (pattern1 findAllIn file)
          .map(x => {
            val newPath = pattern2 replaceAllIn(x, "")
            clarifyAddress(root, relRoot, dirInput, relDirInput, newPath) 
          }).mkString(",")
      }

      val clean_spaces = (s:String) => {
        val pattern = ("[ ]+").r
        pattern replaceAllIn(s, " ")
      }

      val clean_newlines = (s:String) => {
        val pattern = ("[\n]+").r
        clean_spaces(pattern replaceAllIn(s, "\n"))
      }

      val clean_comments2 = (s:String) => {
        val pattern = ("""[/][*](?>.|\n)+?[*][/]""").r
        clean_newlines(pattern replaceAllIn(s, ""))
      }

      val clean_comments1 = (s:String) => {
        val pattern = ("""[/][/].*""").r
        clean_comments2(pattern replaceAllIn(s, ""))
      }

      val cleanName = cleanQuotationMarks(name)
      extractFiles(cleanName, clean_comments1(Files.readString( Path.of(cleanName), StandardCharsets.UTF_8)))
    })

}