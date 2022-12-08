package agh.ics.oop.utils;

import java.util.HashMap;
import javafx.scene.image.Image;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class TextureManager {
    private final HashMap<String, Image> images = new HashMap<>();

    public Image getImage(String file_path) {
        if(images.containsKey(file_path)) {
            return images.get(file_path);
        }
        else {
            try {
                Image image = new Image(new FileInputStream(file_path));
                images.put(file_path, image);
                return image;
            } catch (FileNotFoundException e) {
                throw new RuntimeException("File - " + file_path + " - was not found");
            }
        }
    }
}