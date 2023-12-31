package com.tongji.microservice.teamsphere.gatewayservice.util;

import com.obs.services.ObsClient;
import com.obs.services.exception.ObsException;
import com.obs.services.model.GetObjectRequest;
import com.obs.services.model.ObsObject;
import com.obs.services.model.PutObjectRequest;

import java.io.*;

public class Loader {

    private static final String endPoint = "https://obs.cn-north-4.myhuaweicloud.com";
    private static final String ak = "4KRYQPZF013ZRMNNXVQT";
    private static final String sk = "yyKzh0mmJxgxtWDjuPa86rdPDPCs3KbfYK04BuE5";
    private static final String bucketName = "test-micro";

    private static final ObsClient obsClient = new ObsClient(ak, sk, endPoint);

    public static ObsClient getObsClient() {
        return obsClient;
    }
    public static String getURL(){
        return "https://test-micro.obs.cn-north-4.myhuaweicloud.com/";
    }
    public static void main(String[] args) {
        try {
            // 获取指定图片对象
            GetObjectRequest request = new GetObjectRequest(bucketName,
                    "地震工程馆整体图像.png");
            ObsObject imageObject = obsClient.getObject(request);

            // 下载图片
            File localFile = new File("/1.img");
            OutputStream o = new FileOutputStream(localFile);
            imageObject.getObjectContent().transferTo(o);

            System.out.println("图片下载成功！");
        } catch (ObsException | IOException e) {
            e.printStackTrace();
        }
        try {
            File localFile = new File("/1.img");
            InputStream i = new FileInputStream(localFile);
            PutObjectRequest request = new PutObjectRequest(bucketName, "2.png", i);
            obsClient.putObject(request);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}