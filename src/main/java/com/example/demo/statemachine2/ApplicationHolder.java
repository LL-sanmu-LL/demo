package com.example.demo.statemachine2;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class ApplicationHolder implements ApplicationContextAware {

   private static ApplicationContext applicationContext;

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    ApplicationHolder.applicationContext = applicationContext;
  }
  public static ApplicationContext getApplicationContext() {
    return applicationContext;
  }
}
