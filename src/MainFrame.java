/**
 * @author: Haythem Khiri
 * @project: Advanced notepad Java App
 * @year: 2014
 * @license: GNU
 */
import javazoom.jl.decoder.JavaLayerException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.*;

/**
 * @author: Haythem Khiri
 */
public class MainFrame extends JFrame {

    private Font font;
    private TextPanel textPanel;
    private JFileChooser fileChooser;
    private File fnameContainer;
    private String currentLanguage;
    private Toolbar toolbar;

    public MainFrame() {
        super("Untitled.txt - Notepad");

        setLayout(new BorderLayout());

        font = new Font("Arial", Font.PLAIN, 15);
        textPanel = new TextPanel();
        fileChooser = new JFileChooser();
        currentLanguage = "English";
        toolbar = new Toolbar();
        setJMenuBar(createMenuBar());

        toolbar.getLanguages(currentLanguage);
        toolbar.setButtonListener(new ButtonListener() {
            @Override
            public void buttonEventOccured() {
                if (toolbar.getReadButtonText() == "Read text") {
                    try {
                        toolbar.readMessage(textPanel.getText().replace("\n", ". ").replace("\r", ". ").replace("\t", ". "));
                    } catch (IOException error) {
                        displayError(error);
                    } catch (JavaLayerException error) {
                        displayError(error);
                    } catch (NoSuchFieldException error) {
                        displayError(error);
                    } catch (IllegalArgumentException error) {
                        displayError(error);
                    } catch (IllegalAccessException error) {
                        displayError(error);
                    }
                } else {
                    toolbar.stopReadingMessage();
                }
            }
        });

        textPanel.setTextFont(font);
        add(textPanel, BorderLayout.CENTER);
        add(toolbar, BorderLayout.SOUTH);

        setMinimumSize(new Dimension(500, 400));
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        // components
        final JMenu fileMenu = new JMenu("File");
        JMenuItem newMenuItem = new JMenuItem("New");
        JMenuItem openMenuItem = new JMenuItem("Open");
        JMenuItem saveMenuItem = new JMenuItem("Save");
        JMenuItem saveAsMenuItem = new JMenuItem("Save as...");
        JMenuItem pageSetupMenuItem = new JMenuItem("Page Setup...");
        JMenuItem printMenuItem = new JMenuItem("Print");
        JMenuItem exitMenuItem = new JMenuItem("Exit");

        JMenu editMenu = new JMenu("Edit");
        JMenuItem undoMenuItem = new JMenuItem("Undo");
        JMenuItem redoMenuItem = new JMenuItem("Redo");
        JMenuItem cutMenuItem = new JMenuItem("Cut");
        JMenuItem copyMenuItem = new JMenuItem("Copy");
        JMenuItem pasteMenuItem = new JMenuItem("Paste");
        JMenuItem deleteMenuItem = new JMenuItem("Delete");
        JMenuItem findMenuItem = new JMenuItem("Find...");
        JMenuItem findNextMenuItem = new JMenuItem("Find Next");
        JMenuItem replaceMenuItem = new JMenuItem("Replace...");
        JMenuItem goToMenuItem = new JMenuItem("Go To...");
        JMenuItem selectAllMenuItem = new JMenuItem("Select All");
        JMenuItem timeDateMenuItem = new JMenuItem("Time/Date");

        JMenu formatMenu = new JMenu("Format");
        JMenuItem wordWrapMenuItem = new JMenuItem("Word wrap");
        JMenuItem fontMenuItem = new JMenuItem("Font...");

        JMenu viewMenu = new JMenu("View");
        JCheckBoxMenuItem statusBarMenuItem = new JCheckBoxMenuItem("Status Bar");

        JMenu helpMenu = new JMenu("Help");
        JMenuItem viewHelpMenuItem = new JMenuItem("View Help");
        JMenuItem aboutNotepadMenuItem = new JMenuItem("About Notepad");

        // ActionListeners
        newMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                MainFrame.this.setTitle("Untitled.txt - Notepad");
                textPanel.setText("");
                fnameContainer = null;
            }
        });

        openMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (fileChooser.showOpenDialog(MainFrame.this) == JFileChooser.APPROVE_OPTION) {
                    try {
                        File file = fileChooser.getSelectedFile();
                        OpenFile(file.getAbsolutePath());
                        MainFrame.this.setTitle(file.getName() + " - Notepad");
                    } catch (IOException error) {
                        displayError(error);
                    }
                }
            }
        });

        saveMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (fnameContainer != null) {
                    fileChooser.setCurrentDirectory(fnameContainer);
                    fileChooser.setSelectedFile(fnameContainer);
                } else {
                    fileChooser.setSelectedFile(new File("Untitled.txt"));
                }

                if (fileChooser.showSaveDialog(null) == fileChooser.APPROVE_OPTION) {
                    try {
                        File file = fileChooser.getSelectedFile();
                        SaveFile(file.getAbsolutePath());
                        MainFrame.this.setTitle(file.getName() + " - Notepad");
                        fnameContainer = file;
                    } catch (Exception error) {
                        displayError(error);
                    }
                }
            }
        });

        printMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

            }
        });

        exitMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (JOptionPane.showConfirmDialog(MainFrame.this, "Do you really want to exit this?", "Confirm Exit", JOptionPane.OK_CANCEL_OPTION) == JFileChooser.APPROVE_OPTION) {
                    System.exit(0);
                }
            }
        });

        undoMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (textPanel.canUndo()) {
                    textPanel.undo();
                }
            }
        });

        redoMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (textPanel.canRedo()) {
                    textPanel.redo();
                }
            }
        });

        selectAllMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                textPanel.selectAll();
            }
        });

        cutMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                textPanel.cut();
            }
        });

        copyMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                textPanel.copy();
            }
        });

        pasteMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                textPanel.paste();
            }
        });

        aboutNotepadMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JOptionPane.showMessageDialog(MainFrame.this,
                        "Author: Haythem Khiri\n" +
                        "Github: http://github.com/haythemkh\n" +
                        "Contact: haythem.khiri@yahoo.fr", "About", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // set up Mnemonics
        fileMenu.setMnemonic(KeyEvent.VK_F);
        newMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        openMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        saveMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        printMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));

        editMenu.setMnemonic(KeyEvent.VK_E);
        undoMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, ActionEvent.CTRL_MASK));
        redoMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, ActionEvent.CTRL_MASK));
        cutMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
        copyMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
        pasteMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));
        deleteMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0));
        findMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, ActionEvent.CTRL_MASK));
        findNextMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F3, 0));
        replaceMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, ActionEvent.CTRL_MASK));
        goToMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, ActionEvent.CTRL_MASK));
        selectAllMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));
        timeDateMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0));

        formatMenu.setMnemonic(KeyEvent.VK_O);
        wordWrapMenuItem.setMnemonic(KeyEvent.VK_W);
        fontMenuItem.setMnemonic(KeyEvent.VK_F);

        viewMenu.setMnemonic(KeyEvent.VK_V);
        statusBarMenuItem.setMnemonic(KeyEvent.VK_S);

        helpMenu.setMnemonic(KeyEvent.VK_H);
        viewHelpMenuItem.setMnemonic(KeyEvent.VK_H);
        aboutNotepadMenuItem.setMnemonic(KeyEvent.VK_A);

        // set up Menus
        fileMenu.add(newMenuItem);
        fileMenu.add(openMenuItem);
        fileMenu.add(saveMenuItem);
        fileMenu.add(saveAsMenuItem);
        fileMenu.addSeparator();
        fileMenu.add(pageSetupMenuItem);
        fileMenu.add(printMenuItem);
        fileMenu.addSeparator();
        fileMenu.add(exitMenuItem);

        editMenu.add(undoMenuItem);
        editMenu.add(redoMenuItem);
        editMenu.addSeparator();
        editMenu.add(cutMenuItem);
        editMenu.add(copyMenuItem);
        editMenu.add(pasteMenuItem);
        editMenu.add(deleteMenuItem);
        editMenu.addSeparator();
        editMenu.add(findMenuItem);
        editMenu.add(findNextMenuItem);
        editMenu.add(replaceMenuItem);
        editMenu.add(goToMenuItem);
        editMenu.addSeparator();
        editMenu.add(selectAllMenuItem);
        editMenu.add(timeDateMenuItem);

        formatMenu.add(wordWrapMenuItem);
        formatMenu.add(fontMenuItem);

        viewMenu.add(statusBarMenuItem);

        helpMenu.add(viewHelpMenuItem);
        helpMenu.addSeparator();
        helpMenu.add(aboutNotepadMenuItem);

        // set up MenuBar
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(formatMenu);
        menuBar.add(viewMenu);
        menuBar.add(helpMenu);

        return menuBar;
    }

    /**
     * Opens a file
     *
     * @param fname
     * @throws IOException
     */
    public void OpenFile(String fname) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fname)));
        String lineContent;

        textPanel.setText("");

        setCursor(new Cursor(Cursor.WAIT_CURSOR));

        while (((lineContent = br.readLine()) != null)) {
            textPanel.setText(textPanel.getText() + lineContent + "\r\n");
        }

        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        br.close();
    }

    /**
     * Saves a file
     *
     * @param fname
     * @throws IOException
     */
    public void SaveFile(String fname) throws IOException {
        setCursor(new Cursor(Cursor.WAIT_CURSOR));
        DataOutputStream dos = new DataOutputStream(new FileOutputStream(fname));
        dos.writeBytes(textPanel.getText());
        dos.close();
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }

    /**
     * Formats the error message to prepare it for display
     *
     * @param error
     */
    public void displayError(Exception error) {
        JOptionPane.showMessageDialog(MainFrame.this, "Unfortunately there is an error:\n" + error.toString(), "Error", JOptionPane.OK_OPTION | JOptionPane.ERROR_MESSAGE);
    }
}
