

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class insertToSQL {
	
	  private Connection con = null; //Database objects 
	  //�s��object 
	  private Statement stat = null; //����,�ǤJ��sql������r�� 
	  private ResultSet rs = null; //���G�� 
	  private PreparedStatement pst = null; //����,�ǤJ��sql���w�x���r��,�ݭn�ǤJ�ܼƤ���m 
	  
	  
	  private String dropdbSQL = "DROP TABLE Customer ";    // drop table
	  
	  private String createdbSQL = "CREATE TABLE Customer (" +    //create table
	    "  name    VARCHAR(20) " + 
	    "  , num  INTEGER " + 
	    "  , price INTEGER " +
		"  , custNo INTEGER )"; 
	  
	  private String insertdbSQL = "insert into Customer" +    //insert data 
	      "(name,num,price,custNo) VALUES" + 
	      "(?,?,?,?)"; 
	  
	  private String selectSQL = "select * from Customer ";  //select all data from Customer
	  
	  public insertToSQL(Customer[] object) 
	  { 
	    try { 
	      Class.forName("com.mysql.jdbc.Driver"); //���Udriver 
	      con = DriverManager.getConnection( 
	      "jdbc:mysql://localhost/test?useUnicode=true&characterEncoding=utf-8", 
	      "root","k25367615"); 
	      //���oconnection
	      //jdbc:mysql://localhost/test?useUnicode=true&characterEncoding=utf-8
	      //localhost=>�D���W,test=>database�W
	      //useUnicode=true&characterEncoding=utf-8�ϥΪ��s�X 
		  
		  //dropTable(); 
	      //createTable(); 
	      for(int i=0;i<object.length;i++)
	        insertTable(object[i].getName(),object[i].getNum(),object[i].getPrice(),object[i].getCustNo()); 
	      
	    } 
	    catch(ClassNotFoundException e) 
	    { 
	      System.out.println("DriverClassNotFound :"+e.toString()); 
	    }//���i��|����sqlexception 
	    catch(SQLException x) { 
	      System.out.println("Exception :"+x.toString()); 
	    } 
	    
	  } 
	  
	  //�إ�table���覡 
	  //�i�H�ݬ�Statement���ϥΤ覡 
	  public void createTable() 
	  { 
	    try 
	    { 
	      stat = con.createStatement(); 
	      stat.executeUpdate(createdbSQL); 
	    } 
	    catch(SQLException e) 
	    { 
	      System.out.println("CreateDB Exception :" + e.toString()); 
	    } 
	    finally 
	    { 
	      Close(); 
	    } 
	  } 
	  
	  //�s�W��� 
	  //�i�H�ݬ�PrepareStatement���ϥΤ覡 
	  public void insertTable(String name,int num,int price,int custNo) 
	  { 
	    try 
	    { 
	      pst = con.prepareStatement(insertdbSQL); 
	      
	      pst.setString(1, name); 
	      pst.setInt(2, num);
	      pst.setInt(3, price);
		  pst.setInt(4, custNo);
	      pst.executeUpdate(); 
	    } 
	    catch(SQLException e) 
	    { 
	      System.out.println("InsertDB Exception :" + e.toString()); 
	    } 
	    finally 
	    { 
	      Close(); 
	    } 
	  } 
	  //�R��Table, 
	  //��إ�table�ܹ� 
	  public void dropTable() 
	  { 
	    try 
	    { 
	      stat = con.createStatement(); 
	      stat.executeUpdate(dropdbSQL); 
	    } 
	    catch(SQLException e) 
	    { 
	      System.out.println("DropDB Exception :" + e.toString()); 
	    } 
	    finally 
	    { 
	      Close(); 
	    } 
	  } 
	  
	  //�d�߸�� 
	  //�i�H�ݬݦ^�ǵ��G���Ψ��o��Ƥ覡 
	  public void SelectTable() 
	  { 
	    try 
	    { 
	      stat = con.createStatement(); 
	      rs = stat.executeQuery(selectSQL); 
	      System.out.println("Name\t\tNumber\t\tPRICE"); 
	      while(rs.next()) 
	      { 
	        System.out.println(rs.getString("name")+"\t\t"+ 
	            rs.getInt("num")+"\t\t"+rs.getInt("price")+"\t\t"+rs.getInt("custNo")); 
	      } 
	    } 
	    catch(SQLException e) 
	    { 
	      System.out.println("DropDB Exception :" + e.toString()); 
	    } 
	    finally 
	    { 
	      Close(); 
	    } 
	  } 
	  //����ϥΧ���Ʈw��,�O�o�n�����Ҧ�Object 
	  //�_�h�b����Timeout��,�i��|��Connection poor�����p 
	  private void Close() 
	  { 
	    try 
	    { 
	      if(rs!=null) 
	      { 
	        rs.close(); 
	        rs = null; 
	      } 
	      if(stat!=null) 
	      { 
	        stat.close(); 
	        stat = null; 
	      } 
	      if(pst!=null) 
	      { 
	        pst.close(); 
	        pst = null; 
	      } 
	    } 
	    catch(SQLException e) 
	    { 
	      System.out.println("Close Exception :" + e.toString()); 
	    } 
	  } 
	  
	}