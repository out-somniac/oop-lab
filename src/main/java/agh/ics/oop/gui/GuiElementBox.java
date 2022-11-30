package agh.ics.oop.gui;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import agh.ics.oop.elements.AbstractEntity;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class GuiElementBox {
    private VBox vBox = new VBox();
    private Label label;

    public GuiElementBox(AbstractEntity entity) {
        try {
            Image image = new Image(new FileInputStream(entity.getImagePath()));
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(20);
            imageView.setFitHeight(20);
            this.label = new Label(entity.getLabelText());
            this.label.setFont(new Font(8));
            this.vBox.getChildren().addAll(imageView, label);
            this.vBox.setAlignment(Pos.CENTER);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("File - " + entity.getImagePath() + " - was not found");
        }
    }

    public VBox getVBox() {
        return this.vBox;
    }
}