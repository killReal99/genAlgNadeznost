package org.mpei.nti;

import org.mpei.nti.genetic.*;
import org.mpei.nti.calculation.economicCalculation.ReliabilityCalculation;
import org.mpei.nti.substation.substationGeneration.IEDImpactGeneration;
import org.mpei.nti.substation.substationStructures.*;
import org.mpei.nti.utils.*;
import org.mpei.nti.substation.substationGeneration.BreakersMapGeneration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Main {

    public static int minArch = 1;
    public static int maxArch = 3;
    public static int populationSize = 10;
    public static int numberOfIterations = 10;

    public static void main(String[] args) throws IOException {
        final long startTime = System.currentTimeMillis();
//        GenerateSchem.generateStartSchem();
        List<SchemaStatus> schemaStatusList = ReadSchemStatus.readSchem();
        HashMap<Breaker, Probability> breakersMap = BreakersMapGeneration.generate();
        List<IEDImpact> iedImpactList = IEDImpactGeneration.generate(breakersMap);

        List<SubstationMeasures> population = new ArrayList<>();
        for (int i = 0; i < populationSize; i++) {
            population.add(PopulationGeneration.generatePopulation(minArch, maxArch));
        }
        BoundaryIndividualsAdding.addBoundaryAdding(population, minArch, maxArch);
        Accelerator.quickStart(population);
        OptimizeGenotype.genotypeOptimization(population);
        ReliabilityCalculation.goalFunctionCalculation(population, breakersMap, iedImpactList, schemaStatusList);
        Selection.selectionOfSuitableIndividuals(population);

        float previousValueOfTotalPrice = 0f;
        int priceIterator = 0;

        for (int i = 0; i < numberOfIterations; i++) {
            if (priceIterator != numberOfIterations * 0.1) {
                List<SubstationMeasures> newPopulation = Crossing.populationCrossing(population);
                Mutating.mutatePopulation(newPopulation);
                for (SubstationMeasures substationMeasures : newPopulation) {
                    OptimizeGenotype.architectureOptimization(substationMeasures);
                }
                population.addAll(newPopulation);
                ReliabilityCalculation.goalFunctionCalculation(population, breakersMap, iedImpactList, schemaStatusList);
                Selection.selectionOfSuitableIndividuals(population);
                Sorting.quickSort(population, 0, population.size() - 1);
                Deletion.deletePartOfPopulation(population);

                System.out.println("Номер итерации " + (i + 1));
                System.out.println("Лучший индивид " + population.get(0).hashCode() + " с весовой функцией " +
                        String.format("%f", population.get(0).getTotalPrice()) + " руб");
            } else break;

            if (previousValueOfTotalPrice == population.get(0).getTotalPrice()) {
                priceIterator++;
            } else priceIterator = 0;

            previousValueOfTotalPrice = population.get(0).getTotalPrice();
        }

        ResultsMapping.resultsMapping(population);

        final long endTime = System.currentTimeMillis();
        System.out.println("Общее время выполнения: " + (endTime - startTime) / 1000 + " секунд");
    }
}