package com.wct.report;


import java.util.*;
import java.sql.*;
import java.util.Date;


public class Vendor
{

    private String   	id;
    private String   	name;
    private String   	orgType;
    private String   	treeCode;
    private String   	parentId;
    private String   	leaf;
    private String   	memo;
    private String   	vid;
    private String   	vidPlain;


    public Vendor() {}


    public String   	getId   	() { return id; }
    public String   	getName   	() { return name; }
    public String   	getOrgType   	() { return orgType; }
    public String   	getTreeCode   	() { return treeCode; }
    public String   	getParentId   	() { return parentId; }
    public String   	getLeaf   	() { return leaf; }
    public String   	getMemo   	() { return memo; }
    public String   	getVid   	() { return vid; }
    public String   	getVidPlain   	() { return vidPlain; }


    public void 	setId   	(String id) { this.id = id; }
    public void 	setName   	(String name) { this.name = name; }
    public void 	setOrgType   	(String orgType) { this.orgType = orgType; }
    public void 	setTreeCode   	(String treeCode) { this.treeCode = treeCode; }
    public void 	setParentId   	(String parentId) { this.parentId = parentId; }
    public void 	setLeaf   	(String leaf) { this.leaf = leaf; }
    public void 	setMemo   	(String memo) { this.memo = memo; }
    public void 	setVid   	(String vid) { this.vid = vid; }
    public void 	setVidPlain   	(String vidPlain) { this.vidPlain = vidPlain; }

}
