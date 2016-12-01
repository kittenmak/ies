package ies;

/**
 * Created by michaelleung on 1/12/2016.
 */
public class KioskItem {
    String device;
    String curFloor;
    String destFloor;
    String time;

    public void setDevice(String device) {
        this.device = device;
    }

    public void setCurFloor(String curFloor) {
        this.curFloor = curFloor;
    }

    public void setDestFloor(String destFloor) {
        this.destFloor = destFloor;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public KioskItem(){
    }

    public String getDevice() {
        return device;
    }

    public String getCurFloor() {
        return curFloor;
    }

    public String getDestFloor() {
        return destFloor;
    }

    public String getTime() {
        return time;
    }
}
