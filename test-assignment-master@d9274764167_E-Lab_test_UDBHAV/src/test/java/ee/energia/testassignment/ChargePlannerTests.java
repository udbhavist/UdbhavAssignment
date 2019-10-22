package ee.energia.testassignment;

import ee.energia.testassignment.planning.ChargePlan;
import ee.energia.testassignment.price.EnergyPrice;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

@SpringBootTest
public class ChargePlannerTests {

	@Test
	public void chargePlannerReturnsDefinedPlanForDefinedPrices() {
		int batteryLevel = 20;
		final ArrayList<EnergyPrice> energyPrices = getDefinedEnergyPriceSequence();
		final ArrayList<ChargePlan> definedPlan = getExpectedDefinedChargePlan(batteryLevel);

		final ArrayList<ChargePlan> chargePlan = ChargePlanner.calculateChargePlan(batteryLevel, energyPrices);

		Assert.assertEquals(definedPlan, chargePlan);
	}

	public ArrayList<EnergyPrice> getDefinedEnergyPriceSequence() {

		final ArrayList<EnergyPrice> energyPrices = new ArrayList<>();
		energyPrices.add(new EnergyPrice(13, 10, 1, 1, 1, 2019));
		energyPrices.add(new EnergyPrice(10, 9, 2, 1, 1, 2019));
		energyPrices.add(new EnergyPrice(8, 7, 3, 1, 1, 2019));
		energyPrices.add(new EnergyPrice(10, 9, 4, 1, 1, 2019));
		energyPrices.add(new EnergyPrice(8, 7, 5, 1, 1, 2019));
		energyPrices.add(new EnergyPrice(10, 8, 6, 1, 1, 2019));
		energyPrices.add(new EnergyPrice(11, 9, 7, 1, 1, 2019));
		energyPrices.add(new EnergyPrice(15, 13, 8, 1, 1, 2019));

		return energyPrices;
	}

	private ArrayList<ChargePlan> getExpectedDefinedChargePlan (int batteryLevel) {
		final ArrayList<ChargePlan> definedPlan = new ArrayList<>();
		definedPlan.add(new ChargePlan(0, 1, 1, 2019));
		definedPlan.add(new ChargePlan(0, 2, 1, 2019));

		int capacity = Math.min(ChargePlanner.REQUIRED_LEVEL - batteryLevel, ChargePlanner.CHARGER_POWER);
		definedPlan.add(new ChargePlan(capacity, 3, 1, 2019));
		batteryLevel = capacity + batteryLevel;

		definedPlan.add(new ChargePlan(0, 4, 1, 2019));

		capacity = Math.min(ChargePlanner.REQUIRED_LEVEL - batteryLevel, ChargePlanner.CHARGER_POWER);
		definedPlan.add(new ChargePlan(capacity, 5, 1, 2019));
		batteryLevel = capacity + batteryLevel;

		definedPlan.add(new ChargePlan(0, 6, 1, 2019));
		definedPlan.add(new ChargePlan(0, 7, 1, 2019));
		definedPlan.add(new ChargePlan(0, 8, 1, 2019));

		Assert.assertEquals(batteryLevel, ChargePlanner.REQUIRED_LEVEL);

		return definedPlan;
	}

}
