package tikal.dos;

public interface Attack extends Runnable {

	public void setUrl(String url) throws Exception;
	
	public void stop();
}
