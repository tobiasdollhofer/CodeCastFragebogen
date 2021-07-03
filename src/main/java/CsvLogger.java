import com.opencsv.CSVWriter;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPSClient;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CsvLogger {

    private static String CSV_FILENAME = "log_" + UuidHelper.getInstance().getUuid().toString() + ".csv";
    private static String CSV_PATH = System.getProperty("user.home") + "/" + "codecast" + "/" + CSV_FILENAME;

    private static String HOST = "tobiasdollhofer.de";
    private static String CODECAST_USER = "codecast";
    private static String CODECAST_ACCESS = "C0d3C4$t";
    /**
     * stores message with timestamp and uuid to csv
     * @param message
     */
    public static void log(String message){
        try{
            File csv = new File(CSV_PATH);

            FileWriter fileWriter = new FileWriter(csv, true);

            String date = getCurrentDate();
            String time = getCurrentTime();
            CSVWriter csvWriter = new CSVWriter(fileWriter);
            String[] data = {date, time,message};
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
            bufferedInputStream = new BufferedInputStream(new FileInputStream(CSV_PATH));
            client.storeFile(CSV_FILENAME, bufferedInputStream);
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
}
