/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stevesouza.jaxrs_resteasy;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * note when methods for getSingletons and getClasses are not implemented
 *   component scanning will be used.  However, it appears at least in RestEasy that
 *   you can't define a singleton service this way (I tried @Singleton) annotation on the class.
 *   I think if you want a singleton you must override these methods.
 * 
 * @author stevesouza
 */
@ApplicationPath("/services")
public class CustomerApp extends Application {

}
