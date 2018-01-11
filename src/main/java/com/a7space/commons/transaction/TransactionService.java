package com.a7space.commons.transaction;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 * <p>事务管理器</p>
 */
public class TransactionService {

	private final static Log log = LogFactory.getLog(TransactionService.class);

	/**
	 * 事务传播属性 -- REQUIRED
	 */
	public final static TransactionDefinition PROPAGATION_REQUIRED = new DefaultTransactionDefinition(
			TransactionDefinition.PROPAGATION_REQUIRED);

	/**
	 * 事务传播属性 -- REQUIRES_NEW
	 */
	public final static TransactionDefinition PROPAGATION_REQUIRES_NEW = new DefaultTransactionDefinition(
			TransactionDefinition.PROPAGATION_REQUIRES_NEW);

	/**
	 * Spring 平台事务管理
	 */
	private PlatformTransactionManager transactionManager;

	/**
	 * 事务管理器构造
	 *
	 * @param transactionManager Spring 平台事务管理器
	 */
	public TransactionService(PlatformTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}

	/**
	 * <p>获取事务传播属性为 {@link TransactionDefinition#PROPAGATION_REQUIRED PROPAGATION_REQUIRED} 的事务状态</p>
	 *
	 * @return Spring事务状态对象
	 */
	public TransactionStatus getRequiredTransactionStatus() {
		return transactionManager.getTransaction(PROPAGATION_REQUIRED);
	}

	/**
	 * <p>获取指定事务传属性的事务状态对象</p>
	 *
	 * @param definition 可以使用预定义的 {@link #PROPAGATION_REQUIRES_NEW} 值，
	 *                   也可以使用  {@link DefaultTransactionDefinition} 类自行创建
	 * @return Spring事务状态对象
	 */
	public TransactionStatus getTransactionStatus(TransactionDefinition definition) {
		return transactionManager.getTransaction(definition);
	}

	/**
	 * <p>提交事务。如果事务状态已经处于完成状态，将不能进行回滚。</p>
	 *
	 * @param status 事务状态对象
	 */
	public void commit(TransactionStatus status) {
		if (status == null) {
			log.error("Transaction status object is null, transaction cannot commit");
			return;
		}
		if (status.isCompleted()) {
			log.warn("Transaction is already completed - do not call commit more than once per transaction");
			return;
		}
		transactionManager.commit(status);
	}

	/**
	 * <p>回滚事务。如果事务状态已经处于完成状态，将不能进行回滚。</p>
	 *
	 * @param status 事务状态对象
	 */
	public void rollback(TransactionStatus status) {
		if (status == null) {
			log.error("Transaction status object is null, transaction cannot rollback");
			return;
		}
		if (status.isCompleted()) {
			log.warn("Transaction is already completed - do not call rollabck more than once per transaction");
			return;
		}
		transactionManager.rollback(status);
	}
}
