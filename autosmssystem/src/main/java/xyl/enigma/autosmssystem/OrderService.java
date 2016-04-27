package xyl.enigma.autosmssystem;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;

import java.net.HttpURLConnection;

public class OrderService extends Service {

    private static String url = "http://192.168.23.1:8888/TestServlet/test";
    private HttpURLConnection conn;
    private int proTotalPrice;
    private Handler handler;
    public static final int SEND_ORDER_SUCCESS = 1;
    public static final int SEND_ORDER_FAIL = 2;
    private static MainActivity mainActivity;


    public static void startIntentService(Context c, String[] split) {
        mainActivity = (MainActivity) c;
        Intent orderIntent = new Intent(c, OrderService.class);
        orderIntent.putExtra("message", split);
        c.startService(orderIntent);
    }

    public OrderService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
