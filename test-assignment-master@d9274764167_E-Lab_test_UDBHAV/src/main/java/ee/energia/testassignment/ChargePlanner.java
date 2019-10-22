package ee.energia.testassignment;

import ee.energia.testassignment.planning.ChargePlan;
import ee.energia.testassignment.price.EnergyPrice;

import java.util.ArrayList;

public class ChargePlanner {

    // the capability of the charger to charge certain amount of energy into the battery in 1 hour
    public final static int CHARGER_POWER = 50;

    // maximum battery level possible
    public final static int MAX_LEVEL = 100;

    // battery level required by the end of the charging
    public final static int REQUIRED_LEVEL = 100;

    /**
     * Method calculates the optimal hourly charge plan.
     * Method finds the cheapest hour to charge the battery (if multiple then the earliest)
     * and uses it to charge the battery up to the {@link ChargePlanner#REQUIRED_LEVEL}.
     * If {@link ChargePlanner#CHARGER_POWER} limitation does not allow to do this in one hour,
     * then method finds the next cheapest opportunities and uses them until {@link ChargePlanner#REQUIRED_LEVEL} is met.
     *
     * Method returns the array of {@link ChargePlan} objects that represent the hourly time slot
     * and the capacity that we need to charge during that hour to charge the battery.
     *
     * @param batteryLevel initial battery level when the charger is connected
     * @param energyPrices the list of the energy prices from the moment when charger is connected until the moment when battery needs to be charged
     *                     there is an assumption that battery is connected the first second of the first given hour and disconnected the last second of the last given hour
     * @return
     */
    public static ArrayList<ChargePlan> calculateChargePlan(int batteryLevel, ArrayList<EnergyPrice> energyPrices) {
    	
    	/*This method assumes that energy prices may be random and the battery level is passed as 20
    	 * (as it is defined in chargePlannerReturnsDefinedPlanForDefinedPrices() )*/
    	
        // Sorting the energy prices in increasing order of the hour
    	Collections.sort(energyPrices); 
    	
    	//Defining two hours on which we'll charge the vehicle
    	int hr1=0, hr2=1;
    	int[] prices= new int[energyPrices.size()];
    	/*
    	 * We need to find the minimum sum of the bidding price 
    	 * where the earlier hour comes first
    	 * */
    	HashMap<Integer, EnergyPrice> energyMap = new HashMap<Integer, EnergyPrice>();
    	for(int i=0; i<energyPrices.size(); i++) {
    		energyMap.put(i, energyPrices.get(i));
    		prices[i]= energyPrices.get(i).getBidPrice();
    	}
    	//Assume the minimum sum of prices is the sum of two prices of consecutive hours
    	 int minSum = prices[0]+prices[1];
    	 for(int i= 0;i<prices.length; i++) {
    		 for(int j= i+1;j<prices.length-1; j++) {
    			 if(prices[i]+prices[j] < minSum) {
    				 minSum = prices[i]+prices[j];
    				 hr1=i;
    				 hr2=j; 
    			 }
    		 }
    	 }
    	
    	ArrayList<ChargePlan> chargePlans = new ArrayList<ChargePlan>();
    	
    	int currLvl = batteryLevel;
    	for (Entry<Integer, EnergyPrice> entry : energyMap.entrySet()) {
    		
		    if(entry.getKey() == hr1) {
		    	chargePlans.add(new ChargePlan(CHARGER_POWER, entry.getValue().getHour(),  entry.getValue().getMonth(),  entry.getValue().getYear()));
		    	currLvl = currLvl+CHARGER_POWER;
		    }else if (entry.getKey() == hr2) {
		    	chargePlans.add(new ChargePlan(REQUIRED_LEVEL-currLvl, entry.getValue().getHour(),  entry.getValue().getMonth(),  entry.getValue().getYear()));
		    	currLvl = currLvl+(REQUIRED_LEVEL-currLvl);
		    }else {
		    	chargePlans.add(new ChargePlan(0, entry.getValue().getHour(),  entry.getValue().getMonth(),  entry.getValue().getYear()));
		    }
		}
    	
    	
    	Collections.sort(chargePlans);
    	
        return  chargePlans;
    }
    /**
     * Method calculates the money saved with the optimal chargeplan vs non optimal, classic charge plan
     *
     * @param chrgPlnList List of hourly charge plan
     * @param epList the list of the energy prices from the moment when charger is connected until the moment when battery needs to be charged
     *                     there is an assumption that battery is connected the first second of the first given hour and disconnected the last second of the last given hour
     * @param batteryLevel current battery level of vehicle
     * @return
     */
    public static Integer calculateMoneySaved(ArrayList<ChargePlan> chrgPlnList, ArrayList<EnergyPrice> epList, int batteryLevel) {
    	
    	int currlvl = batteryLevel;
    	int nonOptimalChrg = 0;
    	Collections.sort(epList);
    	
    	HashMap<Integer, Integer> hm = new HashMap<Integer, Integer>();
    	
    	int optimalChrg = 0;
    	ArrayList<Integer> nonZeroChrgHourList = new ArrayList<Integer>();
    	
    	for(ChargePlan cp: chrgPlnList) {
    		if(cp.getCapacity() != 0) {
    			nonZeroChrgHourList.add(cp.getHour());
    		}
    	}
    	
    	for(int i= 0; i<nonZeroChrgHourList.size();i++ ) {
    		for(int j=0; j<epList.size(); j++) {
    			if(nonZeroChrgHourList.get(i)==epList.get(j).getHour()) {
    				optimalChrg = optimalChrg+epList.get(j).getBidPrice();
    			}
    		}
    	}
    	
    	for(EnergyPrice ep : epList) {
    		if(currlvl+CHARGER_POWER < REQUIRED_LEVEL) {
    			currlvl = currlvl+CHARGER_POWER;
    			nonOptimalChrg= nonOptimalChrg+ep.getBidPrice();
    			
    		}else if(currlvl < REQUIRED_LEVEL && currlvl != REQUIRED_LEVEL) {
    			currlvl = currlvl+ (REQUIRED_LEVEL-currlvl);
    			nonOptimalChrg = nonOptimalChrg+ep.getBidPrice();
    		}
    		else {
    			break;
    		}
    		
    	}
    	    	    	    	
    	return nonOptimalChrg-optimalChrg;
    }
}
