package millenniumfinance.backend.services;

import java.util.List;
import millenniumfinance.backend.data.v1.structures.BotState;
import millenniumfinance.backend.data.v1.structures.DataRow;
import millenniumfinance.backend.data.v1.structures.DataTable;
import millenniumfinance.backend.data.v1.structures.GainLossReport;
import millenniumfinance.backend.data.v2.structures.BotSimulationInput;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import static millenniumfinance.backend.utilities.BigDecimalHelpers.fromNumber;
import static org.slf4j.LoggerFactory.getLogger;

@Service
public final class SimulationBot {
  // Start at 224 because you need the 15 minute lower bollinger band to start buying
  public static final int PERIOD_START_OFFSET = 224;
  public static final double BINANCE_TRANSACTION_FEE_BITCOIN = 1 - 0.001;
  private final Logger logger = getLogger(SimulationBot.class);
  
  public GainLossReport runSimulation(DataTable dataTable, BotSimulationInput input) {
    List<DataRow> dataRows = dataTable.getDataRows();
    BotState state = new BotState();
    state.setInput(input);
    state.setTransactionFee(fromNumber(BINANCE_TRANSACTION_FEE_BITCOIN));
    state.reset();
    
    for (DataRow dataRow : dataRows) {
      state.consume(dataRow);
      
      if (state.isConditionsForBuy()) {
        state.buy();
      } else if (state.isConditionsForStopLoss()) {
        state.stopLoss();
      } else if (state.isConditionsForSell()) {
        state.sell();
      }
    }
    
    return state.generateGainLossReport();
  }
}
