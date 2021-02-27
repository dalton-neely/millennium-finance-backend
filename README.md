# Millennium Finance Backend Server

## How to Run the Server
The server uses `Java` to run so installing `JDK11+` is recommended. From there the build tool `Gradle` will handle 
compiling and running the server. To achieve this run the command.
```bash
./gradlew bootRun
```
This will spin up a server ot [localhost:8080](http://localhost:8080/) that can be contacted via a REST API.

## REST API Endpoints
1. Calculate Financial Indicator Data - POST: `/api/v1/data/calculate`
   
   Input:
   ```json
    {
      "symbol": "BTCUSD",
      "interval": "1m",
      "limit": "10"
    }
    ```
   Output:
   ```json
    [
        {
            "candlestick": {
                "openTime": 1614370620000,
                "openPrice": 46819.3,
                "highestPrice": 46819.3,
                "lowestPrice": 46695.0,
                "closePrice": 46723.71,
                "volume": 1.275255,
                "closeTime": 1614370679999,
                "quoteAssetVolume": 59595.0802,
                "numberOfTrades": 59,
                "takerBuyBaseAssetVolume": 0.113256,
                "takerBuyQuoteAssetVolume": 5293.036
            },
            "index": 0,
            "exponentialMovingAverageShortTerm": 0.0,
            "exponentialMovingAverageLongTerm": 0.0,
            "movingAverageConvergenceDivergence": 0.0,
            "signal": 0.0,
            "longTermMovingAverage": 0.0,
            "movingAverage": 0.0,
            "standardDeviation": 0.0,
            "upperBollingerBand": 0.0,
            "lowerBollingerBand": 0.0,
            "relativeStrengthIndex": 0.0,
            "smoothedLongTermMovingAverage": 0.0,
            "smoothedMovingAverage": 0.0,
            "smoothedStandDeviation": 0.0,
            "smoothedUpperBollingerBand": 0.0,
            "smoothedLowerBollingerBand": 0.0,
            "smoothedRelativeStrengthIndex": 0.0
        }
   ]
   ```
1. Run Bot Simulation - POST: `/api/v1/bot/run`
    
   Input:
   ```json
    {
        "calculateDataInput": {
            "symbol": "BTCUSD",
            "interval": "1m",
            "limit": "1000"
        },
        "whenToDelayABuyAfterALossParameters": {
            "rsiCeiling": 30.00
        },
        "whenToBuyAfterALossParameters": {
            "priceCeilingMultiplier": 1.00,
            "rsiCeiling": 18.00
        },
        "whenToBuyParameters": {
            "rsiCeiling": 50.00,
            "lowerBollingerCeilingMultiplier": 1.00
        },
        "whenToSellForALoss": {
            "floorGainLossPercentage": -0.04
        },
        "whenToSellForLongTrade": {
            "upperBollingerFloorMultiplier": 1.00,
            "priceFloorMultiplier": 1.03,
            "rsiFloor": 75.00
        },
        "whenToSellForShortTrade": {
            "priceFloorMultiplier": 1.035
        }
    }
   ```
   Output:
   ```json
    {
        "amountOfWins": 0,
        "amountOfLosses": 1,
        "unitedStatesDollarTetherBalance": 3125.42811569244,
        "cryptocurrencyBalance": 0.0,
        "lastSecurityPrice": 46406.87,
        "transactionRecords": [
            {
                "positionState": "BUY",
                "timestamp": 1614324959999,
                "securityPrice": 44652.01,
                "unitedStatesDollarTetherBalance": 0.0,
                "cryptocurrencyBalance": 0.0671190389861509,
                "tradeMethod": "BUY_NORMALLY",
                "relativeStrengthIndex": 41.43852010760993
            },
            {
                "positionState": "SELL",
                "timestamp": 1614330659999,
                "securityPrice": 46612.06,
                "unitedStatesDollarTetherBalance": 3125.42811569244,
                "cryptocurrencyBalance": 0.0,
                "tradeMethod": "SELL_FOR_LOSS",
                "relativeStrengthIndex": 60.7738608522014
            }
        ],
        "simulationId": "5b75059a-ef4a-45c8-966b-2e3e9b9aa5e8"
    }
   ```
