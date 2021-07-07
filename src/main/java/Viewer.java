
import com.intellij.ui.jcef.JBCefBrowser;
import log.CsvLogger;

import javax.swing.*;

public class Viewer {

    private static String BROWSER_URL = "https://docs.google.com/forms/d/e/1FAIpQLSfW-vDJk4x4B325IhCI9nCzH3h8hSUArPkI5bzDJZsUtHHsGw/viewform?usp=sf_link";

    private JPanel windowContent;
    private JTextField headline;
    private JTextPane description;
    private JButton submitButton;
    private JTextPane importantField;
    private JBCefBrowser browser;

    public Viewer() {
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
    }

    public JPanel getContent(){
        return windowContent;
    }

    private void createUIComponents() {

    }
}
