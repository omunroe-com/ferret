package com.google.research.ic.ferret.data.ext.reflection;

import com.google.research.ic.ferret.data.Event;
import com.google.research.ic.ferret.data.SearchEngine;
import com.google.research.ic.ferret.test.Debug;
import com.google.research.reflection.data.Observation;

/**
 * TODO: Insert description here. (generated by liyang)
 */
public class ReflectionEvent extends Event {
  
  String identifier;
  Observation observation;
  int identifierId;
  
  public ReflectionEvent(Observation observation) {
    this.observation = observation;
    setTimeStamp(observation.getTime());
    setUserId(observation.getDevice());
    setDeviceId(observation.getDevice());
    setModuleName(observation.appName);
    if (observation.getEventName().contains("/com.android.phone/incoming/")) {
      setEventTypeName("Incoming call");
      identifier = "incoming_call";
    } else if (observation.getEventName().contains("/com.android.phone/outgoing/")) {
      setEventTypeName("Outgoing call");
      identifier = "outgoing_call";
    } else {
      if (observation.getEventName().startsWith("/reflection/")) {
        String type = observation.getEventName().split("/")[2];
        setEventTypeName(type);
        identifier = type;
      } else {
        if (observation.isHomeScreen) {
          setEventTypeName("Launcher");
          identifier = "launcher";
        } else {
          setEventTypeName("App usage");
          identifier = observation.getEventName().split("/")[1];
        }
      }
    }
    identifierId = SearchEngine.getSearchEngine().retrieveOrRegisterIdentifier(identifier);
    init();
  }
  
  @Override
  public String getIdentifier() {
    return identifier;
  }
  
  @Override
  public void init() {
    if (observation.isHomeScreen()) {
      displayTitle = "Show";
      displayEvent = "Home Screen";
      displayExtra = observation.getPackageName();
    } else if (observation.getEventName().startsWith("/reflection/")) {
      displayTitle = "System";
      displayEvent = getEventTypeName();
      displayExtra = "(reflection)";
    } else {
      String appName = observation.getAppName();
      displayTitle = "Launch";
      
      if (appName == null) {
        displayEvent = "Unknown";
      } else {
        displayEvent = observation.getAppName();      
      } 
      displayExtra = observation.getPackageName();
    }
//    displayEvent = observation.getEventName();
//    String[] packageParts = observation.getPackageName().split(".");
//    if (packageParts.length > 0) {
//      displayExtra = packageParts[packageParts.length - 1];
//    } else {
//      displayExtra = observation.getPackageName();
//    }
  }

  /* (non-Javadoc)
   * @see com.google.research.ic.qbdila.data.Event#getDisplayTitle()
   */
  @Override
  public String getDisplayTitle() {
    return displayTitle;
  }

  /* (non-Javadoc)
   * @see com.google.research.ic.qbdila.data.Event#getDisplayEvent()
   */
  @Override
  public String getDisplayEvent() {
    return displayEvent;
  }

  /* (non-Javadoc)
   * @see com.google.research.ic.qbdila.data.Event#getDisplayExtra()
   */
  @Override
  public String getDisplayExtra() {
    return displayExtra;
  }

  /**
   * @return
   */
  public Observation getObservation() {
    return observation;
  }
}
