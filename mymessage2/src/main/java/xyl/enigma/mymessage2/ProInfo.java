package xyl.enigma.mymessage2;

/**
 * Created by 一伦 on 2016/4/14.
 */
public class ProInfo {
    private int proID;
    private int proPrice;
    private int courierNo;

    public ProInfo(int proID, int proPrice, int courierNo) {
        this.proID = proID;
        this.proPrice = proPrice;
        this.courierNo = courierNo;
    }

    public ProInfo() {
    }

    public int getProID() {
        return proID;
    }

    public void setProID(int proID) {
        this.proID = proID;
    }

    public int getProPrice() {
        return proPrice;
    }

    public void setProPrice(int proPrice) {
        this.proPrice = proPrice;
    }

    public int getCourierNo() {
        return courierNo;
    }

    public void setCourierNo(int courierNo) {
        this.courierNo = courierNo;
    }
}
