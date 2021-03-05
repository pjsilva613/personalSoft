package com.buildings.msservicebuildings.controller;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@Builder
public class ErrorMessage {

    private String code;
    private List<Map<String, String>>  Message;
    
    
	public ErrorMessage() {
		super();
	}
	public ErrorMessage(String code, List<Map<String, String>> message) {
		super();
		this.code = code;
		Message = message;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public List<Map<String, String>> getMessage() {
		return Message;
	}
	public void setMessage(List<Map<String, String>> message) {
		Message = message;
	}
	@Override
	public String toString() {
		return "ErrorMessage [code=" + code + ", Message=" + Message + "]";
	}

    
}
