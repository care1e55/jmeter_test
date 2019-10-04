# jmeter_test
Sample jmeter test using jmeter core library. Put load to local nginx home page

Include sample code:

* Custom listener logging response body
* Factory class creating custom http samplers and returning subtree of a test plan hash tree
* Creating and returning part of a hashtree with backend listener cofigured to send data to local influxdb
* Creating and returning test plan JMX
* Unit test launching test plan

TODO: 
- [ ] Refactor as spring apllication
- [ ] Parameterisig thread counst and timers
- [ ] AbstractFactory class for creating samplers
