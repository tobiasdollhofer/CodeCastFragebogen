package log.listeners;

import com.intellij.openapi.editor.event.CaretEvent;
import com.intellij.openapi.editor.event.CaretListener;
import log.Context;
import log.CsvLogger;
import log.EventType;
import org.jetbrains.annotations.NotNull;

public class MyCaretListener implements CaretListener {

    @Override
    public void caretPositionChanged(@NotNull CaretEvent event) {
        CaretListener.super.caretPositionChanged(event);
        int line = event.getEditor().getCaretModel().getLogicalPosition().line + 1;
        int col = event.getEditor().getCaretModel().getLogicalPosition().column;
        CsvLogger.log(Context.EDITOR, EventType.CARET_POSITION_CHANGED, line + ":" + col);
    }


}
