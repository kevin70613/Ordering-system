

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class insertToSQL {
	
	  private Connection con = null; //Database objects 
	  //連接object 
	  private Statement stat = null; //執行,傳入之sql為完整字串 
	  private ResultSet rs = null; //結果集 
	  private PreparedStatement pst = null; //執行,傳入之sql為預儲之字申,需要傳入變數之位置 
	  
	  
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
	      Class.forName("com.mysql.jdbc.Driver"); //註冊driver 
	      con = DriverManager.getConnection( 
	      "jdbc:mysql://localhost/test?useUnicode=true&characterEncoding=utf-8", 
	      "root","k25367615"); 
	      //取得connection
	      //jdbc:mysql://localhost/test?useUnicode=true&characterEncoding=utf-8
	      //localhost=>主機名,test=>database名
	      //useUnicode=true&characterEncoding=utf-8使用的編碼 
		  
		  //dropTable(); 
	      //createTable(); 
	      for(int i=0;i<object.length;i++)
	        insertTable(object[i].getName(),object[i].getNum(),object[i].getPrice(),object[i].getCustNo()); 
	      
	    } 
	    catch(ClassNotFoundException e) 
	    { 
	      System.out.println("DriverClassNotFound :"+e.toString()); 
	    }//有可能會產生sqlexception 
	    catch(SQLException x) { 
	      System.out.println("Exception :"+x.toString()); 
	    } 
	    
	  } 
	  
	  //建立table的方式 
	  //可以看看Statement的使用方式 
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
	  
	  //新增資料 
	  //可以看看PrepareStatement的使用方式 
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
	  //刪除Table, 
	  //跟建立table很像 
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
	  
	  //查詢資料 
	  //可以看看回傳結果集及取得資料方式 
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
	  //完整使用完資料庫後,記得要關閉所有Object 
	  //否則在等待Timeout時,可能會有Connection poor的狀況 
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