package jhmk.clinic.entity.cdss;

/**
 * @author ziyu.zhou
 * @date 2018/12/6 18:55
 */

public class Date1206 {
    private String doctorId;
    private int currect;
    private int error;
    private int all;
    private float avg;

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(final String doctorId) {
        this.doctorId = doctorId;
    }

    public int getCurrect() {
        return currect;
    }

    public void setCurrect(final int currect) {
        this.currect = currect;
    }

    public int getError() {
        return error;
    }

    public void setError(final int error) {
        this.error = error;
    }

    public int getAll() {
        return all;
    }

    public void setAll(final int all) {
        this.all = all;
    }

    public float getAvg() {
        return avg;
    }

    public void setAvg(final float avg) {
        this.avg = avg;
    }
}
