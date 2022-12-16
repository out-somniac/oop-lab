package simulation;

import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javafx.util.Pair;
import simulation.exceptions.InvalidConfiguration;

import java.io.BufferedReader;

public class Configuration {
    private Map<String, Integer> values = new HashMap<String, Integer>();
    private final String[] required_keys = { "width", "height", "plants_initial_total", "plant_energy", "plant_growth",
            "animals_initial_total", "starting_energy", "full_energy", "max_energy", "daily_energy_loss",
            "creation_energy", "min_mutations", "max_mutations", "genome_length", "energy_penalty" };

    public Configuration(String filepath) throws InvalidConfiguration {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filepath));
            String line;
            int line_no = 1;
            while ((line = reader.readLine()) != null) {
                Pair<String, Integer> pair = parseLine(line, line_no);
                this.values.put(pair.getKey(), pair.getValue());
                line_no += 1;
            }
            reader.close();
            if (!hasAllKeys()) {
                throw new InvalidConfiguration(
                        "Configuration file at " + filepath + " doesn't have all required settings.");
            }
        } catch (IOException e) {
            throw new InvalidConfiguration("Can't read configuration file: " + filepath, e.getCause());
        }
    }

    private Pair<String, Integer> parseLine(String line, int line_no) throws InvalidConfiguration {
        String[] pair = line.split("=");
        if (pair.length != 2) {
            throw new InvalidConfiguration("Invalid configuration in file. Error on line " + Integer.toString(line_no));
        }
        try {
            String key = pair[0].trim();
            Integer value = Integer.parseInt(pair[1].trim());
            return new Pair<String, Integer>(key, value);
        } catch (NumberFormatException e) {
            throw new InvalidConfiguration("Invalid key-value pair in configuration file on line " + line_no,
                    e.getCause());
        }
    }

    private boolean hasAllKeys() {
        return Arrays.stream(this.required_keys).map(key -> this.values.containsKey(key))
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
}