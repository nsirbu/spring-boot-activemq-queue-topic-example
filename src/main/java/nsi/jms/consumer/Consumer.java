package nsi.jms.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class Consumer {

  private static final Logger LOGGER = LoggerFactory.getLogger(Consumer.class);

  @JmsListener(destination = "demo-single-msg-queue")
  public void consumeSingleMessageFromQueue(String message) {
    LOGGER.info("Message received from ActiveMQ queue: {}.", message);
  }

  @JmsListener(destination = "demo-multiple-msg-queue")
  public void consumeMultipleMessageFromQueue(String message) {
    LOGGER.info("Messages received from ActiveMQ queue: {}.", message);
  }

  @JmsListener(destination = "demo-topic")
  public void consumeMessageFromTopic(String message) {
    LOGGER.info("Message received from ActiveMQ topic: {}.", message);
  }
}
