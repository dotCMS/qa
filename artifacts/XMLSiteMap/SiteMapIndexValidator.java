import java.net.*;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.*;

import org.xml.sax.*;

public class SiteMapIndexValidator
{
	public static void main(String[] args) throws Exception {
		if(args.length != 1)
		{
			System.out.println("Usage:  'java SiteMapIndexValidator URLofSiteMapIndex (i.e. http://demo.dotcms.com:8080/sitemap_index.xml)'");
			System.exit(1);
		}

		URL schemaFile = new URL("http://www.sitemaps.org/schemas/sitemap/0.9/siteindex.xsd");
		Source xmlFile = new StreamSource(args[0]);
		SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		Schema schema = schemaFactory.newSchema(schemaFile);
		Validator validator = schema.newValidator();
		try {
		  validator.validate(xmlFile);
		  System.out.println(xmlFile.getSystemId() + " is valid");
		} catch (SAXException e) {
		  System.out.println(xmlFile.getSystemId() + " is NOT valid");
		  System.out.println("Reason: " + e.getLocalizedMessage());
		}
	}
}
