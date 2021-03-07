package millenniumfinance.backend.genetics;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import millenniumfinance.backend.data.v1.structures.GainLossReport;
import millenniumfinance.backend.data.v2.structures.BotSimulationInput;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NaturalSelectionOutput {
  private Integer rounds;
  private BigDecimal amountGainLoss;
  private BigDecimal gainLossPercentage;
  private long timeRun;
  private BotSimulationInput winningGenes;
  private GainLossReport winningStats;
}
