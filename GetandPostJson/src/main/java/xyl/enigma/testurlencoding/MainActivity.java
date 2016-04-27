package xyl.enigma.testurlencoding;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private static final String BASEURL = "http://192.168.23.1:8888/JSONTest/json";
    private String postJsonStr;
    private String getJsonStr;

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    Toast.makeText(MainActivity.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    Toast.makeText(MainActivity.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
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


        final Button getJson = (Button) findViewById(R.id.getJson);
        getJson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            getJson();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

            }
        });


        final Button postJson = (Button) findViewById(R.id.postJson);
        postJson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        postJson();
                    }
                }).start();
            }
        });
    }

    private void getJson() throws JSONException, IOException {
        //将person对象转换成Json字符串
        Person person1 = new Person("熊一伦", 22, "湖南省长沙市", true);
        Gson gson = new Gson();
        String str = gson.toJson(person1);
//         会出错，不知道为什么
//        Order order = new Order("1","5","15555215556","天马学生公寓3区16栋");
//        order.setOrderTime("2016-04-12 04:09:27");
//        Gson gson = new Gson();
//        String str = gson.toJson(order);
        //获得总的URL
        String totalURL = BASEURL + "?getJson=" + str;
        Log.i("111111111111", totalURL);
        //获得返回Json字符串
        getJsonStr= HttpUtils.getJsonContent(totalURL);
        //将返回Json字符串转换成person对象
        Person person = gson.fromJson(getJsonStr,Person.class);
        //handler处理
        Message msg = new Message();
        msg.what = 1;
        msg.obj = person;
        handler.sendMessage(msg);

    }

    private void postJson() {
//        //将person对象转换成Json字符串
//        Person person2 = new Person("燕文倩",20, "湖南省长沙市", false);
//        Gson gson = new Gson();
//        String str = gson.toJson(person2);
        Order order = new Order("1","5","15555215556","天马学生公寓3区16栋");
        order.setOrderTime("2016-04-12 04:09:27");
        Gson gson = new Gson();
        String str = gson.toJson(order);
        //将数据转换成字节数组
        byte[] data =("postJson=" + str).toString().getBytes();
        //获得返回Json字符串
        postJsonStr = HttpUtils.postJsonContent(BASEURL,data);
        //将返回Json字符串转换成person对象
        Person person = gson.fromJson(postJsonStr,Person.class);
        //handler处理
        Message msg = new Message();
        msg.what = 2;
        msg.obj = person;
        handler.sendMessage(msg);
    }


}
