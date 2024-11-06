package jpabook.jpashop.Exception;

public class NotEnoughStockExcepion extends RuntimeException{

    public NotEnoughStockExcepion() {
        super();
    }

    public NotEnoughStockExcepion(String message) {
        super(message);
    }

    public NotEnoughStockExcepion(String message, Throwable cause) {
        super(message, cause);
    }

    public NotEnoughStockExcepion(Throwable cause) {
        super(cause);
    }

    protected NotEnoughStockExcepion(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
