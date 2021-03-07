package millenniumfinance.backend.genetics;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import millenniumfinance.backend.data.v1.structures.DataTable;
import millenniumfinance.backend.data.v1.structures.GainLossReport;
import millenniumfinance.backend.data.v2.structures.GeneticAlgorithmInput;
import millenniumfinance.backend.services.SimulationBot;
import static millenniumfinance.backend.genetics.Phenotype.randomizePhenotype;
import static millenniumfinance.backend.utilities.BigDecimalHelpers.isGreaterThanZero;
import static millenniumfinance.backend.utilities.BigDecimalHelpers.isLessThanZero;
import static millenniumfinance.backend.utilities.BigDecimalHelpers.maxZeroMeansLess;
import static millenniumfinance.backend.utilities.BigDecimalHelpers.subtract;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Generation {
  private List<Phenotype> phenotypes;
  private List<Phenotype> winners;
  private Integer populationSize;
  private Integer winnerCircleSize;
  
  public static Generation randomizeGeneration(GeneticAlgorithmInput input) {
    Generation generation = new Generation();
    generation.init(input);
    
    List<Phenotype> phenotypes = new ArrayList<>();
    for (int index = 0; index < input.getPopulationSize(); index++) {
      phenotypes.add(randomizePhenotype(input));
    }
    generation.setPhenotypes(phenotypes);
    
    return generation;
  }
  
  public void init(GeneticAlgorithmInput input) {
    phenotypes = new ArrayList<>();
    winners = new ArrayList<>();
    populationSize = input.getPopulationSize();
    winnerCircleSize = input.getWinnerCircleSize();
  }
  
  public static Generation fromPreviousGenChildren(
      List<Phenotype> children,
      GeneticAlgorithmInput input
  ) {
    Generation generation = new Generation();
    generation.init(input);
    List<Phenotype> phenotypes = generation.getPhenotypes();
    phenotypes.addAll(children);
    for (int index = 0; index < input.getPopulationSize() - children.size(); index++) {
      phenotypes.add(randomizePhenotype(input));
    }
    return generation;
  }
  
  public void runSimulation(DataTable dataTable, SimulationBot simulationBot) {
    for (int index = 0; index < populationSize; index++) {
      Phenotype current = phenotypes.get(index);
      current.setReport(simulationBot.runSimulation(dataTable, current.getGenotype().getGenes()));
    }
    sortWinners();
    winners = phenotypes.subList(0, winnerCircleSize);
  }
  
  public void sortWinners() {
    phenotypes.sort((first, second) -> {
      GainLossReport firstReport = first.getReport();
      GainLossReport secondReport = second.getReport();
      BigDecimal firstUGL = firstReport.getUnrealizedGainLoss();
      BigDecimal firstRGL = firstReport.getRealizedGainLoss();
      BigDecimal secondUGL = secondReport.getUnrealizedGainLoss();
      BigDecimal secondRGL = secondReport.getRealizedGainLoss();
      
      BigDecimal remainder = subtract(maxZeroMeansLess(firstUGL, firstRGL), maxZeroMeansLess(secondUGL, secondRGL));
      
      if (isGreaterThanZero(remainder)) {
        return -1;
      } else if (isLessThanZero(remainder)) {
        return 1;
      } else {
        return 0;
      }
    });
  }
}
