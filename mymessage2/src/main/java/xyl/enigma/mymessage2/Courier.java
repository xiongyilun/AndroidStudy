package xyl.enigma.mymessage2;

/**
 * Created by 一伦 on 2016/4/16.
 */
public class Courier {
    private int courierNo;
    private int count;

    public Courier(int courierNo, int count){
        this.courierNo = courierNo;
        this.count = count;
    }

    public int getCourierNo() {
        return courierNo;
    }

    public void setCourierNo(int courierNo) {
        this.courierNo = courierNo;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "编号:"+courierNo+","+"工作量:"+count+"\n";
    }
}
