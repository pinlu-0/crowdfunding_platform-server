package com.atcpl.crowd.util;


import com.aliyun.api.gateway.demo.util.HttpUtils;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.comm.ResponseMessage;
import com.aliyun.oss.model.PutObjectResult;
import com.atcpl.crowd.constant.CrowdConstant;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.util.EntityUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 项目通用工具类
 * @author cpl
 */
public class CrowdUtil {


    /**
     * 负责上传文件到OSS服务器上的方法
     *
     * @param endPoint
     * @param bucketName
     * @param accessKeyId
     * @param accessKeySecret
     * @param bucketDoMain
     * @param inputStream     要上传的文件的输入流
     * @param originalName    要上传的文件的原始文件名
     * @return 包含上传结果以及上传的文件在 OSS 上的访问路径
     */
    public static ResultEntity<String> uploadFileToOSS(String endPoint,
                                                       String bucketName,
                                                       String accessKeyId,
                                                       String accessKeySecret,
                                                       String bucketDoMain,
                                                       InputStream inputStream,
                                                       String originalName) {
        // 创建ossClient实例
        OSS ossClient = new OSSClientBuilder().build(endPoint, accessKeyId, accessKeySecret);
        // 生成上传文件的目录
        String catalogName = new SimpleDateFormat("yyyyMMdd").format(new Date());
        // 使用UUID生成文件主体名称，生成原始文件在OSS服务器上保存时的文件名；原始文件名：xxx.jpg ，生成文件名：9a600b4a3ce145acabceed758db410b8.jpg
        String fileMainName = UUID.randomUUID().toString().replace("-", "");
        // 从原生文件名中获取文件扩展名
        String expanded_name = originalName.substring(originalName.lastIndexOf("."));
        // 使用文件目录、文件主题名称、文件扩展名称拼接得到对象名称
        String objectName = catalogName + "/" + fileMainName + expanded_name;
        try {
            //使用OSS客户端对象的方法上传文件并获取响应结果数据
            PutObjectResult putObjectResult = ossClient.putObject(bucketName, objectName, inputStream);
            // 从响应结果中获取具体响应消息
            ResponseMessage responseMessage = putObjectResult.getResponse();
            // 根据响应状态判断请求是否成功
            if (responseMessage == null) {
                // 拼接上传的文件的路径
                String ossFileAccessPath = bucketDoMain + "/" + objectName;
                // 当前方法返回成功
                return ResultEntity.successWithData(ossFileAccessPath);
            } else {
                // 获取响应状态码
                int statusCode = responseMessage.getStatusCode();
                // 如果请求没成功，获取错误消息
                String errorMessage = responseMessage.getErrorResponseAsString();
                // 当前方法反回失败信息
                return ResultEntity.failed("当前响应状态码=" + statusCode + "错误消息=" + errorMessage);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        } finally {
            // 关闭OSS客户端，释放资源
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
    }

    /**
     * @param host     ：短信接口调用URL地址
     * @param path     ：请求路径
     * @param method   ：请求方式
     * @param phoneNum ：接收短信的手机号
     * @param appCode  ：用来调用第三方短信API的AppCode
     * @param sign     ： 签名
     * @param skin     ：模板
     * @return 返回调用结果是够成功，以及失败信息
     */
    public static ResultEntity<String> sendShortMessage(String host, String path, String method, String phoneNum, String appCode, String sign, String skin) {

        // 生成随机4位数验证码
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            int random = (int) (Math.random() * 10);
            builder.append(random);
        }
        String code = builder.toString();

        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appCode);
        Map<String, String> querys = new HashMap<String, String>();
        // 需要发送的手机号
        querys.put("mobile", phoneNum);
        // 短信模板
        querys.put("param", "**code**:" + code + ",**minute**:5");
        // 签名ID（短信前缀ID）
        querys.put("smsSignId", sign);
        // 模板ID（短信正文ID）
        querys.put("templateId", skin);
        Map<String, String> bodys = new HashMap<String, String>();

        try {
            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            StatusLine statusLine = response.getStatusLine();
            // 状态码：200正常 ；400 URL无效；401 appCode无效 ； 403 次数用完 ； 500 API网关错误
            int statusCode = statusLine.getStatusCode();

            String reasonPhrase = statusLine.getReasonPhrase();

            if (statusCode == 200) {
                return ResultEntity.successWithData(code);
            }
            return ResultEntity.failed(reasonPhrase);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }
    }

    /**
     * 短信发送备份
     *
     * @return
     */
    public static ResultEntity<String> sendShortMessage2(String host, String path, String method, String phoneNum, String appCode, String sign, String skin) {

        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appCode);
        //根据API的要求，定义相对应的Content-Type
        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        //需要给X-Ca-Nonce的值生成随机字符串，每次请求不能相同
        headers.put("X-Ca-Nonce", UUID.randomUUID().toString());
        Map<String, String> querys = new HashMap<String, String>();
        Map<String, String> bodys = new HashMap<String, String>();
        bodys.put("filterVirtual", "false");
        bodys.put("phoneNumber", phoneNum);
        bodys.put("reqNo", "miitangtest01");
        bodys.put("smsSignId", sign);
        bodys.put("smsTemplateNo", skin);
        bodys.put("verifyCode", "");
        try {

            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            StatusLine statusLine = response.getStatusLine();
            // 状态码：200正常 ；400 URL无效；401 appCode无效 ； 403 次数用完 ； 500 API网关错误
            int statusCode = statusLine.getStatusCode();

            String reasonPhrase = statusLine.getReasonPhrase();
            String code = EntityUtils.toString(response.getEntity());
            if (statusCode == 200) {
                return ResultEntity.successWithData(code);
            }
            return ResultEntity.failed(reasonPhrase);
            //获取response的body
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.successWithData(e.getMessage());
        }
    }


    /**
     * 对明文进行MD5加密
     *
     * @param source ：传入的明文字符串
     * @return ：返回的加密结果
     */
    public static String md5(String source) {
        // 判断source是否有效
        if (source == null || source.length() == 0) {
            // 如果不是有效的字符抛出异常
            throw new RuntimeException(CrowdConstant.MESSAGE_STRING_INVALIDATE);
        }
        // 获取MessageDigest对象
        String algorithm = "md5";
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
            // 获取明文对应的字节数组
            byte[] input = source.getBytes();
            // 执行加密
            byte[] output = messageDigest.digest(input);
            // 创建BigInteger对象
            int signum = 1;
            BigInteger bigInteger = new BigInteger(signum, output);
            // 按照16进制将bigInteger的值转换为字符串
            int radix = 16;
            String encoded = bigInteger.toString(radix).toUpperCase();
            return encoded;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 判断当前请求是否为Ajax请求
     *
     * @param request 请求对象
     * @return true：当前请求是Ajax请求    false：当前请求不是Ajax请求
     */
    public static boolean judgeRequestType(HttpServletRequest request) {
        String acceptHeader = request.getHeader("Accept");
        String xRequestHeader = request.getHeader("X-Requested-With");

        //判断是Ajax请求 还是普通请求
        return (acceptHeader != null && acceptHeader.equals("application/json") || xRequestHeader != null && xRequestHeader.equals("XMLHttpRequest"));
    }
}
