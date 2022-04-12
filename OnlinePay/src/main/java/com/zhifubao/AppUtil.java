package com.zhifubao;

import java.io.FileWriter;
import java.io.IOException;

public class AppUtil {
    // 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
    public static String app_id = "2021000117637766";

    // 商户私钥，您的PKCS8格式RSA2私钥
    //public static String merchant_private_key="MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQC5x2M5Qo6CwiR3Gk7rJJkBRTlHSwC9vnbw4hbN2mq0LTIn4Q6SFgOQSN7z6Oz49kQqy5KQKoh3rV7PsP5GjVmI7S68gsGN1sssw0/w82m8hUHFf4nYRhsqQzisbabwJFLULkDvBk0/nc+h/MG8x+b729Fx4xnrf6wFW+6o/FYzbi6cawL88w1flFqL3V/Qfr81Je+vl2eCVZJZUhOnQObrCUBTPxis6WmJvw6h59T3GTBFmXzerjHzn7wfhUSEqk4Hj/NqKnmAF3CYCzQZKQf8Wbmwbxw1o41VWzJtkMtu1c/Eh6tOV3+uZtlEyXKyplzKWKDBPOgs/5MrT1xAhfcvAgMBAAECggEBAIhoFjrc27a2xf+NmZSd8po0MbJfJ9QqHTsRRp0vWlmhNcqCPSiWcVdnqgx9YkIK0FJ71slz8W+uJIdq9cjn0G3xePeBKozywYvEfNX2ZBS/42MYGiZqk1ecs+gnOwPoJ8kTjSnz/pRPUz0lUTtDrB4RcN+mWEnKHIxyy/HYFZL8en4r8Z131wDnIpSz6eGrDAFgloFZKZtH8prWdhTcZXJYEcjA7rfWJcJCqfVKjprjq6NIHVo5JRvOzMbLTTgVieU6QUNWpDp2ATbog6gMI4ar3P/rZl+kk044nnLSNboTq6fj576m2Tx9qjmVYZj73UnvvVTSQIWrBn/vs979koECgYEA7II3eU6En4tgpLeMB+8o8BgBeFBq7MaaF8y37yJN1x/rq8ePBZHbH8cBHW/8KRl7WtQqOgn1pngvrP30D8TtzbNAJ5YmaN2VJx2JkWjS2HgXcrSkDZ/E8hmCCAUsVlC5/x1OM2Wxkiu5F2kbmpSHE4XGoaMKqp7aJ65xFls63ncCgYEAyRbj0tW/0rFa48YnN1WgTUgPspATx1xYtSPQJZDPgvfcDtwG8WTUVXZt3SY8LLWOIwk6JHkYvA9FdAMCLqUUNRxSp+CoIxmfjIcCiXKVMsdhMvXKQeWcn04AKyfTMkZyxxnMjwFiFlm92hQzglMqcjj3l4CDPyxH/8tmyT5dQwkCgYEAijk4bp8jB5OPsfTL1D9EL7337fch5qvTlyFARnRIBfhT5htFTT4HULUfw7KVLvgDjfv9idoURWxsgLATGbxANz4RupEYdaU67JdotSEMSRM2sQU98/ByTAP4aPONDA02BxF7iLJga0s+a540Uc2IfDJs+j6tlzYrzZE36pb6+tUCgYEAvJRuywYRgnE4n2rw0//E+TSfJAHhypBb0GPpi5EmSdhP0s+c4zx/tDYACekVfYg15717fUDjZVmc9JLttPiy3qvVCAKzK1eYPJ1QT73ZFWnNSZEfQHUoUG1B0zbh5MAUW4tiPqo5ZmR6ms1fENUVdVDfQIEQlGnei5AgsJF/vKECgYBOySv+lUQBuVxSkrJAmfl5lHpV4tpbrzTlONTZ+ZVeo0cK6ZLHwrxD7UcvyfHDYajwMNsqxEMYNaDLz1m9ExzPAR8iy4gugD7XMobOdGY6ye83gnieCzCUKTvi/U+pUmu22184xXgINgPFPE8XgfqpeWD+fmIl58+CSidO9Er+Sw==";
    public static String merchant_private_key="MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQDIdcRehmDYCHjADuTo0kxM4f0guFYVi7vJXuaDUoNz6VFb/lczFkG30TSNROUNQnjCePcaNqiTiIseh7bLNDYDPcCW+pT/KigItcOrIlI2mGMJqIymxujRns2BNPkSOy786JrDdzFZ+1J4xM0jvg37j7r68YNbo70+oc7J/o7aP40e2vIJk/ExJrl2M7Pk8vYj4+r2LTzyilsDGvYP4j4dU6CPWIMYvQi2Q849TqLqHmstaTOqley5odfQbkKlhFdEnqwiA2LI0E/spr6Wa4L5G1ZCD0BmJ+Adwo+/3InJAp5Wvy87cTyVDzvTRSKqhN9Omr9z+o+mikbT6hoZl3PbAgMBAAECggEAF0FqVZj7VTSuZYGU1tnG1kiFaC1ad0dtsq/ayDNjYqgXszdvgFK9rwiLMzRh6kbi4ZTM/fKHXg5CLXmLIEnfVkLsu9c4P93xJQmJOKpTpVfKbY1rO9Hpop/ssWem2ZGaIucNRU3noMvCRpZ2QgZDcwK6q3AnUBNP2htFdTOd+P2f2AbO37dF7L2jDfGUrjHL4KKN4bUh+SgF6VtBNiY4ZmtZqnMlm41hSKmAdoBNcYGb0LaTjWWizxZ/CBTEcJyduMW/3HAaXvzK9SGMibZYDdBT+sWU6xBJ/maMedtfi3H/0iAUjvO6FTY/EQSyUmzS8u52lUvAOX26xm7tew4PKQKBgQDp0UqOcWjPhnNEsEfg8wkN1dXdRdSp98mUsCKuRzC8WJsUlEXu9QfgcgoATw+EI5ctTauaQJUlOQUiNhc4NUNig9qW9QXqQe06TZ/m1m56I9RYytUr19EISZBpVH4edQzhL+6NSa/PyQT9IxPmqjV+44p7olpHZ7jnmHMj/v2OZQKBgQDbelMLQgiHp9Hvaq9uWAWD4jngKC4o1sroSQgE7v8Q8wbczDV+VLMCA9IPYwZ0NH7qBcaL+vMz9zl6J37J4OosxLCIQbLuCQ2ld0cqVf8QCIuVsEe6Ope5mlS7+NNPqBXRfBK0oXDZa+YBbpNfp5UQFm9EVP+wJUv+FhD5mzS1PwKBgAeEZgw8Uy1Q2kXcEQOHbMQYGt8ZTqQERmPUYdkOit+TTbEUr0bmc9FgeTfXUt1hs4uOUrUL3H+s3KBj8s0o0TjKmm1+BSmp4vGb60cln7mniz0rkDzvnFXS36SzRba+9qIKw3WhZ9ibUuC8jvg8gqnIsPwWlSZ0jDpN6W2/1fENAoGANnd89GZGx6OWLdxD/RZLCBe0VTSjf+8OG+dD3jWRhxtIsLLc86UBZ5abIsZoa547gMg+Ja75fAP2d6IljOQMOV/2j1n+laQZht67An6k9c2pFlTQ+B5iWOU78gomJraH85ze54TJBKwKQIC4ghyMmZl3s3WEnqoi2cOQIuGpivECgYBetM3LPT/rtWvTsm6X6FVp+PcRdRtZDVUJ1YuQFciAFlPKY0E02vRlHNYrLPOw2hwEM0edBIxPRuNRFz5lQQDDknSt8DwtUePmlDTTEfg37CM0cuGK5gtBvkYLxsZHoxmT5ecugoEof2Us7dHsZeIZ9Qn7rZ1ptKeHdbEv4UAF5Q==";
    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    //public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAnIk16vAThzutW+Dfb5tBDCWM2m6bzTkla2/C0tVWfaKrV5KtmT/P7VewjYkRmCGCknhppcYJw8cLHXkkHEwKzL6kreROYTTdxImrWZ5AmFpIwn0e1zFlYKyfa5uU0EbwssceFsXONM9qtzMYByeoyWRkJoT63/Q+lNVoDBQoJSmx/yKR3FXu3ds5bBLbQmAJQh2ZVwlwoMIOs75sFoIq69QZ2f7IUhaBHZ6Rh0Vf3XfldpQTX03EK5jCesgC7huuMdRUi3ayRsxYHtDtWIFwmba5IUNJj5zlio66sK/ZUUXnV0Z1zSA5q5bQIpzVyUl8yid5Y9kA+LyMHyInzXXJ9wIDAQAB";
    public static String alipay_public_key="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEApYV7k130FqSLATUq08uVEpHC5e2j7Q9q/QJewcEgiuL1sNJ7YPBc6kGBlzb/hlheb6lp/jPuNt6kNZx7cXjxeoTJzfG7rUbilErjb2Zqm0evT12iYqBa64AUNcbQJQNRnSChW7PsoBMK7tqZYJpgocepARCkdM/N6sBMNJI/0oqsfSsBvCBdMi+HOg+tEp1elMLwgkwOpVdDZPRokVqROhiRD+bAqTc3oZ9Ud8jLwdy0XTIXdLLMswcIE6msrpxT64nxTLsQO/ZY2XSFle7/Zyuw7meAIEXS7apGl0MidZftyhm33IPNtBjs0peKvtQ9gTfbiOKJsSq8/3nLpMzKdQIDAQAB";
    // 服务器异步通知页面路径
    //需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String notify_url = "http://localhost:8081/getnotify";

    // 页面跳转同步通知页面路径
    //需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String return_url = "http://localhost:8081/getreturn";

    // 签名方式
    public static String sign_type = "RSA2";

    // 字符编码格式
    public static String charset = "utf-8";

    // 支付宝网关,注意这些使用的是沙箱的支付宝网关，与正常网关的区别是多了dev
    public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";

    // 支付宝网关
    public static String log_path = "/Users/xubin03/Projects/MyWeb/OnlinePay";


//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

    /**
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     * @param sWord 要写入日志里的文本内容
     */
    public static void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis()+".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
