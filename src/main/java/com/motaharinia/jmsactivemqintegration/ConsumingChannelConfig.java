package com.motaharinia.jmsactivemqintegration;

import javax.jms.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.jms.ChannelPublishingJmsMessageListener;
import org.springframework.integration.jms.JmsMessageDrivenEndpoint;
import org.springframework.jms.listener.SimpleMessageListenerContainer;

/**
 * User: https://github.com/motaharinia<br>
 * Date: 2020-06-12<br>
 * Time: 01:05:58<br>
 * Description:<br>
 *تنظیمات کانال دریافت کننده پیامها
 *
 */
@Configuration
public class ConsumingChannelConfig {

  /**
   * با استفاده از انوتیشن Value ، نام مقصد را از پرونده application.properties در مسیر src / main / منابع بارگذاری می کنیم. <br>
   * سپس آن را با استفاده از setDestinationName تنظیم می کنیم
   */
  @Value("${destination.integration}")
  private String integrationDestination;

  /**
   * متد consumingChannel را به عنوان یک بین DirectChannel تعریف میکنیم. <br>
   * این کانال پیش فرض ارائه شده توسط فریمورک اسپرینگ است ، اما می توانید از هرکدام از کانالهای پیام که اسپرینگ فراهم می کند را استفاده کنید <br>
   * https://docs.spring.io/spring-integration/docs/5.1.5.RELEASE/reference/html/#messaging-channels-section
   * @return خروجی: کانال دریافت کننده پیام
   */
  @Bean
  public DirectChannel consumingChannel() {
    return new DirectChannel();
  }

  /**
   * یک نقظه پایانی JmsMessageDrivenEndpoint ایجاد میکنیم که بتواند پیامهای JMS را دریافت کند.
   * @param connectionFactory
   * @return
   */
  @Bean
  public JmsMessageDrivenEndpoint jmsMessageDrivenEndpoint(ConnectionFactory connectionFactory) {
    // سازنده یک پیام MessageListenerContainer و ChannelPublishingJmsMessageListener را به عنوان پارامترهای ورودی در نظر می گیرد.
    JmsMessageDrivenEndpoint jmsMessageDrivenEndpoint = new JmsMessageDrivenEndpoint(simpleMessageListenerContainer(connectionFactory), channelPublishingJmsMessageListener());
    //ما با استفاده از متد setOutputChannel نقطه پایانی را به ConsumingChannel وصل می کنیم.
    jmsMessageDrivenEndpoint.setOutputChannel(consumingChannel());
    return jmsMessageDrivenEndpoint;
  }

  @Bean
  public SimpleMessageListenerContainer simpleMessageListenerContainer(ConnectionFactory connectionFactory) {
    SimpleMessageListenerContainer simpleMessageListenerContainer = new SimpleMessageListenerContainer();
    simpleMessageListenerContainer.setConnectionFactory(connectionFactory);
    simpleMessageListenerContainer.setDestinationName(integrationDestination);
    return simpleMessageListenerContainer;
  }

  /**
   * کلاس ChannelPublishingJmsMessageListener شنونده ای ایجاد می کند که یک پیام JMS را به یک پیام Spring Integration تبدیل می کند و آن پیام را به یک کانال می فرستد.
   * @return
   */
  @Bean
  public ChannelPublishingJmsMessageListener channelPublishingJmsMessageListener() {
    return new ChannelPublishingJmsMessageListener();
  }

  /**
   * برای آزمایش راه اندازی ما ، یک لوبیای CountDownLatchHandler مشخص شده است که با استفاده از انوتیشن ServiceActivator به ConsumingChannel مرتبط است.<br>
   * کلاس CountDownLatchHandler به ما امکان می دهد صحت کار دو کانال متصل خود را تأیید کنیم.
   *  متد handMessage رابط MessageHandler را پیاده سازی می کند.
   * پیام های ConsumingChannel پیوست شده ثبت می شوند و CountDownLatch در هر پیام کاهش می یابد.
   * @return
   */
  @Bean
  @ServiceActivator(inputChannel = "consumingChannel")
  public CountDownLatchHandler countDownLatchHandler() {
    return new CountDownLatchHandler();
  }
}
