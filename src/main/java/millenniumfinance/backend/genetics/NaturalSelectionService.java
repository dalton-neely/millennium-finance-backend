package millenniumfinance.backend.genetics;

import lombok.Data;
import millenniumfinance.backend.clients.BinanceClient;
import millenniumfinance.backend.data.v2.structures.GeneticAlgorithmInput;
import millenniumfinance.backend.services.SimulationBot;
import org.springframework.stereotype.Service;
import static millenniumfinance.backend.data.v1.structures.DataTable.fromBinanceApiString;
import static millenniumfinance.backend.utilities.BigDecimalHelpers.fromNumber;
import static millenniumfinance.backend.utilities.BigDecimalHelpers.maxZeroMeansLess;
import static millenniumfinance.backend.utilities.BigDecimalHelpers.subtract;

@Service
@Data
public class NaturalSelectionService {
  private Population population;
  private BinanceClient client;
  private SimulationBot bot;
  
  public NaturalSelectionService(BinanceClient client, SimulationBot bot) {
    this.client = client;
    this.bot = bot;
  }
  
  public NaturalSelectionOutput runNaturalSelection(GeneticAlgorithmInput input) {
    long startTime = System.nanoTime();
    Population population = Population.builder()
        .generationSize(input.getGenerationSize())
        .dataTable(fromBinanceApiString(client.getCandlestickData(input.getDataFetchParameters())))
        .bot(bot)
        .build();
    
    population.runAllGenerations(input);
    Phenotype winner = population.getTheWinner();
    long endTime = System.nanoTime();
    
    return NaturalSelectionOutput.builder()
        .rounds(input.getGenerationSize())
        .winningStats(winner.getReport())
        .winningGenes(winner.getGenotype().getGenes())
        .timeRun((endTime - startTime) / 1_000_000_000)
        .gainLossPercentage(maxZeroMeansLess(winner.getReport().getRealizedGainLoss(), winner.getReport().getUnrealizedGainLoss()))
        .amountGainLoss(subtract(winner.getReport().getPortfolioMarketValue(), fromNumber(input.getStartingBalance())))
        .build();
  }
}
