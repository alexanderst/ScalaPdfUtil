
package pdf

import org.apache.pdfbox.ExtractText
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.util.PDFTextStripper

import scala.io.Source
import java.io.{FileReader, FileNotFoundException, IOException}
 
object PdfParser {
    def apply(inputFilePath:String) = {
        new PdfParser(inputFilePath)
    }
}

class PdfParser(inputFilePath:String) {   
    private var pdfContent:PDDocument = null
               
    def getText():String = {           
        var retVal = ""
        try {
            pdfContent = PDDocument.load(inputFilePath)
            val pdfStripper = new PDFTextStripper
            retVal = pdfStripper.getText(pdfContent)                       
        }
        catch {
            case ex: FileNotFoundException => retVal = ex.toString()
            case ex: IOException => retVal = ex.toString()
        }
        finally {
            pdfContent.close()
        }
       
        retVal
    }
}
