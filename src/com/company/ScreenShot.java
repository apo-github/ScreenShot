package com.company;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ScreenShot extends JFrame implements FlavorListener {
    //private static Clipboard clipboard = null;//イベントを受け取るためのやつ
    static String folderPath = "";
    String fileName = "";
    boolean exit = true;//ChooserのDistroy()がなかったので無理やり実装
    public static void main(String[] args) {


        ScreenShot dispCap = new ScreenShot();

        //保存するフォルダの初期決定
        if(folderPath.equals("")) {
            GUI gui = new GUI();
            folderPath = gui.getFolderPath();
        }


        Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();//クリップボードの情報取得
        clip.addFlavorListener( dispCap );// FlavorListener を登録
        ScreenShot.clearClip();// クリップボードの値をクリア

        //FlavorEvent で処理させるので、ずっとスレッドをSleep
        while(true){
            try{
                Thread.sleep(2000);
            }catch (InterruptedException ex){}
        }

    }
    public static void clearClip(){

        StringSelection strSel = new StringSelection("");
        Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();//クリップボードの情報取得
        clip.setContents(strSel,strSel);
    }

    @Override
    public void flavorsChanged(FlavorEvent e) {

        try {//ここでスリープがないとクリップボードに入る前に保存処理に移るためエラーが大量に出る
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        //クリアの時のイベントを拾わない
        Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();//クリップボードの情報取得
        Transferable flavor = clip.getContents(null);//フレーバーを定義,Transferableインスタンスとしてクリップボードの中身を取り出す。

        if (flavor.isDataFlavorSupported(DataFlavor.imageFlavor)){
            try {
                //ファイルの保存
                while (fileName.equals("") && exit){
                    JFileChooser fileChooser = new JFileChooser(folderPath);
                    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    fileChooser.setDialogTitle("名前を付けて保存");
                    String tip = "同じファイル名は上書きされます";
                    fileChooser.setApproveButtonToolTipText(tip);


                    FileFilter filter = new FileNameExtensionFilter("png","png");
                    fileChooser.addChoosableFileFilter(filter);
                    int selected = fileChooser.showSaveDialog(this);
                    if (selected == JFileChooser.APPROVE_OPTION){
                        File file = fileChooser.getSelectedFile();
                        fileName = file.getName();
                        BufferedImage bimg = (BufferedImage) flavor.getTransferData(DataFlavor.imageFlavor);//flavorをBufferedImageに変換
                        ImageIO.write(bimg, "PNG", new File(folderPath + "/" + fileName + ".png"));//pngで書き出し,BufferedImageはImageIoでファイル書き出しが簡単に行える
                        System.out.println("保存しました");
                    }
                    if (selected == JFileChooser.CANCEL_OPTION){//バツもしくは取り消しの時
                        System.out.println("作業が取り消されました");
                        fileChooser.cancelSelection();
                        exit = false;
                    }
                }





            } catch (UnsupportedFlavorException ex) {
                System.out.println("Flavorがオブジェクトをサポートしていないエラーだぞ");
            } catch (IOException ex) {
                System.out.println("IOExceptionエラーだぞ(多分初期フォルダが指定されてないぜ)");
            }

            fileName = "";
            exit = true;
            clearClip();
        }
    }
}
