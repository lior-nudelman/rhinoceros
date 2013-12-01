package com.danter.google.auth;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.CredentialStore;
import com.google.api.client.auth.oauth2.CredentialStoreRefreshListener;
import com.google.api.client.auth.oauth2.MemoryCredentialStore;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeRequestUrl;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential.Builder;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson.JacksonFactory;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.Arrays;

/**
 * A helper class for Google's OAuth2 authentication API.
 * @version 20130224
 * @author Matyas Danter
 */
public final class GoogleAuthHelper {

	/**
	 * Please provide a value for the CLIENT_ID constant before proceeding, set this up at https://code.google.com/apis/console/
	 */
	private static final String CLIENT_ID = "159872143583.apps.googleusercontent.com";
	/**
	 * Please provide a value for the CLIENT_SECRET constant before proceeding, set this up at https://code.google.com/apis/console/
	 */
	private static final String CLIENT_SECRET = "x2hF3tBFnfmlzELIRUlmGWdv";

	/**
	 * Callback URI that google will redirect to after successful authentication
	 */
	private static final String CALLBACK_URI = "http://localhost:8080/OAuth2v1/index.jsp";
	
	// start google authentication constants
	private static final Iterable<String> SCOPE = Arrays.asList("https://mail.google.com/;https://www.googleapis.com/auth/userinfo.profile;https://www.googleapis.com/auth/userinfo.email".split(";"));
	private static final String USER_INFO_URL = "https://www.googleapis.com/oauth2/v1/userinfo";
	private static final JsonFactory JSON_FACTORY = new JacksonFactory();
	private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
	// end google authentication constants
	
	private String stateToken;
	
	private final GoogleAuthorizationCodeFlow flow;
	
	/**
	 * Constructor initializes the Google Authorization Code Flow with CLIENT ID, SECRET, and SCOPE 
	 */
	public GoogleAuthHelper() {
		flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT,
				JSON_FACTORY, CLIENT_ID, CLIENT_SECRET, SCOPE).setAccessType("offline").build();
		
		generateStateToken();
	}

	/**
	 * Builds a login URL based on client ID, secret, callback URI, and scope 
	 */
	public String buildLoginUrl() {
		
		final GoogleAuthorizationCodeRequestUrl url = flow.newAuthorizationUrl();
		String URL = url.setRedirectUri(CALLBACK_URI).setState(stateToken).build();
		System.out.println(URL);
		return URL;
	}
	
	/**
	 * Generates a secure state token 
	 */
	private void generateStateToken(){
		
		SecureRandom sr1 = new SecureRandom();
		
		stateToken = "google;"+sr1.nextInt();
		
	}
	
	/**
	 * Accessor for state token
	 */
	public String getStateToken(){
		return stateToken;
	}
	
	/**
	 * Expects an Authentication Code, and makes an authenticated request for the user's profile information
	 * @return JSON formatted user profile information
	 * @param authCode authentication code provided by google
	 */
	public String getUserInfoJson(final String authCode) throws IOException {

		final GoogleTokenResponse response = flow.newTokenRequest(authCode).setRedirectUri(CALLBACK_URI).execute();
		String refresh = response.getRefreshToken();
		String access = response.getAccessToken();
		System.out.println("access: "+access);
		System.out.println("refresh: "+refresh);
		
//		HttpResponse revokeResponse = HTTP_TRANSPORT.createRequestFactory()
//	            .buildGetRequest(new GenericUrl(
//	                String.format(
//	                    "https://accounts.google.com/o/oauth2/revoke?token=%s",
//	                    access))).execute();
//		System.out.println(revokeResponse.getStatusMessage());
		if(refresh == null){
			refresh="1/rral8nlUFZSfo3nL_xwdIV35EG0PaXzP9rIv0Q9hIAc";
		}
		if(refresh != null){
			GoogleCredential credentials = new GoogleCredential.Builder()
		    .setClientSecrets(CLIENT_ID, CLIENT_SECRET)
		    .setJsonFactory(JSON_FACTORY).setTransport(HTTP_TRANSPORT).build()
		    .setRefreshToken(refresh).setAccessToken(null);	
			credentials.refreshToken();
			System.out.println(credentials.getAccessToken());
		
		}
		final Credential credential = flow.createAndStoreCredential(response, null);
		final HttpRequestFactory requestFactory = HTTP_TRANSPORT.createRequestFactory(credential);
		// Make an authenticated request
		final GenericUrl url = new GenericUrl(USER_INFO_URL);
		final HttpRequest request = requestFactory.buildGetRequest(url);
		request.getHeaders().setContentType("application/json");
		final String jsonIdentity = request.execute().parseAsString();

		return jsonIdentity;

	}



}