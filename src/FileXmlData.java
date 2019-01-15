import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.File;
import java.text.SimpleDateFormat;

@XmlRootElement( name = "file" )
public class FileXmlData 
{
    
	@XmlAttribute( name = "name" )
    public String name;
	
	public int size;
    public HashString hash;
    public String url;
    public String lastModified;

    public FileXmlData() 
    {
    	//default constructor
    }

    public FileXmlData( File oFile, String prefixURLString, String localDirectoryURLString ) 
    {
    	size = ( int )oFile.length();
        name = oFile.getName();
        hash = new HashString( oFile );        
        url = prefixURLString + GetPath( oFile, localDirectoryURLString );
        lastModified = GetLastModifiedDate( oFile );
    }

	private String GetLastModifiedDate( File oFile )
	{
		SimpleDateFormat oSimpleDateFormat = new SimpleDateFormat( "MM/dd/yyyy HH:mm:ss" );
		
		return oSimpleDateFormat.format( oFile.lastModified() );
	}

	private String GetPath( File oFile, String localDirectoryURLString ) 
	{
		String pathString = oFile.getAbsolutePath().replace( localDirectoryURLString, "" );
        
        if( pathString.charAt( 0 ) == '\\' ) 
        {       	
        	pathString = pathString.substring( 1, pathString.length() );
        	pathString = pathString.replace( "\\", "/" );
        	//System.out.println(oPathString);
        }
        
        return pathString;
	}
}