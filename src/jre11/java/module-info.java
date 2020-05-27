module com.guicedee.guicedservlets {

	uses com.guicedee.guicedservlets.services.IGuiceSiteBinder;

	requires transitive com.google.guice.extensions.servlet;
	requires transitive com.guicedee.guicedinjection;
	requires transitive java.servlet;

	requires transitive java.validation;

	exports com.guicedee.guicedservlets;
	exports com.guicedee.guicedservlets.services;

	provides com.guicedee.guicedinjection.interfaces.IGuiceModule with com.guicedee.guicedservlets.services.GuiceSiteInjectorModule;
	provides com.guicedee.guicedinjection.interfaces.IGuicePreStartup with com.guicedee.guicedservlets.implementations.GuiceServletKeyStartup;

}
