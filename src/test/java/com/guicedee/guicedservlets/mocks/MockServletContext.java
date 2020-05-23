package com.guicedee.guicedservlets.mocks;

import org.junit.platform.commons.util.StringUtils;

import javax.servlet.*;
import javax.servlet.descriptor.JspConfigDescriptor;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.*;

public class MockServletContext
		implements ServletContext
{

	// -------------------------------------------------------- Constants

	/**
	 * The servlet context default context path, <em>"/mock"</em>.
	 */
	public static final String DEFAULT_CONTEXT_PATH = "/mock";

	// -------------------------------------------------------- Private variables

	/**
	 * Map of attributes.
	 */
	private final Map<String, Object> attributes = new HashMap<>();

	/**
	 * Map of initialization parameters.
	 */
	private final Map<String, String> initParameters = new HashMap<>();

	/**
	 * Map of mime types.
	 */
	private final Map<String, String> mimeTypes = new HashMap<>();

	/**
	 * The context temporary path.
	 */
	private String tempPath;

	/**
	 * The web application path.
	 */
	private String webappPath;

	/**
	 * The web application root file. The File is created from the
	 * {@link #webappPath} value.
	 */
	private File webappRoot;

	/**
	 * The servlet context name, <em>"mock"</em>.
	 */
	private String servletContextName = "mock";

	/**
	 * The context path, by default its value is set to
	 * {@link #DEFAULT_CONTEXT_PATH}.
	 */
	private String contextPath = MockServletContext.DEFAULT_CONTEXT_PATH;

	/**
	 * Default constructor for this mock object.
	 * <p/>
	 * The servlet context name is set to 'mock'.
	 * The web content root and temporary work direcotry are set to null.
	 */
	public MockServletContext()
	{
		this(MockServletContext.DEFAULT_CONTEXT_PATH, null, null);
	}

	/**
	 * Create the mock object. As part of the creation, the context sets the
	 * root directory where web application content is stored. This must be an
	 * ABSOLUTE directory relative to where the tests are being executed.
	 * <p/>
	 * For example: <code>System.getProperty("user.dir") + "/src/webapp"</code>
	 * <p/>
	 * In addition to setting the web root directory, this constructor also sets
	 * up a temporary work directory for things like file uploads.
	 * <p/>
	 * <b>Note</b> this temporary work directory is set as the value of the
	 * ServletContext attribute 'javax.servlet.context.tempdir'.
	 *
	 * @param contextPath
	 * 		the servlet context path
	 * @param webappPath
	 * 		the path to the root of the web application
	 * @param tempPath
	 * 		the temporary work directory
	 */
	public MockServletContext(final String contextPath,
	                          final String webappPath, final String tempPath)
	{
		setContextPath(contextPath);

		//Setup temp path, before webapp path, since setWebappPath() will
		//default tempPath to java.io.tmpdir if tempPath does not have a value
		//yet
		setTempPath(tempPath);
		setWebappPath(webappPath);

		mimeTypes.put("html", "text/html");
		mimeTypes.put("htm", "text/html");
		mimeTypes.put("css", "text/css");
		mimeTypes.put("xml", "text/xml");
		mimeTypes.put("js", "text/plain");
		mimeTypes.put("gif", "image/gif");
		mimeTypes.put("jpg", "image/jpeg");
		mimeTypes.put("png", "image/png");
	}

	/**
	 * Creates the web application root File {@link #getWebappRoot()}.
	 *
	 * @throws IllegalStateException
	 * 		if the {@link #getWebappPath()} cannot
	 * 		be found
	 */
	public void createWebappRoot()
	{
		webappRoot = null;

		if (StringUtils.isBlank(getWebappPath()))
		{
			return;
		}

		webappRoot = new File(getWebappPath());
		if (webappRoot.exists() && webappRoot.isDirectory())
		{
			//If the webappRoot is a legal directory, we can return
			System.out.println("    WEB root directory defined at -> "
			                   + webappRoot.getAbsolutePath());
			return;
		}

		//Build up a string of locations that were checked
		String checkedPaths = webappRoot.getAbsolutePath();

		//If webappRoot is not a legal directory, look for the webappRoot on
		//the classpath
		URL url = null;
		try
		{
			url = getResource(getWebappPath());
			if (url == null)
			{
				webappRoot = null;
			}
			else
			{
				URI uri = new URI(url.toString());
				webappRoot = new File(uri);
				if (webappRoot.exists() && webappRoot.isDirectory())
				{
					//If the webappRoot is a legal directory on the classpath
					//we can return
					System.out.println("    WEB root directory defined at -> "
					                   + webappRoot.getAbsolutePath());
					return;
				}
			}
			if (webappRoot == null)
			{
				checkedPaths += ". Also note that the path '" + getWebappPath()
				                + "' was checked but not found on the classpath";
			}
			else
			{
				checkedPaths += ", " + webappRoot.getAbsolutePath();
			}
		}
		catch (Exception ex)
		{
			String msg = "error occurred while checking for existence of the web"
			             + " application root directory at : " + url;
			throw new RuntimeException(msg, ex);
		}

		//At this stage it seems that the path cannot be mapped to a
		//valid directory, so throw an exception so user can provide the
		//correct path
		throw new IllegalStateException("ERROR: The "
		                                + "directory cannot be found: " + getWebappPath() + ". "
		                                + "The following absolute locations were checked for the path: "
		                                + checkedPaths + ".");
	}

	/**
	 * Return the temporary path where files are stored during test runs.
	 *
	 * @return the temporary path where files are stored during test runs
	 */
	public String getTempPath()
	{
		return tempPath;
	}

	/**
	 * Set the temporary path where files are stored during test runs.
	 *
	 * @param tempPath
	 * 		the temporary path where files are stored during test
	 * 		runs
	 */
	public void setTempPath(String tempPath)
	{
		if (StringUtils.isBlank(tempPath))
		{
			return;
		}

		//If the specified temp path is the same as the temp dir, add a mock
		//folder to stop possible locking on Windows OS.
		if (System.getProperty("java.io.tmpdir")
		          .equals(tempPath))
		{
			if (!tempPath.endsWith("/"))
			{
				tempPath += "/";
			}
			tempPath = tempPath + "click-temp";
		}
		this.tempPath = tempPath;

	}

	/**
	 * Return the web application path where resources like javascript, css
	 * and images can be picked up.
	 *
	 * @return the web application path
	 */
	public String getWebappPath()
	{
		return webappPath;
	}

	/**
	 * Sets the mock web application path to the specified webappPath.
	 * <p/>
	 * <b>Note:</b> this method will also set the web application's temporary
	 * directory to the value {@link #getTempPath()}. If {@link #getTempPath()}
	 * is not set, this method will default tempPath to:
	 * <tt>System.getProperty("java.io.tmpdir")</tt>.
	 *
	 * @param webappPath
	 * 		set the context web application path
	 */
	public void setWebappPath(String webappPath)
	{
		this.webappPath = webappPath;

		if (StringUtils.isBlank(webappPath))
		{
			return;
		}

		//Create the context root
		createWebappRoot();

		if (getTempPath() == null)
		{
			setTempPath(System.getProperty("java.io.tmpdir"));
		}
	}

	/**
	 * Remove the leading slash '/' from the specified name.
	 *
	 * @param name
	 * 		the name from which to remove the leading slash '/'
	 *
	 * @return the name with the leading slash removed
	 */
	private String removeLeadingSlash(String name)
	{
		if (name.startsWith("/"))
		{
			name = name.substring(1);
		}
		return name;
	}

	/**
	 * Create the mock object. As part of the creation, the context sets the
	 * root directory where web application content is stored. This must be an
	 * ABSOLUTE directory relative to where the tests are being executed.
	 * <p/>
	 * For example: <code>System.getProperty("user.dir") + "/src/webapp"</code>
	 * <p/>
	 * In addition to setting the web root directory, this constructor also sets
	 * up a temporary work directory for things like file uploads.
	 * <p/>
	 * <b>Note</b> this temporary work directory is set as the value of the
	 * ServletContext attribute 'javax.servlet.context.tempdir'.
	 * <p/>
	 * The temporary work directory defaults to
	 * System.getProperty("java.io.tmpdir").
	 *
	 * @param contextPath
	 * 		the servlet context path
	 * @param webappPath
	 * 		The path to the root of the web application
	 */
	public MockServletContext(final String contextPath,
	                          final String webappPath)
	{
		this(contextPath, webappPath, System.getProperty("java.io.tmpdir"));
	}

	/**
	 * Delete the specified directory and any subdirectories.
	 *
	 * @param directory
	 * 		to delete
	 *
	 * @return true if the directory was successfully deleted, false otherwise
	 */
	static synchronized boolean deleteDirectory(final File directory)
	{
		if (directory == null)
		{
			return true;
		}

		if (!directory.exists() || !directory.isDirectory())
		{
			return true;
		}

		Throwable shutdownException = null;
		try
		{
			System.out.println("Deleting temporary directory '" + directory.getAbsolutePath() + "'");
			directory.delete();
		}
		catch (Exception e)
		{
			shutdownException = e;
		}
		return true;
	}

	/**
	 * Return the web application root File where resources like javascript, css
	 * and images can be picked up.
	 *
	 * @return the web application root File
	 */
	public File getWebappRoot()
	{
		return webappRoot;
	}

	/**
	 * Add an init parameter.
	 *
	 * @param name
	 * 		The parameter name
	 * @param value
	 * 		The parameter value
	 */
	public void addInitParameter(final String name, final String value)
	{
		initParameters.put(name, value);
	}

	// Configuration methods

	/**
	 * Add the map of init parameters.
	 *
	 * @param initParameters
	 * 		A map of init parameters
	 */
	public void addInitParameters(final Map<String, String> initParameters)
	{
		if (initParameters == null)
		{
			return;
		}
		initParameters.putAll(initParameters);
	}

	/**
	 * Add a new recognized mime type.
	 *
	 * @param fileExtension
	 * 		The file extension (e.g. "jpg")
	 * @param mimeType
	 * 		The mime type (e.g. "image/jpeg")
	 */
	public void addMimeType(final String fileExtension, final String mimeType)
	{
		mimeTypes.put(fileExtension, mimeType);
	}

	/**
	 * Return the servlet context path.
	 *
	 * @return the servletContext path
	 */
	@Override
	public String getContextPath()
	{
		return contextPath;
	}

	// -------------------------------------------------------- ServletContext interface methods

	/**
	 * Get the context for the given URL path.
	 *
	 * @param name
	 * 		The url path
	 *
	 * @return Always returns this
	 */
	@Override
	public ServletContext getContext(String name)
	{
		return this;
	}

	/**
	 * Return the major version of the Servlet spec that this package supports,
	 * defaults to 2.
	 *
	 * @return the major version of the Servlet spec that this package supports,
	 * 		defaults to 2.
	 */
	@Override
	public int getMajorVersion()
	{
		return 2;
	}

	/**
	 * Return the minor version of the Servlet spec that this package supports,
	 * defaults to 3.
	 *
	 * @return the minor version of the Servlet spec that this package supports,
	 * 		defaults to 3.
	 */
	@Override
	public int getMinorVersion()
	{
		return 3;
	}

	@Override
	public int getEffectiveMajorVersion()
	{
		return 0;
	}

	@Override
	public int getEffectiveMinorVersion()
	{
		return 0;
	}

	/**
	 * Get the mime type for the given file. Uses a hardcoded map of mime
	 * types set at initialization time. If the mime type was not explicitly
	 * set, this method will fallback to
	 *
	 * @param name
	 * 		The name to get the mime type for
	 *
	 * @return The mime type
	 */
	@Override
	public String getMimeType(final String name)
	{
		int index = name.lastIndexOf('.');
		if (index == -1 || index == (name.length() - 1))
		{
			return null;
		}
		String type = name.substring(index + 1);
		if (mimeTypes.containsKey(type))
		{
			return mimeTypes.get(type);
		}
		else
		{
			return "Unknown MIME Type " + name;
		}
	}

	/**
	 * Get the resource paths starting from the web app root directory and then
	 * relative to the given name.
	 *
	 * @param name
	 * 		The starting name
	 *
	 * @return The set of resource paths at this location
	 *
	 * @throws IllegalArgumentException
	 * 		if the specified name does not start
	 * 		with a "/" character
	 */
	@Override
	public Set<String> getResourcePaths(String name)
	{
		if (!name.startsWith("/"))
		{
			throw new IllegalArgumentException("Path " + name
			                                   + " does not start with a \"/\" character");
		}
		if (webappRoot == null)
		{
			return new HashSet<>();
		}

		name = name.substring(1);
		if (name.endsWith("/"))
		{
			name = name.substring(0, name.length() - 1);
		}
		String[] elements = null;
		if (name.trim()
		        .length() == 0)
		{
			elements = new String[0];
		}
		else
		{
			elements = name.split("/");
		}

		//Find the most specific matching path
		File current = webappRoot;
		for (int i = 0; i < elements.length; i++)
		{
			File[] files = current.listFiles();
			boolean match = false;
			for (int f = 0; f < files.length; f++)
			{
				if (files[f].getName()
				            .equals(elements[i])
				    && files[f].isDirectory())
				{
					current = files[f];
					match = true;
					break;
				}
			}
			if (!match)
			{
				return null;
			}
		}

		//List of resources in the matching path
		File[] files = current.listFiles();
		Set<String> result = new HashSet<>();
		int stripLength = webappRoot.getPath()
		                            .length();
		for (int f = 0; f < files.length; f++)
		{
			String s = files[f].getPath()
			                   .substring(stripLength)
			                   .replace('\\', '/');
			if (files[f].isDirectory())
			{
				s = s + "/";
			}
			result.add(s);
		}
		return result;
	}

	/**
	 * Get the URL for a particular resource that is relative to the web app
	 * root directory.
	 *
	 * @param name
	 * 		The name of the resource to get
	 *
	 * @return The resource, or null if resource not found
	 *
	 * @throws MalformedURLException
	 * 		If the URL is invalid
	 */
	@Override
	public URL getResource(String name) throws MalformedURLException
	{
		if (webappRoot == null)
		{
			name = removeLeadingSlash(name);
			return Thread.currentThread()
			             .getContextClassLoader()
			             .getResource(name);
		}

		File f = new File(webappRoot, name);
		if (!f.exists())
		{
			name = removeLeadingSlash(name);
			return Thread.currentThread()
			             .getContextClassLoader()
			             .getResource(name);
		}
		else
		{
			return f.toURI()
			        .toURL();
		}
	}

	/**
	 * Get an input stream for a particular resource that is relative to the
	 * web app root directory or the current classpath. If the webappRoot is
	 * not set, this method will try and load the resource from the classpath.
	 *
	 * @param name
	 * 		The name of the resource to get
	 *
	 * @return The input stream for the resource, or null if resource is not
	 * 		found
	 */
	@Override
	public InputStream getResourceAsStream(String name)
	{
		if (webappRoot == null)
		{
			name = removeLeadingSlash(name);
			return Thread.currentThread()
			             .getContextClassLoader()
			             .getResourceAsStream(name);
		}

		File f = new File(webappRoot, name);
		if (!f.exists())
		{
			name = removeLeadingSlash(name);
			return Thread.currentThread()
			             .getContextClassLoader()
			             .getResourceAsStream(name);
		}
		else
		{
			try
			{
				return new FileInputStream(f);
			}
			catch (FileNotFoundException e)
			{
				e.printStackTrace();
				return null;
			}
		}
	}

	/**
	 * Returns a RequestDispatcher for the specified path. The dispatcher
	 * will not dispatch to the resource. It only records the specified path
	 * so that one can test if the correct path was dispatched to.
	 *
	 * @param path
	 * 		a String specifying the pathname to the resource
	 *
	 * @return a dispatcher for the specified path
	 */
	@Override
	public RequestDispatcher getRequestDispatcher(String path)
	{
		return new MockRequestDispatcher(path);
	}

	/**
	 * Returns a RequestDispatcher for the specified name. The dispatcher
	 * will not dispatch to the resource. It only records the specified name
	 * so that one can test if the correct name was dispatched to.
	 *
	 * @param name
	 * 		a String specifying the name of a servlet to wrap
	 *
	 * @return a dispatcher for the specified name
	 */
	@Override
	public RequestDispatcher getNamedDispatcher(final String name)
	{
		return getRequestDispatcher(name);
	}

	/**
	 * NOT USED - Servlet Spec requires that this always returns null.
	 *
	 * @param name
	 * 		Not used
	 *
	 * @return null
	 *
	 * @throws ServletException
	 * 		Not used
	 */
	@Override
	public Servlet getServlet(String name) throws ServletException
	{
		return null;
	}

	/**
	 * NOT USED - Servlet spec requires that this always returns null.
	 *
	 * @return null
	 */
	@Override
	public Enumeration<Servlet> getServlets()
	{
		return null;
	}

	/**
	 * NOT USED - Servlet spec requires that this always returns null.
	 *
	 * @return null
	 */
	@Override
	public Enumeration<String> getServletNames()
	{
		return null;
	}

	/**
	 * Log the message to System.out.
	 *
	 * @param msg
	 * 		The message to log
	 */
	@Override
	public void log(String msg)
	{
		System.out.println(msg);
	}

	/**
	 * Log the exception to System.err and the message to System.out.
	 *
	 * @param e
	 * 		The exception to log
	 * @param msg
	 * 		The message to log
	 */
	@Override
	public void log(Exception e, String msg)
	{
		log(msg, e);
	}

	/**
	 * Log the cause to System.err and the message to System.out.
	 *
	 * @param msg
	 * 		The message to log
	 * @param cause
	 * 		The cause exception
	 */
	@Override
	public void log(String msg, Throwable cause)
	{
		log(msg);
		cause.printStackTrace();
	}

	/**
	 * Get the real file path of the given resource name.
	 *
	 * @param name
	 * 		The name
	 *
	 * @return The real path or null
	 */
	@Override
	public String getRealPath(String name)
	{
		if (webappRoot == null)
		{
			return null;
		}

		if (name.startsWith("/"))
		{
			name = name.substring(1);
		}

		File f = new File(webappRoot, name);
		if (!f.exists())
		{
			return null;
		}
		else
		{
			return f.getPath();
		}
	}

	/**
	 * Get the server info.
	 *
	 * @return The server info
	 */
	@Override
	public String getServerInfo()
	{
		return "Click Mock Environment";
	}

	/**
	 * Get the init parameter with the given name.
	 *
	 * @param name
	 * 		The name
	 *
	 * @return The parameter, or null if no such parameter
	 */
	@Override
	public String getInitParameter(final String name)
	{
		return initParameters.get(name);
	}

	/**
	 * Get the name of all of the init parameters.
	 *
	 * @return The init parameter names
	 */
	@Override
	public Enumeration<String> getInitParameterNames()
	{
		return Collections.enumeration(initParameters.keySet());
	}

	@Override
	public boolean setInitParameter(String name, String value)
	{
		return false;
	}

	/**
	 * Get an attribute with the given name.
	 *
	 * @param name
	 * 		The attribute name
	 *
	 * @return The value, or null
	 */
	@Override
	public Object getAttribute(final String name)
	{
		return attributes.get(name);
	}

	/**
	 * Get all of the attribute names.
	 *
	 * @return The attribute names
	 */
	@Override
	public Enumeration<String> getAttributeNames()
	{
		return Collections.enumeration(attributes.keySet());
	}

	/**
	 * Set an attribute.
	 *
	 * @param name
	 * 		The name of the attribute
	 * @param o
	 * 		The value
	 */
	@Override
	public void setAttribute(final String name, final Object o)
	{
		attributes.put(name, o);
	}

	/**
	 * Remove an attribute with the given name.
	 *
	 * @param name
	 * 		The name
	 */
	@Override
	public void removeAttribute(final String name)
	{
		attributes.remove(name);
	}

	/**
	 * Return the name of the servlet context.
	 *
	 * @return The name
	 */
	@Override
	public String getServletContextName()
	{
		return servletContextName;
	}

	@Override
	public ServletRegistration.Dynamic addServlet(String servletName, String className)
	{
		return null;
	}

	@Override
	public ServletRegistration.Dynamic addServlet(String servletName, Servlet servlet)
	{
		return null;
	}

	@Override
	public ServletRegistration.Dynamic addServlet(String servletName, Class<? extends Servlet> servletClass)
	{
		return null;
	}

	@Override
	public ServletRegistration.Dynamic addJspFile(String servletName, String jspFile)
	{
		return null;
	}

	@Override
	public <T extends Servlet> T createServlet(Class<T> clazz) throws ServletException
	{
		return null;
	}

	@Override
	public ServletRegistration getServletRegistration(String servletName)
	{
		return null;
	}

	@Override
	public Map<String, ? extends ServletRegistration> getServletRegistrations()
	{
		return null;
	}

	@Override
	public FilterRegistration.Dynamic addFilter(String filterName, String className)
	{
		return null;
	}

	@Override
	public FilterRegistration.Dynamic addFilter(String filterName, Filter filter)
	{
		return null;
	}

	@Override
	public FilterRegistration.Dynamic addFilter(String filterName, Class<? extends Filter> filterClass)
	{
		return null;
	}

	@Override
	public <T extends Filter> T createFilter(Class<T> clazz) throws ServletException
	{
		return null;
	}

	@Override
	public FilterRegistration getFilterRegistration(String filterName)
	{
		return null;
	}

	@Override
	public Map<String, ? extends FilterRegistration> getFilterRegistrations()
	{
		return null;
	}

	@Override
	public SessionCookieConfig getSessionCookieConfig()
	{
		return null;
	}

	@Override
	public void setSessionTrackingModes(Set<SessionTrackingMode> sessionTrackingModes)
	{

	}

	@Override
	public Set<SessionTrackingMode> getDefaultSessionTrackingModes()
	{
		return null;
	}

	@Override
	public Set<SessionTrackingMode> getEffectiveSessionTrackingModes()
	{
		return null;
	}

	@Override
	public void addListener(String className)
	{

	}

	@Override
	public <T extends EventListener> void addListener(T t)
	{

	}

	@Override
	public void addListener(Class<? extends EventListener> listenerClass)
	{

	}

	@Override
	public <T extends EventListener> T createListener(Class<T> clazz) throws ServletException
	{
		return null;
	}

	@Override
	public JspConfigDescriptor getJspConfigDescriptor()
	{
		return null;
	}

	@Override
	public ClassLoader getClassLoader()
	{
		return null;
	}

	@Override
	public void declareRoles(String... roleNames)
	{

	}

	@Override
	public String getVirtualServerName()
	{
		return null;
	}

	@Override
	public int getSessionTimeout()
	{
		return 0;
	}

	@Override
	public void setSessionTimeout(int sessionTimeout)
	{

	}

	@Override
	public String getRequestCharacterEncoding()
	{
		return null;
	}

	@Override
	public void setRequestCharacterEncoding(String encoding)
	{

	}

	@Override
	public String getResponseCharacterEncoding()
	{
		return null;
	}

	@Override
	public void setResponseCharacterEncoding(String encoding)
	{

	}

	/**
	 * Set the servlet context name to the specified value.
	 *
	 * @param servletContextName
	 * 		the servlet context name
	 */
	public void setServletContextName(String servletContextName)
	{
		this.servletContextName = servletContextName;
	}

	/**
	 * Sets the servlet context path.
	 *
	 * @param contextPath
	 * 		the servlet context path
	 */
	public void setContextPath(String contextPath)
	{
		this.contextPath = contextPath;
	}

}
