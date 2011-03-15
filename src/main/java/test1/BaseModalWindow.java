package test1;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

public class BaseModalWindow extends Panel {

	public static final String MODAL_WINDOW_WID = "modalWindow";
	public ModalWindow modalWindow;
	
	private ModalWindow getModalWindow()
	{
		modalWindow = new ModalWindow(MODAL_WINDOW_WID);
		
		modalWindow.setCloseButtonCallback(new ModalWindow.CloseButtonCallback()
		{
			/** Constant needed for serialization. */
			private static final long serialVersionUID = 3142942753714951984L;

			@Override
			public boolean onCloseButtonClicked(AjaxRequestTarget target)
			{	
				return true;				
			}
		});
		return modalWindow;
	}

	public BaseModalWindow(String id, IModel<?> model)
	{
		super(id, model);	
		add(getModalWindow());
	}
	
	public BaseModalWindow(String id)
	{
		super(id);
		add(getModalWindow());
	}
	
	public String getContentId()
	{
		return modalWindow.getContentId();
	}
	
	public void show(AjaxRequestTarget target)
	{
		modalWindow.show(target);
	}
	
	public void close(AjaxRequestTarget target)
	{
		modalWindow.close(target);
	}
	
	public void setTitle(String title)
	{
		modalWindow.setTitle(title);
	}
	
	public void setContent(Component component)
	{
		modalWindow.setContent(component);
	}
	
	public void setContent(Panel component)
	{
		modalWindow.setContent(component);
	}
	
	public void setInitialHeight(int height)
	{
		modalWindow.setInitialHeight(height);
	}
	
	public void setInitialWidth(int width)
	{
		modalWindow.setInitialWidth(width);
	}
	
	public void resetDialog(Object object)
	{
		if(modalWindow != null)
		{
			modalWindow.setDefaultModelObject(null);
			modalWindow.setDefaultModelObject(object);
		}
	}	
	
	public void setCookieName(String cookieName)
	{
		modalWindow.setCookieName(cookieName);
	}
	
	public void setWindowClosedCallback(ModalWindow.WindowClosedCallback callback)
	{
		modalWindow.setWindowClosedCallback(callback);
	}
}
