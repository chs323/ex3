package xmltodb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class Database {

       //멤버변수
       Connection con;
       PreparedStatement psmt;

       //생성자
       public Database() {
              try {
                     Class.forName("oracle.jdbc.OracleDriver");
                     con = DriverManager.getConnection("jdbc:oracle:thin://@127.0.0.1:1521:xe", "indigo", "indigo");
                     System.out.println("오라클 DB 연결성공");
              } catch (ClassNotFoundException e) {
                     System.out.println("오라클 드라이버 로딩 실패");
                     e.printStackTrace();
              } catch (SQLException e) {
                     System.out.println("DB 연결 실패");
                     e.printStackTrace();
              } catch (Exception e) {
                     System.out.println("알수 없는 예외발생");
              }
       }

       //실제 쿼리작성 및 실행을 위한 메소드
       public void insertCustomer(HashMap<String, DimCustomerDTO> dtoMap) {

              String query = "INSERT INTO Dim_Customer VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
              try {
                     //prepare객체 생성
                     psmt = con.prepareStatement(query);
                     int i = 0;
                     Iterator<String> keys = dtoMap.keySet().iterator();
                     while (keys.hasNext()) {
                            String key = keys.next();
                            //인파라미터 설정
                            psmt.setInt(1, dtoMap.get(key).getCustomerID());
                            psmt.setString(2, dtoMap.get(key).getTaxID());
                            psmt.setString(3, dtoMap.get(key).getLastName());
                            psmt.setString(4, dtoMap.get(key).getFirstName());
                            psmt.setString(5, dtoMap.get(key).getMiddleInitial());
                            psmt.setString(6, dtoMap.get(key).getGender());
                            psmt.setString(7, dtoMap.get(key).getTier());
                            psmt.setString(8, dtoMap.get(key).getDOB());
                            psmt.setString(9, dtoMap.get(key).getAddressLine1());
                            psmt.setString(10, dtoMap.get(key).getAddressLine2());
                            psmt.setString(11, dtoMap.get(key).getPostalCode());
                            psmt.setString(12, dtoMap.get(key).getCity());
                            psmt.setString(13, dtoMap.get(key).getStateProv());
                            psmt.setString(14, dtoMap.get(key).getPhone1());
                            psmt.setString(15, dtoMap.get(key).getPhone2());
                            psmt.setString(16, dtoMap.get(key).getPhone3());

                            //addBatch에 담기
                            psmt.addBatch();
                            psmt.clearParameters();

                            //메모리주의 - 데이터 10000단위로 인서트후 커밋
                            if ((i % 10000) == 0) {
                                   psmt.executeBatch();
                                   psmt.clearBatch();
                                   con.commit();
                            }
                            i++;
                     }

                     // 커밋되지 못한 나머지 구문에 대하여 커밋
                     psmt.executeBatch();
                     con.commit();

              } catch (SQLIntegrityConstraintViolationException e) {
                     //System.out.println("중복된 데이터 발생");
              } catch (SQLException e) {
                     System.out.println("쿼리실행에 문제가 발생하였습니다.");
                     e.printStackTrace();
              } finally {
                     System.out.println("cutomer insert 끝");
                     //자원반납
                     close();
              }
       }

       //실제 쿼리작성 및 실행을 위한 메소드
       public void insertAccount(ArrayList<DimAccountDTO> dtoList) {
              String query = "INSERT INTO Dim_Account (AccountID, CustomerId ,AccountDesc , TaxStatus , BatchID) VALUES (?,?,?,?,?)";
              try {
                     //prepare객체 생성
                     psmt = con.prepareStatement(query);
                     for (int i = 0; i < dtoList.size(); i++) {

                            //인파라미터 설정
                            psmt.setInt(1, dtoList.get(i).getAccountID());
                            psmt.setInt(2, dtoList.get(i).getCustomerId());
                            psmt.setString(3, dtoList.get(i).getAccountDesc());
                            psmt.setInt(4, dtoList.get(i).getTaxStatus());
                            psmt.setInt(5, dtoList.get(i).getBatchID());

                            psmt.addBatch();//Batch에 담기
                            psmt.clearParameters();

                            if ((i % 10000) == 0) {
                                   psmt.executeBatch();//insert
                                   psmt.clearBatch();//Batch 초기화
                                   con.commit();//커밋
                            }

                     }

                     // 커밋되지 못한 나머지 구문에 대하여 커밋
                     psmt.executeBatch();
                     con.commit();

              } catch (SQLException e) {
                     System.out.println("쿼리실행에 문제가 발생하였습니다.");
                     e.printStackTrace();
              } finally {
                     System.out.println("account insert 끝");
                     //자원반납
                     close();
              }
       }

       private void close() {
              try {
                     if (psmt != null)
                            psmt.close();//PreparedStatement객체 자원반납
                     if (con != null)
                            con.close();//Connection객체 자원반납
                     System.out.println("DB자원반납완료");
              } catch (SQLException e) {
                     System.out.println("자원반납시 오류가 발생하였습니다.");
              }
       }
}
