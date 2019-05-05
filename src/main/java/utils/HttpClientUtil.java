package utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.CodingErrorAction;
import java.security.KeyStore;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.lang.StringUtils;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.MessageConstraints;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 说明：
 * @author xiayisan 
 * @version 创建时间: 2015年1月26日 下午3:56:38
 * 
 */
@SuppressWarnings("deprecation")
public final class HttpClientUtil
{

	private final static Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);

	public static String configFileName = "/httpClientUtil.properties";

	// http client
	public static PoolingHttpClientConnectionManager httpConnManager = null;
	private static CloseableHttpClient httpClient = null;
	private static int POOL_MAX_TOTAL = 300;
	private static int POOL_MAX_PER_ROUTE = 100;

	// https client with cert
	public static PoolingHttpClientConnectionManager httpsCertConnManager = null;
	private static CloseableHttpClient httpsCertClient = null;
	private static int HTTPS_CERT_POOL_MAX_TOTAL = 100;
	private static int HTTPS_CERT_POOL_MAX_PER_ROUTE = 50;
	private static String HTTPS_CERT_FILE = null;
	private static String HTTPS_CERT_KEYSTORE_TYPE = "jks";
	private static String HTTPS_CERT_PASSWORD = null;

	// common config
	public static int HTTP_TIMEOUT = 5000;
	public static int SO_TIMEOUT = 5000;
	private static int KEEP_ALIVE = 30000;

	static
	{
		initConfig();
		initHttpClient();
		initHttpsCertClient();
	}

	/**
	 * 配置文件
	 */
	private static void initConfig()
	{
		PropertiesUtil propertiesUtil = PropertiesUtil.newInstance(configFileName);

		String POOL_MAX_TOTAL_STR = propertiesUtil.getValue("POOL_MAX_TOTAL");
		if (StringUtils.isNotBlank(POOL_MAX_TOTAL_STR))
		{
			POOL_MAX_TOTAL = Integer.valueOf(POOL_MAX_TOTAL_STR);
		}
		logger.info("[HttpClient init] Http conn pool max total: " + POOL_MAX_TOTAL);

		String POOL_MAX_PER_ROUTE_STR = propertiesUtil.getValue("POOL_MAX_PER_ROUTE");
		if (StringUtils.isNotBlank(POOL_MAX_PER_ROUTE_STR))
		{
			POOL_MAX_PER_ROUTE = Integer.valueOf(POOL_MAX_PER_ROUTE_STR);
		}
		logger.info("[HttpClient init] Http conn pool max per route: " + POOL_MAX_PER_ROUTE);

		String HTTPS_CERT_POOL_MAX_TOTAL_STR = propertiesUtil.getValue("HTTPS_CERT_POOL_MAX_TOTAL");
		if (StringUtils.isNotBlank(HTTPS_CERT_POOL_MAX_TOTAL_STR))
		{
			HTTPS_CERT_POOL_MAX_TOTAL = Integer.valueOf(HTTPS_CERT_POOL_MAX_TOTAL_STR);
		}
		logger.info("[HttpClient init] Https cert conn pool max total: " + HTTPS_CERT_POOL_MAX_TOTAL);

		String HTTPS_CERT_POOL_MAX_PER_ROUTE_STR = propertiesUtil.getValue("HTTPS_CERT_POOL_MAX_PER_ROUTE");
		if (StringUtils.isNotBlank(HTTPS_CERT_POOL_MAX_PER_ROUTE_STR))
		{
			HTTPS_CERT_POOL_MAX_PER_ROUTE = Integer.valueOf(HTTPS_CERT_POOL_MAX_PER_ROUTE_STR);
		}
		logger.info("[HttpClient init] Https cert conn pool max per route: " + HTTPS_CERT_POOL_MAX_PER_ROUTE);

		String HTTPS_CERT_FILE_STR = propertiesUtil.getValue("HTTPS_CERT_FILE");
		if (StringUtils.isNotBlank(HTTPS_CERT_FILE_STR))
		{
			HTTPS_CERT_FILE = HTTPS_CERT_FILE_STR;
		}
		logger.info("[HttpClient init] Https cert file: " + HTTPS_CERT_FILE);

		String HTTPS_CERT_KEYSTORE_TYPE_STR = propertiesUtil.getValue("HTTPS_CERT_KEYSTORE_TYPE");
		if (StringUtils.isNotBlank(HTTPS_CERT_KEYSTORE_TYPE_STR))
		{
			HTTPS_CERT_KEYSTORE_TYPE = HTTPS_CERT_KEYSTORE_TYPE_STR;
		}
		logger.info("[HttpClient init] Https cert keystore type: " + HTTPS_CERT_KEYSTORE_TYPE);

		String HTTPS_CERT_PASSWORD_STR = propertiesUtil.getValue("HTTPS_CERT_PASSWORD");
		if (StringUtils.isNotBlank(HTTPS_CERT_PASSWORD_STR))
		{
			HTTPS_CERT_PASSWORD = HTTPS_CERT_PASSWORD_STR;
		}
		logger.info("[HttpClient init] Https cert password: " + HTTPS_CERT_PASSWORD);
	}

	/**
	 * 初始化httpClient对象
	 */
	private static void initHttpClient()
	{
		try
		{
			SSLContext sslContext = SSLContexts.custom().useTLS().build();
			sslContext.init(null, new TrustManager[] { new X509TrustManager()
			{

				public X509Certificate[] getAcceptedIssuers()
				{
					return null;
				}

				public void checkClientTrusted(X509Certificate[] certs, String authType)
				{
				}

				public void checkServerTrusted(X509Certificate[] certs, String authType)
				{
				}
			} }, null);
			
			X509HostnameVerifier hostnameverifier = new X509HostnameVerifier()
			{
				public boolean verify(String arg0, SSLSession arg1)
				{
					return true;
				}

				@Override
				public void verify(String arg0, SSLSocket arg1) throws IOException
				{
				}

				@Override
				public void verify(String arg0, X509Certificate arg1) throws SSLException
				{
				}

				@Override
				public void verify(String arg0, String[] arg1, String[] arg2) throws SSLException
				{
				}
			};
			
			Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder
					.<ConnectionSocketFactory> create().register("http", PlainConnectionSocketFactory.INSTANCE)
					.register("https", new SSLConnectionSocketFactory(sslContext,hostnameverifier)).build();

			ConnectionKeepAliveStrategy keepAliveStrategy = new DefaultConnectionKeepAliveStrategy()
			{
				@Override
				public long getKeepAliveDuration(HttpResponse response, HttpContext context)
				{
					long keepAlive = super.getKeepAliveDuration(response, context);
					if (keepAlive == -1)
					{
						// Keep connections alive KEEP_ALIVE milliseconds if a
						// keep-alive value has not be explicitly set by the
						// server
						keepAlive = KEEP_ALIVE;
					}
					return keepAlive;
				}
			};

			httpConnManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
			httpClient = HttpClients.custom().setConnectionManager(httpConnManager)
					.setKeepAliveStrategy(keepAliveStrategy).build();
			// Create socket configuration
			SocketConfig socketConfig = SocketConfig.custom().setTcpNoDelay(true).setSoKeepAlive(true)
					.setSoReuseAddress(true).build();
			httpConnManager.setDefaultSocketConfig(socketConfig);
			// Create message constraints
			MessageConstraints messageConstraints = MessageConstraints.custom().setMaxHeaderCount(200)
					.setMaxLineLength(2000).build();
			// Create connection configuration
			ConnectionConfig connectionConfig = ConnectionConfig.custom()
					.setMalformedInputAction(CodingErrorAction.IGNORE)
					.setUnmappableInputAction(CodingErrorAction.IGNORE).setCharset(Consts.UTF_8)
					.setMessageConstraints(messageConstraints).build();
			httpConnManager.setDefaultConnectionConfig(connectionConfig);

			httpConnManager.setMaxTotal(POOL_MAX_TOTAL);
			httpConnManager.setDefaultMaxPerRoute(POOL_MAX_PER_ROUTE);
		} catch (Exception e)
		{
			logger.error("InitHttpClient Exception:", e);
		}
	}

	/**
	 * 初始化httpsCertClient对象
	 */
	private static void initHttpsCertClient()
	{
		if (StringUtils.isBlank(HTTPS_CERT_FILE))
		{
			return;
		}
		try
		{
			KeyStore ks = KeyStore.getInstance(HTTPS_CERT_KEYSTORE_TYPE);
			ks.load(new FileInputStream(HTTPS_CERT_FILE), HTTPS_CERT_PASSWORD.toCharArray());
			KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
			kmf.init(ks, HTTPS_CERT_PASSWORD.toCharArray());

			SSLContext sslContext = SSLContexts.custom().useTLS().build();
			sslContext.init(kmf.getKeyManagers(), new TrustManager[] { new X509TrustManager()
			{
				public X509Certificate[] getAcceptedIssuers()
				{
					return null;
				}

				public void checkClientTrusted(X509Certificate[] certs, String authType)
				{
				}

				public void checkServerTrusted(X509Certificate[] certs, String authType)
				{
				}
			} }, null);

			X509HostnameVerifier hostnameverifier = new X509HostnameVerifier()
			{
				public boolean verify(String arg0, SSLSession arg1)
				{
					return true;
				}

				@Override
				public void verify(String arg0, SSLSocket arg1) throws IOException
				{
				}

				@Override
				public void verify(String arg0, X509Certificate arg1) throws SSLException
				{
				}

				@Override
				public void verify(String arg0, String[] arg1, String[] arg2) throws SSLException
				{
				}
			};

			Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder
					.<ConnectionSocketFactory> create()
					.register("https", new SSLConnectionSocketFactory(sslContext, hostnameverifier)).build();

			ConnectionKeepAliveStrategy keepAliveStrategy = new DefaultConnectionKeepAliveStrategy()
			{
				@Override
				public long getKeepAliveDuration(HttpResponse response, HttpContext context)
				{
					long keepAlive = super.getKeepAliveDuration(response, context);
					if (keepAlive == -1)
					{
						// Keep connections alive KEEP_ALIVE milliseconds if a
						// keep-alive value has not be explicitly set by the
						// server
						keepAlive = KEEP_ALIVE;
					}
					return keepAlive;
				}
			};

			httpsCertConnManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
			httpsCertClient = HttpClients.custom().setConnectionManager(httpsCertConnManager)
					.setKeepAliveStrategy(keepAliveStrategy).build();
			// Create socket configuration
			SocketConfig socketConfig = SocketConfig.custom().setTcpNoDelay(true).setSoKeepAlive(true)
					.setSoReuseAddress(true).build();
			httpsCertConnManager.setDefaultSocketConfig(socketConfig);
			// Create message constraints
			MessageConstraints messageConstraints = MessageConstraints.custom().setMaxHeaderCount(200)
					.setMaxLineLength(2000).build();
			// Create connection configuration
			ConnectionConfig connectionConfig = ConnectionConfig.custom()
					.setMalformedInputAction(CodingErrorAction.IGNORE)
					.setUnmappableInputAction(CodingErrorAction.IGNORE).setCharset(Consts.UTF_8)
					.setMessageConstraints(messageConstraints).build();
			httpsCertConnManager.setDefaultConnectionConfig(connectionConfig);

			httpsCertConnManager.setMaxTotal(HTTPS_CERT_POOL_MAX_TOTAL);
			httpsCertConnManager.setDefaultMaxPerRoute(HTTPS_CERT_POOL_MAX_PER_ROUTE);
		} catch (Exception e)
		{
			logger.error("InitHttpsCertClient Exception: ", e);
		}
	}

	/**
	 * invokePost
	 * 
	 * @param url
	 * @param params
	 * @param encode
	 * @param connectTimeout
	 * @param soTimeout
	 * @return success return content get from response and failed return null
	 */
	public static String invokePost(String url, Map<String, Object> params, String encode, int connectTimeout,
			int soTimeout)
	{
		return invokePost(url, params, null, encode, connectTimeout, soTimeout);
	}

	/**
	 * @param url
	 * @param params
	 * @param headers
	 * @param encode
	 * @param connectTimeout
	 * @param soTimeout
	 * @return
	 */
	public static String invokePost(String url, Map<String, Object> params, Map<String, String> headers, String encode,
			int connectTimeout, int soTimeout)
	{

		StringBuilder sb = new StringBuilder();
		sb.append("url : ").append(url).append(", params : ").append(params.toString());

		logger.info("[HttpUtils POST] Begin post, " + sb.toString());
		List<NameValuePair> paramPairs = new ArrayList<NameValuePair>();
		if (params != null && !params.isEmpty())
		{
			for (Map.Entry<String, Object> entry : params.entrySet())
			{
				BasicNameValuePair param = new BasicNameValuePair(entry.getKey(), String.valueOf(entry.getValue()));
				paramPairs.add(param);
			}
		}
		HttpPost httpPost = new HttpPost(url);
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(soTimeout)
				.setConnectTimeout(connectTimeout).setConnectionRequestTimeout(connectTimeout)
				.setExpectContinueEnabled(false).build();
		httpPost.setConfig(requestConfig);

		if (headers != null && !headers.isEmpty())
		{
			for (Entry<String, String> entry : headers.entrySet())
			{
				httpPost.setHeader(entry.getKey(), entry.getValue());
			}
		}

		try
		{
			httpPost.setEntity(new UrlEncodedFormEntity(paramPairs, StringUtils.isEmpty(encode) ? Consts.UTF_8.name()
					: encode));
			CloseableHttpResponse response = httpClient.execute(httpPost);
			try
			{
				int statusCode = response.getStatusLine().getStatusCode();
				logger.info("[HttpUtils POST] RequestUri : " + url + ", Response status code : " + statusCode);
				if (statusCode == HttpStatus.SC_OK)
				{
					HttpEntity entity = response.getEntity();
					if (entity != null)
					{
						String str = EntityUtils.toString(entity, Consts.UTF_8);
						logger.info("[HttpUtils POST] RequestUri : " + url + ", Response content : " + str);
						return str;
					}
				} else
				{
					httpPost.abort();
				}
			} finally
			{
				if (response != null)
				{
					response.close();
				}
			}
		} catch (UnsupportedEncodingException e)
		{
			logger.error("UnsupportedEncodingException", e);
		} catch (Exception e)
		{
			logger.error("Exception", e);
		} finally
		{
			httpPost.releaseConnection();
		}
		return null;

	}

	/**
	 * 
	 * @param url
	 * @param content
	 *            要post的字符串
	 * @param encode
	 * @param connectTimeout
	 * @param soTimeout
	 * @return
	 */
	public static String invokePost(String url, String content, String encode, int connectTimeout, int soTimeout)
	{
		return invokePost(url, content, null, encode, connectTimeout, soTimeout);
	}

	/**
	 * 
	 * @param url
	 * @param content
	 *            要post的字符串
	 * @param headers
	 * @param encode
	 * @param connectTimeout
	 * @param soTimeout
	 * @return
	 */
	public static String invokePost(String url, String content, Map<String, String> headers, String encode,
			int connectTimeout, int soTimeout)
	{
		if (content == null || content.length() == 0)
		{
			throw new IllegalArgumentException();
		}
		HttpPost httpPost = new HttpPost(url);
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(soTimeout)
				.setConnectTimeout(connectTimeout).setConnectionRequestTimeout(connectTimeout)
				.setExpectContinueEnabled(false).build();
		httpPost.setConfig(requestConfig);

		if (headers != null && !headers.isEmpty())
		{
			for (Entry<String, String> entry : headers.entrySet())
			{
				httpPost.setHeader(entry.getKey(), entry.getValue());
			}
		}
		encode = StringUtils.isEmpty(encode) ? Consts.UTF_8.name() : encode;

		try
		{
			HttpEntity requestEntity = new StringEntity(content, encode);
			httpPost.setEntity(requestEntity);
			CloseableHttpResponse response = httpClient.execute(httpPost);
			try
			{
				int statusCode = response.getStatusLine().getStatusCode();
				logger.info("[HttpUtils POST] RequestUri : " + url + ", Response status code : " + statusCode);
				if (statusCode == HttpStatus.SC_OK)
				{
					HttpEntity entity = response.getEntity();
					if (entity != null)
					{
						String str = EntityUtils.toString(entity, Consts.UTF_8);
						logger.info("[HttpUtils POST] RequestUri : " + url + ", Response content : " + str);
						return str;
					}
				} else
				{
					httpPost.abort();
				}
			} finally
			{
				if (response != null)
				{
					response.close();
				}
			}
		} catch (UnsupportedEncodingException e)
		{
			logger.error("UnsupportedEncodingException", e);
		} catch (Exception e)
		{
			logger.error("Exception", e);
		} finally
		{
			httpPost.releaseConnection();
		}
		return null;
	}

	/**
	 * 
	 * @param url
	 * @param content
	 *            要post的字符串
	 * @param headers
	 * @param encode
	 * @param connectTimeout
	 * @param soTimeout
	 * @return
	 */
	public static String invokePostWithCert(String url, String content, Map<String, String> headers, String encode,
			int connectTimeout, int soTimeout)
	{
		if (content == null || content.length() == 0)
		{
			throw new IllegalArgumentException();
		}
		HttpPost httpPost = new HttpPost(url);
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(soTimeout)
				.setConnectTimeout(connectTimeout).setConnectionRequestTimeout(connectTimeout)
				.setExpectContinueEnabled(false).build();
		httpPost.setConfig(requestConfig);

		if (headers != null && !headers.isEmpty())
		{
			for (Entry<String, String> entry : headers.entrySet())
			{
				httpPost.setHeader(entry.getKey(), entry.getValue());
			}
		}
		encode = StringUtils.isEmpty(encode) ? Consts.UTF_8.name() : encode;

		try
		{
			HttpEntity requestEntity = new InputStreamEntity(new ByteArrayInputStream(content.getBytes(encode)));
			httpPost.setEntity(requestEntity);
			CloseableHttpResponse response = httpsCertClient.execute(httpPost);
			try
			{
				int statusCode = response.getStatusLine().getStatusCode();
				logger.info("[HttpUtils POST] RequestUri : " + url + ", Response status code : " + statusCode);
				if (statusCode == HttpStatus.SC_OK)
				{
					HttpEntity entity = response.getEntity();
					if (entity != null)
					{
						String str = EntityUtils.toString(entity, Consts.UTF_8);
						logger.info("[HttpUtils POST] RequestUri : " + url + ", Response content : " + str);
						return str;
					}
				} else
				{
					httpPost.abort();
				}
			} finally
			{
				if (response != null)
				{
					response.close();
				}
			}
		} catch (UnsupportedEncodingException e)
		{
			logger.error("UnsupportedEncodingException", e);
		} catch (Exception e)
		{
			logger.error("Exception", e);
		} finally
		{
			httpPost.releaseConnection();
		}
		return null;
	}

	/**
	 * INVOKE GET
	 * 
	 * @param url
	 * @param params
	 * @param encode
	 * @param connectTimeout
	 * @param soTimeout
	 * @return success return content get from response and failed return null;
	 */
	public static String invokeGet(String url, Map<String, String> params, String encode, int connectTimeout,
			int soTimeout)
	{
		return invokeGet(url, params, null, encode, connectTimeout, soTimeout);
	}

	/**
	 * @param url
	 * @param params
	 * @param headers
	 * @param encode
	 * @param connectTimeout
	 * @param soTimeout
	 * @return
	 */
	@Deprecated
	public static String invokeGet(String url, Map<String, String> params, Map<String, String> headers, String encode,
			int connectTimeout, int soTimeout)
	{
		String responseString = null;
		StringBuilder sb = new StringBuilder();
		sb.append(url);
		int i = 0;
		for (Entry<String, String> entry : params.entrySet())
		{
			if (i == 0 && !url.contains("?"))
			{
				sb.append("?");
			} else
			{
				sb.append("&");
			}
			sb.append(entry.getKey());
			sb.append("=");
			String value = entry.getValue();
			try
			{
				sb.append(URLEncoder.encode(value, "UTF-8"));
			} catch (UnsupportedEncodingException e)
			{
				logger.warn("encode http get params error, value is " + value, e);
				sb.append(URLEncoder.encode(value));
			}
			i++;
		}
		System.out.println("[HttpUtils Get] begin invoke:" + sb.toString());
		HttpGet httpGet = new HttpGet(sb.toString());
		RequestConfig config = RequestConfig.custom().setSocketTimeout(soTimeout).setConnectTimeout(connectTimeout)
				.setConnectionRequestTimeout(connectTimeout).build();
		httpGet.setConfig(config);

		if (headers != null && !headers.isEmpty())
		{
			for (Entry<String, String> entry : headers.entrySet())
			{
				httpGet.setHeader(entry.getKey(), entry.getValue());
			}
		}

		try
		{
			CloseableHttpResponse response = httpClient.execute(httpGet);
			try
			{
				int statusCode = response.getStatusLine().getStatusCode();
				logger.info("[HttpUtils Get] RequestUri : " + sb.toString() + ", Response status code : " + statusCode);
				if (statusCode == HttpStatus.SC_OK)
				{
					HttpEntity entity = response.getEntity();
					if (entity != null)
					{
						responseString = EntityUtils.toString(entity, encode);
//						logger.info("[HttpUtils Get] RequestUri : " + sb.toString() + ", ResponseContent : "
//								+ responseString);
						return responseString;
					}
				} else
				{
					httpGet.abort();
				}
			} finally
			{
				if (response != null)
				{
					response.close();
				}
			}
		} catch (SocketTimeoutException e)
		{
			logger.error(String.format("[HttpUtils Get]invoke get timout error, url:%s", sb.toString()), e);
		} catch (Exception e)
		{
			logger.error(String.format("[HttpUtils Get]invoke get error, url:%s", sb.toString()), e);
		} finally
		{
			httpGet.releaseConnection();
		}

		return responseString;
	}

	/**
	 * HTTPS请求 废除，可以使用invokePost代替
	 * 
	 * @param url
	 * @param params
	 * @param connectTimeout
	 * @param soTimeout
	 * @return success return content get from response and failed return null
	 */
	@Deprecated
	public static String invokeHttpsPost(String url, Map<String, String> params, int connectTimeout, int soTimeout)
	{
		String responseContent = null;
		HttpPost httpPost = new HttpPost(url);
		try
		{
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(soTimeout)
					.setConnectTimeout(connectTimeout).setConnectionRequestTimeout(connectTimeout)
					.setExpectContinueEnabled(false).build();

			List<NameValuePair> formParams = new ArrayList<NameValuePair>();
			httpPost.setConfig(requestConfig);
			for (Map.Entry<String, String> entry : params.entrySet())
			{
				formParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			}
			httpPost.setEntity(new UrlEncodedFormEntity(formParams, Consts.UTF_8));
			logger.info("[HttpUtils https post], uri : " + httpPost.getURI() + ", params:" + params);
			CloseableHttpResponse response = httpClient.execute(httpPost);
			try
			{
				if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
				{
					HttpEntity entity = response.getEntity();
					if (null != entity)
					{
						responseContent = EntityUtils.toString(entity, Consts.UTF_8);
						logger.info("requestURI : " + httpPost.getURI() + ", responseContent: " + responseContent);
					}
				} else
				{
					httpPost.abort();
				}
			} finally
			{
				if (response != null)
				{
					response.close();
				}
			}
		} catch (ClientProtocolException e)
		{
			logger.error("ClientProtocolException", e);
		} catch (IOException e)
		{
			logger.error("IOException", e);
		} finally
		{
			httpPost.releaseConnection();
		}
		return responseContent;
	}

	/**
	 * HTTPS请求 废除，可以使用invokeGet替换
	 * 
	 * @param url
	 * @param connectTimeout
	 * @param soTimeout
	 * @return success return content get from response and failed return null
	 */
	@Deprecated
	public static String invokeHttpsGet(String url, int connectTimeout, int soTimeout)
	{
		String responseContent = null;
		HttpGet httpGet = new HttpGet(url);
		try
		{
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(soTimeout)
					.setConnectTimeout(connectTimeout).setConnectionRequestTimeout(connectTimeout)
					.setExpectContinueEnabled(false).build();

			httpGet.setConfig(requestConfig);
			logger.info("[HttpUtils https get], uri : " + httpGet.getURI());
			CloseableHttpResponse response = httpClient.execute(httpGet);
			try
			{
				if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
				{
					HttpEntity entity = response.getEntity();
					if (null != entity)
					{
						responseContent = EntityUtils.toString(entity, Consts.UTF_8);
						logger.info("requestURI : " + httpGet.getURI() + ", responseContent: " + responseContent);
					}
				} else
				{
					httpGet.abort();
				}
			} finally
			{
				if (response != null)
				{
					response.close();
				}
			}
		} catch (ClientProtocolException e)
		{
			logger.error("ClientProtocolException", e);
		} catch (IOException e)
		{
			logger.error("IOException", e);
		} finally
		{
			httpGet.releaseConnection();
		}
		return responseContent;
	}

	/**
	 * 多媒体格式的https请求
	 * 
	 * @param file
	 * @param url
	 * @param params
	 * @param connectTimeout
	 * @param soTimeout
	 * @return
	 */
	public static String invoteHttpsPostForMultiPart(File file, String url, Map<String, String> params,
			int connectTimeout, int soTimeout)
	{
		return invoteHttpsPostForMultiPart(file, url, params, null, connectTimeout, soTimeout);
	}

	/**
	 * 多媒体格式的https请求
	 * 
	 * @param file
	 * @param url
	 * @param params
	 * @param headers
	 * @param connectTimeout
	 * @param soTimeout
	 * @return
	 */
	public static String invoteHttpsPostForMultiPart(File file, String url, Map<String, String> params,
			Map<String, String> headers, int connectTimeout, int soTimeout)
	{
		String responseContent = null; // 响应内容
		HttpPost httpPost = new HttpPost(url); // 创建HttpPost
		try
		{
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(soTimeout)
					.setConnectTimeout(connectTimeout).setConnectionRequestTimeout(connectTimeout)
					.setExpectContinueEnabled(false).build();
			httpPost.setConfig(requestConfig);

			if (headers != null && !headers.isEmpty())
			{
				for (Entry<String, String> entry : headers.entrySet())
				{
					httpPost.setHeader(entry.getKey(), entry.getValue());
				}
			}

			MultipartEntity reqEntity = new MultipartEntity();
			FileBody fileBody = new FileBody(file);
			reqEntity.addPart("pic", fileBody);
			for (Map.Entry<String, String> entry : params.entrySet())
			{
				StringBody sbody = new StringBody(entry.getValue(), Charset.forName("UTF-8"));
				reqEntity.addPart(entry.getKey(), sbody);
			}
			httpPost.setEntity(reqEntity);
			CloseableHttpResponse response = httpClient.execute(httpPost);
			try
			{
				HttpEntity entity = response.getEntity();
				if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
				{
					if (null != entity)
					{
						responseContent = EntityUtils.toString(entity, Consts.UTF_8);
						logger.info("requestURI : " + httpPost.getURI() + ", responseContent: " + responseContent);
					}
				} else
				{
					httpPost.abort();
				}
			} finally
			{
				if (response != null)
				{
					response.close();
				}
			}
		} catch (ClientProtocolException e)
		{
			logger.error("ClientProtocolException", e);
		} catch (IOException e)
		{
			logger.error("IOException", e);
		} finally
		{
			httpPost.releaseConnection();
		}
		return responseContent;
	}

	/**
	 * 消息推送功能
	 * 
	 * @param url
	 * @param uuid
	 * @param value
	 * @param type
	 *            0:tile, 1:toast
	 * @param connectTimeout
	 *            连接超时
	 * @return 200:推送成功，404：推送失败，设备已移除，其他code：推送失败，其他错误
	 */
	// public static ResponseCode wpPush(String url, String value, int type, int
	// connectTimeout) {
	// ResponseCode code = new ResponseCode();
	// HttpPost post = new HttpPost(url);
	// try {
	// RequestConfig requestConfig =
	// RequestConfig.custom().setSocketTimeout(connectTimeout)
	// .setConnectTimeout(connectTimeout).setConnectionRequestTimeout(connectTimeout)
	// .setExpectContinueEnabled(false).build();
	// HttpEntity uefEntity = new StringEntity(value, "UTF-8");
	// post.setHeader("Content-type", "xml; charset=UTF-8");
	// if (type == 0) {
	// post.setHeader("X-WindowsPhone-Target", "token");
	// post.setHeader("X-NotificationClass", "1");
	// } else if (type == 1) {
	// post.setHeader("X-WindowsPhone-Target", "toast");
	// post.setHeader("X-NotificationClass", "2");
	// }
	// post.setEntity(uefEntity);
	// post.setConfig(requestConfig);
	// CloseableHttpResponse response = httpClient.execute(post);
	// int statusCode = response.getStatusLine().getStatusCode();
	// logger.info("headers:" + response.getAllHeaders());
	// logger.info("push statusCode : " + statusCode);
	// String connectionStatus =
	// response.getLastHeader("X-DeviceConnectionStatus") == null ? "" :
	// response
	// .getLastHeader("X-DeviceConnectionStatus").getValue();
	// String notificationStatus =
	// response.getLastHeader("X-NotificationStatus") == null ? "" : response
	// .getLastHeader("X-NotificationStatus").getValue();
	// String subscriptionStatus =
	// response.getLastHeader("X-SubscriptionStatus") == null ? "" : response
	// .getLastHeader("X-SubscriptionStatus").getValue();
	// response.close();
	// code.setCode(statusCode);
	// code.setMessage("X-DeviceConnectionStatus:" + connectionStatus +
	// ",X-NotificationStatus:"
	// + notificationStatus + ",X-SubscriptionStatus:" + subscriptionStatus);
	// if (statusCode == 404) {
	// if (connectionStatus.equalsIgnoreCase("Connected") &&
	// notificationStatus.equalsIgnoreCase("Dropped")
	// && subscriptionStatus.equalsIgnoreCase("Expired")) {
	// logger.info("this device need to shutDown push");
	// code.setMessage(code.getMessage() + ", response code:" + statusCode);
	// code.setCode(999);
	// return code;
	// }
	// }
	// if ((statusCode == 200 &&
	// notificationStatus.equalsIgnoreCase("QueueFull")) || statusCode == 412
	// || statusCode == 503) {
	// logger.info("this device need to send push again");
	// code.setMessage(code.getMessage() + ", response code:" + statusCode);
	// code.setCode(888);
	// return code;
	// }
	// logger.info(code.getMessage());
	// return code;
	// } catch (UnsupportedEncodingException e) {
	// logger.error("UnsupportedEncodingException", e);
	// } catch (Exception e) {
	// logger.error("Exception", e);
	// } finally {
	// post.releaseConnection();
	// }
	// code.setCode(500);
	// code.setMessage("push error");
	// return code;
	// }

	public static void main(String[] args) throws InterruptedException
	{
		// System.out.println(ss);

		// IdleConnectionMonitorServletListener idleConnectionMonitorListener =
		// new IdleConnectionMonitorServletListener();
		// idleConnectionMonitorListener.contextInitialized(null);
		//
		// ExecutorService executor = Executors.newFixedThreadPool(5);
		// for(int i=0; i<5; i++){
		// final int x = i;
		// executor.execute(new Runnable() {
		//
		// @Override
		// public void run() {
		// invokeGet("http://user.laohu.com/m/charge/nopending?p="+x, new
		// HashMap<String, String>(), "UTF-8", 1000, 1000);
		// }
		// });
		// }
		// invokeGet("http://api.dev.laohu.com/", new HashMap<String, String>(),
		// "UTF-8", 5000, 5000);

		// System.out.println("---------------------------------------------------thread 5 over!");
		//
		// TimeUnit.SECONDS.sleep(10);
		//
		// System.out.println("HttpClientUtil connManager.getTotalStats() ： "+connManager.getTotalStats());
		//
		// idleConnectionMonitorListener.contextDestroyed(null);

		// for(int i=5; i<10; i++){
		// final int x = i;
		// executor.execute(new Runnable() {
		//
		// @Override
		// public void run() {
		// invokeGet("http://user.laohu.com/m/charge/nopending?p="+x, new
		// HashMap<String, String>(), "UTF-8", 1000, 1000);
		// }
		// });
		// }
		//
		// System.out.println("---------------------------------------------------thread 2 over!");
		// TimeUnit.SECONDS.sleep(30);
		//
		// System.out.println("HttpClientUtil connManager.getTotalStats() ： "+connManager.getTotalStats());
		//
		// for(int i=10; i<15; i++){
		// final int x = i;
		// executor.execute(new Runnable() {
		//
		// @Override
		// public void run() {
		// invokeGet("http://user.laohu.com/m/charge/nopending?p="+x, new
		// HashMap<String, String>(), "UTF-8", 1000, 1000);
		// }
		// });
		// }

		// executor.shutdown();
	}
}
