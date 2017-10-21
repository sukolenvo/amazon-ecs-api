package com.sukolenvo.amazon.advertising;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import java.io.ByteArrayOutputStream;
import java.util.Set;
import java.util.logging.Logger;

/**
 * This simple SOAPHandler will output the contents of incoming
 * and outgoing messages.
 */
public class SOAPLoggingHandler implements SOAPHandler<SOAPMessageContext> {

    private static final Logger logger = Logger.getLogger(SOAPLoggingHandler.class.getName());

    public Set<QName> getHeaders() {
        return null;
    }

    public boolean handleMessage(SOAPMessageContext smc) {
        logMessage(smc);
        return true;
    }

    private void logMessage(SOAPMessageContext smc) {
        SOAPMessage message = smc.getMessage();
        logger.fine(() -> messageToString(message));
    }

    private String messageToString(SOAPMessage message) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            message.writeTo(out);
        } catch (Exception e) {
            logger.warning("Cannot serialise SOAP message: " + e);
        }
        return new String(out.toByteArray());
    }

    public boolean handleFault(SOAPMessageContext smc) {
        logMessage(smc);
        return true;
    }

    // nothing to clean up
    public void close(MessageContext messageContext) {
    }

}

