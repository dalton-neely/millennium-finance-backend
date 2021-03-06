package millenniumfinance.backend.data.v2.structures;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DataInput {
  private String symbol;
  private String interval;
  private String limit;
  private Date startDate;
  private Date stopDate;
}
