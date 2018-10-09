import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.riot.RDFFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.topbraid.shacl.validation.ValidationUtil;
import org.topbraid.shacl.vocabulary.SH;
import org.topbraid.spin.util.JenaUtil;

import java.io.ByteArrayInputStream;
import java.io.StringWriter;

public class ShaclValidation {
    private static Logger logger = LoggerFactory.getLogger(ShaclValidation.class);
    private static final String CONFORMS  = "CONFORMS";

    public String validateData(String data, String dataFormat, String shape, String shapeFormat){
        Model dataModel = JenaUtil.createDefaultModel();
        dataModel.read(new ByteArrayInputStream(data.getBytes()), null, dataFormat);
        Model shapeModel  = JenaUtil.createDefaultModel();
        shapeModel.read(new ByteArrayInputStream(shape.getBytes()), null, shapeFormat);
        Resource reportResource = ValidationUtil.validateModel(dataModel, shapeModel, true);
        boolean conforms = reportResource.getProperty(SH.conforms).getBoolean();
        logger.trace("Conforms = " + conforms);
        if(!conforms){
            StringWriter stringWriter = new StringWriter();
            RDFDataMgr.write(stringWriter, reportResource.getModel(), RDFFormat.TTL);
            return stringWriter.toString();
        }
        return CONFORMS;
    } 
}
