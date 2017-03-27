package com.xkeshi.netty.forward;

import com.xkeshi.mongo.Api;
import com.xkeshi.netty.entity.XResponse;
import com.xkeshi.netty.exception.XGatewayException;
import com.xkeshi.netty.registry.ApiLocator;
import com.xkeshi.netty.util.Constant;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by ruancl@xkeshi.com on 2017/3/23.
 */
@Component
public class HttpProcessor implements Forward<HttpUriRequest>{

  @Autowired
  private  HttpClient httpClient;

  @Autowired
  private ApiLocator apiLocator;




  @Override
  public HttpUriRequest transformRequest(HttpRequest request,String content) {
    String host = request.headers().get(HttpHeaderNames.HOST, "unknown");
    String api = request.headers().get(Constant.API_KEY);
    String version = request.protocolVersion().toString();
    String requestUri = request.uri();
    HttpHeaders headers = request.headers();

    Api apiUri = apiLocator.locationApi(api);
    if(apiUri == null ){
      throw new XGatewayException("service not found");
    }

    RequestBuilder requestBuilder  = RequestBuilder.create(request.method().name());
    requestBuilder.setUri(apiUri.getUrl()+requestUri);

    if (!headers.isEmpty()) {
      for (Map.Entry<String, String> h : headers) {
        CharSequence key = h.getKey();
        CharSequence value = h.getValue();
        if(key.equals("Content-Length"))
        {
          continue;
        }
        requestBuilder.addHeader(key.toString(),value.toString());
      }
    }
    requestBuilder.setEntity(new StringEntity(content, ContentType.APPLICATION_JSON));

    return requestBuilder.build();
  }

  @Override
  public XResponse forward(HttpUriRequest httpUriRequest){
    XResponse xResponse = new XResponse();
    try {
      HttpResponse response = httpClient.execute(httpUriRequest);
      String responseContent = "";
        InputStream inputStream = response.getEntity().getContent();
        responseContent = readStream(inputStream);
      xResponse.setContent(responseContent);
      xResponse.setStatus(response.getStatusLine().getStatusCode());
      xResponse.setHeaders(Arrays.asList(response.getAllHeaders()).stream().collect(
          Collectors.toMap(Header::getName,Header::getValue)));
    } catch (IOException e) {
     throw new XGatewayException("数据异常");
    }
    return xResponse;
  }

  private String readStream(InputStream inputStream) throws IOException {
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    int len;
    byte[] buff = new byte[1024];
    while((len = inputStream.read(buff,0,1024))>0){
      byteArrayOutputStream.write(buff,0,len);
    }
    String result = byteArrayOutputStream.toString();
    if(byteArrayOutputStream!=null){
      byteArrayOutputStream.close();
    }
    return result;
  }


}
