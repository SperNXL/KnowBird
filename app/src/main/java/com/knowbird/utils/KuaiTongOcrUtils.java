package com.knowbird.utils;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpVersion;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.util.*;

/**
 * <p>
 * 快瞳OCR调用示例
 * </p>
 */
public class KuaiTongOcrUtils {
    //常量字段
    private static final String IMG_URL = "imgUrl";
    private static final String TOKEN = "token";
    private static final String FILE = "file";
    private static final String IMG_BASE_64 = "imgBase64";
    private static final String ACCESS_KEY = "accessKey";
    private static final String ACCESS_SECRET = "accessSecret";
    //http连接超时时间
    private static final int SO_TIMEOUT = 30 * 1000;
    //数据传输超时时间
    private static final int SO_TIMEOUT_DATA = 30 * 1000;

    public static void main(String[] args) {
        kuaiTongTest1();
    }

    /**
     * <p>
     * 调用快瞳OCR接口示例
     * </p>
     */
    public static void kuaiTongTest1() {
        //图片URL
        String imgUrl = "";
        //图片base64字符
        String imgBase64 = "";
        //token秘钥
        String token = "a62c56ace614a6546191d5af8ca8b1513cfaeaea7ce67d0a37de994ab6c2aa4e2a0b058e0da575ff376dd51dc19c5ad34fb457b56aca9e517e57646e1423484c3ff1e096c1fb0f40c7620204a23164037f2afa23e392602cf7fce6f7a7a38b96b9b30efd4100b6e9040efd1d57fbc65c6cf3e1046634d6f1e9b8b9063534f5844f2a728f4468b5d5bedc8ae2461357974401680c4902eb2f769b5df8cf73bdb43bb7f1787f16cd93947243bfb93399dc54db45652e7a07d4467be22a0c3e8200d81f91d9377c2abcedcedc1183b3f80219ea6189b47bc5c45bb898842a552f65b320e5e24e125f6bcde777337523f851";
        //接口URL
        String url = "https://ai.inspirvision.cn/s/api/domesticBirdType";
        //图片文件
        File file = new File("D:\\code\\android\\KnowBird\\app\\src\\main\\res\\drawable\\bird_sample_3.jpg");
        Map<String, String> map = new HashMap<>();
        map.put(IMG_URL, imgUrl);
        map.put(IMG_BASE_64, imgBase64);
        map.put(TOKEN, token);
        kuaiTongPost(url, map, file);
    }

    /**
     * <p>
     * 调用快瞳鉴权接口示例
     * </p>
     */
    public static void KuaiTongTest2() {
        Map<String, String> mapParam = new HashMap<>();
        mapParam.put(ACCESS_KEY, "APPID_BlIW67d7y540E8Pz");
        mapParam.put(ACCESS_SECRET, "4e2f5fed60c1189fc07a61de89ac1908");
        String url = "https://inspirvision.cn/s/api/getAccessToken";
        kuaiTongGet(url, mapParam);
    }

    /**
     * <p>
     * 发送http post请求
     * </p>
     *
     * @param url   请求地址
     * @param param 请求参数
     * @param file  文件参数
     */
    public static String kuaiTongPost(String url, Map<String, String> param, File file) {
        String result = "";
        CloseableHttpClient httpClient = HttpClients.createDefault();
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(SO_TIMEOUT_DATA)
                .setConnectTimeout(SO_TIMEOUT).setConnectionRequestTimeout(SO_TIMEOUT).build();// 设置请求和传输超时时间
        HttpPost httpPost = new HttpPost(url);
        httpPost.setProtocolVersion(HttpVersion.HTTP_1_0);
        try {
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            //文件参数
            if (file != null) {
                FileBody fileBody = new FileBody(file);
                builder.addPart(FILE, fileBody);
            }
            //其他参数
            if (param != null) {
                for (String key : param.keySet()) {
                    builder.addPart(key,
                            new StringBody(param.get(key), ContentType.create("text/plain", Consts.UTF_8)));
                }
            }
            HttpEntity reqEntity = builder.build();
            httpPost.setEntity(reqEntity);

            httpPost.setConfig(requestConfig);
            CloseableHttpResponse response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(entity);
            System.out.println("POST请求返回值：" + result);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * <p>
     * 发送http get请求
     * </p>
     *
     * @param url   请求地址
     * @param param 请求参数
     */
    public static String kuaiTongGet(String url, Map<String, String> param) {
        String result = "";
        CloseableHttpClient httpClient = HttpClients.createDefault();
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(SO_TIMEOUT_DATA)
                .setConnectTimeout(SO_TIMEOUT).setConnectionRequestTimeout(SO_TIMEOUT).build();// 设置请求和传输超时时间
        try {
            StringBuffer stringBuffer = new StringBuffer();
            if (param != null) {
                stringBuffer.append("?");
                for (String key : param.keySet()) {
                    stringBuffer.append(key);
                    stringBuffer.append("=");
                    stringBuffer.append(param.get(key));
                    stringBuffer.append("&");
                }
            }
            String str = stringBuffer.toString();
            String params = str.substring(0, str.length() - 1);
            HttpGet httpGet = new HttpGet(url + params);
            httpGet.setConfig(requestConfig);
            CloseableHttpResponse response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(entity);
            System.out.println("GET请求返回值：" + result);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

}

