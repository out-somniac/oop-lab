package simulation;

public class Statistics {

    private final int dayNr;
    private final int nrOfAnimals;
    private final long animalsBorn;
    private final double averageEnergy;
    private final int plantCount;

    public Statistics(int dayNr, int nrOfAnimals, long animalsBorn, double averageEnergy, int plantCount) {
        this.dayNr = dayNr;
        this.nrOfAnimals = nrOfAnimals;
        this.animalsBorn = animalsBorn;

        this.averageEnergy = averageEnergy;
        this.plantCount = plantCount;
    }

    @Override
    public String toString() {
        return """
                ========================
                day: %d overview
                total animals: %d
                animals born: %d
                average energy: %.2f
                total plants: %d
                ========================
                """.formatted(dayNr, nrOfAnimals, animalsBorn, averageEnergy, plantCount);
    }
}
