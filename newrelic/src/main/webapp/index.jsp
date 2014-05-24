<%@ page import="com.newrelic.api.agent.NewRelic" %>
<html>
<body>
<h2>Test New Relic API </h2>
<%
    // Code that tests the NewRelic api
    // I tested this with jetty.  Prerequisites
    //  1) Install jetty
    //  2) download newrelic (newrelic_agent3.7.0.zip) into jetty root dir
    //  3) unzip this file in the jetty root dir (creates a subdir called newrelic)
    //  4) cd /path/to/appserver/newrelic
    //     java -jar newrelic.jar install -l <newreliclicensekey>
    //  5) Change jettypath/newrelic/newrelic.yml (see this projects resources/newrelic.yml file for an example)
    //   5.1) to trigger saving the newrelic 'transaction' after 1 second
    //     change
    //      transaction_threshold: apdex_f
    //     to
    //      transaction_threshold: 1
    //   5.2) If the key isn't in teh file add it
    //     license_key: '4d6894ba468f70e0ec669e8a9646c2836c01ee68'
    //   5.3) Change appname from from default to yourappname
    //      app_name: My Wonderful AppName
    //   5.4) Be careful as indentation seems to matter in yml files

    // resources:
    // installing newrelic: http://docs.newrelic.com/docs/java/java-agent-self-installer
    //   https://docs.newrelic.com/docs/java/java-agent-api
    //   https://docs.newrelic.com/docs/java/java-agent-api-example-program

    // all metrics must start with Custom/

    // The null is passed in for 'category'.  Note sure what that is for, but maybe something like
    // 'database', 'rest' etc.  i.e. subsystems.
    NewRelic.setTransactionName(null, "/stevestesttransaction");
    Thread.sleep(2000);
    // These create numbers and aren't really part of the transactions.
    // Similar to jamon
    NewRelic.incrementCounter("Custom/ssouza/pageCounter");
    NewRelic.recordMetric("Custom/myfunction_time", 75);
    NewRelic.recordResponseTimeMetric("Custom/StevesOtherTimer", 125);

    // The following are properties associated with the transaction.  They will show up
    // in transactions gui IF the transaction exceeds the performance threshold defined in
    // 5.1) above.  Types of things that would be useful here would be userName, and
    // the query string
    NewRelic.setUserName("userIsSteveSouza");
    NewRelic.addCustomParameter("customParmeter", "StevesCustomParameter");
    NewRelic.addCustomParameter("purchasePrice", 123.34);

    // I didn't try the following, but it looks useful for tracking exception stack traces.
    // There are other signatures for the method too.
    // NewRelic.noticeError(java.lang.Throwable throwable)

%>
</body>
</html>
