package callbs;

import java.io.Serializable;

public class DataPoint implements Serializable {
	private int year;
	private int period;
	private String periodtype;
	private double value;
	
	public DataPoint()
	{
		year = 0;
		period = 0;
		periodtype = "";
		value = 0;
	}
	
	public DataPoint(int inyear, int inperiod, String inperiodtype, double invalue)
	{
		year = inyear;
		period = inperiod;
		periodtype = inperiodtype;
		value = invalue;
	}
	
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getPeriod() {
		return period;
	}
	public void setPeriod(int period) {
		this.period = period;
	}
	public String getPeriodtype() {
		return periodtype;
	}
	public void setPeriodtype(String periodtype) {
		this.periodtype = periodtype;
	}
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}
	
	
}
