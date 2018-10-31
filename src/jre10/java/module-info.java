import com.jwebmp.guicedinjection.interfaces.IGuiceModule;
import com.jwebmp.guicedinjection.interfaces.IGuicePreStartup;
import com.jwebmp.guicedservlets.GuiceServletKeyStartup;
import com.jwebmp.guicedservlets.GuiceSiteInjectorModule;
import com.jwebmp.guicedservlets.services.IGuiceSiteBinder;

module com.jwebmp.guicedservlets {
	uses IGuiceSiteBinder;

	requires transitive com.google.guice.extensions.servlet;
	requires transitive com.jwebmp.guicedinjection;

	requires transitive javax.servlet.api;

	exports com.jwebmp.guicedservlets;
	exports com.jwebmp.guicedservlets.services;

	provides IGuiceModule with GuiceSiteInjectorModule;
	provides IGuicePreStartup with GuiceServletKeyStartup;

}
