package millenniumfinance.backend.genetics;

import java.math.BigDecimal;
import lombok.Data;
import millenniumfinance.backend.clients.BinanceClient;
import millenniumfinance.backend.data.v1.structures.CalculateDataInput;
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
  
  public NaturalSelectionOutput runNaturalSelection(CalculateDataInput calculateDataInput) {
    NaturalSelectionOutput.NaturalSelectionOutputBuilder builder = NaturalSelectionOutput.builder();
    
    Population population = new Population();
    population.setRounds(100);
    population.loadDataInput(fromBinanceApiString(client.getCandlestickData(calculateDataInput)));
    population.loadBot(bot);
//    population.initialGeneration();
    population.runAllGenerations();

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
