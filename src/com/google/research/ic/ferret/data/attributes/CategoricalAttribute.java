package com.google.research.ic.ferret.data.attributes;

/**
 * TODO: Insert description here. (generated by marknewman)
 */
public class CategoricalAttribute implements Attribute {

  public static final String TYPE = "CATEGORICAL";
  
  private String key = null;
  private String value = null;
  private String type = TYPE;

  
  public CategoricalAttribute(String k, String v) {
    key = k;
    value = v;
  }
  
  @Override
  public String getKey() {
    return key;
  }
  
  @Override
  public String getValue() {
    return value;
  }
  
  public void setValue(String v) {
    value = v;
  }

  public String getType() {
    return type;
  }
}
