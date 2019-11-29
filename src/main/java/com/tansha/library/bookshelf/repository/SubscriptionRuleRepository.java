package com.tansha.library.bookshelf.repository;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.tansha.library.bookshelf.model.SubscriptionRule;

import org.springframework.data.repository.CrudRepository;
//import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
//import org.springframework.data;
//import org.springframework.boot.autoconfigur
@Transactional
public interface SubscriptionRuleRepository extends CrudRepository<SubscriptionRule,String> {

	List<SubscriptionRule> findByRuleName(String ruleName);
	List<SubscriptionRule> findByRuleId(int subc_ID);
	List<SubscriptionRule> findByIsActive(int isActive);
}
