/*
	Project 6: Yellowstone National Park
	Lodging Reservation System Part 6
	Programmer: Leonard Evans
	Date: 05-01-2008
	Program Name: Project6
*/

//This class decides which special is applicable, calls HikersDelight, FamilyVacation,
//or Reservation class calcAmounts(), and builds the summary info string for retrieval.
//It also verifies that the date supplied by the user is not empty, non-numeric,
//or during the period that the park is closed.

import java.util.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.text.*;

public class MakeReservation
{
    //helper methods
    public static boolean verifyInOutDates(String dateString) throws ParseException
    {
        int dateStringLength = dateString.length();
        if (dateString == null || dateString.equals("") || dateStringLength != 10)
        {
            throw new StringIndexOutOfBoundsException();
        }

        //Not using currently, keep:
        //String dateRegex = "(0[1-9]|1[012]|[1-9])/(0[1-9]|[1-9]|[12][0-9]|3[01])/(19|20)\\d{2}";
        //if (!dateString.matches(dateRegex))

        //convert dateString to an array of characters
        char[] dateChars = dateString.toCharArray();

        //Create an array containing the position numbers of the characters in the dateString that are needed.
        //(the "/" character in positions 2 and 5 is not needed)
        int[] datePositions = {0, 1, 3, 4, 6, 7, 8, 9};
        
        for (int p = 0; p < 8; p++)
        {
            int c = datePositions[p]; //c now represents a character position in dateString
            Character d = dateChars[c]; //d is the actual character in that position
            if (!Character.isDigit(d)) //if d is not a digit, throw exception
            {
                throw new NumberFormatException();
            }
        }

        GregorianCalendar gc = convertToGC(dateString);
        int mo = gc.get(Calendar.MONTH);
        int day = gc.get(Calendar.DAY_OF_MONTH);
        int yr = gc.get(Calendar.YEAR);
        
        if (mo >= 9 || mo <= 1)
        {
            return false;
        }
        else
        {
            return true;
        }

    }

    public static int calcDurationOfStay(String checkInDateString, String checkOutDateString) throws ParseException
    {
        //calculates number of days between check-in and check-out dates
        String dateString;
        dateString = checkInDateString;
        GregorianCalendar gcEarly = convertToGC(dateString);
        dateString = checkOutDateString;
        GregorianCalendar gcLate = convertToGC(dateString);
        return (int) ((gcLate.getTimeInMillis() - gcEarly.getTimeInMillis()) / (24 * 60 * 60 * 1000));
    }

    public static GregorianCalendar convertToGC(String dateString) throws ParseException
    {
        //takes a date string in format mm/dd/yyyy returns a GregorianCalendar object
        DateFormat formatter;
        Date date;
        formatter = new SimpleDateFormat("MM/dd/yyyy");
        date = (Date)formatter.parse(dateString);
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        return cal;
    }

    public static String buildReservation(int numberOfGuests, String checkInDateString, String checkOutDateString, String locationOfStayString) throws ParseException
	{
		//string we will later return
		String reservationInfo;
        int durationOfStay = calcDurationOfStay(checkInDateString, checkOutDateString);
        String reservationInfo2 = "\n\nReservation for " + numberOfGuests + " people for " + durationOfStay + " day(s):\n";

		if (numberOfGuests == 2 && durationOfStay >= 7)
		{
			//reservationInfo = "Your party qualifies for the Hiker's Delight special.\n";
			HikersDelight hd = new HikersDelight(numberOfGuests, durationOfStay, locationOfStayString);
            hd.applyRestriction();
			hd.calcAmounts();
			reservationInfo = hd.getNote() + reservationInfo2 + hd.getReservationInfo();
		}

		else if (numberOfGuests >= 3 && durationOfStay >= 4)
		{
			//reservationInfo = "Your party qualifies for the Family Vacation special.\n";
			FamilyVacation fv = new FamilyVacation(numberOfGuests, durationOfStay, checkOutDateString);
            fv.applyRestriction();
			fv.calcAmounts();
            reservationInfo = fv.getNote() + reservationInfo2 + fv.getReservationInfo();
		}

		else
		{
			Reservation rv = new Reservation(numberOfGuests, durationOfStay);
			rv.calcAmounts();
            reservationInfo = rv.getNote() + reservationInfo2 + rv.getReservationInfo();
		}

		return reservationInfo;
	}

	//method that prepares output string
	public static String buildSummaryInfo(int numberOfGuests, String[] guestNames, String checkInDateString, String checkOutDateString, String locationOfStayString) throws ParseException
	{

        String guestNamesString = "";
        for(int guestNumber = 0; guestNumber < numberOfGuests; guestNumber++)
        {
            guestNamesString += guestNames[guestNumber] + "\n";
        }

        String summaryInfo = "Transaction Summary:\n\n" +
                    buildReservation(numberOfGuests, checkInDateString, checkOutDateString, locationOfStayString) +
                    "\n\nLocation: " + locationOfStayString + "\n\n" +
                    "Checking In On: " + checkInDateString + "\n" +
                    "Checking Out On: " + checkOutDateString + "\n" + "Guests:\n" + guestNamesString;

		return summaryInfo;
	}
}