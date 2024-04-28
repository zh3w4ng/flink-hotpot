package io.diablogato.flink.hotpot.test.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FixtureLoader {
    public static String load(String fixtureName) {
        try {
            String path = FixtureLoader.class.getClassLoader().getResource(fixtureName).getPath();
            String jsonString = Files.readString(Paths.get(path));
            return jsonString;
        } catch (IOException e) {
            return "";
        }
    }
}
