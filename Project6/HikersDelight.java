/*
	Project 6: Yellowstone National Park
	Lodging Reservation System Part 6
	Programmer: Leonard Evans
	Date: 05-01-2008
	Program Name: Project6
*/

//This class inherits Reservation class and overides calcAmounts() to apply a discount.

public class HikersDelight extends Reservation implements Restrictable
{
	//sub-class instance variables
	private double discountRate = 0.10;
	private String locationOfStayString;

	//sub-class non-default constructor
	public HikersDelight(int numberOfGuests, int durationOfStay)
	{
		this.setNumberOfGuests(numberOfGuests);
		this.setDurationOfStay(durationOfStay);
	}

    //sub-class additional non-default constructor
    public HikersDelight(int numberOfGuests, int durationOfStay, String locationOfStayString)
    {
        this.setNumberOfGuests(numberOfGuests);
        this.setDurationOfStay(durationOfStay);
        this.setLocationOfStayString(locationOfStayString);
    }

	//accessor methods
    public double getDiscountRate()
    {
        return discountRate;
    }

    public void setDiscountRate (double discountRate)
    {
        this.discountRate = discountRate;
    }

    public String getLocationOfStayString()
    {
        return locationOfStayString;
    }

    public void setLocationOfStayString (String locationOfStayString)
    {
        this.locationOfStayString = locationOfStayString;
    }

    //implement restrictable interface
    public void applyRestriction()
    {
        if (locationOfStayString.toUpperCase().contains("CABIN"))
        {
            setDiscountRate(0);
            setNote("Hikers' Delight special is not applicable\nbecause you are staying at one of the cabins.");
            applicable = false;
        }
        else
        {
            setDiscountRate(0.10);
            setNote("Good news! You qualify for the Hiker's Delight special\nand you save 10% off the total");
            applicable = true;
        }
    }
    
    //instance method - override the calcAmounts() method in Reservation Class
    @Override
    public void calcAmounts()
    {
        this.setTotal(this.getDurationOfStay() * calcAmountsCommon());
        this.setDiscount(this.getTotal()*discountRate);
        this.setNet(this.getTotal() - this.getDiscount());
    }
}