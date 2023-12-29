package com.redstars.tdengine.core.exception;

/**
 * @author : zhouhx
 * 异常类
 * @date : 2023/6/12 11:32
 */
public class TdengineException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private String msg;

    public TdengineException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public TdengineException(String msg, Throwable e) {
        super(msg, e);
        this.msg = msg;
    }
    public TdengineException(Throwable e) {
        super(e);
        this.msg = e.getMessage();
    }
}
