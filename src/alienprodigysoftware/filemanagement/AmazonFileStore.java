package alienprodigysoftware.filemanagement;

import java.io.File;
import java.util.List;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.ClasspathPropertiesFileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import alienprodigysoftware.Interfaces.IFileStore;

public class AmazonFileStore implements IFileStore
{
	private String _bucketName;
	private AmazonS3 _amazonS3drive;
	
	private String getBucketName()
	{
		return this._bucketName;
	}
	
	private void setBucketName(String bucketName)
	{
		this._bucketName = bucketName;
	}
	
	private AmazonS3 getAmazonS3drive()
	{
		return this._amazonS3drive;
	}
	
	private void setAmazonS3drive(String credentialsProvider)
	{
		this._amazonS3drive = new AmazonS3Client(new ClasspathPropertiesFileCredentialsProvider(credentialsProvider));
	}
	
	public AmazonFileStore(String bucketName, String credentialsProvider)
	{
		setBucketName(bucketName);
		setAmazonS3drive(credentialsProvider);
	}
	
	@Override
	public boolean BackupTo(IFileStore fileStore)
	{
		

		
		return false;
	}

	@Override
	public String GetFileHash(String filePath)
	{
		try
		{
			ObjectMetadata md = this.getAmazonS3drive().getObjectMetadata(this.getBucketName(), filePath);
			return md.getETag();
		}
		catch (AmazonS3Exception s3e) 
	    {
	        if (s3e.getStatusCode() == 404) {
	        // i.e. 404: NoSuchKey - The specified key does not exist
	            return "File Does Not Exist";
	        }
	        else {
	            throw s3e;    // rethrow all S3 exceptions other than 404   
	        }
	    }
	}
	
	@Override
	public boolean AddFile(File file)
	{
		Boolean successfulUpload = true;
        
        try
        {
        	String amazonFileNameKey = file.getAbsolutePath();
        	
//        	// amazon file path cannot start with "/"
//        	if (amazonFileNameKey.startsWith("/"))
//        	{
//        		amazonFileNameKey = amazonFileNameKey.substring(1, amazonFileNameKey.length() - 1);
//        	}
 	
            // Upload file
            this.getAmazonS3drive().putObject(new PutObjectRequest(this.getBucketName(), amazonFileNameKey, file));
        }
        catch (Exception e)
        {
        	successfulUpload = false;
        	System.out.println(e.getMessage());
        }
        
        return successfulUpload;
	}
}
