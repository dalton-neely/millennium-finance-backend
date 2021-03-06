package millenniumfinance.backend.data.v2.structures;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GeneticAlgorithmInput {
  private boolean elitism;
  private double mutationPercentage;
  private Integer generationSize;
  private Integer populationSize;
  private Integer winnerCircleSize;
  private Double startingBalance;
  private RandomizeContext randomizeContext;
  private DataInput dataFetchParameters;
  private boolean seedAlgorithm;
  private BotSimulationInput seed;
}
