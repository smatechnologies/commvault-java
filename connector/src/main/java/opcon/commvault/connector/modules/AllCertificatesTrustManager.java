package opcon.commvault.connector.modules;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.X509TrustManager;

public class AllCertificatesTrustManager implements X509TrustManager {

	@Override
	public void checkClientTrusted(X509Certificate[] arg0, String arg1)
			throws CertificateException {
		
	}

	@Override
	public void checkServerTrusted(X509Certificate[] arg0, String arg1)
			throws CertificateException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public X509Certificate[] getAcceptedIssuers() {
		return null;
	}

	public HostnameVerifier getHostnameVerifier() {
		return new HostnameVerifier() {
			@Override
			public boolean verify(String hostname,	javax.net.ssl.SSLSession sslSession) {
				return true;
			}
		};
	}

}
