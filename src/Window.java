
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.concurrent.TimeUnit;
import javax.swing.JComponent;

public class Window extends Thread {
    int ID;


    public Window() throws AWTException {
    }

    JFrame MakeScreen(){
        JFrame fr = new JFrame();
        fr.setSize(600, 600);
        fr.setDefaultCloseOperation(3);
        fr.setVisible(true);

        return(fr);
    }
    JPanel MakePanel(JFrame fr){
        JPanel pn = new JPanel();
        fr.add(pn);
        return(pn);
    }
    public void ClearScreen(JFrame fr, JPanel pn) {
        fr.getContentPane().removeAll();
        pn.removeAll();
    }
    public void setID(int IDvar){
        ID = IDvar;
    }
    public void setSize(JFrame fr, int x, int y){
        fr.setSize(x, y);
    }
    public void InstantGoTo(JFrame fr, int x, int y){
        fr.setLocation(x, y);
    }
    public void GoTo(JFrame fr, int x, int y, int time) throws InterruptedException {
        //double dist = Math.sqrt(x * x + y * y);
        int OriginX = fr.getX();
        int OriginY = fr.getY();

        double vectx = (x - OriginX) / time;
        //System.out.println(vectx);
        double vecty = (y - OriginY) / time;
        for(int i=0; i<=time+3; i++) {

            fr.setLocation(
                    OriginX + (int)(vectx * i),
                    OriginY + (int)(vecty * i)
            );
            //System.out.println(originx);
            //System.out.println(vectx*(i / time));
            //System.out.println(originx + (int)Math.round(vectx*(i / time)));
            //System.out.println(originy + (int)Math.round(vecty*(i / time)));
            //System.out.println(OriginX + (int)(vecty * (time + 3)));
            //Thread.sleep(1);
            TimeUnit.MILLISECONDS.sleep(1);
        }
        //System.out.println(y);
        fr.setLocation(x, y);
    }

    public void setColorToScreen(int x, int y, JFrame fr, BufferedImage capture) throws AWTException {


        fr.getContentPane().setBackground(new Color(capture.getRGB(x, y)));
    }
}
