import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.temporal.ChronoUnit;

public class Clock {
    LocalDateTime STARTDATE = LocalDateTime.of(2026, Month.MARCH, 11, 1, 30);
    LocalDateTime currentDate = STARTDATE;
    LocalDateTime endDate;
    
    
    // constructor paramter = the maximum number of days to take the journey
    Clock(int days){
        endDate =  STARTDATE.plus(days, ChronoUnit.DAYS);
        
        
    }
    
    public String getDate(){
        return "" + currentDate.getYear() + "-" + currentDate.getMonthValue() + "-" + currentDate.getDayOfMonth();
    }
    public String getTime() {
        return "" + currentDate.getHour() + ":" + currentDate.getMinute();
    }
    
    
    
    public void passTime(int minutes) {
        currentDate = currentDate.plus(minutes, ChronoUnit.MINUTES);
        
        //call methods for random events HERE inside of passTime.
        
        if (currentDate.isAfter(endDate.minus(1, ChronoUnit.DAYS))) {
            //implement game loss method (likely in main game loop or player class)
            //also implement condition to check for certain time on finalDay
            
            //Split the passtime call into 2 calls, one consisting of the valid passed time pre-loss. This permits valid calculation of random events that may influence
            // time. the 2nd call just makes u lose if the added time is still 
            
            
        }
            
    }
    
}