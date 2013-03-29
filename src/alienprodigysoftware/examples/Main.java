package alienprodigysoftware.examples;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.zip.Checksum;

import org.apache.commons.codec.digest.DigestUtils;

import alienprodigysoftware.Configuration.AppConfig;
import alienprodigysoftware.Interfaces.IFileStore;
import alienprodigysoftware.backupsystem.BackupProcessor;
import alienprodigysoftware.filemanagement.AmazonFileStore;
import alienprodigysoftware.filemanagement.LocalFileStore;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.ClasspathPropertiesFileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import com.google.common.io.Files;

public class Main
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		BackupProcessor backup = new BackupProcessor();
		backup.ProcessAmazonS3Backup();
	}
	
	public static File FetchFile(String filePath)
	{
		File file = new File(filePath);		
		return file;
	}
		
	public static void AmazonS3Sync()
	{
		AmazonS3 s3 = new AmazonS3Client(new ClasspathPropertiesFileCredentialsProvider());

        String bucketName = "grantwoodford";
        String key = "home/grant/Alien Prodigy Software/website content.odt";
        //testing
        try
        {
        	// List buckets
        	System.out.println("Listing buckets");
            for (Bucket bucket : s3.listBuckets()) {
                System.out.println(" - " + bucket.getName());
            }
            System.out.println();
            
            // Upload file
            s3.putObject(new PutObjectRequest(bucketName, key, FetchFile("home/eyesonlyhack/Alien Prodigy Software/website content.odt")));
            
            s3.deleteObject(bucketName, "prisonwives.rip");
            
            // Upload file
        }
        catch (Exception e)
        {
        	System.out.println(e.getMessage());
        }
        
        System.out.println("Complete");
	}
	
	
	public static void testHashing()
	{
		//System.out.print(System.getProperty("user.dir"));
		AmazonS3 s3 = new AmazonS3Client(new ClasspathPropertiesFileCredentialsProvider());

        String bucketName = "grantwoodford";
        String key = "home/eyesonlyhack/Alien Prodigy Software/prisonwives.rip";
        
        File file = FetchFile("/"+key);
        
        try
        {
        	ObjectMetadata md = s3.getObjectMetadata(bucketName, key);
        	
            //System.out.println(md.getContentMD5());
            System.out.println(md.getETag());
            
            HashCode md5 = Files.hash(file, Hashing.md5());
            String md5Hex = md5.toString();
            
            System.out.println(md5Hex);
            
            // Upload file
            //s3.putObject(new PutObjectRequest(bucketName, key, FetchFile(file.getAbsolutePath())));
        }
        catch (Exception e)
        {
        	System.out.println(e.getMessage());
        }
        
        System.out.println("Complete");
	}
}
