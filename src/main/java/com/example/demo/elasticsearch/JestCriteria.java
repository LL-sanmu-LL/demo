package com.example.demo.elasticsearch;

public class JestCriteria {

  private String fieldName;
  private Object fieldValue;

  public JestCriteria(String fieldName, Object fieldValue) {
    this.fieldName = fieldName;
    this.fieldValue = fieldValue;
  }

  public String getFieldName() {
    return fieldName;
  }

  public Object getFieldValue() {
    return fieldValue;
  }
}
