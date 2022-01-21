package nsi.jms.config;

import javax.jms.Queue;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;

@EnableJms
@Configuration
public class SpringActiveMQConfig {

  @Value("${activemq.broker.url}")
  private String brokerUrl;

  @Bean(name = "singleMsgQueue")
  public Queue singleMessageQueue() {
    return new ActiveMQQueue("demo-single-msg-queue");
  }

  @Bean(name = "multipleMsgQueue")
  public Queue multipleMessageQueue() {
    return new ActiveMQQueue("demo-multiple-msg-queue");
  }

  @Bean
  public Topic topic() {
    return new ActiveMQTopic("demo-topic");
  }

  @Bean
  public ActiveMQConnectionFactory activeMQConnectionFactory() {
    ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory();
    activeMQConnectionFactory.setBrokerURL(brokerUrl);
    return activeMQConnectionFactory;
  }

  @Bean
  public JmsTemplate jmsTemplate() {
    JmsTemplate template = new JmsTemplate();
    template.setConnectionFactory(activeMQConnectionFactory());
//    template.setPubSubDomain(true);  // Uncomment to transform it to a Topic.
    return template;
  }

  @Bean
  public DefaultJmsListenerContainerFactory jmsListenerContainerFactory() {
    DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
    factory.setConnectionFactory(activeMQConnectionFactory());
    factory.setConcurrency("1-1");
//    factory.setPubSubDomain(true);   // Uncomment to transform it to a Topic.
    return factory;
  }
}
