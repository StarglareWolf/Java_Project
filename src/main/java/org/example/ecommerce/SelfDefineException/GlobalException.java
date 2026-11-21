package org.example.ecommerce.SelfDefineException;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalException {

    private static final Logger logger = LoggerFactory.getLogger(GlobalException.class);

    // 1. 抓捕我们自定义的“业务异常”（用户操作错误）
    @ExceptionHandler(InformationException.class)
    public Map<String, Object> handleInformationException(InformationException e) {
        Map<String, Object> result = new HashMap<>();
        result.put("msg", e.getMessage());
        // 业务异常通常不需要打印堆栈，记录一句 warn 即可
        logger.warn("业务异常拦截：{}", e.getMessage());
        return result;
    }

    // 2. 抓捕所有其他“未知系统异常”
    @ExceptionHandler(Exception.class)
    public Map<String, Object> handleException(Exception e) {

        logger.error("系统发生未知异常：", e);

        Map<String, Object> result = new HashMap<>();
        result.put("code", 500);
        // 给前端返回安全的页面说明
        result.put("msg", "系统开小差了，请稍后再试。");
        return result;
    }
}