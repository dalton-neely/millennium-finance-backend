package millenniumfinance.backend.genetics;

import lombok.Data;
import millenniumfinance.backend.clients.BinanceClient;
import millenniumfinance.backend.data.v2.structures.GeneticAlgorithmInput;
import millenniumfinance.backend.services.SimulationBot;
import org.springframework.stereotype.Service;
import static millenniumfinance.backend.data.v1.structures.DataTable.fromBinanceApiString;

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
    Population population = Population.builder()
        .generationSize(input.getGenerationSize())
        .dataTable(fromBinanceApiString(client.getCandlestickData(input.getDataFetchParameters())))
        .bot(bot)
        .build();
    
    population.runAllGenerations(input);
    
    return new NaturalSelectionOutput();
  }
}
