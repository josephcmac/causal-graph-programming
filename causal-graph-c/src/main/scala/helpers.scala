package helpers

package object helpers {
    val findDir = (s: String) => {
        val i = s.substring(0,s.length-1).lastIndexOf("/")
        s.substring(i+1, s.length)
    }

    private val previousDir = (s: String) => {
        val i = s.substring(0,s.length-1).lastIndexOf("/")
        s.substring(0, i+1)
    }

    private val withoutRoot = (s: String) => {
        val i = s.indexOf("/")
        s.substring(i+1, s.length)
    }

    val cleanQuotationMarks = (s: String) => {
        val pattern = "[\"]".r
        pattern replaceAllIn(s, "")
    }

    private val cleanLessMore = (s: String) => {
        val pattern = "[<]|[>]".r
        pattern replaceAllIn(s, "")
    }

    private val pattern3Action = (root: String, dirInput: String, relDirInput: String, newPath: String) => {
        val cleanName = cleanLessMore(newPath)
        root + "include/asm-generic/" + withoutRoot(cleanName)
    }

    private val pattern2Action = (dirInput: String, relDirInput: String, newPath: String) => {
        dirInput + withoutRoot(cleanLessMore(newPath))
    }

    private val pattern1Action: (String, String) => String = (dirInput: String, newPath: String) => {
        if ( newPath.contains("..") ) {
            pattern1Action(previousDir(dirInput), withoutRoot(newPath))
        } else {
            dirInput + newPath
        }
    }
        

    val clarifyAddress = (root: String, relRoot: String, dirInput: String, relDirInput: String, newPath: String) => {
        val pattern3 = ("[<]asm[/].*?[>]").r
        val pattern2 = ("[<]" + relRoot + ".*?[>]").r
        val pattern1 = "[\"].*?[\"]".r
        newPath match {
          case pattern3() => pattern3Action(root, dirInput, relDirInput, newPath)        
          case pattern2() => pattern2Action(dirInput, relDirInput, newPath)
          case pattern1() => pattern1Action(dirInput, cleanQuotationMarks(newPath))
          case _ => ""
        }
    }
}

