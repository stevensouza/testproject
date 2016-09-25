/*

 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stevesouza.jaxrs_resteasy;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 *  In a real app this would be a jpa object.  note jaxb (to from xml) has builtin 
 * support in jax rs.  jaxb requires annotations (unlike json)

 * @author stevesouza
 */
@XmlRootElement(name="customer")
// this allows you to specify where xml elements will be by default taken.
// i.e. fields, get* methods, public members etc. Note it will create xmlElements
// not attributes.  I believe you still have to annotate the field or property with 
// the XmlAnnotation and XmlElement to have it work.
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Customer {
    
   //  @XmlAttribute would make it a property of the root element i.e. <customer fName="steve">
    @XmlElement
    //@XmlAttribute
    private String fName;
    
    @XmlElement
    //@XmlAttribute
    private String lName;
    
    // needed for jaxb i believe.
    public Customer() {
        
    }
    
    
    public Customer(String fName, String lName) {
        this.fName = fName;
        this.lName = lName;
    }
    

    // XmlAccessType.PROPERTIES means get* methods
    //@XmlElement
    //@XmlAttribute
    public String getfName() {
        return fName;
    }

    //@XmlElement
    //@XmlAttribute
    public String getlName() {
        return lName;
    }
    
    @Override
    public String toString() {
        return "fName="+fName+", lName="+lName;
    }
    
}
