# jmeter_test
Sample jmeter test using jmeter core library. Put load to local nginx home page

Include sample code:

- [x] Custom listener logging response body
- [x] Factory class creating custom http samplers and returning subtree of a test plan hash tree
- [x] Creating and returning part of a hashtree with backend listener cofigured to send data to local influxdb
- [x] Creating and returning test plan JMX
- [x] Unit test launching test plan

TODO: 
- [ ] Refactor as spring apllication
- [ ] Parameterisig thread counst and timers
- [ ] AbstractFactory class for creating samplers
