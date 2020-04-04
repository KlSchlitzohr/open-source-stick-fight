package de.klschlitzohr.stickfight.message.language;

import com.google.common.io.Files;
import de.klschlitzohr.stickfight.main.Main;
import org.bukkit.Bukkit;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class LanguageManager {

    private Thread loadingThread;

    private String language;
    private HashMap<String, String> messages = new HashMap<>();

    public void load() {
        this.loadingThread = new Thread(() -> {
            try {
                File selected = new File("plugins/Stickfight/languages/selected.cfg");

                if (!selected.getParentFile().exists()) {
                    this.saveDefaultLanguages(selected.getParentFile());
                }

                if (!selected.exists()) {
                    selected.createNewFile();

                    FileOutputStream out = new FileOutputStream(selected);
                    out.write("en".getBytes(StandardCharsets.UTF_8));
                    out.close();
                }

                language = Files.readFirstLine(selected, StandardCharsets.UTF_8);

                File languageFile = new File(selected.getParentFile(), "messages_" + language + ".properties");

                if (!languageFile.exists()) {
                    languageFile = new File(selected.getParentFile(), "messages.properties");

                    if (!languageFile.exists()) {
                        this.messages = null;
                        this.saveDefaultLanguages(selected.getParentFile());
                        return;
                    }
                }

                if (language.equals("en"))
                    languageFile = new File(selected.getParentFile(), "messages.properties");

                this.loadMessages(languageFile);
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }, "Language Loader");

        this.loadingThread.start();
    }

    public boolean changeLanguage(String language) {
        if (!language.equals("en"))
            language = "_" + language;
        else
            language = "";

        File languageFile = new File("plugins/Stickfight/languages/messages" + language + ".properties");

        if (!languageFile.exists())
            return false;

        try {
            this.loadMessages(languageFile);
        } catch (IOException exception) {
            return false;
        }

        this.saveChanges(language);

        return true;
    }

    private void saveChanges(String language) {
        new Thread(() -> {
            File selected = new File("plugins/Stickfight/languages/selected.cfg");

            if (selected.exists())
                selected.delete();

            try {
                selected.createNewFile();

                FileOutputStream out = new FileOutputStream(selected);
                out.write(language.getBytes(StandardCharsets.UTF_8));
                out.close();
            } catch (IOException ignored) { }
        }, "Language saver").start();
    }

    private void loadMessages(final File languageFile) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(languageFile), StandardCharsets.UTF_8))) {
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("#") || line.startsWith("!"))
                    continue;

                try {
                    String[] pair = line.split("=");
                    this.messages.put(pair[0], pair[1]);
                } catch (IndexOutOfBoundsException ignored) { }
            }
        }
    }

    private void saveDefaultLanguages(final File parent) throws IOException {
        this.saveFile("languages/messages.properties", parent);
        this.saveFile("languages/messages_de.properties", parent);
    }

    private void saveFile(final String resourceName, final File parent) throws IOException {
        InputStream input = this.getClass().getClassLoader().getResourceAsStream(resourceName);

        if (input == null) {
            System.out.println("Couldn't open input stream for resource " + resourceName + ".");
            return;
        }

        File outFile = new File(parent, resourceName.substring(resourceName.lastIndexOf('/')));

        if (!outFile.exists()) {
            outFile.getParentFile().mkdirs();
            outFile.createNewFile();
        }

        OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(outFile), StandardCharsets.UTF_8);

        int i;

        while ((i = input.read()) != -1) {
            writer.write(i);
        }

        input.close();
        writer.close();
    }

    public Thread getLoadingThread() {
        return loadingThread;
    }

    public HashMap<String, String> getMessages() {
        return messages;
    }

    public void checkResources() {
        while (this.getLoadingThread().isAlive()) {
            Thread.yield();

            try {
                Thread.sleep(1);
            } catch (InterruptedException ignored) {
            }
        }

        if (this.getMessages() == null) {
            Bukkit.getPluginManager().disablePlugin(Main.getPlugin());
            return;
        }
    }
}
