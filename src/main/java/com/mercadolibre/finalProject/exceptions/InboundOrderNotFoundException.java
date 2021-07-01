package com.mercadolibre.finalProject.exceptions;

public class InboundOrderNotFoundException extends Exception {
    public InboundOrderNotFoundException() {
        super("Inbound Order Not Found");
    }
}
