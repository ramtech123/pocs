package com.ramtech.lwj;

import com.ramtech.lwj.pojo.Configuration;
import org.jibx.runtime.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Created by rmogasale on 4/3/2017.
 */
public class LogbackWithJibxRunner {

    /**
     * Run with 'config/test-sample.xml' as program argument.
     * @param args
     * @throws JiBXException
     * @throws FileNotFoundException
     */
    public static void main(String[] args) throws JiBXException, FileNotFoundException {
        IBindingFactory bfact = BindingDirectory.getFactory(Configuration.class);
        IUnmarshallingContext uctx = bfact.createUnmarshallingContext();
        String xmlFilePath = args[0];
        FileInputStream in = new FileInputStream(xmlFilePath);
        Configuration configuration = (Configuration)uctx.unmarshalDocument(in, null);
        System.out.println(configuration);
        IMarshallingContext mctx = bfact.createMarshallingContext();
        mctx.setIndent(4);
        mctx.marshalDocument(configuration, null, null, System.out);
    }
}
