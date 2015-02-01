

import java.util.*;
import java.sql.*;
import java.util.Date;


public class BIDevice
{

    private int   	id;
    private String   	mac;
    private Date   	loginDate;
    private String   	ip;


    public BIDevice() {}


    public int   	getId   	() { return id; }
    public String   	getMac   	() { return mac; }
    public Date   	getLoginDate   	() { return loginDate; }
    public String   	getIp   	() { return ip; }


    public void 	setId   	(int id) { this.id = id; }
    public void 	setMac   	(String mac) { this.mac = mac; }
    public void 	setLoginDate   	(Date loginDate) { this.loginDate = loginDate; }
    public void 	setIp   	(String ip) { this.ip = ip; }

}
