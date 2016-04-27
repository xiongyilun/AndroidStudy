package xyl.enigma.mymessage2;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.net.HttpURLConnection;

/**
 * Created by 一伦 on 2016/4/12.
 */
public class AccountIntentService extends IntentService {
    private static MainActivity mainActivity;
    private static String BASEURL = "http://192.168.23.1:8888/TestServlet/account";
    private Handler handler;
    private HttpURLConnection conn;
    private int courierNo;
    private String courierTel;
    public static final int SEND_ACCOUNT_SUCCESS = 3;
    public static final int SEND_ACCOUNT_FAIL = 4;

    public AccountIntentService() {
        super("AccountIntentService");
    }

    public static void startIntentService(Context c) {
        mainActivity = (MainActivity) c;
        Intent orderIntent = new Intent(c, AccountIntentService.class);
        c.startService(orderIntent);
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        handler = mainActivity.getHandler();
        Gson gson = new Gson();
        String str = gson.toJson(mainActivity.order);
        //将数据转换成字节数组
        byte[] data = ("postAccount=" + str).toString().getBytes();
        try {
            Message mes = new Message();
            if (sendPostRequest(BASEURL, data)) {
                mes.what = SEND_ACCOUNT_SUCCESS;
                mes.obj = courierTel;
                handler.sendMessage(mes);
            } else {
                mes.what = SEND_ACCOUNT_FAIL;
                handler.sendMessage(mes);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private boolean sendPostRequest(String BASEURL, byte[] data) throws Exception {
        String str = HttpUtils.postJsonContent(BASEURL, data);
        if (str.equals("")) {
            return false;
        }
        JSONObject jo = new JSONObject(str);
        courierTel = jo.getString("courierTel");
        courierNo = jo.getInt("courierNo");
        MainActivity.order.setCourierNo(courierNo);
        return true;

    }
}
