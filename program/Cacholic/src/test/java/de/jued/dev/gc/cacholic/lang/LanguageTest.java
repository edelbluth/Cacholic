package de.jued.dev.gc.cacholic.lang;

import static junit.framework.Assert.assertEquals;
import junit.framework.TestCase;

/**
 * Simple minimum Test
 * 
 * @author Juergen Edelbluth
 * @version 0.1
 * @since 0.1
 */
public class LanguageTest extends TestCase 
{
    
    public LanguageTest(String testName)
    {
        super(testName);
    }
    
    @Override
    protected void setUp() throws Exception 
    {
        super.setUp();
    }
    
    @Override
    protected void tearDown() throws Exception
    {
        super.tearDown();
    }

    /**
     * Test of get method, of class Language.
     */
    public void testGet0_String_String()
    {
        System.out.println("get");
        String key = "gc.AppName";
        String def = "def";
        Language instance = Language.factory();
        String expResult = "Cacholic Geocaching Organizer";
        String result = instance.get(key, def);
        assertEquals(expResult, result);
    }

    /**
     * Test of get method, of class Language.
     */
    public void testGet1_String_String()
    {
        System.out.println("get");
        String key = "gc.AppNameXXX";
        String def = "def";
        Language instance = Language.factory();
        String expResult = def;
        String result = instance.get(key, def);
        assertEquals(expResult, result);
    }

    /**
     * Test of get method, of class Language.
     */
    public void testGet0_String() 
    {
        System.out.println("get");
        String key = "gc.AppName";
        Language instance = Language.factory();
        String expResult = "Cacholic Geocaching Organizer";
        String result = instance.get(key);
        assertEquals(expResult, result);
    }

    /**
     * Test of get method, of class Language.
     */
    public void testGet1_String() 
    {
        System.out.println("get");
        String key = "gc.AppNameXXX";
        Language instance = Language.factory();
        String expResult = String.format("[Key '%s' not found in 'en']", key);
        String result = instance.get(key);
        assertEquals(expResult, result);
    }

    /**
     * Test of factory method, of class Language.
     */
    public void testFactory()
    {
        System.out.println("factory");
        Language result = Language.factory();
        assertNotNull(result);
    }
}
