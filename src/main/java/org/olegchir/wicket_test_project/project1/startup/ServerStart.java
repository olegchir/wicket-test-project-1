package org.olegchir.wicket_test_project.project1.startup;

import org.mortbay.jetty.Connector;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.nio.SelectChannelConnector;
import org.mortbay.jetty.webapp.WebAppContext;

public class ServerStart
{
   
	public static void main(String[] args)
	{
		Server server = new Server();
		SelectChannelConnector connector = new SelectChannelConnector();
		connector.setPort(8095);
		server.setConnectors(new Connector[]
		{ connector });
		WebAppContext web = new WebAppContext();
		web.setContextPath("/testapp");
		web.setWar("src/main/webapp");
		server.addHandler(web);
		System.setProperty("FUNCTIONAL_TEST", "yes");
		try
		{
			server.start();
			server.join();

		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.exit(1);
		}
	}
}