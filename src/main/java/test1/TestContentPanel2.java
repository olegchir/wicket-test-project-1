package test1;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;

public class TestContentPanel2 extends Panel {

	public TestContentPanel2(String id) {
		super(id);
		add(new Label("message", "Content level 2"));
	}
}
