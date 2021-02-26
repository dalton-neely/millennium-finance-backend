package millenniumfinance.backend.controllers;

import millenniumfinance.backend.clients.BinanceClient;
import millenniumfinance.backend.data.v1.structures.BotSimulationInput;
import millenniumfinance.backend.data.v1.structures.DataRow;
import millenniumfinance.backend.data.v1.structures.DataTable;
import millenniumfinance.backend.data.v1.structures.GainLossReport;
import millenniumfinance.backend.services.SimulationBot;
import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static millenniumfinance.backend.Constants.*;
import static millenniumfinance.backend.data.v1.structures.DataTable.fromBinanceApiString;
import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.ResponseEntity.ok;

@RestController
public class BackendControllerV1 {
    public static final String DATA = "data";
    public static final String BOT = "bot";
    public static final String RUN = "run";
    public static final String CALCULATE = "calculate";
    public static final String API_VERSION = "v1";
    public static final String API_PREFIX = "api";

    public static final String DATA_CALCULATE_ENDPOINT = API_PREFIX + "/" + API_VERSION + "/" + DATA + "/" + CALCULATE;
    public static final String BOT_RUN_SIMULATION = API_PREFIX + "/" + API_VERSION + "/" + BOT + "/" + RUN;

    private final BinanceClient binanceClient;
    private final SimulationBot simulationBot;
    private final Logger logger = getLogger(BackendControllerV1.class);

    public BackendControllerV1(BinanceClient binanceClient, SimulationBot simulationBot) {
        this.binanceClient = binanceClient;
        this.simulationBot = simulationBot;
    }

    @GetMapping(path = DATA_CALCULATE_ENDPOINT, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<DataRow>> getCalculatedData(
            @RequestParam(defaultValue = SYMBOL_BITCOIN) String symbol,
            @RequestParam(defaultValue = INTERVAL_ONE_MINUTE) String interval,
            @RequestParam(defaultValue = LIMIT_1) String limit
    ) {
        logger.debug("Hit endpoint: " + DATA_CALCULATE_ENDPOINT);
        DataTable dataTable = fromBinanceApiString(binanceClient.getCandlestickData(symbol, interval, limit));
        return ok(dataTable.getDataRows());
    }

    @PostMapping(path = BOT_RUN_SIMULATION, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<GainLossReport> getBotRunSimulation(
            @RequestBody BotSimulationInput botSimulationInput
    ) {
        logger.debug("Hit endpoint: " + BOT_RUN_SIMULATION);
        logger.debug("Running simulation with input of: " + botSimulationInput.toString());
        GainLossReport gainLossReport = simulationBot.runSimulation(
                fromBinanceApiString(binanceClient.getCandlestickData(
                        botSimulationInput.getSymbol(),
                        botSimulationInput.getInterval(),
                        botSimulationInput.getLimit())
                ),
                botSimulationInput
        );
        return ok(gainLossReport);
    }
}
