package com.ramtech.logback.jaxb;

import com.ramtech.logback.jaxb.pojo.Configuration;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by rmogasale on 4/4/2017.
 */
public class LogbackWithJaxbRunner {

    /**
     * Run it with "config/logback.xml" as program argument.
     * @param args
     * @throws JAXBException
     * @throws IOException
     */
    public static void main(String[] args) throws JAXBException, IOException {
        JAXBContext context = JAXBContext.newInstance(Configuration.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        String xmlFilePath = args[0];
        FileInputStream in = new FileInputStream(xmlFilePath);
        Configuration config = (Configuration) unmarshaller.unmarshal(in);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(config, System.out);
    }
}
