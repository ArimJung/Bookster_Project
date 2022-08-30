package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import set.BoardSet;
import set.ReplySet;
import util.JDBCUtil;
import vo.BoardVO;
import vo.NovelVO;
import vo.ReplyVO;
import vo.Reply_reVO;

public class BoardDAO {
	Connection conn;
	PreparedStatement pstmt;
	final String sql_selectAll_BT="SELECT * FROM BOARD LEFT OUTER JOIN MEMBER ON BOARD.MID=MEMBER.MID WHERE BTITLE LIKE '%'||?||'%' ORDER BY BID DESC";
	final String sql_selectAll_BC="SELECT * FROM BOARD LEFT OUTER JOIN MEMBER ON BOARD.MID=MEMBER.MID WHERE BCONTENT LIKE '%'||?||'%' ORDER BY BID DESC";
	final String sql_selectAll_MID="SELECT * FROM BOARD LEFT OUTER JOIN MEMBER ON BOARD.MID=MEMBER.MID WHERE MID LIKE '%'||?||'%' ORDER BY BID DESC";
	// 게시글 검색
	final String sql_selectAll_BoardAll="SELECT * FROM BOARD LEFT OUTER JOIN MEMBER ON BOARD.MID=MEMBER.MID ORDER BY BID DESC";
	// 게시글 전체 뽑는 것
	//========================================================================================================================================================
	
//	final String sql_selectOne_BL="SELECT * FROM BOARD LEFT OUTER JOIN LLIKE ON BOARD.BID=LLIKE.BID WHERE BOARD.BID=?";
	// 게시글 추천수를 뽑을 것
//	final String sql_selectOne="SELECT B.BTITLE, B.MID, COUNT(L.REPORT) AS CNT FROM BOARD B, LLIKE L WHERE B.MID=L.MID(+) GROUP BY B.MID, B.BTITLE ORDER BY CNT DESC"; 
//	// 관리자가 사용할 selectOne 신고"
//	final String sql_selectOne_="SELECT B.BTITLE,B .MID, COUNT(L.LSTATUS) AS CNT FROM BOARD B, LLIKE L WHERE B.MID=L.MID(+) GROUP BY B.BTITLE, B.MID ORDER BY CNT DESC";
//	// 관리자가 사용할 selectOne 추천	
	//========================================================================================================================================================
	
	final String sql_selectOne_BoardOne="SELECT * FROM BOARD LEFT OUTER JOIN MEMBER ON BOARD.MID=MEMBER.MID WHERE BID=?";
	// 게시글 하나만 뽑는 것
	//========================================================================================================================================================
	
	
	final String sql_selectAll_BoardAll_ADMIN="SELECT * FROM BOARD LEFT OUTER JOIN MEMBER ON BOARD.MID=MEMBER.MID ORDER BY BID DESC WHERE MEMBER.MID=?";
	// 게시글 전체 뽑는 것 = 내가 쓴 게시글 
	final String sql_selectOne_ReplyOne="SELECT * FROM REPLY LEFT OUTER JOIN MEMBER ON REPLY.MID=MEMBER.MID WHRER RID=?";
	// 댓글 하나만 뽑는 것
	final String sql_selectAll_ReplyAll="SELECT * FROM REPLY LEFT OUTER JOIN MEMBER ON REPLY.MID=MEMBER.MID ORDER BY RID DESC WHERE REPLY.MID=?";
	// 댓글 전체 뽑는 것 = 내가 쓴 댓글 전체
	final String sql_selectAll_ReplyAll_Board="SELECT * FROM REPLY LEFT OUTER JOIN BOARD ON REPLY.BID=BOARD.BID ORDER BY RID DESC";
	// 해당 게시글의 댓글만 뽑는 것 = 게시글에 맞는 댓글
	final String sql_selectAll_Reply_re="SELECT * FROM REPLY_RE LEFT OUTER JOIN REPLY ON REPLY_RE.RID=REPLY.RID ORDER BY RRID DESC";
	// 대댓글 전체 출력 = 해당 댓글과 맞는 대댓글

	final String sql_insert_B="INSERT INTO BOARD VALUES((SELECT NVL(MAX(BID),1000)+1 FROM BOARD),?,?,TO_DATE(sysdate,'yyyy.mm.dd hh24:mi'),?)";
	final String sql_update_B="UPDATE BOARD SET TITLE=?,CONTENT=? WHERE BID=?";
	final String sql_delete_B="DELETE FROM BOARD WHERE BID=?";






	public BoardSet sql_selectOne_BoardOne(BoardVO bvo) {
		conn=JDBCUtil.connect();
		BoardSet bs = new BoardSet();
		System.out.println("시작로그");
		try {
			pstmt=conn.prepareStatement(sql_selectOne_BoardOne);
			ResultSet rs=pstmt.executeQuery();
			System.out.println("시작한다?!");
			while(rs.next()) {
				BoardVO boardVO = new BoardVO();
				boardVO.setBid(rs.getInt("BID"));
				boardVO.setBcontent(rs.getString("BCONTENT"));
				boardVO.setBtitle(rs.getString("BTITLT"));
				boardVO.setBdate(rs.getString("BDATE"));
				if(rs.getString("NICKNAME")==null) {
					boardVO.setMid("[이름없음]");
				} else {
					// WRITER대신 MNAME을 담아서 WRITER를 뽑으면 MNAME이 출력된다.
					boardVO.setMid(rs.getString("NICKNAME"));
				}
				bs.setBoardVO(boardVO);

				ArrayList<ReplySet> replySet = new ArrayList<ReplySet>();
				System.out.println("댓글 all로그");
				pstmt=conn.prepareStatement(sql_selectAll_ReplyAll);
				pstmt.setString(1, bvo.getMid());
				ResultSet rs2 =pstmt.executeQuery();
				System.out.println("댓글 all 로그 2");
				while(rs2.next()) {
					ReplyVO rvo = new ReplyVO();
					ReplySet rSet = new ReplySet();
					rvo.setRid(rs2.getInt("RID"));
					rvo.setBid(rs2.getInt("BID"));
					rvo.setRcontent(rs2.getString("RCONTENT"));
					rvo.setRdate(rs2.getString("RDATE"));
					if(rs.getString("NICKNAME")==null) {
						rvo.setMid("[이름없음]");
					} else {
						// WRITER대신 MNAME을 담아서 WRITER를 뽑으면 MNAME이 출력된다.
						rvo.setMid(rs.getString("NICKNAME"));
					}
					rSet.setReplyVO(rvo);


					ArrayList<Reply_reVO> rrList=new ArrayList<Reply_reVO>();
					pstmt=conn.prepareStatement(sql_selectAll_Reply_re);
					ResultSet rs3 = pstmt.executeQuery();
					while(rs3.next()) {
						Reply_reVO rrvo = new Reply_reVO();
						rrvo.setBid(rs3.getInt("BID"));
						rrvo.setRid(rs3.getInt("RID"));
						rrvo.setRrcontent(rs3.getString("RRCONTENT"));
						rrvo.setRrdate(rs3.getString("RRDATE"));
						rrvo.setRrid(rs3.getInt("RRID"));
						if(rs.getString("NICKNAME")==null) {
							rrvo.setMid("[이름없음]");
						} else {
							// WRITER대신 MNAME을 담아서 WRITER를 뽑으면 MNAME이 출력된다.
							rrvo.setMid(rs.getString("NICKNAME"));
						}
						rrList.add(rrvo);		
					}
					rSet.setrrList(rrList);
					replySet.add(rSet);
					bs.setReplySet(replySet);

				}

			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return bs;
	}


	public ArrayList<BoardSet> sql_selectAll_BoardAll(BoardVO bvo){
		ArrayList<BoardSet> datas = new ArrayList<BoardSet>();
		conn=JDBCUtil.connect();
		System.out.println("시작로그");
		if(bvo.getSearchCondition() == null) {
			bvo.setSearchCondition("");
		}
		try {
			if(bvo.getSearchCondition().equals("Bcontent")) {
				pstmt=conn.prepareStatement(sql_selectAll_BC);
				pstmt.setString(1, bvo.getSearchContent());
			}
			else if(bvo.getSearchCondition().equals("Btitlt")) {
				pstmt=conn.prepareStatement(sql_selectAll_BT);
				pstmt.setString(1, bvo.getSearchContent());
			}
			else if(bvo.getSearchCondition().equals("Mid")) {
				pstmt=conn.prepareStatement(sql_selectAll_MID);
				pstmt.setString(1, bvo.getSearchContent());
			}
			else {
				pstmt=conn.prepareStatement(sql_selectAll_BoardAll);
			}
			ResultSet rs=pstmt.executeQuery();
			System.out.println("시작한다?!");
			while(rs.next()) {
				BoardSet bs = new BoardSet();
				BoardVO boardVO = new BoardVO();
				boardVO.setBid(rs.getInt("BID"));
				boardVO.setBcontent(rs.getString("BCONTENT"));
				boardVO.setBtitle(rs.getString("BTITLT"));
				boardVO.setBdate(rs.getString("BDATE"));
				if(rs.getString("NICKNAME")==null) {
					boardVO.setMid("[이름없음]");
				} else {
					// WRITER대신 MNAME을 담아서 WRITER를 뽑으면 MNAME이 출력된다.
					boardVO.setMid(rs.getString("NICKNAME"));
				}
				bs.setBoardVO(boardVO);

				ArrayList<ReplySet> replySet = new ArrayList<ReplySet>();
				System.out.println("댓글 all로그");
				pstmt=conn.prepareStatement(sql_selectAll_ReplyAll);
				pstmt.setString(1, bvo.getMid());
				ResultSet rs2 =pstmt.executeQuery();
				System.out.println("댓글 all 로그 2");
				while(rs2.next()) {
					ReplyVO rvo = new ReplyVO();
					ReplySet rSet = new ReplySet();
					rvo.setRid(rs2.getInt("RID"));
					rvo.setBid(rs2.getInt("BID"));
					rvo.setRcontent(rs2.getString("RCONTENT"));
					rvo.setRdate(rs2.getString("RDATE"));
					if(rs.getString("NICKNAME")==null) {
						rvo.setMid("[이름없음]");
					} else {
						// WRITER대신 MNAME을 담아서 WRITER를 뽑으면 MNAME이 출력된다.
						rvo.setMid(rs.getString("NICKNAME"));
					}
					rSet.setReplyVO(rvo);


					ArrayList<Reply_reVO> rrList=new ArrayList<Reply_reVO>();
					pstmt=conn.prepareStatement(sql_selectAll_Reply_re);
					ResultSet rs3 = pstmt.executeQuery();
					while(rs3.next()) {
						Reply_reVO rrvo = new Reply_reVO();
						rrvo.setBid(rs3.getInt("BID"));
						rrvo.setRid(rs3.getInt("RID"));
						rrvo.setRrcontent(rs3.getString("RRCONTENT"));
						rrvo.setRrdate(rs3.getString("RRDATE"));
						rrvo.setRrid(rs3.getInt("RRID"));
						if(rs.getString("NICKNAME")==null) {
							rrvo.setMid("[이름없음]");
						} else {
							// WRITER대신 MNAME을 담아서 WRITER를 뽑으면 MNAME이 출력된다.
							rrvo.setMid(rs.getString("NICKNAME"));
						}
						rrList.add(rrvo);		
					}
					rSet.setrrList(rrList);
					replySet.add(rSet);
					bs.setReplySet(replySet);

				}
				datas.add(bs);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return datas;
	}


	public ArrayList<BoardVO> sql_selectAll_BoardSearch(BoardVO bvo){
		ArrayList<BoardVO> datas=new ArrayList<BoardVO>();
		conn=JDBCUtil.connect();
		try {
			if(bvo.getSearchCondition().equals("Bcontent")) {
				pstmt=conn.prepareStatement(sql_selectAll_BC);
				pstmt.setString(1, bvo.getSearchContent());
			}
			else if(bvo.getSearchCondition().equals("Btitlt")) {
				pstmt=conn.prepareStatement(sql_selectAll_BT);
				pstmt.setString(1, bvo.getSearchContent());
			}
			else if(bvo.getSearchCondition().equals("Mid")) {
				pstmt=conn.prepareStatement(sql_selectAll_MID);
				pstmt.setString(1, bvo.getSearchContent());
			}
			ResultSet rs=pstmt.executeQuery();
			while(rs.next()) {
				BoardVO data=new BoardVO();
				data.setBid(rs.getInt("BID"));
				data.setMid(rs.getString("MID"));
				data.setBcontent(rs.getString("BCONTENT"));
				data.setBtitle(rs.getString("BTITLT"));
				data.setBdate(rs.getString("BDATE"));
				datas.add(data);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCUtil.disconnect(pstmt, conn);
		}      
		return datas;
	}





	public boolean insert_B(BoardVO bvo) {
		conn=JDBCUtil.connect();
		try {
			pstmt=conn.prepareStatement(sql_insert_B);
			pstmt.setString(1, bvo.getBtitle());
			pstmt.setString(2, bvo.getBcontent());
			pstmt.setString(3, bvo.getMid());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			JDBCUtil.disconnect(pstmt, conn);
		}
		return true;
	}
	public boolean update_B(BoardVO bvo) {
		conn=JDBCUtil.connect();
		try {
			pstmt=conn.prepareStatement(sql_update_B);
			pstmt.setString(1, bvo.getBtitle());
			pstmt.setString(2, bvo.getBcontent());
			pstmt.setInt(3,bvo.getBid());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			JDBCUtil.disconnect(pstmt, conn);
		}
		return true;
	}
	public boolean delete_B(BoardVO bvo) {
		conn=JDBCUtil.connect();
		try {
			pstmt=conn.prepareStatement(sql_delete_B);
			pstmt.setInt(1,bvo.getBid());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			JDBCUtil.disconnect(pstmt, conn);
		}
		return true;
	}

}
