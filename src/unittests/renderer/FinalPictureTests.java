package unittests.renderer;

import geometries.*;
import lighting.*;
import org.junit.jupiter.api.Test;
import primitives.*;
import renderer.*;
import scene.Scene;

import static java.awt.Color.*;

/**
 * Class FinalPictureTests is used for rendering pictures for
 * the final project submission.
 * @author Rivka Sheiner
 */
public class FinalPictureTests {

    @Test
    public void picture1ForMP1(){

        Scene scene = new Scene("MP1 picture1 scene").setBackground(new Color(0,200,216));
        Camera camera1 = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0),1.5,800,true,81) //
                .setVPSize(200, 200).setVPDistance(1000).setNumThreads(3);


        scene.geometries.add(
                new Cone(30, 22, new Point(0,65,-100))
                        .setEmission(new Color(216,105,41))
                        .setMaterial(new Material().setKd(0.3)), // roof of the tower
                new Cylinder(new Ray(new Point(0,32,-100), new Vector(0,1,0)), 22, 67)
                        .setEmission(new Color(0,0,200))
                        .setMaterial(new Material().setKs(0.2)), // tower

                new Square(new Point(-5,0,100), new Point(5,0,100),new Point(5,15,100),new Point(-5,15,100))
                        .setEmission(new Color(222,128,96)), // door
                new Circle(new Point(0,15,100), new Vector(0,0,1),5)
                        .setEmission(new Color(222,128,96)), // door


                new Square(new Point(150,0,-100), new Point(150,-150,-100), new Point(-150,-150,-100),
                        new Point(-150,0,-100))
                        .setEmission(new Color(41,208,30))
                        .setMaterial(new Material().setKs(0.6).setShininess(60)), // green plane- grass

                new Sphere(new Point(-50,85,-100), 15)
                        .setEmission(new Color(255,242,0))
                        .setMaterial(new Material().setKt(0.7)),// yellow sun

                new Sphere(new Point(-50,85,-100), 10)
                        .setEmission(new Color(orange))
                        .setMaterial(new Material().setKt(0.7)),// yellow sun

                new Sphere(new Point(-50,85,-100), 5)
                        .setEmission(new Color(red))// yellow sun


        );

        // adding man to the scene
        scene.geometries.add(new Sphere(new Point(-60,25,100),8)
                        .setEmission(new Color(240,208,189)), // head
                new Triangle(new Point(-60,45,110), new Point(-50,29,110),new Point(-70,29,110) )
                        .setEmission(new Color(22,193,70)), // hut

                new Circle(new Point(-63,27,110), new Vector(0,0,1), 1.5), // left eye
                new Circle(new Point(-57,27,110), new Vector(0,0,1), 1.5), // right eye
                new Square(new Point(-62,22,110), new Point(-58,22,110),new Point(-58,20.5,110),new Point(-62,20.5,110))
                        .setEmission(new Color(236,28,36)), // mouth

                new Square(new Point(-63,18,110),new Point(-65,14,110),new Point(-80,5,110), new Point(-80,9,110))
                        .setEmission(new Color(22,193,70)), // left hand

                new Square(new Point(-57,18,110),new Point(-55,14,110),new Point(-40,5,110), new Point(-40,9,110))
                        .setEmission(new Color(22,193,70)), // right hand

                new Sphere(new Point(-40,14,115),5)
                        .setEmission(new Color(red))
                        .setMaterial(new Material().setKt(0.6)),
                new Sphere(new Point(-40,14,115),3)
                        .setEmission(new Color(blue)),

                new Square(new Point(-63,18,110),new Point(-57,18,110),new Point(-45,-5,110), new Point(-75,-5,110))
                        .setEmission(new Color(22,193,70)), // shirt

                new Square(new Point(-63,-5,110), new Point(-67,-5,110), new Point(-67,-9,110), new Point(-63,-9,110)), // left leg
                new Square(new Point(-57,-5,110), new Point(-53,-5,110), new Point(-53,-9,110), new Point(-57,-9,110)) // right leg
        );

        // adding tree to the scene
        scene.geometries.add(new Square(new Point(64,28,110),new Point(56,28,110),new Point(56,-5,110), new Point(64,-5,110))
                        .setEmission(new Color(179,91,31)),
                new Circle(new Point(60,36,115), new Vector(0,0,-1), 17)
                        .setEmission(new Color(24,200,73))
                        .setMaterial(new Material().setKs(0.1)));

        // adding oranges to the tree
        for(int i = 0; i < 2; i++){
            for(int j = 0; j< 3; j++){
                scene.geometries.add(new Circle(new Point(60+7*i,30+7*j,120), new Vector(0,0,-1), 2)
                                .setEmission(new Color(orange)).setMaterial(new Material().setKd(0.1)),
                        new Circle(new Point(60-7*i,30+7*j,120), new Vector(0,0,-1), 2)
                                .setEmission(new Color(orange)).setMaterial(new Material().setKt(0.1)));
            }
        }



        // adding trees to the scene
        scene.geometries.add(new Square(new Point(90,28,-710),new Point(80,28,-710),new Point(80,-5,-710), new Point(90,-5,-710))
                        .setEmission(new Color(179,91,31)),
                new Circle(new Point(85,36,-705), new Vector(0,0,-1), 17)
                        .setEmission(new Color(24,200,73))
                        .setMaterial(new Material().setKs(0.1)));

        scene.geometries.add(new Square(new Point(50,28,-510),new Point(40,28,-510),new Point(40,-5,-510), new Point(50,-5,-510))
                        .setEmission(new Color(179,91,31)),
                new Circle(new Point(45,36,-505), new Vector(0,0,-1), 17)
                        .setEmission(new Color(24,200,73))
                        .setMaterial(new Material().setKs(0.1)));

        scene.geometries.add(new Square(new Point(130,28,-410),new Point(120,28,-410),new Point(120,-5,-410), new Point(130,-5,-410))
                        .setEmission(new Color(179,91,31)),
                new Circle(new Point(125,36,-405), new Vector(0,0,-1), 17)
                        .setEmission(new Color(24,200,73))
                        .setMaterial(new Material().setKd(0.1))
        );


        int x = 6;
        int y = 5;
        // loop for adding windows to the tower
        for(int i = 0; i < 2; i++){
            y+=15;

            scene.geometries.add(new Square(new Point(2*x,y,200), new Point(x-2,y,200),new Point(x-2,y+10,200),new Point(2*x,y+10,200))
                    .setEmission(new Color(219,244,243)));

            scene.geometries.add(new Square(new Point(-2*x,y,200), new Point(-x+2,y,200),new Point(-x+2,y+10,200),new Point(-2*x,y+10,200))
                    .setEmission(new Color(219,244,243)));
        }


        // adding stairs before the tower
        int stairX = -5, stairY = -2;
        for(int i = 0; i < 14 ; i++){
            scene.geometries.add(new Square(new Point(stairX,stairY,100), new Point(stairX+10,stairY,100), new Point(stairX+8,stairY-4,100), new Point(stairX-2,stairY-4,100))
                    .setEmission(new Color(216,105,41)));
            stairX -= 5;
            stairY -= 6;
        }



        scene.lights.add( new SpotLight(new Color(200, 0, 280), new Point(-100, -100, 500), new Vector(-1, -1, -2)) //
                        .setKl(0.0004).setKq(0.0000006));
        scene.lights.add(new DirectionalLight(new Color(100,0,100),new Vector(0,0,-1)));
        scene.lights.add(new PointLight(new Color(0,200,20), new Point(70,100,250)));

        scene.lights.add(new PointLight(new Color(255,242,0), new Point(150,100,0)));


        camera1.setImageWriter(new ImageWriter("picture1MP1", 500, 500)) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage() //
                .writeToImage();
    }


    @Test
    public void picture2ForMP1() {
        Scene scene2 = new Scene("MP1 picture2 scene").setBackground(new Color(13, 38, 122));
        Camera camera2 = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0), 1.5, 500, true, 81) //
                .setVPSize(300, 300).setVPDistance(1000).setNumThreads(3);


        // adding spaceship to the scene
        scene2.geometries.add(new Cylinder(new Ray(new Point(0,30,0), new Vector(0,1,0)),15,70)
                        .setEmission(new Color(orange)),
                new Cone(30, 15, new Point(0,65,0))
                        .setEmission(new Color(red)).setMaterial(new Material().setKs(0.7)),
                new Square(new Point(12,0,0), new Point(-12,0,0),new Point(-12,-5,0), new Point(12,-5,0))
                        .setEmission(new Color(red)),
                new Square(new Point(7,-10,0), new Point(-7,-10,0),new Point(-7,-5,0), new Point(7,-5,0))
                        .setEmission(new Color(gray)),
                new Triangle(new Point(7,-10,0), new Point(-7,-10,0), new Point(0,-40,0))
                        .setEmission(new Color(yellow)),
                new Triangle(new Point(7,-10,0), new Point(-7,-10,0), new Point(12,-25,0))
                        .setEmission(new Color(yellow)),
                new Triangle(new Point(7,-10,0), new Point(-7,-10,0), new Point(-12,-25,0))
                        .setEmission(new Color(yellow)),
                new Triangle(new Point(15,30,0), new Point(15,10,0), new Point(30,-5,0))
                        .setEmission(new Color(red)),
                new Triangle(new Point(-15,30,0), new Point(-15,10,0), new Point(-30,-5,0))
                        .setEmission(new Color(red)),
                new Circle(new Point(0,50,30), new Vector(0,0,1), 9)
                        .setEmission(new Color(171,232,244)),
                new Circle(new Point(0,50,30), new Vector(0,0,1), 11,3)
                        .setEmission(new Color(white))
        );

        scene2.lights.add(new SpotLight(new Color(255,242,0), new Point(0,110,0), new Vector(0,0,-1)));


        int x = 230, z = -1000,eps = -3;
        for(int i = 0; i < 2; i++){
            // adding spaceship to the scene
            scene2.geometries.add(
                    new Cylinder(new Ray(new Point(x,30,z), new Vector(0,1,0)),15,70)
                            .setEmission(new Color(orange)),
                    new Cone(30, 15, new Point(0+x,65,0+z))
                            .setEmission(new Color(red)),
                    new Square(new Point(12+x,0,0+z), new Point(-12+x,0,0+z),new Point(-12+x,-5,0+z), new Point(12+x,-5,0+z))
                            .setEmission(new Color(red)),
                    new Square(new Point(7+x,-10,0+z), new Point(-7+x,-10,0+z),new Point(-7+x,-5,0+z), new Point(7+x,-5,0+z))
                            .setEmission(new Color(gray)),
                    new Triangle(new Point(7+x,-10,0+z), new Point(-7+x,-10,0+z), new Point(0+x,-40,0+z))
                            .setEmission(new Color(yellow)),
                    new Triangle(new Point(7+x,-10,0+z), new Point(-7+x,-10,0+z), new Point(12+x,-25,0+z))
                            .setEmission(new Color(yellow)),
                    new Triangle(new Point(7+x,-10,0+z), new Point(-7+x,-10,0+z), new Point(-12+x,-25,0+z))
                            .setEmission(new Color(yellow)),
                    new Triangle(new Point(15+x,30,0+z), new Point(15+x,10,0+z), new Point(30+x,-5,0+z))
                            .setEmission(new Color(red)),
                    new Triangle(new Point(-15+x,30,0+z), new Point(-15+x,10,0+z), new Point(-30+x,-5,0+z))
                            .setEmission(new Color(red)),
                    new Circle(new Point(eps+x,50,30+z), new Vector(0,0,1), 9)
                            .setEmission(new Color(171,232,244)),
                    new Circle(new Point(eps+x,50,30+z), new Vector(0,0,1), 11,3)
                            .setEmission(new Color(white))
            );

            x = -230; z = -1300;eps = 3;
        }


        // adding planets to the sphere
        scene2.geometries.add(new Sphere(new Point(100,80,-20),20)
                        .setEmission(new Color(175,216,47))
                        .setMaterial(new Material().setKr(0.1)),
                new Circle(new Point(100,80,-20), new Vector(1,5,0), 40, 5)
                        .setEmission(new Color(88,88,88)));

        // the earth
        scene2.geometries.add(new Sphere(new Point(80,-10,-30),20)
                .setEmission(new Color(0,168,243))
                .setMaterial(new Material().setKr(0.1)));


        scene2.geometries.add(new Sphere(new Point(-80,60,-70),17)
                .setEmission(new Color(255,127,39))
                .setMaterial(new Material().setKr(0.1).setKs(0.4)));

        scene2.geometries.add(new Sphere(new Point(-100,-35,-60),20)
                        .setEmission(new Color(162,234,232)).setMaterial(new Material().setKr(0.1)),
                new Circle(new Point(-100,-35,-60), new Vector(-1,-3,0),35,5)
                        .setEmission(new Color(50,160,223)));

        scene2.lights.add(new PointLight(new Color(255,242,0), new Point(-110,125,-60)));
        scene2.geometries.add(new Sphere(new Point(-110,125,-60),20)
                .setEmission(new Color(255,0,24))
                .setMaterial(new Material().setKr(0.1).setKd(0.5)));

        // the sun
        scene2.geometries.add(new Sphere(new Point(-10,-100,60),40)
                        .setEmission(new Color(255,242,0))
                        .setMaterial(new Material().setKt(0.7).setKd(0.3)),
                new Sphere(new Point(-10,-100,60),32)
                        .setEmission(new Color(255,200,0))
                        .setMaterial(new Material().setKt(0.7)),
                new Sphere(new Point(-10,-100,60),20)
                        .setEmission(new Color(255,150,0)));

        scene2.lights.add(new SpotLight(new Color(255,42,0), new Point(-10,-30,60), new Vector(0,0,-1)));

        // the moon
        scene2.geometries.add(new Sphere(new Point(100,-120,20),25)
                .setEmission(new Color(234,234,234))
                .setMaterial(new Material().setKs(0.1)));



        // adding flag to the moon
        scene2.geometries.add(new Square(new Point(102,-95,20), new Point(104,-95,20)
                        , new Point(104,-85,20), new Point(102,-85,20))
                        .setEmission(new Color(231,105,33)),
                new Square(new Point(104,-85,20), new Point(104,-65,20),
                        new Point(80,-65,20), new Point(80,-85,20))
                        .setEmission(new Color(white)),
                new Triangle(new Point(98,-72,22), new Point(86,-72,22),new Point(92,-80,22))
                        .setEmission(new Color(36,49,234)),
                new Triangle(new Point(86,-78,22),new Point(98,-78,22), new Point(92,-70,22))
                        .setEmission(new Color(36,49,234)),
                new Square(new Point(80,-67,22), new Point(104,-67,22), new Point(104,-69,22),new Point(80,-69,22))
                        .setEmission(new Color(36,49,234)),
                new Square(new Point(80,-82,22), new Point(104,-82,22), new Point(104,-84,22),new Point(80,-84,22))
                        .setEmission(new Color(36,49,234)));


        scene2.lights.add(new SpotLight(new Color(200, 0, 280), new Point(-100, -100, 500), new Vector(-1, -1, -2)) //
                .setKl(0.0004).setKq(0.0000006));
        scene2.lights.add(new DirectionalLight(new Color(100, 0, 100), new Vector(0, 0, -1)));
        scene2.lights.add(new PointLight(new Color(0, 200, 20), new Point(70, 100, 250)));

        camera2.setImageWriter(new ImageWriter("picture2MP1", 500, 500)) //
                .setRayTracer(new RayTracerBasic(scene2)) //
                .renderImage() //
                .writeToImage();
    }


    @Test
    public void picture1MP2(){
        Scene scene3 = new Scene("MP2 scene").setBackground(new Color(0,200,216));
        Camera camera3 = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0),1.5,800,true,125) //
                .setVPSize(600, 600).setVPDistance(1000).setNumThreads(4);


        // adding middle path
        int x = 30, y = -20, z = -60;
        for(int i = 0; i < 5; i++){
            scene3.geometries.add(
                    new Square(new Point(x,y,z), new Point(-x,y,z), new Point(-x-10,y-40,z+40), new Point(x+10,y-40,z+40))
                            .setEmission(new Color(blue))
                            .setMaterial(new Material().setKd(0.5)));
            x+=10; y-=50; z+=55;
        }
        // adding winning gate at the end of the middle path
        scene3.geometries.add(new Square(new Point(30,-20,-60), new Point(20,-20,-60), new Point(20,60,-60), new Point(30,60,-60))
                        .setEmission(new Color(237,15,23)),
                new Square(new Point(-30,-20,-60), new Point(-20,-20,-60), new Point(-20,60,-60), new Point(-30,60,-60))
                        .setEmission(new Color(237,15,23)),
                new Square(new Point(30,70,-60), new Point(-30,70,-60), new Point(-30,60,-60), new Point(30,60,-60))
                        .setEmission(new Color(237,15,23)));
        for(int i = 0; i < 3; i++){
            scene3.geometries.add(new Triangle(new Point(30-20*i,70,-60), new Point(20-20*i,85,-60), new Point(10-20*i,70,-60))
                    .setEmission(new Color(237,15,23)));
        }

        // adding left path
        x = -100; y = -20; z = -60;
        for(int i = 0; i < 5; i++){
            scene3.geometries.add(
                    new Square(new Point(x,y,z), new Point(x-60,y,z), new Point(x-80,y-40,z+40), new Point(x-10,y-40,z+40))
                            .setEmission(new Color(blue))
                            .setMaterial(new Material().setKd(0.5)));
            x-=20; y-=50; z+=55;
        }

        // adding winning gate at the end of the left path
        scene3.geometries.add(new Square(new Point(-160,-20,-60), new Point(-150,-20,-60), new Point(-150,60,-60), new Point(-160,60,-60))
                        .setEmission(new Color(237,15,23)),
                new Square(new Point(-100,-20,-60), new Point(-110,-20,-60), new Point(-110,60,-60), new Point(-100,60,-60))
                        .setEmission(new Color(237,15,23)),
                new Square(new Point(-160,70,-60), new Point(-100,70,-60), new Point(-100,60,-60), new Point(-160,60,-60))
                        .setEmission(new Color(237,15,23)));
        for(int i = 0; i < 3; i++){
            scene3.geometries.add(new Triangle(new Point(-160+20*i,70,-60), new Point(-150+20*i,85,-60), new Point(-140+20*i,70,-60))
                    .setEmission(new Color(237,15,23)));
        }


        // adding right path
        x = 100; y = -20; z = -60;
        for(int i = 0; i < 5; i++){
            scene3.geometries.add(
                    new Square(new Point(x,y,z), new Point(x+60,y,z), new Point(x+80,y-40,z+40), new Point(x+10,y-40,z+40))
                            .setEmission(new Color(blue))
                            .setMaterial(new Material().setKd(0.5)));
            x+=20; y-=50; z+=55;
        }

        // adding winning gate at the end of the right path
        scene3.geometries.add(new Square(new Point(160,-20,-60), new Point(150,-20,-60), new Point(150,60,-60), new Point(160,60,-60))
                        .setEmission(new Color(237,15,23)),
                new Square(new Point(100,-20,-60), new Point(110,-20,-60), new Point(110,60,-60), new Point(100,60,-60))
                        .setEmission(new Color(237,15,23)),
                new Square(new Point(160,70,-60), new Point(100,70,-60), new Point(100,60,-60), new Point(160,60,-60))
                        .setEmission(new Color(237,15,23)));
        for(int i = 0; i < 3; i++){
            scene3.geometries.add(new Triangle(new Point(160-20*i,70,-60), new Point(150-20*i,85,-60), new Point(140-20*i,70,-60))
                    .setEmission(new Color(237,15,23)));
        }

        // adding balls on the middle path
        scene3.geometries.add(new Sphere(new Point(0,-10,-30),20)
                .setEmission(new Color(red)).setMaterial(new Material().setKd(0.5)));
        scene3.geometries.add(new Sphere(new Point(20,-120,70),20)
                .setEmission(new Color(blue)).setMaterial(new Material().setKd(0.5)));
        scene3.geometries.add(new Sphere(new Point(-30,-180,140),20)
                .setEmission(new Color(green)).setMaterial(new Material().setKd(0.5).setKt(0.3)));


        // adding balls on the left path
        scene3.geometries.add(new Sphere(new Point(-130,-10,-30),15)
                .setEmission(new Color(blue)).setMaterial(new Material().setKt(0.5).setKd(0.5)));
        scene3.geometries.add(new Sphere(new Point(-170,-120,70),15)
                .setEmission(new Color(green)).setMaterial(new Material().setKt(0.5).setKd(0.5)));
        scene3.geometries.add(new Sphere(new Point(-180,-180,140),15)
                .setEmission(new Color(red)).setMaterial(new Material().setKt(0.5).setKd(0.5)));

        // adding balls on the right path
        scene3.geometries.add(new Sphere(new Point(130,-10,-30),15)
                .setEmission(new Color(blue)).setMaterial(new Material().setKd(0.5)));
        scene3.geometries.add(new Sphere(new Point(170,-120,70),15)
                .setEmission(new Color(red)).setMaterial(new Material().setKd(0.5)));
        scene3.geometries.add(new Sphere(new Point(190,-180,140),15)
                .setEmission(new Color(green)).setMaterial(new Material().setKd(0.5).setKt(0.5)));


        // adding white clouds to the scene
        x = 220;
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 3; j++){
                scene3.geometries.add(new Sphere(new Point(x+20*j,200,-100),20)
                        .setEmission(new Color(white)));
                scene3.geometries.add(new Sphere(new Point(x-20*j,200,-100),20)
                        .setEmission(new Color(white)) );

                scene3.geometries.add(new Sphere(new Point(x-20*j,260,-100),20)
                        .setEmission(new Color(white)));
                scene3.geometries.add(new Sphere(new Point(x+20*j,260,-100),20)
                        .setEmission(new Color(white)));
            }
            x-=150;
        }

        // adding light sources to the scene
        scene3.lights.add(new DirectionalLight(new Color(100,0,100),new Vector(0,0,-1)));
        scene3.lights.add(new PointLight(new Color(0,200,20), new Point(70,100,250)));

        // light from the sky in the clouds
        scene3.lights.add(new SpotLight(new Color(yellow),new Point(0,200,-150), new Vector(0,-0,1)));

        scene3.lights.add(new PointLight(new Color(255,242,0), new Point(-130,100,-60)));
        scene3.lights.add(new PointLight(new Color(255,242,0), new Point(130,100,-60)));


        camera3.setImageWriter(new ImageWriter("picture1MP2", 500, 500)) //
                .setRayTracer(new RayTracerBasic(scene3)) //
                .renderImage() //
                .writeToImage();

    }


    @Test
    public void picture2MP2(){
        Scene scene4 = new Scene("MP2 scene").setBackground(new Color(89,6,78));
        Camera camera4 = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0),1.5,800,true,125) //
                .setVPSize(400, 400).setVPDistance(1000).setNumThreads(4);


        // Adding towers for the disks
        scene4.geometries.add(
                new Cylinder(new Ray(new Point(100,-70,10), new Vector(0,10,1)),4,100)
                        .setEmission(new Color(0,0,200)),
                new Cylinder(new Ray(new Point(-100,-70,10), new Vector(0,10,1)),4,100)
                        .setEmission(new Color(200,0,0)),
                new Cylinder(new Ray(new Point(0,-70,10), new Vector(0,10,1)),4,100)
                        .setEmission(new Color(0,150,0)));


        // adding a base to disk towers
        scene4.geometries.add(new Square(new Point(-150,-100,-200), new Point(180,-100,-200), new Point(130,-150,0), new Point(-170,-150,0))
                .setEmission(new Color(0,105,105))
                .setMaterial(new Material().setKd(0.1).setKs(0.1)));

        // Adding disks to the towers
        int delta = 50;
        for(int i = 0; i < 6; i++){
            scene4.geometries.add(new Circle(new Point(100,-100+13*i,20), new Vector(-1,10,1), 40-5*i)
                            .setEmission(new Color(delta * (i+1),0,200)).setMaterial(new Material().setKd(0.7)),
                    new Circle(new Point(-100,-100+13*i,20), new Vector(-1,10,1), 40-5*i)
                            .setEmission(new Color(200,delta * (i+1),0)).setMaterial(new Material().setKd(0.7)),
                    new Circle(new Point(0,-100+13*i,20), new Vector(-1,10,1), 40-5*i)
                            .setEmission(new Color(0,150,delta * (i+1))).setMaterial(new Material().setKd(0.7)));
        }


        Color brown = new Color(207,92,26);
        delta = 0;
        int zDif = 300;
        int yDif = 60;

        // adding 3 upper hot air balloons to the scene
        for(int i = 0; i < 3; i++){
            scene4.geometries.add(new Cylinder(new Ray(new Point(100-delta,70+yDif,0-zDif), new Vector(1,20,0)),10,25)
                            .setEmission(brown).setMaterial(new Material().setKd(0.1)),
                    new Sphere(new Point(100-delta,140+yDif,0-zDif),40)
                            .setEmission(new Color(red)).setMaterial(new Material().setKd(0.1).setKs(0.1).setKt(0.3)),
                    new Sphere(new Point(100-delta,140+yDif,0-zDif),30)
                            .setEmission(new Color(orange)).setMaterial(new Material().setKd(0.1).setKt(0.5)),
                    new Square(new Point(120-delta,110+yDif,0-zDif), new Point(117-delta,110+yDif,0-zDif), new Point(107-delta,70+yDif,0-zDif), new Point(110-delta,70+yDif,0-zDif))
                            .setEmission(brown).setMaterial(new Material().setKd(0.1)),
                    new Square(new Point(80-delta,110+yDif,0-zDif), new Point(83-delta,110+yDif,0-zDif), new Point(93-delta,70+yDif,0-zDif), new Point(90-delta,70+yDif,0-zDif))
                            .setEmission(brown).setMaterial(new Material().setKd(0.1)));

            delta +=(130+10*i);
            zDif+=50;
        }

        // adding 4 lower hot air balloons to the scene
        delta = -80; zDif = 900; yDif = 30;
        for(int i = 0; i < 4; i++){
            scene4.geometries.add(new Cylinder(new Ray(new Point(100-delta,70-yDif,0-zDif), new Vector(1,20,0)),10,25)
                            .setEmission(brown).setMaterial(new Material().setKd(0.1)),
                    new Sphere(new Point(100-delta,140-yDif,0-zDif),40)
                            .setEmission(new Color(26,38,157)).setMaterial(new Material().setKd(0.1).setKt(0.3)),
                    new Sphere(new Point(100-delta,140-yDif,0-zDif),30)
                            .setEmission(new Color(green)).setMaterial(new Material().setKd(0.1).setKt(0.3)),
                    new Sphere(new Point(100-delta,140-yDif,0-zDif),20)
                            .setEmission(new Color(orange)).setMaterial(new Material().setKd(0.1).setKt(0.3)),
                    new Square(new Point(120-delta,110-yDif,0-zDif), new Point(117-delta,110-yDif,0-zDif), new Point(107-delta,70-yDif,0-zDif), new Point(110-delta,70-yDif,0-zDif))
                            .setEmission(brown).setMaterial(new Material().setKd(0.1)),
                    new Square(new Point(80-delta,110-yDif,0-zDif), new Point(83-delta,110-yDif,0-zDif), new Point(93-delta,70-yDif,0-zDif), new Point(90-delta,70-yDif,0-zDif))
                            .setEmission(brown).setMaterial(new Material().setKd(0.1)));

            delta +=150;
            zDif-=50;
        }


        scene4.lights.add(new PointLight(new Color(255,202,0),new Point(0,140,100)));
        scene4.lights.add(new DirectionalLight(new Color(100,0,100),new Vector(0,0,-1)));
        scene4.lights.add(new SpotLight(new Color(255,242,0), new Point(100,10,100), new Vector(0,0,-1)));
        scene4.lights.add(new SpotLight(new Color(255,242,0), new Point(-100,10,100), new Vector(0,0,-1)));


        camera4.setImageWriter(new ImageWriter("picture2MP2", 500, 500)) //
                .setRayTracer(new RayTracerBasic(scene4))
                .renderImage() //
                .writeToImage();
    }


}

