package xyl.enigma.mymessage2;

/**
 * Created by 一伦 on 2016/4/12.
 */
public class Order {
    private String proAddress;
    private String proCount;
    private String customerTel;
    private String proID;
    private String orderTime;
    private int courierNo;
    private int proTotalPrice;
    private String deliveryOrderNo;

    public Order(String proID, String proCount, String customerTel, String proAddress) {
        this.proAddress = proAddress;
        this.proCount = proCount;
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

    public String getProCount() {
        return proCount;
    }

    public void setProCount(String proCount) {
        this.proCount = proCount;
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


    public String getDeliveryOrderNo() {
        return deliveryOrderNo;
    }

    public void setDeliveryOrderNo(String deliveryOrderNo) {
        this.deliveryOrderNo = deliveryOrderNo;
    }

    public int getCourierNo() {
        return courierNo;
    }

    public void setCourierNo(int courierNo) {
        this.courierNo = courierNo;
    }

    public int getProTotalPrice() {
        return proTotalPrice;
    }

    public void setProTotalPrice(int proTotalPrice) {
        this.proTotalPrice = proTotalPrice;
    }
}