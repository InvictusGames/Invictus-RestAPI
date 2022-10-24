package cc.invictusgames.invictusresapi.util.configuration;

import cc.invictusgames.invictusresapi.InvictusRestAPI;
import com.google.gson.Gson;
import lombok.Setter;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * @author Emilxyz (langgezockt@gmail.com)
 * 12.02.2020 / 21:05
 * iLib / cc.invictusgames.ilib.configuration
 */

public class JsonConfigurationService implements ConfigurationService {

    @Override
    public void saveConfiguration(StaticConfiguration configuration, File file) {
        try {
            FileOutputStream outputStream = new FileOutputStream(file);
            OutputStreamWriter writer = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);
            InvictusRestAPI.GSON.toJson(configuration, writer);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            System.err.println("Failed to save configuration " + configuration.getClass().getName() + " to file " + file.getName());
        }
    }

    @Override
    public <T extends StaticConfiguration> T loadConfiguration(Class<? extends T> clazz, File file) {
        if ((!file.getParentFile().exists()) && (!file.getParentFile().mkdir())) {
            System.err.println("Failed to create parent folder for " + file.getName());
            return null;
        }
        try {
            T config = clazz.newInstance();
            if (!file.exists()) {
                if (!file.createNewFile()) {
                    System.err.println("Failed to create file for " + file.getName());
                    return null;
                }
                saveConfiguration(config, file);
            }
        } catch (InstantiationException | IllegalAccessException | IOException e) {
            e.printStackTrace();
        }

        try {
            T config = InvictusRestAPI.GSON.fromJson(new BufferedReader(new FileReader(file)), clazz);
            saveConfiguration(config, file);
            return config;
        } catch (FileNotFoundException e) {
            System.err.println("Failed to load configuration " + clazz.getName() + " from file " + file.getName());
            return null;
        }
    }
}
