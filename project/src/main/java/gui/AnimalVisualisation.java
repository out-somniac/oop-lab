package gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import simulation.Animal;

import java.util.ArrayList;
import java.util.List;

public class AnimalVisualisation extends VBox {
    private final Label titleLabel = new Label("Tracked Animal:");
    private final Label energyLabel = new Label("Energy: _");
    private final Label dayOfBirthLabel = new Label("Day of birth: _");
    private final Label positionLabel = new Label("Position: _");
    private final Label directionLabel = new Label("Direction: _");
    private final Label childrenLabel = new Label("Number of children: _");
    private final Label genotypeLabel = new Label("Genotype: _");

    List<Label> labelList = List.of(titleLabel, positionLabel, directionLabel, energyLabel, dayOfBirthLabel, childrenLabel, genotypeLabel);

    Animal animal;
    AnimalVisualisation() {
        setPadding(new Insets(10));
        setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, null,
                new BorderWidths(1))));
        labelList.forEach(label -> label.setMaxWidth(Double.MAX_VALUE));
        titleLabel.setAlignment(Pos.CENTER);
        getChildren().addAll(labelList);
    }

    public void update() {
        visualizeAnimal(this.animal);
    }

    public void visualizeAnimal(Animal animal) {
        this.animal = animal;
        if (animal == null) {
            energyLabel.setText("Energy: _");
            dayOfBirthLabel.setText("Day of birth: _");
            positionLabel.setText("Position: _");
            directionLabel.setText("Direction: _");
            childrenLabel.setText("Number of children: _");
            genotypeLabel.setText("Genotype: _");
            return;
        }
        energyLabel.setText("Energy: " + animal.energy);
        dayOfBirthLabel.setText("Day of birth: " + animal.dayOfBirth);
        positionLabel.setText("Position: " + animal.getPosition());
        directionLabel.setText("Direction: " + animal.getDirection());
        childrenLabel.setText("Number of children: " + animal.getNrOfChildren());
        genotypeLabel.setText("Genotype: " + animal.getGenotype());



    }

}
