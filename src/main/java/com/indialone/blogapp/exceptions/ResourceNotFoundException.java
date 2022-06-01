package com.indialone.blogapp.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException {

	String resourceName;
	String resourceFieldName;
	long resourceId;

	public ResourceNotFoundException(String resourceName, String resourceFieldName, long resourceId) {
		super(String.format("%s not found with %s :  %1", resourceName, resourceFieldName, resourceId));
		this.resourceName = resourceName;
		this.resourceFieldName = resourceFieldName;
		this.resourceId = resourceId;
	}
	
	
	
}
