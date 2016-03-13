/**
 * @author: Haythem Khiri
 * @project: Advanced notepad Java App
 * @year: 2014
 * @license: GNU
 */
import javax.swing.*;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.UndoManager;
import java.awt.*;

/**
 * @author: Haythem Khiri
 */
public class TextPanel extends JPanel {

    private JTextArea textArea;
    private UndoManager undoManager;
    private boolean canUndo, canRedo;

    public TextPanel() {
        setLayout(new BorderLayout());

        textArea = new JTextArea();
        undoManager = new UndoManager();

        textArea.getDocument().addUndoableEditListener(new UndoableEditListener() {
            @Override
            public void undoableEditHappened(UndoableEditEvent e) {
                undoManager.addEdit(e.getEdit());
                canUndo = undoManager.canUndo();
                canRedo = undoManager.canRedo();
            }
        });

        add(new JScrollPane(textArea), BorderLayout.CENTER);
    }

    public boolean canUndo() {
        return canUndo;
    }

    public boolean canRedo() {
        return canRedo;
    }

    public String getText() {
        return textArea.getText();
    }

    public void setText(String text) {
        textArea.setText(text);
    }

    public void setTextFont(Font font) {
        textArea.setFont(font);
    }

    public void selectAll() {
        textArea.selectAll();
    }

    public void undo() {
        try {
            undoManager.undo();
            canUndo = undoManager.canUndo();
            canRedo = undoManager.canRedo();
        } catch (CannotRedoException cre) {
            System.out.println(cre);
        }
    }

    public void redo() {
        try {
            undoManager.redo();
            canUndo = undoManager.canUndo();
            canRedo = undoManager.canRedo();
        } catch (CannotRedoException cre) {
            System.out.println(cre);
        }
    }

    public void cut() {
        textArea.cut();
    }

    public void copy() {
        textArea.copy();
    }

    public void paste() {
        textArea.paste();
    }
}
