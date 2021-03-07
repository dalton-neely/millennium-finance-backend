package millenniumfinance.backend.genetics;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import millenniumfinance.backend.data.v1.structures.DataTable;
import millenniumfinance.backend.data.v1.structures.GainLossReport;
import millenniumfinance.backend.data.v2.structures.BotSimulationInput;
import millenniumfinance.backend.data.v2.structures.GeneticAlgorithmInput;
import millenniumfinance.backend.services.SimulationBot;
import static millenniumfinance.backend.genetics.Genotype.randomizeGenotype;
import static millenniumfinance.backend.utilities.BigDecimalHelpers.maxZeroMeansLess;
import static millenniumfinance.backend.utilities.BigDecimalHelpers.subtract;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Generation {
  private List<Phenotype> phenotypes = new ArrayList<>();
  private List<Phenotype> winners = new ArrayList<>();
  private Integer populationSize;
  private Integer winnerCircleSize = 20;
  private Integer winnerIndex = 0;
  
  public void randomize(GeneticAlgorithmInput input) {
    for (int index = 0; index < populationSize; index++) {
      Phenotype phenotype = new Phenotype();
      phenotype.setGenotype(randomizeGenotype(input));
      phenotypes.add(phenotype);
    }
  }
  
  public void runSimulation(DataTable dataTable, SimulationBot simulationBot) {
    for (int index = 0; index < populationSize; index++) {
      Phenotype current = phenotypes.get(index);
      BotSimulationInput genes = current.getGenotype().getGenes();
      GainLossReport report = simulationBot.runSimulation(dataTable, genes);
      current.setReport(report);
    }
  }
  
  public List<Phenotype> getWinners() {
    sortWinners();
    winners = phenotypes.subList(0, winnerCircleSize);
    return winners;
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
    phenotypes.addAll(children);
    for (int index = 0; index < populationSize - children.size(); index++) {
      Phenotype current = new Phenotype();
      current.setGenotype(randomizeGenotype(input));
      phenotypes.add(current);
    }
  }
}
