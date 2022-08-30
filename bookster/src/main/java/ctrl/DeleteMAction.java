package ctrl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.MemberDAO;
import vo.MemberVO;



public class DeleteMAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session=request.getSession();

		ActionForward forward = null;
		MemberVO vo = new MemberVO();
		MemberDAO dao = new MemberDAO();

		vo.setMid((String)session.getAttribute("mid"));
		vo.setMpw(request.getParameter("mpw"));
		vo = dao.selectOne_M(vo);

		if(vo != null) {
				dao.delete_M(vo);
				session.invalidate();
				forward = new ActionForward();
				forward.setPath("main.do");
				forward.setRedirect(true);
			}
			else {
				throw new Exception("deleteM ����");
			}
		return forward;
	}
}

/*
		else if(action.equals("memberDelete")){
			MemberVO member=(MemberVO)session.getAttribute("member");
			if(member!=null && mDAO.delete(member)){
			//	bVO.setWriter(member.getMname());
			//	bDAO.delete_all(bVO);
				session.invalidate(); // ���� ���� ��ü �����ϱ�
				response.sendRedirect("login.jsp");
			}
			else {
				throw new Exception("memberdelete ����");
			}
		}	
 */