package api;

import cn.xfyun.api.GeneralWordsClient;
import cn.xfyun.config.LanguageEnum;
import cn.xfyun.config.LocationEnum;
import cn.xfyun.config.OcrWordsEnum;
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
import java.net.SocketTimeoutException;
import java.util.Base64;

/**
 * @author mqgao
 * @version 1.0
 * @date 2021/7/6 18:04
 */

@RunWith(PowerMockRunner.class)
@PrepareForTest({GeneralWordsClientTest.class})
@PowerMockIgnore("javax.net.ssl.*")
public class GeneralWordsClientTest {

    private static final String appId = PropertiesConfig.getAppId();
    private static final String apiKey = PropertiesConfig.getGeneralWordsClientApiKey();

    private String resourcePath = this.getClass().getResource("/").getPath();

    /**
     * 测试参数设置
     */
    @Test
    public void testParams() {
        GeneralWordsClient client = new GeneralWordsClient
                .Builder(appId, apiKey, OcrWordsEnum.PRINT)
                .build();
        Assert.assertEquals(appId, client.getAppId());
        Assert.assertEquals(apiKey, client.getApiKey());
        Assert.assertEquals("https://webapi.xfyun.cn/v1/service/v1/ocr/", client.getHostUrl());
        Assert.assertEquals(LocationEnum.OFF, client.getLocation());
        Assert.assertEquals(LanguageEnum.CN, client.getLanguage());
    }

    @Test
    public void testBuildParams() {
        GeneralWordsClient client = new GeneralWordsClient
                .Builder(appId, apiKey, OcrWordsEnum.HANDWRITING)
                .hostUrl("test.url")
                .language(LanguageEnum.EN)
                .location(LocationEnum.ON)
                .callTimeout(1)
                .readTimeout(2)
                .writeTimeout(3)
                .connectTimeout(4)
                .retryOnConnectionFailure(true)
                .build();
        Assert.assertEquals("test.url", client.getHostUrl());
        Assert.assertEquals(LanguageEnum.EN, client.getLanguage());
        Assert.assertEquals(LocationEnum.ON, client.getLocation());
        Assert.assertEquals(1, client.getCallTimeout());
        Assert.assertEquals(2, client.getReadTimeout());
        Assert.assertEquals(3, client.getWriteTimeout());
        Assert.assertEquals(4, client.getConnectTimeout());
        Assert.assertEquals(true, client.getRetryOnConnectionFailure());
    }

    @Test
    public void test() throws IOException {
        GeneralWordsClient client = new GeneralWordsClient
                .Builder(appId, apiKey, OcrWordsEnum.HANDWRITING)
                .build();
        byte[] imageByteArray = read(resourcePath + "/image/1.jpg");
        String imageBase64 = Base64.getEncoder().encodeToString(imageByteArray);
        try {
            System.out.println(client.generalWords(imageBase64));
        } catch (SocketTimeoutException e){
            System.out.println("SocketTimeoutException!");
        }
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
