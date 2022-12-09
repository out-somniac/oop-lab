package org.example;

public class Genotype {
    private final int min_gene = 0;
    private final int max_gene = 7;

    private final int[] genes;
    private final int number_of_genes;
    private int gene_index = 0;

    public Genotype(int number_of_genes) {
        this.number_of_genes = number_of_genes;
        this.genes = random_genes(number_of_genes);
    }

    /*
     * Cyclicly moves the gene_index to next gene
     */
    public void next_gene() {
        this.gene_index = (this.gene_index + 1) % this.number_of_genes;
    }

    private int[] random_genes(int number_of_genes) {
        int[] result = new int[number_of_genes];
        for (int i = 0; i < number_of_genes; i++) {
            result[i] = min_gene + (int) (Math.random() * ((max_gene - min_gene) + 1));
        }
        return result;
    }

    /*
     * For a given current animal direction returns the direction in which the
     * animal should move. When called it will NOT advance to the next gene so
     * moving to the next gene has to be handled outside of this class.
     */
    public Direction move_direction(Direction animal_direction) {
        Direction result = animal_direction.rotate(genes[gene_index]);
        return result;
    }
}
