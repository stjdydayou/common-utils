/*
 * Copyright (C) 2008 Happy Fish / YuQing
 * 
 * FastDFS Java Client may be copied only under the terms of the GNU Lesser
 * General Public License (LGPL). Please visit the FastDFS Home Page
 * http://www.csource.org/ for more detail.
 */

package com.a7space.commons.exception;

/**
* My Exception
* @author Happy Fish / YuQing
* @version Version 1.0
*/
public class FDFSException extends Exception {
    /**
     * 
     */
    private static final long serialVersionUID = 2699517589111700417L;

    public FDFSException() {
    }

    public FDFSException(String message) {
        super(message);
    }
}
