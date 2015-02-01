

import java.util.*;
import java.sql.*;
import java.util.Date;


public class NmpspLogin
{

    private int   	id;
    private String   	device_mac;
    private Date   	challenger_times_date;


    public NmpspLogin() {}


    public int   	getId   	() { return id; }
    public String   	getDevice_mac   	() { return device_mac; }
    public Date   	getChallenger_times_date   	() { return challenger_times_date; }


    public void 	setId   	(int id) { this.id = id; }
    public void 	setDevice_mac   	(String device_mac) { this.device_mac = device_mac; }
    public void 	setChallenger_times_date   	(Date challenger_times_date) { this.challenger_times_date = challenger_times_date; }

}
