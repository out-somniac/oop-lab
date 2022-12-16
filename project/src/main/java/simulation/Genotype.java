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
    private final int genes_count;
    private Iterator<Integer> iter;
    private Integer current_gene;

    public Genotype(int genes_count) {
        this.genes_count = genes_count;
        this.genes = getRandomGenes(genes_count);
        this.iter = genes.iterator();
        this.current_gene = iter.next();
    }

    public void advance_gene() {
        if (iter.hasNext()) {
            current_gene = iter.next();
        } else {
            iter = genes.iterator();
            current_gene = iter.next();
        }
    }

    private int randomGene() {
        return MIN_GENE_VALUE + (int) (Math.random() * ((MAX_GENE_VALUE - MIN_GENE_VALUE) + 1));
    }

    private ArrayList<Integer> getRandomGenes(int genes_count) {
        return IntStream.range(0, genes_count).map(i -> randomGene())
                .boxed()
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public int getRotation() {
        return current_gene;
    }
}
