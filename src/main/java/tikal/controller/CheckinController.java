package tikal.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import tikal.FuseDayStarterApplication;
import tikal.model.Checkin;


@RestController
public class CheckinController {

    private FuseDayStarterApplication app;

    public CheckinController() {
        app = new FuseDayStarterApplication();
    }

    private static final Logger logger = Logger.getLogger(CheckinController.class);
	@RequestMapping(value="/checkin",method=RequestMethod.POST)
	public Object checkin(@RequestBody final Checkin checkin) {
        //logger.info("received :"+checkin);
		checkin.setTimestamp(System.currentTimeMillis());
        String message = "{\"userId\":\""+checkin.getUserId()+"\",\"latitude\":"+checkin.getLatitude()+",\"longitude\":"+checkin.getLongitude()+"}";

        // rabbitmq
        app.sendMessage(message);
		return true;
	}
}
