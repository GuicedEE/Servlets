import com.jwebmp.guicedinjection.interfaces.IGuiceModule;
import com.jwebmp.guicedservlets.GuiceSiteInjectorModule;
import com.jwebmp.guicedservlets.services.IGuiceSiteBinder;

module com.jwebmp.guicedservlets {
	uses IGuiceSiteBinder;
	requires com.google.guice.extensions.servlet;
	requires com.jwebmp.guicedinjection;
	requires com.jwebmp.logmaster;
	requires com.google.guice;
	requires java.logging;
	requires javax.servlet.api;
	requires aopalliance;
	requires java.validation;

	exports com.jwebmp.guicedservlets;
	exports com.jwebmp.guicedservlets.services;

	provides IGuiceModule with GuiceSiteInjectorModule;
}
