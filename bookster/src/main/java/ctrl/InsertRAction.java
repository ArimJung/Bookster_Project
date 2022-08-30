package ctrl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.ReplyDAO;
import vo.ReplyVO;



public class InsertRAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ActionForward forward = null;
		ReplyDAO dao = new ReplyDAO();
		ReplyVO vo = new ReplyVO();
		HttpSession session=request.getSession();
		
		vo.setRcontent(request.getParameter("content"));
		vo.setMid((String)session.getAttribute("mid"));
		vo.setLid(Integer.parseInt(request.getParameter("lid")));
		vo.setBid(Integer.parseInt(request.getParameter("bid")));	
		
		
		
		if(dao.insert_R(vo)) {
			forward = new ActionForward();
			forward.setPath("communityBoard.do");
			forward.setRedirect(false);
		}
		else {
			throw new Exception("insertR ����");
		}
		
		request.setAttribute("cnt", request.getParameter("cnt"));
		request.setAttribute("bid", request.getParameter("bid"));
		
		return forward;
	}

}

/*

		if(bDAO.insertR(rVO)){
			pageContext.forward("ctrlB.jsp?action=main");
		}

*/