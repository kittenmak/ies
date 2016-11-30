package ies;


import comp4007.SharedConsts;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Kitten on 27/11/2016.
 */
public class LogAction {

    File logFile = new File(SharedConsts.LogFilePath);


    public LogAction(String input) {
        String timeStamp = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Calendar.getInstance().getTime());

        if (!checkLogExist()) {
            writeLog(timeStamp, "Log Generated");
            writeLog(timeStamp, input);
        } else {
            writeLog(timeStamp, input);
        }
    }

    private boolean checkLogExist() {
        if (logFile.exists() && !logFile.isDirectory()) {
            return true;
        } else {
            return false;
        }
    }


    private void writeLog(String timeStamp, String event) {
        try (FileWriter fw = new FileWriter(logFile, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
//            String syntax = timeStamp + " - " + event;
//            out.println(syntax);

            out.println(syntax(timeStamp, event));
            out.close();


        } catch (IOException e) {
            //TODO
        }
    }

    private String syntax(String timeStamp, String event){
        return timeStamp + " - " + event;
    }

    public static void main(String args[]) {
        LogAction la = new LogAction("Hello");
        LogAction la2 = new LogAction("Hello2");


    }

}
