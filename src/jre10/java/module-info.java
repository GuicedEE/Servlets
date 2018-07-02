module com.jwebmp.guicedservlets {
	requires com.google.guice.extensions.servlet;
	requires com.jwebmp.guicedinjection;
	requires com.jwebmp.logmaster;
	requires com.google.guice;
	requires java.logging;
	requires javax.servlet.api;
	requires aopalliance;

	exports com.jwebmp.guicedservlets;
}
