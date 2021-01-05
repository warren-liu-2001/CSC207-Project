package Gateway;

import Presenters.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;

public class FeedbackFileExporter {

    private FeedbackPresenter fbp;

    /**
     * instantiates it
     */
    public FeedbackFileExporter() {}

    /**
     *
     * @param fbp set the feedback presenter
     */
    public void setFbp(FeedbackPresenter fbp) {
        this.fbp = fbp;
    }

    /**
     *
     * @param informationLines List of Lines to export
     */

    public void FeedbackExport(ArrayList<String> informationLines) {

        Timestamp ts = new Timestamp(System.currentTimeMillis());

        Instant instant = ts.toInstant();
        String filename = "Feedback_Log_@_Time" + instant.toString();


        FileGenerator(filename);

        FileWriter(informationLines, filename);

    }

    /**
     * Write lines to filename provided
     * @param informationLines information lines to be written
     * @param filename the name of the file
     */
    private void FileWriter(ArrayList<String> informationLines, String filename) {
        try {
            FileWriter myWriter = new FileWriter(filename + ".txt");
            for (String Review:
                    informationLines) {
                myWriter.write(Review);
                myWriter.write("\n");
            }
            myWriter.close();
            this.fbp.MessagePrinter("File has been written to successfully, " +
                    "and is under the name " + filename + ".txt");
        }
        catch (IOException e2) {
            this.fbp.MessagePrinter("ERROR: FILE HAS NOT BEEN WRITTEN");

        }
    }

    /**
     *
     * @param filename generate a file with this filenqme
     */
    private void FileGenerator(String filename) {
        try {
            File OutputFile = new File(filename + ".txt");
            String OutputFilePath = OutputFile.getName();
            if (OutputFile.createNewFile()) {
                this.fbp.MessagePrinter("File created: " + OutputFilePath);
            } else {
                this.fbp.MessagePrinter("Error: message has already been printed");
            }
        } catch (IOException e) {
            this.fbp.MessagePrinter("An error has occurred");
        }
    }
}
