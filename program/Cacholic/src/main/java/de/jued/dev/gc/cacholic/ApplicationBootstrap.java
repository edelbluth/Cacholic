package de.jued.dev.gc.cacholic;

import com.google.common.base.Joiner;
import de.jued.dev.lib.libjued.CommandLine;
import de.jued.dev.lib.libjued.Stream;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The Cacholic Application is an easy to use Geocaching database.
 * 
 * @author Juergen Edelbluth <dev@jued.de>
 * @version 0.1
 * @since 0.1
 */
public class ApplicationBootstrap 
{
    
    public static final String JVMOPTIONS = "META-INF/JVMOptions";
    
    /**
     * Destroy a process and hide the exceptions
     * 
     * @param p The process to be destroyed.
     */
    private static void destroyProcess(final Process p)
    {
        if (null == p)
        {
            return;
        }
        try
        {
            p.destroy();
        }
        catch(Exception e)
        {
            Logger.getLogger(ApplicationBootstrap.class.getCanonicalName()).log(Level.WARNING, "Unable to destroy process.", e);
        }
    }
    
    /**
     * The standard main method. This is where the program starts.
     * 
     * @param args Command Line Arguments
     */
    public static void main(final String[] args)
    {
        final CommandLine cmd = CommandLine.factory(args);
        if (cmd.isSwitched("bootstrapped"))
        {
            try 
            {
                Cacholic.setup(cmd).launch();
            }
            catch (InterruptedException | InvocationTargetException | CacholicException ex)
            {
                Logger.getLogger(ApplicationBootstrap.class.getCanonicalName()).log(Level.SEVERE, "Unable to launch application.", ex);
                System.exit(-1);
            }
        }
        else
        {
            InputStream in = null;
            try
            {
                in = ApplicationBootstrap.class.getClassLoader().getResourceAsStream(JVMOPTIONS);
            }
            catch (NullPointerException e)
            {
                Logger.getLogger(ApplicationBootstrap.class.getCanonicalName()).log(Level.SEVERE, String.format("Unable to read JVM Options from %s", JVMOPTIONS), e);
                System.exit(-1);
                return;
            }
            InputStreamReader isr = null;
            try
            {
                isr = new InputStreamReader(in);
            }
            catch (NullPointerException e)
            {
                Logger.getLogger(ApplicationBootstrap.class.getCanonicalName()).log(Level.SEVERE, "Unable to read JVM Options from META-INF/JVMOptions", e);
                Stream.close(in);
                System.exit(-1);
                return;
            }
            BufferedReader br;
            try
            {
                br = new BufferedReader(isr);
            }
            catch (Exception e)
            {
                Logger.getLogger(ApplicationBootstrap.class.getCanonicalName()).log(Level.SEVERE, "Unable to read JVM Options from META-INF/JVMOptions", e);
                Stream.close(isr);
                Stream.close(in);
                System.exit(-1);  
                return;
            }
            final LinkedList<String> jvmArgs = new LinkedList<>();
            try
            {
                String line;
                while ((line = br.readLine()) != null)
                {
                    if (line == null)
                    {
                        continue;
                    }
                    final String l = line.trim();
                    if (l.startsWith("#"))
                    {
                        continue;
                    }
                    if (l.length() <= 0)
                    {
                        continue;
                    }
                    jvmArgs.add(l);
                }
            }
            catch (IOException e)
            {
                Logger.getLogger(ApplicationBootstrap.class.getCanonicalName()).log(Level.SEVERE, "Unable to read JVM Options from META-INF/JVMOptions", e);
                Stream.close(br);
                Stream.close(isr);
                Stream.close(in);
                System.exit(-1);  
                return;                
            }
            Stream.close(br);
            Stream.close(isr);
            Stream.close(in);
            final String javaHome = System.getProperty("java.home");
            File j = new File(javaHome);
            j = new File(j, "bin");
            j = new File(j, "java");
            final String java = j.getAbsolutePath();
            final String jvmOptions = Joiner.on(' ').skipNulls().join(jvmArgs);
            final String dArgs = Joiner.on(' ').skipNulls().join(args);
            final File jar = new File(ApplicationBootstrap.class.getProtectionDomain().getCodeSource().getLocation().getPath());
            final String loc = jar.getAbsolutePath().trim();
            String javaCall;
            if (loc.endsWith(".jar"))
            {
                javaCall = String.format("%s %s -jar %s %s -bootstrapped", java, jvmOptions, loc, dArgs);
            }
            else
            {
                final String classPath = String.format("%s%c%s", loc, File.pathSeparatorChar, System.getProperty("java.class.path"));
                javaCall = String.format("%s %s -classpath \"%s\" %s %s -bootstrapped", java, jvmOptions, classPath, ApplicationBootstrap.class.getCanonicalName(), dArgs);
            }
            Logger.getLogger(ApplicationBootstrap.class.getCanonicalName()).log(Level.INFO, String.format("Booting application code: %s", javaCall));
            Process proc;
            try
            {
                proc = Runtime.getRuntime().exec(javaCall);
            }
            catch (IOException e)
            {
                Logger.getLogger(ApplicationBootstrap.class.getCanonicalName()).log(Level.SEVERE, "Unable to launch application.", e);
                System.exit(-1);
                return;
            }
            final BufferedInputStream stdout = new BufferedInputStream(proc.getInputStream());
            final BufferedInputStream stderr = new BufferedInputStream(proc.getErrorStream());
            Thread stdoutThread = new Thread(String.format("%s stdout", ApplicationBootstrap.class.getCanonicalName()))
            {
                @Override
                public void run()
                {
                    try
                    {
                        Stream.copy(stdout, System.out, 2, true);
                    }
                    catch (IOException e)
                    {
                        Logger.getLogger(ApplicationBootstrap.class.getCanonicalName()).log(Level.FINE, "Unable to read stdout.", e);
                    }
                }
            };            
            Thread stderrThread = new Thread(String.format("%s stderr", ApplicationBootstrap.class.getCanonicalName()))
            {
                @Override
                public void run()
                {
                    try
                    {
                        Stream.copy(stderr, System.err, 2, true);
                    }
                    catch (IOException e)
                    {
                        Logger.getLogger(ApplicationBootstrap.class.getCanonicalName()).log(Level.FINE, "Unable to read stdout.", e);
                    }
                }
            };
            stderrThread.setDaemon(true);
            stderrThread.start();
            stdoutThread.setDaemon(true);
            stdoutThread.start();
            try
            {
                proc.waitFor();
            }
            catch (InterruptedException e)
            {
                Logger.getLogger(ApplicationBootstrap.class.getCanonicalName()).log(Level.SEVERE, "Unable to wait for the process to exit.", e);
                ApplicationBootstrap.destroyProcess(proc);
                Stream.close(stderr);
                Stream.close(stdout);
                System.exit(-1);
                return;
            }
            ApplicationBootstrap.destroyProcess(proc);
            Stream.close(stderr);
            Stream.close(stdout);
            System.exit(0);
        }
    }
    
}
