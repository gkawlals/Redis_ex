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

import poly.dto.MovieDTO;
import poly.persistance.redis.IRedisMovieMapper;

@Component("RedisMovieMapper")
public class RedisMovieMapper implements IRedisMovieMapper{
	
	@Autowired
	public RedisTemplate<String, Object> redisDB;
	
	private Logger log = Logger.getLogger(this.getClass());
	
	/*
	 * 영화순위 정보가 존재하는지 체크 
	 * */
	
	@Override
	public boolean getExists(String key) throws Exception {
		
		log.info(this.getClass().getName() + " .getExists Start!");
		
		
		
		log.info(this.getClass().getName() + " .getExists End!");
		
		return redisDB.hasKey(key);
	}
	
	@Override
	public List<MovieDTO> getMovieRank(String key) throws Exception {
		log.info(this.getClass().getName() + ".getMovieRank Start!");
		
		List<MovieDTO> rList = null; // redisDB로부터 조회된 데이터를 저장할 객체 
		
		redisDB.setKeySerializer(new StringRedisSerializer());
		redisDB.setValueSerializer(new Jackson2JsonRedisSerializer<>(MovieDTO.class));
		
		if(redisDB.hasKey(key)) {
			rList = (List)redisDB.opsForList().range(key, 0, -1);
		}
		
		log.info(this.getClass().getName() + ".getMovieRank End !");
		
		return rList;
		
	}
	

	@Override
	public int setMovieRank(String key, List<MovieDTO> pList) throws Exception {
		int res = 0; 
		log.info(this.getClass().getName() + ".setMovieRank Start!");
		
		redisDB.setKeySerializer(new StringRedisSerializer());
		redisDB.setValueSerializer(new Jackson2JsonRedisSerializer<>(MovieDTO.class));
		
		//기존순위정보가 존재하면, 삭제한다. 
		if(this.getExists(key)) {
			redisDB.delete(key);
		}
		
		Iterator<MovieDTO> it = pList.iterator();
		
		while(it.hasNext()) {
			MovieDTO pDTO = (MovieDTO) it.next();
			
			pDTO = null;
		}
		
		res = 1;
		
		log.info(this.getClass().getName() + ".setMovieRank End!");
		
		return res;
	}
	
	@Override
	public boolean setTimeOutHour(String roomKey,int hours) throws Exception{
		log.info(this.getClass().getName() + ".setTimeOutHour Start!");
		return redisDB.expire(roomKey, hours, TimeUnit.HOURS);
	}

}
