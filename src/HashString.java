import java.security.MessageDigest;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.bind.annotation.XmlAttribute;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.File;

@XmlRootElement
public class HashString {
    
	@XmlAttribute( name = "type" )
    public String _typeString = "MD5";
    
    @XmlValue
    public String _hashString;

    public HashString()
    {
    	//default constructor
    }

    public HashString( File oFile ) 
    {
        try 
        {
            _hashString = getMD5Checksum( 
            		MessageDigest.getInstance( _typeString ), oFile );
        } 
        catch( IOException e )
        {
        	System.out.println( "IOException: " + e.getMessage() );
        }
        catch( Exception e ) 
        {
            e.printStackTrace();
        }
    }

    private static String getMD5Checksum( MessageDigest oMessageDigest, File oFile ) throws IOException
    {
    	int iBytesCount = 0;
        byte[] aFileInputStreamByteArray = new byte[1024];
        byte[] aFileMessageDigestBytes = null;
        FileInputStream oFileInputStream = new FileInputStream( oFile );
        StringBuilder oDiggestStringBuilder = new StringBuilder();

        while( ( iBytesCount = oFileInputStream.read( aFileInputStreamByteArray ) ) != -1) 
        {
        	oMessageDigest.update( aFileInputStreamByteArray, 0, iBytesCount );
        }

        oFileInputStream.close();
        aFileMessageDigestBytes = oMessageDigest.digest();
        
        if( aFileMessageDigestBytes != null )
        {
            for( int iIterator = 0; iIterator < aFileMessageDigestBytes.length; iIterator++ )
            {
                oDiggestStringBuilder.append( Integer.toString( ( aFileMessageDigestBytes[iIterator] & 0xff ) 
                							   + 0x100, 16 ).substring( 1 ) );
            }	
        }

        return oDiggestStringBuilder.toString();
    }
}