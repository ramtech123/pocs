package com.ramtech.logback.jaxb;

import com.ramtech.logback.jaxb.pojo.Configuration;
import com.ramtech.logback.jaxb.pojo.LoggerConfig;

import javax.xml.bind.*;
import javax.xml.bind.util.ValidationEventCollector;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
        Marshaller marshaller = context.createMarshaller();
        ValidationEventCollector eventHandler = new ValidationEventCollector();
        unmarshaller.setEventHandler(eventHandler);
        int i = 0;
        while (i < 1){
            ValidationEvent[] parseErrors = tryUnmarshal(unmarshaller, args[0], marshaller);
            System.out.println("\n************\n");
            int count = 1;
            for (ValidationEvent event : parseErrors) {
                System.out.println(count + ". " + event.getMessage() + "@" + event.getLocator().toString());
                //System.out.println(event.getLinkedException());
                count++;
            }
            System.out.println("\n************\n");
            eventHandler.reset();
            i++;
        }

    }

    private static ValidationEvent[] tryUnmarshal(Unmarshaller unmarshaller, String xmlFilePath, Marshaller marshaller) throws FileNotFoundException, JAXBException {
        FileInputStream in = new FileInputStream(xmlFilePath);
        Configuration config = (Configuration) unmarshaller.unmarshal(in);
        writeXML(config, marshaller);
        for (LoggerConfig logger :config.getLoggerConfigs()) {
            System.out.println(logger.getName() + " : " + logger.getResetTime() + "," + logger.getTimerStartTime());
        }
        return ((ValidationEventCollector)unmarshaller.getEventHandler()).getEvents();
    }

    private static void writeXML (Configuration config, Marshaller marshaller) throws JAXBException {
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(config, System.out);
    }


}
