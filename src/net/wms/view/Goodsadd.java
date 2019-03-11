package net.wms.view;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import net.wms.bean.Goods;
import net.wms.dao.GoodsmanagementImp;
import net.wms.dao.StoragemanagementImp;
import net.wms.util.DB;

public class Goodsadd extends IndexAdmin{
	JTextField name;
	JTextField style;
	JTextField number;
	JComboBox id;
	Goods goods = new Goods();
	
	public Goodsadd(String name) {
		super(name);
		init();
	}
	public void init() {
		Font d = new Font("����", Font.BOLD, 24);
		Font f = new Font("����", Font.BOLD, 18);
		JLabel goodstitle = new JLabel("�����Ʒ");
		JLabel goodsname = new JLabel("��Ʒ����");
		name = new JTextField();
		JLabel goodsstyle = new JLabel("��  �ͣ�");
		style = new JTextField();
		JLabel goodsnumber = new JLabel("��  ����");
		number = new JTextField();
		JLabel storageid = new JLabel("�ֿ�ţ�");
		StoragemanagementImp s = new StoragemanagementImp();
		try {
			s.Query1("select storageID from storage");
			id = new JComboBox(StoragemanagementImp.vr);
		} catch (SQLException e2) {
			// TODO �Զ����ɵ� catch ��
			e2.printStackTrace();
		}
		JButton submit = new JButton("�ύ");
		JButton reset = new JButton("����");
		goodstitle.setBounds(130, 40, 100, 40);
		goodstitle.setFont(d);
		goodsname.setBounds(60, 120, 80, 30);
		goodsname.setFont(f);
		name.setBounds(140, 120, 150, 30);
		name.setFont(f);
		goodsstyle.setBounds(60, 180, 80, 30);
		goodsstyle.setFont(f);
		style.setBounds(140, 180, 150, 30);
		style.setFont(f);
		goodsnumber.setBounds(60, 240, 80, 30);
		goodsnumber.setFont(f);
		number.setBounds(140, 240, 150, 30);
		number.setFont(f);
		storageid.setBounds(60, 300, 80, 30);
		storageid.setFont(f);
		id.setBounds(140, 300, 150, 30);
		id.setFont(f);
		submit.setBounds(90, 360, 80, 30);
		submit.setFont(f);
		reset.setBounds(200, 360, 80, 30);
		reset.setFont(f);
		index.add(goodstitle);
		index.add(goodsname);
		index.add(name);
		index.add(goodsstyle);
		index.add(style);
		index.add(goodsnumber);
		index.add(number);
		index.add(storageid);
		index.add(id);
		index.add(submit);
		index.add(reset);
		
		reset.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO �Զ����ɵķ������
				name.setText("");
				style.setText("");
				number.setText("");
			}
		});
		submit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO �Զ����ɵķ������
				if(name.getText().equals("") || style.getText().equals("") || number.getText().equals("")) {
					JOptionPane.showMessageDialog(null,"����������");
				} else {
					goods.setGoodsname(name.getText());
					goods.setGoodsstyle(style.getText());
					goods.setGoodsnumber(Integer.parseInt(number.getText()));
					goods.setStorageID(id.getSelectedItem().toString());
					GoodsmanagementImp g = new GoodsmanagementImp();
					String gname = goods.getGoodsname();
			        int num = goods.getGoodsnumber();
					int Stge = Integer.parseInt(goods.getStorageID());
					boolean flag = false;
					Connection conn = DB.getConnection();
					int number1 = -1,st = -1;
				    String sql = "select goodsname,goodsnumber from goods,storage where storage.id = "+Stge;
				    try {
					PreparedStatement pra = conn.prepareStatement(sql);
					ResultSet rs = pra.executeQuery();
					//���������
					while(rs.next())
					{
						//��ȡ���ݿ����ֶ�
					   if(rs.getString("goodsname").equals(gname))
					   {
						   number1 = rs.getInt("goodsnumber");
						  // st = Integer.parseInt(rs.getString("StorageID"));
						   flag = true;
						   break;
					   }
						
					}
					
				}
				    catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				  if(flag == false)
				  {
					 try {
						g.Add(goods, "insert into goods(goodsname,goodsstyle,goodsnumber,storageID)" + "values(?,?,?,?)");
						name.setText("");
						style.setText("");
						number.setText("");
						JOptionPane.showMessageDialog(null, "��ӳɹ�");
					}
					 catch (SQLException e1) {
						// TODO �Զ����ɵ� catch ��
						e1.printStackTrace();
					}
				  }
				  else 
				  {
					  number1 += num;
					 // if()
					  try {
						g.Out("update goods set goodsnumber = ? where  goodsname = '"+gname+"' and storageID = '"+Stge+"'" ,number1);
						JOptionPane.showMessageDialog(null, "��ӳɹ�");
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
						name.setText("");
						style.setText("");
						number.setText("");
				  }
					
				}
			}
		});
	}
}
