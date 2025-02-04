package org.mpei.nti.genetic;

import org.mpei.nti.modelCalculation.CostsCalculation;
import org.mpei.nti.substation.substationGeneration.*;
import org.mpei.nti.substation.substationStructures.*;
import org.mpei.nti.utils.RoulettePart;

import java.util.ArrayList;
import java.util.List;

public class Crossing {

    public static List<SubstationMeasures> populationCrossing(List<SubstationMeasures> population) {
        List<SubstationMeasures> newSubstationMeasuresList = new ArrayList<>();
        float fullPopulationWeight = 0.0f;
        for (SubstationMeasures individual : population) {
            fullPopulationWeight += individual.getTotalPrice();
        }
        List<RoulettePart> rouletteWeights = rouletteWeightCalculation(population, fullPopulationWeight);
        for (int i = 0; i < population.size(); i++) {
            SubstationMeasures firstParent = population.get(rouletteIndexChecker(rouletteWeights,
                    Math.random() * rouletteWeights.get(rouletteWeights.size() - 1).getRightPart()));
            SubstationMeasures secondParent = population.get(rouletteIndexChecker(rouletteWeights,
                    Math.random() * rouletteWeights.get(rouletteWeights.size() - 1).getRightPart()));
            if (Math.random() >= 0.5) {
                yearSwap(newSubstationMeasuresList, firstParent, secondParent);
            } else {
                insideYearSwap(newSubstationMeasuresList, firstParent, secondParent);
            }
        }
        return newSubstationMeasuresList;
    }

    public static void yearSwap(List<SubstationMeasures> substationMeasuresList, SubstationMeasures firstParent,
                                SubstationMeasures secondParent) {
        SubstationMeasures child = new SubstationMeasures();
        List<SubstationMeasuresPerYear> substationMeasuresPerYearList = new ArrayList<>();
        int swapYearNumber = (int) (Math.random() * (25 - 1) + 1);
        for (int i = 0; i <= 24; i++) {
            if (i <= swapYearNumber) {
                int architectureType = firstParent.getSubstationMeasuresPerYear().get(i).getArchitectureType();
                OrganizationalMeasures childdOrganizationalMeasures = OrganizationalMeasuresGeneration.organizationalMeasuresGeneration(
                        firstParent.getSubstationMeasuresPerYear().get(i).getOrganizationalMeasures().getD1(),
                        firstParent.getSubstationMeasuresPerYear().get(i).getOrganizationalMeasures().getD6(),
                        firstParent.getSubstationMeasuresPerYear().get(i).getOrganizationalMeasures().getD10(),
                        firstParent.getSubstationMeasuresPerYear().get(i).getOrganizationalMeasures().getD12(),
                        firstParent.getSubstationMeasuresPerYear().get(i).getOrganizationalMeasures().getD16(),
                        firstParent.getSubstationMeasuresPerYear().get(i).getOrganizationalMeasures().getD22());
                ImprosedMeasures childImprosedMeasures = ImprosedMeasuresGeneration.improsedMeasuresGeneration(
                        firstParent.getSubstationMeasuresPerYear().get(i).getImprosedMeasures().getD3(),
                        firstParent.getSubstationMeasuresPerYear().get(i).getImprosedMeasures().getD7(),
                        firstParent.getSubstationMeasuresPerYear().get(i).getImprosedMeasures().getD11(),
                        firstParent.getSubstationMeasuresPerYear().get(i).getImprosedMeasures().getD19(),
                        firstParent.getSubstationMeasuresPerYear().get(i).getImprosedMeasures().getD20(),
                        firstParent.getSubstationMeasuresPerYear().get(i).getImprosedMeasures().getD21(),
                        firstParent.getSubstationMeasuresPerYear().get(i).getImprosedMeasures().getD24()
                );
                List<IED> childIedList = new ArrayList<>();
                for (int j = 0; j < firstParent.getSubstationMeasuresPerYear().get(i).getIedList().size(); j++) {
                    IED childIED = IEDGeneration.iedGeneration(
                            firstParent.getSubstationMeasuresPerYear().get(i).getIedList().get(j).getD2(),
                            firstParent.getSubstationMeasuresPerYear().get(i).getIedList().get(j).getD4(),
                            firstParent.getSubstationMeasuresPerYear().get(i).getIedList().get(j).getD5(),
                            firstParent.getSubstationMeasuresPerYear().get(i).getIedList().get(j).getD8(),
                            firstParent.getSubstationMeasuresPerYear().get(i).getIedList().get(j).getD9(),
                            firstParent.getSubstationMeasuresPerYear().get(i).getIedList().get(j).getD13(),
                            firstParent.getSubstationMeasuresPerYear().get(i).getIedList().get(j).getD14(),
                            firstParent.getSubstationMeasuresPerYear().get(i).getIedList().get(j).getD15(),
                            firstParent.getSubstationMeasuresPerYear().get(i).getIedList().get(j).getD17(),
                            firstParent.getSubstationMeasuresPerYear().get(i).getIedList().get(j).getD18(),
                            firstParent.getSubstationMeasuresPerYear().get(i).getIedList().get(j).getD23());
                    childIedList.add(childIED);
                }
                substationMeasuresPerYearList.add(SubstationMeasuresPerYearGeneration.substationMeasuresGeneration(
                        architectureType, (i + 1), childdOrganizationalMeasures, childImprosedMeasures, childIedList));
            } else {

                int architectureType = secondParent.getSubstationMeasuresPerYear().get(i).getArchitectureType();
                OrganizationalMeasures childdOrganizationalMeasures = OrganizationalMeasuresGeneration.organizationalMeasuresGeneration(
                        secondParent.getSubstationMeasuresPerYear().get(i).getOrganizationalMeasures().getD1(),
                        secondParent.getSubstationMeasuresPerYear().get(i).getOrganizationalMeasures().getD6(),
                        secondParent.getSubstationMeasuresPerYear().get(i).getOrganizationalMeasures().getD10(),
                        secondParent.getSubstationMeasuresPerYear().get(i).getOrganizationalMeasures().getD12(),
                        secondParent.getSubstationMeasuresPerYear().get(i).getOrganizationalMeasures().getD16(),
                        secondParent.getSubstationMeasuresPerYear().get(i).getOrganizationalMeasures().getD22());
                ImprosedMeasures childImprosedMeasures = ImprosedMeasuresGeneration.improsedMeasuresGeneration(
                        secondParent.getSubstationMeasuresPerYear().get(i).getImprosedMeasures().getD3(),
                        secondParent.getSubstationMeasuresPerYear().get(i).getImprosedMeasures().getD7(),
                        secondParent.getSubstationMeasuresPerYear().get(i).getImprosedMeasures().getD11(),
                        secondParent.getSubstationMeasuresPerYear().get(i).getImprosedMeasures().getD19(),
                        secondParent.getSubstationMeasuresPerYear().get(i).getImprosedMeasures().getD20(),
                        secondParent.getSubstationMeasuresPerYear().get(i).getImprosedMeasures().getD21(),
                        secondParent.getSubstationMeasuresPerYear().get(i).getImprosedMeasures().getD24()
                );
                List<IED> childIedList = new ArrayList<>();
                for (int j = 0; j < secondParent.getSubstationMeasuresPerYear().get(i).getIedList().size(); j++) {
                    IED childIED = IEDGeneration.iedGeneration(
                            secondParent.getSubstationMeasuresPerYear().get(i).getIedList().get(j).getD2(),
                            secondParent.getSubstationMeasuresPerYear().get(i).getIedList().get(j).getD4(),
                            secondParent.getSubstationMeasuresPerYear().get(i).getIedList().get(j).getD5(),
                            secondParent.getSubstationMeasuresPerYear().get(i).getIedList().get(j).getD8(),
                            secondParent.getSubstationMeasuresPerYear().get(i).getIedList().get(j).getD9(),
                            secondParent.getSubstationMeasuresPerYear().get(i).getIedList().get(j).getD13(),
                            secondParent.getSubstationMeasuresPerYear().get(i).getIedList().get(j).getD14(),
                            secondParent.getSubstationMeasuresPerYear().get(i).getIedList().get(j).getD15(),
                            secondParent.getSubstationMeasuresPerYear().get(i).getIedList().get(j).getD17(),
                            secondParent.getSubstationMeasuresPerYear().get(i).getIedList().get(j).getD18(),
                            secondParent.getSubstationMeasuresPerYear().get(i).getIedList().get(j).getD23());
                    childIedList.add(childIED);
                }
                substationMeasuresPerYearList.add(SubstationMeasuresPerYearGeneration.substationMeasuresGeneration(
                        architectureType, (i + 1), childdOrganizationalMeasures, childImprosedMeasures, childIedList));
            }
        }
        child.setSubstationMeasuresPerYear(substationMeasuresPerYearList);
        substationMeasuresList.add(child);
    }

    public static void insideYearSwap(List<SubstationMeasures> substationMeasuresList, SubstationMeasures firstParent,
                                      SubstationMeasures secondParent) {
        int swapYearNumber = (int) (Math.random() * (25 - 1) + 1);
        List<SubstationMeasuresPerYear> substationMeasuresPerYearList = new ArrayList<>();
        for (int i = 0; i < 25; i++) {
            int architectureTypeSecond = firstParent.getSubstationMeasuresPerYear().get(i).getArchitectureType();
            OrganizationalMeasures childOrganizationalMeasuresTwo = new OrganizationalMeasures(
                    firstParent.getSubstationMeasuresPerYear().get(i).getOrganizationalMeasures().getD1(),
                    firstParent.getSubstationMeasuresPerYear().get(i).getOrganizationalMeasures().getD6(),
                    firstParent.getSubstationMeasuresPerYear().get(i).getOrganizationalMeasures().getD10(),
                    firstParent.getSubstationMeasuresPerYear().get(i).getOrganizationalMeasures().getD12(),
                    firstParent.getSubstationMeasuresPerYear().get(i).getOrganizationalMeasures().getD16(),
                    firstParent.getSubstationMeasuresPerYear().get(i).getOrganizationalMeasures().getD22());
            ImprosedMeasures childImprosedMeasuresTwo = new ImprosedMeasures(
                    firstParent.getSubstationMeasuresPerYear().get(i).getImprosedMeasures().getD3(),
                    firstParent.getSubstationMeasuresPerYear().get(i).getImprosedMeasures().getD7(),
                    firstParent.getSubstationMeasuresPerYear().get(i).getImprosedMeasures().getD11(),
                    firstParent.getSubstationMeasuresPerYear().get(i).getImprosedMeasures().getD19(),
                    firstParent.getSubstationMeasuresPerYear().get(i).getImprosedMeasures().getD20(),
                    firstParent.getSubstationMeasuresPerYear().get(i).getImprosedMeasures().getD21(),
                    firstParent.getSubstationMeasuresPerYear().get(i).getImprosedMeasures().getD24()
            );
            List<IED> childIedListTwo = new ArrayList<>();
            for (int j = 0; j < firstParent.getSubstationMeasuresPerYear().get(i).getIedList().size(); j++) {
                IED childIED = IEDGeneration.iedGeneration(
                        firstParent.getSubstationMeasuresPerYear().get(i).getIedList().get(j).getD2(),
                        firstParent.getSubstationMeasuresPerYear().get(i).getIedList().get(j).getD4(),
                        firstParent.getSubstationMeasuresPerYear().get(i).getIedList().get(j).getD5(),
                        firstParent.getSubstationMeasuresPerYear().get(i).getIedList().get(j).getD8(),
                        firstParent.getSubstationMeasuresPerYear().get(i).getIedList().get(j).getD9(),
                        firstParent.getSubstationMeasuresPerYear().get(i).getIedList().get(j).getD13(),
                        firstParent.getSubstationMeasuresPerYear().get(i).getIedList().get(j).getD14(),
                        firstParent.getSubstationMeasuresPerYear().get(i).getIedList().get(j).getD15(),
                        firstParent.getSubstationMeasuresPerYear().get(i).getIedList().get(j).getD17(),
                        firstParent.getSubstationMeasuresPerYear().get(i).getIedList().get(j).getD18(),
                        firstParent.getSubstationMeasuresPerYear().get(i).getIedList().get(j).getD23());
                childIedListTwo.add(childIED);}

                SubstationMeasuresPerYear childSubstationMeasuresPerYearTwo = new SubstationMeasuresPerYear(0.0f, 0.0f, 0.0f,
                        architectureTypeSecond, i, childOrganizationalMeasuresTwo, childImprosedMeasuresTwo, childIedListTwo);

                substationMeasuresPerYearList.add(childSubstationMeasuresPerYearTwo);
        }
        SubstationMeasures child = new SubstationMeasures(0.0f, 0.0f, 0.0f,
                substationMeasuresPerYearList);

        double randomNumber = Math.random();
        if (randomNumber < 0.25) {
            int arch = secondParent.getSubstationMeasuresPerYear().get(swapYearNumber - 1).getArchitectureType();
            substationMeasuresPerYearList.get(swapYearNumber - 1).setArchitectureType(arch);
        } else if (randomNumber < 0.5) {
            OrganizationalMeasures childdOrganizationalMeasures = new OrganizationalMeasures(
                    secondParent.getSubstationMeasuresPerYear().get(swapYearNumber - 1).getOrganizationalMeasures().getD1(),
                    secondParent.getSubstationMeasuresPerYear().get(swapYearNumber - 1).getOrganizationalMeasures().getD6(),
                    secondParent.getSubstationMeasuresPerYear().get(swapYearNumber - 1).getOrganizationalMeasures().getD10(),
                    secondParent.getSubstationMeasuresPerYear().get(swapYearNumber - 1).getOrganizationalMeasures().getD12(),
                    secondParent.getSubstationMeasuresPerYear().get(swapYearNumber - 1).getOrganizationalMeasures().getD16(),
                    secondParent.getSubstationMeasuresPerYear().get(swapYearNumber - 1).getOrganizationalMeasures().getD22());
            substationMeasuresPerYearList.get(swapYearNumber).setOrganizationalMeasures(childdOrganizationalMeasures);
        } else if (randomNumber < 0.75) {
            ImprosedMeasures childImprosedMeasures = new ImprosedMeasures(
                    secondParent.getSubstationMeasuresPerYear().get(swapYearNumber - 1).getImprosedMeasures().getD3(),
                    secondParent.getSubstationMeasuresPerYear().get(swapYearNumber - 1).getImprosedMeasures().getD7(),
                    secondParent.getSubstationMeasuresPerYear().get(swapYearNumber - 1).getImprosedMeasures().getD11(),
                    secondParent.getSubstationMeasuresPerYear().get(swapYearNumber - 1).getImprosedMeasures().getD19(),
                    secondParent.getSubstationMeasuresPerYear().get(swapYearNumber - 1).getImprosedMeasures().getD20(),
                    secondParent.getSubstationMeasuresPerYear().get(swapYearNumber - 1).getImprosedMeasures().getD21(),
                    secondParent.getSubstationMeasuresPerYear().get(swapYearNumber - 1).getImprosedMeasures().getD24()
            );
            substationMeasuresPerYearList.get(swapYearNumber - 1).setImprosedMeasures(childImprosedMeasures);
        } else {
            List<IED> childIedList = new ArrayList<>();
            for (int j = 0; j < secondParent.getSubstationMeasuresPerYear().get(swapYearNumber - 1).getIedList().size(); j++) {
                IED childIED = IEDGeneration.iedGeneration(
                        secondParent.getSubstationMeasuresPerYear().get(swapYearNumber - 1).getIedList().get(j).getD2(),
                        secondParent.getSubstationMeasuresPerYear().get(swapYearNumber - 1).getIedList().get(j).getD4(),
                        secondParent.getSubstationMeasuresPerYear().get(swapYearNumber - 1).getIedList().get(j).getD5(),
                        secondParent.getSubstationMeasuresPerYear().get(swapYearNumber - 1).getIedList().get(j).getD8(),
                        secondParent.getSubstationMeasuresPerYear().get(swapYearNumber - 1).getIedList().get(j).getD9(),
                        secondParent.getSubstationMeasuresPerYear().get(swapYearNumber - 1).getIedList().get(j).getD13(),
                        secondParent.getSubstationMeasuresPerYear().get(swapYearNumber - 1).getIedList().get(j).getD14(),
                        secondParent.getSubstationMeasuresPerYear().get(swapYearNumber - 1).getIedList().get(j).getD15(),
                        secondParent.getSubstationMeasuresPerYear().get(swapYearNumber - 1).getIedList().get(j).getD17(),
                        secondParent.getSubstationMeasuresPerYear().get(swapYearNumber - 1).getIedList().get(j).getD18(),
                        secondParent.getSubstationMeasuresPerYear().get(swapYearNumber - 1).getIedList().get(j).getD23());
                childIedList.add(childIED);
            }
            substationMeasuresPerYearList.get(swapYearNumber - 1).setIedList(childIedList);
        }

        for (SubstationMeasuresPerYear substationMeasuresPerYear : substationMeasuresPerYearList) {
            if (substationMeasuresPerYear.getYearNumber() == 1) {
                substationMeasuresPerYear.setCapexPrice(CostsCalculation.buildingCAPEX(substationMeasuresPerYear) +
                        CostsCalculation.exploitationCAPEX(substationMeasuresPerYear));
                substationMeasuresPerYear.setOpexPrice(CostsCalculation.buildingOPEX(substationMeasuresPerYear) +
                        CostsCalculation.exploitationOPEX(substationMeasuresPerYear));
            } else {
                substationMeasuresPerYear.setCapexPrice(CostsCalculation.exploitationCAPEX(substationMeasuresPerYear));
                substationMeasuresPerYear.setOpexPrice(CostsCalculation.exploitationOPEX(substationMeasuresPerYear));
            }
            substationMeasuresPerYear.setTotalPrice(substationMeasuresPerYear.getCapexPrice() +
                    substationMeasuresPerYear.getOpexPrice());
        }
        child.setSubstationMeasuresPerYear(substationMeasuresPerYearList);
        substationMeasuresList.add(child);
    }


    public static Integer rouletteIndexChecker(List<RoulettePart> rouletteWeights, double pointer) {
        int index = 0;
        for (RoulettePart roulettePart : rouletteWeights) {
            if (pointer >= roulettePart.getLeftPart() && pointer <= roulettePart.getRightPart()) {
                index = roulettePart.getIndex();
            }
        }
        return index;
    }

    public static List<RoulettePart> rouletteWeightCalculation(List<SubstationMeasures> population,
                                                               float fullPopulationWeight) {
        List<RoulettePart> rouletteWeightList = new ArrayList<>();
        float partOfPopulationWeight = 0.0f;
        int individualIndex = 0;
        for (SubstationMeasures individual : population) {
            RoulettePart roulettePart = new RoulettePart();
            roulettePart.setLeftPart(partOfPopulationWeight);
            partOfPopulationWeight += fullPopulationWeight / individual.getTotalPrice();
            roulettePart.setRightPart(partOfPopulationWeight);
            roulettePart.setIndex(individualIndex);
            rouletteWeightList.add(roulettePart);
            individualIndex++;
        }
        return rouletteWeightList;
    }

}