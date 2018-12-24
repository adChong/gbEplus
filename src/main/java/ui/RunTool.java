package main.java.ui;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

import org.apache.commons.io.FilenameUtils;
import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import main.java.model.gbXML.ReverseTranslator;
import main.java.plugins.ashraeassumptions.ASHRAEConstructions;
import main.java.plugins.ashraeassumptions.ASHRAELightData;
import main.java.plugins.ashraeassumptions.ASHRAEOAData;
import main.java.plugins.ashraeassumptions.DOEReferenceEquipmentData;
import main.java.plugins.ashraebaseline.ASHRAEHVAC;

/**
 * Test code
 * @author weilixu
 *
 */
public class RunTool {
	   public static Logger logger = Logger.getLogger(RunTool.class.getName());
	   public static StringBuilder logMsg = new StringBuilder();
	   public static String TEST_XML_STRING = null;
	   public static int PRETTY_PRINT_INDENT_FACTOR = 4;

	   public static void main(String[] args) {	 
		   
//	       String gbXMLfilepath = args[0];  
//	       String OutputFilepath = args[1]; 
		   String gbXMLfilepath = "./resource/Ventus_Archi_updated materials.xml";
		   String OutputFilepath = "./output"; 
	       System.out.println("translating gbXML to IDF.....");
	       Path p = Paths.get(gbXMLfilepath);        
	       String OutputFileName =   FilenameUtils.getBaseName(p.getFileName().toString());           
	       exportIDF(gbXMLfilepath, OutputFilepath, OutputFileName);
	   }


	   public static void captureLog(String msg) {
	       logMsg.append(System.getProperty("line.separator"));
	       logMsg.append(msg);
	   }

	   public static int exportIDF(String gbxmlfilepath, String idfOutputpath, String outputfilename) {
	       SAXBuilder builder = new SAXBuilder();
	       try {

	           File xmlFile = new File(gbxmlfilepath);

	           Document doc;
	           doc = (Document) builder.build(xmlFile);

	           ReverseTranslator trans = new ReverseTranslator(doc, "8.6");
	           //register construction
	           trans.registerDataPlugins(new ASHRAEConstructions());
	           trans.registerDataPlugins(new ASHRAELightData("./resource/Sep_test_internalloads.xml"));
	           trans.registerDataPlugins(new ASHRAEOAData("./resource/Sep_test_internalloads.xml"));
	           trans.registerDataPlugins(new DOEReferenceEquipmentData("./resource/Sep_test_internalloads.xml"));
//	           trans.registerDataPlugins(new ASHRAEHVAC());
	           trans.convert();
	           trans.exportFile(idfOutputpath, outputfilename);
	           System.out.println("exported IDF to " + idfOutputpath +  outputfilename + ".idf"  );
	           
	       } catch (JDOMException e) {
//	            // TODO Auto-generated catch block
//	            e.printStackTrace();
//	            System.out.println(¡°Severe : ¡± + e.toString());


	       } catch (IOException e) {
//	            // TODO Auto-generated catch block
//	            e.printStackTrace();
//	            System.out.println(¡°Severe : ¡± + e.toString());
	       }

	       return 0;
	   }


	}
