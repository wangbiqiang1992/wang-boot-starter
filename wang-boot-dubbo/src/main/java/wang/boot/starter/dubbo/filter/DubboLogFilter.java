package wang.boot.starter.dubbo.filter;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;

import com.alibaba.dubbo.rpc.Filter;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.Result;
import com.alibaba.dubbo.rpc.RpcException;
import com.alibaba.dubbo.rpc.service.GenericService;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import wang.boot.starter.dubbo.filter.enums.LogMethod;
import wang.boot.starter.dubbo.filter.enums.NoLogMethod;
import wang.boot.starter.dubbo.filter.model.DubboFilterModel;

import java.lang.reflect.Method;

@Slf4j
@Activate(group = Constants.PROVIDER)
public class DubboLogFilter implements Filter {

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        try {
            Method method = invoker.getInterface()
                    .getDeclaredMethod(invocation.getMethodName(), invocation.getParameterTypes());
            LogMethod logMethod = method.getAnnotation(LogMethod.class);
            NoLogMethod noLogMethod = method.getAnnotation(NoLogMethod.class);
            if (logMethod == null || noLogMethod != null) {
                return invoker.invoke(invocation);
            }
            DubboFilterModel filterModel = new DubboFilterModel();
            filterModel.setInterfaceName(invoker.getInterface().getName());
            filterModel.setMethodName(invocation.getMethodName());
            filterModel.setArgs(invocation.getArguments());
            log.info("dubbo请求参数:[{}]", JSON.toJSONString(filterModel));
            Result result = invoker.invoke(invocation);
            if (result.hasException() && invoker.getInterface() != GenericService.class) {
                log.error("dubbo执行异常:[{}]", result.getException());
            } else {
                filterModel.setArgs(null);
                filterModel.setReturnResult(result.getValue());
                log.info("dubbo返回数据:[{}]", JSON.toJSONString(filterModel));
            }
            return result;
        } catch (Exception e) {
            log.error("dubbo调用异常,service:{},method:{},exception:{}", invoker.getInterface(), invocation.getMethodName(), e);
            throw new RpcException(e.getMessage(),e.getCause());
        }
    }
}
