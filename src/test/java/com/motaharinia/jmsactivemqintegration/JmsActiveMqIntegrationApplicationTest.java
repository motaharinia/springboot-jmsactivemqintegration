package com.motaharinia.jmsactivemqintegration;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.jms.support.JmsHeaders;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.junit.jupiter.api.*;


/**
 * User: https://github.com/motaharinia<br>
 * Date: 2020-06-12<br>
 * Time: 01:05:58<br>
 * Description:<br>
 *  کلاس تست ارسال و دریافت پیام از طریق Spring Integration و JMS و ActiveMQ
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("dev")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext
public class JmsActiveMqIntegrationApplicationTest {

  @Value("${destination.integration}")
  private String integrationDestination;

  @Autowired
  private ApplicationContext applicationContext;

  @Autowired
  private CountDownLatchHandler countDownLatchHandler;

  @Test
  public void testIntegration() throws Exception {
    MessageChannel producingChannel = applicationContext.getBean("producingChannel", MessageChannel.class);

    Map<String, Object> headers = Collections.singletonMap(JmsHeaders.DESTINATION, integrationDestination);

    System.out.println("sending 10 messages");
    for (int i = 0; i < 10; i++) {
      GenericMessage<String> message = new GenericMessage<>("Hello Spring Integration JMS " + i + "!", headers);
      producingChannel.send(message);
      System.out.println("sent message="+ message);
    }

    countDownLatchHandler.getLatch().await(10000, TimeUnit.MILLISECONDS);
    assertThat(countDownLatchHandler.getLatch().getCount()).isEqualTo(0);
  }
}
