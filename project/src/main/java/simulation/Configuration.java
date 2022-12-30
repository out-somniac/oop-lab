package simulation;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javafx.util.Pair;
import simulation.exceptions.InvalidConfiguration;

import java.io.BufferedReader;

public class Configuration {
    private Map<String, Integer> values = new HashMap<String, Integer>();
    public static final String[] requiredKeys = { "width", "height", "plants_initial_total", "plant_energy",
            "plant_growth",
            "animals_initial_total", "starting_energy", "full_energy", "max_energy", "daily_energy_loss",
            "creation_energy", "min_mutations", "max_mutations", "genome_length", "energy_penalty" };

    private boolean isValid() {
        if (this.getWidth() > 100 || this.getHeight() > 100) {
            return false;
        }
        if (this.getInitialAnimalsTotal() > this.getWidth() * this.getHeight()) {
            return false;
        }
        if (this.getInitialPlantsTotal() > this.getWidth() * this.getHeight()) {
            return false;
        }
        if (this.getCreationEnergy() > this.getFullEnergy()) {
            return false;
        }
        return Arrays.stream(Configuration.requiredKeys).map(key -> this.values.get(key) >= 0).reduce(true,
                (left, right) -> left && right);
    }

    public Configuration(String filepath) throws InvalidConfiguration {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filepath));
            String line;
            int lineNo = 1;
            while ((line = reader.readLine()) != null) {
                Pair<String, Integer> pair = parseLine(line, lineNo);
                this.values.put(pair.getKey(), pair.getValue());
                lineNo += 1;
            }
            reader.close();
            if (!hasAllKeys()) {
                throw new InvalidConfiguration(
                        "Configuration file at " + filepath + " doesn't have all required settings.");
            }
            if (!isValid()) {
                throw new InvalidConfiguration(
                        "This configuration has invalid fields");
            }
        } catch (IOException e) {
            throw new InvalidConfiguration("Can't read configuration file: " + filepath, e.getCause());
        }
    }

    public Configuration(String[] fields) throws InvalidConfiguration {
        if (fields.length != this.requiredKeys.length) {
            throw new InvalidConfiguration("Incorrect number of fields in configuration fields in configuration");
        }
        for (int i = 0; i < fields.length; i++) {
            try {
                Integer field_value = Integer.parseInt(fields[i]);
                this.values.put(this.requiredKeys[i], field_value);
            } catch (NumberFormatException e) {
                throw new InvalidConfiguration("Invalid key-value pair in configuration",
                        e.getCause());
            }
        }
        if (!isValid()) {
            throw new InvalidConfiguration(
                    "This  has invalid fields");
        }
    }

    private Pair<String, Integer> parseLine(String line, int lineNo) throws InvalidConfiguration {
        String[] pair = line.split("=");
        if (pair.length != 2) {
            throw new InvalidConfiguration("Invalid configuration in file. Error on line " + Integer.toString(lineNo));
        }
        try {
            String key = pair[0].trim();
            Integer value = Integer.parseInt(pair[1].trim());
            return new Pair<String, Integer>(key, value);
        } catch (NumberFormatException e) {
            throw new InvalidConfiguration("Invalid key-value pair in configuration file on line " + lineNo,
                    e.getCause());
        }
    }

    public void saveToFile(String filepath) throws IOException {
        FileWriter writer = new FileWriter(filepath);
        Arrays.stream(this.requiredKeys).forEach(key -> {
            try {
                writer.write(key + "=" + this.values.get(key) + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        writer.close();
    }

    private boolean hasAllKeys() {
        return Arrays.stream(this.requiredKeys).map(key -> this.values.containsKey(key))
                .reduce(true, (total, element) -> total && element);
    }

    public int getWidth() {
        return this.values.get("width");
    }

    public int getHeight() {
        return this.values.get("height");
    }

    public int getGenomeLength() {
        return this.values.get("genome_length");

    }

    public int getStartingEnergy() {
        return this.values.get("starting_energy");
    }

    public int getEnergyPenalty() {
        return this.values.get("energy_penalty");
    }

    public int getInitialAnimalsTotal() {
        return this.values.get("animals_initial_total");
    }

    public int getInitialPlantsTotal() {
        return this.values.get("plants_initial_total");
    }

    public int getCreationEnergy() {
        return this.values.get("creation_energy");
    }

    public int getPlantGrowth() {
        return this.values.get("plant_growth");
    }

    public int getPlantEnergy() {
        return this.values.get("plant_energy");
    }

    public int getDailyEnergyLoss() {
        return this.values.get("daily_energy_loss");
    }

    public int getMaxEnergy() {
        return this.values.get("max_energy");
    }

    public int getFullEnergy() {
        return this.values.get("full_energy");
    }

    public int getMinMutations() {
        return this.values.get("min_mutations");
    }

    public int getMaxMutations() {
        return this.values.get("max_mutations");
    }

    public String[] getAllFields() {
        return Arrays.stream(this.requiredKeys).map(key -> this.values.get(key)).map(value -> Integer.toString(value))
                .toArray(String[]::new);
    }
}
