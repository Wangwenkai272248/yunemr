package jhmk.clinic.entity.constant;

/**
 * @author liujinlong
 * @date 2018/01/26
 */
public enum  Sex {

    MALE(1),FEMALE(0);

    private Sex(int sex) {
        this.sex = sex;
    }

    private int sex;

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }
}
