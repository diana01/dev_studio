/**
 * 
 */


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.jogamp.nativewindow.WindowClosingProtocol.WindowClosingMode;
import com.jogamp.newt.Display;
import com.jogamp.newt.NewtFactory;
import com.jogamp.newt.Screen;
import com.jogamp.newt.event.WindowAdapter;
import com.jogamp.newt.event.WindowEvent;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.GL3;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.Animator;

public class Main {
	
	protected static final int DEFAULT_WIDTH = 800;
	protected static final int DEFAULT_HEIGHT = 560;
	
	static boolean objloaded;
	private static ObjectLoader _obj;
	private static GLWindow window;
	private static Animator animator;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Display display = NewtFactory.createDisplay(null);
		Screen screen = NewtFactory.createScreen(display, 0);
		GLProfile glp = GLProfile.get(GLProfile.GL3);
		GLCapabilities caps = new GLCapabilities(glp);
		window = GLWindow.create(screen, caps);
		window.setTitle("Computer Graphics and Image Processing Project");
		window.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		window.setUndecorated(false);
        window.setAlwaysOnTop(false);
        window.setFullscreen(false);
        window.setPointerVisible(true);
        window.confinePointer(false);
        window.setVisible(true);
		window.setDefaultCloseOperation(WindowClosingMode.DISPOSE_ON_CLOSE);
		
		EventClass event = new EventClass();
	    window.addGLEventListener(event);
	    animator = new Animator(window);
	    animator.start();  
	    
	    window.addWindowListener(new WindowAdapter() {
            @Override
            public void windowDestroyNotify(final WindowEvent e) {
                System.out.println("GLWindow.destroyNotify");
            }

            @Override
            public void windowDestroyed(final WindowEvent e) {
                System.out.println("GLWindow.destroyed");
                animator.stop();
            }
        });
	    
	        
	        
//		JPanel toolbarPanel = new JPanel();
//		ImageIcon addObjectIcon = new ImageIcon("icons/addObjectIcon.png");
//		JButton addObjectButton = new JButton(addObjectIcon);
//		toolbarPanel.add(addObjectButton);
//		
//		
//		JPanel mainPanel = new JPanel();
//		mainPanel.setBackground(Color.WHITE);
//		frame.add(toolbarPanel, BorderLayout.NORTH);
//		frame.add(mainPanel, BorderLayout.CENTER);

	       
	}



//	private static void initialState() {
//		objloaded = false;
//		_obj = new ObjectLoader();
//	}
	
	

}
