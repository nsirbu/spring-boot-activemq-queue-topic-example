package nsi.jms.producer;

import javax.jms.Queue;
import javax.jms.Topic;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import nsi.jms.dto.Student;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/produce")
public class Producer {

  private static final Logger logger = LoggerFactory.getLogger(Producer.class);

  private final JmsTemplate jmsTemplate;
  private final Queue singleQueue;
  private final Queue multipleQueue;
  private final Topic topic;
  private final ObjectMapper mapper;

  public Producer(JmsTemplate jmsTemplate,
                  @Qualifier("singleMsgQueue") Queue singleQueue,
                  @Qualifier("multipleMsgQueue") Queue multipleQueue,
                  Topic topic) {
    this.jmsTemplate = jmsTemplate;
    this.singleQueue = singleQueue;
    this.multipleQueue = multipleQueue;
    this.topic = topic;
    this.mapper = new ObjectMapper();
  }

  @PostMapping("/singleMessageToQueue")
  public void sendSingleMessageToQueue() throws JsonProcessingException {
    List<Student> students = buildMessagesToSend();

    logger.info("Start sending...");
    long start = System.currentTimeMillis();
    for (Student student : students) {
      String studentAsJson = mapper.writeValueAsString(student);
      jmsTemplate.convertAndSend(singleQueue, studentAsJson);
    }
    long finish = System.currentTimeMillis();
    long timeElapsed = finish - start;
    logger.info("Done sending! {}", timeElapsed);
  }

  @PostMapping("/multipleMessageToQueue")
  public void sendMultipleMessageToQueue() throws JsonProcessingException {
    List<Student> students = buildMessagesToSend();

    logger.info("Start sending...");
    long start = System.currentTimeMillis();
    String studentAsJson = mapper.writeValueAsString(students);
    jmsTemplate.convertAndSend(multipleQueue, studentAsJson);
    long finish = System.currentTimeMillis();
    long timeElapsed = finish - start;
    logger.info("Done sending! {}", timeElapsed);
  }

  @PostMapping("/messageToTopic")
  public void sendMessageToTopic(@RequestBody Student student) throws JsonProcessingException {
    String studentAsJson = mapper.writeValueAsString(student);
    jmsTemplate.convertAndSend(topic, studentAsJson);
  }

  private List<Student> buildMessagesToSend() {
    List<Student> students = new ArrayList<>();
    for (int i = 0; i < 100; i++) {
      students.add(new Student("" + i, "Tom"));
    }

    return students;
  }
}
