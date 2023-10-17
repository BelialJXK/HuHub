package hu.helper.bang.center.common.exception;

import lombok.Data;

/**
 * @Author : Luo Siwei
 * @Date : 2023/1/27 21:02
 * @Description : 帮帮网业务异常
 */
@Data
public class BangException extends RuntimeException {
    private String msg;
    private int code = 500;

    public BangException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public BangException(BangErrorCodes code) {
        super(code.getDesc());
        this.msg = code.getDesc();
    }

    public BangException(String msg, Throwable e) {
        super(msg, e);
        this.msg = msg;
    }

    public BangException(String msg, int code) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    public BangException(String msg, int code, Throwable e) {
        super(msg, e);
        this.msg = msg;
        this.code = code;
    }
}
