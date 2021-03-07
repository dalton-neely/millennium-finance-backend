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
import static millenniumfinance.backend.genetics.Generation.fromPreviousGenChildren;
import static millenniumfinance.backend.genetics.Generation.randomizeGeneration;
import static millenniumfinance.backend.genetics.Phenotype.crossoverPhenotype;
import static millenniumfinance.backend.utilities.BigDecimalHelpers.formatTwoPlaces;
import static millenniumfinance.backend.utilities.BigDecimalHelpers.fromNumber;
import static millenniumfinance.backend.utilities.BigDecimalHelpers.maxZeroMeansLess;
import static millenniumfinance.backend.utilities.BigDecimalHelpers.subtract;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Population {
  private List<Generation> generations;
  private Integer generationSize;
  private Integer generationsRun;
  private DataTable dataTable;
  private SimulationBot bot;
  
  public void runAllGenerations(GeneticAlgorithmInput input) {
    long startTime = System.nanoTime();
    generations = new ArrayList<>();
    generations.add(randomizeGeneration(input));
    for (int index = 0; index < generationSize; index++) {
      int displayIndex = index + 1;
      System.out.println("running generation: " + displayIndex);
      
      Generation current = generations.get(index);
      current.runSimulation(dataTable, bot);
      
      generations.add(fromPreviousGenChildren(crossover(current.getWinners(), input.getWinnerCircleSize()), input));
    }
    long endTime = System.nanoTime();
    System.out.println("finished");
    GainLossReport winner = getTheWinner().getReport();
    BigDecimal totalGainLoss = subtract(winner.getPortfolioMarketValue(), fromNumber(input.getStartingBalance()));
    System.out.println("UGL/RGL: " + maxZeroMeansLess(winner.getUnrealizedGainLoss(), winner.getRealizedGainLoss()));
    System.out.println("Total Gain Loss: " + formatTwoPlaces(totalGainLoss));
    System.out.println("Total Time Run: " + ((endTime - startTime) / 1_000_000_000) + " seconds");
  }
  
  public List<Phenotype> crossover(List<Phenotype> winners, Integer winnerCircleSize) {
    List<Phenotype> children = new ArrayList<>();
    for (int index = 0; index < winnerCircleSize; index += 2) {
      children.add(crossoverPhenotype(winners.get(index), winners.get(index + 1)));
    }
    return children;
  }
  
  public Phenotype getTheWinner() {
    return generations.get(generationSize - 1).getWinners().get(0);
  }
}
