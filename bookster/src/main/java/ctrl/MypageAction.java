package ctrl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.MemberDAO;
import vo.MemberVO;

public class MypageAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		MemberVO vo = new MemberVO();
		MemberDAO dao = new MemberDAO();
		// 세션의 mid를 겟 어트리뷰트 해서 selectOne에 넣어야 하는거 아닌가?
		// selectOne은 mid, mpw가 필요함
		// 1. 마이페이지 이동할때 파라미터 mpw를 받을건지
		// 2. 현재 세션에는 mid,nickname,role이 있는데 mpw도 세션에 넣을건지? (보안문제 있을수있음)
		// 3. sql문을 마이페이지용 하나더 만들어서 mid만 들어가서 실행되게 할건지? 
		HttpSession session=request.getSession();
		vo.setMid((String)session.getAttribute("mid"));

		vo = dao.selectOne_MID(vo); // 마이페이지용 selectOne으로 수정해야함
		if(vo != null) {
			request.setAttribute("data", vo);
			forward=new ActionForward();
			forward.setPath("mypage.jsp");
			forward.setRedirect(false);			
		}
		else {
			throw new Exception("Mypage 오류");
		}
		
		return forward;
	}

}
