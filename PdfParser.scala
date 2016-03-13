
package scalaguiapp

import scala.swing._       
import scala.swing.event._
import pdf._
import javax.swing.text._
import javax.swing.JEditorPane       
import javax.swing.JFileChooser       
import javax.swing.filechooser.FileNameExtensionFilter
       
import java.io.{FileNotFoundException, IOException}

       
object Main extends SimpleGUIApplication {
    object CtrlNames extends Enumeration {
        val FrameTitle = Value("PDF Analyzer")
        val OpenFileButton = Value("Open File")
        val InputFileLabel = Value("Input File")
        val ExtractTextButton = Value("Extract Text")
        val ExtractTextEdit = Value("Extracted Text")
        val OpenDialogText = Value("Select PDF file")
    }

    object Geometry {
            final val smallTextField = 10
            final val largeTextField = 50
    }

    def top = new MainFrame {
        title = CtrlNames.FrameTitle.toString
        object inputFileEdit extends TextField { columns = Geometry.smallTextField }
        object outputText extends TextArea {
            columns = Geometry.largeTextField
            rows = Geometry.largeTextField
        }  

        val openFileButton = new Button(CtrlNames.OpenFileButton.toString)
        val extractTextButton = new Button(CtrlNames.ExtractTextButton.toString)

        val chooser = new FileChooser
        chooser.fileFilter = new FileNameExtensionFilter("PDF","pdf")

        contents = new BoxPanel(Orientation.Vertical) {
            contents += openFileButton
            contents += inputFileEdit
            contents += outputText
            border = Swing.EmptyBorder(10, 10, 10, 10)
        }
        listenTo(inputFileEdit, openFileButton)

        reactions += {
            case ValueChanged (`inputFileEdit`) =>
                outputText.text = PdfParser(inputFileEdit.text).getText()

            case ButtonClicked(openFileButton) =>
                val retVal = chooser.showDialog(inputFileEdit,
                                                CtrlNames.OpenDialogText.toString)
               
                if (retVal == FileChooser.Result.Approve) {
                    val file = chooser.selectedFile
                   
                    try {
                        inputFileEdit.text = file.getAbsolutePath()
                    } catch {
                        case ex: IOException => outputText.text = ex.toString()
                    }                   
                }
        }
    }
}

