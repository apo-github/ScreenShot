package com.company;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FileName extends Thread{

    String text = "";
    public FileName(){
        JFrame frame = new JFrame();
        frame.setTitle("ファイル名を決めてください");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTextField textField = new JTextField(30);//テキストフィールド30文字までおｋ
        textField.setMargin(new Insets(5,5,5,5));//余白
        textField.setFont(new Font("メイリオ",Font.PLAIN,16));//フォント
        LineBorder border = new LineBorder(Color.LIGHT_GRAY,1,true);//枠線
        textField.setBorder(border);

        JButton button = new JButton("保存");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                text = textField.getText();//テキストフィールドの値を取得
                frame.dispose();//frameを自動で閉じる
            }
        });

        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(600,100));


        panel.add(textField);
        panel.add(button);

        frame.add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }

    public String getText() {
        return text;
    }
}
