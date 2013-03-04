package alienprodigysoftware.filemanagement;

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
		this._directories = directories;
	}
	
	@Override
	public boolean BackupTo(IFileStore fileStore)
	{
		// TODO Auto-generated method stub
		return false;
	}

}
