package api;

import cn.xfyun.api.ImageWordClient;
import cn.xfyun.config.ImageWordEnum;
import config.PropertiesConfig;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

/**
 * @author mqgao
 * @version 1.0
 * @date 2021/7/8 15:56
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({JDOcrClientTest.class})
@PowerMockIgnore({"javax.crypto.*", "javax.net.ssl.*"})
public class ImageWordClientTest {

    private static final String appId = PropertiesConfig.getAppId();
    private static final String apiKey = PropertiesConfig.getApiKey();
    private static final String apiSecret = PropertiesConfig.getApiSecret();

    private String resourcePath = this.getClass().getResource("/").getPath();

    @Test
    public void testParams() {
        ImageWordClient client = new ImageWordClient
                .Builder(appId, apiKey, apiSecret, ImageWordEnum.IDCARD)
                .build();
        Assert.assertEquals(appId, client.getAppId());
        Assert.assertEquals(apiKey, client.getApiKey());
        Assert.assertEquals("https://api.xf-yun.com/v1/private/", client.getHostUrl());
    }

    @Test
    public void testBuildParams() {
        ImageWordClient client = new ImageWordClient
                .Builder(appId, apiKey, apiSecret, ImageWordEnum.IDCARD)
                .hostUrl("test.url")
                .callTimeout(1)
                .readTimeout(2)
                .writeTimeout(3)
                .connectTimeout(4)
                .retryOnConnectionFailure(true)
                .build();
        Assert.assertEquals("test.url", client.getHostUrl());
        Assert.assertEquals(1, client.getCallTimeout());
        Assert.assertEquals(2, client.getReadTimeout());
        Assert.assertEquals(3, client.getWriteTimeout());
        Assert.assertEquals(4, client.getConnectTimeout());
        Assert.assertEquals(true, client.getRetryOnConnectionFailure());
    }

    @Test
    public void testIdcard() throws IOException {
        ImageWordClient client = new ImageWordClient
                .Builder(appId, apiKey, apiSecret, ImageWordEnum.IDCARD)
                .build();
        File file = new File(resourcePath + "/image/car.jpg");
        byte[] imageByteArray = Files.readAllBytes(Paths.get(file.getPath()));
        String imageBase64 = Base64.getEncoder().encodeToString(imageByteArray);
        System.out.println(client.imageWord(imageBase64, "jpg"));
    }

    @Test
    public void testPrintWord() throws IOException {
        ImageWordClient client = new ImageWordClient
                .Builder(appId, apiKey, apiSecret, ImageWordEnum.PRINTED_WORD)
                .build();
        File file = new File(resourcePath + "/image/print.jpg");
        byte[] imageByteArray = Files.readAllBytes(Paths.get(file.getPath()));
        String imageBase64 = Base64.getEncoder().encodeToString(imageByteArray);
        System.out.println(client.imageWord(imageBase64, "jpg"));
    }

    @Test
    public void testBusinessLicense() throws IOException {
        ImageWordClient client = new ImageWordClient
                .Builder(appId, apiKey, apiSecret, ImageWordEnum.BUSINESS_LICENSE)
                .build();
        File file = new File(resourcePath + "/image/car.jpg");
        byte[] imageByteArray = Files.readAllBytes(Paths.get(file.getPath()));
        String imageBase64 = Base64.getEncoder().encodeToString(imageByteArray);
        System.out.println(client.imageWord(imageBase64, "jpg"));
    }

    @Test
    public void testInvoice() throws IOException {
        ImageWordClient client = new ImageWordClient
                .Builder(appId, apiKey, apiSecret, ImageWordEnum.INVOICE)
                .build();
        File file = new File(resourcePath + "/image/car.jpg");
        byte[] imageByteArray = Files.readAllBytes(Paths.get(file.getPath()));
        String imageBase64 = Base64.getEncoder().encodeToString(imageByteArray);
        System.out.println(client.imageWord(imageBase64, "jpg"));
    }

    @Test
    public void testTaxiInvoice() throws IOException {
        ImageWordClient client = new ImageWordClient
                .Builder(appId, apiKey, apiSecret, ImageWordEnum.TAXI_INVOICE)
                .build();
        File file = new File(resourcePath + "/image/car.jpg");
        byte[] imageByteArray = Files.readAllBytes(Paths.get(file.getPath()));
        String imageBase64 = Base64.getEncoder().encodeToString(imageByteArray);
        System.out.println(client.imageWord(imageBase64, "jpg"));
    }

    @Test
    public void testTrainTicket() throws IOException {
        ImageWordClient client = new ImageWordClient
                .Builder(appId, apiKey, apiSecret, ImageWordEnum.TRAIN_TICKET)
                .build();
        File file = new File(resourcePath + "/image/car.jpg");
        byte[] imageByteArray = Files.readAllBytes(Paths.get(file.getPath()));
        String imageBase64 = Base64.getEncoder().encodeToString(imageByteArray);
        System.out.println(client.imageWord(imageBase64, "jpg"));
    }


}
