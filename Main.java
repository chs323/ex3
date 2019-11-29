package xmltodb;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Main {

       static DimCustomerDTO customerDTO = new DimCustomerDTO();//하나의 Customer 데이터를 담을 DTO 
       static ArrayList<DimAccountDTO> accountDtoList = new ArrayList<DimAccountDTO>();//전체 Account 데이터를 담을 List
       static HashMap<String, DimCustomerDTO> customerMap = new HashMap<String,DimCustomerDTO>();//전체 Customer 데이터를 담을 Map 

       public static void main(String[] args) {

              long s = System.currentTimeMillis();

              //시작함수
              start();

              long e = System.currentTimeMillis();

              System.out.println("실행 시간 : " + (e - s) / 1000.0);
              System.out.println("사용한 메모리 : " + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory())/1024);
              
       }

       private static void start() {
              setXmlData("C:\\03과제\\99.과제첨부파일\\3.xml2db\\CustomerMgmt1.xml");
              setXmlData("C:\\03과제\\99.과제첨부파일\\3.xml2db\\CustomerMgmt2.xml");

              /*******DB insert*******/
              new Database().insertCustomer(customerMap);
              new Database().insertAccount(accountDtoList);
       }

       private static void setXmlData(String filePath) {
              File file;
              DocumentBuilderFactory docBuildFact;
              DocumentBuilder docBuild;
              Document mgmt1Doc;
              
              try {
                     file = new File(filePath);
                     docBuildFact = DocumentBuilderFactory.newInstance();
                     docBuild = docBuildFact.newDocumentBuilder();
                     mgmt1Doc = docBuild.parse(file);
                     mgmt1Doc.getDocumentElement().normalize();

                     Element root = mgmt1Doc.getDocumentElement();
                     NodeList tpcdActioniList = root.getElementsByTagName("TPCDI:Action");

                     //System.out.println(tpcdActioniList.getLength());//14572 - TPCDI:Action 개수
                     for (int i = 0; i < tpcdActioniList.getLength(); i++) {
                            Element tpcdiActionElement = (Element) tpcdActioniList.item(i);
                            //System.out.println(tpcdiElement.getAttribute("ActionType"));//각 ActionType (NEW...등)
                            
                            //ActionType이 NEW인 데이터만 찾기위한 if문
                            if (tpcdiActionElement.getAttribute("ActionType").equals("NEW")) {
                                   DimCustomerDTO dto = new DimCustomerDTO();//임시 DTO 
                                   
                                   //------------<Customer> - Customer의 속성값들 가져오기
                                   NodeList customerList = tpcdiActionElement.getElementsByTagName("Customer");
                                   Element customerElement = (Element) customerList.item(0);

                                   /**************** <Customer> 데이터 저장 start *****************/
                                   customerDTO.setCustomerID(Integer.parseInt(customerElement.getAttribute("C_ID")));
                                   customerDTO.setGender(customerElement.getAttribute("C_GNDR"));
                                   customerDTO.setTier(customerElement.getAttribute("C_TIER"));
                                   customerDTO.setDOB(customerElement.getAttribute("C_DOB"));
                                   //System.out.println(customerDTO.getCustomerID()+", "+customerDTO.getGender()+", "+customerDTO.getTier()+", "+customerDTO.getDOB());// 각 값들 잘나옴
                                   /*************** <Customer> 데이터 저장 end *****************/

                                   /*************** <각 엘리먼트> 데이터 저장  start *****************/
                                   setNameInfo(customerElement);//<Name> 데이터 저장
                                   setAddressInfo(customerElement); //<Address> 데이터 저장
                                   setContactInfo(customerElement);//<ContactInfo>-<Phone>데이터 저장
                                   setTaxInfo(customerElement); //<TaxInfo> 데이터 저장
                                   setAccountInfo(customerElement, Integer.parseInt(customerElement.getAttribute("C_ID"))); //<Account> 데이터 저장
                                   /*************** <각 엘리먼트> 데이터 저장  end *****************/
                                
                                   /*****임시 dto로 데이터 저장 start*****/
                                   //dto=customerDTO;
                                   dto.setAddressLine1(customerDTO.getAddressLine1());
                                   dto.setAddressLine2(customerDTO.getAddressLine2());
                                   dto.setCity(customerDTO.getCity());
                                   dto.setCustomerID(customerDTO.getCustomerID());
                                   dto.setDOB(customerDTO.getDOB());
                                   dto.setFirstName(customerDTO.getFirstName());
                                   dto.setGender(customerDTO.getGender());
                                   dto.setLastName(customerDTO.getLastName());
                                   dto.setMiddleInitial(customerDTO.getLastName());
                                   dto.setPhone1(customerDTO.getPhone1());
                                   dto.setPhone2(customerDTO.getPhone2());
                                   dto.setPhone3(customerDTO.getPhone3());
                                   dto.setPostalCode(customerDTO.getPostalCode());
                                   dto.setStateProv(customerDTO.getStateProv());
                                   dto.setTaxID(customerDTO.getTaxID());
                                   dto.setTier(customerDTO.getTier());
                                   /*****임시 dto로 데이터 저장 end*****/
                                   
                                   /*****임시 dto(customer데이터)를 Map에 저장*****/
                                   customerMap.put(customerDTO.getFirstName()+customerDTO.getLastName()+customerDTO.getLastName(), dto);
                            }
                     }
              } catch (NullPointerException e) {
                     e.printStackTrace();
              } catch (Exception e) {
                     e.printStackTrace();
              }
       }

       //<NAME>데이터 저장
       private static void setNameInfo(Element customerElement) {
              //              NodeList nameList = customerElement.getElementsByTagName("Name");
              //              Element nameElement = (Element) nameList.item(0);
              Node c_L_NameNode = getNode(customerElement, "C_L_NAME");
              Node c_F_NameNode = getNode(customerElement, "C_F_NAME");
              Node c_M_NameNode = getNode(customerElement, "C_M_NAME");

              /*************** <NAME> 데이터 저장 start *****************/
              customerDTO.setLastName(getValue(c_L_NameNode));
              customerDTO.setFirstName(getValue(c_F_NameNode));
              customerDTO.setMiddleInitial(getValue(c_M_NameNode));
              //System.out.println(customerDTO.getLastName()+", "+customerDTO.getFirstName()+", "+customerDTO.getMiddleInitial());//각 C_M_NAME 잘나옴
              /*************** <NAME> 데이터 저장 end *****************/
       }

       //<Address> 데이터 저장
       private static void setAddressInfo(Element customerElement) {

              Node c_ADLineNode1 = getNode(customerElement, "C_ADLINE1");
              Node c_ADLineNode2 = getNode(customerElement, "C_ADLINE2");
              Node c_ZipcodeNode = getNode(customerElement, "C_ZIPCODE");
              Node c_CityNode = getNode(customerElement, "C_CITY");
              Node c_State_ProvNode = getNode(customerElement, "C_STATE_PROV");

              /*************** <Address> 데이터 저장 start *****************/
              customerDTO.setAddressLine1(getValue(c_ADLineNode1));
              customerDTO.setAddressLine2(getValue(c_ADLineNode2));
              customerDTO.setPostalCode(getValue(c_ZipcodeNode));
              customerDTO.setCity(getValue(c_CityNode));
              customerDTO.setStateProv(getValue(c_State_ProvNode));
              //System.out.println(customerDTO.getAddressLine1() + ", " + customerDTO.getAddressLine2() + ", " + customerDTO.getPostalCode() + ", " + customerDTO.getCity() + ", " + customerDTO.getStateProv());
              /*************** <Address> 데이터 저장 end *****************/
       }

       //<ContactInfo> 데이터 저장
       private static void setContactInfo(Element customerElement) {

              NodeList c_PoneList1 = customerElement.getElementsByTagName("C_PHONE_1");
              Element c_PoneElement1 = (Element) c_PoneList1.item(0);
              Node c_CtryNode1 = getNode(c_PoneElement1, "C_CTRY_CODE");
              Node c_AreaNode1 = getNode(c_PoneElement1, "C_AREA_CODE");
              Node c_LocalNode1 = getNode(c_PoneElement1, "C_LOCAL");

              NodeList c_PoneList2 = customerElement.getElementsByTagName("C_PHONE_2");
              Element c_PoneElement2 = (Element) c_PoneList2.item(0);
              Node c_CtryNode2 = getNode(c_PoneElement2, "C_CTRY_CODE");
              Node c_AreaNode2 = getNode(c_PoneElement2, "C_AREA_CODE");
              Node c_LocalNode2 = getNode(c_PoneElement2, "C_LOCAL");

              NodeList c_PoneList3 = customerElement.getElementsByTagName("C_PHONE_3");
              Element c_PoneElement3 = (Element) c_PoneList3.item(0);
              Node c_CtryNode3 = getNode(c_PoneElement3, "C_CTRY_CODE");
              Node c_AreaNode3 = getNode(c_PoneElement3, "C_AREA_CODE");
              Node c_LocalNode3 = getNode(c_PoneElement3, "C_LOCAL");

              /*************** <ContactInfo>_<Phone> 데이터 저장 start *****************/
              customerDTO.setPhone1(getValue(c_CtryNode1) + getValue(c_AreaNode1) + getValue(c_LocalNode1));
              customerDTO.setPhone2(getValue(c_CtryNode2) + getValue(c_AreaNode2) + getValue(c_LocalNode2));
              customerDTO.setPhone3(getValue(c_CtryNode3) + getValue(c_AreaNode3) + getValue(c_LocalNode3));
              //System.out.println(customerDTO.getPhone1()+", "+customerDTO.getPhone2()+", "+customerDTO.getPhone3());
              /*************** <ContactInfo>_<Phone> 데이터 저장 end *****************/
       }

       //<TaxInfo> 데이터 저장
       private static void setTaxInfo(Element customerElement) {

              Node c_LclNode = getNode(customerElement, "C_LCL_TX_ID");
              Node c_NatNode = getNode(customerElement, "C_NAT_TX_ID");

              /*************** <Address> 데이터 저장 start *****************/
              customerDTO.setTaxID(getValue(c_LclNode) + getValue(c_NatNode));
              //System.out.println(customerDTO.getTaxID());
              /*************** <Address> 데이터 저장 end *****************/
       }

       //<Account> 데이터 저장
       private static void setAccountInfo(Element customerElement, int c_ID) {

              NodeList accountList = customerElement.getElementsByTagName("Account");

              for (int i = 0; i < accountList.getLength(); i++) {
                     DimAccountDTO accountDTO = new DimAccountDTO();//하나의 Account 데이터를 담을 DTO
                     
                     Element accountElement = (Element) accountList.item(i);
                     //System.out.println(accountElement.getAttribute("CA_ID"));//잘나옴
                     //System.out.println(accountElement.getAttribute("CA_TAX_ST"));
                     Node ca_NameNode = getNode(accountElement, "CA_NAME");
                     Node ca_B_IdNode = getNode(accountElement, "CA_B_ID");

                     /*************** <Account> 데이터 저장 start *****************/
                     accountDTO.setAccountID(Integer.parseInt(accountElement.getAttribute("CA_ID")));
                     accountDTO.setCustomerId(c_ID);
                     accountDTO.setAccountDesc(getValue(ca_NameNode));
                     accountDTO.setTaxStatus(Integer.parseInt(accountElement.getAttribute("CA_TAX_ST")));
                     accountDTO.setBatchID(Integer.parseInt(getValue(ca_B_IdNode)));
                     //System.out.println(accountDTO.getAccountID()+",  "+accountDTO.getCustomerId()+",  "+accountDTO.getAccountDesc()+",  "+accountDTO.getTaxStatus()+",  "+accountDTO.getBatchID());
                     /*************** <Account> 데이터 저장 end *****************/

                     //***ArrayList써서 accountDTO 저장***//
                     accountDtoList.add(accountDTO);
                     //System.out.println(accountDtoList.get(i).getAccountID()+",     "+accountDtoList.get(0).getCustomerId()+",     "+accountDtoList.get(0).getAccountDesc()+",     "+accountDtoList.get(0).getTaxStatus()+",     "+accountDtoList.get(0).getBatchID());
              }
       }

       //해당 엘리먼트의 노드 가져오는 함수
       private static Node getNode(Element element, String tagName) {
              NodeList nodeList = element.getElementsByTagName(tagName);
              Element element2 = (Element) nodeList.item(0);
              Node node = element2.getFirstChild();

              return node;
       }

       //Node의 value가 Null인경우 ""을 넣어주는 함수.
       private static String getValue(Node node) {
              String value = "";
              try {
                     value = node.getNodeValue();
              } catch (NullPointerException e) {
                     value = "";
              }
              return value;
       }
}







