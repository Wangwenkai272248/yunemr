package jhmk.clinic.entity.bean;

/**
 * @author ziyu.zhou
 * @date 2019/1/7 17:27
 * 既往史疾病名个
 */

public class Disease {

    private String disease_name;
    private String treatment;
    private String duration_of_illness;
    private String duration_of_illness_unit;

    public String getDisease_name() {
        return disease_name;
    }

    public void setDisease_name(final String disease_name) {
        this.disease_name = disease_name;
    }

    public String getTreatment() {
        return treatment;
    }

    public void setTreatment(final String treatment) {
        this.treatment = treatment;
    }

    public String getDuration_of_illness() {
        return duration_of_illness;
    }

    public void setDuration_of_illness(final String duration_of_illness) {
        this.duration_of_illness = duration_of_illness;
    }

    public String getDuration_of_illness_unit() {
        return duration_of_illness_unit;
    }

    public void setDuration_of_illness_unit(final String duration_of_illness_unit) {
        this.duration_of_illness_unit = duration_of_illness_unit;
    }
}
