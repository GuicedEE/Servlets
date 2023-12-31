import com.guicedee.guicedservlets.services.IGuiceSiteBinder;
import com.guicedee.guicedservlets.tests.ServletTestBindings;

module guiced.servlets.test {
	requires com.guicedee.guicedservlets;
	
	
	requires org.junit.jupiter.api;
	requires org.slf4j;
	requires org.slf4j.simple;
	requires static lombok;
	requires org.apache.commons.io;
	
	provides IGuiceSiteBinder with ServletTestBindings;
	
	opens com.guicedee.guicedservlets.tests to org.junit.platform.commons,com.google.guice;
	opens com.guicedee.guicedservlets.tests.mocks to com.google.common,com.google.guice.extensions.servlet, com.google.guice;
}