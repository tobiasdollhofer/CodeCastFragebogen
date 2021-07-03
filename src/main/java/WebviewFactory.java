import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import org.jetbrains.annotations.NotNull;

public class WebviewFactory implements ToolWindowFactory {


    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        UuidHelper uuidHelper = UuidHelper.getInstance();
        System.out.println(uuidHelper.getUuid());
        CsvLogger.log("Toolwindow Created");
        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
        Viewer viewer = new Viewer();
        Content content = contentFactory.createContent(viewer.getContent(), "CodeCast Fragebogen", false);
        toolWindow.getContentManager().addContent(content);
    }
}
