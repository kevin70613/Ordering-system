

import java.io.Serializable;

public class Customer implements Serializable{
	
	private String name;
	private int num;
	private int price;
	private int custNo = 0;
	private int finish;  //�ΨӧP�_�����U���I�\�O�_���� �P�U���I�\��ƵL�� �I�\����assign 1 else 0
	
	public Customer(String name,int num,int price,int custNo,int finish){
		setName(name);
		setNum(num);
		setPrice(price);
		setCustNo(custNo);
		setFinish(finish);
	}
	
	public Customer(Customer obj){
		this.name = obj.getName();
		this.num = obj.getNum();
		this.price = obj.getPrice();
		this.custNo = obj.getCustNo();
		this.finish = obj.getFinish();
	}
	
	public String getName(){
		return name;
	}
	
	public int getNum(){
		return num;
	}
	
	public int getPrice(){
		return price;
	}
	
	public int getCustNo(){
		return custNo;
	}
	
    public int getFinish(){
	     return finish;
	}	
	
	public void setName(String name)
	{
		this.name = name;
	} 
	
	public void setNum(int num){
		this.num = num;
	}
	
	public void setPrice(int price){
		this.price = price;
	}
	
	public void setCustNo(int custNo){
		this.custNo = custNo;
	}
	
	public void setFinish(int finish){
	    this.finish = finish;
	}	
	
}