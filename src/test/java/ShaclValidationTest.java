import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.Scanner;

public class ShaclValidationTest {
    @Test
    public void testShaclValidation(){
        ClassLoader classLoader = getClass().getClassLoader();
        String data = getFile("person.ttl");
        String shapes = getFile("personshape.ttl");
        ShaclValidation shaclValidation = new ShaclValidation();
        String results = shaclValidation.validateData(data, shapes);
        System.out.println(results);
    }

    private String getFile(String fileName){
        String result = "";
        ClassLoader classLoader = getClass().getClassLoader();
        try
        {
            result = IOUtils.toString(classLoader.getResourceAsStream(fileName), Charset.defaultCharset());
        }catch (IOException e){
            e.printStackTrace();
        }
        return result;
    }
}
