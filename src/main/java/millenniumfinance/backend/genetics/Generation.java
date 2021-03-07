package millenniumfinance.backend.genetics;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import millenniumfinance.backend.data.v1.structures.DataTable;
import millenniumfinance.backend.data.v2.structures.GeneticAlgorithmInput;
import millenniumfinance.backend.services.SimulationBot;
import static millenniumfinance.backend.genetics.Genotype.randomizeGenotype;
import static millenniumfinance.backend.genetics.Phenotype.randomizePhenotype;
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
  private Integer winnerIndex;
  
  public static Generation randomizeGeneration(GeneticAlgorithmInput input) {
    GenerationBuilder builder = builder();
    
    List<Phenotype> phenotypes = new ArrayList<>();
    for (int index = 0; index < input.getPopulationSize(); index++) {
      phenotypes.add(randomizePhenotype(input));
    }
    
    builder.phenotypes(phenotypes)
        .winners(new ArrayList<>())
        .populationSize(input.getPopulationSize())
        .winnerCircleSize(input.getWinnerCircleSize())
        .winnerIndex(0);
    
    return builder.build();
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
    phenotypes.sort((a, b) -> {
      double value = subtract(
          maxZeroMeansLess(a.getReport().getUnrealizedGainLoss(), a.getReport().getRealizedGainLoss()), maxZeroMeansLess(b.getReport().getRealizedGainLoss(), b.getReport().getUnrealizedGainLoss())
      ).doubleValue();
      if (value > 0) {
        return -1;
      } else if (value < 0) {
        return 1;
      } else {
        return 0;
      }
    });
  }
  
  public void seedNew(
      List<Phenotype> children,
      GeneticAlgorithmInput input
  ) {
    phenotypes = new ArrayList<>();
    winners = new ArrayList<>();
    winnerCircleSize = input.getWinnerCircleSize();
    populationSize = input.getPopulationSize();
    winnerIndex = 0;
    phenotypes.addAll(children);
    for (int index = 0; index < populationSize - children.size(); index++) {
      Phenotype current = new Phenotype();
      current.setGenotype(randomizeGenotype(input));
      phenotypes.add(current);
    }
  }
}
