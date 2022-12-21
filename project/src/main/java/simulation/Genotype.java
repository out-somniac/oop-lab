package simulation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Genotype {
    public static final int MAX_GENE_VALUE = Direction.size - 1;
    public static final int MIN_GENE_VALUE = 0;

    private final List<Integer> genes;
    private Iterator<Integer> iter;
    private Integer currentGene;

    public Genotype(int genesCount) {
        this.genes = getRandomGenes(genesCount);
        this.iter = genes.iterator();
        this.currentGene = iter.next();
    }

    private Genotype(List<Integer> geneList) {
        this.genes = geneList;
        this.iter = genes.iterator();
        this.currentGene = iter.next();
    }

    public void advanceGene() {
        if (!iter.hasNext()) {
            iter = genes.iterator();
        }
        currentGene = iter.next();
    }

    private int randomGene() {
        return MIN_GENE_VALUE + (int) (Math.random() * ((MAX_GENE_VALUE - MIN_GENE_VALUE) + 1));
    }

    private ArrayList<Integer> getRandomGenes(int genesCount) {
        return IntStream.range(0, genesCount).map(i -> randomGene())
                .boxed()
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public int getRotation() {
        return currentGene;
    }

    public void mutateGenes(int nrOfMutations, int maxDifference) {
        int n = this.genes.size();
        for (int i = 0; i < nrOfMutations; i++) {
            int k = ThreadLocalRandom.current().nextInt(0, this.genes.size());
            this.genes.set(k, (this.genes.get(k) + n +
                    ThreadLocalRandom.current().nextInt(-maxDifference, maxDifference + 1)) % n);
        }
    }


    /**
     * Function that creates new genotype from two other with ratio that tells
     * what percentage of the new genome should come from each.
     * It takes genes from right from gen1 and genes from left gen2
     * @param gen1 first Genotype
     * @param gen2 second Genotype
     * @param firstToSecondRatio ratio of first genome to the second one
     * @return new Genotype
     */
    static Genotype mixGenotypes(Genotype gen1, Genotype gen2, double firstToSecondRatio) {
        double total = firstToSecondRatio + 1f;
        int nrOfGenes1 = (int) Math.floor(gen1.genes.size() * (firstToSecondRatio / total));
        int nrOfGenes2 = (int) Math.ceil(gen2.genes.size() * (1 - firstToSecondRatio / total));
        List<Integer> geneList = new ArrayList<>(gen1.genes.subList(0, nrOfGenes1));
        geneList.addAll(gen2.genes.subList(gen2.genes.size() - nrOfGenes2, gen2.genes.size()));
        return new Genotype(geneList);
    }
}
