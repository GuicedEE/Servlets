package com.guicedee.guicedservlets.tests.mocks;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import java.io.IOException;

public class MockRequestDispatcher
		implements RequestDispatcher
{

	/**
	 * The resource path to dispatch to.
	 */
	private String resourcePath;

	/**
	 * Constructs a new RequestDispatcher instance for the specified
	 * resourcePath.
	 *
	 * @param resourcePath
	 * 		the resource path to dispatch to
	 */
	public MockRequestDispatcher(String resourcePath)
	{
		this.resourcePath = resourcePath;
	}

	/**
	 * This method stores the dispatcher's resourcePath on the request.
	 * <p/>
	 * The resourcePath can later be retrieved by calling
	 * {@link MockRequest#getForward()}
	 *
	 * @param request
	 * 		the servlet request
	 * @param response
	 * 		the servlet response
	 *
	 * @throws jakarta.servlet.ServletException
	 * 		if the response was already
	 * 		committed
	 * @throws java.io.IOException
	 * 		if the target resource throws this exception
	 */
	@Override
	public void forward(ServletRequest request, ServletResponse response) throws ServletException, IOException
	{
		MockRequest mockRequest = new MockRequest();
		mockRequest.setForward(resourcePath);
	}

	/**
	 * This method stores the dispatcher's resourcePath on the request.
	 * <p/>
	 * The resourcePath can be retrieved by calling
	 * {@link MockRequest#getIncludes()}
	 *
	 * @param request
	 * 		the servlet request
	 * @param response
	 * 		the servlet response
	 *
	 * @throws jakarta.servlet.ServletException
	 * 		if the response was already
	 * 		committed
	 * @throws java.io.IOException
	 * 		if the target resource throws this exception
	 */
	@Override
	public void include(ServletRequest request, ServletResponse response) throws ServletException, IOException
	{
		MockRequest mockRequest = new MockRequest();
		mockRequest.addInclude(resourcePath);
	}

}
