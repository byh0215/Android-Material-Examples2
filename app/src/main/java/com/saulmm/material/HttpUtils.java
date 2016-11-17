package com.saulmm.material;

import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/11/6.
 */
public class HttpUtils {
    private static final String Url = "http://127.0.0.1:8005/UserController/login";
    private String urlContent = null;
    private TextView Tv=null;
    // 接收线程发送过来信息，并用TextView显示
   /* public Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Tv.setText(urlContent);
        }
    };*/

    public void post_test_login() {
        URI u;
        try {
            u = new URI(Url);
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(u);

            //添加参数的代码
            NameValuePair pair1 = new BasicNameValuePair("uac", "admin");
            NameValuePair pair2 = new BasicNameValuePair("upw", "123");
            //将准备好的键值对对象放置在一个List当中
            ArrayList<NameValuePair> pairs = new ArrayList<NameValuePair>();
            pairs.add(pair1);
            pairs.add(pair2);
            //创建代表请求体的对象（注意，是请求体）
            HttpEntity requestEntity = new UrlEncodedFormEntity(pairs, "UTF-8");
            //将请求体放置在请求对象当中
            httppost.setEntity(requestEntity);
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            String content = httpclient.execute(httppost, responseHandler);
			content = new String(content.getBytes("ISO-8859-1"),"UTF-8");
            //目标页面编码为UTF-8,没这个会乱码
            urlContent = "getHTMLbyHttpClient:\n"+content;
        } catch (URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    public boolean get_test_login() {
        try {
            URL newUrl = new URL(Url);
            URLConnection connect = newUrl.openConnection();
            connect.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            DataInputStream dis = new DataInputStream(connect.getInputStream());
            BufferedReader in = new BufferedReader(new InputStreamReader(dis, "UTF-8"));// 目标页面编码为UTF-8
            String html = "";
            String readLine = null;
            while ((readLine = in.readLine()) != null) {
                html = html + readLine;
            }
            in.close();
            urlContent = html;
            if (urlContent.equals("1"))
            {
                return true;
            }
        } catch (MalformedURLException me) {
        } catch (IOException ioe) {
        }
        return false;
    }
}

