package de.lessvoid.font;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.glu.GLU;

public class FontTest
{
  private static Log log= LogFactory.getLog( FontTest.class );
  
  private static Font font;
  
  private static void init()
  {
    font= new Font();
    font.init( "verdana-48-regular.fnt" );

    // init 2d
    GL11.glViewport( 0, 0, Display.getDisplayMode().getWidth(), Display.getDisplayMode().getHeight());

    GL11.glMatrixMode( GL11.GL_PROJECTION );
      GL11.glLoadIdentity();
      GL11.glOrtho( 0, Display.getDisplayMode().getWidth(), Display.getDisplayMode().getHeight(), 0, -9999, 9999 );

    GL11.glMatrixMode( GL11.GL_MODELVIEW );
      GL11.glLoadIdentity();

    // Prepare Rendermode
    GL11.glDisable( GL11.GL_DEPTH_TEST );
    GL11.glDisable( GL11.GL_BLEND );
    GL11.glDisable( GL11.GL_CULL_FACE );

    GL11.glEnable( GL11.GL_ALPHA_TEST );
    GL11.glAlphaFunc( GL11.GL_NOTEQUAL, 0 );

    GL11.glDisable( GL11.GL_LIGHTING );
    GL11.glEnable( GL11.GL_TEXTURE_2D );

    GL11.glEnable( GL11.GL_BLEND );
    GL11.glBlendFunc( GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA );
  }
  
  private static void frame()
  {
    GL11.glClearColor( 0.2f, 0.0f, 0.0f, 1.0f );
    GL11.glClear( GL11.GL_COLOR_BUFFER_BIT );
    
    // GL11.glColor3f( (float)Math.random(), (float)Math.random(), (float)Math.random() );
    GL11.glColor4f( 1.0f, 1.0f, 1.0f, 1.0f );
    font.drawString(
        100, // (int)(Math.random() * Display.getDisplayMode().getWidth()),
        100, // (int)(Math.random() * Display.getDisplayMode().getHeight()),
        "Hallo Welt!" );
    
    font.setSelectionStart(2);
    font.setSelectionEnd(4);
    font.renderWithSizeAndColor( 
        200, // (int)(Math.random() * Display.getDisplayMode().getWidth()),
        200, // (int)(Math.random() * Display.getDisplayMode().getHeight()),
        "Hallo Welt!", 1.0f, 0.0f, 1.0f, 0.0f, 0.5f );

  }
  
  
  /**
   * @param args
   */
  public static void main( String[] args )
  {
    // init graphics
    if( !initGraphics())
      return;

    // init input system
    initInput();

    // init the test code
    init();
    
    // wait for user to close window
    while( !Display.isCloseRequested() )
    {
      // frame
      frame();
      
      // show render
      Display.update();

      int error= GL11.glGetError();
      if( error != GL11.GL_NO_ERROR )
      {
        String glerrmsg= GLU.gluErrorString( error );
        log.error( "OpenGL Error: (" + error + ") " + glerrmsg );
      }

      // check keys, buffered
      Keyboard.poll();

      while( Keyboard.next())
      {
        if( Keyboard.getEventKeyState() && Keyboard.getEventKey() == Keyboard.KEY_ESCAPE )
        {
          return;
        }
      }
    }

    // nuke window and get out
    Display.destroy();
  }

    
  /**
   * 
   *
   */
  private static boolean initGraphics()
  {
    try
    {
      //  get avaialble modes, and print out
      DisplayMode[] modes= Display.getAvailableDisplayModes();
      log.debug( "Found " + modes.length + " display modes" );

      int x= 0, y= 0;
      boolean fullscreen= false;
      log.debug( "Moving to 100, 100" );
      Display.setLocation( x, y );

      // Create the actual window
      try
      {
        setDisplayMode();
        Display.setFullscreen( fullscreen );
        Display.create();
      }
      catch( Exception e )
      {
        e.printStackTrace();
        log.error( "Unable to create window!, exiting..." );
        System.exit( -1 );
      }

      log.debug( "Window created" );
      log.debug( "Width: " + Display.getDisplayMode().getWidth() + ", Height: " + Display.getDisplayMode().getHeight() + ", Bits per pixel: "
          + Display.getDisplayMode().getBitsPerPixel() + ", Frequency: " + Display.getDisplayMode().getFrequency() + ", Title: " + Display.getTitle() );

      Display.setVSyncEnabled( true );
      Display.setTitle( "Fragment Shader Scroller Demonstration Disaster :)" );
      return true;
    }
    catch( LWJGLException e )
    {
      e.printStackTrace();
      return false;
    }
  }

  /**
   * 
   *
   */
  protected static void initInput()
  {
    try
    {
      Keyboard.create();
    }
    catch( Exception e )
    {
      e.printStackTrace();
      log.error( "Unable to create keyboard!, exiting..." );
      System.exit( -1 );
    }
  }

  /**
   * Sets the display mode for fullscreen mode
   */
  protected static boolean setDisplayMode()
  {
    try
    {
      // get modes
      DisplayMode[] dm= org.lwjgl.util.Display.getAvailableDisplayModes( 1024, 768, -1, -1, 32, 32, 75, 85 );
      org.lwjgl.util.Display.setDisplayMode(
          dm,
          new String[] { "width=" + 640, "height=" + 480, "freq=" + 60, "bpp=" + org.lwjgl.opengl.Display.getDisplayMode().getBitsPerPixel() } );
      return true;
    }
    catch( Exception e )
    {
      e.printStackTrace();
    }

    return false;
  }
 

}