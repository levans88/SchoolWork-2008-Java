/*
	Project 6: Yellowstone National Park
	Lodging Reservation System Part 6
	Programmer: Leonard Evans
	Date: 05-01-2008
	Program Name: Project6
*/

//This class determines normalNightlyRate before using any class's calcAmounts(),
//supplies summary data to MakeReservation class, and is super class
//for FamilyVacation and HikersDelight.
import java.text.NumberFormat;

public class Reservation
{
	//instance variables
	private int numberOfGuests;
	private int durationOfStay;
	private double total;
	private double discount;
  private double discountRate = 0.0;
	private double net;
	private String note;
	private String sourceRestriction;
  boolean applicable;
  private double normalNightlyRate;
	
	//static variables
	public static final double REGULAR_RATE = 100.00f;

	//constructor
	public Reservation()
	{
		numberOfGuests = 0;
		durationOfStay = 0;
		total = 0.0;
		discount = 0.0;
    net = 0.0;
		//note = "";
		sourceRestriction = "";
    normalNightlyRate = 0.0;

	}
  
  //overload custructor
  public Reservation(int numberOfGuests, int durationOfStay)
  {
      this.numberOfGuests = numberOfGuests;
      this.durationOfStay = durationOfStay;
      note = "We are sorry, but we couldn't find any\napplicable specials for you.";
  }

	//accessor methods (getters and setters for each private variable)
	public int getNumberOfGuests()
	{
		return numberOfGuests;
	}

	public void setNumberOfGuests (int numberOfGuests)
	{
		this.numberOfGuests = numberOfGuests;
	}

	public int getDurationOfStay()
	{
		return durationOfStay;
	}

	public void setDurationOfStay (int durationOfStay)
	{
		this.durationOfStay = durationOfStay;
	}

	public double getTotal()
	{
		return total;
	}

	public void setTotal(double total)
	{
		this.total = total;
	}

	public double getDiscount()
	{
		return discount;
	}

	public void setDiscount(double discount)
	{
		this.discount = discount;
	}

	public double getNet()
	{
		return net;
	}

	public void setNet(double net)
	{
		this.net = net;
	}

	public String getNote()
	{
		return note;
	}

	public void setNote (String note)
	{
		this.note = note;
	}

	public String getSourceRestriction()
	{
		return sourceRestriction;
	}

	public void setSourceRestriction (String sourceRestriction)
	{
		this.sourceRestriction = sourceRestriction;
	}

  public double getNormalNightlyRate()
	{
		return normalNightlyRate;
	}

	public void setNormalNightlyRate (double normalNightlyRate)
	{
		this.normalNightlyRate = normalNightlyRate;
	}

	//instance methods
	public double calcAmountsCommon()
	{
		if (numberOfGuests%2 == 0/2 && numberOfGuests > 2)
		{
			normalNightlyRate = ((numberOfGuests/2) * 100);
		}
		else if (numberOfGuests%2 != 0/2 && numberOfGuests > 2)
		{
			normalNightlyRate = (((numberOfGuests + 1)/2)*100);
		}
    else
    {
        normalNightlyRate = REGULAR_RATE;
    }
    return normalNightlyRate;
  }

  public void calcAmounts()
  {
    total = durationOfStay * calcAmountsCommon();
		discount = 0;
    net = total - discount;
	}

	public String getReservationInfo()
	{
		NumberFormat currencyFormat;
		String totalString, discountString, netString;
		String infoString;

		currencyFormat = NumberFormat.getCurrencyInstance();

		totalString = currencyFormat.format(total);
		discountString = currencyFormat.format(discount);
		netString = currencyFormat.format(net);

		infoString = "Total: " + totalString + "\nDiscount: " + discountString +
                "\nNet Due: " + netString;

    return infoString;
	}
}