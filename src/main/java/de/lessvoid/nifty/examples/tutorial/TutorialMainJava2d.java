package de.lessvoid.nifty.examples.tutorial;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.java2d.input.InputSystemAwtImpl;
import de.lessvoid.nifty.java2d.renderer.RenderDeviceJava2dImpl;
import de.lessvoid.nifty.lwjglslick.sound.SlickSoundDevice;
import de.lessvoid.nifty.sound.SoundSystem;
import de.lessvoid.nifty.tools.TimeProvider;

public final class TutorialMainJava2d {

  public static void main(final String[] args) {
    InputSystemAwtImpl inputSystem = new InputSystemAwtImpl();

    Dimension dimension = new Dimension(1024, 768);

    final Canvas canvas = new Canvas();
    canvas.addMouseMotionListener(inputSystem);
    canvas.addMouseListener(inputSystem);

    Frame f = new Frame("Nifty Java 2d");
    f.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }

      public void windowDeiconified(WindowEvent e) {
      }

      public void windowIconified(WindowEvent e) {
      }
    });
    
    f.add(canvas);
    f.pack();
    f.setSize(dimension);
    f.setVisible(true);
    // f.setIgnoreRepaint(true);
    // canvas.setIgnoreRepaint(true);

    RenderDeviceJava2dImpl renderDevice = new RenderDeviceJava2dImpl(canvas);

    Nifty nifty = new Nifty(renderDevice, new SoundSystem(new SlickSoundDevice()), inputSystem, new TimeProvider());
    nifty.fromXml("tutorial/tutorial.xml", "start");

    boolean done = false;
    long time = System.currentTimeMillis();
    long frames = 0;
    while (!done) {
      done = nifty.render(false);
      frames++;

      long diff = System.currentTimeMillis() - time;
      if (diff >= 1000) {
        time += diff;
        System.out.println("fps: " + frames);
        frames = 0;
      }
    }

  }
}
