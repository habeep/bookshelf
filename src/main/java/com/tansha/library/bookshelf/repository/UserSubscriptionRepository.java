package com.tansha.library.bookshelf.repository;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.tansha.library.bookshelf.model.Subscription;
import com.tansha.library.bookshelf.model.UserSubscription;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
//import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
//import org.springframework.data;
//import org.springframework.boot.autoconfigur
@Transactional
public interface UserSubscriptionRepository extends CrudRepository<UserSubscription,String> {

	UserSubscription findById(int id);
	List<UserSubscription> findByUserId(int userId);
	
	//@Query("SELECT userSubscription from UserSubscription userSubscription where userSubscription.userId=?1 AND userSubscription.validTo > NOW() AND userSubscription.paymentStatus = 'success' ")
	@Query("SELECT subscriptions.subcId,subscriptions.subscriptionName,subscriptions.subscriptionDescription,subscriptions.subscriptionSummary,subscriptions.amount,"
			+ " subscriptions.noofMonths,subscriptions.maxNumberofBooks,subscriptions.maxNumberofDelivery from Subscription subscriptions"
			//+ " INNER JOIN  SubscriptionRule subscriptionRule ON subscriptionRule.ruleId = subscriptions.subcId"
			+ " INNER JOIN UserSubscription userSubscription ON userSubscription.subscriptionId = subscriptions.subcId "
			+ " WHERE  subscriptions.isActive = 1  AND userSubscription.userId=?1 AND userSubscription.validTo >= NOW() AND (userSubscription.paymentStatus = 'success' OR userSubscription.paymentStatus = 'pending' OR userSubscription.paymentStatus = 'paybycash') ")
	List<Object[]> getUserSubscriptionDetails(int userId);
	
	@Query("SELECT subscriptions "
			+ "  from Subscription subscriptions"
			+ " INNER JOIN UserSubscription userSubscription ON userSubscription.subscriptionId = subscriptions.subcId "
			+ " WHERE  subscriptions.isActive = 1 AND userSubscription.userId=?1 AND userSubscription.validTo >= NOW() AND userSubscription.paymentStatus = 'success' ")
	List<Subscription> getUserSubscribedDetails(int userId);
	 
	@Query("SELECT subscriptions.subscriptionName,subscriptions.maxNumberofDelivery,userSubscription.validTo,subscriptions.maxNumberofBooks "
			+ "  from Subscription subscriptions"
			+ " INNER JOIN UserSubscription userSubscription ON userSubscription.subscriptionId = subscriptions.subcId "
			+ " WHERE  subscriptions.isActive = 1 AND userSubscription.userId=?1 AND userSubscription.validTo >= NOW() AND userSubscription.paymentStatus = 'success' ")
	List<Object[]> getUserCurrentSubscriptionDetail(int userId);
	
	@Query("SELECT subscriptions.subscriptionName,subscriptions.maxNumberofDelivery,userSubscription.validTo,subscriptions.amount,userSubscription.paymentUrl,userSubscription.paymentStatus,userSubscription.subscriptionId,userSubscription.id "
			+ "  from Subscription subscriptions"
			+ " INNER JOIN UserSubscription userSubscription ON userSubscription.subscriptionId = subscriptions.subcId "
			+ " WHERE  subscriptions.isActive = 1 AND userSubscription.userId=?1 AND userSubscription.validTo >= NOW() AND (userSubscription.paymentStatus = 'pending' OR userSubscription.paymentStatus = 'paybycash') ")
	List<Object[]> getUserCurrentSubscriptionDetails(int userId);

	
	
	@Query("SELECT userSubscription "
			+ "  from UserSubscription userSubscription"
			+ " WHERE  userSubscription.paymentTransactionId=?1 AND userSubscription.validTo >= NOW() AND userSubscription.paymentStatus = ?2")
	UserSubscription getUserCurrentSubscriptionDetailByStatus(String paymentTransactionId,String paymentStatus);
	
	@Query("SELECT userSubscription "
			+ "  from UserSubscription userSubscription"
			+ " WHERE  userSubscription.userId=?1 AND userSubscription.validTo >= NOW() AND userSubscription.paymentStatus = ?2")
	UserSubscription getUserPendingSubscriptionDetailByStatus(int userId,String paymentStatus);
	
	
	@Query("SELECT count(userSubscription) from UserSubscription userSubscription "
			+ " WHERE    userSubscription.userId=?1 AND userSubscription.validTo >= CURRENT_DATE() AND userSubscription.paymentStatus = 'success'  ")
	Integer isUserSubscribed(int userId);
	
	@Query("SELECT count(userSubscription) from UserSubscription userSubscription "
			+ " WHERE    userSubscription.userId=?1 AND userSubscription.validTo >= NOW()  ")
	Integer isUserSubscribedCreated(int userId);
	
	@Query("SELECT count(userSubscription) from UserSubscription userSubscription "
			+ " WHERE    userSubscription.userId=?1 AND userSubscription.validTo >= NOW() AND (userSubscription.paymentStatus = 'pending' OR userSubscription.paymentStatus = 'paybycash')  ")
	Integer isUserSubscribedPaid(int userId);
	
	@Query("SELECT count(userSubscription) from UserSubscription userSubscription "
			+ " WHERE    userSubscription.userId=?1 AND userSubscription.validTo >= NOW() AND  userSubscription.paymentStatus = 'paybycash'  ")
	Integer isUserSubscribedPaidByCash(int userId);
	
	
}
