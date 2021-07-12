package com.mercadolibre.finalProject.exceptions;

import java.util.List;

public class TransferStockException extends ListException {
    public TransferStockException( List<SubError> subErrors) {
        super("Error in transfer "+ subErrors.size()+ " batches",subErrors);
    }
}
