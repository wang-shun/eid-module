package com.eid.anonymous.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;

/**
 * HttpClient Connection Management Utility
 * 
 */
public class HttpClientUtil {
	public final static String JSON_TYPE = "json";

	private final static int MAX_TOTAL_CONNECTIONS = 800;
	private final static int WAIT_TIMEOUT = 300000;
	private final static int MAX_ROUT_CONNECTIONS = 400;
	private final static int CONNECT_TIMEOUT = 300000;
	private final static int READ_TIMEOUT = 300000;
	private final static String JSON_UTF8 = "application/json;charset=UTF-8";
	private static BasicHttpParams httpParams = null;
	private static DefaultHttpClient httpClient = null;
	private static ThreadSafeClientConnManager clientConnectionManager = null;

	private final static Log log = LogFactory.getLog(HttpClientUtil.class);

	/**
	 * Initialize httpClient pool
	 */
	static {
		try {
			httpParams = new BasicHttpParams();
			httpParams.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,
					WAIT_TIMEOUT);

			HttpConnectionParams.setConnectionTimeout(httpParams,
					CONNECT_TIMEOUT);
			HttpConnectionParams.setSoTimeout(httpParams, READ_TIMEOUT);

			SchemeRegistry schemeRegistry = new SchemeRegistry();
			schemeRegistry.register(new Scheme("http", 80, PlainSocketFactory
					.getSocketFactory()));

			SSLContext sslContext = SSLContext.getInstance("SSL");
			sslContext.init(null,
					new TrustManager[] { new TrustAnyTrustManager() }, null);
			SSLSocketFactory sslSf = new SSLSocketFactory(sslContext,
					SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			schemeRegistry.register(new Scheme("https", 443, sslSf));

			clientConnectionManager = new ThreadSafeClientConnManager(
					schemeRegistry);
			clientConnectionManager.setDefaultMaxPerRoute(MAX_ROUT_CONNECTIONS);
			clientConnectionManager.setMaxTotal(MAX_TOTAL_CONNECTIONS);

			httpClient = new DefaultHttpClient(clientConnectionManager,
					httpParams);

		} catch (Exception e) {
			log.error("Fail to Connect !!");
		}
	}

	/**
	 * <p>
	 * Get httpClient for initializing.
	 * </p>
	 * 
	 * @return HttpClient
	 */
	public static HttpClient getHttpClient() {
		return httpClient;
	}

	/**
	 * <p>
	 * Close the clientConnectionManager
	 * </p>
	 */
	public static void release() {
		if (clientConnectionManager != null) {
			clientConnectionManager.shutdown();
		}
	}

	/**
	 * 
	 * @param requestType
	 *            (HttpClientUtil.JSON_TYPE,HttpClientUtil.XML_TYPE)
	 * @param requestUrl
	 * @param requestStr
	 * @return
	 * @throws Exception
	 */
	public static String httpclientRequest(String requestType,
			String requestUrl, String requestStr) throws Exception {
		StringEntity strEntity;
		try {
			strEntity = new StringEntity(requestStr, HTTP.UTF_8);
			Header header = new BasicHeader("Content-Type", JSON_UTF8);
			strEntity.setContentType(header);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("result unsupportedEncodingException");
		}
		return httpclientRequest(strEntity, requestUrl);
	}

	/**
	 * 
	 * @param nameValuePairList
	 * @param requestUrl
	 * @return
	 * @throws Exception
	 */
	public static String httpclientRequest(
			List<NameValuePair> nameValuePairList, String requestUrl)
			throws Exception {
		UrlEncodedFormEntity uefEntity;
		try {
			uefEntity = new UrlEncodedFormEntity(nameValuePairList, HTTP.UTF_8);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("result unsupportedEncodingException");
		}
		return httpclientRequest(uefEntity, requestUrl);
	}

	private static String httpclientRequest(HttpEntity httpEntity,
			String requestUrl) throws Exception {
		HttpClient httpClient = HttpClientUtil.getHttpClient();
		HttpPost httpPost = new HttpPost(requestUrl);
		try {
			httpPost.setEntity(httpEntity);
			HttpResponse httpPostResponse = httpClient.execute(httpPost);
			int code = httpPostResponse.getStatusLine().getStatusCode();
			log.debug("result status: " + httpPostResponse.getStatusLine());
			if (code == 200) {
				return EntityUtils.toString(httpPostResponse.getEntity(),
						HTTP.UTF_8);
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("result exception");
		} finally {
			if (!httpPost.isAborted())
				httpPost.abort();
		}
	}

	private static class TrustAnyTrustManager implements X509TrustManager {
		@Override
		public void checkClientTrusted(X509Certificate[] chain, String authType)
				throws CertificateException {
		}

		@Override
		public void checkServerTrusted(X509Certificate[] chain, String authType)
				throws CertificateException {
		}

		@Override
		public X509Certificate[] getAcceptedIssuers() {
			return new X509Certificate[] {};
		}
	}

}
