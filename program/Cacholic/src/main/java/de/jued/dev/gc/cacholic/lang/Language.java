package de.jued.dev.gc.cacholic.lang;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Multi Language Support
 * 
 * @author Juergen Edelbluth <dev@jued.de>
 */
public class Language
{

    public static final String DEFAULT_LANGUAGE = "en";
    
    private String languageCode = null;
    private Properties properties = null;
    
    /**
     * Because of the initialization, the constructor is hidden. Use one of the
     * factories.
     * 
     * @see Language#factory() 
     * @see Language#factory(java.lang.String) 
     * 
     * @param language Language Code
     */
    private Language(final String language)
    {
        this.languageCode = language;
    }
    
    /**
     * Initialize the class
     */
    private void initialize()
    {
        this.properties = new Properties();
        try
        {
            this.properties.load(Language.class.getResourceAsStream(String.format("%s.properties", this.languageCode)));
        }
        catch (IOException | NullPointerException ex)
        {
            Logger.getLogger(Language.class.getCanonicalName()).log(Level.WARNING, String.format("Language '%s' could not be loaded.", languageCode), ex);
            this.properties = new Properties();
        }
    }
    
    /**
     * Get the language word
     * 
     * @param key The key in the language file
     * @param def The default value if nothing found
     * @return The language value or <code>def</code> if not found
     */
    public String get(final String key, final String def)
    {
        return this.properties.getProperty(key, def);
    }
    
    /**
     * Get a language word
     * 
     * @param key The key in the language file
     * @return The language value or a default value signalling key not found
     */
    public String get(final String key)
    {
        return this.get(key, String.format("[Key '%s' not found in '%s']", key, this.languageCode));
    }
    
    /**
     * Create a new language instance with given language code
     * 
     * @param language Language Code
     * @return The language object
     */
    public static Language factory(final String language)
    {
        final Language lang = new Language(language);
        lang.initialize();
        return lang;
    }
    
    /**
     * Create a language instance with the default language
     * 
     * @see Language#DEFAULT_LANGUAGE
     * @return The language object
     */
    public static Language factory()
    {
        return Language.factory(Language.DEFAULT_LANGUAGE);
    }
    
}
