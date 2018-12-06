package jhmk.clinic.entity.cdss;

/**
 * @author ziyu.zhou
 * @date 2018/12/6 18:55
 */

public class Date1206 {
    private String doctorId;
    private float currect;
    private float error;
    private float all;
    private float avg;

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(final String doctorId) {
        this.doctorId = doctorId;
    }

    public float getCurrect() {
        return currect;
    }

    public void setCurrect(final float currect) {
        this.currect = currect;
    }

    public float getError() {
        return error;
    }

    public void setError(final float error) {
        this.error = error;
    }

    public float getAll() {
        return all;
    }

    public void setAll(final float all) {
        this.all = all;
    }

    public float getAvg() {
        return avg;
    }

    public void setAvg(final float avg) {
        this.avg = avg;
    }
}
