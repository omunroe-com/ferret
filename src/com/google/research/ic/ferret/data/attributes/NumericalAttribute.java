package com.google.research.ic.ferret.data.attributes;

/**
 * TODO: Insert description here. (generated by marknewman)
 */
public class NumericalAttribute implements Attribute {

  public static final String TYPE = "NUMERICAL";
  
  private String key = null;
  private Double value = null;
  private String type = TYPE;

  public NumericalAttribute(String key, Double value) {
    this.key = key;
    this.value = value;
  }
  
  @Override
  public String getKey() {
    return key;
  }

  @Override
  public Double getValue() {
    return value;
  }

  @Override
  public String getType() {
    return type;
  }

}
