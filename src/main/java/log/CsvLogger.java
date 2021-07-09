package log;

import com.opencsv.CSVWriter;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPSClient;
import uuid.UuidHelper;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class CsvLogger {

    private static String EDITOR_CSV_FILENAME = UuidHelper.getInstance().getUuid().toString() + "_editor.csv";
    private static String EDITOR_CSV_PATH = System.getProperty("user.home") + "/" + "codecast" + "/" + EDITOR_CSV_FILENAME;

    private static String PLAYER_CSV_FILENAME = UuidHelper.getInstance().getUuid().toString() + "_player.csv";
    private static String PLAYER_CSV_PATH = System.getProperty("user.home") + "/" + "codecast" + "/" + PLAYER_CSV_FILENAME;

    private static String HOST = "tobiasdollhofer.de";
    private static String CODECAST_USER = "codecast";
    private static String CODECAST_ACCESS = "C0d3C4$t";

    /**
     * stores message with timestamp and uuid to csv
     * @param message
     */
    public static void log(Context context, EventType type, String message){
        try{
            File csv = new File(EDITOR_CSV_PATH);
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
     * method sends final csv to ftp server
     */
    public static void sendToServer(){
        FTPSClient client = new FTPSClient();
        BufferedInputStream bufferedInputStream = null;
        try{
            client.connect(HOST);
            client.login(CODECAST_USER, CODECAST_ACCESS);
            client.enterLocalPassiveMode();

            File editorCsv = new File(EDITOR_CSV_PATH);
            if(editorCsv.exists()){
                bufferedInputStream = new BufferedInputStream(new FileInputStream(EDITOR_CSV_PATH));
                client.storeFile(EDITOR_CSV_FILENAME, bufferedInputStream);
            }

            File playerCsv = new File(PLAYER_CSV_PATH);
            if(playerCsv.exists()){
                bufferedInputStream = new BufferedInputStream(new FileInputStream(PLAYER_CSV_PATH));
                client.storeFile(PLAYER_CSV_FILENAME, bufferedInputStream);
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


    public static void logStartup(){
        log(Context.EDITOR, EventType.STARTUP, "New EDITOR-SESSION-ID: " + UuidHelper.getInstance().getSessionId());
    }
}
