package xyl.enigma.jsontest;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    Button button;
    TextView tv;
    Button button2;
    String str;
    TextView tv2;
    TextView tv3;
    TextView tv4;
    TextView tv5;
    TextView tv6;
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

        tv = (TextView) findViewById(R.id.textView);
        tv2 = (TextView) findViewById(R.id.textView2);
        tv3 = (TextView) findViewById(R.id.textView3);
        tv4 = (TextView) findViewById(R.id.textView4);
        tv5 = (TextView) findViewById(R.id.textView5);
        tv6 = (TextView) findViewById(R.id.textView6);
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Json();
                tv.setText(str);
            }
        });

        button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    praseJson();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void praseJson() throws JSONException {
        //注意这里可以使用optJSONArray(),optString()等方法
        JSONArray jsonArray = new JSONObject(str).getJSONArray("phone");
        tv2.setText(jsonArray.optString(0)+jsonArray.optString(1));

        String jsonStr = new JSONObject(str).getString("name");
        tv3.setText(jsonStr);

        int jsonint = new JSONObject(str).getInt("age");
        tv4.setText(String.valueOf(jsonint));

        JSONObject jsonObject = new JSONObject(str).getJSONObject("address");
        String country = jsonObject.getString("country");
        String province = jsonObject.getString("province");
        tv5.setText(country+province);

        Boolean jsonboolen = new JSONObject(str).getBoolean("married");
        tv6.setText(String.valueOf(jsonboolen));
    }

    private void Json(){
        try {
            //  {
            //      "phone" : ["12345678", "87654321"], // 数组
            //      "name" : "yuanzhifei89", // 字符串
            //      "age" : 100, // 数值
            //      "address" : { "country" : "china", "province" : "jiangsu" }, // 对象
            //      "married" : false // 布尔值
            //  }
            // 首先最外层是{}，是创建一个对象
            JSONObject person = new JSONObject();
            // 第一个键phone的值是数组，所以需要创建数组对象
            JSONArray phone = new JSONArray();
            phone.put("12345678").put("87654321");
            person.put("phone", phone);

            person.put("name", "yuanzhifei89");
            person.put("age", 100);
            // 键address的值是对象，所以又要创建一个对象
            JSONObject address = new JSONObject();
            address.put("country", "china");
            address.put("province", "jiangsu");
            person.put("address", address);
            person.put("married", false);
            str=person.toString();
        } catch (JSONException ex) {
            // 键为null或使用json不支持的数字格式(NaN, infinities)
            throw new RuntimeException(ex);
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
