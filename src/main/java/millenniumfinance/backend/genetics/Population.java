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
import static millenniumfinance.backend.genetics.Generation.randomizeGeneration;
import static millenniumfinance.backend.genetics.Phenotype.crossoverPhenotype;

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
    generations = new ArrayList<>();
    generations.add(randomizeGeneration(input));
    for (int index = 0; index < generationSize - 1; index++) {
      System.out.println("running generation: " + index);
      Generation current = generations.get(index);
      current.runSimulation(dataTable, bot);
      List<Phenotype> winners = current.getWinners();
      List<Phenotype> children = crossover(winners);
      Generation nextGeneration = new Generation();
      nextGeneration.setPopulationSize(input.getPopulationSize());
      nextGeneration.seedNew(children, input);
      generations.add(nextGeneration);
    }
    System.out.println("finished");
    System.out.println(generations.get(generations.size() - 2).getWinners().get(0).getReport().toString());
  }
  
  public List<Phenotype> crossover(List<Phenotype> winners) {
    List<Phenotype> children = new ArrayList<>();
    int size = 20;
    for (int index = 0; index < size; index += 2) {
      Phenotype current = winners.get(index);
      Phenotype next = winners.get(index + 1);
      children.add(crossoverPhenotype(current, next));
    }
    
    return children;
  }
}
