package org.olegchir.wicket_test_project.project1.startup;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.Page;
import org.apache.wicket.Request;
import org.apache.wicket.RequestCycle;
import org.apache.wicket.Response;
import org.apache.wicket.Session;
import org.apache.wicket.application.IComponentOnBeforeRenderListener;
import org.apache.wicket.guice.GuiceComponentInjector;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.protocol.http.WebRequest;
import org.apache.wicket.protocol.http.WebRequestCycle;
import org.apache.wicket.protocol.http.WebResponse;
import org.olegchir.wicket_test_project.project1.common.WSession;
import org.olegchir.wicket_test_project.project1.common.WicketModule;
import org.olegchir.wicket_test_project.project1.common.ui.Home;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;

public class Application extends WebApplication {
	private static final ThreadLocal<List<String>> COMPONENTS_INJECTED_ON_INSTANTIATION = new ThreadLocal<List<String>>() {
		@Override
		public List<String> initialValue() {
			return new ArrayList<String>();
		}
	};

	transient private final Module guiceModule;

	transient private Module wModule;

	transient private Injector injector;

	public Application() {
		this(null);
	}

	public Application(final Module guiceModule) {
		super();
		this.guiceModule = guiceModule;
	}

	@Override
	public Class<? extends Page> getHomePage() {
		return Home.class;
	}

	@Override
	public Session newSession(final Request request, final Response response) {
		return new WSession(request);
	}

	@Override
	protected void init() {
		super.init();
		wModule = guiceModule == null ? new WicketModule() : guiceModule;

		injector = Guice.createInjector(wModule);

		this.registerComponentInstantiationListener(injector);
		this.registerComponentOnBeforeRenderListener(injector);
		Application.get().getRequestLoggerSettings()
				.setRequestLoggerEnabled(true);
		pageMounts();
	}

	@Override
	public RequestCycle newRequestCycle(final Request request,
			final Response response) {
		return new WebRequestCycle(this, (WebRequest) request,
				(WebResponse) response) {
			@Override
			protected void onEndRequest() {
				COMPONENTS_INJECTED_ON_INSTANTIATION.get().clear();
				super.onEndRequest();
			}
		};
	}

	private String formatUniqueId(final Component component) {
		return component.getClass().getName() + "@"
				+ Integer.toHexString(component.hashCode());
	}

	private boolean isInjectable(final Component component) {
		return component.getClass().getName()
				.startsWith(Application.class.getPackage().getName());
	}

	private void markComponentAsInjected(final Component component) {
		COMPONENTS_INJECTED_ON_INSTANTIATION.get().add(
				this.formatUniqueId(component));
	}

	private boolean needsInjection(final Component component) {
		return !COMPONENTS_INJECTED_ON_INSTANTIATION.get().contains(
				this.formatUniqueId(component));
	}

	private void registerComponentOnBeforeRenderListener(final Injector injector) {
		addPreComponentOnBeforeRenderListener(new IComponentOnBeforeRenderListener() {
			/**
			 * Inject components that have been "de-serialized"
			 */
			@Override
			public void onBeforeRender(Component component) {
				if (isInjectable(component) && needsInjection(component)) {
					injector.injectMembers(component);
				}
			}
		});
	}

	private void registerComponentInstantiationListener(final Injector injector) {
		GuiceComponentInjector wicketInjector = new GuiceComponentInjector(
				this, injector) {
			@Override
			public void onInstantiation(final Component component) {
				if (isInjectable(component)) {
					injector.injectMembers(component);
					markComponentAsInjected(component);
				}
			}
		};
		addComponentInstantiationListener(wicketInjector);
	}

	private void pageMounts() {
		// mountBookmarkablePage("/mypage", MyPage.class);

	}
}
