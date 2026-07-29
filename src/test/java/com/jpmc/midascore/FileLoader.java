package com.jpmc.midascore;

import org.springframework.stereotype.Component;
import org.apache.commons.io.IOUtils;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Component
public class FileLoader {
    public String[] loadStrings(String path) {
        // try-with-resources: resource leak se bachata hai aur stream ko automatically close karta hai
        try (InputStream inputStream = this.getClass().getResourceAsStream(path)) {

            // Null check: agar file path galat ho ya file na mile
            if (inputStream == null) {
                return new String[0];
            }

            // Safe UTF-8 conversion
            String fileText = IOUtils.toString(inputStream, StandardCharsets.UTF_8);

            // \\r?\\n Regex: Windows (\r\n) aur Linux/Mac (\n) dono line breaks ko handle karega
            return fileText.split("\\r?\\n");

        } catch (Exception e) {
            // Error aane par crash hone ki jagah empty array return karega
            return new String[0];
        }
    }
}