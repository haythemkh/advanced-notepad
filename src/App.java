/**
 * @author: Haythem Khiri
 * @project: Advanced notepad Java App
 * @year: 2014
 * @license: GNU
 */

import javax.swing.*;

/**
 * @author: Haythem Khiri
 */
public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainFrame();
            }
        });
    }
}
