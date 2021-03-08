package millenniumfinance.backend.genetics;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import millenniumfinance.backend.data.v1.structures.GainLossReport;
import millenniumfinance.backend.data.v2.structures.BotSimulationInput;
import millenniumfinance.backend.data.v2.structures.BotSimulationInput.BotSimulationInputBuilder;
import millenniumfinance.backend.data.v2.structures.GeneticAlgorithmInput;
import static millenniumfinance.backend.data.v2.structures.MarketParameters.crossoverMarket;
import static millenniumfinance.backend.genetics.Genotype.randomizeGenotype;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Phenotype {
  private Genotype genotype;
  private GainLossReport report;
  
  public static Phenotype fromSeed(GeneticAlgorithmInput input) {
    return new Phenotype(Genotype.fromSeed(input), new GainLossReport());
  }
  
  public static Phenotype randomizePhenotype(GeneticAlgorithmInput input) {
    return new Phenotype(randomizeGenotype(input), new GainLossReport());
  }
  
  public static Phenotype crossoverPhenotype(Phenotype mother, Phenotype father) {
    BotSimulationInput motherGene = mother.getGenotype().getGenes();
    BotSimulationInput fatherGene = father.getGenotype().getGenes();
    BotSimulationInputBuilder builder = BotSimulationInput.builder();
    
    builder.data(motherGene.getData())
        .starting(motherGene.getStarting())
        .marketIndicators(motherGene.getMarketIndicators())
        .bullMarket(crossoverMarket(motherGene.getBullMarket(), fatherGene.getBullMarket()))
        .bearMarket(crossoverMarket(motherGene.getBearMarket(), motherGene.getBearMarket()));
    
    return new Phenotype(new Genotype(builder.build()), new GainLossReport());
  }
}
