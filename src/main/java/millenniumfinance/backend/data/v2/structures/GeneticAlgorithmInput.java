package millenniumfinance.backend.data.v2.structures;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import millenniumfinance.backend.data.v1.structures.CalculateDataInput;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GeneticAlgorithmInput {
  private Integer generationSize;
  private Integer populationSize;
  private CalculateDataInput dataFetchParameters;
}
