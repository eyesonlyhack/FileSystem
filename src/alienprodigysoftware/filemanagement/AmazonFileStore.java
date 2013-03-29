package alienprodigysoftware.filemanagement;

import java.io.File;
import java.util.List;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
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
	private Boolean _doesBucketExist = null;
	
	private Boolean getDoesBucketExist()
	{
		if (this._doesBucketExist == null)
		{
			// this check is expensive, so make sure we only do it once
			this._doesBucketExist = this.getAmazonS3drive().doesBucketExist(this.getBucketName());
		}
		
		return this._doesBucketExist;
	}
	
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
	
	private void setAmazonS3drive(final String amazonAccessKeyID, final String amazonSecretKey)
	{
		AWSCredentials awsc = new AWSCredentials()
		{			
			@Override
			public String getAWSSecretKey()
			{
				return amazonSecretKey;
			}
			
			@Override
			public String getAWSAccessKeyId()
			{
				return amazonAccessKeyID;
			}
		};
		
		this._amazonS3drive = new AmazonS3Client(awsc);
	}
	
	public AmazonFileStore(String bucketName, String amazonAccessKeyID, String amazonSecretKey)
	{
		setBucketName(bucketName);
		setAmazonS3drive(amazonAccessKeyID, amazonSecretKey);
	}
	
	@Override
	public boolean BackupTo(IFileStore fileStore)
	{
		

		
		return false;
	}

	@Override
	public String GetFileHash(String filePath)
	{
		if (filePath.startsWith("/"))
    	{
			filePath = filePath.substring(1, filePath.length());
    	}
		
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
        	
        	// add bucket if it does not exist
        	if (!this.getDoesBucketExist())
        	{
        		this.getAmazonS3drive().createBucket(this.getBucketName());
        	}
        	
        	// amazon file path cannot start with "/"
        	if (amazonFileNameKey.startsWith("/"))
        	{
        		amazonFileNameKey = amazonFileNameKey.substring(1, amazonFileNameKey.length());
        	}
 	
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
