
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBPanel;
import com.intellij.ui.jcef.JBCefBrowser;
import com.intellij.uiDesigner.core.GridLayoutManager;

import javax.swing.*;

public class Viewer {


    private JPanel windowContent;
    private JBCefBrowser browser;

    public JPanel getContent(){
        return windowContent;
    }

    private void createUIComponents() {
        browser = new JBCefBrowser("https://docs.google.com/forms/d/e/1FAIpQLSfW-vDJk4x4B325IhCI9nCzH3h8hSUArPkI5bzDJZsUtHHsGw/viewform?usp=sf_link");
        windowContent = (JPanel) browser.getComponent();
    }
}
