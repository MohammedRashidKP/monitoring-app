# monitoring-app
1. Monitoring app consists of 2 modules central-service and ware-house service corresponding to the 2 microservices.
2. They are independently runnable, and they talk through a queue using broker RabbitMQ.
3. Multiple instances of warehouse-service can be run with different host and port.
4. Few unit tests are added for central-service.
5. 