/**
 * @author: Haythem Khiri
 * @project: Advanced notepad Java App
 * @year: 2014
 * @license: GNU
 */

/**
 * @author: Haythem Khiri
 */
public class Utils {

    public Utils() {}

    /**
     * Formats the language's name to prepare it for display
     * @param languageName
     * @return
     */
    public String formatLanguageName(String languageName) {
        return (languageName.substring(0, 1).toUpperCase() + languageName.substring(1).toLowerCase()).replace("_", " ");
    }
}
