/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stevesouza.jaxrs_resteasy.client;
import com.stevesouza.jaxrs_resteasy.customer.Customer;
import com.stevesouza.jaxrs_resteasy.customer.CustomerService;
import com.stevesouza.jaxrs_resteasy.customer.CustomerServiceImpl;
import javax.ws.rs.core.Response;
import static org.assertj.core.api.Assertions.assertThat;
import org.jboss.resteasy.plugins.server.tjws.TJWSEmbeddedJaxrsServer;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author stevesouza
 */
public class ResteasyClientProxyTest {
    
    private static final int PORT = 12345;
    private CustomerService customerService;
    
 private static TJWSEmbeddedJaxrsServer server;

    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
   
    
    // note if this setup/teardown is too expensive this could be done in 
    // @BeforeClass and @AfterClass.  An advantage of doing it in @Before
    // and @After is that there wouldn't be a risk of side effects for the 
    // different tests (say adding a Customer and not deleting it)
    @Before
    public void setUp() {
        server = new TJWSEmbeddedJaxrsServer();
        server.setPort(PORT);
        server.getDeployment().getActualResourceClasses().add(CustomerServiceImpl.class);
        // could provide an appname to appear in the url i.e. http://localhost:12345/myappname
        server.setRootResourcePath("/");
        server.start();
        customerService = getCustomerService();
    }
    
    @After
    public void tearDown() {
       server.stop();
    }
    
    private CustomerService getCustomerService() {
        ResteasyClientProxy client = new ResteasyClientProxy("http://localhost:"+PORT+"/customers");
        return client.getCustomerService();
    }

    /**
     * Test of getCustomerService method, of class ResteasyClientProxy.
     */
    @Test
    public void testGetCustomerCount() {
        assertThat(customerService.getCustomerCount()).isEqualTo(4);
    }
    
    
        @Test
    public void testGetCustomerList() {
        assertThat(customerService.getCustomerList()).hasSize(4);
    }
    
    @Test
    public void testCreateCustomer() {
        Response response = customerService.createCustomer(new Customer("albert", "einstein"));
        response.close();
        assertThat(customerService.getCustomerCount()).isEqualTo(5);
    }
    
}
