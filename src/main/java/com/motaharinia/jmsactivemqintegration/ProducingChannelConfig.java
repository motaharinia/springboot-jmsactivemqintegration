package com.motaharinia.jmsactivemqintegration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.jms.JmsSendingMessageHandler;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.MessageHandler;

/**
 * User: https://github.com/motaharinia<br>
 * Date: 2020-06-12<br>
 * Time: 01:05:58<br>
 * Description:<br>
 *تنظیمات کانال ارسال کننده پیامها
 *
 */
@Configuration
public class ProducingChannelConfig {

  /**
   * با استفاده از انوتیشن Value ، نام مقصد را از پرونده application.properties در مسیر src / main / منابع بارگذاری می کنیم. <br>
   * سپس آن را با استفاده از setDestinationName تنظیم می کنیم
   */
  @Value("${destination.integration}")
  private String integrationDestination;

  /**
   * متد ProducingChannel را به عنوان یک بین DirectChannel تعریف میکنیم. <br>
   * این کانال پیش فرض ارائه شده توسط فریمورک اسپرینگ است ، اما می توانید از هرکدام از کانالهای پیام که اسپرینگ فراهم می کند را استفاده کنید <br>
   * https://docs.spring.io/spring-integration/docs/5.1.5.RELEASE/reference/html/#messaging-channels-section
   * @return خروجی: کانال ارسال کننده پیام
   */
  @Bean
  public DirectChannel producingChannel() {
    return new DirectChannel();
  }

  /**
   * ما JmsSendingMessageHandler را ایجاد می کنیم که پیام های دریافت شده از ProducingChannel را به یک مقصد ارسال می کند. <br>
   * هندلر JmsSendingMessageHandler با استفاده از انوتیشن ServiceActivator به ProducingChannel وصل شده است. <br>
   *  به عنوان inputChannel برای ایجاد پیوند باید به عنوان یک جفت کلید / مقدار ، productChannel را به عنوان مقدار تنظیم کنیم
   * @param jmsTemplate  ما به سادگی الگوی JmsTemplate را که بصورت خودکار توسط Spring Boot تنظیم شده است ، تزریق می کنیم
   * @return خروجی: هندلر پیام
   */
  @Bean
  @ServiceActivator(inputChannel = "producingChannel")
  public MessageHandler jmsMessageHandler(JmsTemplate jmsTemplate) {
    //سازنده به JmsTemplate نیاز دارد تا به عنوان پارامتر منتقل شود.
    JmsSendingMessageHandler handler = new JmsSendingMessageHandler(jmsTemplate);
    handler.setDestinationName(integrationDestination);
    return handler;
  }
}
