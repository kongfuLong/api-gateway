package com.xkeshi.netty;


import static io.netty.handler.codec.http.HttpResponseStatus.BAD_GATEWAY;
import static io.netty.handler.codec.http.HttpResponseStatus.CONTINUE;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

import com.xkeshi.netty.entity.XResponse;
import com.xkeshi.netty.exception.XGatewayException;
import com.xkeshi.netty.forward.Forward;
import com.xkeshi.netty.forward.ForwardStrategy;
import com.xkeshi.netty.util.Constant;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.handler.codec.http.LastHttpContent;
import io.netty.util.CharsetUtil;
import java.util.Iterator;
import java.util.Map.Entry;

/**
 * Created by ruancl@xkeshi.com on 2017/3/23.
 */
public class MessageHandler extends SimpleChannelInboundHandler<Object> {

  /**
   * Buffer that stores the response content
   */
  private final StringBuilder buf = new StringBuilder();
  //在每个线程channel里面可以保证是有序的?   这是官方文档的方法  待证实
  private HttpRequest request;

  private ForwardStrategy forwardStrategy;

  public MessageHandler(ForwardStrategy forwardStrategy) {
    this.forwardStrategy = forwardStrategy;
  }

  private static void send100Continue(ChannelHandlerContext ctx) {
    FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, CONTINUE);
    ctx.write(response);
  }

  @Override
  public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
    ctx.flush();
  }

  @Override
  protected void channelRead0(ChannelHandlerContext ctx, Object msg)
      throws Exception {
    try {

    if (msg instanceof HttpRequest) {
      HttpRequest request = this.request = (HttpRequest) msg;
      buf.setLength(0);
      if (HttpUtil.is100ContinueExpected(request)) {
        send100Continue(ctx);
      }
    }
    if (msg instanceof HttpContent) {
      Forward forward = forwardStrategy
          .chooseForwardProcessor(request.headers().get(Constant.REQUEST_TYPE_KEY));
      HttpContent httpContent = (HttpContent) msg;
      ByteBuf content = httpContent.content();
      if (content.isReadable()) {
        buf.append(content.toString(CharsetUtil.UTF_8));
      }

      if (msg instanceof LastHttpContent) {
        //--------------信息接收完毕

        if (!writeResponse(forward.forward(forward.transformRequest(request, buf.toString())),
            ctx)) {
          // If keep-alive is off, close the connection once the content is fully written.
          ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(
              ChannelFutureListener.CLOSE);
        }
      }
    }
    }catch (XGatewayException x){
      FullHttpResponse response = new DefaultFullHttpResponse(
                          HTTP_1_1,   BAD_GATEWAY,
                           Unpooled.copiedBuffer(x.getMessage(), CharsetUtil.UTF_8));
      response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain; charset=UTF-8");
      response.headers().setInt(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes());
      response.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
      ctx.write(response);
    }
  }

  private boolean writeResponse(XResponse xResponse, ChannelHandlerContext ctx) {


    // Decide whether to close the connection or not.
    boolean keepAlive = HttpUtil.isKeepAlive(request);
    // Build the response object.
    FullHttpResponse response = new DefaultFullHttpResponse(
        HTTP_1_1, HttpResponseStatus.valueOf(xResponse.getStatus()),
        Unpooled.copiedBuffer(xResponse.getContent() == null? "":xResponse.getContent(), CharsetUtil.UTF_8));
    if(xResponse.getHeaders()!=null && xResponse.getHeaders().size()>0){
      Iterator<Entry<String,String>> iterator = xResponse.getHeaders().entrySet().iterator();
      while(iterator.hasNext()){
        Entry<String,String> entry = iterator.next();
        response.headers().add(entry.getKey(),entry.getValue());
      }
    }else{
      if(keepAlive){
        response.headers().setInt(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes());
        // Add keep alive header as per:
        // - http://www.w3.org/Protocols/HTTP/1.1/draft-ietf-http-v11-spec-01.html#Connection
        response.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
      }

      response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain; charset=UTF-8");
    }


    // Write the response.
    ctx.write(response);

    return keepAlive;
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
    cause.printStackTrace();
    ctx.close();
  }


}
