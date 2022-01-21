package nsi.jms.dto;

import java.io.Serializable;

public class Student implements Serializable {
  private static final long serialVersionUID = 1L;
  private String studentId;
  private String name;

  public Student(String studentId, String name) {
    this.studentId = studentId;
    this.name = name;
  }

  public String getStudentId() {
    return studentId;
  }

  public void setStudentId(String studentId) {
    this.studentId = studentId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
