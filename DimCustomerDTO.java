package xmltodb;

import java.util.Date;

public class DimCustomerDTO {
       private int CustomerID;
       private String TaxID;
       private String LastName;
       private String FirstName;
       private String MiddleInitial;
       private String Gender;
       private String Tier;
       private String DOB;
       private String AddressLine1;
       private String AddressLine2;
       private String PostalCode;
       private String City;
       private String StateProv;
       private String Phone1;
       private String Phone2;
       private String Phone3;

       public DimCustomerDTO() {
       }

       public DimCustomerDTO(int customerID, String taxID, String lastName, String firstName, String middleInitial, String gender, String tier, String dOB, String addressLine1, String addressLine2, String postalCode, String city, String stateProv, String phone1, String phone2, String phone3) {
              super();
              CustomerID = customerID;
              TaxID = taxID;
              LastName = lastName;
              FirstName = firstName;
              MiddleInitial = middleInitial;
              Gender = gender;
              Tier = tier;
              DOB = dOB;
              AddressLine1 = addressLine1;
              AddressLine2 = addressLine2;
              PostalCode = postalCode;
              City = city;
              StateProv = stateProv;
              Phone1 = phone1;
              Phone2 = phone2;
              Phone3 = phone3;
       }

       public int getCustomerID() {
              return CustomerID;
       }

       public void setCustomerID(int customerID) {
              CustomerID = customerID;
       }

       public String getTaxID() {
              return TaxID;
       }

       public void setTaxID(String taxID) {
              TaxID = taxID;
       }

       public String getLastName() {
              return LastName;
       }

       public void setLastName(String lastName) {
              LastName = lastName;
       }

       public String getFirstName() {
              return FirstName;
       }

       public void setFirstName(String firstName) {
              FirstName = firstName;
       }

       public String getMiddleInitial() {
              return MiddleInitial;
       }

       public void setMiddleInitial(String middleInitial) {
              MiddleInitial = middleInitial;
       }

       public String getGender() {
              return Gender;
       }

       public void setGender(String gender) {
              Gender = gender;
       }

       public String getTier() {
              return Tier;
       }

       public void setTier(String tier) {
              Tier = tier;
       }

       public String getDOB() {
              return DOB;
       }

       public void setDOB(String dOB) {
              DOB = dOB;
       }

       public String getAddressLine1() {
              return AddressLine1;
       }

       public void setAddressLine1(String addressLine1) {
              AddressLine1 = addressLine1;
       }

       public String getAddressLine2() {
              return AddressLine2;
       }

       public void setAddressLine2(String addressLine2) {
              AddressLine2 = addressLine2;
       }

       public String getPostalCode() {
              return PostalCode;
       }

       public void setPostalCode(String postalCode) {
              PostalCode = postalCode;
       }

       public String getCity() {
              return City;
       }

       public void setCity(String city) {
              City = city;
       }

       public String getStateProv() {
              return StateProv;
       }

       public void setStateProv(String stateProv) {
              StateProv = stateProv;
       }

       public String getPhone1() {
              return Phone1;
       }

       public void setPhone1(String phone1) {
              Phone1 = phone1;
       }

       public String getPhone2() {
              return Phone2;
       }

       public void setPhone2(String phone2) {
              Phone2 = phone2;
       }

       public String getPhone3() {
              return Phone3;
       }

       public void setPhone3(String phone3) {
              Phone3 = phone3;
       }

}
