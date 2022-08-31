package ctrl;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.OpinionDAO;
import vo.MemberVO;
import vo.OpinionVO;

public class MyreviewAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ArrayList<OpinionVO> datas = new ArrayList<OpinionVO>(); 
		ActionForward forward = null;
		OpinionDAO dao = new OpinionDAO();
		OpinionVO vo = new OpinionVO();

		HttpSession session=request.getSession();
		MemberVO mvo = (MemberVO)session.getAttribute("member");

		vo.setMid(mvo.getMid());

		datas = dao.내가 쓴 리뷰 보기(vo);
		
		request.setAttribute("datas", datas);
		
		forward = new ActionForward();
		forward.setPath("/myreview.jsp");
		forward.setRedirect(false);


		return forward;
	}

}
