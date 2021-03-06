package poly.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import poly.service.IMyRedisService;

@Controller
public class MyRedisController {

		
		private Logger log = Logger.getLogger(this.getClass());
		
		@Resource(name="MyRedisService")
		private IMyRedisService myRedisService;
		
		/*
		 * 빅데이터 처리를 위한 세팅 
		 * */
		@RequestMapping(value="myRedis/test")
		@ResponseBody
		public String myRedis(HttpServletRequest request, HttpServletResponse response) throws Exception{
			
			log.info(this.getClass().getName() + " .myReis start !");
			
			myRedisService.doSaveData();
			
			log.info(this.getClass().getName() + " .myReis end !");
			
			return "success";
		}
		
		@RequestMapping(value="myRedis/test02")
		@ResponseBody
		public String test02(HttpServletRequest request, HttpServletResponse response) throws Exception{
			
			log.info(this.getClass().getName() + " .myReis start !");
			
			myRedisService.doSaveDataforList();
			
			log.info(this.getClass().getName() + " .myReis end !");
			
			return "success";
		}
		
//		@RequestMapping(value="myRedis/test03")
//		@ResponseBody
///		public String test03(HttpServletRequest request, HttpServletResponse response) throws Exception{
///			
///			log.info(this.getClass().getName() + " .doSaveDataforListJSON start !");
//			
///			myRedisService.doSaveDataforListJSON();
///			
//		log.info(this.getClass().getName() + " .doSaveDataforListJSON end !");
//			
///			return "success";
	//	}
	

}
