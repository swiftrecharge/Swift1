package sixtus.com.homenavigation;

/**
 * Created by markzeno on 5/30/17.
 */

public class Meter {
    public  int meterId;
    public int meter_number;
    public double current_units;

    Meter(int meterId,int meter_number,double current_units){
        this.meterId=meterId;
        this.meter_number= meter_number;
        this.current_units=current_units;

    }

    public int getMeter_number() {
        return meter_number;
    }

    public double getCurrent_credit() {
        return current_units;
    }


    public int getMeterId() {
        return meterId;
    }
}
