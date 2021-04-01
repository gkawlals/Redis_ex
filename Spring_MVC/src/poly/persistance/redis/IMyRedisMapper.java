package poly.persistance.redis;

public interface IMyRedisMapper {
	
	public void doSaveData() throws Exception;
	
	public void doSaveDataforList() throws Exception;
	
	public void doSaveDataforListJSON() throws Exception;

}
