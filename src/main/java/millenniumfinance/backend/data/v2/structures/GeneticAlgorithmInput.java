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
  private Integer generationSize;
  private Integer populationSize;
  private DataInput dataFetchParameters;
}
