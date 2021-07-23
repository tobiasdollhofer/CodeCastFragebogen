package log.listeners;

import com.intellij.openapi.editor.event.CaretEvent;
import com.intellij.openapi.editor.event.CaretListener;
import com.intellij.openapi.util.TextRange;
import log.Context;
import log.CsvLogger;
import log.EventType;
import org.jetbrains.annotations.NotNull;

/**
 * provides logging for changing of caret position
 */
public class MyCaretListener implements CaretListener {

    @Override
    public void caretPositionChanged(@NotNull CaretEvent event) {
        CaretListener.super.caretPositionChanged(event);
        int line = event.getEditor().getCaretModel().getLogicalPosition().line + 1;
        int col = event.getEditor().getCaretModel().getLogicalPosition().column;

        String text = getLineContent(event, line-1);
        CsvLogger.log(Context.EDITOR, EventType.CARET_POSITION_CHANGED, line + ":" + col + ", line content: " + text);
    }

    /**
     *
     * @param e caret event
     * @param line line of caret
     * @return text of code line
     */
    private String getLineContent(CaretEvent e, int line){
        int offsetStart = e.getEditor().getDocument().getLineStartOffset(line);
        int offsetEnd = e.getEditor().getDocument().getLineEndOffset(line);
        return e.getEditor().getDocument().getText(new TextRange(offsetStart, offsetEnd)).trim();
    }
}
