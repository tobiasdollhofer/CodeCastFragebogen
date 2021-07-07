
import com.intellij.ui.jcef.JBCefApp;
import com.intellij.ui.jcef.JBCefBrowser;
import log.CsvLogger;

import javax.swing.*;
import java.awt.*;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class Viewer {

    private static String BROWSER_URL = "https://forms.gle/P8jMtnnQkfJSwcxM8";

    private JPanel windowContent;
    private JTextField headline;
    private JTextPane description;
    private JButton submitButton;
    private JTextPane importantField;
    private JBCefBrowser browser;

    public Viewer() {
        if(JBCefApp.isSupported()){
            browser = new JBCefBrowser(BROWSER_URL);
            submitButton.addActionListener((e) -> {
                windowContent.remove(headline);
                windowContent.remove(description);
                windowContent.remove(submitButton);
                windowContent.remove(importantField);
                windowContent.setLayout(new BoxLayout(windowContent, BoxLayout.Y_AXIS));

                windowContent.add((JPanel) browser.getComponent());
                CsvLogger.sendToServer();
            });
        }else{
            submitButton.addActionListener((e) -> {
                try{
                    openWebpage(new URL(BROWSER_URL));
                    CsvLogger.sendToServer();
                }catch (MalformedURLException ex){
                    ex.printStackTrace();
                }

            });
        }

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
