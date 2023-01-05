package simulation;

public record Statistics(int dayNr, int nrOfAnimals, long animalsBorn, double averageEnergy, int plantCount, double lifespanOfDead) {

    @Override
    public String toString() {
        return """
                day: %d overview
                total animals: %d
                animals born: %d
                average energy: %.2f
                total plants: %d
                average lifespan of death animals: %.2f
                """.formatted(dayNr, nrOfAnimals, animalsBorn, averageEnergy, plantCount, lifespanOfDead);
    }

    public String getCSVFormat() {
        return "%d; %d; %d; %.2f; %d; %.2f".formatted(dayNr, nrOfAnimals, animalsBorn, averageEnergy, plantCount, lifespanOfDead);
    }
}
