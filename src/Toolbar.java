/**
 * @author: Haythem Khiri
 * @project: Advanced notepad Java App
 * @year: 2014
 * @license: GNU
 */
import com.gtranslate.Audio;
import com.gtranslate.Language;
import javazoom.jl.decoder.JavaLayerException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;

/**
 * @author: Haythem Khiri
 */
public class Toolbar extends JPanel implements ActionListener {

    private JComboBox languageComboBox;
    private final JButton readTextButton;
    private ButtonListener buttonListener;

    public Toolbar() {
        setBorder(BorderFactory.createEtchedBorder());

        readTextButton = new JButton("Read text");
        languageComboBox = new JComboBox();
        languageComboBox.setModel(new DefaultComboBoxModel(new String[]{"Item 1", "Item 2", "Item 3", "Item 4"}));

        readTextButton.addActionListener(this);

        setLayout(new FlowLayout(FlowLayout.RIGHT));

        add(languageComboBox);
        add(readTextButton);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        JButton clicked = (JButton) actionEvent.getSource();

        if (clicked == readTextButton) {
            buttonListener.buttonEventOccured();
        }
    }

    public String getReadButtonText() {
        return this.readTextButton.getText();
    }

    public void setButtonListener(ButtonListener bl) {
        this.buttonListener = bl;
    }

    /**
     * Gets the list of languages and insert them into languageComboBox
     *
     * @param defaultLanguage
     */
    public void getLanguages(String defaultLanguage) {
        Field[] fields = Language.class.getDeclaredFields();
        languageComboBox.removeAllItems();
        for (Field field : fields) {
            if (field.getType() == String.class) {
                languageComboBox.addItem(new Utils().formatLanguageName(field.getName()));
            }
        }

        languageComboBox.setSelectedItem(defaultLanguage);
    }

    /**
     * Read the inputted message
     *
     * @param message
     * @throws java.io.IOException
     * @throws javazoom.jl.decoder.JavaLayerException
     * @throws NoSuchFieldException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    public void readMessage(String message) throws IOException, JavaLayerException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        if (message != null && message != "") {
            Audio audio = Audio.getInstance();

            String language = languageComboBox.getSelectedItem().toString().toUpperCase().replace(" ", "_");
            InputStream sound = audio.getAudio(message, (String) Language.class.getField(language).get(null));
            audio.play(sound);
            readTextButton.setText("Read text");
        }
    }

    /**
     * Stop reading the message
     */
    public void stopReadingMessage() {
        readTextButton.setText("Stop reading");
    }
}
