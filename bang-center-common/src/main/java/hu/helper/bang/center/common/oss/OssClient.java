package hu.helper.bang.center.common.oss;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.OSSObject;
import com.aliyuncs.utils.StringUtils;
import hu.helper.bang.center.common.exception.BangException;
import hu.helper.bang.center.common.result.BangResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

/**
 * @author 江星坤
 */
@Api(value = "OSS控制器", tags = {"OSS相关接口"})
@RestController
@Slf4j
@RequestMapping(value = "common/oss")
public class OssClient {
    
    @Value("${oss.endpoint}")
    private  String endpoint;
    @Value("${oss.accessKeyId}")
    private  String accessKeyId;
    @Value("${oss.accessKeySecret}")
    private  String accessKeySecret;
    @Value("${oss.bucketName}")
    private  String bucketName;
    @Value("${oss.baseUrl}")
    private  String baseUrl;

    private  final ArrayList<String> IMAGE_TYPE = new ArrayList<>(Arrays.asList(".bmp", ".jpg", ".jpeg", ".gif", ".png"));
    private  final ArrayList<String> FILE_TYPE = new ArrayList<>(Arrays.asList(".txt", ".pdf", ".doc", ".docx", ".ppt",
            "pptx", "xls", "xlsx", "rar", "zip"));


    @ApiOperation(value = "文件上传")
    @PostMapping("/upLoad")
    public BangResult upLoadFile(@Validated MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        InputStream inputStream = file.getInputStream();
        return upLoad(originalFilename,inputStream);
    }

    @ApiOperation(value = "文件下载")
    @GetMapping("/download")
    public BangResult downloadFile( String path) {
        return download(path);
    }

    /**
     * 文件上传
     *
     * @param fileName    文件名字
     * @param inputStream 文件流
     * @return 如果返回的结果代码是200，就取“msg”来获取文件的储存地址
     */
    public  BangResult upLoad(String fileName, InputStream inputStream) {
        String fullPath;
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        try {
            //上传
            log.info("上传文件名:{}", fileName);
            fullPath = getUploadPath(fileName);
            ossClient.putObject(bucketName, fullPath, inputStream);
        } catch (OSSException oe) {
            //传到oss，但是被拒绝了
            log.info("被OSS拒绝,Error Message:{},Error Code:{}", oe.getErrorMessage(), oe.getErrorCode());
            throw new BangException(oe.getErrorMessage());
        } catch (ClientException ce) {
            //客户端在尝试与OSS通信时遇到严重的内部问题
            log.info("与OSS通讯失败，Error Message:{}", ce.getMessage());
            throw new BangException(ce.getErrorMessage());
        } catch (Exception e) {
            log.info("上传失败,errorMsg：{}", e.getMessage());
            return BangResult.error(e.getMessage());
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        if (!StringUtils.isEmpty(fullPath)) {
            log.info("上传成功,文件地址：{}", baseUrl + fullPath);
            return BangResult.ok(baseUrl + fullPath);
        }
        return BangResult.error();
    }

    /**
     * 文件下载
     *
     * @param filePath 文件的下载路径
     * @return 如果返回的结果代码是200，就取“fileStream”来获取文件流
     */
    public  BangResult download(String filePath) {

        String downloadPath = getDownloadPath(filePath);
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        InputStream content;
        try {
            log.info("下载路径:{}", downloadPath);
            // 调用ossClient.getObject返回一个OSSObject实例，该实例包含文件内容及文件元信息。
            OSSObject ossObject = ossClient.getObject(bucketName, downloadPath);
            // 调用ossObject.getObjectContent获取文件输入流，可读取此输入流获取其内容。
            content = ossObject.getObjectContent();
        } catch (OSSException oe) {
            //传到oss，但是被拒绝了
            log.info("被OSS拒绝,Error Message:{},Error Code:{}", oe.getErrorMessage(), oe.getErrorCode());
            throw new BangException(oe.getErrorMessage());
        } catch (ClientException ce) {
            //客户端在尝试与OSS通信时遇到严重的内部问题
            log.info("与OSS通讯失败，Error Message:{}", ce.getMessage());
            throw new BangException(ce.getErrorMessage());
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        if (null != content) {
            log.info("下载成功，文件流：{}", content);
            return BangResult.ok(content);
        }
        return BangResult.error();
    }

    /**
     * 获取下载路径
     * @param filePath 文件储存的地址 http://xxx/phtots（files）/yyyy/MM/dd/xxx.xx
     * @return 去掉网站ip后的地址 phtots（files）/yyyy/MM/dd/xxx.xx
     */
    public String getDownloadPath(String filePath){
        String[] split = filePath.split("/");
        StringBuilder s1 = new StringBuilder();
        for (int i = 3; i < split.length; i++) {
            s1.append(split[i]);
            s1.append("/");
        }
        return s1.substring(0,s1.length()-1);
    }
    /**
     * 根据文件名生成对应的上传路径
     *
     * @param fileName 文件名
     * @return 完整上传路径 phtots（files）/yyyy/MM/dd/xxx.xx
     */
    public  String getUploadPath(String fileName) {
        Date date = new Date();
        String format = new SimpleDateFormat("yyyy/MM/dd").format(date);
        String fileType = fileName.substring(fileName.lastIndexOf("."));
        String file = UUID.randomUUID() + fileType;

        String path = getFileType(fileType);
        return path + format + "/" + file;
    }

    /**
     * 获取该文件上传的位置
     *
     * @param type 文件类型
     * @return 对应的类型路径
     */
    public  String getFileType(String type) {
        if (IMAGE_TYPE.contains(type)) {
            return "photos/";
        } else if (FILE_TYPE.contains(type)) {
            return "files/";
        } else {
            log.error("不支持该文件类型上传:{}",type);
            throw new BangException("不支持该文件类型上传.");
        }
    }
}
