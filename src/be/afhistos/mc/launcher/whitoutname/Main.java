package be.afhistos.mc.launcher.whitoutname;

import fr.theshark34.openauth.AuthPoints;
import fr.theshark34.openauth.AuthenticationException;
import fr.theshark34.openauth.Authenticator;
import fr.theshark34.openauth.model.AuthAgent;
import fr.theshark34.openauth.model.response.AuthResponse;
import fr.theshark34.openlauncherlib.LaunchException;
import fr.theshark34.openlauncherlib.external.ExternalLaunchProfile;
import fr.theshark34.openlauncherlib.external.ExternalLauncher;
import fr.theshark34.openlauncherlib.minecraft.*;
import fr.theshark34.openlauncherlib.util.CrashReporter;
import fr.theshark34.supdate.BarAPI;
import fr.theshark34.supdate.SUpdate;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class Main {
    public static String IP = "http://149.91.81.78/s-update/launcher/";
    public static final GameVersion VERSION = new GameVersion("1.7.10", GameType.V1_7_10);
    public static final GameInfos INFOS = new GameInfos("WithoutName", VERSION, new GameTweak[]{GameTweak.FORGE});
    public static final File DIR = INFOS.getGameDir();
    public static final File CRASH_DIR = new File(DIR, "errors");
    private static AuthInfos authInfos;
    private static Thread update;
    private static CrashReporter reporter;
    static {
        reporter = new CrashReporter("WithoutName", CRASH_DIR);
    }
    public static void auth(String un, String pwd) throws AuthenticationException{
        Authenticator authenticator = new Authenticator("https://authserver.mojang.com/", AuthPoints.NORMAL_AUTH_POINTS);
        AuthResponse authResponse = authenticator.authenticate(AuthAgent.MINECRAFT, un, pwd, "");
        authInfos = new AuthInfos(authResponse.getSelectedProfile().getName(), authResponse.getAccessToken(), authResponse.getSelectedProfile().getId());

    }
    public static void update() throws Exception{
        SUpdate server = new SUpdate(IP, DIR);
        server.getServerRequester().setRewriteEnabled(true);
        update = new Thread(){
            private int val;
            private int max;
            @Override
            public void run(){
                while (!this.isInterrupted()){
                    if(BarAPI.getNumberOfFileToDownload() == 0){
                        Frame.getInstance().getPanes().setInfoText("Vérifications des fichiers");
                        //Faire une animation de chargement |/-\|\-/
                        continue;
                    }
                    this.val = BarAPI.getNumberOfDownloadedFiles();
                    this.max = BarAPI.getNumberOfFileToDownload();
                    Frame.getInstance().getPanes().getProgressBar().setValue(this.val);
                    Frame.getInstance().getPanes().getProgressBar().setMaximum(this.max);
                    if(this.val > this.max){
                        Frame.getInstance().getPanes().setInfoText("Téléchargement des fichiers terminé avec succès");
                        continue;
                    }
                    Frame.getInstance().getPanes().setInfoText("Téléchargement des fichiers du jeu: "+val+" / "+max);
                }
            }

        };
        update.start();
        server.start();
        update.interrupt();
    }

    public static void launch() throws IOException, LaunchException{
        ExternalLaunchProfile profile = MinecraftLauncher.createExternalProfile(INFOS, GameFolder.BASIC, authInfos);
        profile.getVmArgs().addAll(Arrays.asList(Frame.getInstance().getPanes().getRam()));
        //profile.setArgs(Arrays.asList("94.23.221.162:26815"));
        ExternalLauncher launcher = new ExternalLauncher(profile);
        launcher.setLogsEnabled(true);
        Frame.getInstance().setVisible(false);
        try{
            launcher.launch();
        } catch (LaunchException e){
            e.printStackTrace();
        }
        System.exit(0);
    }

    public static void InterruptThread(){
        update.interrupt();
    }

    public static CrashReporter getReporter() {
        return reporter;
    }
}
