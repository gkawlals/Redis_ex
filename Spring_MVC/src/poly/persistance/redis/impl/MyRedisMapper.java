package poly.persistance.redis.impl;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import poly.persistance.redis.IMyRedisMapper;
import poly.util.CmmUtil;

/*
 *  Ioc 컨테이너에 등록할때 메타정보 주입해주는 어노테이션이다.
 *  메모리에 올릴때, Service, Controller 선언하면 자동으로 싱글톤으로 메모리에 올리고,
 *  @Component = 컴포넌트 스캔할 패키지가 정의되면, 그 패키지를 모두 조회, 
 *  컴포넌트로 선언된 자바파일들을 모두 싱글톤으로 메모리에 올린다.
 * */

@Component("MyRedisMapper")
public class MyRedisMapper implements IMyRedisMapper{

	//Autowired = 위의 컴포넌트를 스캔 후 빈을 자동 주입한다. 
	@Autowired
	public RedisTemplate<String, Object> redisDB;
	
	private Logger log = Logger.getLogger(this.getClass());
	
	@Override
	public void doSaveData() throws Exception {
		log.info(this.getClass().getName() + ".getDoSaveData start!");
		
		String redisKey = "Test01";
		String saveData = "난 저장되는 데이터이다.";
		/*
		 * redis 저장 및 읽기에 대한 데이터 타입 지정 (String 타입으로 지정함 )
		 * */
		
		redisDB.setKeySerializer(new StringRedisSerializer());//String 타입
		redisDB.setValueSerializer(new StringRedisSerializer());//String 타입 
		/*
		 * 2 데이터가 존재하면 바로 변환 
		 * */
		if(redisDB.hasKey(redisKey)) {
			String res = (String)redisDB.opsForValue().get(redisKey);
			
			log.info("res : " + res);
		} else {
			redisDB.opsForValue().set(redisKey, saveData);
			
			// 2Days로 데이터가 유지되게 저장함 즉, 데이터 유지시간 지정
			redisDB.expire(redisKey, 2, TimeUnit.DAYS);
			
			log.info("No Data!");
		}
		log.info(this.getClass().getName() + ".getDoSaveData end!");
		
	}
	
	@Override
	public void doSaveDataforList() throws Exception {
		log.info(this.getClass().getName() + ".getDoSaveData start!");
		
		String redisKey = "Test02-List";

		/*
		 * redis 저장 및 읽기에 대한 데이터 타입 지정 (String 타입으로 지정함 )
		 * */
		
		redisDB.setKeySerializer(new StringRedisSerializer());//String 타입
		redisDB.setValueSerializer(new StringRedisSerializer());//String 타입 
		/*
		 * 2 데이터가 존재하면 바로 변환 
		 * */
		if(redisDB.hasKey(redisKey)) {
			//Redis에 저장된 데이터 전체 가져오기 
			// 데이터 인덱스는 0부터 시작하며, 세번째 인자값은 -1로 설정하면 모두 가져옴 
			List<String> pList = (List) redisDB.opsForList().range(redisKey, 0, -1);
			
			Iterator<String> it = pList.iterator();
			
			while (it.hasNext()) {
				String data = (String)it.next();
				
				log.info(" data : " + data);
			}
		} else {
			for(int i = 0; i < 10; i++) {
				redisDB.opsForList().leftPush(redisKey, "[" + i + "]번째 데이터 입니다. ");
			}
			
			log.info("No Data!");
		}
		log.info(this.getClass().getName() + ".getDoSaveData end!");
		
	}
	
	//@Override
//	public void doSaveDataforListJSON() throws Exception {
//		log.info(this.getClass().getName() + ".getDoSaveData start!");
//		
//		String redisKey = "Test02-List-JSON";
///
	//	/*
	///	 * redis 저장 및 읽기에 대한 데이터 타입 지정 (String 타입으로 지정함 )
		// * */
//		
//		redisDB.setKeySerializer(new StringRedisSerializer());//String 타입
//		
//		//  DTO를 JSON구조로 변경
///		redisDB.setValueSerializer(new Jackson2JsonRedisSerializer<>(MyJsonDTO.class));
//		
//		MyJsonDTO pDTO = null;
//		/*
//		 * 2 데이터가 존재하면 바로 변환 
//		 * */
//		if(redisDB.hasKey(redisKey)) {
//			//Redis에 저장된 데이터 전체 가져오기 
//			// 데이터 인덱스는 0부터 시작하며, 세번째 인자값은 -1로 설정하면 모두 가져옴 
//			List<String> pList = (List) redisDB.opsForList().range(redisKey, 0, -1);
//			
//			Iterator<String> it = pList.iterator();
///			
	//		while (it.hasNext()) {
	///			
		//		MyJsonDTO rDTO = (MyJsonDTO)it.next();
		//		
//				if(rDTO == null) {
//					
//					rDTO = new MyJsonDTO();
//				}
//				log.info(" name : " + CmmUtil.nvl(rDTO.getName()));
//				log.info(" email : " + CmmUtil.nvl(rDTO.getEmail()));
//				log.info(" addr : " + CmmUtil.nvl(rDTO.getAddr()));
//			}
//		} else {
//			pDTO = new MyJsonDTO();
//			
//			pDTO.setName("함지민");
//			pDTO.setEmail("함지민@gamil.com");
//			pDTO.setAddr("서울시 강서구 ");
//			
//			redisDB.opsForList().rightPush(redisKey, pDTO);
//			
//			pDTO = null ;
//			
//			pDTO = new MyJsonDTO();
//			
//			pDTO.setName("이협건");
//			pDTO.setEmail("이협건@gmail");
//			pDTO.setAddr("서울시 강서구");
///			
	//		redisDB.opsForList().rightPush(redisKey, pDTO);
	//		
	//		pDTO = null;
	//		
	//		//저장되는 데이터의 유효기간(TTL)은 100분으로 정의
	//		redisDB.expire(redisKey,100,TimeUnit.MINUTES);
	//		
	//		
	//		log.info("Save Data!");
	//	}
	//	log.info(this.getClass().getName() + ".getDoSaveData end!");
		
	//}

	
}
