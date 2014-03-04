import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

public class HttpInvoke {
	private static final Logger LOGGER = Logger.getLogger(HttpInvoke.class);
	public static void main(String[] args) throws Exception {
		HttpClientBuilder builder = HttpClientBuilder.create();
		CloseableHttpClient client = builder.build();
		HttpHost host = new HttpHost("127.0.0.1", 8080);
		HttpPost hostRequest = new HttpPost("/OneStopService/SystemManagerSevlet/user/login");
		// NameValuePair 参数对
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("username", "crjun"));
		nvps.add(new BasicNameValuePair("password", "123456"));
		// UrlEncodedFormEntity特定用于POST方法 此处调用一直出现参数找不到，原因是因为没有设置编码格式
		hostRequest.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8)); 
        ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
            public String handleResponse(
                    final HttpResponse response) throws ClientProtocolException, IOException {
                int status = response.getStatusLine().getStatusCode();
                if (status >= 200 && status < 300) {
                    HttpEntity entity = response.getEntity();
                    return entity != null ? EntityUtils.toString(entity) : null;
                } else {
                    throw new ClientProtocolException("Unexpected response status: " + status);
                }
            }

        };
        String responseBody = client.execute(host, hostRequest, responseHandler);
        LOGGER.debug("----------------------------------------");
        LOGGER.debug(responseBody);
        LOGGER.debug("----------------------------------------");
	}
}
