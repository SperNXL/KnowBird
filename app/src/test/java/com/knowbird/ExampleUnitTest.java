package com.knowbird;

import org.junit.Test;

import static org.junit.Assert.*;

import com.knowbird.utils.KuaiTongOcrUtils;

import java.io.FileInputStream;
import java.util.Properties;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void OcrGetTokenTest() {
        KuaiTongOcrUtils.KuaiTongTest2();
    }

    @Test
    public void OcrBirdPostTest() {
        KuaiTongOcrUtils.kuaiTongTest1();
    }

    @Test
    public void getApiInfoTest() {
        String accessKey = BuildConfig.KUAI_TONG_ACCESS_KEY;
        String accessSecret = BuildConfig.KUAI_TONG_ACCESS_SECRET;
        String token = BuildConfig.KUAI_TONG_TOKEN;

        boolean isEmpty = false;
        if (accessKey.isEmpty() || accessSecret.isEmpty() || token.isEmpty()) {
            isEmpty = true;
        }
        System.out.println("accessKey:" + accessKey + "accessSecret:" + accessSecret +
                "\n token:" + token);
        assertEquals(false, isEmpty);
    }
}