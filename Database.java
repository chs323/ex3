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

       //�������
       Connection con;
       PreparedStatement psmt;

       //������
       public Database() {
              try {
                     Class.forName("oracle.jdbc.OracleDriver");
                     con = DriverManager.getConnection("jdbc:oracle:thin://@127.0.0.1:1521:xe", "indigo", "indigo");
                     System.out.println("����Ŭ DB ���Ἲ��");
              } catch (ClassNotFoundException e) {
                     System.out.println("����Ŭ ����̹� �ε� ����");
                     e.printStackTrace();
              } catch (SQLException e) {
                     System.out.println("DB ���� ����");
                     e.printStackTrace();
              } catch (Exception e) {
                     System.out.println("�˼� ���� ���ܹ߻�");
              }
       }

       //���� �����ۼ� �� ������ ���� �޼ҵ�
       public void insertCustomer(HashMap<String, DimCustomerDTO> dtoMap) {

              String query = "INSERT INTO Dim_Customer VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
              try {
                     //prepare��ü ����
                     psmt = con.prepareStatement(query);
                     int i = 0;
                     Iterator<String> keys = dtoMap.keySet().iterator();
                     while (keys.hasNext()) {
                            String key = keys.next();
                            //���Ķ���� ����
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

                            //addBatch�� ���
                            psmt.addBatch();
                            psmt.clearParameters();

                            //�޸����� - ������ 10000������ �μ�Ʈ�� Ŀ��
                            if ((i % 10000) == 0) {
                                   psmt.executeBatch();
                                   psmt.clearBatch();
                                   con.commit();
                            }
                            i++;
                     }

                     // Ŀ�Ե��� ���� ������ ������ ���Ͽ� Ŀ��
                     psmt.executeBatch();
                     con.commit();

              } catch (SQLIntegrityConstraintViolationException e) {
                     //System.out.println("�ߺ��� ������ �߻�");
              } catch (SQLException e) {
                     System.out.println("�������࿡ ������ �߻��Ͽ����ϴ�.");
                     e.printStackTrace();
              } finally {
                     System.out.println("cutomer insert ��");
                     //�ڿ��ݳ�
                     close();
              }
       }

       //���� �����ۼ� �� ������ ���� �޼ҵ�
       public void insertAccount(ArrayList<DimAccountDTO> dtoList) {
              String query = "INSERT INTO Dim_Account (AccountID, CustomerId ,AccountDesc , TaxStatus , BatchID) VALUES (?,?,?,?,?)";
              try {
                     //prepare��ü ����
                     psmt = con.prepareStatement(query);
                     for (int i = 0; i < dtoList.size(); i++) {

                            //���Ķ���� ����
                            psmt.setInt(1, dtoList.get(i).getAccountID());
                            psmt.setInt(2, dtoList.get(i).getCustomerId());
                            psmt.setString(3, dtoList.get(i).getAccountDesc());
                            psmt.setInt(4, dtoList.get(i).getTaxStatus());
                            psmt.setInt(5, dtoList.get(i).getBatchID());

                            psmt.addBatch();//Batch�� ���
                            psmt.clearParameters();

                            if ((i % 10000) == 0) {
                                   psmt.executeBatch();//insert
                                   psmt.clearBatch();//Batch �ʱ�ȭ
                                   con.commit();//Ŀ��
                            }

                     }

                     // Ŀ�Ե��� ���� ������ ������ ���Ͽ� Ŀ��
                     psmt.executeBatch();
                     con.commit();

              } catch (SQLException e) {
                     System.out.println("�������࿡ ������ �߻��Ͽ����ϴ�.");
                     e.printStackTrace();
              } finally {
                     System.out.println("account insert ��");
                     //�ڿ��ݳ�
                     close();
              }
       }

       private void close() {
              try {
                     if (psmt != null)
                            psmt.close();//PreparedStatement��ü �ڿ��ݳ�
                     if (con != null)
                            con.close();//Connection��ü �ڿ��ݳ�
                     System.out.println("DB�ڿ��ݳ��Ϸ�");
              } catch (SQLException e) {
                     System.out.println("�ڿ��ݳ��� ������ �߻��Ͽ����ϴ�.");
              }
       }
}
