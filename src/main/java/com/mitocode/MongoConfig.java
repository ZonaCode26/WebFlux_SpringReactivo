package com.mitocode;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.mongodb.ReactiveMongoDatabaseFactory;
import org.springframework.data.mongodb.ReactiveMongoTransactionManager;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.transaction.ReactiveTransactionManager;
import org.springframework.transaction.reactive.TransactionalOperator;

@Configuration
//https://stackoverflow.com/questions/23517977/spring-boot-mongodb-how-to-remove-the-class-column
public class MongoConfig implements InitializingBean {

	@Autowired
	@Lazy
	private MappingMongoConverter mappingMongoConverter;

	@Override
	public void afterPropertiesSet() throws Exception {
		mappingMongoConverter.setTypeMapper(new DefaultMongoTypeMapper(null));
	}
	
	/*//Para transacciones (requiere un cluster configurado, ver MongoDB Atlas)
	@Bean
	public TransactionalOperator transactionalOperator(ReactiveTransactionManager txm) {
		return TransactionalOperator.create(txm);
	}
	
	@Bean
	public ReactiveTransactionManager transactionManager(ReactiveMongoDatabaseFactory dbf) {
		return new ReactiveMongoTransactionManager(dbf);
	}
	*/
	
}
