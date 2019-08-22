package ua.RetroCars.web.Tag;

import java.util.List;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

/**
 *   Tag to count the number of pages for view
 *
 */

public class PageView extends BodyTagSupport {
	private static final long serialVersionUID = 6729371432157422092L;
	
	private List<Object> list;
	private List<Object> pageList;
	private Integer indexFirstPage;
	private Integer indexPreviousPage;
	private Integer indexPage;
	private Integer indexNextPage;
	private Integer indexLastPage;
	private Integer size = 10;

	public void setList(List<Object> list) {
		this.list = list;
	}

	public void setIndexPage(int indexPage) {
		this.indexPage = indexPage;
	}

	@Override
	public int doStartTag() throws JspException {
		if (list != null && list.size() != 0) {			
			int allCar=list.size();
			int count = (allCar-1) / size + 1;
			indexFirstPage = indexPage > 1 ? 1 : null;
			indexPreviousPage = indexPage > 2 ? indexPage - 1 : null;			
			indexNextPage = indexPage < count - 1 ? indexPage + 1: null;
			indexLastPage = indexPage < count ? count : null;
			int lastIndex= size * indexPage;
			if(lastIndex>allCar){
				lastIndex=allCar;
			}
			pageList = list.subList(size * (indexPage - 1),lastIndex);
			pageContext.getRequest().setAttribute("indexFirstPage",
					indexFirstPage);
			pageContext.getRequest().setAttribute("indexPreviousPage",
					indexPreviousPage);
			pageContext.getRequest().setAttribute("indexLastPage",
					indexLastPage);
			pageContext.getRequest().setAttribute("indexNextPage",
					indexNextPage);
			pageContext.getRequest().setAttribute("pageList", pageList);
		}		
		return SKIP_BODY;
	}
	
}
