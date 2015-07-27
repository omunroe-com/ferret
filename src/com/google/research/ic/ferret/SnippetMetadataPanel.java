package com.google.research.ic.ferret;

import com.google.research.ic.ferret.data.Snippet;
import com.google.research.ic.ferret.data.SubSequence;
import com.google.research.ic.ferret.data.attributes.Attribute;
import com.google.research.ic.ferret.data.attributes.AttributeManager;
import com.google.research.ic.ferret.test.Debug;

import java.awt.Color;
import java.awt.Dimension;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * TODO: Insert description here. (generated by marknewman)
 */
public class SnippetMetadataPanel extends JPanel implements SessionListener {
  
  private Snippet subSnippet = null;
  private SubSequence subSequence = null;
  
  
  public SnippetMetadataPanel(SubSequence subS) {
    subSequence = subS;
    subSnippet = subS.getSubSnippet();
    Debug.log("Got sub snippet, and it's got " + subSnippet.getEvents().size()
        + " events");
    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    updateAttributeLabels(Session.getCurrentSession().getAttributesToShow());
    setBackground(Color.white);
    setOpaque(true);
  }
    
  public void sessionUpdated(Session s) {
    updateAttributeLabels(s.getAttributesToShow());
  }
  
  private void updateAttributeLabels(List<String> attributesToShow) {
    this.removeAll(); // gonna create all new labels now
    
    String displayString = null;
    String prettyDisplayString = null;
    ImageIcon displayIcon = null;
    
    for (String attrKey: attributesToShow) {
      Attribute snipAttr = subSnippet.getAttribute(attrKey);
      String kName = AttributeManager.getManager().getKeyDisplayString(snipAttr);
      String vName = AttributeManager.getManager().getValueDisplayString(snipAttr);
      displayString = kName + ":" + vName;
      prettyDisplayString = "<html>" + 
          "<div style=\"text-align:left;font-size:xx-small\">" +
          displayString + "</div></html>";
      JLabel propLabel = new JLabel(prettyDisplayString);      
      add(propLabel);
    }

    if (subSequence != null) {
      prettyDisplayString = "<html>" + 
          "<div style=\"text-align:left;font-size:xx-small\">" +
          subSequence.getDistance() +"(" + subSequence.getStartIndex() + "-"
          + subSequence.getEndIndex() + ")" + "</div></html>";
      JLabel distInfoLabel = new JLabel(prettyDisplayString);
      add(distInfoLabel);
    }
    add(Box.createRigidArea(new Dimension(10,40)));
  }  
}
