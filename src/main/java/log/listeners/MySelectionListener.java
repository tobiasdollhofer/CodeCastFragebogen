package log.listeners;

import com.intellij.openapi.editor.event.SelectionEvent;
import com.intellij.openapi.editor.event.SelectionListener;
import log.Context;
import log.CsvLogger;
import log.EventType;
import org.jetbrains.annotations.NotNull;

/**
 * provides logging for changing of selected code/text
 */
public class MySelectionListener implements SelectionListener {

    @Override
    public void selectionChanged(@NotNull SelectionEvent e) {
        SelectionListener.super.selectionChanged(e);
        String text = e.getEditor().getSelectionModel().getSelectedText();
        if(text != null){
            CsvLogger.log(Context.EDITOR, EventType.SELECTION_CHANGED, text.replace("\n", "").replace("\r", ""));
        }
    }
}
