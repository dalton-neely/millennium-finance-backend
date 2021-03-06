package millenniumfinance.backend.data.v1.structures;

import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GainLossReport {
  private Integer wins;
  private Integer losses;
  private BigDecimal winLossRatio;
  private BigDecimal cashBalance;
  private BigDecimal assetBalance;
  private BigDecimal lastAssetPrice;
  private BigDecimal portfolioMarketValue;
  private BigDecimal unrealizedGainLoss;
  private BigDecimal realizedGainLoss;
  private List<TransactionRecord> transactionRecords;
  private String simulationId;
}
