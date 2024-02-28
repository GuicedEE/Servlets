import com.guicedee.guicedservlets.implementations.*;

module com.guicedee.guicedservlets {
	
	requires transitive com.google.guice.extensions.servlet;
	requires transitive com.guicedee.client;
	requires transitive jakarta.servlet;
	requires transitive jakarta.validation;
	
	requires static lombok;
	
	exports com.guicedee.guicedservlets;
	exports com.guicedee.guicedservlets.services;
	exports com.guicedee.guicedservlets.services.scopes;
	
	opens com.guicedee.guicedservlets.services.scopes to com.google.common,com.google.guice.extensions.servlet, com.google.guice;
	opens com.guicedee.guicedservlets to com.google.common,com.google.guice.extensions.servlet, com.google.guice;
	opens com.guicedee.guicedservlets.services to com.google.guice;
	opens com.guicedee.guicedservlets.implementations to com.google.guice;

	provides com.guicedee.guicedinjection.interfaces.IGuiceModule with com.guicedee.guicedservlets.services.GuiceSiteInjectorModule;
	provides com.guicedee.guicedinjection.interfaces.IGuicePreStartup with com.guicedee.guicedservlets.implementations.GuiceServletKeyStartup;
	
	provides com.guicedee.guicedservlets.servlets.services.IOnCallScopeEnter with OnCallScopeEnterBindFromRequest;
	
	uses com.guicedee.guicedservlets.servlets.services.IOnCallScopeEnter;
	uses com.guicedee.guicedservlets.servlets.services.IOnCallScopeExit;

}
