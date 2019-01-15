import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@XmlRootElement( name = "metalink" )
public class MetalinkFile 
{
    public Date published = new Date();
    public List<FileXmlData> file = new ArrayList<FileXmlData>();

    public void add( FileXmlData oFileXmlData ) 
    {
    	file.add( oFileXmlData );
    }
}