import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.util.concurrent.TimeUnit;


public class Main {
    Window[] w = new Window[99];
    JFrame fr[] = new JFrame[99];
    int ID = 0;
    Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
    Robot bebra = new Robot();
    BufferedImage capture = bebra.createScreenCapture(screenRect);

    public Main() throws AWTException {
    }

    public void UpdateImage(){
        capture = bebra.createScreenCapture(screenRect);
    }
    Window MakeWindow(int x, int y, int sizex, int sizey) throws InterruptedException, AWTException {
        //AI types
        //1 = tries to stay at range away as much as possible
        //2 = will be running headfirst at the enemy and minimizing the range
        //3 = will stay at range+1 away
        //4 = will move randomly
        //COLORS
        //0 = black
        //1 = red
        //2 = blue
        //3 = green
        //4 = orange
        //5 = invisible
        ID += 1;
        w[ID] = new Window();
        fr[ID] = w[ID].MakeScreen();
        w[ID].GoTo(fr[ID], x, y, 1);
        w[ID].setSize(fr[ID], sizex, sizey);
        w[ID].setID(ID);
        return(w[ID]);

    }

    public void limbo(Point2D[] p) throws InterruptedException {
        Point2D[] pclone = p.clone();

        for(Window wd : w){
            if(wd!=null) {
                int rnd = new Random().nextInt(pclone.length-1) + 1;
                //System.out.println(rnd);
                wd.GoTo(fr[wd.ID], (int) pclone[rnd].getX(), (int) pclone[rnd].getY(), 20);
                Point2D[] pcloneclone = new Point2D[pclone.length-1];
                for(int i=0, k=0;i<pclone.length;i++){
                    if(i!=rnd){
                        pcloneclone[k]=pclone[i];
                        k++;
                    }
                }
                pclone = pcloneclone;
            }
        }
    }

    public void Worm(int x, int y) throws InterruptedException, AWTException {

        //w[1].GoTo(fr[1], x, y, 10);
        w[1].InstantGoTo(fr[1], x, y);
        w[1].setColorToScreen(x, y, fr[1], capture);
        for(Window wd : w){
            if(wd!=null){
                if(wd.ID!=1) {
                    int Originx = fr[wd.ID].getX();
                    int Originy = fr[wd.ID].getY();
                    double dist = Math.sqrt(Math.pow(fr[wd.ID-1].getX() - fr[wd.ID].getX(), 2)
                            +
                            Math.pow(fr[wd.ID-1].getY() - fr[wd.ID].getY(), 2)
                    );
                    double vectx = (fr[wd.ID-1].getX() - fr[wd.ID].getX()) / dist;
                    double vecty = (fr[wd.ID-1].getY() - fr[wd.ID].getY()) / dist;
                    //System.out.println(vectx);
                    //wd.InstantGoTo(fr[wd.ID], Originx + (int)(vectx * (dist-40)), Originy + (int)(vecty * (dist-40)));
                    int destx = Originx + (int)(vectx * (dist-40));
                    int desty = Originy + (int)(vecty * (dist-40));
                    wd.InstantGoTo(fr[wd.ID], destx, desty);
                    wd.setColorToScreen(destx, desty, fr[wd.ID], capture);
                    //TimeUnit.MILLISECONDS.sleep(200);
                }
            }
        }
    }



    public static void main(String[] args) throws InterruptedException, AWTException {
        Main trollface = new Main();


        for(int i = 0; i<4; i++){
            trollface.MakeWindow(140 * i + 700, 200, 100,100);
        }
        for(int i = 0; i<4; i++){
            trollface.MakeWindow(140 * i + 700, 600, 100,100);
        }
        //for(int i = 0; i<4; i++){
        //    trollface.MakeWindow(140 * i + 700, 1000, 100,100);
        //}
        //for(int i = 0; i<4; i++){
        //   trollface.MakeWindow(140 * i + 700, 1400, 100,100);
        //}
        TimeUnit.MILLISECONDS.sleep(200);

        Point2D[] p = new Point2D[9];
        int pID = 0;
        for(int i = 0; i<4; i++){
            pID += 1;
            int finalI = i;
            p[pID] = new Point2D() {
                @Override
                public double getX() {
                    return 140 * finalI + 700;
                }

                @Override
                public double getY() {
                    return 200;
                }

                @Override
                public void setLocation(double x, double y) {

                }
            };
        }

        for(int i = 0; i<4; i++){
            pID += 1;
            int finalI = i;
            p[pID] = new Point2D() {
                @Override
                public double getX() {
                    return 140 * finalI + 700;
                }

                @Override
                public double getY() {
                    return 600;
                }

                @Override
                public void setLocation(double x, double y) {

                }
            };
        }


        //System.out.println(p[1].getX());
        Random rand = new Random();
        //for(int i=0; i<4; i++){
        //    trollface.limbo(p);
        //    TimeUnit.MILLISECONDS.sleep(130);
        //}

        //for(int i=0; i<10; i++) {
        //    trollface.Worm(rand.nextInt(1900), rand.nextInt(1000));
        //    TimeUnit.MILLISECONDS.sleep(100);
        //}
        int c = 0;
        while(true){
            c+=1;
            if(c%1000==0){
                trollface.UpdateImage();
            }
            trollface.Worm(MouseInfo.getPointerInfo().getLocation().x, MouseInfo.getPointerInfo().getLocation().y);
            //TimeUnit.MILLISECONDS.sleep(1);
        }

        //for(int i=0; i<40; i++){
        //    trollface.limbo(p);
        //    TimeUnit.MILLISECONDS.sleep(130);
        //}
    }

}