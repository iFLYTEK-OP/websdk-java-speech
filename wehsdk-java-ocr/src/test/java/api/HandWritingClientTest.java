package api;

import cn.xfyun.api.HandWritingClient;
import cn.xfyun.config.HandWritingLanguageEnum;
import cn.xfyun.config.HandWritingLocationEnum;
import config.PropertiesConfig;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

/**
 * @author mqgao
 * @version 1.0
 * @date 2021/7/6 17:36
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({HandWritingClientTest.class})
@PowerMockIgnore("javax.net.ssl.*")
public class HandWritingClientTest {


    private static final String appId = PropertiesConfig.getAppId();
    private static final String apiKey = PropertiesConfig.getApiKey();

    private String resourcePath = this.getClass().getResource("/").getPath();

    @Test
    public void testParams() {
        HandWritingClient client = new HandWritingClient
                .Builder(appId, apiKey)
                .build();
        Assert.assertEquals(appId, client.getAppId());
        Assert.assertEquals(apiKey, client.getApiKey());
        Assert.assertEquals("https://webapi.xfyun.cn/v1/service/v1/ocr/handwriting", client.getHostUrl());
        Assert.assertEquals(HandWritingLocationEnum.OFF, client.getLocation());
        Assert.assertEquals(HandWritingLanguageEnum.CN, client.getLanguage());
    }

    @Test
    public void testBuildParams() {
        HandWritingClient client = new HandWritingClient
                .Builder(appId, apiKey)
                .hostUrl("test.url")
                .language(HandWritingLanguageEnum.EN)
                .location(HandWritingLocationEnum.ON)
                .callTimeout(1)
                .readTimeout(2)
                .writeTimeout(3)
                .connectTimeout(4)
                .retryOnConnectionFailure(true)
                .build();
        Assert.assertEquals("test.url", client.getHostUrl());
        Assert.assertEquals(HandWritingLanguageEnum.EN, client.getLanguage());
        Assert.assertEquals(HandWritingLocationEnum.ON, client.getLocation());
        Assert.assertEquals(1, client.getCallTimeout());
        Assert.assertEquals(2, client.getReadTimeout());
        Assert.assertEquals(3, client.getWriteTimeout());
        Assert.assertEquals(4, client.getConnectTimeout());
        Assert.assertEquals(true, client.getRetryOnConnectionFailure());
    }

    @Test
    public void test() throws IOException {
        HandWritingClient client = new HandWritingClient
                .Builder(appId, apiKey)
                .build();
        byte[] imageByteArray = read(resourcePath + "/image/1.jpg");
        String imageBase64 = Base64.getEncoder().encodeToString(imageByteArray);
        System.out.println(client.handWriting(imageBase64));
    }


    /**
     * 流转二进制数组
     *
     * @param in
     * @return
     * @throws IOException
     */
    private static byte[] inputStream2ByteArray(InputStream in) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024 * 4];
        int n = 0;
        while ((n = in.read(buffer)) != -1) {
            out.write(buffer, 0, n);
        }
        return out.toByteArray();
    }

    private static byte[] read(String filePath) throws IOException {

        InputStream in = new FileInputStream(filePath);
        byte[] data = inputStream2ByteArray(in);
        in.close();
        return data;
    }


}
