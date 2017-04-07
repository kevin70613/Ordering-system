import java.io.*;
import java.net.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class ClientGUI extends JFrame{
    
		FoodPanel[] food;
		Label total;
		JTable list;
		Integer ordernum = 1;  // initial is 1
		Label number;
		
		public ClientGUI()
		{
			setSize(1280,768);
			setUndecorated(true);
			//GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().setFullScreenWindow(this);
			total = new Label("0");
			
			//First layout
			Panel container = new Panel();
			container.setLayout(new BorderLayout());
			container.setSize(800,600);
			
			
			//Left layout on first layout
			Panel leftBlock = new Panel();
			GridLayout layout = new GridLayout(3,3);
			leftBlock.setLayout(layout);
			leftBlock.setBackground(Color.BLACK);
			leftBlock.setSize(600,600);
			layout.setHgap(10);
			layout.setVgap(10);
			container.add("Center",leftBlock);
			
			//Right layout on first layout
			Panel rightBlock = new Panel();
			rightBlock.setLayout(new BoxLayout(rightBlock,BoxLayout.Y_AXIS));
			rightBlock.setPreferredSize(new Dimension(200,600));
			//rightBlock.setBackground(Color.RED);
			container.add("East",rightBlock);
			
			//9 blocks of food on left layout
			food = new FoodPanel[9];
			for(int i=0;i<9;i++)
			{
				int cost;
				
				if(i<6)
				{
					cost = 420;
				}
				else
				{
					cost = 480;
				}
				
				food[i] = new FoodPanel(this,"00"+(i+1)+".jpg",(i+1),total,cost);
				leftBlock.add(food[i]);			
			} 
			
			//Right Layout 
			//Number of order 
			
			Label text_number = new Label("取餐編號");
			//text_number.setBackground(Color.RED);
			text_number.setAlignment(Label.CENTER);
			text_number.setPreferredSize(new Dimension(200,30));
			text_number.setMaximumSize(new Dimension(200,30));
			text_number.setFont(new Font(null,Font.PLAIN,16));
			rightBlock.add(text_number);
			
			number = new Label(ordernum.toString());
			number.setAlignment(Label.CENTER);
			number.setBackground(Color.RED);
			number.setPreferredSize(new Dimension(200,50));
			number.setMaximumSize(new Dimension(200,50));
			number.setFont(new Font(null,Font.PLAIN,32));
			rightBlock.add(number);	
			
			//Order list
			Label text_order = new Label("點餐清單");
			text_order.setAlignment(Label.CENTER);
			text_order.setPreferredSize(new Dimension(200,30));
			text_order.setMaximumSize(new Dimension(200,30));
			text_order.setFont(new Font(null,Font.PLAIN,16));
			rightBlock.add(text_order);
			
			String[] columnnames = {"品項","數量"};
			Object[][] data = {{"1號餐",new Integer(0)} , {"2號餐",new Integer(0)} , {"3號餐",new Integer(0)},
							   {"4號餐",new Integer(0)} , {"5號餐",new Integer(0)} , {"6號餐",new Integer(0)},
							   {"7號餐",new Integer(0)} , {"8號餐",new Integer(0)} , {"9號餐",new Integer(0)}};
				
			list = new JTable(data,columnnames);
			list.setFont(new Font(null,Font.PLAIN,32));
			list.setRowHeight(30);
			list.setPreferredSize(new Dimension(200,270));
			list.setMaximumSize(new Dimension(200,280));
			list.setBackground(Color.GREEN);
			rightBlock.add(list);
			
			//Total money
			Label text_total = new Label("總金額");
			text_total.setAlignment(Label.CENTER);
			text_total.setFont(new Font(null,Font.PLAIN,16));
			text_total.setPreferredSize(new Dimension(200,30));
			text_total.setMaximumSize(new Dimension(200,30));
			rightBlock.add(text_total);
					
			total.setAlignment(Label.CENTER);
			total.setFont(new Font(null,Font.PLAIN,32));
			total.setPreferredSize(new Dimension(200,50));
			total.setMaximumSize(new Dimension(200,50));
			rightBlock.add(total);
			
			//Confirm and cancel
			Panel confirm = new Panel();
			confirm.setLayout(new GridLayout(2,1));
			confirm.setSize(200,100);
			rightBlock.add(confirm);
			
			Button ok = new Button("結帳");
			ok.setFont(new Font(null,Font.PLAIN,32));
			ok.setBackground(Color.ORANGE);
			ok.addMouseListener(new Pay(this));
			//ok.setIcon(arg0);
			confirm.add(ok);
			Button reset = new Button("清除");
			reset.setBackground(Color.ORANGE);
			reset.setFont(new Font(null,Font.PLAIN,32));
			reset.addMouseListener(new ResetAll(this));
			confirm.add(reset);

			add(container);
		}
		
		public void updateTotal()
		{
			
			Integer totalmoney=0;
			
			for(FoodPanel f : food)
			{
				totalmoney += f.getNumber() * f.getCost();
			}
			
			//System.out.println(totalmoney);
			
			total.setText(totalmoney.toString());
			
		}

		public void resetTotal()
		{
			for(int i=0;i<9;i++)
			{
				food[i].number=0;
				list.setValueAt(0,i,1);
			}
			
			updateTotal();
		}

		public void setOrderNum(Integer num)
		{
			number.setText(num.toString());
		}


		public void setList()
		{
			Integer a;
			for(int i=0;i<9;i++)
			{
				a = food[i].getNumber();
				list.setValueAt(a,i,1);
			}
		}

		public void outPut(Integer num)
		{	
			int flag=0;
			int zero=0;
			String[] ordering;
			ArrayList<String> ordStr = new ArrayList<String>();
			
			for(int i=8;i>=0;i--){
				if(food[i].getNumber()!=0){
					zero = i;
					break;
				}
			}

			try{
				for(int i=0;i<9;i++){
					if(i == zero){
						flag = 1;
					}
					if(food[i].getNumber()!=0)			
					{
						ordStr.add((i+1) + "號餐 " + food[i].getNumber()+ " " + food[i].getCost()+ " " + num + " " +flag);
					}
				 }
			ordering = new String[ordStr.size()];
			for(int i=0;i<ordering.length;i++)
			   ordering[i] = ordStr.get(i);
			toSocket(ordering);
		    }catch(Exception e){
			    e.printStackTrace();
		    }
		}	


		class ResetAll extends MouseAdapter
		{
			ClientGUI jgui;

			public ResetAll(ClientGUI jgui)
			{
				this.jgui = jgui;
			}

			public void mouseClicked(MouseEvent e)
			{
				jgui.resetTotal();
			}
		}	

	//public static void main(String[] args)
	//{
		//	ClientGUI menu = new ClientGUI();
		//	menu.setVisible(true);
	//}

	public void toSocket(String[] ordering) throws UnknownHostException, ClassNotFoundException, IOException, InterruptedException{
		   Customer[] object = new Customer[ordering.length];
		   for(int i=0;i<ordering.length;i++){
			  String[] split = ordering[i].split(" ");
		      object[i] = new Customer(split[0],Integer.parseInt(split[1]),Integer.parseInt(split[2]),Integer.parseInt(split[3]),Integer.parseInt(split[4]));		      
		   }   
		   SocketClient_v3 client = new SocketClient_v3(object);
		   Integer custNo = Integer.parseInt(client.run());
		   setOrderNum(custNo);		 
		    for(int i=0;i<ordering.length;i++){
			   object[i].setCustNo(custNo);
			}
	}
	
}

class FoodPanel extends Panel{
	Button button;
	int number = 0;
	int cost = 0;
	
	public FoodPanel(ClientGUI jgui,String image,int Food,Label total,int cost)
	{
		this.cost = cost;
		setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		
		JLabel photo = new JLabel();
		photo.setIcon(new ImageIcon(image));
		photo.setPreferredSize(new Dimension(200,200));
		add(photo);   
		button = new Button(Food+"號餐詳情");
		
		button.setFont(new Font(null,Font.PLAIN,32));
        button.setBackground(Color.ORANGE);
		//button.setPreferredSize(new Dimension(180,10));
		button.addMouseListener(new ShowDetail(jgui,"00"+Food+".txt",total,this));
		add(button);
	} 
	
	public void setNumber(int number)
	{
		this.number = number;
		//System.out.println(number);
	}
	
	public int getNumber()
	{
		return number;
	}
	
	public int getCost()
	{
		return cost;
	}

}

class ShowDetail extends MouseAdapter{
	ClientGUI jgui;
	Label total;
	String filename;
	FoodPanel f;
	
	public ShowDetail(ClientGUI jgui,String filename,Label total,FoodPanel f)
	{
		this.jgui = jgui;
		this.total = total;
		this.filename = filename;
		this.f = f;
	}
	
	public void mouseClicked(MouseEvent e)
	{
		Dialog pop = new Dialog(jgui);
		pop.setSize(600,200);
		pop.setLocationRelativeTo(null);
		pop.setBackground(Color.WHITE);
		pop.setUndecorated(true);
		pop.add(new MenuDetail(pop,filename,total,f,jgui));
		pop.setVisible(true);
	}
}

class MenuDetail extends Panel
{	
	public MenuDetail(Dialog pop,String filename,Label total,FoodPanel f,ClientGUI jgui)
	{
		setSize(600,200);
		Panel container = new Panel();
		container.setLayout(new BorderLayout());
		
		try
		{
			byte[] encoded = Files.readAllBytes(Paths.get(filename));
			TextArea detail = new TextArea(new String(encoded));
			detail.setPreferredSize(new Dimension(400,200));
			detail.setBackground(Color.WHITE);
			container.add("Center",detail);
		}
		catch(Exception e)
		{
		}
		
		Panel rightBlock = new Panel();
		GridLayout layout = new GridLayout(3,1);
		rightBlock.setLayout(layout);
		layout.setHgap(20);
		layout.setVgap(20);
		rightBlock.setPreferredSize(new Dimension(200,200));
		container.add("East",rightBlock);
		
		Integer[] amount = {0,1,2,3,4,5,6,7,8,9};
		JComboBox amountlist = new JComboBox<Integer>(amount);
		amountlist.setSelectedIndex(0);
		rightBlock.add(amountlist); 
		
		Button ok = new Button("Confirm");
		ok.addMouseListener(new MakeConfirm(amountlist,total,pop,f,jgui));
		ok.setBackground(Color.RED);
		rightBlock.add(ok);
		Button cancel = new Button("Cancel");
		cancel.setBackground(Color.RED);
		cancel.addMouseListener(new MakeCancel(pop,amountlist));
		rightBlock.add(cancel);
		
		setVisible(true);
		add(container);		
	}
}

class MakeConfirm extends MouseAdapter
{
	Label total;
	Dialog pop;
	JComboBox list;
	Integer totalmoney;
	FoodPanel f;
	ClientGUI jgui;
		
	public MakeConfirm(JComboBox list,Label total,Dialog pop,FoodPanel f,ClientGUI jgui)
	{
		this.list = list;
		this.pop = pop;
		this.total = total;
		this.f = f;
		this.jgui = jgui;
	}
	
	public void mouseClicked(MouseEvent e)
	{
		totalmoney = Integer.valueOf(total.getText());
		int money;
		int amount = (int)list.getSelectedItem();
		//System.out.println(amount);
		f.setNumber(amount);
		jgui.updateTotal(); 
		jgui.setList();
		pop.dispose();
	}
}

class MakeCancel extends MouseAdapter
{
	Dialog pop;
	JComboBox list;
	
	public MakeCancel(Dialog pop,JComboBox list)
	{
		this.pop = pop;
		this.list = list;
	}
	
	public void mouseClicked(MouseEvent e)
	{
		list.setSelectedIndex(0);
		pop.dispose();		
	}	
}


class Pay extends MouseAdapter
{
	ClientGUI jgui;

	public Pay(ClientGUI jgui)
	{
		this.jgui = jgui;
	}

	public void mouseClicked(MouseEvent e)
	{
		jgui.outPut(jgui.ordernum);	
		//
		jgui.setOrderNum(++jgui.ordernum);
		jgui.resetTotal();
	}
}

