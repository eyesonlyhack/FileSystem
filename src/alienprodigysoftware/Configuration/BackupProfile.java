package alienprodigysoftware.Configuration;

public class BackupProfile
{
	private String _Username;	
	private String _Password;
	private String _BackupPaths;
	private String _Bucket;
	private int _BackupProfileIndex;
	
	public String get_Username()
	{
		return _Username;
	}
	
	public void set_Username(String _Username)
	{
		this._Username = _Username;
	}
	
	public String get_Password()
	{
		return _Password;
	}
	
	public void set_Password(String _Password)
	{
		this._Password = _Password;
	}

	public String get_BackupPaths()
	{
		return _BackupPaths;
	}

	public void set_BackupPaths(String _BackupPaths)
	{
		this._BackupPaths = _BackupPaths;
	}

	public String get_Bucket()
	{
		return _Bucket;
	}

	public void set_Bucket(String _Bucket)
	{
		this._Bucket = _Bucket;
	}

	public int get_BackupProfileIndex()
	{
		return _BackupProfileIndex;
	}

	public void set_BackupProfileIndex(int _BackupProfileIndex)
	{
		this._BackupProfileIndex = _BackupProfileIndex;
	}
	
}
