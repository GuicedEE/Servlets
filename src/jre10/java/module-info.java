module com.jwebmp.guicedservlets {
	requires transitive com.google.guice.extensions.servlet;
	requires transitive com.jwebmp.guicedinjection;
	requires transitive com.jwebmp.logmaster;
	requires com.google.guice;
	requires java.logging;
	requires javax.servlet.api;
	requires aopalliance;
	requires java.validation;

	exports com.jwebmp.guicedservlets;
}
