/*
 * ResourceNotFoundException.java 1.0 2/11/2020
 */

/*
  This file represents a exception when a resource is not found.

  @author Gonzalo Berné
  @version 1.0, 2/11/2020
 */

package com.project.LearnAndTrade.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
