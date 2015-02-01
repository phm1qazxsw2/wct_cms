package com.wct.report;


import dbo.*;
import java.util.Date;
import java.text.*;
import java.sql.*;

public class VendorMgr extends dbo.Manager<Vendor>
{
    private static VendorMgr _instance = null;

    VendorMgr() {}

    public synchronized static VendorMgr getInstance()
    {
        if (_instance==null) {
            _instance = new VendorMgr();
        }
        return _instance;
    }

    public VendorMgr(int tran_id) throws Exception {
        super(tran_id);
    }

    protected String getTableName()
    {
        return "t_system_org";
    }

    protected Object makeBean()
    {
        return new Vendor();
    }

    protected String getIdentifier(Object obj)
    {
        Vendor o = (Vendor) obj;
        return null;
    }

    protected void fillBean(ResultSet rs, Object obj, Connection con)
        throws Exception
    {
        Vendor item = (Vendor) obj;
        try {
            String	id		 = rs.getString("id");
            item.setId(id);
            String	name		 = rs.getString("name");
            item.setName(name);
            String	orgType		 = rs.getString("orgType");
            item.setOrgType(orgType);
            String	treeCode		 = rs.getString("treeCode");
            item.setTreeCode(treeCode);
            String	parentId		 = rs.getString("parentId");
            item.setParentId(parentId);
            String	leaf		 = rs.getString("leaf");
            item.setLeaf(leaf);
            String	memo		 = rs.getString("memo");
            item.setMemo(memo);
            String	vid		 = rs.getString("vid");
            item.setVid(vid);
            String	vidPlain		 = rs.getString("vidPlain");
            item.setVidPlain(vidPlain);
        }
        catch (Exception e)
        {
            throw e;
        }
    }

    protected String getSaveString(Object obj)
    {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date d;
        Vendor item = (Vendor) obj;

        String ret = 
            "`id`='" + ServerTool.escapeString(item.getId()) + "'"
            + ",`name`='" + ServerTool.escapeString(item.getName()) + "'"
            + ",`orgType`='" + ServerTool.escapeString(item.getOrgType()) + "'"
            + ",`treeCode`='" + ServerTool.escapeString(item.getTreeCode()) + "'"
            + ",`parentId`='" + ServerTool.escapeString(item.getParentId()) + "'"
            + ",`leaf`='" + ServerTool.escapeString(item.getLeaf()) + "'"
            + ",`memo`='" + ServerTool.escapeString(item.getMemo()) + "'"
            + ",`vid`='" + ServerTool.escapeString(item.getVid()) + "'"
            + ",`vidPlain`='" + ServerTool.escapeString(item.getVidPlain()) + "'"

        ;
        return ret;
    }

    protected String getInsertString()
    {
         return  "`id`,`name`,`orgType`,`treeCode`,`parentId`,`leaf`,`memo`,`vid`,`vidPlain`";
    }

    protected String getCreateString(Object obj)
    {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date d;
        Vendor item = (Vendor) obj;

        String ret = 
            "'" + ServerTool.escapeString(item.getId()) + "'"
            + ",'" + ServerTool.escapeString(item.getName()) + "'"
            + ",'" + ServerTool.escapeString(item.getOrgType()) + "'"
            + ",'" + ServerTool.escapeString(item.getTreeCode()) + "'"
            + ",'" + ServerTool.escapeString(item.getParentId()) + "'"
            + ",'" + ServerTool.escapeString(item.getLeaf()) + "'"
            + ",'" + ServerTool.escapeString(item.getMemo()) + "'"
            + ",'" + ServerTool.escapeString(item.getVid()) + "'"
            + ",'" + ServerTool.escapeString(item.getVidPlain()) + "'"

        ;
        return ret;
    }
}
