package poly.persistance.redis;

import java.util.List;

import poly.dto.MovieDTO;

public interface IRedisMovieMapper {

	int setMovieRank(String key, List<MovieDTO> pList) throws Exception;

	boolean setTimeOutHour(String roomKey, int hours) throws Exception;

	boolean getExists(String key) throws Exception;

	List<MovieDTO> getMovieRank(String key) throws Exception;

}
