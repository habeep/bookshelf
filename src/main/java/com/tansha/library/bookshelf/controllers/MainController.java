package com.tansha.library.bookshelf.controllers;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.tansha.library.bookshelf.validator.UserRegistrationValidator;
import com.tansha.library.bookshelf.validator.UserUpdateValidator;
import com.tansha.library.bookshelf.validator.UserValidator;

import com.tansha.library.bookshelf.service.UserBookCartService;

import com.tansha.library.bookshelf.model.UserBookCart;
import com.tansha.library.bookshelf.model.UserSubscription;
import com.tansha.library.bookshelf.model.UserWishBookList;
import com.instamojo.wrapper.model.Payment;
import com.instamojo.wrapper.model.PaymentOrder;
import com.instamojo.wrapper.model.PaymentOrderResponse;
import com.instamojo.wrapper.model.PaymentRequest;
import com.instamojo.wrapper.util.HttpUtils;
import com.instamojo.wrapper.exception.HTTPException;
import com.google.gson.Gson;
import com.instamojo.wrapper.api.ApiContext;
import com.instamojo.wrapper.api.Instamojo;
import com.instamojo.wrapper.api.InstamojoImpl;
import com.instamojo.wrapper.exception.ConnectionException;
import com.tansha.library.bookshelf.exceptions.Err;
import com.tansha.library.bookshelf.model.Area;
import com.tansha.library.bookshelf.model.Book;
import com.tansha.library.bookshelf.model.BookBorrow;
import com.tansha.library.bookshelf.model.Children;
import com.tansha.library.bookshelf.model.DeliverySlot;
import com.tansha.library.bookshelf.model.Mail;
import com.tansha.library.bookshelf.model.Rating;
import com.tansha.library.bookshelf.model.ReadingLevel;
import com.tansha.library.bookshelf.model.SearchResult;
import com.tansha.library.bookshelf.model.Subscription;
import com.tansha.library.bookshelf.model.Testimonial;
import com.tansha.library.bookshelf.model.User;
import com.tansha.library.bookshelf.repository.AreaRepository;
import com.tansha.library.bookshelf.repository.AuthorRepository;
import com.tansha.library.bookshelf.repository.BookCategoryRepository;
import com.tansha.library.bookshelf.repository.BookRepository;
import com.tansha.library.bookshelf.repository.BooksBorrowRepository;
import com.tansha.library.bookshelf.repository.DeliverySlotRepository;
import com.tansha.library.bookshelf.repository.LanguageRepository;
import com.tansha.library.bookshelf.repository.PublisherRepository;
import com.tansha.library.bookshelf.repository.RatingRepository;
import com.tansha.library.bookshelf.repository.ReadingLevelRepository;
import com.tansha.library.bookshelf.repository.SubscriptionRepository;
import com.tansha.library.bookshelf.repository.TestimonialRepository;
import com.tansha.library.bookshelf.repository.UserBookCartRepository;
import com.tansha.library.bookshelf.repository.UserSubscriptionRepository;
import com.tansha.library.bookshelf.repository.UserWishBookListRepository;
import com.tansha.library.bookshelf.service.BookCategoryService;
import com.tansha.library.bookshelf.service.BookService;
import com.tansha.library.bookshelf.service.EmailService;
import com.tansha.library.bookshelf.service.SecurityService;
import com.tansha.library.bookshelf.service.TestimonialService;
import com.tansha.library.bookshelf.service.UserService;
import com.tansha.library.bookshelf.utils.Constants;
import com.tansha.library.bookshelf.utils.EmailSender;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class MainController {

	private String PAYMENT_REQUEST_ENDPOINT;
	private Map<String, String> headers = new HashMap<>();
	private Gson gson = new Gson();
	private ApiContext context;

	private PaymentRequest paymentRequest = new PaymentRequest();

	@Autowired
	private UserService userService;

	@Autowired
	private SecurityService securityService;

	@Autowired
	private BookService bookService;

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private BookCategoryService bookCategoryService;

	@Autowired
	private PublisherRepository publisherRepository;

	@Autowired
	private AuthorRepository authorRepository;

	@Autowired
	private LanguageRepository languageRepository;

	@Autowired
	private AreaRepository areaRepository;

	@Autowired
	private BookCategoryRepository bookCategoryRepository;

	@Autowired
	private ReadingLevelRepository readingLevelRepository;

	@Autowired
	private SubscriptionRepository subscriptionRepository;

	@Autowired
	private UserSubscriptionRepository userSubscriptionRepository;

	@Autowired
	private UserWishBookListRepository userWishBookListRepository;

	@Autowired
	private BooksBorrowRepository booksBorrowRepository;

	@Autowired
	DeliverySlotRepository deliverySlotRepository;
	@Autowired
	private UserValidator userValidator;

	@Autowired
	private UserUpdateValidator userUpdateValidator;
	@Autowired
	private UserRegistrationValidator userRegistrationValidator;

	@Autowired
	private Environment env;

	@Autowired
	private EmailSender emailSender;

	@Autowired
	private EmailService emailService;

	@Autowired
	private UserBookCartService ubcService;

	@Autowired
	private TestimonialRepository testimonialRepository;

	@Autowired
	private TestimonialService testimonialService;

	@Autowired
	private RatingRepository ratingRepository;

	@Autowired
	private UserBookCartRepository userBookCartRepository;

	@RequestMapping(value = { "/login" }, method = RequestMethod.GET)
	public ModelAndView login(RedirectAttributes redirAttr) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("readingLevels", readingLevelRepository.findAllActiveReadingLevel(1));
		modelAndView.addObject("publishers", publisherRepository.findAllActivePublisher(1));
		modelAndView.addObject("languages", languageRepository.findAllActiveLanguage(1));
		modelAndView.addObject("categories", bookCategoryRepository.findAllActiveCategory(1));
		modelAndView.addObject("authors", authorRepository.findAllActivAuthor(1));

		modelAndView.addObject("testimonials", testimonialRepository.getTestimonials(1));
		User user = new User();
		modelAndView.addObject("user", user);
		redirAttr.addFlashAttribute("successMessage", "User has been registered successfully");
		modelAndView.setViewName("login");
		return modelAndView;
	}
	
	@RequestMapping(value = { "/contact" }, method = RequestMethod.GET)
	public ModelAndView contact(RedirectAttributes redirAttr) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("readingLevels", readingLevelRepository.findAllActiveReadingLevel(1));
		modelAndView.addObject("publishers", publisherRepository.findAllActivePublisher(1));
		modelAndView.addObject("languages", languageRepository.findAllActiveLanguage(1));
		modelAndView.addObject("categories", bookCategoryRepository.findAllActiveCategory(1));
		modelAndView.addObject("authors", authorRepository.findAllActivAuthor(1));

		modelAndView.addObject("testimonials", testimonialRepository.getTestimonials(1));
		modelAndView.setViewName("contact");
		return modelAndView;
	}
	

	@RequestMapping(value = { "/" }, method = RequestMethod.GET)
	public ModelAndView index() {
		//System.out.println("\n\n inside index start 1 \n");
		ModelAndView modelAndView = new ModelAndView();
		//System.out.println("\n\n inside index start 2 \n");
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		//System.out.println("\n\n inside index start 3 \n");
		User user = userService.findUserByEmail(auth.getName());
		//System.out.println("\n\n inside index start 4 \n");
		modelAndView.addObject("user", user);
		if (user != null) {
			modelAndView.addObject("userId", user.getId());
			modelAndView.addObject("rentFlag", 1);
			List<Object[]> userbookcartsize = userBookCartRepository.getUserBookCartSize(user.getId());
			modelAndView.addObject("userbookcartsize", userbookcartsize.size());
		} else {
			modelAndView.addObject("userId", -1);
		}
		//System.out.println("\n\n inside index start 5 \n");
		List<ReadingLevel> readingLevels = readingLevelRepository.findAllActiveReadingLevel(1);
//		//System.out.println("the readingLevels are"+readingLevels.);
		List<Object[]> featuredBooks = bookService.getFeaturedBooks();
		List<Object[]> fBooks = new ArrayList<Object[]>();
		int idx = 0;
		//System.out.println("size is "+featuredBooks.size());
		for(Object[] featureBookObj:featuredBooks) {
			fBooks.add(featureBookObj);
			if(idx == 5 ) {
				break;
			}
			idx++;
		}
		//System.out.println(fBooks.size());
		modelAndView.addObject("books", fBooks);
		modelAndView.addObject("testimonials", testimonialRepository.getTestimonials(1));
		modelAndView.addObject("authors", authorRepository.findAll());
		modelAndView.addObject("publishers", publisherRepository.findAll());
		modelAndView.addObject("languages", languageRepository.findAll());
		modelAndView.addObject("categories", bookCategoryRepository.findAll());
//		modelAndView.addObject("userID", user.getId());
		modelAndView.setViewName("index");
		//System.out.println("\n\n inside index start 6 \n");
		return modelAndView;
	}

	@RequestMapping(value = { "/signin" }, method = RequestMethod.GET)
	public ModelAndView sigin() {

		ModelAndView modelAndView = new ModelAndView();

		User user = new User();
		modelAndView.addObject("user", user);

		modelAndView.addObject("testimonials", testimonialRepository.getTestimonials(1));
		modelAndView.addObject("authors", authorRepository.findAll());
		modelAndView.addObject("publishers", publisherRepository.findAll());
		modelAndView.addObject("languages", languageRepository.findAll());
		modelAndView.addObject("categories", bookCategoryRepository.findAll());
		modelAndView.setViewName("signin");
		return modelAndView;
	}

	public boolean checkUserObjectsExists(User user) {
		if (user.getPincode() == 0 || user.getName() == null || user.getPhoneNumber() == 0
				|| user.getHouseNumber() == null) {
			return false; 
		}

		return true;
	}

	@RequestMapping(value = { "/dashboard" }, method = RequestMethod.GET)
	public ModelAndView dashboard(final RedirectAttributes redirectAttributes) {
		int bookId = 0;
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		List<Object[]> userbookcartsize = userBookCartRepository.getUserBookCartSize(user.getId());
		modelAndView.addObject("userbookcartsize", userbookcartsize.size());
		if (!checkUserObjectsExists(user)) {
			redirectAttributes.addFlashAttribute("userId", user.getId());
			modelAndView.setViewName("redirect:/register2");
			redirectAttributes.addFlashAttribute("redirectMsg",
					"Sorry. You are not subscribed User. Please subscribe now.");
			return modelAndView;
		}
		
		int isUserPaid = 0;
		int isUserSubscriptionRequested = 0;
		int userId = 0;
		int payByCashOption = 0;
		int maxNumberofDeliveries = 0;
		int totalBooksAllocated = 0 ;
		int sumOfDeliveriesSoFar = 0;
		int uSubcId = 0;
		if (user != null) {
			modelAndView.addObject("userId", user.getId());
			userId = user.getId();
			isUserSubscriptionRequested = userSubscriptionRepository.isUserSubscribedCreated(userId);
			if (isUserSubscriptionRequested > 0) {
				isUserPaid = userSubscriptionRepository.isUserSubscribedPaid(userId);
				modelAndView.addObject("isUserSubscriptionRequested", isUserSubscriptionRequested);
				modelAndView.addObject("isUserPaid", isUserPaid);
				payByCashOption = userSubscriptionRepository.isUserSubscribedPaidByCash(userId);
				modelAndView.addObject("paybyCashFlag", payByCashOption);
				List<Object[]> usc = userSubscriptionRepository.getUserCurrentSubscriptionDetails(user.getId());
				modelAndView.addObject("usc", usc);
				List<Subscription> uSubc = userSubscriptionRepository.getUserSubscribedDetails(userId);
				
				List<UserSubscription> uSubcs = userSubscriptionRepository.findByUserId(userId);
				
				
				for (UserSubscription userSubscription : uSubcs) {
					uSubcId = userSubscription.getId();
				}
				
				for (Subscription userSubscription : uSubc) {
					maxNumberofDeliveries = userSubscription.getNoofMonths() * userSubscription.getMaxNumberofDelivery();
					
					//totalBooksAllocated = userSubscription.getNoofMonths() * userSubscription.getMaxNumberofBooks();
					//totalBooksAllocated = totalBooksAllocated * userSubscription.getMaxNumberofBooks();
					System.out.println("\n ******* userSubscription.getNoofMonths() >>> "+userSubscription.getNoofMonths()+" **** userSubscription.getMaxNumberofDelivery() >>> "+userSubscription.getMaxNumberofDelivery());
					System.out.println("\n\n maxNumberofDeliveries="+maxNumberofDeliveries +"\n\n");
					
					
				}
				List<BookBorrow> bw = booksBorrowRepository.getTotalDeliveries(userId,uSubcId);
				
				if(bw != null) {
					sumOfDeliveriesSoFar = bw.size();
				}
				
				System.out.println( " \n\n ---- sumOfDeliveriesSoFar="+sumOfDeliveriesSoFar+"\n\n");

				modelAndView.addObject("numberofdeliveriesLeft", (maxNumberofDeliveries - sumOfDeliveriesSoFar) );

			} else {
				modelAndView.addObject("isUserSubscriptionRequested", isUserSubscriptionRequested);
				modelAndView.addObject("isUserPaid", -1);
				modelAndView.addObject("paybyCashFlag", -1);
				modelAndView.addObject("numberofdeliveriesLeft", -1 );
				modelAndView.setViewName("redirect:/quickSearch");
				
				// List<Object[]> usc =
				// userSubscriptionRepository.getUserCurrentSubscriptionDetails(user.getId());
				// modelAndView.addObject("usc", usc);
			}
		}

		modelAndView.addObject("testimonials", testimonialRepository.getTestimonials(1));
		modelAndView.addObject("userName", user.getName());
		modelAndView.addObject("readingLevels", readingLevelRepository.findAllActiveReadingLevel(1));
		modelAndView.addObject("publishers", publisherRepository.findAllActivePublisher(1));
		modelAndView.addObject("languages", languageRepository.findAllActiveLanguage(1));
		modelAndView.addObject("categories", bookCategoryRepository.findAllActiveCategory(1));
		modelAndView.addObject("authors", authorRepository.findAllActivAuthor(1));
		List<Object[]> books = bookRepository.bookBorrowHistoryDetails(user.getId());
		List<Object[]> deliveryBooks = bookRepository.getDeliveryInProgroseBooks(user.getId());
		modelAndView.addObject("deliveryBookssize", deliveryBooks.size());
		modelAndView.addObject("deliveryBooks", deliveryBooks);
		List<Object[]> returnInProgressBooks = new ArrayList<Object[]>();
		List<Object[]> notReturnBooks = new ArrayList<Object[]>();
		List<Object[]> returnConfirmBooks = new ArrayList<Object[]>();
		boolean flag = false;
		for (Object[] book : books) {
			flag = false;
			Optional<Rating> ratingObj = ratingRepository.getUserBookRating(user.getId(), (String) book[6]);
			bookId = (int) book[0];
			List<Object[]> tmp = bookRepository.bookBorrowHistoryDetailswithReturnInProgress(user.getId(), bookId);
			if (tmp.size() != 0) {
				returnInProgressBooks.addAll(tmp);
				flag = true;
			}
			List<Object[]> tmp1 = bookRepository.bookBorrowHistoryDetailswithReturnRequested(user.getId(), bookId);
			if (tmp1.size() != 0) {
				returnConfirmBooks.addAll(tmp1);
				flag = true;
			}
			if (!flag) {
				notReturnBooks.add(book);
			}
			if (ratingObj.isPresent()) {
				modelAndView.addObject("myrating_" + (int) book[0], ratingObj.get().getRatings());
				modelAndView.addObject("isExist_" + (int) book[0], "rated");
			}
		}
		books.removeAll(returnInProgressBooks);
		books.removeAll(returnConfirmBooks);
		modelAndView.addObject("notreturnBooksSize", notReturnBooks.size());
		modelAndView.addObject("returnInProgressBooks", returnInProgressBooks);
		modelAndView.addObject("returnConfirmBooks", returnConfirmBooks);
		modelAndView.addObject("noOfreturnInProgressBooks", returnInProgressBooks.size());
		modelAndView.addObject("noOfreturnconfirmBooks", returnConfirmBooks.size());
		modelAndView.addObject("books", notReturnBooks);
		modelAndView.addObject("noOfCartItems", books.size());
		return modelAndView;
	}

	@RequestMapping(value = { "/rentalhistory" }, method = RequestMethod.GET)
	public ModelAndView bookhistory() {

		int year = Calendar.getInstance().get(Calendar.YEAR);
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.YEAR, -1);
		int nextYear = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);

		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		modelAndView.addObject("testimonials", testimonialRepository.getTestimonials(1));

		if (user != null) {
			modelAndView.addObject("userId", user.getId());

			// modelAndView.addObject("books", bookService.getFeaturedBooks());
			modelAndView.addObject("readingLevels", readingLevelRepository.findAllActiveReadingLevel(1));
			modelAndView.addObject("publishers", publisherRepository.findAllActivePublisher(1));
			modelAndView.addObject("languages", languageRepository.findAllActiveLanguage(1));
			modelAndView.addObject("categories", bookCategoryRepository.findAllActiveCategory(1));
			modelAndView.addObject("authors", authorRepository.findAllActivAuthor(1));
			List<Object[]> books = bookRepository.bookBorrowHistoryDetail(user.getId());
			modelAndView.addObject("books", books);
			modelAndView.addObject("year", year);
			modelAndView.addObject("nextYear", nextYear);
			modelAndView.addObject("selectedmonth", month + 1);
			modelAndView.addObject("selectedyear", year);
			modelAndView.setViewName("rentalhistory");
			List<Object[]> userbookcartsize = userBookCartRepository.getUserBookCartSize(user.getId());
			modelAndView.addObject("userbookcartsize", userbookcartsize.size());
		} else {
			modelAndView.setViewName("redirect:/login");
		}

		return modelAndView;
	}

	@RequestMapping(value = { "/rentalhistory" }, method = RequestMethod.POST)
	public ModelAndView bookhistoryRange(@RequestParam("month") final int month, @RequestParam("year") final int year) {
		int currentYear = Calendar.getInstance().get(Calendar.YEAR);
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.YEAR, -1);
		int nextYear = cal.get(Calendar.YEAR);
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		if (user != null) {
			modelAndView.addObject("userId", user.getId());
			// modelAndView.addObject("books", bookService.getFeaturedBooks());
			modelAndView.addObject("readingLevels", readingLevelRepository.findAllActiveReadingLevel(1));
			modelAndView.addObject("publishers", publisherRepository.findAllActivePublisher(1));
			modelAndView.addObject("languages", languageRepository.findAllActiveLanguage(1));
			modelAndView.addObject("categories", bookCategoryRepository.findAllActiveCategory(1));
			modelAndView.addObject("authors", authorRepository.findAllActivAuthor(1));
			List<Object[]> books = bookRepository.bookBorrowHistoryArchiveDetail(user.getId(), month, year);
			modelAndView.addObject("books", books);
			modelAndView.addObject("year", currentYear);
			modelAndView.addObject("nextYear", nextYear);
			modelAndView.addObject("selectedmonth", month);
			modelAndView.addObject("selectedyear", year);
			modelAndView.setViewName("rentalhistory");
		} else {
			modelAndView.setViewName("redirect:/login");
		}

		return modelAndView;
	}

//	@RequestMapping(value = "/registration", method = RequestMethod.GET)
//	public ModelAndView registration(final HttpServletRequest request) {
//		ModelAndView modelAndView = new ModelAndView();
//		User user = new User();
//		modelAndView.addObject("testimonials", testimonialRepository.getTestimonials(1));
//		modelAndView.addObject("user", user);
//		modelAndView.setViewName("registration");
//		return modelAndView;
//	}

	/*
	 * @RequestMapping(value="/search", method = RequestMethod.POST) public
	 * ModelAndView searchBook(final HttpServletRequest request){
	 * bookService.searchBooks(searchStr) ModelAndView modelAndView = new
	 * ModelAndView();
	 * 
	 * modelAndView.setViewName("book-list"); return modelAndView; }
	 */

	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public ModelAndView executeSearchBook(final HttpServletRequest request,
			@RequestParam("searchStr") final String searchStr) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("testimonials", testimonialRepository.getTestimonials(1));
		// List<Object[]> books = bookService.searchBooks(searchStr);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		modelAndView.addObject("user", user);
		if (user != null) {
			modelAndView.addObject("userId", user.getId());
			modelAndView.addObject("rentFlag", 1);
			List<Object[]> userbookcartsize = userBookCartRepository.getUserBookCartSize(user.getId());
			modelAndView.addObject("userbookcartsize", userbookcartsize.size());
		} else {
			modelAndView.addObject("userId", -1);
		}
		//System.out.println("\n\n ****** \n\n");
		modelAndView.addObject("books", bookService.searchBooks(searchStr));
		//System.out.println("******* \n\n");
		modelAndView.addObject("readingLevels", readingLevelRepository.findAllActiveReadingLevel(1));
		modelAndView.addObject("publishers", publisherRepository.findAllActivePublisher(1));
		modelAndView.addObject("languages", languageRepository.findAllActiveLanguage(1));
		modelAndView.addObject("categories", bookCategoryRepository.findAllActiveCategory(1));
		modelAndView.addObject("authors", authorRepository.findAllActivAuthor(1));
		modelAndView.setViewName("book-list");
		return modelAndView;
	}

	@RequestMapping(value = "/selectSearch", method = RequestMethod.POST)
	public ModelAndView executeSearchBooks(final HttpServletRequest request,
			@RequestParam("readingLevelList") final int readingLevelList,
			@RequestParam("categoriesList") final int categoriesList,
			@RequestParam("languageList") final int languageList,
			@RequestParam("publishersList") final int publishersList) {
		ModelAndView modelAndView = new ModelAndView();

		// List<Object[]> books = bookService.searchBooks(searchStr);
		/*
		 * modelAndView.addObject("books", bookService.searchBooks(searchStr));
		 * modelAndView.addObject("authors",authorRepository.findAll());
		 * modelAndView.addObject("publishers",publisherRepository.findAll());
		 * modelAndView.addObject("languages",languageRepository.findAll());
		 * modelAndView.addObject("categories",bookCategoryRepository.findAll());
		 */
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		modelAndView.addObject("user", user);
		if (user != null) {
			modelAndView.addObject("userId", user.getId());
			modelAndView.addObject("rentFlag", 1);
			List<Object[]> userbookcartsize = userBookCartRepository.getUserBookCartSize(user.getId());
			modelAndView.addObject("userbookcartsize", userbookcartsize.size());

		} else {
			modelAndView.addObject("userId", -1);
		}
		List<SearchResult> booksSearchResults = bookService.selectSearchBooks(readingLevelList, categoriesList,
				languageList, publishersList);
		System.out.println("*** booksSearchResults size() >>> "+booksSearchResults.size());
		modelAndView.addObject("books", booksSearchResults);
		modelAndView.addObject("readingLevels", readingLevelRepository.findAllActiveReadingLevel(1));
		modelAndView.addObject("publishers", publisherRepository.findAllActivePublisher(1));
		modelAndView.addObject("languages", languageRepository.findAllActiveLanguage(1));
		modelAndView.addObject("categories", bookCategoryRepository.findAllActiveCategory(1));
		modelAndView.addObject("testimonials", testimonialRepository.getTestimonials(1));
		modelAndView.setViewName("searchbook-list");
		return modelAndView;
	}

	@RequestMapping(value = "/quickSearch", method = RequestMethod.GET)
	public ModelAndView executeQuickReadingLevelSearchBooks(final HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();

		// List<Object[]> books = bookService.searchBooks(searchStr);
		/*
		 * modelAndView.addObject("books", bookService.searchBooks(searchStr));
		 * modelAndView.addObject("authors",authorRepository.findAll());
		 * modelAndView.addObject("publishers",publisherRepository.findAll());
		 * modelAndView.addObject("languages",languageRepository.findAll());
		 * modelAndView.addObject("categories",bookCategoryRepository.findAll());
		 */
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		modelAndView.addObject("user", user);
		if (user != null) {
			List<Object[]> userbookcartsize = userBookCartRepository.getUserBookCartSize(user.getId());
			modelAndView.addObject("userbookcartsize", userbookcartsize.size());
			modelAndView.addObject("userId", user.getId());
			modelAndView.addObject("rentFlag", 1);
		} else {
			modelAndView.addObject("userId", -1);
		}
		List<Object[]> booksSearchResults = bookRepository.quickSearchBooks();
		modelAndView.addObject("books", booksSearchResults);
		modelAndView.addObject("readingLevels", readingLevelRepository.findAllActiveReadingLevel(1));
		modelAndView.addObject("publishers", publisherRepository.findAll());
		modelAndView.addObject("languages", languageRepository.findAll());
		modelAndView.addObject("categories", bookCategoryRepository.findAll());
		modelAndView.addObject("testimonials", testimonialRepository.getTestimonials(1));
		modelAndView.setViewName("book-list");
		return modelAndView;
	}

	@RequestMapping(value = "/quickReadingLevelSearch/{readingLevelId}", method = RequestMethod.GET)
	public ModelAndView executeQuickReadingLevelSearchBooks(final HttpServletRequest request,
			@PathVariable("readingLevelId") final int readingLevelId) {
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		modelAndView.addObject("user", user);
		if (user != null) {
			modelAndView.addObject("userId", user.getId());
			modelAndView.addObject("rentFlag", 1);
			List<Object[]> userbookcartsize = userBookCartRepository.getUserBookCartSize(user.getId());
			modelAndView.addObject("userbookcartsize", userbookcartsize.size());
		} else {
			modelAndView.addObject("userId", -1);
		}
		List<Object[]> booksSearchResults = bookRepository.selectQuickReadingLevelSearchBooks(readingLevelId);
		modelAndView.addObject("books", booksSearchResults);
		modelAndView.addObject("user", user);
		modelAndView.addObject("readingLevels", readingLevelRepository.findAllActiveReadingLevel(1));
		modelAndView.addObject("publishers", publisherRepository.findAllActivePublisher(1));
		modelAndView.addObject("languages", languageRepository.findAllActiveLanguage(1));
		modelAndView.addObject("categories", bookCategoryRepository.findAllActiveCategory(1));
		modelAndView.addObject("testimonials", testimonialRepository.getTestimonials(1));
		modelAndView.setViewName("book-list");
		return modelAndView;
	}

	@RequestMapping(value = "/quickCategorySearch/{categoryId}", method = RequestMethod.GET)
	public ModelAndView executeQuickCategorySearchBooks(final HttpServletRequest request,
			@PathVariable("categoryId") final int categoryId) {
		ModelAndView modelAndView = new ModelAndView();

		// List<Object[]> books = bookService.searchBooks(searchStr);
		/*
		 * modelAndView.addObject("books", bookService.searchBooks(searchStr));
		 * modelAndView.addObject("authors",authorRepository.findAll());
		 * modelAndView.addObject("publishers",publisherRepository.findAll());
		 * modelAndView.addObject("languages",languageRepository.findAll());
		 * modelAndView.addObject("categories",bookCategoryRepository.findAll());
		 */
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		modelAndView.addObject("user", user);
		if (user != null) {
			List<Object[]> userbookcartsize = userBookCartRepository.getUserBookCartSize(user.getId());
			modelAndView.addObject("userbookcartsize", userbookcartsize.size());
			modelAndView.addObject("userId", user.getId());
			modelAndView.addObject("rentFlag", 1);
		} else {
			modelAndView.addObject("userId", -1);
		}
		List<Object[]> booksSearchResults = bookRepository.selectQuickCategorySearchBooks(categoryId);
		modelAndView.addObject("books", booksSearchResults);
		modelAndView.addObject("readingLevels", readingLevelRepository.findAllActiveReadingLevel(1));
		modelAndView.addObject("publishers", publisherRepository.findAllActivePublisher(1));
		modelAndView.addObject("languages", languageRepository.findAllActiveLanguage(1));
		modelAndView.addObject("categories", bookCategoryRepository.findAllActiveCategory(1));
		modelAndView.addObject("testimonials", testimonialRepository.getTestimonials(1));
		modelAndView.setViewName("book-list");
		return modelAndView;
	}

	@RequestMapping(value = "/bookdetails/{bookId}", method = RequestMethod.GET)
	public ModelAndView bookDetails(final HttpServletRequest request, @PathVariable("bookId") final int bookId) {

		ModelAndView modelAndView = new ModelAndView();
		List<Object[]> book = bookService.bookDetails(bookId);

		String isbnCode = "";
		for (Object[] bookObj : book) {
			isbnCode = (String) bookObj[6].toString();
		}
		modelAndView.addObject("books", bookService.bookDetails(bookId));
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		modelAndView.addObject("user", user);
		if (user != null) {
			List<Object[]> userbookcartsize = userBookCartRepository.getUserBookCartSize(user.getId());
			modelAndView.addObject("userbookcartsize", userbookcartsize.size());
			modelAndView.addObject("userId", user.getId());
			modelAndView.addObject("rentFlag", 1);
		} else {
			modelAndView.addObject("userId", -1);
		}
		List<Rating> ratings = ratingRepository.getBookRatings(isbnCode);
		int noOfUsers = ratings.size();
		int ratingValues = 0;
		for (Rating rating : ratings) {
			ratingValues += rating.getRatings();
		}
		int finalRatingScore = 0;
		if (ratingValues != 0 && noOfUsers != 0) {
			finalRatingScore = ratingValues / noOfUsers;
		}
		modelAndView.addObject("finalRatingScore", finalRatingScore);
		modelAndView.addObject("readingLevels", readingLevelRepository.findAllActiveReadingLevel(1));
		modelAndView.addObject("publishers", publisherRepository.findAllActivePublisher(1));
		modelAndView.addObject("languages", languageRepository.findAllActiveLanguage(1));
		modelAndView.addObject("categories", bookCategoryRepository.findAllActiveCategory(1));
		modelAndView.addObject("authors", authorRepository.findAllActivAuthor(1));
		List<Object[]> featuredBooks = bookService.getFeaturedBooks();
		
		
		List<Object[]> fBooks = new ArrayList<Object[]>();
		int idx = 0;
		//System.out.println("size is "+featuredBooks.size());
		for(Object[] featureBookObj:featuredBooks) {
			fBooks.add(featureBookObj);
			if(idx == 5 ) {
				break;
			}
			idx++;
		}
		
		modelAndView.addObject("featuredBooks", fBooks);
		modelAndView.setViewName("book-view");
		return modelAndView;
	}

	@RequestMapping(value = "/sendAreaAdditionNotification", method = RequestMethod.POST)
	public ModelAndView sendAreaAdditionNotification(final HttpServletRequest request,
			@RequestParam("areaName") final String areaName, @RequestParam("phoneNumber") final long phoneNumber)
			throws MessagingException, Exception {
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		// info@bookshelf-library.com
		String content = user.getEmailId() + " is looking for delivery to the Area or LandMark :: " + areaName
				+ " and Phone number is " + phoneNumber;
		sendEmail("info@bookshelf-library.com", "BookShelf-library.com: Area Enquiry Notification", user.getEmailId(),
				content, "/emailTemplatess/email-area-notification");

		modelAndView.setViewName("register2");
		return modelAndView;
	}

	@RequestMapping(value = "/verifyDelivery", method = RequestMethod.POST)
	public ModelAndView checkDeliveryAreaAvailability(final HttpServletRequest request,
			@RequestParam("pincode") final int pincode) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("testimonials", testimonialRepository.getTestimonials(1));
		Area areaExist = areaRepository.findByPincodeAndIsActive(pincode, 1);
		if (areaExist != null) {
			modelAndView.addObject("areaAvailabiltyStatus", 1);
			// modelAndView.setViewName("register2");
			modelAndView.setViewName("redirect:/register3");
		} else {
			modelAndView.addObject("areaAvailabiltyStatus", 0);
			modelAndView.setViewName("register2");
		}

		return modelAndView;
	}

	@RequestMapping(value = "/register2", method = RequestMethod.GET)
	public ModelAndView register2(final HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		List<Object[]> userbookcartsize = userBookCartRepository.getUserBookCartSize(user.getId());
		modelAndView.addObject("userbookcartsize", userbookcartsize.size());
		modelAndView.addObject("readingLevels", readingLevelRepository.findAllActiveReadingLevel(1));
		modelAndView.addObject("publishers", publisherRepository.findAllActivePublisher(1));
		modelAndView.addObject("languages", languageRepository.findAllActiveLanguage(1));
		modelAndView.addObject("categories", bookCategoryRepository.findAllActiveCategory(1));
		modelAndView.addObject("authors", authorRepository.findAllActivAuthor(1));
		modelAndView.addObject("testimonials", testimonialRepository.getTestimonials(1));
		modelAndView.setViewName("register2");
		return modelAndView;
	}

	@RequestMapping(value = "/register3", method = RequestMethod.GET)
	public ModelAndView register3(final HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		List<Object[]> userbookcartsize = userBookCartRepository.getUserBookCartSize(user.getId());
		modelAndView.addObject("userbookcartsize", userbookcartsize.size());
//		if(user.getPhoneNumber() == 0 ) {+
//			user.setPhoneNumber(0);
//		} 
//		if(user.getPincode() == 0 ) {
//			user.setPincode(0);
//		}
		modelAndView.addObject("user", user);
		modelAndView.addObject("readingLevels", readingLevelRepository.findAllActiveReadingLevel(1));
		modelAndView.addObject("publishers", publisherRepository.findAllActivePublisher(1));
		modelAndView.addObject("languages", languageRepository.findAllActiveLanguage(1));
		modelAndView.addObject("categories", bookCategoryRepository.findAllActiveCategory(1));
		modelAndView.addObject("authors", authorRepository.findAllActivAuthor(1));
		modelAndView.addObject("testimonials", testimonialRepository.getTestimonials(1));
		modelAndView.setViewName("register3");
		return modelAndView;
	}

	@RequestMapping(value = "/register4", method = RequestMethod.GET)
	public ModelAndView register4(final HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		List<Object[]> userbookcartsize = userBookCartRepository.getUserBookCartSize(user.getId());
		modelAndView.addObject("userbookcartsize", userbookcartsize.size());
		modelAndView.addObject("readingLevels", readingLevelRepository.findAllActiveReadingLevel(1));
		modelAndView.addObject("publishers", publisherRepository.findAllActivePublisher(1));
		modelAndView.addObject("languages", languageRepository.findAllActiveLanguage(1));
		modelAndView.addObject("categories", bookCategoryRepository.findAllActiveCategory(1));
		modelAndView.addObject("authors", authorRepository.findAllActivAuthor(1));
		modelAndView.addObject("subscriptions", subscriptionRepository.getAllActiveSubscriptions());
		modelAndView.addObject("subscriptionsMonths", subscriptionRepository.getAllActiveSubscriptionMonths());
		modelAndView.addObject("user", user);
		modelAndView.addObject("testimonials", testimonialRepository.getTestimonials(1));
		modelAndView.setViewName("register4");
		return modelAndView;
	}

	@RequestMapping(value = "/userdetails", method = RequestMethod.GET)
	public ModelAndView userdetails(final HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		int userId = user.getId();
		List<Object[]> userbookcartsize = userBookCartRepository.getUserBookCartSize(userId);
		modelAndView.addObject("userbookcartsize", userbookcartsize.size());
		modelAndView.addObject("user", user);
		modelAndView.addObject("testimonials", testimonialRepository.getTestimonials(1));
		modelAndView.addObject("readingLevels", readingLevelRepository.findAllActiveReadingLevel(1));
		modelAndView.addObject("publishers", publisherRepository.findAllActivePublisher(1));
		modelAndView.addObject("languages", languageRepository.findAllActiveLanguage(1));
		modelAndView.addObject("categories", bookCategoryRepository.findAllActiveCategory(1));
		modelAndView.addObject("authors", authorRepository.findAllActivAuthor(1));
		modelAndView.setViewName("userdetails");
		return modelAndView;
	}

	@RequestMapping(value = "/userdetails", method = RequestMethod.POST)
	public ModelAndView userDetailsSubmitForm(@Valid User user, BindingResult bindingResult,
			final HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("testimonials", testimonialRepository.getTestimonials(1));
		userUpdateValidator.validate(user, bindingResult);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User userObj = userService.findUserByEmail(auth.getName());
		Area areaExist = areaRepository.findByPincodeAndIsActive(user.getPincode(), 1);
		if (areaExist == null) {
			bindingResult.rejectValue("pincode", "error.user", "sorry we don’t deliver to this pincode and your address cannot be updated in our records");
		} else {

		}
		if (bindingResult.hasErrors()) {
			modelAndView.setViewName("userdetails");
		} else {
			
			userObj.setName(user.getName());
			userObj.setHouseNumber(user.getHouseNumber());
			userObj.setStreet(user.getStreet());
			userObj.setLocality(user.getLocality());
			userObj.setCity(user.getCity());
			userObj.setLandmark(user.getLandmark());
			userObj.setPincode(user.getPincode());
			userObj.setPhoneNumber(user.getPhoneNumber());
			userObj.setFlag(user.getFlag());

			
			
			//userObj.setId(user.getId());
			//userObj.setEmailId(user.getEmailId());
			//userObj.setName(user.getName());
			//userObj.setHouseNumber(user.getHouseNumber());
			//userObj.setStreet(user.getStreet());
			//userObj.setLocality(user.getLocality());
			//userObj.setCity(user.getCity());
			//userObj.setLandmark(user.getLandmark());
			//userObj.setPincode(user.getPincode());
			//userObj.setPhoneNumber(user.getPhoneNumber());

			userService.updateUser(userObj);

			modelAndView.setViewName("userdetails");
		}
		return modelAndView;
	}

	@RequestMapping(value = "/register3", method = RequestMethod.POST)
	public ModelAndView processRegister3Form(@Valid User user, BindingResult bindingResult,
			final HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("testimonials", testimonialRepository.getTestimonials(1));
		userUpdateValidator.validate(user, bindingResult);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User userObj = userService.findUserByEmail(auth.getName());
		Area areaExist = areaRepository.findByPincodeAndIsActive(user.getPincode(), 1);
		if (areaExist == null) {
			bindingResult.rejectValue("pincode", "error.user", "Sorry.. We don't have service available in your area.");
		}
		if (bindingResult.hasErrors()) {
			modelAndView.setViewName("register3");
		} else {
			userObj.setName(user.getName());
			userObj.setHouseNumber(user.getHouseNumber());
			userObj.setStreet(user.getStreet());
			userObj.setLocality(user.getLocality());
			userObj.setCity(user.getCity());
			userObj.setLandmark(user.getLandmark());
			userObj.setPincode(user.getPincode());
			userObj.setPhoneNumber(user.getPhoneNumber());
			userObj.setFlag(user.getFlag());
			userService.updateUser(userObj);
			int isUserSubscriptionRequested = userSubscriptionRepository.isUserSubscribedCreated(user.getId());
			if(isUserSubscriptionRequested > 0) {
				modelAndView.setViewName("redirect:/dashboard");
			} else {
				modelAndView.setViewName("redirect:/pricing");
			}
			
		}
		return modelAndView;
	}

	@RequestMapping(value = "/forgotpassword", method = RequestMethod.GET)
	public ModelAndView displayForgotPasswordPage(final HttpServletRequest request) {
		return new ModelAndView("forgotpassword");
	}

	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult, final HttpServletRequest request)
			throws MessagingException, IOException {

		userValidator.validate(user, bindingResult);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("testimonials", testimonialRepository.getTestimonials(1));
		User userExists = userService.findUserByEmail(user.getEmailId());
		if (userExists != null) {
			bindingResult.rejectValue("emailId", "error.user",
					"There is already a user registered with the email provided");
		}
		if (bindingResult.hasErrors()) {
			modelAndView.setViewName("registration");
		} else {
			userService.saveUser(user);
			Mail mail = new Mail();
			mail.setFrom(env.getProperty("support.email"));
			mail.setTo(user.getEmailId());
			mail.setSubject("BookShelf Registered!!!");
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("name", user.getName());
			model.put("signature", "http://acclivers.com/");
			model.put("content", "You have successfully registered.");
			mail.setModel(model);
			emailService.sendSimpleMessage(mail, "email-template");

			modelAndView.addObject("successMessage", "User has been registered successfully");
			modelAndView.addObject("user", new User());
			modelAndView.setViewName("registration");

		}
		return modelAndView;
	}

	@RequestMapping(value = "/initialRegistration", method = RequestMethod.POST)
	public ModelAndView initialRegistration(@Valid User user, BindingResult bindingResult,
			final HttpServletRequest request, RedirectAttributes redirAttr,
			@ModelAttribute("successMessage") String successMessage) throws Exception {
		userRegistrationValidator.validate(user, bindingResult);
		ModelAndView modelAndView = new ModelAndView();
		if (bindingResult.hasErrors()) {
			modelAndView.setViewName("signin");
		} else {
			userService.saveUser(user);
			modelAndView.addObject("successMessage", "User has been registered successfully");
			String content = "";

			sendEmail(user.getEmailId(), "BookShelf-library.com: Successfully Registered!!!", user.getEmailId(),
					content, "/emailTemplatess/email-register");
			/*
			 * Mail mail = new Mail(); mail.setFrom(env.getProperty("support.email"));
			 * mail.setTo(user.getEmailId()); mail.setSubject("BookShelf Registered!!!");
			 * 
			 * Map<String, Object> model = new HashMap<String, Object>(); model.put("name",
			 * user.getEmailId()); model.put("signature", "http://bookshelf-library.com/");
			 * model.put("content", "You have successfully registered.");
			 * mail.setModel(model); emailService.sendSimpleMessage(mail, "email-template");
			 */
			redirAttr.addFlashAttribute("successMessage", "User has been registered successfully");
			securityService.autoLogin(user.getEmailId(), user.getPassword());
			modelAndView.addObject("user", new User());
			modelAndView.setViewName("redirect:/register2");

		}
		return modelAndView;
	}

	@RequestMapping(value = "/forgotpassword", method = RequestMethod.POST)
	public ModelAndView forgotPassword(@RequestParam("email") final String userEmail, final HttpServletRequest request)
			throws MessagingException, IOException {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("testimonials", testimonialRepository.getTestimonials(1));
		User userExists = userService.findUserByEmail(userEmail);
		if (userExists == null) {
			modelAndView.addObject("errorMessage", "We didn't find an account for that e-mail address.");
		} else {
			userExists.setResetToken(UUID.randomUUID().toString());
			userService.saveUser(userExists);
			String appUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
			String content = appUrl + "/reset?token=" + userExists.getResetToken();
			try {
				modelAndView.addObject("successMessage", "you’ll receive an email in a few minutes to your registered id containing a link that will allow you to reset your password. If you don’t see the email in your inbox shortly, check your spam folder.");
				sendEmail(userExists.getEmailId(), "Bookshelf-library.com : Reset Password !!!", userExists.getName(),
						content, "/emailTemplatess/email-reset-password");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			modelAndView.addObject("successMessage", "you’ll receive an email in a few minutes to your registered id containing a link that will allow you to reset your password. If you don’t see the email in your inbox shortly, check your spam folder.");
		}
		modelAndView.setViewName("forgotpassword");
		return modelAndView;
	}

	// Display form to reset password
	@RequestMapping(value = "/reset", method = RequestMethod.GET)
	public ModelAndView displayResetPasswordPage(ModelAndView modelAndView, @RequestParam("token") String token) {
		Optional<User> user = userService.findUserByResetToken(token);
		modelAndView.addObject("testimonials", testimonialRepository.getTestimonials(1));
		if (user.isPresent()) { // Token found in DB
			modelAndView.addObject("resetToken", token);
		} else { // Token not found in DB
			modelAndView.addObject("errorMessage", "Oops!  This is an invalid password reset link.");
		}
		modelAndView.setViewName("resetpassword");
		return modelAndView;
	}

	// Process reset password form
	@RequestMapping(value = "/reset", method = RequestMethod.POST)
	public ModelAndView setNewPassword(ModelAndView modelAndView, @RequestParam("resetToken") final String resetToken,
			@RequestParam("newpassword") final String newpassword, RedirectAttributes redir) {
		// Find the user associated with the reset token
		Optional<User> user = userService.findUserByResetToken(resetToken);
		modelAndView.addObject("testimonials", testimonialRepository.getTestimonials(1));
		// This should always be non-null but we check just in case
		if (user.isPresent()) {
			User resetUser = user.get();

			// Set new password
			resetUser.setPassword((newpassword));

			// Set the reset token to null so it cannot be used again
			resetUser.setResetToken(null);

			// Save user
			userService.saveUser(resetUser);

			// In order to set a model attribute on a redirect, we must use
			// RedirectAttributes
			redir.addFlashAttribute("successMessage", "You have successfully reset your password.  You may now login.");

			modelAndView.setViewName("redirect:login");
			return modelAndView;

		} else {
			modelAndView.addObject("errorMessage", "Oops!  This is an invalid password reset link.");
			modelAndView.setViewName("resetpassword");
		}

		return modelAndView;
	}

	@RequestMapping(value = "/changepassword", method = RequestMethod.POST)
	public ModelAndView changepassword(ModelAndView modelAndView, @RequestParam("newpassword") final String newpassword,
			@RequestParam("confirmnewpassword") final String confirmnewpassword, RedirectAttributes redir) {

		// Find the user associated with the reset token
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		modelAndView.addObject("user", user);
		modelAndView.addObject("testimonials", testimonialRepository.getTestimonials(1));
		// This should always be non-null but we check just in case
		if (newpassword == null || newpassword.equals("") || newpassword == "") {
			modelAndView.addObject("errorMessage", "Password shouldn't be empty.");
			modelAndView.setViewName("userdetails");
			return modelAndView;
		} else if (!newpassword.equals(confirmnewpassword)) {
			modelAndView.addObject("errorMessage", "Oops!  Confirm password must match with new password.");
			modelAndView.setViewName("userdetails");
			return modelAndView;
		} else if (user != null) {

			// Set new password
			user.setPassword((newpassword));

			// Set the reset token to null so it cannot be used again

			// Save user
			userService.saveUser(user);

			// In order to set a model attribute on a redirect, we must use
			// RedirectAttributes
			redir.addFlashAttribute("successMessage", "You have successfully changed your password.");
			modelAndView.addObject("successMessage", "You have successfully changed your password.");
			modelAndView.setViewName("userdetails");
			return modelAndView;

		} else {
			modelAndView.addObject("errorMessage", "Oops!  This is an invalid password reset link.");
			modelAndView.setViewName("userdetails");
		}

		return modelAndView;
	}

	// Going to reset page without a token redirects to login page
	@ExceptionHandler(MissingServletRequestParameterException.class)
	public ModelAndView handleMissingParams(MissingServletRequestParameterException ex) {
		return new ModelAndView("redirect:login");
	}

	@RequestMapping(value = "/user/home", method = RequestMethod.GET)
	public ModelAndView home() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("testimonials", testimonialRepository.getTestimonials(1));
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		modelAndView.addObject("userName", "Welcome " + user.getName() + " (" + user.getEmailId() + ")");
		modelAndView.addObject("adminMessage", "Content Available Only for Users with Admin Role");
		modelAndView.setViewName("user/home");
		return modelAndView;
	}

	@RequestMapping(value = "/user/managechildren")
	public ModelAndView managechildren() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("testimonials", testimonialRepository.getTestimonials(1));
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		modelAndView.addObject("userId", user.getId());
		modelAndView.addObject("userName", "Welcome " + user.getName() + " ( " + user.getEmailId() + ")");
		modelAndView.addObject("adminMessage", "Manage Children Page");
		Children children = new Children();
		modelAndView.addObject("children", children);
		modelAndView.setViewName("user/managechildren");
		return modelAndView;
	}

	@RequestMapping(value = "/user/updateprofile")
	public ModelAndView updateProfile() {
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		// modelAndView.addObject("userId", user.getId());
		// modelAndView.addObject("userName", "Welcome " + user.getFirstName() + " " +
		// user.getLastName() + " (" + user.getEmailId() + ")");
		// modelAndView.addObject("adminMessage","Manage Children Page");
		// Children children = new Children();
		modelAndView.addObject("user", user);
		modelAndView.setViewName("user/updateprofile");
		return modelAndView;
	}

	@RequestMapping(value = "/user/changepassword")
	public ModelAndView changePassword() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!auth.isAuthenticated()) {
			return new ModelAndView("login");
		} else {
			return new ModelAndView("changepassword");
		}

	}

	/*
	 * private SimpleMailMessage constructResendVerificationTokenEmail(final String
	 * contextPath, final Locale locale, final User user) { final String
	 * confirmationUrl = contextPath + "/registrationConfirm.html?firstName=" +
	 * user.getFirstName(); //final String message =
	 * messages.getMessage("message.resendToken", null, locale); final String
	 * message = "Resent Registration Token"; return
	 * constructEmail("Resend Registration Token", message + " \r\n" +
	 * confirmationUrl, user); }
	 * 
	 * private SimpleMailMessage ceonstructResetTokenEmail(String contextPath, final
	 * Locale locale, final String token, final User user) { final String url =
	 * contextPath + "/user/changePassword?id=" + user.getId() + "&token=" + token;
	 * final String message = messages.getMessage("message.resetPassword", null,
	 * locale); return constructEmail("Reset Password", message + " \r\n" + url,
	 * user); }
	 * 
	 * private SimpleMailMessage constructEmail(String subject, String body, User
	 * user) { final SimpleMailMessage email = new SimpleMailMessage();
	 * email.setSubject(subject); email.setText(body);
	 * email.setTo(user.getEmailId());
	 * email.setFrom(env.getProperty("support.email")); return email; }
	 */
	@GetMapping("/shoppingCart")
	public ModelAndView shoppingCart() {
		ModelAndView modelAndView = new ModelAndView("/shoppingCart");
		modelAndView.addObject("testimonials", testimonialRepository.getTestimonials(1));
		// modelAndView.addObject("products", shoppingCartService.getProductsInCart());
		// modelAndView.addObject("total", shoppingCartService.getTotal().toString());
		return modelAndView;
	}

	@GetMapping("/shoppingCart/addProduct/{productId}")
	public ModelAndView addProductToCart(@PathVariable("productId") Long productId) {

		// productService.findById(productId).ifPresent(shoppingCartService::addProduct);
		return shoppingCart();
	}

	private String getAppUrl(HttpServletRequest request) {
		return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
	}

	@RequestMapping(value = { "/pricing" }, method = RequestMethod.GET)
	public ModelAndView pricing() {

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("testimonials", testimonialRepository.getTestimonials(1));
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
//		String addToPricingStatus = "Successfully Removed!!";
		if (user != null) {
			List<Object[]> userbookcartsize = userBookCartRepository.getUserBookCartSize(user.getId());
			modelAndView.addObject("userbookcartsize", userbookcartsize.size());
			List<Object[]> usc = userSubscriptionRepository.getUserCurrentSubscriptionDetail(user.getId());
			if (usc.size() > 0) {
				String addToPricingStatus = "You Are Already Subscribed User!!";
				modelAndView.addObject("userSubscribedFlag", 1);
				modelAndView.addObject("addToPricingStatus", addToPricingStatus);
			} else {
				modelAndView.addObject("userSubscribedFlag", 0);
			}

			modelAndView.addObject("userId", user.getId());
		} else {
			modelAndView.addObject("userSubscribedFlag", 0);
			modelAndView.addObject("userId", -1);
		}

		modelAndView.addObject("readingLevels", readingLevelRepository.findAllActiveReadingLevel(1));
		modelAndView.addObject("publishers", publisherRepository.findAllActivePublisher(1));
		modelAndView.addObject("languages", languageRepository.findAllActiveLanguage(1));
		modelAndView.addObject("categories", bookCategoryRepository.findAllActiveCategory(1));
		modelAndView.addObject("authors", authorRepository.findAllActivAuthor(1));
		List<Object[]> subscriptions = subscriptionRepository.getAllActiveSubscriptions();
		modelAndView.addObject("subscriptions", subscriptions);
		modelAndView.addObject("subscriptionsMonths", subscriptionRepository.getAllActiveSubscriptionMonths());
		modelAndView.setViewName("pricing");
		return modelAndView;
	}
	
	@RequestMapping(value = { "/pricing_cash" }, method = RequestMethod.GET)
	public ModelAndView pricing_new() {

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("testimonials", testimonialRepository.getTestimonials(1));
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
//		String addToPricingStatus = "Successfully Removed!!";
		if (user != null) {
			List<Object[]> userbookcartsize = userBookCartRepository.getUserBookCartSize(user.getId());
			modelAndView.addObject("userbookcartsize", userbookcartsize.size());
			List<Object[]> usc = userSubscriptionRepository.getUserCurrentSubscriptionDetail(user.getId());
			if (usc.size() > 0) {
				String addToPricingStatus = "You Are Already Subscribed User!!";
				modelAndView.addObject("userSubscribedFlag", 1);
				modelAndView.addObject("addToPricingStatus", addToPricingStatus);
			} else {
				modelAndView.addObject("userSubscribedFlag", 0);
			}

			modelAndView.addObject("userId", user.getId());
		} else {
			modelAndView.addObject("userSubscribedFlag", 0);
			modelAndView.addObject("userId", -1);
		}

		modelAndView.addObject("readingLevels", readingLevelRepository.findAllActiveReadingLevel(1));
		modelAndView.addObject("publishers", publisherRepository.findAllActivePublisher(1));
		modelAndView.addObject("languages", languageRepository.findAllActiveLanguage(1));
		modelAndView.addObject("categories", bookCategoryRepository.findAllActiveCategory(1));
		modelAndView.addObject("authors", authorRepository.findAllActivAuthor(1));
		List<Object[]> subscriptions = subscriptionRepository.getAllActiveSubscriptions();
		modelAndView.addObject("subscriptions", subscriptions);
		modelAndView.addObject("subscriptionsMonths", subscriptionRepository.getAllActiveSubscriptionMonths());
		modelAndView.setViewName("pricing_cash");
		return modelAndView;
	}

	/**
	 * Subscrption Details
	 */

	@RequestMapping(value = "/subscriptionPage", method = RequestMethod.GET)
	public ModelAndView subscriptionPage() {
		List<Object[]> subscriptions = subscriptionRepository.getAllSubscriptions();
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("testimonials", testimonialRepository.getTestimonials(1));
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		if (user != null) {
			List<Object[]> userbookcartsize = userBookCartRepository.getUserBookCartSize(user.getId());
			modelAndView.addObject("userbookcartsize", userbookcartsize.size());
			modelAndView.addObject("userId", user.getId());
		} else {
			modelAndView.addObject("userId", -1);
		}
		modelAndView.addObject("subscriptions", subscriptions);
		modelAndView.setViewName("subscriptionPage");
		return modelAndView;

	}

	public PaymentOrderResponse paymentGateway(User user, Integer subcId, int amount, List<Subscription> subscriptions)
			throws IOException, ConnectionException, HTTPException {
		//System.out.println("User Details >>> " + user.toString());
		//System.out.println("subcId >>> " + subcId);
		//System.out.println("amount >>> " + amount);
		//System.out.println("subscriptions >>> " + subscriptions.toString());
		 //String clientId = "test_J5M3yYug3e8r2FXTNjZXoTAuHDNZiMjGuUS";
		 //String client_Secret =
		 //"test_2FWX8pILyNnQdaPRVXONZz7fIFoeUjbLabWaqDIqolG1qrnBGQCwdMwKzkLKNBzvuCVJih3j4bJpQoLhbpx0s64pUgSQFrUyjvVCyXN4R91AnEMzyroqhgdBOgQ";
		String clientId = "erVhj8zEjRjmTobcLYL7YAGewJfXrnpiFOT2RHzB";
		String client_Secret = "4as5UgBgCvAWAAY9xuqO5LGL72bYaHZqz28NGCM06ab4rSIKwupVlLazpUnILNljD7aYu07zH5n1Ofvj7JWNHKMAYRlG6vC57hdBboOc4wUEvgngP2CITFfZzp7NJldu";
		//System.out.println("inside payment gateway");
		/*
		 * ApiContext context = ApiContext.create(clientId, client_Secret,
		 * ApiContext.Mode.TEST);
		 */
		ApiContext context = ApiContext.create(clientId, client_Secret, ApiContext.Mode.LIVE);
		//System.out.println("inside payment gateway 0");
		PAYMENT_REQUEST_ENDPOINT = context.getApiPath(Constants.PATH_PAYMENT_REQUEST);
		//System.out.println("inside payment gateway 1");
		headers.put(Constants.HEADER_AUTHORIZATION, context.getAuthorization());
		//System.out.println("inside payment gateway 2");

		
		Instamojo api = new InstamojoImpl(context);
		/*
		 * Create a new payment order
		 */
		//System.out.println("inside payment gateway 3");
		Date date = new Date();
		//System.out.println("inside payment gateway 4");
		long time = date.getTime();
		//System.out.println("inside payment gateway 5");
		PaymentOrder order = new PaymentOrder();
		//System.out.println("inside payment gateway 6");
		order.setName(user.getName());
		//System.out.println("inside payment gateway 7");
		order.setEmail(user.getEmailId());
		//System.out.println("inside payment gateway 8");
		order.setPhone(String.valueOf(user.getPhoneNumber()));
		//System.out.println("inside payment gateway 9");
		order.setCurrency("INR");
		//System.out.println("inside payment gateway 10");
		order.setAmount(Double.valueOf(amount));
		//System.out.println("inside payment gateway 11");
		order.setDescription(user.getEmailId() + "User Subscription payment :: " + time);
		//System.out.println("inside payment gateway 12");
		order.setRedirectUrl("http://bookshelf-library.com/paymentconfirmation");
		order.setWebhookUrl("http://bookshelf-library.com/paymentconfirmation");
		//System.out.println("inside payment gateway 13 >>> "+ time + "_" + user.getName().replaceAll("\\s", ""));
		order.setTransactionId(time + "_" + user.getName().replaceAll("\\s", ""));
		
		
		//System.out.println("inside payment gateway 14");
		//System.out.println("\n\n Order details >>> "+order.toString());
		PaymentOrderResponse paymentOrderResponse = null;
		//System.out.println("\n\ninside payment gateway 15");
		paymentRequest.setAmount(Double.valueOf(amount));
		//System.out.println("inside payment gateway 16");
		paymentRequest.setPurpose(user.getName() + " User Subscription payment ");
		//System.out.println("inside payment gateway 17");
		paymentRequest.setBuyerName(user.getName());
		//System.out.println("inside payment gateway 18");
		paymentRequest.setEmail(user.getEmailId());
		//System.out.println("inside payment gateway 19");
		paymentRequest.setPhone(String.valueOf(user.getPhoneNumber()));
		//System.out.println("inside payment gateway 20");

		paymentRequest.setRedirectUrl("http://bookshelf-library.com/paymentconfirmation");
		//System.out.println("inside payment gateway 21");
		paymentRequest.setSendEmail(true);
		//System.out.println("inside payment gateway 22");
		paymentRequest.setSendSms(false);
		//System.out.println("inside payment gateway 23");
		paymentRequest.setWebhookUrl("http://bookshelf-library.com/paymentconfirmation");
		//System.out.println("inside payment gateway 24");
		paymentRequest.setAllowRepeatedPayments(false);
		//System.out.println("inside payment gateway 25");
		try {
			// String response = HttpUtils.post(PAYMENT_REQUEST_ENDPOINT, headers,
			// gson.toJson(paymentRequest));

			// paymentRequest = gson.fromJson(response, PaymentRequest.class);
			//System.out.println("inside payment gateway 26");
			paymentOrderResponse = api.createPaymentOrder(order);
			//System.out.println("inside payment gateway 27");
		//} catch (HTTPException e) {
			////System.out.println("inside payment gateway Exception >>> " + e);
			////System.out.println("Error >>> " + e.getMessage());
		} catch (Exception err) {
			//System.out.println("inside payment gateway Exception block 2 >>> " + err);
			//System.out.println("Errors in Exception 2 block >> " + err.getMessage());
		}
		return paymentOrderResponse;

	}
	

	@RequestMapping(value = "/subscribeMe", method = RequestMethod.POST)
	public ModelAndView subscribeMe(@RequestParam("subcId") int subcId,@RequestParam("payOption") int payOption, RedirectAttributes redirAttr)
			throws MessagingException, Exception {
		System.out.println(" inside subscribeMe 0");
		List<Subscription> subscriptions = subscriptionRepository.getSubscriptionsById(subcId);
		System.out.println(" inside subscribeMe 1");
		int amount = 0;
		int noofMonths = 0;
		int noOfBooks=0;
		String planName = "";
		for (Subscription subscription : subscriptions) {
			amount = subscription.getAmount();
			noofMonths = subscription.getNoofMonths();
			noOfBooks= subscription.getMaxNumberofBooks();
			planName = subscription.getSubscriptionName();
			
		}

		// int amount = (int) subscriptions[4];
		ModelAndView modelAndView = new ModelAndView();

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
System.out.println(" inside subscribeMe count() >>> "+userSubscriptionRepository.getUserSubscriptionDetails(user.getId()));
		if (userSubscriptionRepository.getUserSubscriptionDetails(user.getId()).size() > 0) {
			System.out.println(" inside subscribeMe 3");
			redirAttr.addFlashAttribute("redirectMsg", "Sorry you are already subscribed user.");
			modelAndView.setViewName("redirect:/dashboard");
			return modelAndView;
		}
		
		if (user != null) {
			
			//System.out.println("inside subscribe");
			if(payOption == 1 ) {
				modelAndView.addObject("userId", user.getId());
				modelAndView.addObject("subscriptions", subscriptionRepository.getAllActiveSubscriptions());
				modelAndView.addObject("subscriptionsMonths", subscriptionRepository.getAllActiveSubscriptionMonths());
				modelAndView.addObject("testimonials", testimonialRepository.getTestimonials(1));
				modelAndView.addObject("authors", authorRepository.findAll());
				modelAndView.addObject("publishers", publisherRepository.findAll());
				modelAndView.addObject("languages", languageRepository.findAll());
				modelAndView.addObject("categories", bookCategoryRepository.findAll());
				
			PaymentOrderResponse paymentOrderResponse = paymentGateway(user, subcId, amount, subscriptions);
			 
			//System.out.println("\n paymentOrder response >> "+paymentOrderResponse.toString());
			//System.out.println("\n paymentOrder ORDER response >> "+paymentOrderResponse.getPaymentOrder().toString());
			//System.out.println("\ninside subscribe 0");
			if (paymentOrderResponse != null) {
				//System.out.println("inside subscribe 1");
				if (paymentOrderResponse.getPaymentOrder().getStatus().equals("pending")) {
					//System.out.println("inside subscribe 2");
					UserSubscription uSubc = new UserSubscription();
					uSubc.setUserId(user.getId());
					Date today = new Date();

					// int noOfDays = 90;
					Calendar calendar = Calendar.getInstance();
					calendar.add(Calendar.MONTH, noofMonths);
					Date validUpto = calendar.getTime();
					// ss
					uSubc.setValidFrom(today);
					uSubc.setValidTo(validUpto);
					uSubc.setSubscriptionId((int) subcId);
					
					uSubc.setPaymentStatus(paymentOrderResponse.getPaymentOrder().getStatus());
					String paymentId = "";
					
					 uSubc.setPaymentTransactionId(paymentOrderResponse.getPaymentOrder().getTransactionId());
					
					uSubc.setPaymentUrl(paymentOrderResponse.getPaymentOptions().getPaymentUrl());
					
					userSubscriptionRepository.save(uSubc);
					modelAndView.setViewName("redirect:" + paymentOrderResponse.getPaymentOptions().getPaymentUrl());
					return modelAndView;
				}
			}
			} else {
				
				UserSubscription uSubc = new UserSubscription();
				uSubc.setUserId(user.getId());
				Date today = new Date();

				// int noOfDays = 90;
				Calendar calendar = Calendar.getInstance();
				calendar.add(Calendar.MONTH, noofMonths);
				Date validUpto = calendar.getTime();
				SimpleDateFormat formatter = new SimpleDateFormat(
					      "dd/MM/yyyy");
				Date validUpto1 = formatter.parse(formatter.format(validUpto));
				// ss
				uSubc.setValidFrom(today);
				uSubc.setValidTo(validUpto);
				uSubc.setSubscriptionId((int) subcId);
				
				uSubc.setPaymentStatus("paybycash");
				Date date = new Date();
				//System.out.println("inside payment gateway 4");
				long time = date.getTime();
				
				
				 uSubc.setPaymentTransactionId(String.valueOf(time));
				
				uSubc.setPaymentUrl("http://bookshelf-library.com/contact");
				
				userSubscriptionRepository.save(uSubc);
				
				UserSubscription usc = userSubscriptionRepository.getUserPendingSubscriptionDetailByStatus(user.getId(),"paybycash");
				modelAndView.addObject("userSubcIdValue",usc.getId());
				modelAndView.addObject("subcIdValue",usc.getSubscriptionId());
				
				modelAndView.addObject("paybyCashFlag",1);
				//modelAndView.addObject("paybyCashFlag", 1);
				modelAndView.setViewName("paymentsuccess");
				
				String content = "Hello, You have subscribed the pay by cash. please contact Sales team or contact us to pay the cash.";
				sendEmail(user.getEmailId(), "BookShelf-library.com: Successfully Subscribed with option of Pay By Cash!!!", user.getName(), content,
						"/emailTemplatess/email-subscribe",validUpto1,planName,noOfBooks);
				
				return modelAndView;
			}
			// paymentOrderResponse.getPaymentOrder().getTransactionId();
		} else {
			modelAndView.addObject("userId", -1);

		}

		modelAndView.setViewName("pricing");
		return modelAndView;

	}
	
	
	@RequestMapping(value = "/subscribeMeCash", method = RequestMethod.POST)
	public ModelAndView subscribeMeCash(@RequestParam("subcId") int subcId,@RequestParam("payOption") int payOption, RedirectAttributes redirAttr)
			throws MessagingException, Exception {
		System.out.println(" inside subscribeMe 0");
		List<Subscription> subscriptions = subscriptionRepository.getSubscriptionsById(subcId);
		System.out.println(" inside subscribeMe 1");
		int amount = 0;
		int noofMonths = 0;
		int noOfBooks=0;
		String planName = "";
		for (Subscription subscription : subscriptions) {
			amount = subscription.getAmount();
			noofMonths = subscription.getNoofMonths();
			noOfBooks= subscription.getMaxNumberofBooks();
			planName = subscription.getSubscriptionName();
			
		}

		// int amount = (int) subscriptions[4];
		ModelAndView modelAndView = new ModelAndView();

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
System.out.println(" inside subscribeMe count() >>> "+userSubscriptionRepository.getUserSubscriptionDetails(user.getId()));
		if (userSubscriptionRepository.getUserSubscriptionDetails(user.getId()).size() > 0) {
			System.out.println(" inside subscribeMe 3");
			redirAttr.addFlashAttribute("redirectMsg", "Sorry you are already subscribed user.");
			modelAndView.setViewName("redirect:/dashboard");
			return modelAndView;
		}
		
		if (user != null) {
			
			//System.out.println("inside subscribe");
			if(payOption == 1 ) {
				modelAndView.addObject("userId", user.getId());
				modelAndView.addObject("subscriptions", subscriptionRepository.getAllActiveSubscriptions());
				modelAndView.addObject("subscriptionsMonths", subscriptionRepository.getAllActiveSubscriptionMonths());
				modelAndView.addObject("testimonials", testimonialRepository.getTestimonials(1));
				modelAndView.addObject("authors", authorRepository.findAll());
				modelAndView.addObject("publishers", publisherRepository.findAll());
				modelAndView.addObject("languages", languageRepository.findAll());
				modelAndView.addObject("categories", bookCategoryRepository.findAll());
				
			PaymentOrderResponse paymentOrderResponse = paymentGateway(user, subcId, amount, subscriptions);
			 
			//System.out.println("\n paymentOrder response >> "+paymentOrderResponse.toString());
			//System.out.println("\n paymentOrder ORDER response >> "+paymentOrderResponse.getPaymentOrder().toString());
			//System.out.println("\ninside subscribe 0");
			if (paymentOrderResponse != null) {
				//System.out.println("inside subscribe 1");
				if (paymentOrderResponse.getPaymentOrder().getStatus().equals("pending")) {
					//System.out.println("inside subscribe 2");
					UserSubscription uSubc = new UserSubscription();
					uSubc.setUserId(user.getId());
					Date today = new Date();

					// int noOfDays = 90;
					Calendar calendar = Calendar.getInstance();
					calendar.add(Calendar.MONTH, noofMonths);
					Date validUpto = calendar.getTime();
					// ss
					uSubc.setValidFrom(today);
					uSubc.setValidTo(validUpto);
					uSubc.setSubscriptionId((int) subcId);
					
					uSubc.setPaymentStatus(paymentOrderResponse.getPaymentOrder().getStatus());
					String paymentId = "";
					
					 uSubc.setPaymentTransactionId(paymentOrderResponse.getPaymentOrder().getTransactionId());
					
					uSubc.setPaymentUrl(paymentOrderResponse.getPaymentOptions().getPaymentUrl());
					
					userSubscriptionRepository.save(uSubc);
					modelAndView.setViewName("redirect:" + paymentOrderResponse.getPaymentOptions().getPaymentUrl());
					return modelAndView;
				}
			}
			} else {
				
				UserSubscription uSubc = new UserSubscription();
				uSubc.setUserId(user.getId());
				Date today = new Date();

				// int noOfDays = 90;
				Calendar calendar = Calendar.getInstance();
				calendar.add(Calendar.MONTH, noofMonths);
				Date validUpto = calendar.getTime();
				SimpleDateFormat formatter = new SimpleDateFormat(
					      "dd/MM/yyyy");
				Date validUpto1 = formatter.parse(formatter.format(validUpto));
				// ss
				uSubc.setValidFrom(today);
				uSubc.setValidTo(validUpto);
				uSubc.setSubscriptionId((int) subcId);
				
				uSubc.setPaymentStatus("paybycash");
				Date date = new Date();
				//System.out.println("inside payment gateway 4");
				long time = date.getTime();
				
				
				 uSubc.setPaymentTransactionId(String.valueOf(time));
				
				uSubc.setPaymentUrl("http://bookshelf-library.com/contact");
				
				userSubscriptionRepository.save(uSubc);
				
				UserSubscription usc = userSubscriptionRepository.getUserPendingSubscriptionDetailByStatus(user.getId(),"paybycash");
				modelAndView.addObject("userSubcIdValue",usc.getId());
				modelAndView.addObject("subcIdValue",usc.getSubscriptionId());
				
				modelAndView.addObject("paybyCashFlag",1);
				//modelAndView.addObject("paybyCashFlag", 1);
				modelAndView.setViewName("paymentsuccess");
				
				String content = "Hello, You have subscribed the pay by cash. please contact Sales team or contact us to pay the cash.";
				sendEmail(user.getEmailId(), "BookShelf-library.com: Successfully Subscribed with option of Pay By Cash!!!", user.getName(), content,
						"/emailTemplatess/email-subscribe",validUpto1,planName,noOfBooks);
				
				return modelAndView;
			}
			// paymentOrderResponse.getPaymentOrder().getTransactionId();
		} else {
			modelAndView.addObject("userId", -1);

		}

		modelAndView.setViewName("pricing_cash");
		return modelAndView;

	}
	
	@RequestMapping(value = "/onlinepayment", method = RequestMethod.POST)
	public ModelAndView executeOnlinepayment(@RequestParam("subcId") int subcId,@RequestParam("userSubcId") int userSubcId, RedirectAttributes redirAttr)
			throws MessagingException, Exception {
		System.out.println("payOption value >>>> "+userSubcId+"\n\n");
		List<Subscription> subscriptions = subscriptionRepository.getSubscriptionsById(subcId);
		
		ModelAndView modelAndView = new ModelAndView();
		int amount = 0;
		int noofMonths = 0;
		for (Subscription subscription : subscriptions) {
			amount = subscription.getAmount();
			noofMonths = subscription.getNoofMonths();
		}

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());

		
		
		if (user != null) {
			
			//System.out.println("inside subscribe");
			//if(userSubcId == 1 ) {
				modelAndView.addObject("userId", user.getId());
				modelAndView.addObject("subscriptions", subscriptionRepository.getAllActiveSubscriptions());
				modelAndView.addObject("subscriptionsMonths", subscriptionRepository.getAllActiveSubscriptionMonths());
				modelAndView.addObject("testimonials", testimonialRepository.getTestimonials(1));
				modelAndView.addObject("authors", authorRepository.findAll());
				modelAndView.addObject("publishers", publisherRepository.findAll());
				modelAndView.addObject("languages", languageRepository.findAll());
				modelAndView.addObject("categories", bookCategoryRepository.findAll());
				
			PaymentOrderResponse paymentOrderResponse = paymentGateway(user, subcId, amount, subscriptions);
			 
			//System.out.println("\n paymentOrder response >> "+paymentOrderResponse.toString());
			//System.out.println("\n paymentOrder ORDER response >> "+paymentOrderResponse.getPaymentOrder().toString());
			//System.out.println("\ninside subscribe 0");
			if (paymentOrderResponse != null) {
				//System.out.println("inside subscribe 1");
				if (paymentOrderResponse.getPaymentOrder().getStatus().equals("pending")) {
					
					UserSubscription uSubc = userSubscriptionRepository.findById(userSubcId);
					
					Date today = new Date();

					// int noOfDays = 90;
					Calendar calendar = Calendar.getInstance();
					calendar.add(Calendar.MONTH, noofMonths);
					Date validUpto = calendar.getTime();
					// ss
					uSubc.setValidFrom(today);
					uSubc.setValidTo(validUpto);
					
					uSubc.setPaymentStatus(paymentOrderResponse.getPaymentOrder().getStatus());
					 uSubc.setPaymentTransactionId(paymentOrderResponse.getPaymentOrder().getTransactionId());
					uSubc.setPaymentUrl(paymentOrderResponse.getPaymentOptions().getPaymentUrl());
					userSubscriptionRepository.save(uSubc);
					modelAndView.setViewName("redirect:" + paymentOrderResponse.getPaymentOptions().getPaymentUrl());
					return modelAndView;
				}
			//}
			} 
			// paymentOrderResponse.getPaymentOrder().getTransactionId();
		} else {
			modelAndView.addObject("userId", -1);

		}

		modelAndView.setViewName("pricing");
		return modelAndView;

	}

	/***
	 * User Cart codes begins
	 */

	/**
	 * @param userId
	 * @param bookId
	 * @param modelAndView
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = "/user/books/{bookId}", method = RequestMethod.GET)
	public ModelAndView addBookToUserCart(@PathVariable("bookId") Integer bookId,
			final RedirectAttributes redirectAttributes) throws ParseException {
		System.out.println("\n\n 1 \n\n");
		ModelAndView modelAndView = new ModelAndView();
		String addToCartStatus;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		int userId = user.getId();
		List<Object[]> userbookcartsize = userBookCartRepository.getUserBookCartSize(user.getId());
		System.out.println("\n\n 2 \n\n");
		modelAndView.addObject("userbookcartsize", userbookcartsize.size());
		
		System.out.println("\n\n 3 \n\n");
		List<Subscription> uSubc = userSubscriptionRepository.getUserSubscribedDetails(userId);
		System.out.println("\n\n 4 \n\n");
		if (uSubc.size() == 0) {
			if (!checkUserObjectsExists(user)) {
				modelAndView.setViewName("redirect:/register2");
				redirectAttributes.addFlashAttribute("redirectMsg",
						"Sorry. You are not subscribed User. Please subscribe now.");
			} else {
				modelAndView.setViewName("redirect:/pricing");
				redirectAttributes.addFlashAttribute("redirectMsg",
						"Sorry. You are not subscribed User. Please subscribe now.");
			}

			return modelAndView;
		}
		System.out.println("\n\n 5 \n\n");
		int maxNumberofBooks = 0;
		modelAndView.addObject("testimonials", testimonialRepository.getTestimonials(1));
		for (Subscription userSubscription : uSubc) {
			maxNumberofBooks = userSubscription.getMaxNumberofBooks();
		}
		System.out.println("\n\n 6 \n\n");
		System.out.println("\n\n maxNumberofBooks===" + maxNumberofBooks+"\n\n");
		Err err = ubcService.addUserBookToCart(new UserBookCart(userId, bookId, 0), maxNumberofBooks);
		System.out.println("\n\n maxNumberofBooks===" + maxNumberofBooks+ "Error msg >>>> " + err.getMessage()+ "\n\n\n");
		if (userId == -1 && user != null) {
			System.out.println("\n\ninside User condition \n\n\n");
			addToCartStatus = "";

			userId = user.getId();
			modelAndView.setViewName("redirect:/user/books/" + bookId);
		} else if (err.isAnError()) {
			System.out.println("\n\ninside Cart Errors \n\n\n");
			addToCartStatus = err.getMessage();
			modelAndView.setViewName("redirect:/cart/");
		} else {
			addToCartStatus = "Book Added To Cart";
			redirectAttributes.addFlashAttribute("addToCartStatus", addToCartStatus);
			modelAndView.setViewName("redirect:/");
		}

		return modelAndView;
	}

	@RequestMapping(value = "/user/return/books/{bookId}", method = RequestMethod.GET)
	public ModelAndView addBookToReturnUserCart(@PathVariable("bookId") Integer bookId) throws ParseException {
		String addToCartStatus;
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("testimonials", testimonialRepository.getTestimonials(1));
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		int userId = user.getId();
		Err err = ubcService.addUserBookToCart(new UserBookCart(userId, bookId, 1), 0);
		if (userId == -1 && user != null) {
			addToCartStatus = "";
			userId = user.getId();
			modelAndView.setViewName("redirect:/user/" + userId + "/books/" + bookId);
		} else if (err.isAnError()) {
			addToCartStatus = err.getMessage();
			modelAndView.setViewName("redirect:/cart/");

		} else {
			addToCartStatus = "Book Added To Cart";
			modelAndView.setViewName("redirect:/dashboard");
		}

		return modelAndView;
	}

	@RequestMapping(value = "/cart/", method = RequestMethod.GET)
	public Object cartPage(ModelAndView modelAndView, final RedirectAttributes redirectAttributes)
			throws ParseException {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		int userId = user.getId();
		modelAndView.setViewName("cart");
		modelAndView.addObject("userId", userId);
		modelAndView.addObject("testimonials", testimonialRepository.getTestimonials(1));
		modelAndView.addObject("checkouturl", "checkout");
		List<Object[]> books = ubcService.getUserBooks(userId, false);
		List<String> deletedBooksfromCart = new ArrayList<String>();
		for(Object[] book: books) {
			System.out.println(" \n\n book title >>>>> " + (String) book[1]+ ","+(int) book[0]+"\n\n");
			if(bookRepository.isBookBorrowed((int) book[0]) > 0) {
				deletedBooksfromCart.add((String) book[1]);
				ubcService.deleteUserBookFromCart(userId, (int) book[0], false);	
			}
		}
		List<Object[]> booksList = ubcService.getUserBooks(userId, false);
		
		List<Object[]> userbookcartsize = userBookCartRepository.getUserBookCartSize(userId);
		modelAndView.addObject("userbookcartsize", userbookcartsize.size());
		List<Object[]> returnbooks = ubcService.getUserBooks(userId, true);
		modelAndView.addObject("noOfBooksDelivery", books.size());
		modelAndView.addObject("noOfBooksReturn", returnbooks.size());
		// +redirectAttributes.getFlashAttributes().get("addToCartStatus")
		// modelAndView.addObject("addToCartStatus", addToCartStatus);
		modelAndView.addObject("readingLevels", readingLevelRepository.findAllActiveReadingLevel(1));
		modelAndView.addObject("publishers", publisherRepository.findAllActivePublisher(1));
		modelAndView.addObject("languages", languageRepository.findAllActiveLanguage(1));
		modelAndView.addObject("categories", bookCategoryRepository.findAllActiveCategory(1));
		modelAndView.addObject("authors", authorRepository.findAllActivAuthor(1));
		
		modelAndView.addObject("books", booksList);
		modelAndView.addObject("deletedbooksfromcart", deletedBooksfromCart);
		modelAndView.addObject("returnbooks", returnbooks);
		
		LocalDate datePlus = LocalDate.now().plusDays(1);
		//String plusDay = date.toString();
		modelAndView.addObject("deliverySlots", deliverySlotRepository.getAllDeliveriesForUserArea(userId));

		return modelAndView;
	}

	@RequestMapping(value = "/wishlist", method = RequestMethod.GET)
	public ModelAndView wishlistpage(final HttpServletRequest request) throws ParseException {
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		int userId = user.getId();
		modelAndView.addObject("testimonials", testimonialRepository.getTestimonials(1));
		modelAndView.addObject("checkouturl", "checkout");
		List<Object[]> userwishList = userWishBookListRepository.bookWishLists(userId);
		String isbnCode = "";
		for (Object[] userwishObj : userwishList) {
			isbnCode = (String) userwishObj[6].toString();
			modelAndView.addObject("rating_" + (int) userwishObj[0], getBookRating(isbnCode));
		}
		List<Object[]> userbookcartsize = userBookCartRepository.getUserBookCartSize(user.getId());
		modelAndView.addObject("userbookcartsize", userbookcartsize.size());
		modelAndView.addObject("noofWishListItems", userwishList.size());
		modelAndView.addObject("readingLevels", readingLevelRepository.findAllActiveReadingLevel(1));
		modelAndView.addObject("publishers", publisherRepository.findAllActivePublisher(1));
		modelAndView.addObject("languages", languageRepository.findAllActiveLanguage(1));
		modelAndView.addObject("categories", bookCategoryRepository.findAllActiveCategory(1));
		modelAndView.addObject("authors", authorRepository.findAllActivAuthor(1));
		modelAndView.addObject("userwishList", userwishList);
		modelAndView.setViewName("wishlist");

		return modelAndView;
	}

	public int getBookRating(String isbnCode) {
		List<Rating> ratings = ratingRepository.getBookRatings(isbnCode);
		int noOfUsers = ratings.size();
		int ratingValues = 0;
		for (Rating rating : ratings) {
			ratingValues += rating.getRatings();
		}
		int finalRatingScore = 0;
		if (ratingValues != 0 && noOfUsers != 0) {
			finalRatingScore = ratingValues / noOfUsers;
		}
		return finalRatingScore;
	}

	/**
	 * @param userId
	 * @param bookId
	 * @param modelAndView
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = "/user/delete/books/{bookId}/{returnFlag}", method = RequestMethod.GET)
	public ModelAndView deleteBookToUserCart(@PathVariable("bookId") Integer bookId,
			@PathVariable("returnFlag") int returnFlag, final RedirectAttributes redirectAttributes)
			throws ParseException {
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		modelAndView.addObject("testimonials", testimonialRepository.getTestimonials(1));
		int userId = user.getId();
		if (returnFlag == 0) {
			ubcService.deleteUserBookFromCart(userId, bookId, false);
		} else if (returnFlag == 1) {
			ubcService.deleteUserBookFromCart(userId, bookId, true);
		}
		String addToCartStatus = "Successfully Removed!!";
		redirectAttributes.addFlashAttribute("addToCartStatus", addToCartStatus);
		modelAndView.setViewName("redirect:/cart/");
		return modelAndView;
	}

	/**
	 * @param userId
	 * @param wishId
	 * @param modelAndView
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = "/deletewishlist/{wishId}", method = RequestMethod.GET)
	public Object deleteWishList(@PathVariable("wishId") Integer wishId, ModelAndView modelAndView)
			throws ParseException {
		UserWishBookList userWishListObj = userWishBookListRepository.findById(wishId);
		userWishBookListRepository.delete(userWishListObj);
		modelAndView.addObject("testimonials", testimonialRepository.getTestimonials(1));
		String addToCartStatus = "Successfully Removed!!";
		modelAndView.setViewName("redirect:/wishlist");

		return modelAndView;
	}

	/**
	 * @param userId
	 * @param bookId
	 * @param modelAndView
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = "/user/movetowishlist/books/{bookId}/{returnFlag}", method = RequestMethod.GET)
	public ModelAndView movetowishlist(@PathVariable("bookId") Integer bookId,
			@PathVariable("returnFlag") int returnFlag) throws ParseException {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("testimonials", testimonialRepository.getTestimonials(1));
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		int userId = user.getId();
		List<Object[]> userbookcartsize = userBookCartRepository.getUserBookCartSize(user.getId());
		modelAndView.addObject("userbookcartsize", userbookcartsize.size());
		if (returnFlag == 0) {

			ubcService.deleteUserBookFromCart(userId, bookId, false);
		} else if (returnFlag == 1) {

			ubcService.deleteUserBookFromCart(userId, bookId, true);
		}
		UserWishBookList userwishList = new UserWishBookList();

		UserWishBookList wishlist = userWishBookListRepository.findByUseridAndBookid(userId, bookId);
		if (wishlist != null) {
			modelAndView.setViewName("redirect:/cart/");
		} else {
			String addToCartStatus = "Successfully Moved to WishList!!";
			userwishList.setBook_id(bookId);
			userwishList.setUser_id(userId);
			userWishBookListRepository.save(userwishList);
			modelAndView.setViewName("redirect:/cart/");
		}
		return modelAndView;

	}

	/**
	 * @param userId
	 * @return
	 * @throws ParseException
	 */

	@RequestMapping(value = "/user/checkout", method = RequestMethod.POST)
	public Object requestBooks(@RequestParam("deliverySlotId") int deliverySlotId,
			RedirectAttributes redirectAttributes) throws ParseException {

		// Check whether he is subscribed or not or subscription expired

		ModelAndView modelAndView = new ModelAndView();

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User userExists = userService.findUserByEmail(auth.getName());
		int userId = userExists.getId();
		List<Subscription> uSubc = userSubscriptionRepository.getUserSubscribedDetails(userId);
		if (uSubc.size() == 0) {
			if (!checkUserObjectsExists(userExists)) {
				modelAndView.setViewName("redirect:/register2");
				redirectAttributes.addFlashAttribute("redirectMsg",
						"Sorry. You are not subscribed User. Please subscribe now.");
			} else {
				modelAndView.setViewName("redirect:/pricing");
				redirectAttributes.addFlashAttribute("redirectMsg",
						"Sorry. You are not subscribed User. Please subscribe now.");
			}

			return modelAndView;
		}
		modelAndView.addObject("testimonials", testimonialRepository.getTestimonials(1));
		List<Object[]> books = ubcService.getUserBooks(userId, false);
		List<Object[]> returnBooks = ubcService.getUserBooks(userId, true);
		DeliverySlot ds = deliverySlotRepository.findBySlotID(deliverySlotId);
		List<Object[]> maxDeliverys = booksBorrowRepository.getbookborrowDeliverySize(userId);
		int maxDelivery = 0;
		int maxNumberofBooks = 0;
		int maxNumberofDeliveries = 0;
		int noOfMonths = 0;
		String deliverySlotDate = "";
		String deliverySlotTime = "";

		for (Subscription userSubscription : uSubc) {
			//System.out.println("\n\n User Subscription Details are >>> "+userSubscription);
			maxNumberofBooks = (int) userSubscription.getMaxNumberofBooks();
			maxNumberofDeliveries = userSubscription.getMaxNumberofDelivery();
			noOfMonths = userSubscription.getNoofMonths();
			maxDelivery = maxDeliverys.size();
			//System.out.println("maxNumberofBooks="+maxNumberofBooks);
			//System.out.println("maxNumberofDeliveries="+maxNumberofDeliveries);
			//System.out.println("noOfMonths="+noOfMonths);
			//System.out.println("maxDelivery="+maxDelivery);
		}
		Date dateNow = new Date();
		long orderNo = dateNow.getTime();

		modelAndView.setViewName("redirect:/cart/");
		Err err = isCheckoutAllowed(maxNumberofBooks, maxNumberofDeliveries, noOfMonths, books.size(),
				returnBooks.size(), maxDelivery);
		if (err.isAnError()) {
			redirectAttributes.addFlashAttribute("addToCartStatus", err.getMessage().toString());
		} else {
			try {
				StringBuilder content = new StringBuilder();
				content.append("Book checkout Summary!" + "<br/>");
				for (Object[] book : books) {
					BookBorrow bookborrow = new BookBorrow();
					int bookId = (int) book[0];
					bookborrow.setOrderId(orderNo);
					bookborrow.setBookID(bookId);
					bookborrow.setSlotID(ds.getSlotID());
					bookborrow.setStaffID(ds.getStaffID());
					bookborrow.setUserId(userId);
					bookborrow.setType_return(0);
					booksBorrowRepository.save(bookborrow);
					Book bookObj = bookRepository.findByBookId(bookId);
					// bookObj.setBookId(bookId);
					bookObj.setIsBookBorrowed(1);
					bookRepository.save(bookObj);
				}
				Date date = new Date();
				String deliveryFrom = "";
				String deliveryTill = "";
				if(ds.getDeliveryTimeFrom() > 12 && ds.getDeliveryTimeFrom() < 24 ){
					deliveryFrom = (ds.getDeliveryTimeFrom() - 12 ) + " PM";
				} else if(ds.getDeliveryTimeFrom() == 12) {
					deliveryFrom = (ds.getDeliveryTimeFrom()  ) + " PM";
				} else if(ds.getDeliveryTimeFrom() < 12) {
					deliveryFrom = (ds.getDeliveryTimeFrom()  ) + " AM";
				} else if(ds.getDeliveryTimeFrom() == 24) {
					deliveryFrom = (ds.getDeliveryTimeFrom() - 12  ) + " AM";
				}
				
				if(ds.getDeliveryTimeTill() > 12 && ds.getDeliveryTimeTill() < 24 ){
					deliveryTill = (ds.getDeliveryTimeTill() - 12 ) + " PM";
				} else if(ds.getDeliveryTimeTill() == 12) {
					deliveryTill = (ds.getDeliveryTimeTill()  ) + " PM";
				} else if(ds.getDeliveryTimeTill() < 12) {
					deliveryTill = (ds.getDeliveryTimeTill()  ) + " AM";
				} else if(ds.getDeliveryTimeTill() == 24) {
					deliveryTill = (ds.getDeliveryTimeTill() - 12  ) + " AM";
				}

				
				String dateOfDeliverytime = deliveryFrom + " - " + deliveryTill;
				SimpleDateFormat formatter = new SimpleDateFormat("E, dd MMM yyyy");
				String dateStr = formatter.format(ds.getDateOfdelivery());
				for (Object[] returnbook : returnBooks) {
					int bookId = (int) returnbook[0];
					BookBorrow bookborrow = booksBorrowRepository.getBookBorrowsForUser(userId, bookId);
					// bookborrow.setBookID(bookId);
					bookborrow.setReturnRequestedON(date);
					bookborrow.setBookReturnStatus(1);
					bookborrow.setType_return(1);
					booksBorrowRepository.save(bookborrow);

					// Book bookObj = bookRepository.findByBookId(bookId);
					// bookObj.setBookId(bookId);
					// bookObj.setIsBookBorrowed(0);
					//
					// bookRepository.save(bookObj);

				}
				ubcService.clearUserCart(userId, false);
				ubcService.clearUserCart(userId, true);

				sendCheckoutEmail(userExists.getEmailId(), "Bookshelf-library.com : Order Details !!!",
						userExists.getName(), content.toString(), "/emailTemplatess/email", books, returnBooks, orderNo,
						dateStr, dateOfDeliverytime, userExists);
				modelAndView.setViewName("redirect:/cartsuccess/");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			}
		}
		return modelAndView;
	}

	public Err isCheckoutAllowed(int maxNumberofBooks, int maxNumberofDeliveries, int noOfMonths, int cartSize,
			int returnCartSize, int maxDeliveryAllowed1) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		System.out.println("maxNumberofBooks>>> "+maxNumberofBooks);
		System.out.println("maxNumberofDeliveries>>> "+maxNumberofDeliveries);
		System.out.println("noOfMonths>>> "+noOfMonths);
		System.out.println("maxDeliveryAllowed1>>> "+maxDeliveryAllowed1);
		
		System.out.println("cartSize>>> "+cartSize);
		System.out.println("returnCartSize>>> "+returnCartSize);
		String addToCartStatus;
//		int userId = user.getId();
		int noOfHoldingBooks = 0;
		int noOfReturnedBooks1 = 0;
		int noOfHoldingBooksinCurrentMonth1 = 0;
		int totalHoldingBooks = 0;
		int sumOfDeliveriesofCurrentMonth = 0;
		int userId = 0;
		
		if (user != null) {
			userId = user.getId();
			List<BookBorrow> bw = booksBorrowRepository.getCurrentMonthDeliveries(userId);
			
			if(bw != null) {
				sumOfDeliveriesofCurrentMonth = bw.size();
			}
			List<BookBorrow> bw1 = booksBorrowRepository.getActiveBookBorrowsForUser(userId);
			if(bw1 != null ) {
				noOfHoldingBooks = bw1.size();
				noOfHoldingBooks = noOfHoldingBooks - returnCartSize;
			}
			
			/*List<BookBorrow> bw2 = booksBorrowRepository.getActiveBookBorrowsForUser(userId);
			if(bw2 != null) {
				noOfReturnedBooks = bw2.size();
			}*/
			/*List<BookBorrow> bw3 = booksBorrowRepository.getCurrentMonthBookBorrowsListForUser(userId);
			if(bw3 != null ) {
				noOfHoldingBooksinCurrentMonth = bw3.size();
			}*/
			
			totalHoldingBooks = noOfHoldingBooks + cartSize;
			//totalHoldingBooks = noOfHoldingBooks + noOfHoldingBooksinCurrentMonth + cartSize;
			//totalHoldingBooks = totalHoldingBooks - returnCartSize;
			
			//System.out.println("noOfHoldingBooks="+noOfHoldingBooks);
			//System.out.println("noOfReturnedBooks="+noOfReturnedBooks);
			//System.out.println("noOfHoldingBooksinCurrentMonth="+noOfHoldingBooksinCurrentMonth);
			//System.out.println("totalHoldingBooks="+totalHoldingBooks);
			//System.out.println("totalHoldingBooks="+totalHoldingBooks);
			
			
		} else {
			//System.out.println("inside condition 0");
			return new Err(true, "User is not logged In");
		}
		Area areaExist = areaRepository.findByPincodeAndIsActive(user.getPincode(), 1);
		//check delivery is available or not
		if (areaExist == null) {
			//System.out.println("inside condition 1");
			return new Err(true, "Opps. There is no delivery available for your area.");
			//Check whether user is subscribed or not
		} else if (userSubscriptionRepository.isUserSubscribed(userId) == 0) {
			//System.out.println("inside condition 2");
			return new Err(true, "Opps. You are not subscribed user");
			// return false;
			//check whether he is trying to get more deliveries than his usual quota
		//}// else if(sumOfDeliveriesofCurrentMonth >= maxNumberofDeliveries) {
			//System.out.println("sumOfDeliveriesofCurrentMonth="+sumOfDeliveriesofCurrentMonth+"== maxNumberofDeliveries="+maxNumberofDeliveries);
			//return new Err(true, "Opps. Sorry you are exceeding your delivery limits per month.");
			//check whether he is trying to get more books than his usual quota
		} else if(cartSize > maxNumberofBooks) {
			System.out.println("cartSize="+cartSize+"== maxNumberofBooks="+maxNumberofBooks);
			return new Err(true, "Opps. Sorry you are exceeding your delivery limits.");
		} else if(totalHoldingBooks > maxNumberofBooks) {
			System.out.println("totalHoldingBooks="+totalHoldingBooks+"== totalHoldingBooks="+totalHoldingBooks);
			return new Err(true, "Opps. Sorry you are exceeding your rental limits.");
		}
		//else if (noOfHoldingBooks >= maxNumberofBooks) {
			//System.out.println("inside condition 3");
			//return new Err(true, "Opps. Sorry you are exceeding your rental limits.");
		//} //else if (noOfHoldingBooksinCurrentMonth >= maxNumberofBooks) {
			//System.out.println("inside condition 4");
			//return new Err(true, "Opps. Sorry you are exceeding your rental limits for this current Month");
		//} else if (totalHoldingBooks >= maxNumberofBooks) {
			//System.out.println("inside condition 5");
			//return new Err(true, "Opps. Sorry you are exceeding your rental limits.");
//		} else if (cartSize > maxNumberofDeliveries) {
//			return new Err(true, "Opps. Sorry you are exceeding your delivery limits.");
		//} else if (maxDeliveryAllowed >= maxNumberofDeliveries) {
			//System.out.println("inside condition 6");
			//return new Err(true, "Opps. Sorry you are exceeding your delivery limits.");
		//} else if ((noOfHoldingBooks + noOfReturnedBooks) > (noOfMonths * maxNumberofBooks)) {
			//System.out.println("inside condition 7");
		//	return new Err(true, "Opps. Sorry you are exceeding your rental limits for this whole subscription!");
		//}
		
		//System.out.println("******* inside NO condition *** ");
		return new Err(false, "");

	}

	
	
	public void sendEmail(String userEmailId, String subject, String userName, String content, String templatePath)
			throws MessagingException, Exception {

		Mail mail = new Mail();
		mail.setFrom(env.getProperty("support.email"));
		mail.setTo(userEmailId);
		mail.setSubject(subject);
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("name", userName + " , ");
		model.put("signature", "http://bookshelf-library.com/");
		model.put("content", content);
		mail.setModel(model);

		emailService.sendSimpleMessage(mail, templatePath);
	}

	public void sendEmail(String userEmailId, String subject, String userName, String content, String templatePath,Date validUpto,String planName,int noOfBooks)
			throws MessagingException, Exception {

		Mail mail = new Mail();
		mail.setFrom(env.getProperty("support.email"));
		mail.setTo(userEmailId);
		mail.setSubject(subject);
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("username", userName + " , ");
		
		model.put("planname", planName);
		model.put("noOfBooks", noOfBooks);
		model.put("endDate", validUpto);
		
		model.put("signature", "http://bookshelf-library.com/");
		model.put("content", content);
		mail.setModel(model);

		emailService.sendSimpleMessage(mail, templatePath);
	}
	
	public void sendCheckoutEmail(String userEmailId, String subject, String userName, String content,
			String templatePath, List<Object[]> books, List<Object[]> returnBooks, long orderId,
			String deliverySlotDate, String deliverySlotTime, User user) throws MessagingException, Exception {

		Mail mail = new Mail();
		mail.setFrom(env.getProperty("support.email"));
		mail.setTo(userEmailId);
		mail.setSubject(subject);
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("name", userName + " , ");
		model.put("books", books);
		model.put("returnbooks", returnBooks);
		model.put("deliverySlotDate", deliverySlotDate);
		model.put("deliverySlotTime", deliverySlotTime);

		model.put("orderNumber", orderId);

		model.put("houseNumber", user.getHouseNumber());
		model.put("street", user.getStreet());
		model.put("locality", user.getLocality());
		model.put("pincode", user.getPincode());

		model.put("signature", "http://bookshelf-library.com/");
		model.put("content", content);
		mail.setModel(model);

		emailService.sendSimpleMessage(mail, templatePath);
	}

	@RequestMapping(value = { "/howitworks" }, method = RequestMethod.GET)
	public ModelAndView howitworks() {

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("readingLevels", readingLevelRepository.findAllActiveReadingLevel(1));
		modelAndView.addObject("publishers", publisherRepository.findAllActivePublisher(1));
		modelAndView.addObject("languages", languageRepository.findAllActiveLanguage(1));
		modelAndView.addObject("categories", bookCategoryRepository.findAllActiveCategory(1));
		modelAndView.addObject("testimonials", testimonialRepository.getTestimonials(1));
		
		modelAndView.setViewName("howitworks");
		return modelAndView;

	}

	@RequestMapping(value = { "/terms" }, method = RequestMethod.GET)
	public ModelAndView terms() {

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("terms");
		return modelAndView;

	}

	@RequestMapping(value = { "/cartsuccess/" }, method = RequestMethod.GET)
	public ModelAndView cartsuccess() {

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("cartsuccess");
		return modelAndView;

	}

	@RequestMapping(value = { "/testimonial" }, method = RequestMethod.GET)
	public ModelAndView addTestimonial() {
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		int userId = user.getId();
		List<Object[]> userbookcartsize = userBookCartRepository.getUserBookCartSize(user.getId());
		modelAndView.addObject("userbookcartsize", userbookcartsize.size());
		if (testimonialRepository.findByUserid(userId).isPresent()) {
			modelAndView.addObject("isExist", "Already you have entered the testimonial.");
		}
		modelAndView.addObject("readingLevels", readingLevelRepository.findAllActiveReadingLevel(1));
		modelAndView.addObject("publishers", publisherRepository.findAllActivePublisher(1));
		modelAndView.addObject("languages", languageRepository.findAllActiveLanguage(1));
		modelAndView.addObject("categories", bookCategoryRepository.findAllActiveCategory(1));
		modelAndView.addObject("authors", authorRepository.findAllActivAuthor(1));
		modelAndView.addObject("testimonials", testimonialRepository.getTestimonials(1));
		modelAndView.setViewName("testimonial");
		return modelAndView;

	}

	@RequestMapping(value = { "/testimonial" }, method = RequestMethod.POST)
	public ModelAndView addTestimonial(@RequestParam("testimonial") final String testimonialName) {
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		int userId = user.getId();
		modelAndView.addObject("testimonials", testimonialRepository.getTestimonials(1));
		Testimonial testimonial = new Testimonial();
		//if (isApproved == 0) {
			testimonial.setTestimonial(testimonialName);
			testimonial.setUserid(userId);
			testimonial.setIsApproved(0);
			testimonial.setIsActive(1);
			testimonialService.addTestimonial(testimonial);
			modelAndView.addObject("successMsg", "We Thank You for taking time out and sharing Your Feedback!");
			
			modelAndView.setViewName("testimonial");
/*		} else if (isApproved == 1) {
			modelAndView.setViewName("testimonial");
		}
*/		return modelAndView;

	}

	@RequestMapping(value = { "/myplan" }, method = RequestMethod.GET)
	public ModelAndView myPlan() {
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		int userId = user.getId();
		List<Object[]> usc = userSubscriptionRepository.getUserCurrentSubscriptionDetail(userId);
		List<Object[]> userbookcartsize = userBookCartRepository.getUserBookCartSize(userId);
		modelAndView.addObject("userbookcartsize", userbookcartsize.size());
		modelAndView.addObject("readingLevels", readingLevelRepository.findAllActiveReadingLevel(1));
		modelAndView.addObject("publishers", publisherRepository.findAllActivePublisher(1));
		modelAndView.addObject("languages", languageRepository.findAllActiveLanguage(1));
		modelAndView.addObject("categories", bookCategoryRepository.findAllActiveCategory(1));
		modelAndView.addObject("authors", authorRepository.findAllActivAuthor(1));
		modelAndView.addObject("usc", usc);
		modelAndView.setViewName("myplan");
		return modelAndView;

	}

	@RequestMapping(value = { "/paymentconfirmation" }, method = RequestMethod.GET)
	public ModelAndView paymentconfirmation(@RequestParam("payment_id") final String payment_id,
		@RequestParam("payment_status") final String payment_status, @RequestParam("id") final String id,
		@RequestParam("transaction_id") final String transaction_id) throws MessagingException, Exception {
		ModelAndView modelAndView = new ModelAndView();

		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		int userId = user.getId();
		UserSubscription usc = userSubscriptionRepository.getUserCurrentSubscriptionDetailByStatus(transaction_id,
				"pending");
		if (payment_status.equalsIgnoreCase("credit") || payment_status.equalsIgnoreCase("success")) {
			usc.setPaymentStatus("success");
			usc.setPaymentTransactionId(payment_id);
			userSubscriptionRepository.save(usc);
		}

		List<Subscription> subscriptions = subscriptionRepository.getSubscriptionsById(usc.getSubscriptionId());
		//System.out.println(" inside subscribeMe 1");
		//int amount = 0;
		//int noofMonths = 0;
		int noOfBooks=0;
		String planName = "";
		for (Subscription subscription : subscriptions) {
			//amount = subscription.getAmount();
			//noofMonths = subscription.getNoofMonths();
			noOfBooks= subscription.getMaxNumberofBooks();
			planName = subscription.getSubscriptionName();
			
		}

		modelAndView.addObject("usc", userSubscriptionRepository.getUserCurrentSubscriptionDetail(userId));
		modelAndView.addObject("payment_id", payment_id);
		modelAndView.addObject("paymentMsg", "You have successly subscribed");
		modelAndView.addObject("userSubcIdValue",-1);
		modelAndView.addObject("subcIdValue",-1);
		
		
		modelAndView.addObject("paybyCashFlag", 0);
		
		modelAndView.setViewName("paymentsuccess");
		
		SimpleDateFormat formatter = new SimpleDateFormat(
			      "dd/MM/yyyy");
		Date validUpto1 = formatter.parse(formatter.format(usc.getValidTo()));
		

		String content = "";
		sendEmail(user.getEmailId(), "BookShelf-library.com: Successfully Subscribed !!!", user.getName(), content,
				"/emailTemplatess/email-subscribe",validUpto1,planName,noOfBooks);

		return modelAndView;

	}

	public PaymentOrder getPaymentOrder(String id) {
		/*
		 * Get details of payment order when order id is given
		 */

		PaymentOrder paymentOrder = null;
		try {
			// String clientId = "test_J5M3yYug3e8r2FXTNjZXoTAuHDNZiMjGuUS";
			// String client_Secret =
			// "test_2FWX8pILyNnQdaPRVXONZz7fIFoeUjbLabWaqDIqolG1qrnBGQCwdMwKzkLKNBzvuCVJih3j4bJpQoLhbpx0s64pUgSQFrUyjvVCyXN4R91AnEMzyroqhgdBOgQ";
			String clientId = "erVhj8zEjRjmTobcLYL7YAGewJfXrnpiFOT2RHzB";
			String client_Secret = "4as5UgBgCvAWAAY9xuqO5LGL72bYaHZqz28NGCM06ab4rSIKwupVlLazpUnILNljD7aYu07zH5n1Ofvj7JWNHKMAYRlG6vC57hdBboOc4wUEvgngP2CITFfZzp7NJldu";

			/*
			 * ApiContext context = ApiContext.create(clientId, client_Secret,
			 * ApiContext.Mode.TEST);
			 */
			ApiContext context = ApiContext.create(clientId, client_Secret, ApiContext.Mode.LIVE);

			PAYMENT_REQUEST_ENDPOINT = context.getApiPath(Constants.PATH_PAYMENT_REQUEST);
			headers.put(Constants.HEADER_AUTHORIZATION, context.getAuthorization());

			Instamojo api = new InstamojoImpl(context);

			paymentOrder = api.getPaymentOrder(id);

		} catch (HTTPException e) {

		} catch (ConnectionException e) {
		}
		return paymentOrder;
	}

	@RequestMapping(value = { "/events" }, method = RequestMethod.GET)
	public ModelAndView events() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("readingLevels", readingLevelRepository.findAllActiveReadingLevel(1));
		modelAndView.addObject("publishers", publisherRepository.findAllActivePublisher(1));
		modelAndView.addObject("languages", languageRepository.findAllActiveLanguage(1));
		modelAndView.addObject("categories", bookCategoryRepository.findAllActiveCategory(1));
		modelAndView.addObject("authors", authorRepository.findAllActivAuthor(1));
		modelAndView.setViewName("noevents");
		return modelAndView;

	}

	
	@RequestMapping(value = { "/checkoutdetails" }, method = RequestMethod.GET)
	public ModelAndView checkoutcondition() {
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User userExists = userService.findUserByEmail(auth.getName());
		int userId = userExists.getId();
		List<Subscription> uSubc = userSubscriptionRepository.getUserSubscribedDetails(userId);
		modelAndView.setViewName("checkoutdetails");
		List<Object[]> maxDeliverys = booksBorrowRepository.getbookborrowDeliverySize(userId);
		List<Object[]> books = ubcService.getUserBooks(userId, false);
		List<Object[]> returnBooks = ubcService.getUserBooks(userId, true);
	
		int maxDelivery = 0;
		int maxNumberofBooks = 0;
		int maxNumberofDeliveries = 0;
		int noOfMonths = 0;

		for (Subscription userSubscription : uSubc) {
			//System.out.println("\n\n User Subscription Details are >>> "+userSubscription);
			maxNumberofBooks = (int) userSubscription.getMaxNumberofBooks();
			maxNumberofDeliveries = userSubscription.getMaxNumberofDelivery();
			noOfMonths = userSubscription.getNoofMonths();
			maxDelivery = maxDeliverys.size();
			modelAndView.addObject("maxNumberofBooks",maxNumberofBooks);
			modelAndView.addObject("maxNumberofDeliveries",maxNumberofDeliveries);
			modelAndView.addObject("noOfMonths",noOfMonths);
			modelAndView.addObject("maxDelivery",maxDelivery);
		}

		Err err = isCheckoutAllowed(maxNumberofBooks, maxNumberofDeliveries, noOfMonths, books.size(),
				returnBooks.size(), maxDelivery);

		if (err.isAnError()) {
			modelAndView.addObject("errorDetails", err.getMessage().toString());

		}

		
		return modelAndView;

	}

}
