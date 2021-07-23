package log;

import com.opencsv.CSVWriter;
import org.apache.commons.net.ftp.FTPSClient;
import util.Config;
import uuid.UuidHelper;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class CsvLogger {

    /**
     * stores message with timestamp and uuid to csv
     * @param message
     */
    public static void log(Context context, EventType type, String message){
        try{
            File csv = new File(Config.EDITOR_CSV_PATH);
            FileWriter fileWriter = new FileWriter(csv, true);

            String uuid = UuidHelper.getInstance().getUuid().toString();
            String sessionId = UuidHelper.getInstance().getSessionId().toString();
            String date = getCurrentDate();
            String time = getCurrentTime();
            String[] data = {uuid, sessionId, date, time, context.toString(), type.toString(), message};
            CSVWriter csvWriter = new CSVWriter(fileWriter);
            System.out.println(Arrays.toString(data));

            csvWriter.writeNext(data);
            csvWriter.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }


    private static String getCurrentDate() {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        return format.format(new Date());
    }

    private static String getCurrentTime() {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss:SSS");
        return format.format(new Date());
    }

    /**
     * method sends final log csvs to ftp server configured in config.properties
     */
    public static void sendToServer(){
        FTPSClient client = new FTPSClient();
        BufferedInputStream bufferedInputStream = null;
        try{
            client.connect(Config.LOG_HOST);
            client.login(Config.LOG_USER, Config.LOG_PW);
            client.enterLocalPassiveMode();

            // send log of this plugin to server
            File editorCsv = new File(Config.EDITOR_CSV_PATH);
            if(editorCsv.exists()){
                bufferedInputStream = new BufferedInputStream(new FileInputStream(Config.EDITOR_CSV_PATH));
                client.storeFile(Config.EDITOR_CSV_FILENAME, bufferedInputStream);
            }

            // send log of player if exists
            File playerCsv = new File(Config.PLAYER_CSV_PATH);
            if(playerCsv.exists()){
                bufferedInputStream = new BufferedInputStream(new FileInputStream(Config.PLAYER_CSV_PATH));
                client.storeFile(Config.PLAYER_CSV_FILENAME, bufferedInputStream);
            }

        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try{
                if(bufferedInputStream != null)
                    bufferedInputStream.close();

                client.logout();
                client.disconnect();
            }catch (IOException e){
                e.printStackTrace();
            }

        }
    }

    /**
     * prints startup message to log file including new session id
     */
    public static void logStartup(){
        log(Context.EDITOR, EventType.STARTUP, "New EDITOR-SESSION-ID: " + UuidHelper.getInstance().getSessionId());
    }
}
