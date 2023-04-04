//bankDetails.txt account details
SBI
Jagannath Prasad
SBIN0012113
//accNumSeq.txt
1010
//BankAccount.java file
import static java.lang.System.out;

import  java.io.BufferedReader;
import  java.io.FileReader;
import  java.io.FileNotFoundException;
import  java.io.IOException;

public class BankAccount{
	private static String bankName;
	private static String branchName;
	private static String ifsc;

	private long accNum;
	private String accHName;
	private double balance;

	static{
		out.println("\nBankAccount class is loaded");
		try{Thread.sleep(1000);}
		catch(InterruptedException e){}

		out.println("SV memory allocated with default values");
		try{Thread.sleep(1000);}
		catch(InterruptedException e){}

		out.println("Reading static variables values from file and initializing them");
		try{Thread.sleep(1000);}
		catch(InterruptedException e){}//

		try{
			/*connecting to file*/
			BufferedReader fileReader=
				 new BufferedReader(new FileReader("bankdetails.txt"));

			/*Reading  values file and storing in static variables*/
			bankName   =fileReader.readLine();
			branchName =fileReader.readLine();
			ifsc       =fileReader.readLine();

        out.println("SV are initialized with file values \n");
		try{Thread.sleep(1000);}
		catch(InterruptedException e){}

		}catch(FileNotFoundException e){
			out.println("Error: bankDetails.txt file is not found");
		}catch(IOException e){
			e.printStackTrace();
		}

		try{Thread.sleep(1000);}
		catch(InterruptedException e){}

	}//static block end

	public BankAccount(long accNum,String accHName,double balance){
		out.println("NSVs memory allocated with default values");
		try{Thread.sleep(1000);}
		catch(InterruptedException e){}

		out.println("NSVs are being initialized with given values\n");
		try{Thread.sleep(1000);}
		catch(InterruptedException e){}

        this.accNum   =accNum;
		this.accHName =accHName;
		this.balance  =balance;

		out.println("NSVs are initialized with given object values\n");
		try{Thread.sleep(1000);}
		catch(InterruptedException e){}


	}//constructor closed

	public static void setBankName(String bankName){
	   BankAccount.bankName=bankName;
	}

	public static String getBankName(){
		return bankName;
	}

	public static void setBranchName(String branchName){
		BankAccount.branchName=branchName;
	}

	public static String getBranchName(){
		return branchName;
	}

	public static void setIfsc(String ifsc){
		BankAccount.ifsc=ifsc;
	}
	public static String getIfsc(){
		return ifsc;
	}

	public long getAccNum(){
		return accNum;
	}

	public void setAccHName(String accHName){
		this.accHName=accHName;
	}

	public String getAccHName(){
		return accHName;
	}

	public double getBalance(){
		return balance;
	}

	public void deposite(double amt){
		this.balance=this.balance+amt;
	}

	public void withdraw(double amt){
		this.balance=this.balance-amt;
	}

	public void currentBalance(){
		out.println(balance);
	}

	public String toString(){
		return ("\n bankName\t:"+bankName)+"\n"+
			   (" branchName\t:"+branchName)+"\n"+
			   (" ifsc\t\t:"+ifsc) +"\n"+
			   (" accNum\t\t:"+accNum) +"\n"+
			   (" accHName\t:"+accHName)+"\n"+
			   (" balance\t:"+balance);
	}

}
//Bank.java
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicLong;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;


public class Bank{
	private BankAccount[] accounts;
	private int           size;

    private Scanner scanner;
	private AtomicLong accNumGenerator;

	public Bank(){
		accounts      =new BankAccount[10];
		size          =0;
		scanner       =new Scanner(System.in);

		try{
			
			BufferedReader accNumReader = new BufferedReader(new FileReader("accNumSeq.txt"));
			long accNumSeq  =Long.parseLong(accNumReader.readLine());
			accNumGenerator =new AtomicLong(accNumSeq);

		}catch(FileNotFoundException e){
			System.out.println("accNumSeq.txt is not found");
		}catch(IOException e){
			e.printStackTrace();
		}

	}//Bank()close

	private void pause(){
		try{Thread.sleep(2000);}
		catch(InterruptedException e){}
	}

	public void openAccount(){

		long accNum=accNumGenerator.incrementAndGet();

		System.out.print("Enter Account Holder Name:");
		String accHName=scanner.nextLine();

		System.out.print("Enter Balance:");
		double balance=scanner.nextDouble();scanner.nextLine();

		System.out.println("BankAccount object creation started...");
		pause();

		BankAccount account = new BankAccount(accNum,accHName,balance);
		System.out.println("BankAccount object is created");
		pause();

		accounts[size++]=account;
		System.out.println("BankAccount object is stored in Bank");
		pause();

		//saving account number in file
        try{
			FileWriter fw=new FileWriter("accNumSeq.txt");
			fw.write(""+accNum);
			fw.flush();
        }catch(FileNotFoundException e){
			System.out.println("accNumSeq.txt file is not found");
        }catch(IOException e){
			e.printStackTrace();
        }
	}//openAccount() method close


	private BankAccount getAccount(long accNum)throws IllegalArgumentException{
		if(accNum<=0)
			throw new IllegalArgumentException("Account number can not be VE number and ZERO");

		System.out.println("Searching for given accountNumber object");
		pause();

		//Linear search algorithm
		for(int i=0;i<size;i++){//loop for finding BankAccount object for the given accnumber
			BankAccount account=accounts[i];

			if(account.getAccNum()==accNum)
				return account;
		}

		return null;
	}

	private boolean amountZeroOrNegative(double amt) {
		return amt<=0?true:false;
	}

	private boolean amountGreaterThanBalance(BankAccount account,double amt){
		return amt>account.getBalance()?true:false;
	}

	public void deposite(long accNum,double amt) throws IllegalArgumentException{
		System.out.println("deposite operation start");
		pause();

		//retrieving the BankAccount object of the given account number
		BankAccount account=getAccount(accNum);

		//checking account and balance valid or not
		if(account ==null)
			throw new IllegalArgumentException("Account is not found with the given details");

		if(amountZeroOrNegative(amt))
			throw new IllegalArgumentException("Amount can not be -VE number ZERO");

		//depositing given amount in the given account
		account.deposite(amt);
		System.out.println("Cash RS "+amt+" is Credited to your account");

		pause();

	}//deposite close

	public void withdraw(long accNum,double amt)throws IllegalArgumentException{
		
		System.out.println("withdraw operation start");
		pause();

		//retrieving the BankAccount object of the given account number
		BankAccount account =getAccount(accNum);

		//checking account and balance valid or not
		if(account == null)
			throw new IllegalArgumentException("Account is not found with the given details:");

		if(amountZeroOrNegative(amt))
			throw new IllegalArgumentException("Ammount can not be -VE number ZERO");

		if(amountGreaterThanBalance(account,amt))
			throw new IllegalArgumentException("Insuffcient Funds");

		account.withdraw(amt);
		System.out.println("Cash Rs "+amt+" is debited from your account");

		pause();

	}//withdraw close

	public void balanceEnquiry(long accNum)throws IllegalArgumentException{
		
		System.out.println("balance enquiry operation start");
		pause();

		//retriveing the BankAccount object of the given account number
		BankAccount account=getAccount(accNum);

		//checking account and balance valid or not
		if(account==null)
			throw new IllegalArgumentException("Account is not found with the given details");

		System.out.println("Current Balance: ");
		account.currentBalance();

		pause();

	}//balanceEnquiry close

       public void transferMoney(long sourceAccNum,long destinationAccNum,double amt) 
		                                                     throws IllegalArgumentException {
		   System.out.println("transfer money operation start");
		   pause();

		   withdraw(sourceAccNum,amt);
		   deposite(destinationAccNum,amt);

		   System.out.println("transfer money operation end");
		   pause();
		
       }//transfer money close

       public void updateAccount(long accNum){

		}//

        public void closeAcccount(long accNum)throws IllegalArgumentException{
			System.out.println("closeAccount operation start");
			pause();

          if(accNum<=0)
			  throw new IllegalArgumentException("Account can not be -VE number and ZERO");

		  System.out.println("Searching for given accountNumber object");
		  pause();

		  //Linear search algorithm
		  for(int i=0;i<size;i++){//for finding BankAccount object for the given accNumber
			BankAccount account = accounts[i];

			if(account.getAccNum()==accNum){
				
				for(int j=i;j<size-1;j++){  //removing currnet location object
				   accounts[j]=accounts[j+1];  //by moving next object one location LEFT
				}

				accounts[size-1]=null;    //removing reference from lastlocation
				size--;                 //decreasing size by one

				System.out.println("account is deleted");
				pause();
				return;
			}
		  }//for loop closed

		  throw new IllegalArgumentException("Account is not found with the given details");

        }//closeAccount() end

		public void displayAccount(long accNum) throws IllegalArgumentException{
			System.out.println("\ndisplay Account operation start");
			pause();

			//retrieving the BankAccount object of the given account number
			BankAccount account=getAccount(accNum);

			//checking account and balance valid or not
			if(account==null)
				throw new IllegalArgumentException("Account is not found with the given details");

			System.out.println("Account details");
			System.out.println(account);

		}//display Account close

		public String toString(){
			StringBuilder accountsBuilder = new StringBuilder();

			if(size==0)
				return "No accounts found";

			for(int i=0;i<size;i++){
				accountsBuilder.append("\naccount "+(i+1)+" details:");
				accountsBuilder.append(accounts[i]+"\n");
			}

			return accountsBuilder.toString();
		}

}

//BankFrontOffice.java
import java.util.Scanner;
public class BankFrontOffice{
	public static void main(String[] args){

		Bank hdfcBank=new Bank();
		Scanner scn = new Scanner(System.in);

		loop:while(true){
			System.out.println("\nChoose one option");
			System.out.println(" 1.OpenAccount");
			System.out.println(" 2.Account details");
			System.out.println(" 3.Deposite");
			System.out.println(" 4.Withdraw");
			System.out.println(" 5.Balance Enquiry");
			System.out.println(" 6.Transfer Money");
			System.out.println(" 7.Display All accounts");
			System.out.println(" 8.Exit");

            System.out.print("Enter option:");
			int option=scn.nextInt();scn.nextLine();

			try{
				switch(option){
					case 1:{//Open Account
						hdfcBank.openAccount();
						break;
					}
					case 2:{//Account Details
						System.out.print("Enter Account number:");
						long accNum=scn.nextLong();scn.nextLine();
                        hdfcBank.displayAccount(accNum);
						break;
					}
					case 3:{//Deposite
					     System.out.print("Enter Account Number:");
						 long accNum=scn.nextLong();scn.nextLine();

						 System.out.print("Enter deposite amount:");
						 double amt=scn.nextDouble();scn.nextLine();

						 hdfcBank.deposite(accNum,amt);
						 break;
					}
					case 4:{//WIthdraw
					     System.out.print("Enter Account Number:");
						 long accNum=scn.nextLong();scn.nextLine();

						 System.out.print("Enter Withdraw amount:");
						 double amt=scn.nextDouble();scn.nextLine();

						 hdfcBank.withdraw(accNum,amt);

						 break;
					}
					case 5:{//Balance Enquiry
					     System.out.print("Enter Accoun Number:");
						 long accNum=scn.nextLong();scn.nextLine();

						 hdfcBank.balanceEnquiry(accNum);

						 break;
					}
					case 6:{//Transfer money
					     System.out.print("Enter source Account Number:");
						 long srcAccNum=scn.nextLong();scn.nextLine();

                         System.out.print("Enter destination Account Number:");
						 long destAccNum=scn.nextLong();scn.nextLine();

		                 System.out.print("Enter transfer Ammount:");
						 double amt=scn.nextDouble();scn.nextLine();

						 hdfcBank.transferMoney(srcAccNum,destAccNum,amt);

						 break;
                    }
					case 7:{//Display all accounts
					    System.out.println(hdfcBank);
						break;
					}
					case 8://Exit
					    System.out.println("_V/_V/_V/_Thank You,Please Visit again_V/_V/_V/_");
						break loop;

						default:
							System.out.println("Invalid option");

					}//switch end
				}catch(IllegalArgumentException e){
					System.out.println("Error:"+e.getMessage());
				}
		}//while(true) end
	}//main closeee
}//class close


