package alienprodigysoftware.Interfaces;

public interface IFileStore 
{
	boolean BackupTo(IFileStore fileStore);
	String GetFileHash(String filePath);
}