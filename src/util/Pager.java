package util;

public class Pager
{
    private String styleClass;
    private int curPage = 0;
    private String prefix = null;
    private int pageCount = 0;
    
    public Pager(String style, int curPage, String prefix, int totalSize, int pageSize)
    {
    	this.styleClass = style;
    	this.curPage = curPage;
    	this.prefix = prefix;
    	this.pageCount = (totalSize/pageSize) + ((totalSize%pageSize)>0?1:0);
    }

    public String drawPagerYvent()
    	throws Exception
	{
    	StringBuffer sb = new StringBuffer();
    	sb.append("<font class=\"numSmall\">").append(curPage).append("</font>");
    	sb.append("<font class=\"numSplit\">/</font>");
    	sb.append("<font class=\"numBig\">").append(pageCount).append("</font>");
    	
    	if (curPage==1) {
    		sb.append("<a class=\"previousPage\" onclick=\"return false\">上一页</a>");
    	}
    	else {
    		sb.append("<a class=\"previousPage\" href=\"").
    		append(prefix).append("page").append("=").
        	append(curPage-1).append("\">上一页</a>");
    	}
    	
    	sb.append("<font class=\"pageSplit\">/</font>");
    	
    	if (curPage==pageCount) {
    		sb.append("<a class=\"nextPage\" onclick=\"return false\">下一页</a>");
    	}
    	else {
    		sb.append("<a class=\"nextPage\" href=\"").
    		append(prefix).append("page").append("=").
        	append(curPage+1).append("\">下一页</a>");
    	}
    	return sb.toString();
	}


//<div class="more-bar" style="padding:5px 0;">
//    <ul class="paginator" style="margin-right:10px;">
//       <li><a class="prev" href="#">上一页</a></li>
//       <li><a href="#">5</a></li>
//       <li class="current">6</li>
//       <li><a href="#">7</a></li>
//       <li><a class="next" href="#">下一页</a></li>
//    </ul>               
// </div>	
    
    public String drawPager2()
	    throws Exception
	{
    	if (this.pageCount<=1)
    		return "";
		StringBuffer sb = new StringBuffer();
	    try
	    {
	        sb.append("<div class=\"more-bar\" style=\"padding:5px 0;\">");
	        sb.append("<ul class=\"paginator\" style=\"margin-right:10px;\">");
	        if(1 == curPage)
	        {
	        	sb.append("<li><a class=\"prev\" onclick=\"return false\">上一页</a></li>");
	        } 
	        else {
	        	sb.append("<li><a class=\"prev\" href=\"").append(prefix).append("page").append("=").
	            	append(curPage - 1).append("\">上一页</a></li>");
	        }
	        if(this.pageCount > 6)
	        {
	            if(1 == curPage)
	            {
	            	sb.append("<li class=\"current\">1</li>");
	            } else
	            {
	            	sb.append("<li><a href=\"").append(prefix).append("page").append("=").
	            		append(1).append("\">").append(1).append("</a></li>");
	            }
	            if(5 < curPage)
	            {
	            	sb.append("<li>...</li>");
	            }
	            for(int j = curPage - 3; j <= curPage + 3; j++)
	            {
	                if(j > 1 && j < pageCount)
	                {
	                    if(j == curPage)
	                    {
	                    	sb.append("<li class=\"current\">").append(j).append("</li>");
	                    } else
	                    {
	                    	sb.append("<li><a href=\"").append(prefix).append("page").
	                    		append("=").append(j).append("\">").append(j).append("</a></li>");
	                    }
	                }
	            }
	
	            if(pageCount - 4 > curPage)
	            {
	            	sb.append("<li>...</li>");
	            }
	            if(pageCount == curPage)
	            {
	                sb.append("<li class=\"current\">").append(pageCount).append("</li>");
	            } else
	            {
	                sb.append("<li><a href=\"").append(prefix).append("page").append("=").
	                	append(pageCount).append("\">").append(pageCount).append("</a></li>");
	            }
	        } 
	        else {
	            for(int i = 1; i <= pageCount; i++)
	            {
	                if(i == curPage)
	                {
	                    sb.append("<li class=\"current\">").append(i).append("</li>");
	                } else
	                { 
	                    sb.append("<li><a href=\"").append(prefix).append("page").append("=").
	                    	append(i).append("\">").append(i).append("</a></li>");
	                }
	            }
	
	        }
	        if(pageCount > curPage)
	        {
	            sb.append("<li><a class=\"next\" href=\"").append(prefix).append("page=").append(curPage + 1).
	            	append("\">下一页</a></li>");
	        } 
	        else {
	            sb.append("<li><a class=\"next\" onclick=\"return false\">下一页</a></li>");
	        }
	        sb.append("</ul>");
	        sb.append("</div>");
	    }
	    catch(Exception e)
	    {
	        e.printStackTrace();
	    }
	    return sb.toString();
	}

    
    
    public String drawPager()
        throws Exception
    {
    	StringBuffer sb = new StringBuffer();
        try
        {
            sb.append("<div class=\"").append(styleClass).append("\">");
            sb.append("<ul class=\"paginator\" style=\"margin-right:10px;\">");
            if(1 == curPage)
            {
            	sb.append("<span class=\"disabled\"> &laquo;  前页</span>");
            } else
            {
            	sb.append("<a href=\"").append(prefix).append("page").append("=").
                	append(curPage - 1).append("\"> &laquo;  前页</a>");
            }
            if(this.pageCount > 6)
            {
                if(1 == curPage)
                {
                	sb.append("<span class=\"current\">1</span>");
                } else
                {
                	sb.append("<a href=\"").append(prefix).append("page").append("=").append(1).append("\">").append(1).append("</a>");
                }
                if(5 < curPage)
                {
                	sb.append("<span>...</span>");
                }
                for(int j = curPage - 3; j <= curPage + 3; j++)
                {
                    if(j > 1 && j < pageCount)
                    {
                        if(j == curPage)
                        {
                        	sb.append("<span class=\"current\">").append(j).append("</span>");
                        } else
                        {
                        	sb.append("<a href=\"").append(prefix).append("page").append("=").append(j).append("\">").append(j).append("</a>");
                        }
                    }
                }

                if(pageCount - 4 > curPage)
                {
                	sb.append("<span>...</span>");
                }
                if(pageCount == curPage)
                {
                    sb.append("<span class=\"current\">").append(pageCount).append("</span>");
                } else
                {
                    sb.append("<a href=\"").append(prefix).append("page").append("=").append(pageCount).append("\">").append(pageCount).append("</a>");
                }
            } else
            {
                for(int i = 1; i <= pageCount; i++)
                {
                    if(i == curPage)
                    {
                        sb.append("<span class=\"current\">").append(i).append("</span>");
                    } else
                    { 
                        sb.append("<a href=\"").append(prefix).append("page").append("=").append(i).append("\">").append(i).append("</a>");
                    }
                }

            }
            if(pageCount > curPage)
            {
                sb.append("<a href=\"").append(prefix).append("page=").append(curPage + 1).append("\">下页  &raquo; </a>");
            } else
            {
                sb.append("<span class=\"disabled\">下页  &raquo; </span>");
            }
            sb.append("</ul>");
            sb.append("</div>");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
