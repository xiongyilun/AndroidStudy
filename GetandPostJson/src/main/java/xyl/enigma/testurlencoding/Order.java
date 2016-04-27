package xyl.enigma.testurlencoding;

/**
 * Created by 一伦 on 2016/4/12.
 */
public class Order {
    private String proAddress;
    private String proConut;
    private String customerTel;
    private String proID;
    private String orderTime;
    private String courierTel;

    public Order(String proID, String proConut, String customerTel, String proAddress) {
        this.proAddress = proAddress;
        this.proConut = proConut;
        this.customerTel = customerTel;
        this.proID = proID;
    }

    public Order() {
    }

    public String getProAddress() {
        return proAddress;
    }

    public void setProAddress(String proAddress) {
        this.proAddress = proAddress;
    }

    public String getProConut() {
        return proConut;
    }

    public void setProConut(String proConut) {
        this.proConut = proConut;
    }

    public String getCustomerTel() {
        return customerTel;
    }

    public void setCustomerTel(String customerTel) {
        this.customerTel = customerTel;
    }

    public String getProID() {
        return proID;
    }

    public void setProID(String proID) {
        this.proID = proID;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getCourierTel() {
        return courierTel;
    }

    public void setCourierTel(String courierTel) {
        this.courierTel = courierTel;
    }
}
