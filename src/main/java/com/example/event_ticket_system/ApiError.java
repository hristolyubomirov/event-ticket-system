package com.example.event_ticket_system;

import java.util.Map;

public record ApiError(int status, String msg,long timestamp,String path,Map<String,String> validationErrors) {

}
