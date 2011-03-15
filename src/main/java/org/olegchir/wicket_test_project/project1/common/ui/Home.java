package org.olegchir.wicket_test_project.project1.common.ui;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitButton;
import org.apache.wicket.extensions.ajax.markup.html.IndicatingAjaxButton;
import org.apache.wicket.extensions.ajax.markup.html.IndicatingAjaxSubmitButton;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.Model;

import test1.TestContentPanel;
import test1.TestModalWindow;

public class Home extends CommonBasePage
{
	public static final String TEST_MODAL_WINDOW_1_WID = "testModalWindow1";
	public static final String TEST_MODAL_WINDOW_1_FORM_WID = "testModalWindow1Form";
	public static final String TEST_MODAL_WINDOW_1_SHOW_BUTTON_WID = "testModalWindow1ShowButton";
	public static final String TEST_MODAL_WINDOW_1_COOKIE = "testModalWindow1";
	public static final String TEST_MODAL_WINDOW_1_HEADER = "Modal Window 1";
	
	private TestModalWindow testModalWindow1 = null;
	private AjaxButton testModalWindow1SubmitButton = null;
	private Label message = null;
	private Form form = null;
	private int testModalWindow1Counter = 0;
	
	public Home()
    {
		createForm();
		resetForm();
		add(createMessage());
		add(form);
    }
	
	private void resetForm() {
		form.addOrReplace(getTestModalWindow1());
		form.addOrReplace(createTestModalWindow1SubmitButton());
	}
	
	private Form createForm() {
		form = new Form(TEST_MODAL_WINDOW_1_FORM_WID);
		form.setOutputMarkupId(true);
		return form;
	}
	
	private Label createMessage() {
		message = new Label("message", "Window1 closed "+testModalWindow1Counter+" times");
		message.setOutputMarkupId(true);
		return message;
	}
	
	private AjaxButton createTestModalWindow1SubmitButton() {
		testModalWindow1SubmitButton = new AjaxButton(TEST_MODAL_WINDOW_1_SHOW_BUTTON_WID){
			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				// TODO Auto-generated method stub
				testModalWindow1.show(target);
			}
		};
		testModalWindow1SubmitButton.setOutputMarkupId(true);
		return testModalWindow1SubmitButton;
	}
	
	private void refreshMessage(AjaxRequestTarget target) {
		testModalWindow1Counter++;
		Home.this.addOrReplace(createMessage());
		target.addComponent(form);
		target.addComponent(message);
	}
	
	private TestModalWindow getTestModalWindow1()
	{
		testModalWindow1 = new TestModalWindow(TEST_MODAL_WINDOW_1_WID);
		
		testModalWindow1.setContent(new TestContentPanel(testModalWindow1.getContentId()));
		testModalWindow1.setTitle(TEST_MODAL_WINDOW_1_HEADER);
		testModalWindow1.setCookieName(TEST_MODAL_WINDOW_1_COOKIE);
		
		testModalWindow1.setWindowClosedCallback(new ModalWindow.WindowClosedCallback()
		{
			/** Constant needed for serialization. */
			private static final long serialVersionUID = -8879879986821552090L;

			@Override
			public void onClose(AjaxRequestTarget target)
			{	
				refreshMessage(target);
			}
		});
		
		testModalWindow1.setOutputMarkupId(true);
		
		return testModalWindow1;
	}
}
