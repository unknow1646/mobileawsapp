package com.example.mobilewebws.servicebean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BeanBImpl implements BeanService{

  Logger logger = LoggerFactory.getLogger(BeanBImpl.class);

  @Override
  public void startBean() {
    logger.info(" Starting Bean B");

  }

  @Override
  public void stopBean() {
    logger.info(" Stopping Bean B");

  }
}
