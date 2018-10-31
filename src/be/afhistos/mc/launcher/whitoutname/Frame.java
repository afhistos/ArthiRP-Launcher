package be.afhistos.mc.launcher.whitoutname;

import fr.theshark34.swinger.Swinger;
import fr.theshark34.swinger.util.WindowMover;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Frame extends JFrame implements ActionListener {
    private static Frame instance;
    private Panes panes;

    public Frame(){
        this.setTitle("Arthi Roleplay | V 1.6");
        this.setSize(875, 465);
        this.setBounds(0, 0, 875, 465);
        this.setDefaultCloseOperation(3);
        this.setLocationRelativeTo(null);
        this.setUndecorated(false);
        this.setIconImage(Swinger.getResource("logo.png"));
        this.setResizable(false);
        this.panes = new Panes();
        this.setContentPane(this.panes);
        WindowMover mover = new WindowMover(this);
        this.addMouseListener(mover);
        this.addMouseMotionListener(mover);
        this.setVisible(true);
    }

    public static void main(String[] args){
        Swinger.setSystemLookNFeel();
        Swinger.setResourcePath("/be/afhistos/mc/launcher/whitoutname/assets");
        Main.CRASH_DIR.mkdirs();
        instance = new Frame();
    }

    public static Frame getInstance() {
        return instance;
    }

    public Panes getPanes() {
        return panes;
    }
    @Override
    public void actionPerformed(ActionEvent e){}
}
