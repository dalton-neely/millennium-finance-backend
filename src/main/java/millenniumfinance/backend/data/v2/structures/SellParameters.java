package millenniumfinance.backend.data.v2.structures;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import static millenniumfinance.backend.genetics.Randomizers.OFF_GENE;
import static millenniumfinance.backend.genetics.Randomizers.activeRandomizer;
import static millenniumfinance.backend.genetics.Randomizers.amountRandomizer;
import static millenniumfinance.backend.genetics.Randomizers.chooseRandom;
import static millenniumfinance.backend.genetics.Randomizers.percentageRandomizer;
import static millenniumfinance.backend.genetics.Randomizers.rsiRandomizer;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SellParameters {
  private Parameter<BigDecimal> rsiFloor;
  private Parameter<BigDecimal> percentageOfUpperBollingerBand;
  private Parameter<BigDecimal> percentageGain;
  private Parameter<BigDecimal> amountAboveCostBasis;
  private Parameter<BigDecimal> targetAmount;
  
  public static SellParameters randomizeSell(GeneticAlgorithmInput input) {
    RandomizeContext context = input.getRandomizeContext();
    RsiWindow rsiWindow = context.getSellRsi();
    PercentageWindow bollinger = context.getSellBollingerPercentage();
    PercentageWindow gain = context.getSellGainPercentage();
    SellParametersBuilder builder = builder();
    Parameter<BigDecimal> amountAboveCostBasis = new Parameter<>(amountRandomizer(context.getMaxAmountAboveCostBasis()), activeRandomizer());
    Parameter<BigDecimal> percentageGain = new Parameter<>(percentageRandomizer(gain.getMin(), gain.getMax()), activeRandomizer());
    Parameter<BigDecimal> rsiFloor = new Parameter<>(rsiRandomizer(rsiWindow.getMin(), rsiWindow.getMax()), activeRandomizer());
    Parameter<BigDecimal> percentageOfUpperBollingerBand = new Parameter<>(percentageRandomizer(bollinger.getMin(), bollinger.getMax()), activeRandomizer());
    
    builder.amountAboveCostBasis(amountAboveCostBasis)
        .percentageGain(percentageGain)
        .rsiFloor(rsiFloor)
        .percentageOfUpperBollingerBand(percentageOfUpperBollingerBand)
        .targetAmount(OFF_GENE);
    
    return builder.build();
  }
  
  public static SellParameters crossoverSell(SellParameters mother, SellParameters father) {
    SellParametersBuilder builder = builder();
    
    builder.amountAboveCostBasis(chooseRandom(mother.getAmountAboveCostBasis(), father.getAmountAboveCostBasis()))
        .percentageGain(chooseRandom(mother.getPercentageGain(), father.getPercentageGain()))
        .rsiFloor(chooseRandom(mother.getRsiFloor(), father.getRsiFloor()))
        .percentageOfUpperBollingerBand(
            chooseRandom(mother.getPercentageOfUpperBollingerBand(), father.getPercentageOfUpperBollingerBand())
        )
        .targetAmount(chooseRandom(mother.getTargetAmount(), father.getTargetAmount()));
    
    return builder.build();
  }
}
