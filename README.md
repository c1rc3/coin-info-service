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
    "btc": {
      "id": "bitcoin",
      "symbol": "BTC",
      "name": "Bitcoin",
      "price_in_usd": 9845.123,
      "price_in_btc": 1,
      "percent_changed_hour": 0.05,
      "percent_changed_day": 0.08,
      "percent_changed_week": 0.10
    },
    "eth": {
      "id": "ethereum",
      "name": "Ethereum",
      "symbol": "ETH",
      "price_in_usd": 876.1844,
      "price_in_btc": 0.021262,
      "percent_changed_hour": -0.58,
      "percent_changed_day": 6.34,
      "percent_changed_week": 8.59
    }
  },
  "code": 1,
  "msg": "OK"
}
```

### Get Coin price histogram
Request
```
curl -XGET /coins/price/date-histogram?symbol=btc
```

Response 
```json
{
  "data": [
    {
      "time": 1520497939878,
      "min": 123.123,
      "max": 432.234,
      "avg": 123.234
    }
  ],
  "code": 1,
  "msg": "OK"
}
```