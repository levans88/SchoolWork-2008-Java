/*
	Project 6: Yellowstone National Park
	Lodging Reservation System Part 6
	Programmer: Leonard Evans
	Date: 05-01-2008
	Program Name: Project6
*/

//This class inherits Reservation class and overides calcAmounts() to apply a discount.

import java.text.ParseException;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class FamilyVacation extends Reservation
{
	//sub-class instance variables
	private double discountRate = 0.15;
	private String seasonRestriction;
        private String dateString;
        private String checkOutDateString;
	
    //sub-class non-default constructor
	public FamilyVacation(int numberOfGuests, int durationOfStay)
	{
		this.setNumberOfGuests(numberOfGuests);
		this.setDurationOfStay(durationOfStay);
	}

    public FamilyVacation(int numberOfGuests, int durationOfStay, String checkOutDateString)
    {
        this.setNumberOfGuests(numberOfGuests);
        this.setDurationOfStay(durationOfStay);
        this.setCheckOutDateString(checkOutDateString);
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

    public String getSeasonRestriction()
    {
        return seasonRestriction;
    }

    public void setSeasonRestriction (String seasonRestriction)
    {
        this.seasonRestriction = seasonRestriction;
    }

    public String getCheckOutDateString()
    {
        return checkOutDateString;
    }

    public void setCheckOutDateString (String checkOutDateString)
    {
        this.checkOutDateString = checkOutDateString;
    }

    public void applyRestriction() throws ParseException
    {
        dateString = checkOutDateString;
        GregorianCalendar gc = MakeReservation.convertToGC(dateString);
        int mo = gc.get(Calendar.MONTH);

        if (mo == 3 || mo == 4 || mo == 5)
        {
            setDiscountRate(0.15);
            setNote("Good News! You qualify for the Family Vacation special\nand you save 15% off the total.");
            applicable = true;
        }
        else
        {
            setDiscountRate(0);
            setNote("Sorry, the Family Vacation special does not apply\n because your checkout date should fall in the months\n" +
                    "of April, May, or June");
            applicable = false;
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