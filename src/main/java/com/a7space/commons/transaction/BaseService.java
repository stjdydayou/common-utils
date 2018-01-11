/**
 *
 */
package com.a7space.commons.transaction;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * @Title: BaseService.java
 * @Description: Service 基础类，提供基本Service支持
 * @version V1.0
 */

public class BaseService extends ApplicationObjectSupport {

    protected final Log log = LogFactory.getLog(this.getClass().getName());

    @Autowired
    protected TransactionTemplate transactionTemplate;

    public TransactionTemplate getTransactionTemplate() {
        return transactionTemplate;
    }

}
