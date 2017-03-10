package com.ramtech.verify;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by rmogasale on 3/9/2017.
 */
public class LogHandler {

    Logger LOGGER = LoggerFactory.getLogger(LogHandler.class);

    public void handleLogging () {
        LOGGER.info("Info log here");
        LOGGER.error("Error log here");
        LOGGER.debug("Debug log here");
    }
}
