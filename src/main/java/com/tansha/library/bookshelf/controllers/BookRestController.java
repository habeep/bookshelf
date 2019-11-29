package com.tansha.library.bookshelf.controllers;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.List;
import java.util.StringTokenizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tansha.library.bookshelf.exceptions.Err;
import com.tansha.library.bookshelf.model.Rating;
import com.tansha.library.bookshelf.model.Subscription;
import com.tansha.library.bookshelf.model.User;
import com.tansha.library.bookshelf.model.UserBookCart;
import com.tansha.library.bookshelf.repository.RatingRepository;
import com.tansha.library.bookshelf.repository.SubscriptionRepository;
import com.tansha.library.bookshelf.repository.TestimonialRepository;
import com.tansha.library.bookshelf.repository.UserBookCartRepository;
import com.tansha.library.bookshelf.repository.UserSubscriptionRepository;
import com.tansha.library.bookshelf.service.UserBookCartService;
import com.tansha.library.bookshelf.service.UserService;

@RestController

public class BookRestController {
	@Autowired
	private SubscriptionRepository subscriptionRepository;
	@Autowired
	private RatingRepository ratingRepository;
	@Autowired
	private UserService userService;
	@Autowired
	private UserBookCartRepository userBookCartRepository;
	@Autowired
	private UserSubscriptionRepository userSubscriptionRepository;
	@Autowired
	private TestimonialRepository testimonialRepository;
	@Autowired
	private UserBookCartService ubcService;

	@RequestMapping(value = "/pricing/getSelectedSubscriptionDetails", method = RequestMethod.GET)
	public Subscription getPricing(@RequestParam("noofMonths") int noofMonths,
			@RequestParam("maxNumberofDelivery") String maxNumberofDelivery) {
		System.out.println("come here");
		int maxNumberofDeliveryVal=0;
		int maxNumberofBooks = 0;
		
		StringTokenizer st = new StringTokenizer(maxNumberofDelivery,",");  
	     while (st.hasMoreTokens()) {
	    	 if(maxNumberofDeliveryVal == 0 ) {
	    	 maxNumberofDeliveryVal = Integer.parseInt(st.nextToken());
	    	 }
	    	 else {
	    		 maxNumberofBooks = Integer.parseInt(st.nextToken());
	    	 }
	     }  
	     
	     System.out.println(" maxNumberofDeliveryVal >>> "+maxNumberofDeliveryVal);
	     System.out.println(" maxNumberofBooks >>> "+maxNumberofBooks);
	     System.out.println(" noofMonths >>> "+noofMonths);
	     
		Subscription subscription = subscriptionRepository.getSelectedSubscriptionMonths(noofMonths,
				maxNumberofDeliveryVal,maxNumberofBooks);
		System.out.println("goes out >>>> "+subscription.toString());
		return subscription;
	}

	
	@RequestMapping(value = "/pricing_cash/getSelectedSubscriptionDetails", method = RequestMethod.GET)
	public Subscription getPricing_new(@RequestParam("noofMonths") int noofMonths,
			@RequestParam("maxNumberofDelivery") String maxNumberofDelivery) {
		System.out.println("come here");
		int maxNumberofDeliveryVal=0;
		int maxNumberofBooks = 0;
		
		StringTokenizer st = new StringTokenizer(maxNumberofDelivery,",");  
	     while (st.hasMoreTokens()) {
	    	 if(maxNumberofDeliveryVal == 0 ) {
	    	 maxNumberofDeliveryVal = Integer.parseInt(st.nextToken());
	    	 }
	    	 else {
	    		 maxNumberofBooks = Integer.parseInt(st.nextToken());
	    	 }
	     }  
	     
	     System.out.println(" maxNumberofDeliveryVal >>> "+maxNumberofDeliveryVal);
	     System.out.println(" maxNumberofBooks >>> "+maxNumberofBooks);
	     System.out.println(" noofMonths >>> "+noofMonths);
	     
		Subscription subscription = subscriptionRepository.getSelectedSubscriptionMonths(noofMonths,
				maxNumberofDeliveryVal,maxNumberofBooks);
		System.out.println("goes out >>>> "+subscription.toString());
		return subscription;
	}
	@RequestMapping(value = "/dashboard/addrating", method = RequestMethod.POST)
	public ResponseEntity<Rating> addRating(@RequestParam("bookId") int bookId, @RequestParam("userId") int userId,
			@RequestParam("rating") int rating) {
		Rating ratingObj = new Rating();
		ratingObj.setBookId(bookId);
		ratingObj.setUserId(userId);
		ratingObj.setRatings(rating);
		if (ratingRepository.save(ratingObj) != null) {
			return new ResponseEntity(ratingObj, HttpStatus.CREATED);
		} else {
			return new ResponseEntity(ratingObj, HttpStatus.CONFLICT);
		}
	}

	@RequestMapping(value = "/search/addtocart", method = RequestMethod.POST)
	public ResponseEntity<String> addBookToUserCart(@RequestParam("bookId") int bookId) throws ParseException {
		return addtoCart(bookId);
	}

	@RequestMapping(value = "/addtocart", method = RequestMethod.POST)
	public ResponseEntity<String> addBookToCartIndex(@RequestParam("bookId") int bookId) throws ParseException {
		return addtoCart(bookId);
	}

	@RequestMapping(value = "/bookdetails/{bookId}/addtocartbookdtails", method = RequestMethod.POST)
	public ResponseEntity<String> addBookToCartBookDeatails(@PathVariable final int bookId) throws ParseException {
		return addtoCart(bookId);
	}

	@RequestMapping(value = "/selectSearch/addtocart", method = RequestMethod.POST)
	public ResponseEntity<String> addBookToCartSelectSearch(@RequestParam("bookId") int bookId) throws ParseException {
		return addtoCart(bookId);
	}

	public ResponseEntity<String> addtoCart(int bookId) {
		String addToCartStatus;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		int userId = 0;
		int isUserPaid = 0;
		int isUserSubscriptionRequested = 0;
	
		if(user != null ) {
			userId= user.getId();
			isUserSubscriptionRequested = userSubscriptionRepository.isUserSubscribedCreated(userId);
			if (isUserSubscriptionRequested > 0) {
				isUserPaid = userSubscriptionRepository.isUserSubscribedPaid(userId);
			
		}
		}
		if(userId == 0 && user == null) {
			String msg="you are not logedin user";
			return new ResponseEntity(msg,HttpStatus.FORBIDDEN);
		}
		if(isUserPaid != 0 ) {
			String msg="Payment is pending";
		
			return new ResponseEntity(msg,HttpStatus.PAYMENT_REQUIRED);
		}

		
		List<Object[]> userbookcartsize = userBookCartRepository.getUserBookCartSize(user.getId());
		List<Subscription> uSubc = userSubscriptionRepository.getUserSubscribedDetails(userId);
		String msg = "";
		if (uSubc.size() == 0) {
			if (!checkUserObjectsExists(user)) {
				msg = "Sorry. You are not subscribed User. Please subscribe now.";
			} else {
				msg = "Sorry. You are not subscribed User. Please subscribe now.";
			}

			return new ResponseEntity(msg,HttpStatus.CONFLICT);
		}
		int maxNumberOfBooks = 0;
		for (Subscription userSubscription : uSubc) {
			maxNumberOfBooks = userSubscription.getMaxNumberofBooks();
		}
		Err err = ubcService.addUserBookToCart(new UserBookCart(userId, bookId, 0), maxNumberOfBooks);
		if (userId == -1 && user != null) {
			addToCartStatus = "";
			userId = user.getId();
			msg = "UnAuthorized User";
			return new ResponseEntity(msg,HttpStatus.CONFLICT);
		} else if (err.isAnError()) {
			addToCartStatus = err.getMessage();
			msg = "UnSuccessfull to add the book to Cart";
			return new ResponseEntity(msg,HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE);
		} else {
			addToCartStatus = "Book Added To Cart";
			msg = "Book added to Cart";
			}
		return new ResponseEntity(msg,HttpStatus.OK);
	}

	public boolean checkUserObjectsExists(User user) {
		if (user.getPincode() == 0 || user.getName() == null || user.getPhoneNumber() == 0
				|| user.getHouseNumber() == null) {
			return false;
		}

		return true;
	}

	@RequestMapping(value = "/getCartNumber", method = RequestMethod.GET)
	public ResponseEntity<String> addBookToUserCartBookDetailsPage() throws ParseException {

		String addToCartStatus;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		int userId = user.getId();
		List<Object[]> userbookcartsizes = userBookCartRepository.getUserBookCartSize(user.getId());
		String msg = "";
		int userbookcartsize = userbookcartsizes.size();
		return new ResponseEntity(userbookcartsize, HttpStatus.OK);
	}

}
