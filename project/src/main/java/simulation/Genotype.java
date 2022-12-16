package simulation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Genotype {
    public static final int MAX_GENE_VALUE = Direction.size - 1;
    public static final int MIN_GENE_VALUE = 0;

    private final ArrayList<Integer> genes;
    private final int genesCount;
    private Iterator<Integer> iter;
    private Integer currentGene;

    public Genotype(int genesCount) {
        this.genesCount = genesCount;
        this.genes = getRandomGenes(genesCount);
        this.iter = genes.iterator();
        this.currentGene = iter.next();
    }

    public void advanceGene() {
        if (iter.hasNext()) {
            currentGene = iter.next();
        } else {
            iter = genes.iterator();
            currentGene = iter.next();
        }
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
}
