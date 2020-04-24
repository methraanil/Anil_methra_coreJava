package mba;

import java.sql.*;

import java.util.HashMap;
import java.util.Map.Entry;

import java.util.Scanner;

public class AccountDetails_Main {


	public static void main(String[] args) throws Exception {
		var accountDetails=new HashMap<Long,AccountDetails>();
			Scanner sc=new Scanner(System.in);
			int actnum;
		

//_______________________________________				J D B C 		CODE		________________________________________________________________

//++  CODE FOR CONNECTING AND OPERATIONS FOR DATABASE ++
			
			
		String url="jdbc:mysql://127.0.0.1:3306/mockbank";
		String uname="root";
		String pass="root";
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con=DriverManager.getConnection(url,uname,pass);
		
		String query="insert into customer_details values(?,?,?,?,?)";
		PreparedStatement ps=con.prepareStatement(query);
		
		String utquery="update customer_details set Account_type=? where Account_number=?";
		String unquery="update customer_details set customer_name=? where Account_number=?";
		String ubquery="update customer_details set Balance=? where Account_number=?";
		String ucquery="update customer_details set Currency=? where Account_number=?";
		
		PreparedStatement pst=con.prepareStatement(utquery);
		PreparedStatement psn=con.prepareStatement(unquery);
		PreparedStatement psb=con.prepareStatement(ubquery);
		PreparedStatement psc=con.prepareStatement(ucquery);
		
		
		
		String dtquery="delete from customer_details where Account_number=?";
		PreparedStatement psd=con.prepareStatement(dtquery);
		
		
		Statement st=con.createStatement();
//______________________________________________________________________________________
			
			System.out.println("******MOCK BANK APPLICATION******\n\n");
			
		System.out.println("Please select the option:\nY: For creating a new Account\nE: Update the details for an Existing account\nV: View the Account details\nD: Delete an account\nN: Close this Screen");
		     char c;
		     c=sc.next().charAt(0);
		     while((c != 'N')&&(c!='n')) {
		    	
		    	 if((c=='Y')||(c=='y')) {		
		    	 AccountDetails account=new AccountDetails();		
		    	 
		    	 
		    	 
  //__________________________________________________________________________________
		    	 
				//1. Getting Input for type of Account
				System.out.println("Enter the type of account:\n1.Savings\n2.Current\n");
				int temp;
				temp=sc.nextInt();
				sc.nextLine();
				while(true) {
				if(temp==1) {
					
					account.setAccountType("Savings");
					ps.setString(1,"Savings");
					break;
				}
				else if(temp==2) {
					account.setAccountType("Current");
					ps.setString(1,"Current");
					break;
				}
				else {
					System.out.println("You have choosen an invalid option");
					System.out.println("_____________________________________________");
					System.out.println("Enter the type of account:\n1.Savings\n2.Current\n");
					temp=sc.nextInt();
				}
				}
				
				
				
				
	//__________________________________________________________________________________
		
				
				//2. Input for the Account holder name
				while(true) {
				
				System.out.println("Enter the Account Holder Full Name : ");
				String name;
				String pattern="^[ a-zA-Z]+$";
				name=sc.nextLine();
				if(((name.length())!=0)&&(name.matches(pattern))) {
				account.setAccountName(name);
				ps.setString(2,name);
				break;
				}
				else {
						System.out.println("*****ENTER VALID NAME*****\n");
				}
				}
				
				
				
				
   //__________________________________________________________________________________

				//3. Input for the Account Number
				while(true) {
					System.out.println("Enter 7 digit account number: ");
					String accnum=sc.nextLine();
					int n=Integer.parseInt(accnum);
					
					 String irquery="select * from customer_details where Account_number='"+n+"'";
				 		ResultSet rts=st.executeQuery(irquery);
				 if(rts.next()) {
				    		 System.out.println("Account number :"+n+" is already Exists, PLEASE TRY ANOTHER ACCOUNT NUMBER");
				    		 System.out.println("___________________________________________________________________________");
				    		 
				    	 }
				 else {
					if((accnum!=null)&&(accnum.length()==7)) {
						try {
							account.setAccountNumber(Long.parseLong(accnum));
							actnum=n;
							ps.setInt(3,n);
							break;
						}
						catch(NumberFormatException e) {
							System.out.println("*****PLEASE ENTER NUMERIC VALUES ONLY and 7 DIGITS*****\n");
						}
					}
			
					else {
						System.out.println("*****ENTER VALID 7 DIGITS ACCOUNT NUMBER*****\n");
						
					}
				
				}
				}
				
  //__________________________________________________________________________________
	
				
				//4. Input for the Amount
				while(true) {
				System.out.println("Enter the Amount : ");
				String amt=sc.nextLine();
				if(amt!=null) {
					try {
					account.setAccountBalance(Double.parseDouble(amt));
					double amt1=Double.parseDouble(amt);
					ps.setDouble(4,amt1);
					break;
				}
					catch(NumberFormatException e) {
						System.out.println("*****PLEASE ENTER NUMERIC VALUES ONLY*****\n");
					}
					
							}
				else {
					System.out.println("*****PLEASE ENTER A NUMERIC VALUE*****\n");
				}
				}
				
//__________________________________________________________________________________
	
				//5. Input for the currency
				while(true) {
				System.out.println("Enter the Currency : ");
				String cur=sc.nextLine();
				if(cur!=null) {
				account.setCurrency(cur);
				ps.setString(5,cur);
				break;
				}
				else {
					System.out.println("*****PLEASE ENTER A CURRENCY*****\n");
				}
				}
//__________________________________________________________________________________
				
				accountDetails.put(account.getAccountNumber(),account);
		    	 System.out.println("_____________________________________________");
		    	 System.out.println("    ACCOUNT ADDED SUCCESSFULLY  ");
			
		    	 
		    	  ps.execute();
		    	  
		    	  //Query for displaying entered details of the new customer
		    	  
		    	  String rtquery="select * from customer_details where Account_number='"+actnum+"'";
					ResultSet rst=st.executeQuery(rtquery);
					rst.next();
			 		DisplayDetails(rst);
				
				System.out.println("Please select the option:\nY: For creating a new Account\nE: Update the details for an Existing account\nV: View the Account details\nD: Delete an account\nN: Close this Screen");
				c=sc.next().charAt(0);
		    	 }
//__________________________________________________________________________________
		    	 

		    	 //Modifying the Account
		    	 
		    	 else if((c=='E')||(c=='e')) {
		    		 
				    	 long an;
				    	 System.out.println("Enter the Account Number");
				    	 an=sc.nextLong();
				    	 String san=String.valueOf(an);
				    	 int ian=Integer.parseInt(san);
				    	
				    	 String rquery="select * from customer_details where Account_number='"+ian+"'";
					 		ResultSet rts=st.executeQuery(rquery);
					    	 if(rts.next()) {
				 
				    		 AccountDetails details=convertToObject(rts);

				    		
				    		 
				    		 System.out.println("Select the field \n1: Account Type\n2: Full Name\n3: Amount \n4: Currency Type ");
				    		 int i;
				    		 i=sc.nextInt();
				    		 switch(i) {
				    		 case 1:{
				    			 if(details.getAccountType().equals("Savings")) {
				    				 details.setAccountType("Current");
				    				 System.out.println("*****Account type was updated from Savings to Current*****");
				    				 accountDetails.put(an,details);
								pst.setString(1,"Current");
								pst.setInt(2,ian);
								pst.executeUpdate();
				    				 DisplayDetails(details);
				    				 break;
				    			 }
				    			 else
				    				 {
				    				 details.setAccountType("Savings");
				    				 System.out.println("*****Account type was updated from Current to Savings*****");
				    				 accountDetails.put(an,details);
										pst.setString(1,"Savings");
										pst.setInt(2,ian);
										pst.executeUpdate();
				    				 DisplayDetails(details);
				    				 break;
				    			 }
				    			 
				    		 }
				    		 case 2:{
				    			sc.nextLine();
				    			 System.out.println("Enter the full name: ");
							String fn=sc.nextLine();
						
				    			 details.setAccountName(fn);
				    			 
				    			 accountDetails.put(an,details);
				    			 System.out.println("*****NAME UPDATED SUCCESSFULLY*****");
								psn.setString(1,fn);
								psn.setInt(2,ian);
								psn.executeUpdate();
			    				 DisplayDetails(details);
				    			 break;
				    			 
				    		 }
				    		 case 3:{
				    			 System.out.println("Enter the Amount: ");
							double at=sc.nextDouble();
				    			 details.setAccountBalance(at);
				    			 accountDetails.put(an,details);
				    			 System.out.println("*****AMOUNT UPDATED SUCCESSFULLY*****");
								psb.setDouble(1,at);
								psb.setInt(2,ian);
								psb.executeUpdate();
			    				 DisplayDetails(details);
				    			 break;
				    		 }
				    		 case 4:{
				    			 System.out.println("Enter Currency Type: ");
							String cr=sc.next();
				    			 details.setCurrency(cr);
				    			 System.out.println("*****CURRENCY UPDATED SUCCESSFULLY*****");
								psc.setString(1,cr);
								psc.setInt(2,ian);
								psc.executeUpdate();
			    				 DisplayDetails(details);
				    			 break;
				    		 }
				    		default:{
				    			System.out.println("Enter a valid option :");
					    		 System.out.println("Select the field \n1: Account Type\n2: Full Name\n3: Amount \n4: Currency Type ");
					    		 i=sc.nextInt();

				    		}
				    		 
				    		 }
				    		 System.out.println("Please select the option:\nY: For creating a new Account\nE: Update the details for an Existing account\nV: View the Account details\nD: Delete an account\nN: Close this Screen");
								c=sc.next().charAt(0);
				    	 }
				    	 else {
				    		 System.out.println("*****ACCOUNT NOT FOUND*****\n");
					    	 System.out.println("_____________________________________________");
					    	 System.out.println("Please select the option:\nY: For creating a new Account\nE: Update the details for an Existing account\nV: View the Account details\nD: Delete an account\nN: Close this Screen");
								c=sc.next().charAt(0);
				    	 }
				    	 
				    	 
				     }
//__________________________________________________________________________________
		    	 
		    //Viewing the account details and withdraw and deposit feature
    	 
		    	 else if((c=='V')||(c=='v')) {
		    		 long an;
		    	
		    		 System.out.println("Enter the Account Number");
			    	 an=sc.nextLong();
			    	 int ian=(int)an;
			    	 
			    	 
			    	 
			 		String rquery="select * from customer_details where Account_number='"+ian+"'";
			 		ResultSet rs=st.executeQuery(rquery);
			    	 if(rs.next()) {
			    		 
			    		 DisplayDetails(rs); 
			
			    		 
			    		 AccountDetails details=convertToObject(rs);
			  
			    		 System.out.println("Do you want to DEPOSIT or WITHDRAW:\n press Y: for Yes\n press any key : for No ");
			    		 char y;
			    		 y=sc.next().charAt(0);
			    		 if((y=='Y')||(y=='y')) {
			    			 while(true) {
			    			 System.out.println("Select your option 1: Deposit\t 2: Withdraw :");
			    			 int p=sc.nextInt();
			    			 if(p==1) {
			    				 System.out.println("Enter the amount to Deposit: ");
			    				 double amt=sc.nextDouble();
			    				 details.setAccountBalance((details.getAccountBalance())+amt);
			    				 accountDetails.put(an,details);
			    				 psb.setDouble(1,(details.getAccountBalance()));
									psb.setInt(2,ian);
									psb.executeUpdate();
			    				 
			    				 System.out.println("UPDATED BALANCE: "+((details.getAccountBalance())+"\n"));
			    				 break;
			    			 }
			    			 else if(p==2) {
			    				 System.out.println("Enter the amount to Withdraw: ");
			    				 while(true) {
			    				 double amt1=sc.nextDouble();
			    				 if((details.getAccountBalance()>=(amt1))){
			    					 details.setAccountBalance((details.getAccountBalance())-amt1);
			    					 accountDetails.put(an,details);
			    					 psb.setDouble(1,(details.getAccountBalance()));
										psb.setInt(2,ian);
										psb.executeUpdate();
			    					 System.out.println("UPDATED BALANCE: "+((details.getAccountBalance())+"\n"));
			    					 break;
			    				 }
			    				 else {
			    					 System.out.println("Please enter amount less than: "+details.getAccountBalance());
			    				 }
			    			 }
			    		break;
			    		 }
			    			 else {
			    				 System.out.println("*****PLEASE SELECT A VALID OPTION*****\n");
			    			 }
			    			 }
			    		 }
			    		 else {
			    			 System.out.println("__________________________________________");
			    		 }
			    			 
			    		 System.out.println("Please select the option:\nY: For creating a new Account\nE: Update the details for an Existing account\nV: View the Account details\nD: Delete an account\nN: Close this Screen");
							c=sc.next().charAt(0);
		    	 }
			    	 else {
			    		 System.out.println("*****ACCOUNT NOT FOUND*****\n");
				    	 System.out.println("_____________________________________________");
				    	 System.out.println("Please select the option:\nY: For creating a new Account\nE: Update the details for an Existing account\nV: View the Account details\nD: Delete an account\nN: Close this Screen");
							c=sc.next().charAt(0);
			    	 }
			    		 
			     }
//__________________________________________________________________________________
		    	 //Deleting the Existing Account
		    	 
			     else if((c=='D')||(c=='d')) {
			    	 long an;
			    	 while(true) {
		    		 System.out.println("Enter the Account Number");
		    		 
			    	 an=sc.nextLong();
			    	 int i=(int)an; 
				 		String drquery="select * from customer_details where Account_number='"+an+"'";
				 		ResultSet rs=st.executeQuery(drquery);
				    	 if(rs.next()) {
			    	 
			    	 if(accountDetails.containsKey(an)) {
			    		accountDetails.remove(an);
			    	 }
			    		psd.setInt(1,i);
			    		psd.executeUpdate();
			    		System.out.println("*****ACCOUNT DELETED SUCCESSFULLY*****");
			    		System.out.println("_____________________________________________");
			    	
			    		 break;
			    
				    	 }
			    	 else {
			    		 System.out.println("*****ACCOUNT NOT FOUND*****");
			    		 System.out.println("___________________________________");
			    		 break;
			    	 }
			     
			     
		    		 }
			    	 System.out.println("Please select the option:\nY: For creating a new Account\nE: Update the details for an Existing account\nV: View the Account details\nD: Delete an account\nN: Close this Screen");
						c=sc.next().charAt(0);
			     }
    //____________________________________________________________________________________
		    	 
		    	 else 
		    		 {
			    	 System.out.println("Invalid option choosen");
			    	 System.out.println("_____________________________________________");
			    	 System.out.println("Please select the option:\nY: For creating a new Account\nE: Update the details for an Existing account\nV: View the Account details\nD: Delete an account\nN: Close this Screen");
						c=sc.next().charAt(0);
			     }
//__________________________________________________________________________________
		    	 
		     }
		   
		     if((c=='N')||(c=='n')) {
		    	 	 System.out.println("\n********************************");
			    	 System.out.println("* THANKS FOR USING OUR SERVICE *");
			    	 System.out.println("********************************");
			    	 System.out.println("_____________________________________________");
			     }
			     
		    	
				
				sc.close();
				int flag=0;
				if(accountDetails.size() > 0) {
					for(Entry<Long, AccountDetails> acc:accountDetails.entrySet()) {
						if(flag==0) {
						   	 System.out.println("_____________________________________________");
						   	 System.out.println(" ACCOUNTS TRIGGERED ON THIS CURRENT EXECUTION             ");
						   	
							}
						DisplayDetails(acc.getValue());
						flag=1;
					}
				

		     }
			st.close();
			con.close();	
			}
//___________________________________________________________________________________________________
	
	public static void DisplayDetails(ResultSet acc) throws SQLException {
		
		
		 System.out.println("_____________________________________________");
			System.out.println("Account Type :\t\t"+acc.getString("Account_type"));
			System.out.println("Account Holder Name :\t"+acc.getString("customer_name"));
			System.out.println("Account Number :\t"+acc.getInt("Account_number"));
			System.out.println("Amount in Account :\t"+acc.getDouble("Balance"));
			System.out.println("Currency mode :\t\t"+acc.getString("Currency"));
	   	 System.out.println("_____________________________________________\n");

	}
	
	public static void DisplayDetails(AccountDetails acc) {
		
		
	   	 System.out.println("_____________________________________________");
			System.out.println("Account Type :\t\t"+acc.getAccountType());
			System.out.println("Account Holder Name :\t"+acc.getAccountName());
			System.out.println("Account Number :\t"+acc.getAccountNumber());
			System.out.println("Amount in Account :\t"+acc.getAccountBalance());
			System.out.println("Currency mode :\t\t"+acc.getCurrency());
	   	 System.out.println("_____________________________________________\n");

		}
	
	//Converts ResultSet object to AccountDetails Object
	public static AccountDetails convertToObject(ResultSet s) throws SQLException {
		AccountDetails details=new AccountDetails();
		details.setAccountType(s.getString("Account_type"));
		 details.setAccountName(s.getString("customer_name"));
		 details.setAccountNumber(s.getInt("Account_number"));
		 details.setAccountBalance(s.getDouble("Balance"));
		 details.setCurrency(s.getString("Currency"));
		return details;
	}
	
		
	
	

	}
