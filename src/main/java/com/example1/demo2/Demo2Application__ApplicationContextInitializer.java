package com.example1.demo2;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportRuntimeHints;

@ImportRuntimeHints({TomcatWebRuntimeHints.class})
@Configuration
public class Demo2Application__ApplicationContextInitializer implements ApplicationContextInitializer {

    @Override
    public void initialize(ConfigurableApplicationContext ctx) {
        ctx.addBeanFactoryPostProcessor(new CustBeanRegistrationsAotProcessor(Demo2Application.class));
    }
}
