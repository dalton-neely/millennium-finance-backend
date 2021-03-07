package millenniumfinance.backend.controllers;

import java.util.List;
import millenniumfinance.backend.clients.BinanceClient;
import millenniumfinance.backend.data.v1.structures.CalculateDataInput;
import millenniumfinance.backend.data.v1.structures.DataRow;
import millenniumfinance.backend.data.v1.structures.DataTable;
import millenniumfinance.backend.data.v1.structures.GainLossReport;
import millenniumfinance.backend.data.v2.structures.BotSimulationInput;
import millenniumfinance.backend.data.v2.structures.GeneticAlgorithmInput;
import millenniumfinance.backend.genetics.NaturalSelectionOutput;
import millenniumfinance.backend.genetics.NaturalSelectionService;
import millenniumfinance.backend.services.SimulationBot;
import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import static millenniumfinance.backend.data.v1.structures.DataTable.fromBinanceApiString;
import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.ResponseEntity.ok;

@RestController
public class BackendControllerV1 {
  public static final String DATA = "data";
  public static final String BOT = "bot";
  public static final String RUN = "run";
  public static final String GENETIC = "genetic";
  public static final String CALCULATE = "calculate";
  public static final String API_VERSION = "v1";
  public static final String API_PREFIX = "api";
  
  public static final String DATA_CALCULATE_ENDPOINT = API_PREFIX + "/" + API_VERSION + "/" + DATA + "/" + CALCULATE;
  public static final String BOT_RUN_SIMULATION = API_PREFIX + "/" + API_VERSION + "/" + BOT + "/" + RUN;
  public static final String BOT_RUN_SIMULATION_GENETICALLY = API_PREFIX + "/" + API_VERSION + "/" + BOT + "/" + GENETIC;
  
  private final BinanceClient binanceClient;
  private final SimulationBot simulationBot;
  private final NaturalSelectionService naturalSelectionService;
  private final Logger logger = getLogger(BackendControllerV1.class);
  
  public BackendControllerV1(BinanceClient binanceClient, SimulationBot simulationBot, NaturalSelectionService naturalSelectionService) {
    this.binanceClient = binanceClient;
    this.simulationBot = simulationBot;
    this.naturalSelectionService = naturalSelectionService;
  }
  
  @PostMapping(path = DATA_CALCULATE_ENDPOINT, produces = APPLICATION_JSON_VALUE)
  public ResponseEntity<List<DataRow>> getCalculatedData(
      @RequestBody CalculateDataInput calculateDataInput
  ) {
    logger.debug("Hit endpoint: " + DATA_CALCULATE_ENDPOINT);
    logger.debug("Calculating data with input of: " + calculateDataInput.toString());
    DataTable dataTable = fromBinanceApiString(binanceClient.getCandlestickData(calculateDataInput));
    return ok(dataTable.getDataRows());
  }
  
  @PostMapping(path = BOT_RUN_SIMULATION, produces = APPLICATION_JSON_VALUE)
  public ResponseEntity<GainLossReport> getBotRunSimulation(
      @RequestBody BotSimulationInput input
  ) {
    logger.debug("Hit endpoint: " + BOT_RUN_SIMULATION);
    logger.debug("Running simulation with input of: " + input.toString());
    GainLossReport gainLossReport = simulationBot.runSimulation(
        fromBinanceApiString(binanceClient.getCandlestickData(input.getData())),
        input
    );
    return ok(gainLossReport);
  }
  
  @PostMapping(path = BOT_RUN_SIMULATION_GENETICALLY, produces = APPLICATION_JSON_VALUE)
  public ResponseEntity<NaturalSelectionOutput> runGeneticCode(
      @RequestBody GeneticAlgorithmInput input
  ) {
    return ok(naturalSelectionService.runNaturalSelection(input));
  }
}
