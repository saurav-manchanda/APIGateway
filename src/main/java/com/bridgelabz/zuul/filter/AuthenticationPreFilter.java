/********************************************************************************* *
 * Purpose: To create an implementation to GoogleKeep(ToDoApplication) and microservices.
 * Creating the Filter class extending the ZuulFilter. Thus we have
 * to implement the unimplemented methods according to our need.
 * @author Saurav Manchanda
 * @version 1.0
 * @since 17/07/2018
 *********************************************************************************/

package com.bridgelabz.zuul.filter;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.bridgelabz.zuul.ToDoExceptionHandler.ToDoException;
import com.bridgelabz.zuul.redisservice.IRedisRepository;
import com.bridgelabz.zuul.security.TokenGenerator;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

/**
 * @author Saurav
 *         <p>
 *         This class if the Filter class extending the ZuulFilter. Thus we have
 *         to implement the unimplemented methods according to our need.
 *         </p>
 */
public class AuthenticationPreFilter extends ZuulFilter {

	@Autowired
	IRedisRepository redisRepository;
	@Autowired
	TokenGenerator tokenGenerator;
	private static final Logger logger = LoggerFactory.getLogger(AuthenticationPreFilter.class);

	/**
	 * 
	 */
	@Override
	public Object run() throws ZuulException {
		logger.info("Inside the run method of AuthenticationPreFilter");
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();
		if (request.getRequestURI().startsWith("/zuulnotes/notes/")) {
			logger.info("run method called for notes");
			String token;
			token = request.getHeader("token");
			if(token==null) {
				return null;
			}
			logger.info("The token generated is:" + token);
			String userId = tokenGenerator.parseJWT(token).getId();
			logger.info("UserId: " + userId);
			String tokenfromredis = redisRepository.getToken(userId);

			if (tokenfromredis != null) {
				ctx.addZuulRequestHeader("userId", userId);
				// request.setAttribute("userId", userId);
				// request.setAttribute("email", email);
				return userId;
			}
		}
		return null;
	}
/**
 * Method should filter. This method tells when to execute the filter
 * @return
 */
	@Override
	public boolean shouldFilter() {
		return true;
	}
/**
 * This method tells the sequence of the filter
 * @return
 */
	@Override
	public int filterOrder() {
		return 1;
	}
/**
 * This method is for the filterType.
 * @return
 */
	@Override
	public String filterType() {
		return "pre";
	}

}
