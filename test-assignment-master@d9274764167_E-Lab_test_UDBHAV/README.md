# Test Assignment

There is a class `ChargePlanner` that should calculate the optimal charging schedule for charging the electrical vehicle. The electrical vehicle charging schedule is considered to be optimal when:
- vehicle is charged when the energy market price is the lowest (that results into the lowest charging expenses)
- vehicle is charged to the required level as early as possible but still taking the previous point into the account

Energy prices are published before-hand and we will assume they are defined per-hour only. 

There is the basic test `chargePlannerReturnsDefinedPlanForDefinedPrices` implemented and failing at the moment. Test is testing strictly defined pre-calculated test case. 

The defined test calculation is provided in the table below.

| | 1:00 | 2:00 | 3:00 | 4:00 | 5:00 | 6:00 | 7:00| 8:00 | 9:00
--- | --- | --- | --- | --- | --- | --- | --- | --- | --- 
Buying Price |13|10|8|10|8|10|11|15|Car Ready
Selling Price|10|9|7|9|7|8|9|13 
Battery Level|20|20|20|70|70|80|80|80|80
Capacity| | |50| |10|


The assignment is to implement the method that will work for given pre-calculated test case, and that will also work for any randomly generated energy price sequences.

Please make `chargePlannerReturnsDefinedPlanForDefinedPrices` test pass to make sure that the method implementation is really working for pre-calculated test case.

Please implement the unit tests that will make sure that this method is really working for any randomly generated energy price sequences, too.

Additionally, you could implement the method that will calculate the amount of saved money that the `ChargePlanner` generates comparing with classical way to charge when the changing would start straight away when connected to the charger, and will charge until charged to maximum.

