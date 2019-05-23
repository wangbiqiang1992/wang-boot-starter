package wang.boot.starter.dubbo.filter.model;

public class DubboFilterModel {

    private String interfaceName;

    private String methodName;

    private Object[] args;

    private Object returnResult;

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    public Object getReturnResult() {
        return returnResult;
    }

    public void setReturnResult(Object returnResult) {
        this.returnResult = returnResult;
    }

}
