package com.example.rest_test;

import com.example.rest_test.business.domain.Logs_domain;
import com.example.rest_test.business.service.LogicService;
import com.example.rest_test.entity.Actions;
import com.example.rest_test.entity.Logs;
import com.example.rest_test.repo.IActionsRepo;
import com.example.rest_test.repo.ILogsRepo;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.swing.*;
import java.io.IOException;
import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

@SpringBootApplication
public class RestTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestTestApplication.class, args);
	}

	@RestController
	@RequestMapping("/controller")
	public class TestController{
		@Autowired
		private LogicService logicService;

		@RequestMapping(method = RequestMethod.GET,path = "/getLogsById/{userId}")
		@ResponseStatus(HttpStatus.OK)
		public Iterable<Logs_domain> getLogsById(@PathVariable(value = "userId") String userId){
			return logicService.getLogsById(userId);
		}

		@RequestMapping(method = RequestMethod.GET,path = "/getLogsByType/{type}")
		@ResponseStatus(HttpStatus.OK)
		public Iterable<Logs_domain> getLogsByType(@PathVariable(value = "type") String type){
			return logicService.getLogsByType(type);
		}

		@RequestMapping(method = RequestMethod.GET,path = "/logsByTypeTime/{types}/{timeFrom}/{timeTo}")
		@ResponseStatus(HttpStatus.OK)
		public Iterable<Logs_domain> getLogsByTypeTime(@PathVariable(value = "timeFrom")String timeFrom,
												   @PathVariable(value = "timeTo")String timeTo,
												   @PathVariable(value = "types")String types){
			return logicService.getLogsByTypeTime(timeFrom,timeTo,types);
		}

		@RequestMapping(method = RequestMethod.GET,path = "/logsByIdType/{userId}/{type}")
		@ResponseStatus(HttpStatus.OK)
		public Iterable<Logs_domain> getLogsByIdAndType(@PathVariable(value = "userId")String userId,
												   @PathVariable(value = "type")String type){
			return logicService.getLogsByIdandType(userId, type);
		}

		@RequestMapping(method = RequestMethod.GET,path = "/logsByIdTime/{userId}/{timeFrom}/{timeTo}")
		@ResponseStatus(HttpStatus.OK)
		public Iterable<Logs_domain> getLogsByIdTime(@PathVariable(value = "userId")String userId,
													   @PathVariable(value = "timeFrom")String timeFrom,
													 @PathVariable(value = "timeTo")String timeTo){
			return logicService.getLogsByIdTime(userId, timeFrom,timeTo);
		}

		@RequestMapping(method = RequestMethod.GET,path = "/logsByIdTypeTime/{userId}/{timeFrom}/{timeTo}/{type}")
		@ResponseStatus(HttpStatus.OK)
		public Iterable<Logs_domain> getLogsByITT(@PathVariable(value = "userId")String userId,
													 @PathVariable(value = "timeFrom")String timeFrom,
													 @PathVariable(value = "timeTo")String timeTo,
												  @PathVariable(value = "type")String type){
			return logicService.getLogsByITT(userId, timeFrom,timeTo, type);
		}

		@RequestMapping(method = RequestMethod.POST,path = "/saveNewLogs")
		@ResponseStatus(HttpStatus.OK)
		public void saveToDatabase(){

			//test data------------
			String uid = "user5";
			List<String> types = new ArrayList<>();
			types.add("click");
			types.add("view");
			types.add("navigate");
			Date dt = new Date();
			HashMap<String,String> params = new HashMap<>();
			params.put("locationX","25");
			params.put("locationY","56");
			params.put("viewedId", "viewd");
			params.put("pageFrom", "cost");
			params.put("pageTo","info");
			//--------------
			logicService.saveToDatabase(uid , dt ,types,params);
			//logicService.saveToDatabase("user5",);
		}

		@RequestMapping(method = RequestMethod.GET,path = "/getAllLogs")
		@ResponseStatus(HttpStatus.OK)
		public Iterable<Logs> getAllLogs(){
			return logicService.getAllLogs();
		}
	}



}
