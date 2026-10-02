import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Clock {
    LocalDate STARTDATE = LocalDate.of(2026, 6, 30);
    LocalDate currentDate = STARTDATE;
    LocalDate endDate;
    int minute;
            
    
    // constructor paramter = the maximum number of days to take the journey
    
    Clock(int days){
        endDate =  STARTDATE.plus(days, ChronoUnit.DAYS);
        
    }
    
    
    //all significant actions have a minimum time of 1 minute.
    //1440 minutes in 24 hours
    //thus increment current day & subtract 1439 minutes when exceeded.
    
    public void passTime(int minutes) {
        minute += minutes;
        if (minute > 1439) {
            minute -= 1439;
            currentDate.plus(1, ChronoUnit.DAYS);
        }
        
        
        if (currentDate.isAfter(endDate.minus(1, ChronoUnit.DAYS))) {
            //implement game loss method (likely in main game loop or player class)
            //also implement condition to check for certain time on finalDay
            
            //Split the passtime call into 2 calls, one consisting of the valid passed time pre-loss. This permits valid calculation of random events that may influence
            // time. the 2nd call just makes u lose if the added time still exceeds the limit.
            
            
        }

        

        //TODO IN CLOCK!!!!

            //implement randomevents as a subclass! this way it will be VERY easy to have the passTime method interact with it
            //the above game loss implementation. lmao









        
    }
    
}
