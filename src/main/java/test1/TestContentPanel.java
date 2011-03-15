package test1;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;
import org.olegchir.wicket_test_project.project1.common.ui.Home;

public class TestContentPanel extends Panel {
	public static final String TEST_MODAL_WINDOW_2_WID = "testModalWindow2";
	public static final String TEST_MODAL_WINDOW_2_FORM_WID = "testModalWindow2Form";
	public static final String TEST_MODAL_WINDOW_2_SHOW_BUTTON_WID = "testModalWindow2ShowButton";
	public static final String TEST_MODAL_WINDOW_2_COOKIE = "testModalWindow2";
	public static final String TEST_MODAL_WINDOW_2_HEADER = "Modal Window 2";
	
	private TestModalWindow2 testModalWindow2 = null;
	private AjaxButton testModalWindow2SubmitButton = null;
	private Label message = null;
	private Form form = null;
	private int testModalWindow2Counter = 0;

	public TestContentPanel(String id) {
		super(id);
		add(new Label("message2", "Content level 1"));
		
		createForm();
		resetForm();
		addOrReplace(createMessage());
		addOrReplace(form);
	}
	
	private void resetForm() {
		form.addOrReplace(getTestModalWindow2());
		form.addOrReplace(createTestModalWindow2SubmitButton());
	}
	
	private Form createForm() {
		form = new Form(TEST_MODAL_WINDOW_2_FORM_WID);
		form.setOutputMarkupId(true);
		return form;
	}
	
	private Label createMessage() {
		message = new Label("message", "Window2 closed "+testModalWindow2Counter+" times");
		message.setOutputMarkupId(true);
		return message;
	}
	
	private AjaxButton createTestModalWindow2SubmitButton() {
		testModalWindow2SubmitButton = new AjaxButton(TEST_MODAL_WINDOW_2_SHOW_BUTTON_WID){
			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				// TODO Auto-generated method stub
				testModalWindow2.show(target);
			}
		};
		testModalWindow2SubmitButton.setOutputMarkupId(true);
		return testModalWindow2SubmitButton;
	}
	
	private void refreshMessage(AjaxRequestTarget target) {
		testModalWindow2Counter++;
		TestContentPanel.this.addOrReplace(createMessage());
		target.addComponent(form);
		target.addComponent(message);
	}
	
	private TestModalWindow2 getTestModalWindow2()
	{
		testModalWindow2 = new TestModalWindow2(TEST_MODAL_WINDOW_2_WID);
		
		testModalWindow2.setContent(new TestContentPanel2(testModalWindow2.getContentId()));
		testModalWindow2.setTitle(TEST_MODAL_WINDOW_2_HEADER);
		//testModalWindow2.setCookieName(TEST_MODAL_WINDOW_2_COOKIE);
		
		testModalWindow2.setWindowClosedCallback(new ModalWindow.WindowClosedCallback()
		{
			/**
			 * 
			 */
			private static final long serialVersionUID = 1865173174089098409L;

			@Override
			public void onClose(AjaxRequestTarget target)
			{	
				refreshMessage(target);
			}
		});
		
		testModalWindow2.setOutputMarkupId(true);
		
		return testModalWindow2;
	}
}
