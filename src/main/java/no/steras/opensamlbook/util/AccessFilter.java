package no.steras.opensamlbook.util;

import org.opensaml.core.config.InitializationException;
import org.opensaml.core.config.InitializationService;
import org.opensaml.xmlsec.config.JavaCryptoValidationInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.*;
import java.io.IOException;
import java.security.Provider;
import java.security.Security;

/**
 * The filter intercepts the user and start the SAML authentication if it is not authenticated
 */
public class AccessFilter implements Filter {
    private static Logger logger = LoggerFactory.getLogger(AccessFilter.class);

    /** OpenSAML使用JCE来提供密码学的功能模块。由于某些
     * JCE的实现并不覆盖所有OpenSAML要求的功能，所以推荐使用**Bouncy Castle**的JCE实现。
     * 为了帮助用户来确认JCE的实现是否正确，可以使用如下函数：
     * @param filterConfig 过滤器配置
     * @throws ServletException
     */
    public void init(FilterConfig filterConfig) throws ServletException {
        JavaCryptoValidationInitializer javaCryptoValidationInitializer =
                new JavaCryptoValidationInitializer();
        try {
            //这个方法应该在OpenSAML初始化之前被调用，
            //来确保当前的JCE环境可以符合要求：AES/CBC/ISO10126Padding
            // 对于XML的加密，JCE需要支持ACE（128/256），并使用ISO10126Padding（填充位）
            javaCryptoValidationInitializer.init();
        } catch (InitializationException e) {
            e.printStackTrace();
        }

        //打印当前已经被安装的所有JCE的provider
        for (Provider jceProvider : Security.getProviders()) {
            logger.info(jceProvider.getInfo());
        }
        try {
            logger.info("Initializing");
            //正式初始化ＳＡＭＬ服务
            InitializationService.initialize();
        } catch (InitializationException e) {
            throw new RuntimeException("Initialization failed");
        }
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
            chain.doFilter(request, response);
    }

    public void destroy() {
    }
}