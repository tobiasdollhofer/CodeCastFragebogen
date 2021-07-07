package util;
import com.android.tools.r8.graph.S;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.editor.EditorFactory;
import com.intellij.openapi.editor.event.SelectionEvent;
import com.intellij.openapi.editor.event.SelectionListener;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManagerListener;
import log.Context;
import log.CsvLogger;
import log.EventType;
import log.listeners.FileEventListener;
import log.listeners.MyCaretListener;
import log.listeners.MySelectionListener;
import org.jetbrains.annotations.NotNull;

/**
 * partly from: https://github.com/JetBrains/intellij-sdk-code-samples/blob/main/max_opened_projects/src/main/java/org/intellij/sdk/maxOpenProjects/ProjectOpenCloseListener.java
 */
public class ProjectOpenCloseListener implements ProjectManagerListener {

    /**
     * initializes services on project startup after index is built up
     * @param project current project
     */
    @Override
    public void projectOpened(@NotNull Project project) {
        ProjectManagerListener.super.projectOpened(project);
        //return if application is in unit-test mode
        if(ApplicationManager.getApplication().isUnitTestMode()){
            return;
        }


        FileEventListener listener = new FileEventListener(project);
        EditorFactory.getInstance().getEventMulticaster().addCaretListener(new MyCaretListener(), ApplicationManager.getApplication());
        EditorFactory.getInstance().getEventMulticaster().addSelectionListener(new MySelectionListener(), ApplicationManager.getApplication());
    }

    /**
     * clears playlist on project close
     * @param project current project
     */
    @Override
    public void projectClosed(@NotNull Project project) {
        ProjectManagerListener.super.projectClosed(project);

        //return if application is in unit-test mode
        if(ApplicationManager.getApplication().isUnitTestMode()){
            return;
        }
    }
}

