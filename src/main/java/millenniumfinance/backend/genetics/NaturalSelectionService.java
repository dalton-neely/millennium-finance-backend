package millenniumfinance.backend.genetics;

import java.math.BigDecimal;
import lombok.Data;
import millenniumfinance.backend.clients.BinanceClient;
import millenniumfinance.backend.data.v2.structures.GeneticAlgorithmInput;
import millenniumfinance.backend.services.SimulationBot;
import millenniumfinance.backend.utilities.BigDecimalHelpers;
import org.springframework.stereotype.Service;
import static millenniumfinance.backend.data.v1.structures.DataTable.fromBinanceApiString;
import static millenniumfinance.backend.utilities.BigDecimalHelpers.fromNumber;
import static millenniumfinance.backend.utilities.BigDecimalHelpers.isEqualTo;

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
    NaturalSelectionOutput.NaturalSelectionOutputBuilder builder = NaturalSelectionOutput.builder();
    
    Population population = new Population();
    population.setGenerationSize(input.getGenerationSize());
    population.loadDataInput(fromBinanceApiString(client.getCandlestickData(input.getDataFetchParameters())));
    population.loadBot(bot);
//    population.initialGeneration();
    population.runAllGenerations(
        input.getPopulationSize(),
        input.getDataFetchParameters(),
        50.00,
        50.00,
        5,
        5,
        6,
        15,
        3000.00
    );

//    population.getGenerations().get(0).runSimulation(dataTable, bot);

//    Genotype genotype = new Genotype();
//    genotype.randomize();

//    System.out.println(population.getGenerations().get(0).getWinners().stream().map(winner -> max(winner.getReport().getRealizedGainLoss(), winner.getReport().getUnrealizedGainLoss())).collect(Collectors.toList()));

//    builder.winningGenes()
//        .winningStats(population.getGenerations().get(0).getReports().get(population.getGenerations().get(0).getWinnerIndex()));
    
    return builder.build();
  }
  
  private BigDecimal max(BigDecimal a, BigDecimal b) {
    if (isEqualTo(a, fromNumber(0))) {
      return b;
    }
    if (isEqualTo(b, fromNumber(0))) {
      return a;
    }
    if (BigDecimalHelpers.isGreaterThan(a, b)) {
      return a;
    } else {
      return b;
    }
  }
}
