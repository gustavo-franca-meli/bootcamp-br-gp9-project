package com.mercadolibre.finalProject.exceptions;

public class TransferBatchException extends SubError{
    public TransferBatchException(String batchId,String message) {
        super("Transfer Batch "+ batchId + " error: " + message);
    }
}
