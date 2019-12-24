package cos.test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.request.UploadFileRequest;
import com.qcloud.cos.sign.Credentials;

public class Costest {

	public static void main(String[] args){
		int appId = 1251769221;
		String appSecretID = "AKIDdHSjppEleGjfYG5v8wFKTlAVjF9dj9zV";
		String SecretKey = "XQOg72GoFeFzXFssHheO1LWOGyYcpmvX";
		String region = "sh";
		String bucket = "reszulongcom";
		
		//腾讯云
        Credentials cred = new Credentials(appId, appSecretID, SecretKey);
        ClientConfig clientConfig = new ClientConfig();
        clientConfig.setRegion(region);
        clientConfig.setConnectionRequestTimeout(10);
        COSClient cosClient = new COSClient(clientConfig, cred);
        byte[] file = File2byte("C:\\Users\\miang\\Desktop\\图\\7.png");
        String uploadFileRet = uploadPic(cosClient, file, "7.png", bucket);
        System.out.println(uploadFileRet);
	}
	
	public static String uploadPic(COSClient cosClient, byte[] bytes, String fileName, String bucket) {
		try{
			long time = System.currentTimeMillis();
			String cosPath = "/image/offcial/" + time + "/" + fileName;
			UploadFileRequest uploadFileRequest = new UploadFileRequest(bucket, cosPath, bytes);
			String uploadFileRet = cosClient.uploadFile(uploadFileRequest);
			return uploadFileRet;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public static byte[] File2byte(String filePath)  
    {  
        byte[] buffer = null;  
        try  
        {  
            File file = new File(filePath);  
            FileInputStream fis = new FileInputStream(file);  
            ByteArrayOutputStream bos = new ByteArrayOutputStream();  
            byte[] b = new byte[1024];  
            int n;  
            while ((n = fis.read(b)) != -1)  
            {  
                bos.write(b, 0, n);  
            }  
            fis.close();  
            bos.close();  
            buffer = bos.toByteArray();  
        }  
        catch (Exception e)  
        {  
            e.printStackTrace();  
        }  
        return buffer;  
    }  
}
