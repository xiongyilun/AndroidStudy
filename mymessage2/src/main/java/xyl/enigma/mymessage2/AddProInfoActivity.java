package xyl.enigma.mymessage2;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

public class AddProInfoActivity extends AppCompatActivity {

    private EditText proIDEdittext;
    private EditText proPriceEdittext;
    private EditText courierNoEdittext;
    private Button button;
    private String result;
    public static final int ADD_PRO_SUCCESS = 7;
    public static final int ADD_PRO_FAIL = 8;
    private static final String BASEURL = "http://192.168.23.1:8888/TestServlet/addProInfo";
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case ADD_PRO_SUCCESS:
                    Toast.makeText(AddProInfoActivity.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
                    break;
                case ADD_PRO_FAIL:
                    Toast.makeText(AddProInfoActivity.this, "网络出错请重试", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pro_info);
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

        proIDEdittext = (EditText) findViewById(R.id.proIDEdittext);
        proPriceEdittext = (EditText) findViewById(R.id.proPriceEdittext);
        courierNoEdittext = (EditText) findViewById(R.id.courierNoEdittext);
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String proID = proIDEdittext.getText().toString();
                String proPrice = proPriceEdittext.getText().toString();
                String courierNo = courierNoEdittext.getText().toString();
                if (proID.equals("") || proPrice.equals("") || courierNo.equals("")) {
                    Toast.makeText(AddProInfoActivity.this, "输入不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    ProInfo proInfo = new ProInfo(Integer.parseInt(proID), Integer.parseInt(proPrice), Integer.parseInt(courierNo));
                    Gson gson = new Gson();
                    String str = gson.toJson(proInfo);
                    //将数据转换成字节数组
                    final byte[] data = ("postProInfo=" + str).toString().getBytes();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Message mes = new Message();
                                if (postProInfo(BASEURL, data)) {
                                    mes.what = ADD_PRO_SUCCESS;
                                    mes.obj = result;
                                    handler.sendMessage(mes);
                                } else {
                                    mes.what = ADD_PRO_FAIL;
                                    handler.sendMessage(mes);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }

            }
        });
    }

    private Boolean postProInfo(String url, byte[] data) throws Exception {
        String str = HttpUtils.postJsonContent(BASEURL, data);
        if (str.equals("")) {
            return false;
        } else
            result = str;
        return true;
    }
}
