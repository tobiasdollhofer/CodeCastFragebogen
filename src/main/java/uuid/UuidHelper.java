package uuid;

import java.io.*;
import java.util.UUID;

/**
 * helper class to get a uuid for csv logging file
 */
public class UuidHelper {

    private static String CODECAST_ROOT = System.getProperty("user.home") + "/" + "codecast" + "/";
    private static String UUID_FILE_PATH = CODECAST_ROOT + "uuid.txt";


    private UUID uuid;
    private UUID sessionId;

    private static UuidHelper instance;

    private UuidHelper(){
        uuid = loadUuidFromUserHome();
        if(uuid == null){
            createNewUuid();
        }
        createNewSessionId();
    }

    /**
     *
     * @return uuid of this user
     */
    public UUID getUuid() {
        return uuid;
    }

    /**
     *
     * @return current session id
     */
    public UUID getSessionId(){
        return sessionId;
    }

    /**
     *
     * @return uuid.UuidHelper instance
     */
    public static UuidHelper getInstance(){
        if(UuidHelper.instance == null){
            UuidHelper.instance = new UuidHelper();
        }
        return UuidHelper.instance;
    }


    /**
     * creates new uuid and saves it to codecast directory as uuid.txt
     */
    private void createNewUuid() {
        uuid = UUID.randomUUID();
        writeId(UUID_FILE_PATH, uuid);
    }


    private void createNewSessionId() {
        sessionId = UUID.randomUUID();
    }


    private void writeId(String path, UUID id){
        try {
            File idFile = new File(path);

            BufferedWriter writer = new BufferedWriter(new FileWriter(idFile));
            writer.write(id.toString());

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * checks if there plugin was already initialized with uuid and loads it from uuid.txt file
     * @return uuid of user or null of not already initialized
     */
    private UUID loadUuidFromUserHome() {
        return loadIdFromPath(UUID_FILE_PATH);
    }


    /**
     *
     * @param path path of potential file
     * @return uuid of txt file or null if file not exist
     */
    private UUID loadIdFromPath(String path){
        // check if there is a codecast directory
        File codecastRoot = new File(CODECAST_ROOT);
        if(!codecastRoot.exists()) {
            // create codecast directory for new uuid creation
            codecastRoot.mkdir();
            return null;
        }

        // check if there is a uuid file to extract
        File idFile = new File(path);
        if(!idFile.exists()) return null;

        return readUuid(path);
    }

    /**
     * reads uuid from txt at path and returns it
     * @return uuid or null if not already initialized
     */
    private UUID readUuid(String path) {
        File idFile = new File(path);
        try{
            BufferedReader reader = new BufferedReader(new FileReader(idFile));
            String line = "";
            if((line = reader.readLine()) != null){
                UUID uuid = UUID.fromString(line);
                return uuid;
            }
        }catch (IOException | IllegalArgumentException e){
            e.printStackTrace();
        }

        return null;
    }

}
