package be.afhistos.mc.launcher.whitoutname;

import fr.theshark34.openauth.AuthenticationException;
import fr.theshark34.openlauncherlib.LaunchException;
import fr.theshark34.openlauncherlib.util.Saver;
import fr.theshark34.swinger.Swinger;
import fr.theshark34.swinger.colored.SColoredBar;
import fr.theshark34.swinger.event.SwingerEvent;
import fr.theshark34.swinger.event.SwingerEventListener;
import fr.theshark34.swinger.textured.STexturedButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class Panes extends JPanel implements SwingerEventListener, ActionListener {
    private Image background = Swinger.getResource("font.png");
    private Saver saver = new Saver(new File(Main.DIR, "launcher.properties"));
    private JTextField usernameField = new JTextField(this.saver.get("username"));
    private JPasswordField passwordField;
    private STexturedButton playButton;
    private STexturedButton discord;
    private JCheckBox savePwd;
    private JCheckBox optifine;
    private JComboBox ram;
    private JLabel ramLabel;
    private SColoredBar progressBar;
    private JLabel infoLabel;

    public Panes(){
        Color green = new Color(55, 119, 33);
        if(new File(Main.DIR, "launcher.properties").exists()) {
            try {
                if (this.saver.get("savePwd?").equals("true")) {
                    this.passwordField = new JPasswordField(decrypt(this.saver.get("password")));
                } else {
                    this.passwordField = new JPasswordField("");
                    Panes.this.saver.set("password", " ");
                }
            } catch (NullPointerException ignored){
                this.passwordField = new JPasswordField("");
            }
        } else {
            this.passwordField = new JPasswordField("");
        }
        this.playButton = new STexturedButton(Swinger.getResource("play.png"));
        this.discord = new STexturedButton(Swinger.getResource("discord.png"));
        this.progressBar = new SColoredBar(Swinger.getTransparentInstance(Color.GRAY, 100), Swinger.getTransparentInstance(Color.GRAY, 150));
        this.infoLabel = new JLabel("Clique sur jouer !", 0);
        //Bouton save pwd
        this.savePwd = new JCheckBox("Sauvegarder le mot de passe");
        this.savePwd.setToolTipText("Permet de choisir si le launcher sauvegarde votre mot de passe");
        this.savePwd.setBounds(37,230, 290,19);
        this.savePwd.setBackground(Swinger.TRANSPARENT);
        this.savePwd.setOpaque(false);
        this.savePwd.setForeground(green);
        this.savePwd.setFont(this.usernameField.getFont().deriveFont(20.0f));
        this.savePwd.addActionListener(this);
        try {
            if (this.saver.get("savePwd?").equals("true")) {
                this.savePwd.setSelected(true);
            } else {
                this.savePwd.setSelected(false);
            }
        } catch (NullPointerException ignored){
            this.savePwd.setSelected(false);
        }
        this.add(savePwd);
        //JcheckBox Optifine (optifine)
        this.optifine = new JCheckBox("Jouer avec OptiFine");
        this.optifine.setToolTipText("Permet de jouer avec le mod optifine ou non");
        this.optifine.setBounds(37, 270, 290, 19);
        this.optifine.setBackground(Swinger.TRANSPARENT);
        this.optifine.setOpaque(false);
        this.optifine.setForeground(green);
        this.optifine.setFont(this.usernameField.getFont().deriveFont(20.0f));
        this.optifine.addActionListener(this);
        try{
            if(this.saver.get("useOptifine?").equals("true")){
                this.optifine.setSelected(true);
            } else {
                this.optifine.setSelected(false);
            }
        } catch (NullPointerException ignored){
            this.optifine.setSelected(false);
        }
        this.add(optifine);
        //JComboBox RAM (ram)
        this.ram = new JComboBox();
        ram.addItem("1 Go");
        ram.addItem("2 Go");
        ram.addItem("3 Go");
        ram.addItem("4 Go");
        ram.addItem("5 Go");
        ram.addItem("6 Go");
        ram.addItem("7 Go");
        ram.addItem("8 Go");
        ram.addItem("9 Go");
        ram.addItem("10 Go");
        ram.addItem("11 Go");
        ram.addItem("12 Go");
        ram.addItem("13 Go");
        ram.addItem("14 Go");
        ram.addItem("15 Go");
        ram.addItem("16 Go");
        this.ramLabel = new JLabel("Ram allouée : ");
        this.ramLabel.setBounds(37, 300, 150, 35);
        this.ramLabel.setForeground(green);
        this.ramLabel.setFont(this.usernameField.getFont().deriveFont(20.0f));
        this.ramLabel.setVisible(true);
        this.ram.setBounds(165, 305, 100, 25);
        try {
            this.ram.setSelectedItem(Panes.this.saver.get("ram"));
        } catch (NullPointerException e){
            this.ram.setSelectedItem(ram.getItemAt(1));
        }
        this.ram.setVisible(true);
        this.add(ramLabel);
        this.add(ram);

        this.setLayout(null);
        Color colors = new Color(199,198,197);
        this.usernameField.setCaretColor(green);
        this.usernameField.setForeground(green);
        this.usernameField.setFont(this.usernameField.getFont().deriveFont(35.0f));
        this.usernameField.setOpaque(false);
        this.usernameField.setBorder(null);
        this.usernameField.setBounds(37,122,317,59);
        this.usernameField.setText(this.saver.get("username", this.getName()));
        this.usernameField.setDisabledTextColor(Swinger.getTransparentInstance(green, 200));
        this.add(this.usernameField);
        this.passwordField.setCaretColor(green);
        this.passwordField.setForeground(green);
        this.passwordField.setFont(this.usernameField.getFont().deriveFont(35.0f));
        this.passwordField.setOpaque(false);
        this.passwordField.setBorder(null);
        this.passwordField.setBounds(522,122,317,59);
        this.passwordField.setDisabledTextColor(Swinger.getTransparentInstance(green, 200));
        this.add(this.passwordField);
        this.playButton.setBounds(680,370);
        this.playButton.addEventListener(this);
        this.add(this.playButton);
        this.discord.setBounds(600, 370);
        this.discord.addEventListener(this);
        this.add(this.discord);
        this.progressBar.setStringPainted(true);
        this.progressBar.setBounds(8,15,850,10);
        this.add(this.progressBar);
        this.infoLabel.setForeground(green);
        this.infoLabel.setFont(this.usernameField.getFont());
        this.infoLabel.setBounds(5,20,835,40);
        this.add(this.infoLabel);
    }

    @Override
    public void onEvent(SwingerEvent e){
        if(e.getSource() == this.playButton){
            this.setFieldsEnabled(false);
            if(this.usernameField.getText().replaceAll(" ", "").length() == 0 ||this.passwordField.getText().length() == 0){
                JOptionPane.showMessageDialog(this, "ERREUR: Adresse mail |Mot de passe invalide", "Erreur de connexion", 2);
                this.setFieldsEnabled(true);
                return;
            }
            Thread t = new Thread(){
                @Override
                public void run(){
                    try{
                        Main.auth(Panes.this.usernameField.getText(), Panes.this.passwordField.getText());
                    } catch (AuthenticationException e){
                        JOptionPane.showMessageDialog(Panes.this, "ERREUR: Impossible de se connecter: \n"+ e.getErrorModel().getErrorMessage(), "Erreur de connexion", 2);
                        Panes.this.setFieldsEnabled(true);
                        return;
                    }
                    Panes.this.saver.set("username", Panes.this.usernameField.getText());
                    if(savePwd.isSelected()) {
                        String pwd = Panes.this.encrypt(Panes.this.passwordField.getText());
                        Panes.this.saver.set("password", pwd);
                        Panes.this.saver.set("savePwd?", "true");
                    } else {
                        Panes.this.saver.set("password", "null");
                        Panes.this.saver.set("savePwd?", "false");
                    }
                    System.out.println((String)Panes.this.ram.getSelectedItem());
                    try {
                        Panes.this.saver.set("ram", (String)Panes.this.ram.getSelectedItem());
                    } catch (NullPointerException e){
                        Panes.this.saver.set("ram", (String)Panes.this.ram.getItemAt(1));
                    }
                    try {
                        Main.update();
                    } catch (Exception e){
                        Main.InterruptThread();
                        Main.getReporter().catchError(e, "ERREUR: Impossible de mettre le launcher à jour!");
                        Panes.this.setFieldsEnabled(true);
                        return;
                    }
                    File opti_dir = new File(Main.DIR, "/mods");
                    if(optifine.isSelected()){
                        Panes.this.saver.set("useOptifine?", "true");
                        if(new File(opti_dir, "optifine.jar.old").exists()){
                            new File(opti_dir, "optifine.jar.old").renameTo(new File(opti_dir,"optifine.jar"));
                        } else {}
                    } else {
                        Panes.this.saver.set("useOptifine?", "false");
                        if(new File(opti_dir, "optifine.jar").exists()){
                            try {
                                new File(opti_dir, "optifine.jar").delete();
                            } catch (Exception e1){e1.printStackTrace();}
                        } else {}
                    }
                    try {
                        Main.launch();
                    } catch (LaunchException e){
                        Main.InterruptThread();
                        Main.getReporter().catchError(e, "ERREUR: Impossible de lancer le jeu");
                        Panes.this.setFieldsEnabled(true);
                    } catch (IOException e){
                        e.printStackTrace();
                    }
                }
            };
            t.start();
        } else if (e.getSource() == this.discord){
            try {
                openWebPage(new URL("https://discord.gg/k9G3bwx"));
            } catch (MalformedURLException e1) {
                e1.printStackTrace();
            }
        }
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(this.background, 0, 0, this.getWidth(), this.getHeight(), this);
    }
    private void setFieldsEnabled(Boolean enabled){
        this.usernameField.setEnabled(enabled);
        this.passwordField.setEnabled(enabled);
        this.playButton.setEnabled(enabled);
    }
    public SColoredBar getProgressBar(){
        return this.progressBar;
    }
    public void setInfoText(String text){
        this.infoLabel.setText(text);
    }
    public String[] getRam(){
        if(!this.saver.get("ram").contains("Go")){
            JOptionPane.showMessageDialog(Frame.getInstance().getPanes(), "ERREUR: Merci de sélectionner le nombre de Go de RAM à allouer!", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
        int maxRam = Integer.parseInt(Panes.this.saver.get("ram").replace(" Go", "")) * 1024;
        int minRam = maxRam - 512;
        return new String[]{"-Xms" + minRam + "M", "-Xmx" + maxRam + "M"};
    }


    public String encrypt(String p){
        String crypt = "";
        int i = 0;
        while (i < p.length()){
            int c = p.charAt(i) ^ 48;
            crypt = String.valueOf(crypt) + (char)c;
            ++i;
        }
        return crypt;
    }
    public String decrypt(String p){
        String crypt = "";
        int i = 0;
        while (i < p.length()){
            int c = p.charAt(i) ^ 48;
            //ou diviser
            crypt = String.valueOf(crypt) + (char)c;
            ++i;
        }
        return crypt;
    }

    public static boolean browse(URI uri){
        Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
        if(desktop != null && desktop.isSupported(Desktop.Action.BROWSE)){
            try{
                desktop.browse(uri);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        return false;
    }
    public static boolean openWebPage(URL url){
        try{
            return browse(url.toURI());
        } catch (URISyntaxException e){
            e.printStackTrace();
        }
        return false;
    }

                                      @Override
    public void actionPerformed(ActionEvent e) {

    }
}
