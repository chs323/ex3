package xmltodb;

import java.util.Date;

public class DimAccountDTO {
       private int accountID;
       private int CustomerId;
       private String AccountDesc;
       private int TaxStatus;
       private int BatchID;
       private Date EndData;

       public DimAccountDTO() {
       }

       public DimAccountDTO(int accountID, int customerId, String accountDesc, int taxStatus, int batchID, Date endData) {
              super();
              this.accountID = accountID;
              CustomerId = customerId;
              AccountDesc = accountDesc;
              TaxStatus = taxStatus;
              BatchID = batchID;
              EndData = endData;
       }

       public int getAccountID() {
              return accountID;
       }

       public void setAccountID(int accountID) {
              this.accountID = accountID;
       }

       public int getCustomerId() {
              return CustomerId;
       }

       public void setCustomerId(int customerId) {
              CustomerId = customerId;
       }

       public String getAccountDesc() {
              return AccountDesc;
       }

       public void setAccountDesc(String accountDesc) {
              AccountDesc = accountDesc;
       }

       public int getTaxStatus() {
              return TaxStatus;
       }

       public void setTaxStatus(int taxStatus) {
              TaxStatus = taxStatus;
       }

       public int getBatchID() {
              return BatchID;
       }

       public void setBatchID(int batchID) {
              BatchID = batchID;
       }

       public Date getEndData() {
              return EndData;
       }

       public void setEndData(Date endData) {
              EndData = endData;
       }
       
       
       
}
