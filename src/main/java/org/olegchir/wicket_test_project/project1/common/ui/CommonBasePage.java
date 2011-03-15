package org.olegchir.wicket_test_project.project1.common.ui;

import org.apache.wicket.markup.html.WebPage;
import org.olegchir.wicket_test_project.project1.common.WSession;

public class CommonBasePage extends WebPage
{
	
	public CommonBasePage()
    {
        super();
        //add(new NavigationPanel("menu"));
    }
	
	public WSession getIupatSession()
	{
		return ((WSession) getSession());
	}
}
