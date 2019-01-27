module com.jwebmp.guicedservlets {
	uses com.jwebmp.guicedservlets.services.IGuiceSiteBinder;

	requires transitive com.google.guice.extensions.servlet;
	requires transitive com.jwebmp.guicedinjection;
	requires transitive com.jwebmp.logmaster;
	requires transitive com.google.guice;

	requires java.logging;
	requires javax.servlet.api;
	requires aopalliance;
	requires java.validation;

	exports com.jwebmp.guicedservlets;
	exports com.jwebmp.guicedservlets.services;


	provides com.jwebmp.guicedinjection.interfaces.IGuiceModule with com.jwebmp.guicedservlets.services.GuiceSiteInjectorModule;
	provides com.jwebmp.guicedinjection.interfaces.IGuicePreStartup with com.jwebmp.guicedservlets.implementations.GuiceServletKeyStartup;

	provides com.jwebmp.guicedinjection.interfaces.IGuiceScanModuleExclusions with com.jwebmp.guicedservlets.implementations.GuicedServletModuleExclusions;
	provides com.jwebmp.guicedinjection.interfaces.IGuiceScanJarExclusions with com.jwebmp.guicedservlets.implementations.GuicedServletModuleExclusions;

}
