package ee.energia.testassignment.planning;

import java.util.Objects;

public class ChargePlan  implements Comparable<ChargePlan>{

    private final int capacity;
    private final int hour;
    private final int month;
    private final int year;

    public ChargePlan (int capacity, int hour, int month, int year) {
        this.capacity = capacity;
        this.hour = hour;
        this.month = month;
        this.year = year;
    }

    public int getCapacity () {
        return capacity;
    }

    public int getHour () {
        return hour;
    }

    public int getMonth () {
        return month;
    }

    public int getYear () {
        return year;
    }

    @Override
    public boolean equals (Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChargePlan that = (ChargePlan) o;
        return getCapacity() == that.getCapacity() &&
                getHour() == that.getHour() &&
                getMonth() == that.getMonth() &&
                getYear() == that.getYear();
    }

    @Override
    public int hashCode () {
        return Objects.hash(getCapacity(), getHour(), getMonth(), getYear());
    }
    
    //Ovverode compareTo make it sortable
    @Override
	public int compareTo(ChargePlan cp) {
		if(this.hour < cp.hour)
			return -1;
		else if(this.hour > cp.hour)
			return 1;
		else
			return 0;	
	}
}
