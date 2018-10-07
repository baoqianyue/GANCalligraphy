package com.barackbao.aicalligraphy.util;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;

public class GenBook {

    /**
     * 用来发送请求的
     *
     * @param content
     * @return 返回html字符串, 给下面那个方法用
     */
    private String sendPost(String content) {

        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        String params = String.format("text=%s&font=9&size=68&color=#000000&bg=#ffffff&list=open", content);
        try {
            URL realUrl = new URL("https://www.zhenhaotv.com");
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(params);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！" + e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 解析html
     *
     * @param content 文字内容，APP获取
     * @return 图片url
     */
    public String GetImgURL(String content) {
        org.jsoup.nodes.Document doc = Jsoup.parse(this.sendPost(content));
        Elements row = doc.select("img[class=img-responsive center-block]");
        String element = row.attr("src");
        String url = "https://www.zhenhaotv.com" + element.substring(1, element.length());
        return url;
    }
}
