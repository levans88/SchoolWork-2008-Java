/*
	Project 6: Yellowstone National Park
	Lodging Reservation System Part 6
	Programmer: Leonard Evans
	Date: 05-01-2008
	Program Name: Project6
*/

//This is the main class. It provides the GUI, gets check-in/out dates, number of guests,
//location of stay, and is responsible for the flow of this program.

import java.text.ParseException;
import javax.swing.*;
import java.util.Date;
import java.text.DateFormat;

public class Driver
{
	//common message pane title
    public static final String paneTitle1 = "Yellowstone Lodging System";
    public static final String paneTitle5 = "Entry Error";

    public static void main(String[] args) throws ParseException
	{
        //local variables
        int datesReversed;
        String summaryString;
        String nextReservation = "N";
        String nowString;
        String locationOfStayString, direction, checkInDateString, checkOutDateString;
        int numberOfGuests;

        //get current date and time
        DateFormat localDate = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT);
        Date n = new Date();
        nowString = localDate.format(n);

        //welcome message
        String welcomeMsg = "Welcome to Yellowstone Lodging System\n\n" + "The current time is: " + nowString +
            "\n\nPlease answer the following questions. If you qualify\n for any specials, it will be applied at the end.";
        JOptionPane.showMessageDialog(null, welcomeMsg, paneTitle1, JOptionPane.INFORMATION_MESSAGE);

        do {
            //calling other methods for user to input location and number of guests
            locationOfStayString = promptFacilityName();
            numberOfGuests = promptNumberOfGuests();
            String[] guestNames = promptGuestNames(numberOfGuests);

            do {
                //setting direction (checking in or checking out), and retrieving the appropriate dateString
                direction = "in";
                checkInDateString = promptInOutDates(direction);
                direction = "out";
                checkOutDateString = promptInOutDates(direction);
                
                //check if dates are reversed
                datesReversed = MakeReservation.calcDurationOfStay(checkInDateString, checkOutDateString);
                
                if (datesReversed < 1)
                {
                    String paneTitle4 = "Date Error";
                    String datesReversedMsg = "The check-out date is before the check-in date,\nplease click \"OK\" to start over again.";
                    JOptionPane.showMessageDialog(null, datesReversedMsg, paneTitle4, JOptionPane.ERROR_MESSAGE);
                }

                } while (datesReversed < 1);

            //create summary String
            summaryString = MakeReservation.buildSummaryInfo(numberOfGuests, guestNames, checkInDateString, checkOutDateString, locationOfStayString);

            //display results
            JOptionPane.showMessageDialog(null, summaryString, paneTitle1, JOptionPane.INFORMATION_MESSAGE);

            //Ask if there is another reservation. If not, end this loop.
            String nextReservationMsg = "Do you want to enter another reservation? (Y=Yes,N=No)";
            nextReservation = JOptionPane.showInputDialog(null, nextReservationMsg, paneTitle1, JOptionPane.QUESTION_MESSAGE);
           } while (!nextReservation.equalsIgnoreCase("N"));
        }

        //new methods
        public static String promptFacilityName()
        {
            String locStayEntryErrorMsg = "You must enter the name of a lodging facility.";
            String locationOfStayMsg = "Which lodging facility will you stay at?";
            String locStayString;
            do {
                locStayString = JOptionPane.showInputDialog(null, locationOfStayMsg, paneTitle1, JOptionPane.QUESTION_MESSAGE);
                
                if(locStayString == null || locStayString.equals(""))
                {
                    JOptionPane.showMessageDialog(null, locStayEntryErrorMsg, paneTitle5, JOptionPane.ERROR_MESSAGE);
                }
            } while(locStayString == null || locStayString.equals(""));
            
            return locStayString;
        }

        public static int promptNumberOfGuests()
        {
            String numGuestsBlankErrMsg = "You must enter the number of guests.";
            String numGuestsNonNumErrMsg = "The number of guests must be entered in numeric characters.";
            String numberOfGuestsMsg = "How many guests will stay with us?";
            String numberOfGuestsString;
            int numGuests = 0;
            boolean validNumGuests = true;

            do {
                numberOfGuestsString = JOptionPane.showInputDialog(null, numberOfGuestsMsg, paneTitle1, JOptionPane.QUESTION_MESSAGE);
                
                if(numberOfGuestsString == null || numberOfGuestsString.equals(""))
                {
                    JOptionPane.showMessageDialog(null, numGuestsBlankErrMsg, paneTitle5, JOptionPane.ERROR_MESSAGE);
                }
                else
                {
                    //parse number of guests to Int before passing
                    try
                    {
                        numGuests = Integer.parseInt(numberOfGuestsString);
                        validNumGuests = true;
                    }
                    catch (NumberFormatException nfe)
                    {
                        validNumGuests = false;
                        JOptionPane.showMessageDialog(null, numGuestsNonNumErrMsg, paneTitle5, JOptionPane.ERROR_MESSAGE);
                    }
                }
            } while(numberOfGuestsString == null || numberOfGuestsString.equals("") || validNumGuests == false);
            
            return numGuests;
        }

        public static String[] promptGuestNames(int numberOfGuests)
        {
            String[] guestNames = new String[numberOfGuests];

            int guestNumber = 0;
            String guestNameErrMsg = "You must enter the name of the guest.";
            
            do {
                String guestNameMsg = "Please enter name of guest #" + (guestNumber + 1);
                guestNames[guestNumber] = JOptionPane.showInputDialog(null, guestNameMsg, paneTitle1, JOptionPane.QUESTION_MESSAGE);
                
                if(guestNames[guestNumber] == null || guestNames[guestNumber].equals(""))
                {
                    JOptionPane.showMessageDialog(null, guestNameErrMsg, paneTitle5, JOptionPane.ERROR_MESSAGE);
                }
                else
                {
                    guestNumber += 1;
                }
            } while(guestNumber < numberOfGuests);
            
            return guestNames;
        }

        public static String promptInOutDates(String direction) throws ParseException
        {
            String paneTitle2 = "Check-" + direction + " Date";
            String paneTitle6 = "Check-" + direction + " Date Error";
            String checkInOutDateMsg = "What day will you check " + direction + " (mm/dd/yyyy)?";
            String invalidCheckInOutDateMsg = "The park is closed from October to February.\n" +
                        "Please enter a date between March and September.\n\nWhat day will you check " + direction + " (mm/dd/yyyy)?";
            String genBadDateErrMsg = "Incomplete date or wrong date format.\nPlease enter date in mm/dd/yyyy format.";
            String nonNumDateErrMsg = "Non-numeric characters in date entry.\nPlease enter date in mm/dd/yyyy format.";

            boolean validDate = true;
            int repeat = 0;
            String dateString;

            //prompt for check in/out date
            do {
                do {
                    if (validDate == false)
                    {
                        dateString = JOptionPane.showInputDialog(null, invalidCheckInOutDateMsg, paneTitle2, JOptionPane.QUESTION_MESSAGE);
                    }
                    else
                    {
                        dateString = JOptionPane.showInputDialog(null, checkInOutDateMsg, paneTitle2, JOptionPane.QUESTION_MESSAGE);
                    }

                    try
                    {
                        validDate = MakeReservation.verifyInOutDates(dateString);
                        repeat = 0;
                    }

                    //incase dateString is empty or wrong length
                    catch(StringIndexOutOfBoundsException siobe)
                    {
                        JOptionPane.showMessageDialog(null, genBadDateErrMsg, paneTitle6, JOptionPane.ERROR_MESSAGE);
                        repeat = 1;
                        //reprompt
                    }

                    //incase dateString is not numbers (except for "/")
                    catch(NumberFormatException nfe)
                    {
                        JOptionPane.showMessageDialog(null, nonNumDateErrMsg, paneTitle6, JOptionPane.ERROR_MESSAGE);
                        repeat = 1;
                    }
                } while (repeat == 1);
            } while (validDate == false);
            
            return dateString;
        }
}