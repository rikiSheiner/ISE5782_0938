package unittests.renderer;

import geometries.*;
import lighting.DirectionalLight;
import lighting.PointLight;
import lighting.SpotLight;
import org.junit.jupiter.api.Test;
import primitives.*;
import renderer.Camera;
import renderer.ImageWriter;
import renderer.RayTracerBasic;
import scene.Scene;

import static java.awt.Color.*;
import static java.awt.Color.orange;

/**
 * Class FinalPictureTests is used for rendering pictures for
 * the final project submission.
 * @author Rivka Sheiner
 */
public class FinalPictureTests {

    @Test
    public void picture1ForMP1(){

        Scene scene = new Scene("MP1 picture1 scene").setBackground(new Color(0,200,216));
        Camera camera1 = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0),1.5,800,true,125) //
                .setVPSize(200, 200).setVPDistance(1000).setNumThreads(3);


        scene.geometries.add(
                new Cone(30, 22, new Point(0,65,-100))
                        .setEmission(new Color(216,105,41)), // roof of the tower
                new Cylinder(new Ray(new Point(0,0,-100), new Vector(0,-1,0)), 20, 60)
                        .setEmission(new Color(0,0,200)), // tower

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
                        .setEmission(new Color(24,200,73)));

        // adding oranges to the tree
        for(int i = 0; i < 2; i++){
            for(int j = 0; j< 3; j++){
                scene.geometries.add(new Circle(new Point(60+7*i,30+7*j,120), new Vector(0,0,-1), 2)
                                .setEmission(new Color(orange)),
                        new Circle(new Point(60-7*i,30+7*j,120), new Vector(0,0,-1), 2)
                                .setEmission(new Color(orange)));
            }
        }


        // adding trees to the scene
        scene.geometries.add(new Square(new Point(90,28,-710),new Point(80,28,-710),new Point(80,-5,-710), new Point(90,-5,-710))
                        .setEmission(new Color(179,91,31)),
                new Circle(new Point(85,36,-705), new Vector(0,0,-1), 17)
                        .setEmission(new Color(24,200,73)));

        scene.geometries.add(new Square(new Point(50,28,-510),new Point(40,28,-510),new Point(40,-5,-510), new Point(50,-5,-510))
                        .setEmission(new Color(179,91,31)),
                new Circle(new Point(45,36,-505), new Vector(0,0,-1), 17)
                        .setEmission(new Color(24,200,73)));

        scene.geometries.add(new Square(new Point(130,28,-410),new Point(120,28,-410),new Point(120,-5,-410), new Point(130,-5,-410))
                        .setEmission(new Color(179,91,31)),
                new Circle(new Point(125,36,-405), new Vector(0,0,-1), 17)
                        .setEmission(new Color(24,200,73)));


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

        camera1.setImageWriter(new ImageWriter("picture1MP1", 500, 500)) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage() //
                .writeToImage();
    }

    @Test
    public void picture2ForMP1() {
        Scene scene2 = new Scene("MP1 picture2 scene").setBackground(new Color(13, 38, 122));
        Camera camera2 = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0), 1.5, 600, true, 125) //
                .setVPSize(300, 300).setVPDistance(1000).setNumThreads(3);


        // adding spaceship to the scene
        scene2.geometries.add(new Cylinder(new Ray(new Point(0,0,0), new Vector(0,1,0)),15,65)
                        .setEmission(new Color(orange)),
                new Cone(30, 15, new Point(0,65,0))
                        .setEmission(new Color(red)),
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

        scene2.geometries.add(new Sphere(new Point(-110,125,-60),20)
                .setEmission(new Color(255,0,24))
                .setMaterial(new Material().setKd(0.1).setKr(0.1)));

        // the sun
        scene2.geometries.add(new Sphere(new Point(-10,-100,60),40)
                        .setEmission(new Color(255,242,0))
                        .setMaterial(new Material().setKt(0.7)),
                new Sphere(new Point(-10,-100,60),32)
                        .setEmission(new Color(255,200,0))
                        .setMaterial(new Material().setKt(0.7)),
                new Sphere(new Point(-10,-100,60),20)
                        .setEmission(new Color(255,150,0)));


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



}

