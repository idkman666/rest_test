package com.example.rest_test.business.service;

import com.example.rest_test.business.domain.Actions_domain;
import com.example.rest_test.business.domain.Logs_domain;
import com.example.rest_test.business.domain.Prop_domain;
import com.example.rest_test.entity.Actions;
import com.example.rest_test.entity.Logs;
import com.example.rest_test.entity.Properties;
import com.example.rest_test.entity.Types;
import com.example.rest_test.repo.IActionsRepo;
import com.example.rest_test.repo.ILogsRepo;
import com.example.rest_test.repo.IPropRepo;
import org.apache.juli.logging.Log;
import org.aspectj.weaver.ast.Var;
import org.hibernate.id.GUIDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.Time;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class LogicService {

    private final IActionsRepo actionsRepo;
    private final ILogsRepo logsRepo;
    private final IPropRepo propRepo;

    @Autowired
    public LogicService(IActionsRepo actionsRepo, ILogsRepo logsRepo, IPropRepo propRepo) {
        this.actionsRepo = actionsRepo;
        this.logsRepo = logsRepo;
        this.propRepo = propRepo;
    }

    //get logs by id
    public Iterable<Logs_domain> getLogsById( String userId) {
        Iterable<Logs> logs = this.logsRepo.findAllByUserId(userId); //get logs without actions
        if(!logs.iterator().hasNext())
        {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "User id not valid. Or No logs with that user"
            );
        }
        List<Logs_domain>logsList = new ArrayList<>();
        for (Logs log: logs)
        {
            List<Actions_domain> adList = new ArrayList<>();
            Iterable<Actions>actions = this.actionsRepo.findBySessionId(log.getSessionId());
            Logs_domain logs_domain = new Logs_domain();
            logs_domain.setSessionId(log.getSessionId());
            logs_domain.setUserId(log.getUserId());
            for (Actions action :
                    actions) {
                Properties properties = this.propRepo.findByActId(action.getActId());
                Prop_domain prop_domain = new Prop_domain();
                fillPropertiesParms(properties, prop_domain);
                Actions_domain actions_domain = new Actions_domain();
                mapToActionDomain(action,actions_domain,prop_domain);
                adList.add(actions_domain);//actions
            }
            logs_domain.setActions(adList);
            logsList.add(logs_domain);
        }

        return finalExceptionCheck(logsList);
    }

    //get logs by type
    public Iterable<Logs_domain> getLogsByType(String type) {
        Iterable<Actions> actions = this.actionsRepo.findByType(type.toLowerCase());
        if(!actions.iterator().hasNext())
        {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Error getting result for the request type: "+ type.toString() + "!!"
            );
        }

        List<Logs_domain> logsList = new ArrayList<>();


        HashSet<String> sessionIdList = new HashSet<>();
        //get userid for all logs from actions
        //then get actions again, but this time, grab actions with all types
        for (Actions action : actions) {
            sessionIdList.add(action.getSessionId());
        }
        for (String sessionId: sessionIdList) {
            List<Actions_domain> adList = new ArrayList<>();
            //get actions list form each log using userid
            Iterable<Actions> act = this.actionsRepo.findBySessionId(sessionId);
            //getting log using userid
            Logs log = this.logsRepo.findBySessionId(sessionId);
            //mapping to logs_domain
            Logs_domain logs_domain = new Logs_domain();
            logs_domain.setSessionId(sessionId);
            logs_domain.setUserId(log.getUserId());
            for (Actions a : act) {
                Properties properties = this.propRepo.findByActId(a.getActId());
                Prop_domain prop_domain = new Prop_domain();
                fillPropertiesParms(properties, prop_domain);
                Actions_domain actions_domain = new Actions_domain();
                mapToActionDomain(a,actions_domain,prop_domain);
                adList.add(actions_domain);//actions
            }
            logs_domain.setActions(adList);
            logsList.add(logs_domain);
        }

        return finalExceptionCheck(logsList);
    }

    //get logs using time
    public Iterable<Logs_domain> getLogsByTime(String time) {
        Iterable<Actions> actions = this.actionsRepo.findByTime(time);
        List<Logs_domain> logsList = new ArrayList<>();
        //since every userid is unique, add all userid from actions to hasset to just an array of unique userid
        HashSet<String> userIdList = new HashSet<>();
        //get userid for all logs from actions
        //then get actions again, but this time, grab actions with all types
        for (Actions action : actions) {
            userIdList.add(action.getUserId());
        }

        for (String userId : userIdList) {
            List<Actions_domain> adList = new ArrayList<>();
            //get actions list form each log using userid
            Iterable<Actions> act = this.actionsRepo.findByUserId(userId);
            //getting log using userid
            Logs log = this.logsRepo.findByUserId(userId);
            //mapping to logs_domain
            Logs_domain logs_domain = new Logs_domain();
            logs_domain.setSessionId(log.getSessionId());
            logs_domain.setUserId(log.getUserId());
            for (Actions a : act) {
               //only one unique property is brought from database
                Properties properties = this.propRepo.findByActId(a.getActId());
                Prop_domain prop_domain = new Prop_domain();
                fillPropertiesParms(properties, prop_domain);
                Actions_domain actions_domain = new Actions_domain();
                mapToActionDomain(a,actions_domain,prop_domain);
                adList.add(actions_domain);//actions
            }
            logs_domain.setActions(adList);
            logsList.add(logs_domain);
        }

        return finalExceptionCheck(logsList);
    }

    //get logs by id and type
    public Iterable<Logs_domain> getLogsByIdandType(String userId, String type) {
        //from logs: one user id, but several session ids
        Iterable<Logs> logs = this.logsRepo.findAllByUserId(userId);
        if(!logs.iterator().hasNext())
        {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "User id not valid. Or No logs with that user"
            );
        }
        List<Logs_domain>logsList = new ArrayList<>();
        Iterable<Actions> actions = this.actionsRepo.findByType(type.toLowerCase());
        if(!actions.iterator().hasNext())
        {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Error getting result for the request type: "+ type.toString() + "!!"
            );
        }
        Iterable<Actions> actFromUserId = this.actionsRepo.findByUserId(userId);
        HashSet<String>sessionIdList = new HashSet<>();
        HashSet<String>sessionIdFiltered = new HashSet<>(); // session id filtered from type and userid
        for (Actions action :
                actions) {
            //filtered by type
            sessionIdList.add(action.getSessionId());
        }
        for(Actions actFromUser: actFromUserId)
        {
            if(sessionIdList.contains(actFromUser.getSessionId()))
            {
                //only add session id with type and user id filter parameters
                sessionIdFiltered.add(actFromUser.getSessionId());
            }
        }

        for (String sessionId : sessionIdFiltered) {
            List<Actions_domain> adList = new ArrayList<>();

            Logs_domain logs_domain = new Logs_domain();

            logs_domain.setSessionId(sessionId);
            logs_domain.setUserId(userId);

            Iterable<Actions> curAction = this.actionsRepo.findBySessionId(sessionId);
            for (Actions a : curAction) {
                Properties properties = this.propRepo.findByActId(a.getActId());
                Prop_domain prop_domain = new Prop_domain();
                fillPropertiesParms(properties, prop_domain);
                Actions_domain actions_domain = new Actions_domain();
                mapToActionDomain(a,actions_domain,prop_domain);
                adList.add(actions_domain);
            }
            logs_domain.setActions(adList);
            logsList.add(logs_domain);
        }
        return finalExceptionCheck(logsList);
    }

    //get logs by time range
    public Iterable<Logs_domain> getLogsByTypeTime(String timeFrom, String timeTo, String type) {
        //validation
        String timeFrm = "";
        String time_To = "";
        LocalDateTime fromTime;
        LocalDateTime toTime;
        try{
            timeFrm = customTimeParse(timeFrom);
            time_To = customTimeParse(timeTo);
            fromTime = LocalDateTime.parse(timeFrm);
            toTime = LocalDateTime.parse(time_To);
        }catch (Exception ex)
        {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Time should be in a format:yyyy-mm-ddTHH:MM:ssz. eg: '2018-10-18T21:37:28-07:00' "
            );
        }

        List<Logs_domain>logsList = new ArrayList<>();
        Iterable<Actions> actions = this.actionsRepo.findByType(type.toLowerCase());

        if(!actions.iterator().hasNext())
        {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,"filter by types yielded no result"
            );
        }

        HashSet<String> sessionIdList = new HashSet<>();
        //find actions with type
        //use sessionid to get all logs
        for (Actions a: actions) {
            LocalDateTime actTime = LocalDateTime.parse(customTimeParse(a.getTime()));
            if(actTime.isEqual(toTime) || actTime.isBefore(toTime))
            {
                if(actTime.isEqual(fromTime) || actTime.isAfter(fromTime))
                {
                    sessionIdList.add(a.getSessionId());
                }
            }
        }
        //loop through sessionid to get logs and actions
        for (String sessionId :sessionIdList) {
            Logs_domain logs_domain = new Logs_domain();
            logs_domain.setSessionId(sessionId);

            Logs log = this.logsRepo.findBySessionId(sessionId);
            Iterable<Actions>actionsIterable = this.actionsRepo.findBySessionId(sessionId); //gives actions with same session id
            List<Actions_domain> actList = new ArrayList<>();
            for (Actions action: actionsIterable) {
                logs_domain.setUserId(action.getUserId());
                Properties properties = this.propRepo.findByActId(action.getActId());
                Prop_domain prop_domain = new Prop_domain();
                Actions_domain actions_domain = new Actions_domain();
                fillPropertiesParms(properties, prop_domain);
                mapToActionDomain(action,actions_domain,prop_domain);
                actList.add(actions_domain);
            }
            logs_domain.setActions(actList);
            logsList.add(logs_domain);
        }
        return finalExceptionCheck(logsList);
    }

    //get logs by id and time
    public Iterable<Logs_domain> getLogsByIdTime(String userId, String timeFrom, String timeTo) {
        String timeFrm = "";
        String time_To = "";
        LocalDateTime fromTime;
        LocalDateTime toTime;
        try{
            timeFrm = customTimeParse(timeFrom);
            time_To = customTimeParse(timeTo);
            fromTime = LocalDateTime.parse(timeFrm);
            toTime = LocalDateTime.parse(time_To);
        }catch (Exception ex)
        {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Time should be in a format:yyyy-mm-ddTHH:MM:ssz. eg: '2018-10-18T21:37:28-07:00' "
            );
        }
        Iterable<Logs> logs = this.logsRepo.findAllByUserId(userId);//all logs have unique sessionId
        if(!logs.iterator().hasNext())
        {
            //exception handling
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "User id not valid"
            );
        }

        List<Logs_domain>logsList = new ArrayList<>();
        HashSet<String> sessionIdList = new HashSet<>();
        for (Logs log: logs) {
            sessionIdList.add(log.getSessionId());
        }
        //find actions and filter sessionIds
        for (String sessionId: sessionIdList) {
            Iterable<Actions>actionsIterable = this.actionsRepo.findBySessionId(sessionId); //gives actions with same session id
            List<Actions_domain> actList = new ArrayList<>();
            Logs_domain logs_domain = new Logs_domain();
            for (Actions action: actionsIterable) {

                logs_domain.setSessionId(sessionId);
                logs_domain.setUserId(action.getUserId());
                //check if action is with in time range
                LocalDateTime actTime = LocalDateTime.parse(customTimeParse(action.getTime()));
                if(actTime.isEqual(toTime) || actTime.isBefore(toTime))
                {
                    if(actTime.isEqual(fromTime) || actTime.isAfter(fromTime))
                    {
                        //action time is between searched time range
                       //sessionId is valid
                        Properties properties = this.propRepo.findByActId(action.getActId());
                        Prop_domain prop_domain = new Prop_domain();
                        Actions_domain actions_domain = new Actions_domain();
                        fillPropertiesParms(properties, prop_domain);
                        mapToActionDomain(action,actions_domain,prop_domain);
                        actList.add(actions_domain);
                    }else
                    {
                        //throw exception
                        throw new ResponseStatusException(
                                HttpStatus.NOT_FOUND, "No results found for given request!"
                        );
                    }
                }else
                {
                    //throw exception
                    throw new ResponseStatusException(
                            HttpStatus.NOT_FOUND, "No results found for given request!"
                    );
                }
            }
            logs_domain.setActions(actList);
            logsList.add(logs_domain);
        }
        return finalExceptionCheck(logsList);
    }

    //get logs by all time, type and user id
    public Iterable<Logs_domain> getLogsByITT(String userId, String timeFrom, String timeTo, String type) {
        //validation
        String timeFrm = "";
        String time_To = "";
        LocalDateTime fromTime;
        LocalDateTime toTime;
        try{
            timeFrm = customTimeParse(timeFrom);
            time_To = customTimeParse(timeTo);
            fromTime = LocalDateTime.parse(timeFrm);
            toTime = LocalDateTime.parse(time_To);
        }catch (Exception ex)
        {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Time should be in a format:yyyy-mm-ddTHH:MM:ssz. eg: '2018-10-18T21:37:28-07:00' "
            );
        }

        Iterable<Logs> logs = this.logsRepo.findAllByUserId(userId);
        if(!logs.iterator().hasNext())
        {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "UserId not valid"
            );
        }
        Logs_domain logs_domain = new Logs_domain();
        List<Logs_domain>logsList = new ArrayList<>();
        Iterable<Actions> actions = this.actionsRepo.findByType(type.toLowerCase());
        if(!actions.iterator().hasNext())
        {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,"filter by types yielded no result"
            );
        }
        HashSet<String> sessionIdList = new HashSet<>();
        //looping through actions gotten through type
        //add unique sessionid to sessionIdList
        for (Actions a: actions) {
            LocalDateTime actTime = LocalDateTime.parse(customTimeParse(a.getTime()));
            if(actTime.isEqual(toTime) || actTime.isBefore(toTime))
            {
                if(actTime.isEqual(fromTime) || actTime.isAfter(fromTime))
                {
                    sessionIdList.add(a.getSessionId());
                }
                else
                {
                    //throw exception
                    throw new ResponseStatusException(
                            HttpStatus.NOT_FOUND, "No results found for given request!"
                    );
                }
            }
            else
            {
                //throw exception
                throw new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "No results found for given request!"
                );
            }
        }

        for(Logs log: logs)
        {
            logs_domain.setUserId(log.getUserId());
            logs_domain.setSessionId(log.getSessionId());
            if(sessionIdList.contains(log.getSessionId()))
            {
                Iterable<Actions>actionsIterable = this.actionsRepo.findBySessionId(log.getSessionId()); //gives actions with same session id
                List<Actions_domain> actList = new ArrayList<>();
                for (Actions action: actionsIterable) {
                    Properties properties = this.propRepo.findByActId(action.getActId());
                    Prop_domain prop_domain = new Prop_domain();
                    Actions_domain actions_domain = new Actions_domain();
                    fillPropertiesParms(properties, prop_domain);
                    mapToActionDomain(action,actions_domain,prop_domain);
                    actList.add(actions_domain);
                }
                logs_domain.setActions(actList);
                logsList.add(logs_domain);
            }
        }

        return finalExceptionCheck(logsList);
    }

    //save to database
    public void saveToDatabase(String userId, Date time, Iterable<String> types, HashMap<String, String>params)
    {
        UUID sessionId = UUID.randomUUID(); //create random sessionid
        UUID actId = UUID.randomUUID();
        String ldt = time.toString();

        Logs logs = new Logs(sessionId.toString(),userId);
        for (String type:types
             ) {
            Actions action = new Actions(actId.toString(),sessionId.toString(),userId,ldt,type);
            actionsRepo.save(action);
            if(type.toLowerCase().equals("click"))
            {
                //location x and location Y
                Long locX = Long.parseLong(params.get("locationX"));
                Long locY = Long.parseLong(params.get("locationY"));

                Properties propt = new Properties(type.toLowerCase(),actId.toString(),locX,locY,null,null,null);
                propRepo.save(propt);
            }else if(type.toLowerCase().equals("view"))
            {
                //
                String viewId = params.get("viewedId");

                Properties propt = new Properties(type.toLowerCase(),actId.toString(),
                        Long.parseLong("0"),Long.parseLong("0"),viewId,null,null);
                propRepo.save(propt);
            }else
            {
                //navigate
                String pageFrm = params.get("pageFrom");
                String pageTo = params.get("pageTo");

                Properties propt = new Properties(type.toLowerCase(),actId.toString(),
                        Long.parseLong("0"),Long.parseLong("0"),null,pageFrm,pageTo);
                propRepo.save(propt);

            }

        }

        logsRepo.save(logs);
    }



    //parse time gotten from database: time without timezone
    private String customTimeParse(String time)
    {
        String parsedTime = "";
        if (time == null)
        {
            return  null;
        }
        //parsing from 2018-10-18T21:37:28-06:00 to 2018-10-18T21:37:28
        parsedTime = time.substring(0,time.length()-6);
        return  parsedTime;

    }

    public Iterable<Logs> getAllLogs()
    {
        return logsRepo.findAll();
    }

    //to map fields from Properties to prop_domain
    private  void fillPropertiesParms(Properties properties, Prop_domain prop_domain)
    {
        if (properties.getType().equals("click")) {
            prop_domain.setLocationX(properties.getLocationX());
            prop_domain.setLocationY(properties.getLocationY());
        } else if (properties.getType().equals("view")) {
            prop_domain.setviewedId(properties.getviewedId());
        } else if (properties.getType().equals("navigate")) {
            prop_domain.setPageFrom(properties.getPageFrom());
            prop_domain.setPageTo(properties.getPageTo());
        }
    }

    //map action to action_domain
    private  void mapToActionDomain(Actions actions, Actions_domain actions_domain, Prop_domain properties)
    {
        actions_domain.setType(actions.getType());
        actions_domain.setTime(actions.getTime());
        actions_domain.setProperties(properties);
    }

    //to check if the return object (Iterable<Logs_domain>) is null.
    //if null: throw an exception
    private Iterable<Logs_domain> finalExceptionCheck(Iterable<Logs_domain> logslist)
    {
        if(!logslist.iterator().hasNext())
        {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "No result found for the requested parameters"
            );
        }
        return logslist;
    }
}
