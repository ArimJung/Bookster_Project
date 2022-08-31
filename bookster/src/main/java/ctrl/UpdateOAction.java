package ctrl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.OpinionDAO;
import vo.OpinionVO;

public class UpdateOAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ActionForward forward = null;
		OpinionDAO dao = new OpinionDAO();
		OpinionVO vo = new OpinionVO();
		
		vo.setOcontent(request.getParameter("ocontent"));
		vo.setOid(Integer.parseInt(request.getParameter("oid")));
				
		request.setAttribute("nid", request.getParameter("nid"));
		
		if(dao.update_O(vo)) {
			forward = new ActionForward();
			forward.setPath("novelBoard.do");
			forward.setRedirect(false);
		}
		else {
			throw new Exception("updateO ����");
		}
					
		return forward;
	}
	
}

/*
		if(bDAO.insert(bVO)){
			response.sendRedirect("ctrlB.jsp?action=main");
		}
*/