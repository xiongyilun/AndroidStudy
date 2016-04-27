package xyl.enigma.logintest;

import android.os.Handler;
import android.os.Message;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * 通过POST方式向服务器发送数据
 * Created by 一伦 on 2016/3/21.
 */
public class SendDateToServer {
    public static final int SEND_SUCCESS = 0x123;
    public static final int SEND_FAIL = 0x124;
    private static String url = "http://192.168.23.1:8888/TestServlet/show";
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
                    if (sendPostRequest(map, url, "utf-8")) {
                        Message message = new Message();
                        message.what = SEND_SUCCESS;
                        handler.sendMessage(message);//通知主线程数据发送成功
                    } else {
                        //将数据发送给服务器失败
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

    private boolean sendPostRequest(Map<String, String> param, String url, String encoding) throws Exception {
        // 注意Post地址中是不带参数的，所以此处这个new StringBuffer()的时候要注意不能加上url参数
        // Post方式提交的时候参数和URL是分开提交的，参数形式是这样子的：name=y&age=6
        StringBuffer sb = new StringBuffer();
        if (!url.equals("") & !param.isEmpty()) {
            for (Map.Entry<String, String> entry : param.entrySet()) {
                sb.append(entry.getKey() + "=").append(URLEncoder.encode(entry.getValue(), encoding));
                sb.append("&");
            }
            sb.deleteCharAt(sb.length() - 1);//删除字符串最后 一个字符“&”
        }
        //Log.w("111111111111111111", sb.toString());
        byte[] data = sb.toString().getBytes();
        conn = (HttpURLConnection) new URL(url).openConnection();
        conn.setConnectTimeout(8000);
        conn.setReadTimeout(8000);
        conn.setRequestMethod("POST");//设置请求方式为POST
        conn.setDoOutput(true);//允许对外传输数据
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");// 设置窗体数据编码为名称/值对
        conn.setRequestProperty("Content-Length", String.valueOf(data.length));
        OutputStream outputStream = conn.getOutputStream();//打开服务器的输入流
        outputStream.write(data);//将数据写入到服务器的输出流
        outputStream.flush();
        outputStream.close();
        if (conn.getResponseCode() == 200) {
            return true;
        }
        return false;
    }
}
