package log.listeners;

import com.intellij.openapi.fileEditor.*;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.util.messages.MessageBus;
import log.Context;
import log.CsvLogger;
import log.EventType;
import org.jetbrains.annotations.NotNull;

/**
 * provides logging events for opening closing and changing of selected files
 * registered in plugin.xml
 */
public class FileEventListener {

    private Project project;

    public FileEventListener(Project project) {
        this.project = project;
        initListener();
    }

    private void initListener() {
        MessageBus messageBus = project.getMessageBus();
        messageBus.connect().subscribe(FileEditorManagerListener.FILE_EDITOR_MANAGER, new FileEditorManagerListener() {

            @Override
            public void fileOpened(@NotNull FileEditorManager source, @NotNull VirtualFile file) {
                FileEditorManagerListener.super.fileOpened(source, file);
                CsvLogger.log(Context.EDITOR, EventType.FILE_OPENED, file.getName());
            }

            @Override
            public void fileClosed(@NotNull FileEditorManager source, @NotNull VirtualFile file) {
                FileEditorManagerListener.super.fileClosed(source, file);
                CsvLogger.log(Context.EDITOR, EventType.FILE_CLOSED, file.getName());
            }

            @Override
            public void selectionChanged(@NotNull FileEditorManagerEvent event) {
                FileEditorManagerListener.super.selectionChanged(event);
                VirtualFile newFile = event.getNewFile();
                if(newFile != null){
                    CsvLogger.log(Context.EDITOR, EventType.FILE_FOCUSES,newFile.getName());
                }
                CsvLogger.log(Context.EDITOR, EventType.FILE_FOCUSES, "No Focused File!");
            }
        });

    }
}
