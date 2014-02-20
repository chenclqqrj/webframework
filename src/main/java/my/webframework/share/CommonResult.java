package my.webframework.share;

/**
 * 用于Web交互通用返回结果，便于统一使用JSON。
 */
public class CommonResult {
    private Object result;

    public CommonResult(Object result) {
        this.result = result;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
