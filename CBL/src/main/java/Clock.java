import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.temporal.ChronoUnit;

public class Clock {
    LocalDateTime STARTDATE = LocalDateTime.of(2026, Month.MARCH, 11, 1, 30);
    LocalDateTime currentDate = STARTDATE;
    LocalDateTime endDate;
    RandomEvents rndEventsReference;
    Player playerReference;
    Renderer renderReference;
    
    
    // constructor paramter = the maximum number of days to take the journey
    Clock(int days, Player player, RandomEvents rndEvents, Renderer r) {
        renderReference = r;
        endDate =  STARTDATE.plus(days, ChronoUnit.DAYS);
        rndEventsReference = rndEvents;
        playerReference = player;
        renderReference.addToLog("The date / time is " + getDate() + " | " + getTime() + ". Your finals are on " + + endDate.getYear() + "-" + endDate.getMonthValue() + "-" + endDate.getDayOfMonth() + " | " + endDate.getHour() + ":" + endDate.getMinute());
        renderReference.addToLog("You have mysteriously woken up fairly far away! you must return to eindhoven by that date, or you fail!");
    }
    
    public String getDate() {
        return "" + currentDate.getYear() + "-" + currentDate.getMonthValue() + "-" + currentDate.getDayOfMonth();
    }
    
    public String getTime() {
        return "" + currentDate.getHour() + ":" + currentDate.getMinute();
    }
    
    
    
    public void passTime(int minutes) {
        currentDate = currentDate.plus(minutes, ChronoUnit.MINUTES);
        
        //call methods for random events HERE inside of passTime.
        if (minutes > 0) {
            rndEventsReference.DoEvent(playerReference, this);
        }
        
        if (currentDate.isAfter(endDate.minus(1, ChronoUnit.DAYS))) {
            renderReference.loseGame("You lose! you could not make it to Eindhoven in time, and you failed your finals by absence!");
            
            
        }
            
    }
    
}