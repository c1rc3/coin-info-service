# Coin Info Service

Provides coin information

## HTTP Restful API

### Get coin info
Request
```
curl -XGET /coins?symbol=btc,eth
```

Response 
```json
{
  "data": {
    "bitcoin": {
      "id": "bitcoin",
      "name": "Bitcoin",
      "symbol": "BTC",
      "price_usd": 8802.66,
      "price_btc": 1,
      "percent_change1h": 0,
      "percent_change24h": 0,
      "percent_change7d": 0
    },
    "ethereum": {
      "id": "ethereum",
      "name": "Ethereum",
      "symbol": "ETH",
      "price_usd": 678.441,
      "price_btc": 0.0773789,
      "percent_change1h": 0,
      "percent_change24h": 0,
      "percent_change7d": 0
    }
  },
  "code": 1,
  "msg": "OK"
}
```

### Get Coin price histogram
Request
```
curl -XGET /coins/:id/date-histogram
```

Response 
```json
{
  "data": [
    {
      "time": 1520587800000,
      "min": 8630.36,
      "max": 8630.36,
      "avg": 8630.36,
      "count": 1
    },
    {
      "time": 1520589600000,
      "min": 8624.61,
      "max": 8741,
      "avg": 8668.325,
      "count": 4
    },
    {
      "time": 1520591400000,
      "min": 8612,
      "max": 8762.1,
      "avg": 8668.325,
      "count": 10
    },
    {
      "time": 1520593200000,
      "min": 8833.72,
      "max": 8837.55,
      "avg": 8835.634999999998,
      "count": 2
    }
  ],
  "code": 1,
  "msg": "OK"
}
```


