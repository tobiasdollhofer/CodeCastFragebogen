package ui;

import com.intellij.ide.plugins.PluginManager;
import com.intellij.openapi.extensions.PluginId;
import log.Context;
import log.CsvLogger;
import log.EventType;
import util.Config;
import uuid.UuidHelper;
import javax.swing.*;
import java.awt.*;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Viewer class to show buttons for questionare and test webforms
 */
public class Viewer {


    private JPanel windowContent;
    private JTextPane title;
    private JTextPane questionaireHeadline;
    private JButton questionaireButton;
    private JTextPane testHeadline;
    private JTextPane testExplanation;
    private JButton finishStudyButton;
    private JTextPane questionaireExplanation;


    public Viewer() {
        initListener();
    }

    /**
     * initializes both buttons
     */
    private void initListener() {
        questionaireButton.addActionListener((e)->{
            try{
                openWebpage(new URL(Config.QUESTIONAIRE_URL));
                CsvLogger.log(Context.EDITOR, EventType.QUESTIONAIRE_CLICKED, "");
            }catch (MalformedURLException ex){
                ex.printStackTrace();
            }
        });

        initFinishStudyListener();
    }

    /**
     * initializes finish study button listener with link to webform depending on if codecast is installed too
     */
    private void initFinishStudyListener() {
        if(PluginManager.isPluginInstalled(PluginId.getId(Config.CODECAST_PLUGIN_ID))){
            finishStudyButton.addActionListener((e) ->{
                try{
                    openWebpage(new URL(Config.CODECAST_TEST_URL));
                    CsvLogger.log(Context.EDITOR, EventType.TEST_CLICKED, "");
                    CsvLogger.sendToServer();
                }catch (MalformedURLException ex){
                    ex.printStackTrace();
                }
            });
        }else{
            finishStudyButton.addActionListener((e) ->{
                try{
                    openWebpage(new URL(Config.TEST_URL));
                    CsvLogger.log(Context.EDITOR, EventType.TEST_CLICKED, "");
                    CsvLogger.sendToServer();
                }catch (MalformedURLException ex){
                    ex.printStackTrace();
                }
            });
        }
    }

    /**
     * opens provided uri in default browser of computer
     * @param uri uri to open
     * @return success of operation
     */
    public static boolean openWebpage(URI uri) {
        Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
        if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
            try {
                desktop.browse(uri);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * opens provided url in default browser of computer
     * @param url url to open
     * @return success of operation
     */
    public static boolean openWebpage(URL url) {
        try {
            return openWebpage(url.toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * @return content of view
     */
    public JPanel getContent(){
        return windowContent;
    }

    private void createUIComponents() {
    }
}
