package millenniumfinance.backend.genetics;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import millenniumfinance.backend.data.v1.structures.GainLossReport;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Phenotype {
  private Genotype genotype;
  private GainLossReport report;
}
