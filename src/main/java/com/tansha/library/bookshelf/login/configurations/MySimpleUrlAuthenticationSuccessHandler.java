package com.tansha.library.bookshelf.login.configurations;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.client.RedirectStrategy;
import org.apache.http.impl.client.DefaultRedirectStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.tansha.library.bookshelf.model.User;
import com.tansha.library.bookshelf.service.UserService;


public class MySimpleUrlAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	 @Autowired
	 private UserService userService;
	 
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// handle(request, response, authentication);
		 
		
	}
	
	/*protected void handle(HttpServletRequest request, 
		      HttpServletResponse response, Authentication authentication)
		      throws IOException {
		  
		        String targetUrl = determineTargetUrl(authentication);
		 
		        if (response.isCommitted()) {
		            
		            return;
		        }
		 //redirectStrategy.
		        redirectStrategy.isRedirected(request, response, targetUrl);
		    }
		 
	protected String determineTargetUrl(Authentication authentication) {
        boolean isDashboard = true;
        boolean isNotUpdated = false;
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        if( user.getName().isEmpty() || user.getPincode() == 0 || user.getName().equals(" ") || user.getName() == "") {
        	isDashboard = false;
        } else {
        	isNotUpdated = true;
        }
        if (isDashboard) {
            return "/dashboard";
        } else if (isAdmin) {
            return "/register2";
        } else {
            throw new IllegalStateException();
        }
    }
	
	public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
        this.redirectStrategy = redirectStrategy;
    }
    protected RedirectStrategy getRedirectStrategy() {
        return redirectStrategy;
    }
 */
	
}
