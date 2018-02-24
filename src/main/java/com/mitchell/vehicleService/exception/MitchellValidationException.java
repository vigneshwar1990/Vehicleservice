package com.mitchell.vehicleService.exception;

public class MitchellValidationException extends  RuntimeException{
    private static final long serialVersionUID = 1L;

    public MitchellValidationException() {
        super();
    }

    public MitchellValidationException(String pMessage,
                                        Throwable pNestedException) {
        super(pMessage, pNestedException);
    }

    public MitchellValidationException(Throwable pNestedException) {
        super(pNestedException);
    }

    public MitchellValidationException(String pMessage) {
        super(pMessage);
    }
}
