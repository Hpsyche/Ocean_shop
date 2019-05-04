package com.alipay.config;

import java.io.FileWriter;
import java.io.IOException;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *修改日期：2017-04-05
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */

public class AlipayConfig {

//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

    // 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
    public static String app_id = "2016093000630125";

    // 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCeahMijey9IXO82unF8CLNc0P2C6sh4jpUHAJcO318+GzhR+2NnCKEiL4uvHQE3fNuxWZKthtQBb962jRLSdiKNMT0SKvrCZ6vUEceWMndbwytXg1bP72W4GzTDZ2ITk3e7P4wG15J2wUPL06It+5w7IZBrBHVxrtw88B27lUxHZN+28MzeXTLlSVFwGGOjPQD8idzEUjiZQTVHHP1n3foGRm3MYUmpI31QMreiT53NpPFrCG2UYVVms3Og7NMkYkAkt9Pdjyf983o0Kh1bpSJFAXAAXVBmkHNdn7r0db2J8ou+ygqBuEtYPYuIUtGAvzQ+1nD6OJ6YfKDNUMMhhqvAgMBAAECggEAE7eGLC3YAm5QdngU0rMh0cp/8fbf1PbQZbmavZoV82EEREHsmkyNq0tNyIliM1zguK/PHItv+NTOUV8pVRma3xDUVsdYxodK/e0S/P72GR6CSG3FYc8cLNRAF0DBk76TSKTcumYdpTmidpJfxvqFrf5yc2gUTDRRDiz4jpT5e4RMpUo6tWfcWk57/RiXzchMS3Sq+SUjL+dwZSs9+1VMZyS2d6b3MtdC4fvopJUAU3MhpugypcKIcM74z5UYccaUqgvjjgOPHcxyEWrtNfYbq+lAf2BCx8mgXLhwzb8hdFwmEP4OeT9Vuoq4G4XRk5zrhz+dsv3/pvgVOn1MC9JkCQKBgQD4PMjXEt84MGkAKDrcmu+nIDZGd+8XEp2iY1EvhPqGpCVCFk3/tAZMewG5fyj0TEo0r6ZhjWFP5eLaVwbkwllIp0MIwW/r1jYZ4F7UfJU3rmnZLe0eMDC6la1XFKSlQncXrF396zrhH1dcnXcGLJUrS9f0tECpbQwhqaRCjdoFtQKBgQCjXjq3qiQdV6OVY62Zly9kDwuMsG+b02ZV5zaYoB6geAvlDKXrQ4cSVLewSULyz8UDOT14qxUcMWB20rlYijAD0heEJKoq9YUSySyrTUMje8L8cGly53hmSP2M9EWKuSHS1NYczanK1M9svbQfFGRWBLXFlFvISkKSOHyd6eXdUwKBgGCfIDl+sf3n8ebKweZ+NAvkINwa8KgVad/8MmyBoRXpLEtl7SQ7STxZoJk4IpO8YiHRwLw10IvN2hfMSfWepCwAVrpAuLSeI169E4w3lckfjTmog6P9c+ocOmUrts5QaZSAVfFwrvFTRqjY7awsjD9W/Q7VXlnUBSXRJwt0tUkFAoGBAJMOMkq7o9Cp3/2MgWsdNTKbfGkDNSQOkjLHc1TF5WvLQ+HFleEFoyN0EPllqRcKwawaLWRelB0RsWSkYOT7VydJSZPQsUEESupTHpuAa+wX7rfvtGwTQkTIdTwZcjPiXDj9D1NUKgADukeHKAlGAN+RU9JrrobHmc8ne71mG10zAoGBAIu2UmIImDuE76+n2412SgsU0PRfco7hPoKbHUIYiYgTBgCjzzhetdVLjBjcbAILX1b5wWFB5Fotq+6s2cEfcrWGmTOBDZFWYE2CYndvuVEb0ye6+pw90nRj9lJynFYWfulnV16zKPWeeS+99DJoLW0RwDZ7UgK4HZjBtxrrPUax";

    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAplZREFe3HcvWVH5F2aEiMQkHfHVftLciKVtnI9mTbIt7tc6bHIVFgMEW9dnsu1RJWYBNhw9YJk3AwzKPveV28BNfSYcUHQ49V58bvm6RwVckt+fh2Z9Xszs9LhIEITVo+rwV62tdnVakqGzAGuhJH/KOiG+BtZyhNQDtjGZRF002hxg5F4zvqMngaKHHk8ZOklCj1VZZcmPLDa81W97T4Ldd9BEU9hIM6euOpp5KoOszUr1ztgQ3aB42JKRY3mdA3K4bRe3vXIScWKpibzanA+1zzUBwFC0+zaE8JeI195OdWZIIiicOybGhKMwIMsb50bk/XsIueLnBS3Vz6+4SzQIDAQAB";

    // 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String notify_url = "http://127.0.0.1:8080/order/updateOrderStatus";

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String return_url = "http://127.0.0.1:8080/order/updateOrderStatus";

    // 签名方式
    public static String sign_type = "RSA2";

    // 字符编码格式
    public static String charset = "utf-8";

    // 支付宝网关
    public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";

    // 支付宝网关
    public static String log_path = "C:\\";


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

