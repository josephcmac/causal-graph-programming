package input.parameters.io

import helpers.helpers.findDir
import scala.io.Source
import java.io.File

package object input {
    val getInputData: String => Tuple4[String, String, String, List[String]] = filename => {
        val l = Source.fromFile(filename).getLines().toList
        (l(0), l(1), findDir(l(1)), l(2).split(" ").toList )
    }

    private val recursiveListFiles: File => List[File] = f => {
        val these = f.listFiles().toList
        these ++ these.filter(_.isDirectory).flatMap(recursiveListFiles)
    }

    private val hasExt: (String, String) => Boolean = (filename, x) => {
        val i = filename.lastIndexOf('.')
        if (i == -1) false else           
            filename.substring( i+1, filename.length ) == x
    }
        

    private val someExt: (String,  List[String]) => Boolean = (filename, l) => l match {
        case x :: xs => if ( hasExt(filename, x) ) true else someExt(filename, xs)
        case _ => false
    }

    val listFiles: Tuple2[String, List[String]] => List[String] = t => {
        val fileWriter = new File(t._1)
        recursiveListFiles(fileWriter)
            .map(file => file.toString)
            .filter(filename => someExt(filename, t._2) )
    }
}



