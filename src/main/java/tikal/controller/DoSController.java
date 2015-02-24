package tikal.controller;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import tikal.dos.Attack;
import tikal.dos.BasicHttpAttack;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
public class DoSController {
	private static final Logger logger = Logger.getLogger(DoSController.class);

	Set<Attack> ongoingAttacks = new HashSet<>();

	ExecutorService execService = Executors.newFixedThreadPool(20);
	
	@RequestMapping(value="/attack/{targetIP}/{threads}",method=RequestMethod.POST)
	public Object start(@PathVariable final int threads, @PathVariable final String targetIP){
        logger.info(threads);
        logger.info(targetIP);
		
		try {
			for (int i = 0; i < threads; i++) {
				Attack attack = new BasicHttpAttack();
				attack.setUrl("http://"+targetIP+":9000/checkin");
				//ongoingAttacks.add(attack);
				execService.execute(attack);
			}
		} catch (Exception e) {
            logger.error(e.getMessage(),e);
			return false;
		}
		
		return true;
	}
	
	@RequestMapping(value="/attack",method=RequestMethod.DELETE)
	public Object stop(){
		for (Attack attack : ongoingAttacks) {
			attack.stop();
		}
		return true;
	}
}
