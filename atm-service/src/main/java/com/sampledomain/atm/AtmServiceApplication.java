package com.sampledomain.atm;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.security.cert.CertificateException;

@EnableSwagger2
@SpringBootApplication
//@EnableEurekaClient
public class AtmServiceApplication {
	@Value("${trust.store}")
	private Resource trustStore;

	@Value("${trust.store.password}")
	private String trustStorePassword;
	public static void main(String[] args) {
		SpringApplication.run(AtmServiceApplication.class, args);
	}

	@Bean
	public Docket productApi() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.sampledomain.atm")).build();
	}

	@Bean
	@LoadBalanced
	RestTemplate restTemplate() throws Exception {
		SSLContext sslContext = new SSLContextBuilder()
				.loadTrustMaterial(trustStore.getURL(), trustStorePassword.toCharArray())
				.build();
		SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(sslContext);
		HttpClient httpClient = HttpClients.custom()
				.setSSLSocketFactory(socketFactory)
				.build();
		HttpComponentsClientHttpRequestFactory factory =
				new HttpComponentsClientHttpRequestFactory(httpClient);

		return new RestTemplate(factory);
	}


//	@Bean
//	@LoadBalanced
//	public RestTemplate restTemplate() throws KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException, UnrecoverableKeyException, NoSuchAlgorithmException, UnrecoverableKeyException, KeyManagementException {
//		KeyStore clientStore = KeyStore.getInstance("PKCS12");
//		clientStore.load(Files.newInputStream(Paths.get("C:\\Users\\FFI\\Desktop\\bank.p12")), trustStorePassword.toCharArray());
//
//		SSLContext sslContext = SSLContextBuilder.create()
//				.setProtocol("TLS")
//				.loadKeyMaterial(clientStore, trustStorePassword.toCharArray())
//				.loadTrustMaterial(new TrustSelfSignedStrategy())
//				.build();
//
//		SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(sslContext);
//		CloseableHttpClient httpClient = HttpClients.custom()
//				.setSSLSocketFactory(sslConnectionSocketFactory)
//				.build();
//
//		HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
//
//		return new RestTemplate(requestFactory);
//
//		//return new RestTemplate();
//	}
}
