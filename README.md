1.) Clone the repo for code base, if code base is not required then dicreclty copy the docker-compose.yml in any directory of your choice.
2.) Run docker-compose up

Run any of the Api,s and check index in Kibana

Currency Exchange service
http://localhost:8000/currency-exchange/from/USD/to/INR

Currency Conversion service
http://localhost:8100/currency-conversion-feign/from/USD/to/INR/quantity/10

Eureka
http://localhost:8761

API Gateway (Below url will work if we do not give the lower case service id property in api gateway)
http://localhost:8765/CURRENCY-EXCHANGE/currency-exchange/from/USD/to/INR

http://localhost:8765/CURRENCY-CONVERSION/currency-conversion/from/USD/to/INR/quantity/10
http://localhost:8765/CURRENCY-CONVERSION/currency-conversion-feign/from/USD/to/INR/quantity/10

API Gateway (Below url will work after above property is made true)
http://localhost:8765/currency-exchange/currency-exchange/from/USD/to/INR

http://localhost:8765/currency-conversion/currency-conversion/from/USD/to/INR/quantity/10
http://localhost:8765/currency-conversion/currency-conversion-feign/from/USD/to/INR/quantity/10

API Gateway (Below URL will work if you have added configuration in api gateway based on routing)
http://localhost:8765/currency-exchange/from/USD/to/INR

http://localhost:8765/currency-conversion/from/USD/to/INR/quantity/10
http://localhost:8765/currency-conversion-feign/from/USD/to/INR/quantity/10
Below will actually redirect to currency-conversion-feign feign only as there is a route configured in gateway
http://localhost:8765/currency-conversion-new/from/USD/to/INR/quantity/10