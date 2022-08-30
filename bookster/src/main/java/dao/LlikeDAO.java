package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import util.JDBCUtil;
import vo.LlikeVO;

public class LlikeDAO {
   Connection conn;
   PreparedStatement pstmt;
   final String sql_insert_L="INSERT INTO LLIKE VALUES((SELECT NVL(MAX(LID),0)+1 FROM LLIKE),?,?,?,?)";
   final String sql_updateLU="UPDATE LLIKE SET LSTATUS=LSTATUS+1 WHERE BID=?";
   final String sql_updateLD="UPDATE LLIKE SET LSTATUS=LSTATUS-1 WHERE BID=?";
   final String sql_updateNLU="UPDATE LLIKE SET NLSTATUS=NLSTATUS+1 WHERE BID=?";
   final String sql_updateNLD="UPDATE LLIKE SET NLSTATUS=NLSTATUS-1 WHERE BID=?";
   final String sql_updateREPU="UPDATE LLIKE SET REPORT=REPORT+1 WHERE BID=?";
   final String sql_updateREPD="UPDATE LLIKE SET REPORT=REPORT-1 WHERE BID=?";
   
   final String sql_selectAll_REPORT="SELECT A.BID, A.CNT, B.MID FROM (SELECT L.BID, COUNT(L.REPORT) AS CNT FROM BOARD B JOIN LLIKE L "
   									+ "ON B.BID=L.BID WHERE L.REPORT=1 GROUP BY L.BID ORDER BY CNT DESC) A JOIN BOARD B ON A.BID=B.BID";
   
   final String sql_selectAll_Lstatus="SELECT A.BID, A.CNT, B.MID FROM (SELECT L.BID, COUNT(L.LSTATUS) AS CNT FROM BOARD B JOIN LLIKE L "
   									+ "ON B.BID=L.BID WHERE L.LSTATUS=1 GROUP BY L.BID ORDER BY CNT DESC) A JOIN BOARD B ON A.BID=B.BID";
   
   public boolean insert_L(LlikeVO lvo) {
      conn=JDBCUtil.connect();
      try {
         pstmt=conn.prepareStatement(sql_insert_L);
         pstmt.setString(1, lvo.getMid());
         pstmt.setInt(2, lvo.getRid());
         pstmt.setInt(3, lvo.getBid());
         pstmt.setInt(4, lvo.getOid());
         pstmt.executeUpdate();
      } catch (SQLException e) {
         e.printStackTrace();
         return false;
      } finally {
         JDBCUtil.disconnect(pstmt, conn);
      }
      return true;
   }
   public boolean update_L(LlikeVO bvo) {
      conn=JDBCUtil.connect();
      try {
         if(bvo.getLstatus()==1) {
            pstmt=conn.prepareStatement(sql_updateLU);
            pstmt.setInt(1, bvo.getBid());
            pstmt.executeUpdate();
         }
         else if(bvo.getLstatus()==-1) {
            pstmt=conn.prepareStatement(sql_updateLD);
            pstmt.setInt(1, bvo.getBid());
            pstmt.executeUpdate();
         }
         else if(bvo.getNlstatus()==1) {
            pstmt=conn.prepareStatement(sql_updateNLU);
            pstmt.setInt(1, bvo.getBid());
            pstmt.executeUpdate();
         }
         else if(bvo.getNlstatus()==-1) {
            pstmt=conn.prepareStatement(sql_updateNLD);
            pstmt.setInt(1, bvo.getBid());
            pstmt.executeUpdate();
         }
         else if(bvo.getReport()==1) {
            pstmt=conn.prepareStatement(sql_updateREPU);
            pstmt.setInt(1, bvo.getBid());
            pstmt.executeUpdate();
         }
         else if(bvo.getReport()==-1) {
            pstmt=conn.prepareStatement(sql_updateREPD);
            pstmt.setInt(1, bvo.getBid());
            pstmt.executeUpdate();
         }
      } catch (SQLException e) {
         e.printStackTrace();
         return false;
      } finally {
         JDBCUtil.disconnect(pstmt, conn);
      }
      return true;
   }
}