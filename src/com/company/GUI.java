package com.company;

import javax.swing.*;
import java.io.File;

public class GUI extends JFrame {
    JPanel panel;
    String folderPath = "";
    String username = System.getProperty("user.name");

    public GUI(){
        
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException
                | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        JFileChooser filechooser = new JFileChooser("C:/Users/" + username + "/Pictures");
        filechooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);//フォルダのみ指定できる
        filechooser.setDialogTitle("クリップボードの保存場所を決めてください");

        int selected = filechooser.showOpenDialog(this);
        if (selected == JFileChooser.APPROVE_OPTION){
            File file = filechooser.getSelectedFile();//フォルダパスを取得
            folderPath = file.getAbsolutePath();
        }
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//自動でFrameを閉じる
    }
    public String getFolderPath(){
        return folderPath;
    }
}
