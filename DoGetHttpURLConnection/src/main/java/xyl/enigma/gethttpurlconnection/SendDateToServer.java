package xyl.enigma.gethttpurlconnection;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.DataInputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * 通过GET方式向服务器发送数据
 * Created by 一伦 on 2016/3/21.
 */
public class SendDateToServer {
    public static final int SEND_SUCCESS = 0x123;
    public static final int SEND_FAIL = 0x124;
    private String result;
    private static String url = "http://10.0.2.2:8888/TestServlet/show";
    private HttpURLConnection conn;
    private Handler handler;

    public SendDateToServer(Handler handler) {
        // TODO Auto-generated constructor stub
        this.handler = handler;
    }

    /**
     * 通过POST方式向服务器发送数据
     *
     * @param name 用户名
     * @param pwd  密码
     */
    public void SendDataToServer(String name, String pwd) {
        final Map<String, String> map = new HashMap<>();
        map.put("name", name);
        map.put("pwd", pwd);
        new Thread(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                try {
                    Message message = new Message();
                    if (sendGetRequest(map, url, "utf-8")) {
                        message.what = SEND_SUCCESS;
                        message.obj = result;
                        handler.sendMessage(message);//通知主线程数据发送成功
                    } else {
                        message.what = SEND_FAIL;
                        handler.sendMessage(message);
                    }
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } finally {
                    if (conn != null) {
                        conn.disconnect();
                    }

                }
            }
        }).start();
    }

    private boolean sendGetRequest(Map<String, String> param, String url, String encoding) throws Exception {
        // 拼凑出请求地址
        StringBuffer sb = new StringBuffer(url);
        if (!url.equals("") & !param.isEmpty()) {
            sb.append("?");
            for (Map.Entry<String, String> entry : param.entrySet()) {
                sb.append(entry.getKey() + "=").append(URLEncoder.encode(entry.getValue(), encoding));
                sb.append("&");
            }
            sb.deleteCharAt(sb.length() - 1);//删除字符串最后 一个字符“&”
        }
        Log.w("111111111111111111", sb.toString());
        conn = (HttpURLConnection) new URL(sb.toString()).openConnection();
        conn.setConnectTimeout(8000);
        conn.setReadTimeout(8000);
        conn.setRequestMethod("GET");//设置请求方式为POST
        if (conn.getResponseCode() == 200) {
            DataInputStream inputStream = new DataInputStream(conn.getInputStream());
            result =inputStream.readUTF();
            return true;
        }
        return false;
    }
}
