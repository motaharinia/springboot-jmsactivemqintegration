package com.motaharinia.jmsactivemqintegration;

import java.util.concurrent.CountDownLatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;

public class CountDownLatchHandler implements MessageHandler {

  private static final Logger LOGGER = LoggerFactory.getLogger(CountDownLatchHandler.class);

  private CountDownLatch latch = new CountDownLatch(10);

  public CountDownLatch getLatch() {
    return latch;
  }

  @Override
  public void handleMessage(Message<?> message) {
    LOGGER.info("received message='{}'", message);
    latch.countDown();
  }
}
