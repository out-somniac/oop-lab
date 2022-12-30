package simulation;

public record Statistics(int dayNr, int nrOfAnimals, long animalsBorn, double averageEnergy, int plantCount) {

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
