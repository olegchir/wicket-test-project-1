package org.olegchir.wicket_test_project.project1.common.ui;

import org.apache.wicket.markup.html.basic.Label;

public class Home extends CommonBasePage
{
	public Home()
    {
		add(new Label("message", "Hello World!"));
    }
}
