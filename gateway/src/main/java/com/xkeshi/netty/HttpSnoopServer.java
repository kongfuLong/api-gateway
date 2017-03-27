package com.xkeshi.netty;

import com.xkeshi.netty.forward.ForwardStrategy;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.SelfSignedCertificate;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by ruancl@xkeshi.com on 2017/3/23.
 */
public final class HttpSnoopServer {
  private static final Logger logger = LoggerFactory.getLogger(HttpSnoopServer.class);


  static final boolean SSL = System.getProperty("ssl") != null;
  static final int PORT = Integer.parseInt(System.getProperty("port", SSL ? "8443" : "8081"));

  private ForwardStrategy forwardStrategy;

  public HttpSnoopServer(ForwardStrategy forwardStrategy) {
    this.forwardStrategy = forwardStrategy;
    try {
      main();
    } catch (Exception e) {

    }
  }

  public void main() throws Exception {
    // Configure SSL.
    final SslContext sslCtx;
    if (SSL) {
      SelfSignedCertificate ssc = new SelfSignedCertificate();
      sslCtx = SslContextBuilder.forServer(ssc.certificate(), ssc.privateKey()).build();
    } else {
      sslCtx = null;
    }

    // Configure the server.
    EventLoopGroup bossGroup = new NioEventLoopGroup(1);
    EventLoopGroup workerGroup = new NioEventLoopGroup();
    try {
      ServerBootstrap b = new ServerBootstrap();
      b.group(bossGroup, workerGroup)
          .channel(NioServerSocketChannel.class)
          .handler(new LoggingHandler(LogLevel.INFO))
          .childHandler(new HttpSnoopServerInitializer(sslCtx,forwardStrategy));

      Channel ch = b.bind(PORT).sync().channel();

      logger.info("Open your web browser and navigate to " +
          (SSL ? "https" : "http") + "://127.0.0.1:" + PORT + '/');

      ch.closeFuture().sync();
    } finally {
      bossGroup.shutdownGracefully();
      workerGroup.shutdownGracefully();
    }
  }


}
