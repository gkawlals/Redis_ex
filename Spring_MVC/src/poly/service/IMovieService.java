package poly.service;

import java.util.List;

import poly.dto.MovieDTO;

public interface IMovieService {

	int getMovieInfoFromWEB() throws Exception;

	List<MovieDTO> getMovieRank(MovieDTO pDTO);
	
}
