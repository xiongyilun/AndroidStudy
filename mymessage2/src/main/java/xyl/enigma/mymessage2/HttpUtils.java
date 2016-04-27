package xyl.enigma.mymessage2;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by 一伦 on 2016/4/15.
 */
public class HttpUtils {

    private static HttpURLConnection httpConn;

    public static String getStringJsonContent(String urlStr) {
        try {// 获取HttpURLConnection连接对象
            URL url = new URL(urlStr);
            httpConn = (HttpURLConnection) url
                    .openConnection();
            // 设置连接属性
            httpConn.setConnectTimeout(8000);
            httpConn.setReadTimeout(8000);
            httpConn.setRequestMethod("GET");
            // 获取相应码
            if (httpConn.getResponseCode() == 200) {
                return ConvertStreamToJson(httpConn.getInputStream());
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (httpConn != null) {
                httpConn.disconnect();
            }
        }
        return "";
    }

    public static int getJsonContent(String urlStr) {
        try {// 获取HttpURLConnection连接对象
            URL url = new URL(urlStr);
            httpConn = (HttpURLConnection) url
                    .openConnection();
            // 设置连接属性
            httpConn.setConnectTimeout(8000);
            httpConn.setReadTimeout(8000);
            httpConn.setRequestMethod("GET");
            // 获取相应码
            if (httpConn.getResponseCode() == 200) {
                return ConvertStreamToJsono(httpConn.getInputStream());
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (httpConn != null) {
                httpConn.disconnect();
            }
        }
        return -1;
    }

    public static String postJsonContent(String urlStr, byte[] data) {
        try {// 获取HttpURLConnection连接对象
            URL url = new URL(urlStr);
            httpConn = (HttpURLConnection) url
                    .openConnection();
            // 设置连接属性
            httpConn.setConnectTimeout(8000);
            httpConn.setReadTimeout(8000);
            httpConn.setRequestMethod("POST");
            httpConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            httpConn.setRequestProperty("Content-Length", String.valueOf(data.length));
            OutputStream outputStream = httpConn.getOutputStream();
            outputStream.write(data);
            // 获取相应码
            if (httpConn.getResponseCode() == 200) {
                return ConvertStreamToJson(httpConn.getInputStream());
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (httpConn != null) {
                httpConn.disconnect();
            }
        }
        return "";
    }

    public static String ConvertStreamToJson(InputStream inputStream) throws IOException {
        DataInputStream dataInputStream = new DataInputStream(inputStream);
        String str = dataInputStream.readUTF();
        return str;
    }

    public static int postJsonContento(String urlStr, byte[] data) {
        try {// 获取HttpURLConnection连接对象
            URL url = new URL(urlStr);
            httpConn = (HttpURLConnection) url
                    .openConnection();
            // 设置连接属性
            httpConn.setConnectTimeout(8000);
            httpConn.setReadTimeout(8000);
            httpConn.setRequestMethod("POST");
            httpConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            httpConn.setRequestProperty("Content-Length", String.valueOf(data.length));
            OutputStream outputStream = httpConn.getOutputStream();
            outputStream.write(data);
            // 获取相应码
            if (httpConn.getResponseCode() == 200) {
                return ConvertStreamToJsono(httpConn.getInputStream());
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (httpConn != null) {
                httpConn.disconnect();
            }
        }
        return -1;
    }

    public static int ConvertStreamToJsono(InputStream inputStream) throws IOException {
        DataInputStream dataInputStream = new DataInputStream(inputStream);
        int num = dataInputStream.readInt();
        return num;
    }
}
