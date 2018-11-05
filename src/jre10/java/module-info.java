import com.jwebmp.guicedinjection.interfaces.IGuiceModule;
import com.jwebmp.guicedinjection.interfaces.IGuicePreStartup;
import com.jwebmp.guicedinjection.interfaces.IGuiceScanJarExclusions;
import com.jwebmp.guicedinjection.interfaces.IGuiceScanModuleExclusions;
import com.jwebmp.guicedservlets.implementations.GuiceServletKeyStartup;
import com.jwebmp.guicedservlets.implementations.GuicedServletModuleExclusions;
import com.jwebmp.guicedservlets.services.GuiceSiteInjectorModule;
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
	provides IGuicePreStartup with GuiceServletKeyStartup;

	provides IGuiceScanModuleExclusions with GuicedServletModuleExclusions;
	provides IGuiceScanJarExclusions with GuicedServletModuleExclusions;

}
