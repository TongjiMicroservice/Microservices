package com.tongji.microservice.teamsphere.gatewayservice.util;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.auth.CredentialsProviderFactory;
import com.aliyun.oss.common.auth.EnvironmentVariableCredentialsProvider;
import com.aliyuncs.exceptions.ClientException;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;

import java.io.*;

public class Loader {

    @Getter
    private static final String endpoint="oss-cn-shanghai.aliyuncs.com";

    private final static EnvironmentVariableCredentialsProvider credentialsProvider;

    static {
        try {
            credentialsProvider = CredentialsProviderFactory.newEnvironmentVariableCredentialsProvider();
            System.out.println(credentialsProvider.getCredentials());
        } catch (ClientException e) {
            throw new RuntimeException(e);
        }
    }

    @Getter
    private static final String bucketName="software-test-2024";

    @Getter
    private static final OSS ossClient=new OSSClientBuilder().build(endpoint,credentialsProvider);

    public static String getURL(){
        return "https://"+bucketName+"."+endpoint+"/";
    }
}