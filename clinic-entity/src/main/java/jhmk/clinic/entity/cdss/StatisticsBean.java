package jhmk.clinic.entity.cdss;

/**
 * @author ziyu.zhou
 * @date 2018/9/11 21:02
 */

public class StatisticsBean {
    private String illName;
    private int count;
    private int day;
    private int avgDay;

    public String getIllName() {
        return illName;
    }

    public void setIllName(String illName) {
        this.illName = illName;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getAvgDay() {
        return this.day/this.getCount();
    }

    public void setAvgDay(int avgDay) {
        this.avgDay = avgDay;
    }
}
