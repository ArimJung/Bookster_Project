package ctrl;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.BoardDAO;
import vo.BoardVO;
import vo.MemberVO;

public class MyboardAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ArrayList<BoardVO> datas = new ArrayList<BoardVO>(); 
		ActionForward forward = null;
		BoardDAO dao = new BoardDAO();
		BoardVO vo = new BoardVO();
		
		HttpSession session=request.getSession();
		MemberVO mvo = (MemberVO)session.getAttribute("member");
		
		vo.setMid(mvo.getMid());
		
		datas = dao.selectAll_MEMBER(vo);
		
		request.setAttribute("datas", datas);
		
		forward = new ActionForward();
		forward.setPath("/myboard.jsp");
		forward.setRedirect(false);
					
		return forward;
	}

}
