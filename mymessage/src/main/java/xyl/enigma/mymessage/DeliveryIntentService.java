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

public class DeliveryIntentService extends IntentService {
    private static MainActivity mainActivity;
    private static String BASEURL = "http://192.168.173.1:8888/TestServlet/delivery";
    private Handler handler;
    private HttpURLConnection conn;
    private String result;
    public static final int SEND_DELIVERY_SUCCESS = 5;
    public static final int SEND_DELIVERY_FAIL = 6;

    public DeliveryIntentService() {
        super("DeliveryIntentService");
    }

    public static void startIntentService(Context c) {
        mainActivity = (MainActivity) c;
        Intent orderIntent = new Intent(c, DeliveryIntentService.class);
        c.startService(orderIntent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        handler = mainActivity.getHandler();
        Gson gson = new Gson();
        String str = gson.toJson(mainActivity.order);
        //将数据转换成字节数组
        byte[] data = ("postDelivery=" + str).toString().getBytes();
        try {
            Message mes = new Message();
            if (sendPostRequest(BASEURL, data)) {
                mes.what = SEND_DELIVERY_SUCCESS;
                mes.obj = result;
                handler.sendMessage(mes);
            } else {
                mes.what = SEND_DELIVERY_FAIL;
                handler.sendMessage(mes);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private boolean sendPostRequest(String url,byte[] data) throws Exception {        // 拼凑出请求地址

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
            result = inputStream.readUTF();
            return true;
        }
        return false;
    }


}