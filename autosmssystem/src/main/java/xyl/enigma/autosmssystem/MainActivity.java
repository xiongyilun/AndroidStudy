package xyl.enigma.autosmssystem;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView telephone;
    private TextView message;
    private IntentFilter receiveFilter;
    private MessageReceiver messageReceiver;
    public static Order order;
    private String str = "";
    String address;
    public Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case OrderService.SEND_ORDER_SUCCESS:
                    SmsManager orderManager = SmsManager.getDefault();
                    orderManager.sendTextMessage(address, null, "[MYSMS][回复]银行账号:6217907500000403879.商品金额:" + String.valueOf(msg.obj) + ".", null, null);
                    break;
                case OrderService.SEND_ORDER_FAIL:
                    Toast.makeText(MainActivity.this, "登陆失败", Toast.LENGTH_SHORT).show();
                    break;
//                case AccountIntentService.SEND_ACCOUNT_SUCCESS:
//                    SmsManager accountManager = SmsManager.getDefault();
//                    String courierTel = (String) msg.obj;
//                    order.setCourierTel(courierTel);
//                    accountManager.sendTextMessage(courierTel, null, str, null, null);
//                case DeliveryIntentService.SEND_DELIVERY_SUCCESS:
//                    Toast.makeText(MainActivity.this, (String)msg.obj, Toast.LENGTH_SHORT).show();
//                    break;
//                case DeliveryIntentService.SEND_DELIVERY_FAIL:
//                    break;
                default:
                    break;
            }
        }

        ;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        telephone = (TextView) findViewById(R.id.telephone);
        message = (TextView) findViewById(R.id.message);

        receiveFilter = new IntentFilter();
        receiveFilter.addAction("android.provider.Telephony.SMS_RECEIVED");
        messageReceiver = new MessageReceiver();
        registerReceiver(messageReceiver, receiveFilter);
    }

    @Override
    protected void onStop() {
        unregisterReceiver(messageReceiver);
        super.onStop();
    }

    public Handler getHandler() {
        return this.handler;
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
            //会被split()方法分隔成
            //[MYSMS,[订单,商品标识信息,1,商品数量,5,客户标识信息,15555215556,发货地址,天马学生公寓3区16栋
            switch (split[1]) {
                //最开始收到到账短信该如何处理
                case "[订单":
                    order = new Order(split[3], split[5], split[7], split[9]);
                    str = fullMessage.replace("订单", "发货");
                    OrderService.startIntentService(context, split);
                    break;
//                //然后收到银行的到账短信该如何处理
//                case "[到账":
//                    AccountIntentService.startIntentService(context, split);
//                    break;
//                //最后收到帮工发完货之后的回复短信该如何处理
//                case "[完毕":
//                    DeliveryIntentService.startIntentService(context, split);
//                    break;
                default:
                    break;
            }

        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
