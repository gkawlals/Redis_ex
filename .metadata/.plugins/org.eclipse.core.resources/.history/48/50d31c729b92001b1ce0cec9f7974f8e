package poly.service.impl;

import java.util.Iterator;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import poly.dto.MovieDto;
import poly.persistance.mapper.IMovieMapper;
import poly.service.IMovieService;
import poly.util.CmmUtil;
import poly.util.DateUtil;

@Service("MovieService")
public class MovieService implements IMovieService {
	
	@Resource(name="MovieMapper")
	private IMovieMapper movieMapper;
	
	private Logger log = Logger.getLogger(this.getClass());
	
	@Override
	public int getMovieInfoFromWEB() throws Exception {
		
		log.info(this.getClass().getName() + ". getMovieInfoWEB Start!");
		
		int res = 0;// 0보다 크면 성공 
		
		//데이터를 가져올 사이트
		String url = "http://www.cgv.co.kr/movies/";
		//JSOUP을 통해 들어가 웹크롤링할 HTML의 소스를 저장할 변수 
		Document doc = null;
		
		//사이트접속 = https 프로토콜은 보안상 안된다 
		doc = Jsoup.connect(url).get();
		//CGV 웹페이지의 전체 소스중 일부 태그를 선택하기 위해 사용한다 
		//<div class="div.sect-movie-chart"> 이 태그내에 있는 HTML 소스만 element에 저장된다 
		Elements element = doc.select("div.sect-movie-chart");
		
		
		// Iterarot을 사용하여 영화순위 정보를 가져온다 
		// 영화순위는 기본적으로 1개이상의 영화가 존재하기 때문에 태그의 반복이 존재할수 있다. 
		Iterator<Element> movie_rank = element.select("strong.rank").iterator();
		// 영화순위 
		Iterator<Element> movie_name = element.select("strong.title").iterator();
		//영화 이름 
		Iterator<Element> movie_reserve = element.select("strong.percent span").iterator();
		// 영화 예매율 
		Iterator<Element> open_day = element.select("span.txt-info").iterator();
		// 점수 
		Iterator<Element> score = element.select("strong.percent").iterator();
		// 개봉일 
		
		MovieDto pDTO = null; 
		
		//수집된 데이터 DB의 저장하기
		while (movie_rank.hasNext()) {
				
		pDTO = new MovieDto();// 수집된 영화정보를  DTO에 저장하기 위해 메모리에 올리기 
		
		//수집시간을 기본키로 사용한다 
		pDTO.setRank_ck_time(DateUtil.getDateTime("yyyyMMdd24hmmss"));
		
		//영화순위 (trim 함수 추가이유 : trim 함수는 글자의 앞뒤 공백삭제 역할을 수행하여,데이터 주입시, 홈페이지 개발자들을 앞뒤공백 집어넣을수 있어서 추가함 
		String rank = CmmUtil.nvl(movie_rank.next().text()).trim(); // NO.1이 들어간다 
		pDTO.setMovie_rank(rank.substring(3, rank.length()));
		
		pDTO.setMovie_nm(CmmUtil.nvl(movie_name.next().text()).trim());
		
		pDTO.setMovie_reserve(CmmUtil.nvl(movie_reserve.next().text()).trim());
		
		pDTO.setScore(CmmUtil.nvl(score.next().text()).trim());
		
		pDTO.setOpen_day(CmmUtil.nvl(open_day.next().text()).trim().substring(0,10));
		
		pDTO.setReg_id("admin");
		
		res += movieMapper.InsertMovieInfo(pDTO);
		
		log.info(pDTO.getMovie_rank());
		log.info(pDTO.getMovie_nm());
		log.info(pDTO.getMovie_reserve());
		log.info(pDTO.getOpen_day());
		log.info(pDTO.getScore());
		log.info(pDTO.getReg_id());
		}
		
		log.info(this.getClass().getName() + " .getMovieInfoFromWEB end");
		
		return res;
	}
	
}
