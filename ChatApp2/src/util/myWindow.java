package util;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class myWindow {
	 public static void run(final JFrame f, final int width, final int height){
         SwingUtilities.invokeLater(new Runnable(){

             public void run(){


                 //������ͼ��
                 //f.setIconImage(new ImageIcon("e:/JavaWS/52b1443632f42/QQ.png").getImage());
                 //����
                 //f.setTitle(f.getClass().getSimpleName());
//                 f.setTitle("ClientLogin");
                 //�����˳���Ϊ
                 f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                 //���ô��ڴ�С���ɱ�
                 f.setResizable(false);
                 //���ô��ڴ򿪾���
//                 f.setLocationRelativeTo(null);
                 f.setLocation(450, 150);
                 //���ڴ�С
                 f.setSize(width, height);
                 //չʾ����
                 f.setVisible(true);
             }
         });
     }
}
