package alienprodigysoftware.Interfaces;

import java.io.File;

public interface IFileStore 
{
	boolean BackupTo(IFileStore fileStore);
	String GetFileHash(String filePath);
	boolean AddFile(File file);
}