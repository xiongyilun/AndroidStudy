package xyl.enigma.mymessage;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import com.google.gson.Gson;

import java.io.DataInputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

public class OrderIntentService extends IntentService {

    private static String BASEURL = "http://192.168.173.1:8888/TestServlet/test";
    private HttpURLConnection conn;
    private int proTotalPrice;
    private Handler handler;
    public static final int SEND_ORDER_SUCCESS = 1;
    public static final int SEND_ORDER_FAIL = 2;
    private static MainActivity mainActivity;

    public static void startIntentService(Context c) {
        mainActivity = (MainActivity) c;
        Intent orderIntent = new Intent(c, OrderIntentService.class);
        c.startService(orderIntent);
    }

    public OrderIntentService() {
        super("OrderIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        handler = mainActivity.getHandler();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String orderTime = sdf.format(new Date());
        MainActivity.order.setOrderTime(orderTime);
        Gson gson = new Gson();
        String str = gson.toJson(mainActivity.order);
        //将数据转换成字节数组
        byte[] data = ("postOrder=" + str).toString().getBytes();
        try {
            Message mes = new Message();
            if (sendPostRequest(BASEURL, data)) {
                mes.what = SEND_ORDER_SUCCESS;
                mes.obj = proTotalPrice;
                handler.sendMessage(mes);
            } else {
                mes.what = SEND_ORDER_FAIL;
                handler.sendMessage(mes);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean sendPostRequest(String url, byte[] data) throws Exception {
        conn = (HttpURLConnection) new URL(url).openConnection();
        conn.setConnectTimeout(8000);
        conn.setReadTimeout(8000);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty("Content-Length", String.valueOf(data.length));
        OutputStream outputStream = conn.getOutputStream();
        outputStream.write(data);
        if (conn.getResponseCode() == 200) {
            DataInputStream inputStream = new DataInputStream(conn.getInputStream());
            proTotalPrice = inputStream.readInt();
            MainActivity.order.setProTotalPrice(String.valueOf(proTotalPrice));
            return true;
        }
        return false;
    }
}
