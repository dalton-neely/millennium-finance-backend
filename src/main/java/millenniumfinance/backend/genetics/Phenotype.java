package millenniumfinance.backend.genetics;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import millenniumfinance.backend.data.v1.structures.GainLossReport;
import millenniumfinance.backend.data.v2.structures.BotSimulationInput;
import millenniumfinance.backend.data.v2.structures.BotSimulationInput.BotSimulationInputBuilder;
import static millenniumfinance.backend.data.v2.structures.MarketParameters.crossoverMarket;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Phenotype {
  private Genotype genotype;
  private GainLossReport report;
  
  public static Phenotype crossoverPhenotype(Phenotype mother, Phenotype father) {
    BotSimulationInput motherGene = mother.getGenotype().getGenes();
    BotSimulationInput fatherGene = father.getGenotype().getGenes();
    BotSimulationInputBuilder builder = BotSimulationInput.builder();
    
    builder.data(motherGene.getData())
        .starting(motherGene.getStarting())
        .marketIndicators(motherGene.getMarketIndicators())
        .bullMarket(crossoverMarket(motherGene.getBullMarket(), fatherGene.getBullMarket()))
        .bearMarket(crossoverMarket(motherGene.getBearMarket(), motherGene.getBearMarket()));
    
    return new Phenotype(new Genotype(), new GainLossReport());
  }
}
