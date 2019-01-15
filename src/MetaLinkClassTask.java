import org.apache.tools.ant.Task;
import org.apache.tools.ant.types.FileSet;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import java.io.File;
import java.util.ArrayList;

public class MetaLinkClassTask extends Task 
{
	private ArrayList<FileSet> _filesetsList = new ArrayList<FileSet>();
	private MetalinkFile _metaLinkFile = new MetalinkFile();
	private String _urlString;
	private String _fileString;
	private String _localDirectoryString;

	public void execute() 
	{
		CheckUrlValue();
		_localDirectoryString = getProject().getProperty( "user.dir" );
		PrintInfoAboutFile();
		AddFiles();
		SetXml();
	}

	public void addFileset( FileSet oFileset ) 
	{
		_filesetsList.add( oFileset );
	}

	public void setFile( String fileString ) 
	{
		_fileString = fileString;
	}

	public void setUrl( String urlString ) 
	{
		_urlString = urlString;
	}

	private void CheckUrlValue() 
	{
		if( _urlString == null ) 
		{
			_urlString = getDefaultUrl();
		}
	}

	private void PrintInfoAboutFile() 
	{
		System.out.println( "<------------------------->" );
		System.out.println( "URL: " + _urlString );
		System.out.println( "FILE: " + _fileString );
		System.out.println( _localDirectoryString );
		System.out.println( "<------------------------->" );
	}

	private void AddFiles( File oFile )
	{
		createFileInfo (oFile );

		if( oFile.isDirectory() )
			for( File oTempFile : oFile.listFiles() )
				AddFiles( oTempFile );
	}

	private String getDefaultUrl() 
	{
		return getProject().getProperty( "server.files.url" );
	}

	private void createFileInfo( File oFile ) 
	{
		FileXmlData oFileInfo = null;

		if( !oFile.isDirectory() ) 
		{
			oFileInfo = new FileXmlData( oFile, _urlString, _localDirectoryString );
			_metaLinkFile.add( oFileInfo );
		}
	}

	private void SetXml() 
	{
		
		Marshaller oMarshaller = null;

		try 
		{
			oMarshaller = JAXBContext.newInstance( MetalinkFile.class ).createMarshaller();
			oMarshaller.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, true );
			oMarshaller.marshal( _metaLinkFile, new File( _fileString ) );
		}
		catch( JAXBException exceptionJAXB ) 
		{
			System.out.println("JAXBException: " + exceptionJAXB.getMessage());
			exceptionJAXB.printStackTrace();
		} 
		catch( Exception anotherException )
		{
			System.out.println("Exception: " + anotherException.getMessage());
			anotherException.printStackTrace();
		}
	}

	private void AddFiles() 
	{
		for( Object oObject : _filesetsList) 
		{
			if( oObject instanceof FileSet ) 
			{
				AddFiles( ( ( FileSet ) oObject ).getDir() );
			}
		}
	}
}