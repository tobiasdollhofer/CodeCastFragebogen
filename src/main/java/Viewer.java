
import com.intellij.ide.plugins.PluginManager;
import com.intellij.openapi.extensions.PluginId;
import com.intellij.ui.jcef.JBCefApp;
import com.intellij.ui.jcef.JBCefBrowser;
import log.Context;
import log.CsvLogger;
import log.EventType;
import uuid.UuidHelper;

import javax.swing.*;
import java.awt.*;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class Viewer {

    private static String UUID_PREFILLED_TEST_URL = "https://docs.google.com/forms/d/e/1FAIpQLScMQo8qtIyEJlV3pQazn-TrXRdZkDKDxYKa0HzcNHKW8Oe4gg/viewform?usp=pp_url&entry.2064818074=" + UuidHelper.getInstance().getUuid();
    private static String UUID_PREFILLED_QUESTIONAIRE_URL = "https://docs.google.com/forms/d/e/1FAIpQLSdPkV_exHIWgXkJ8vkYt73bryyf48coaDc2l-AKWg6G8AXVPg/viewform?usp=pp_url&entry.152260275=" + UuidHelper.getInstance().getUuid();
    private static String UUID_PREFILLED_TEST_URL_CODECAST = "https://docs.google.com/forms/d/e/1FAIpQLSebZuywaXdlaP4wTjBHvPQoPv8QwykDeWfbGi__BoZWPhTHOQ/viewform?usp=pp_url&entry.1153713854=" + UuidHelper.getInstance().getUuid();


    private JPanel windowContent;
    private JTextPane title;
    private JTextPane questionaireHeadline;
    private JButton questionaireButton;
    private JTextPane testHeadline;
    private JTextPane testExplanation;
    private JButton finishStudyButton;
    private JTextPane questionaireExplanation;
    private JBCefBrowser browser;

    public Viewer() {
        if(PluginManager.isPluginInstalled(PluginId.getId("de.tobiasdollhofer.CodeCast"))){
            finishStudyButton.addActionListener((e) ->{
                try{
                    openWebpage(new URL(UUID_PREFILLED_TEST_URL_CODECAST));
                    CsvLogger.log(Context.EDITOR, EventType.TEST_CLICKED, "");
                    CsvLogger.sendToServer();
                }catch (MalformedURLException ex){
                    ex.printStackTrace();
                }
            });
        }else{
            finishStudyButton.addActionListener((e) ->{
                try{
                    openWebpage(new URL(UUID_PREFILLED_TEST_URL));
                    CsvLogger.log(Context.EDITOR, EventType.TEST_CLICKED, "");
                    CsvLogger.sendToServer();
                }catch (MalformedURLException ex){
                    ex.printStackTrace();
                }
            });
        }

        questionaireButton.addActionListener((e)->{
            try{
                openWebpage(new URL(UUID_PREFILLED_QUESTIONAIRE_URL));
                CsvLogger.log(Context.EDITOR, EventType.QUESTIONAIRE_CLICKED, "");
            }catch (MalformedURLException ex){
                ex.printStackTrace();
            }
        });

    }

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

    public static boolean openWebpage(URL url) {
        try {
            return openWebpage(url.toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return false;
    }

    public JPanel getContent(){
        return windowContent;
    }

    private void createUIComponents() {

    }
}
