package alienprodigysoftware.filemanagement;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import alienprodigysoftware.Interfaces.IFileStore;

public class LocalFileStore implements IFileStore
{
	private List<String> _directories;
	
	private List<String> getDirectories()
	{
		return this._directories;
	}
	
	private void setDirectories(List<String> directories)
	{
		this._directories = directories;
	}
	
	public LocalFileStore(List<String> directories)
	{
		setDirectories(directories);
	}
	
	@Override
	public boolean BackupTo(IFileStore fileStore)
	{
		for (File file : this.FilesList())
		{
			
		}
		
		return false;
	}

	private List<File> FilesList()
	{
		List<File> returnFilesList = new ArrayList<File>();
		
		for (String directory : getDirectories())
		{
			for (File file : GetFilesList(directory))
			{
				returnFilesList.add(file);
			}
		}
		
		return returnFilesList;
	}

//	private static File FetchFile(String filePath)
//	{
//		File file = new File(filePath);		
//		return file;
//	}
	

	
	private static List<File> GetFilesList(String directoryPath)
	{
		List<File> filesList = new ArrayList<File>();
		
		File folder = new File(directoryPath);

		File[] listOfFiles = folder.listFiles();
		
		for (int i = 0; i < listOfFiles.length; i++)
		{			
			
			if (listOfFiles[i].isDirectory())
			{
				filesList.addAll(GetFilesList(listOfFiles[i].getAbsolutePath()));
			}
			else
			{
				filesList.add(listOfFiles[i]);
			}
		}
		
		return filesList;
	}
}
