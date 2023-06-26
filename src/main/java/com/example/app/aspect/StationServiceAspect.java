package com.example.app.aspect;

import org.apache.kafka.common.protocol.types.Field;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class StationServiceAspect {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    Logger logger = LoggerFactory.getLogger(StationServiceAspect.class);

    @Pointcut("execution(* com.example.app.service.StationService.*(..)) ")
    private void anyStationService() {}

    @Before("anyStationService()")
    public void beforeAdvice(JoinPoint joinPoint)
    {
        String msg = "Start operation: " + joinPoint.getSignature();
        logger.info(msg);
        kafkaTemplate.send("topic1", msg);
    }

    @After("anyStationService()")
    public void afterAdvice(JoinPoint joinPoint)
    {
        String msg = "End operation: " + joinPoint.getSignature();
        logger.info(msg);
        kafkaTemplate.send("topic1", msg);
    }
}
