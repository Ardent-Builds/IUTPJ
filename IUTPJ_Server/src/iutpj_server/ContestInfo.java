/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iutpj_server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author HEROK
 */
public class ContestInfo implements Serializable {
    private String contestID;
    private String contestSetter;
    private String contestName;
    private Date startTime;
    private String durationMinutes;
    private final List<String> problems;
    
    public ContestInfo(){
        problems = new ArrayList<>();
        contestName = null;
        startTime = null;
        durationMinutes = null;
        contestID = null;
        contestSetter = null;
    }
    public void setContestID(String contestID)
    {
        this.contestID = contestID;
    }
    public void setContestSetter(String contestSetter)
    {
        this.contestSetter = contestSetter;
    }
    public void setContestName(String contestName){
        this.contestName = contestName;
    }
    public void setStartTime(Date startTime){
        this.startTime = startTime;
    }
    public void setDurationMinutes(String durationMinutes){
        this.durationMinutes = durationMinutes;
    }
    
    public void addProblem(String problemID)
    {
        this.problems.add(problemID);
    }
    
    public void removeProblem(String problemID)
    {
        this.problems.remove(problemID);
    }
    
    public String getContestID()
    {
        return this.contestID;
    }
    public String getContestSetter()
    {
        return this.contestSetter;
    }
    public String getContestName(){
        return this.contestName;
    }
    public String getdurationMinutes(){
        return this.durationMinutes;
    }
    public Date getStartTime()
    {
        return this.startTime;
    }
    
    public List<String> getProblemIDs()
    {
        return this.problems;
    }
    
    
}
