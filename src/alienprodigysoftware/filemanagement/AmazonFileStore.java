package alienprodigysoftware.filemanagement;

import java.util.List;

import com.amazonaws.auth.ClasspathPropertiesFileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;

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
	public List<String> FilesList()
	{
		// TODO Auto-generated method stub
		return null;
	}

	
}
