package com.jcca.common.config.mybits;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 *
 */
@Aspect
@Component
@Slf4j
public class DataSourceAop {

    /**
     * 使用从库查询
     */
    @Pointcut("@annotation(com.jcca.common.config.mybits.WorkSlave) " +
            "|| execution(* com.jcca.logic.service.*.*.select*(..)) " +
            "|| execution(* com.jcca.logic.service.*.*.list*(..)) " +
            "|| execution(* com.jcca.logic.service.*.*.query*(..)) " +
            "|| execution(* com.jcca.logic.service.*.*.find*(..)) " +
            "|| execution(* com.jcca.logic.service.*.*.get*(..))"+
            				
            "|| execution(* com.jcca.data.dao.*.*.*.select*(..)) " +
            "|| execution(* com.jcca.data.dao.*.*.*.list*(..)) " +
            "|| execution(* com.jcca.data.dao.*.*.*.query*(..)) " +
            "|| execution(* com.jcca.data.dao.*.*.*.find*(..)) " +
            "|| execution(* com.jcca.data.dao.*.*.*.get*(..))")
    public void readPointcut() {
        log.info("readPointcut");
    }

    /**
     * 使用主库插入更新
     */
    @Pointcut("@annotation(com.jcca.common.config.mybits.WorkMaster) " +
            "|| execution(* com.jcca.logic.service.*.*.login(..)) " +
            "|| execution(* com.jcca.logic.service.*.*.save*(..)) " +
            "|| execution(* com.jcca.logic.service.*.*.add*(..)) " +
            "|| execution(* com.jcca.logic.service.*.*.update*(..)) " +
            "|| execution(* com.jcca.logic.service.*.*.edit*(..)) " +
            "|| execution(* com.jcca.logic.service.*.*.delete*(..)) " +
            "|| execution(* com.jcca.logic.service.*.*.remove*(..))"+

            "|| execution(* com.jcca.data.dao.*.*.*.login(..)) " +
            "|| execution(* com.jcca.data.dao.*.*.*.insert*(..)) " +
            "|| execution(* com.jcca.data.dao.*.*.*.add*(..)) " +
            "|| execution(* com.jcca.data.dao.*.*.*.update*(..)) " +
            "|| execution(* com.jcca.data.dao.*.*.*.edit*(..)) " +
            "|| execution(* com.jcca.data.dao.*.*.*.delete*(..)) " +
            "|| execution(* com.jcca.data.dao.*.*.*.remove*(..))")
    public void writePointcut() {
        log.info("writePointcut");
    }

    @Before("readPointcut()")
    public void read() {
        DBContextHolder.workSlave();
    }

    @Before("writePointcut()")
    public void write() {
        DBContextHolder.workMaster();
    }

}
