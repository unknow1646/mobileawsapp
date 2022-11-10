package com.example.mobilewebws.servicebean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BeanAImpl implements BeanService{

  private Logger logger =  LoggerFactory.getLogger(BeanAImpl.class);

  @Override
  public void startBean() {
    logger.info(" Starting Bean A ... ");

  }

  @Override
  public void stopBean() {
    logger.info(" Stopping Bean A ... ");

  }
}
