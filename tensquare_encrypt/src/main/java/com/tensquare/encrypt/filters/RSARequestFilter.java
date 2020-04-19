package com.tensquare.encrypt.filters;

import com.google.common.base.Charsets;
import com.google.common.base.Strings;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.netflix.zuul.http.ServletInputStreamWrapper;
import com.tensquare.encrypt.rsa.RsaKeys;
import com.tensquare.encrypt.service.RsaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * @ClassName RSARequestFilter
 * @Description (这里用一句话描述这个类的作用)
 * @Author YongXi.Wang
 * @Date  2020年04月11日 1:29
 * @Version 1.0.0
*/
@Component
public class RSARequestFilter extends ZuulFilter{

  @Autowired
  private RsaService rsaService;

  @Override
  public String filterType() {
    return FilterConstants.PRE_TYPE;
  }

  @Override
  public int filterOrder() {
    return FilterConstants.PRE_DECORATION_FILTER_ORDER + 1;
  }

  @Override
  public boolean shouldFilter() {
    return true;
  }

  @Override
  public Object run() throws ZuulException {
    System.out.println("过滤器执行了");

    //获取requestContext容器
    RequestContext currentContext = RequestContext.getCurrentContext();
    HttpServletRequest request = currentContext.getRequest();
    HttpServletResponse response = currentContext.getResponse();

    //声明存放加密后的数据的变量
    String requestData = null;
    //声明存放解密后的数据的变量
    String decrytData = null;

    //通过request获取inputStream
    try {
      ServletInputStream is = request.getInputStream();

      //从inputStream中得到加密后的数据
      requestData = StreamUtils.copyToString(is, Charsets.UTF_8);

      System.out.println(requestData);

      //解密数据
      if(!Strings.isNullOrEmpty(requestData)){

        decrytData = rsaService.RSADecryptDataPEM(requestData, RsaKeys.getServerPrvKeyPkcs8());
        System.out.println(decrytData);

      }

      //把解密后的数据转发给服务中, 放到request中
      if(!Strings.isNullOrEmpty(decrytData)){

        byte[] bytes = decrytData.getBytes();

        //使用requestContext修改body数据
        currentContext.setRequest(new HttpServletRequestWrapper(request){
          @Override
          public ServletInputStream getInputStream() throws IOException {
            return new ServletInputStreamWrapper(bytes);
          }

          @Override
          public int getContentLength() {
            return bytes.length;
          }

          @Override
          public long getContentLengthLong() {
            return bytes.length;
          }

        });

      }

      //需要设置request请求头中的Content-Type 为 json格式的数据
      //不设置 api接口模块就需要进行url转码的操作
      currentContext.addZuulRequestHeader("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE);

    } catch (Exception e) {
      e.printStackTrace();
    }


    return null;
  }
}
