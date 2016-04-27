package xyl.enigma.mymessage2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;


public class MainActivity extends AppCompatActivity {

    private TextView telephone;
    private DrawerLayout mDrawerLayout;
    private TextView message;
    private IntentFilter receiveFilter;
    private MessageReceiver messageReceiver;
    public static Order order;
    private String str = "";
    private static final String URL = "http://192.168.23.1:8888/TestServlet/getTotal";
    private static final String URL2 = "http://192.168.23.1:8888/TestServlet/workCount";
    public static final int To = 7;
    public static final int TOTAL_PRICE_SUCCESS = 9;
    public static final int TOTAL_PRICE_FAIL = 10;
    public static final int TOTAL_WORK_SUCCESS = 11;
    public static final int TOTAL_WORK_FAIL = 12;
    private int result;
    private StringBuilder sb;
    String address;
    public Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case OrderIntentService.SEND_ORDER_SUCCESS:
                    SmsManager orderManager = SmsManager.getDefault();
                    orderManager.sendTextMessage(address, null, "[MYSMS][回复]银行账号:6217907500000403879.商品金额:" + String.valueOf(msg.obj) + ".", null, null);
                    break;
                case OrderIntentService.SEND_ORDER_FAIL:
                    Toast.makeText(MainActivity.this, "订货失败", Toast.LENGTH_SHORT).show();
                    break;
                case AccountIntentService.SEND_ACCOUNT_SUCCESS:
                    SmsManager accountManager = SmsManager.getDefault();
                    String courierTel = (String) msg.obj;
                    accountManager.sendTextMessage(courierTel, null, str, null, null);
                case AccountIntentService.SEND_ACCOUNT_FAIL:
                    break;
                case DeliveryIntentService.SEND_DELIVERY_SUCCESS:
                    Toast.makeText(MainActivity.this, (String) msg.obj, Toast.LENGTH_SHORT).show();
                    break;
                case DeliveryIntentService.SEND_DELIVERY_FAIL:
                    break;
                case MainActivity.TOTAL_PRICE_SUCCESS:
                    Snackbar.make(telephone, "                                  总收入：" + msg.obj.toString(), Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    break;
                case MainActivity.TOTAL_PRICE_FAIL:
                    break;
                case MainActivity.TOTAL_WORK_SUCCESS:
                    Toast.makeText(MainActivity.this, (String) msg.obj, Toast.LENGTH_SHORT).show();
                    break;
                case MainActivity.TOTAL_WORK_FAIL:
                    break;
                default:
                    break;
            }
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_layout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.mipmap.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);

        telephone = (TextView) findViewById(R.id.telephone);
        message = (TextView) findViewById(R.id.message);


        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        receiveFilter = new IntentFilter();
        receiveFilter.addAction("android.provider.Telephony.SMS_RECEIVED");
        messageReceiver = new MessageReceiver();
        registerReceiver(messageReceiver, receiveFilter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop() {
        unregisterReceiver(messageReceiver);
        super.onStop();
    }

    public Handler getHandler() {
        return this.handler;
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.proTotalPrice:
                                getTotalPrice();
                                break;
                            case R.id.courierTotalWork:
                                getcourierTotalWorkCount();
                                break;
                            case R.id.add_proInfo:
                                addproinfo();
                                break;
                            default:
                                break;

                        }
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });
    }

    private void getcourierTotalWorkCount() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message mes = new Message();
                if (requestTotalWork(URL2)) {
                    mes.what = TOTAL_WORK_SUCCESS;
                    mes.obj = sb.toString();
                    handler.sendMessage(mes);
                } else {
                    mes.what = TOTAL_WORK_FAIL;
                    handler.sendMessage(mes);
                }
            }
        }).start();
    }

    private boolean requestTotalWork(String url) {
        String str = HttpUtils.getStringJsonContent(url);
        if (str.equals("")) {
            return false;
        }
        Gson gson = new Gson();
        List<Courier> ps = gson.fromJson(str, new TypeToken<List<Courier>>(){}.getType());
        sb = new StringBuilder();
        for(int i = 0; i < ps.size() ; i++)
        {
            Courier p = ps.get(i);
            sb.append(p.toString());
        }
        return true;
    }

    private void getTotalPrice() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message mes = new Message();
                if (requestTotalPrice(URL)) {
                    mes.what = TOTAL_PRICE_SUCCESS;
                    mes.obj = result;
                    handler.sendMessage(mes);
                } else {
                    mes.what = TOTAL_PRICE_FAIL;
                    handler.sendMessage(mes);
                }
            }
        }).start();
    }

    private boolean requestTotalPrice(String BASEURL) {
        int i = HttpUtils.getJsonContent(BASEURL);
        if (i == -1) {
            return false;
        }
        result = i;
        return true;
    }

    private void addproinfo() {
        Intent intent = new Intent(MainActivity.this, AddProInfoActivity.class);
        startActivity(intent);
    }

    class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            Object[] pdus = (Object[]) bundle.get("pdus"); // 提取短信消息
            String format = (String) bundle.get("format");
            SmsMessage[] messages = new SmsMessage[pdus.length];
            for (int i = 0; i < messages.length; i++) {
                messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i], format);
            }
            // 获取发送方号码
            address = messages[0].getOriginatingAddress();
            String fullMessage = "";
            //for(元素类型t 元素变量x : 遍历对象obj) foreach循环
            for (SmsMessage message : messages) {
                // 获取短信内容
                fullMessage += message.getMessageBody();
            }


            String[] split = fullMessage.split(":|\\.|]");//以.和:作为分隔
            if (split[0].equals("[MYSMS")) {
                telephone.setText(address);
                message.setText(fullMessage);
            }


            //客户订货短信模板：[MYSMS][订单]商品标识信息:1.商品数量:5.客户标识信息:15555215556.发货地址:天马学生公寓3区16栋.
            //会被split()方法分隔成[MYSMS,[订单,商品标识信息,1,商品数量,5,客户标识信息,15555215556,发货地址,天马学生公寓3区16栋
            //到账短信模板：[MYSMS][到账]客户标识信息:15555215556.商品金额:75.
            //会被split()方法分割成[MYSMS,[到账,客户标识信息,15555215556,商品金额,75
            //完毕短信模板:[MYSMS][完毕]发货单标识信息:0987654321
            //会被split()方法分割成[MYSMS,[完毕,发货单标识信息,0987654321
            switch (split[1]) {
                //最开始收到到账短信该如何处理
                case "[订单":
                    order = new Order(split[3], split[5], split[7], split[9]);
                    str = fullMessage.replace("订单", "发货");
                    OrderIntentService.startIntentService(context);
                    break;
                //然后收到银行的到账短信该如何处理
                case "[到账":
                    AccountIntentService.startIntentService(context);
                    break;
                //最后收到帮工发完货之后的回复短信该如何处理
                case "[完毕":
                    order.setDeliveryOrderNo(split[3]);
                    DeliveryIntentService.startIntentService(context);
                    break;
                default:
                    break;
            }

        }

    }
}
