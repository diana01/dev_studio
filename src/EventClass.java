import com.jogamp.opengl.GL3;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;

public class EventClass implements GLEventListener {

	@Override
	public void display(final GLAutoDrawable drawable) {
		System.out.println("display");
		GL3 gl3 = drawable.getGL().getGL3();
		gl3.glClearColor(0.827f, 0.827f, 0.827f, 1f);
        gl3.glClearDepthf(1f);
        gl3.glClear(GL3.GL_COLOR_BUFFER_BIT | GL3.GL_DEPTH_BUFFER_BIT);
	}

	@Override
	public void dispose(final GLAutoDrawable drawable) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init(final GLAutoDrawable drawable) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reshape(final GLAutoDrawable drawable, final int x, final int y, final int width, final int height) {
		// TODO Auto-generated method stub
		
	}

}
