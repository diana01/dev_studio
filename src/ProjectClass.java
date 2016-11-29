
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL3;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLJPanel;
import com.jogamp.opengl.util.FPSAnimator;

public class ProjectClass implements GLEventListener {

	private static JFrame frame;
	private static FPSAnimator animator;
	private static GLJPanel panel;
	private static int program;
	private static int positionAttribute;
	private static ObjectLoader _obj;
	private static boolean objloaded;
	static float[] vertices = {
			0.0f,  0.5f, 
			0.5f, -0.5f,
			-0.5f, -0.5f  
	};

	IntBuffer vao;
	IntBuffer vbo;
	FloatBuffer vertBuffer;
	private int fragmentShader;
	private int vertexShader;

	private String fragProgramString = "#version 330" + "\n" +
			"out vec3 outColor;" + "\n" +
			"void main(){" + "\n" +
			"outColor = vec3(1.0, 0.0, 0.0);" + "\n}";

	private String vertProgramString = "#version 330" + "\n" +
			"in vec2 position;" + "\n" +
			"void main(){" + "\n" +
			"gl_Position = vec4(position, 0.0, 1.0);}";





	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new ProjectClass();
			}
		});

	}




	public ProjectClass() {
		frame = new JFrame("Computer Graphics and Image Processing");
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				animator.stop();
				frame.dispose();
				System.exit(0);
			}
		});

		GLProfile profile = GLProfile.get(GLProfile.GL3);
		JToolBar toolbarMenu = new JToolBar();
		toolbarMenu.add(newObject());
		frame.add(toolbarMenu, BorderLayout.NORTH);
		panel = new GLJPanel(new GLCapabilities(profile));
		panel.addGLEventListener(this);

		frame.add(panel, BorderLayout.CENTER);
		frame.pack();
		frame.setVisible(true);
		frame.setSize(700, 700);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		animator = new FPSAnimator(panel, 60, true);
		animator.start();

	}

	@Override
	public void init(GLAutoDrawable drawable) {
		System.out.println("init");
		GL3 gl = drawable.getGL().getGL3();

		initializeBuffers(gl);
		initializeVertexArrays(gl);

		createProgram(gl);
		gl.glClearColor(1f, 1f, 1f, 1f);
	}


	@Override
	public void display(GLAutoDrawable drawable) {
		//		System.out.println("display");

		GL3 gl = drawable.getGL().getGL3();
		gl.glClear(GL3.GL_COLOR_BUFFER_BIT);

		gl.glUseProgram(program);
		gl.glBindBuffer(GL3.GL_ARRAY_BUFFER, vbo.get(0));
//		positionAttribute = -1;
//		positionAttribute = gl.glGetAttribLocation(program, "position");
//		System.out.println("POSITION " + positionAttribute);

		gl.glEnableVertexAttribArray(positionAttribute);
		gl.glVertexAttribPointer(positionAttribute, 2, GL3.GL_FLOAT, false, 0, 0L);

		gl.glDrawArrays(GL3.GL_TRIANGLES, 0, 3);
		gl.glDisableVertexAttribArray(positionAttribute);
	}


	@Override
	public void dispose(GLAutoDrawable drawable) {
		System.out.println("dispose");
		GL3 gl = drawable.getGL().getGL3();
		gl.glUseProgram(0);
		gl.glDetachShader(program, vertexShader);
		gl.glDeleteShader(vertexShader);
		gl.glDetachShader(program, fragmentShader);
		gl.glDeleteShader(fragmentShader);
		gl.glDeleteProgram(program);
		gl.glDeleteBuffers(1, vbo);
		gl.glDeleteVertexArrays(1, vao);
	}

	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		GL3 gl = drawable.getGL().getGL3();
		gl.glViewport(0, 0, width, height);
	}

	private void initializeVertexArrays(GL3 gl) {
		vao = Buffers.newDirectIntBuffer(1);
		vbo = Buffers.newDirectIntBuffer(1);
		gl.glGenVertexArrays(1, vao);
		gl.glBindVertexArray(vao.get(0));
	}

	private void initializeBuffers(GL3 gl) {
		vertBuffer = Buffers.newDirectFloatBuffer(vertices);
		gl.glGenBuffers(1, vbo);
		gl.glBindBuffer(GL3.GL_ARRAY_BUFFER, vbo.get(0));       
		gl.glBufferData(GL3.GL_ARRAY_BUFFER, vertBuffer.limit() * Buffers.SIZEOF_FLOAT, vertBuffer, GL3.GL_STATIC_DRAW);     
	}

	private void createProgram(GL3 gl) {
		vertexShader = gl.glCreateShader(GL3.GL_VERTEX_SHADER);
		String[] vertProgram = new String[] {vertProgramString };
		gl.glShaderSource(vertexShader, 1, vertProgram, null);
		gl.glCompileShader(vertexShader);
		IntBuffer status = Buffers.newDirectIntBuffer(1);
		gl.glGetShaderiv(vertexShader, GL3.GL_COMPILE_STATUS, status);
		System.out.println(status.get(0)==GL3.GL_TRUE);

		fragmentShader = gl.glCreateShader(GL3.GL_FRAGMENT_SHADER);
		String[] fragProgram = new String[] {fragProgramString};
		gl.glShaderSource(fragmentShader, 1, fragProgram, null);
		gl.glCompileShader(fragmentShader);
		IntBuffer status2 = Buffers.newDirectIntBuffer(1);
		gl.glGetShaderiv(vertexShader, GL3.GL_COMPILE_STATUS, status2);
		System.out.println(status2.get(0)==GL3.GL_TRUE);

		program = gl.glCreateProgram();
		gl.glAttachShader(program, vertexShader);
		gl.glAttachShader(program, fragmentShader);
		gl.glBindFragDataLocation(program, 0, "outColor");
		gl.glLinkProgram(program);
	}

	private static JButton newObject(){
		ImageIcon newObjectIcon = new ImageIcon("icons/addObjectIcon.png");
		JButton newObject = new JButton(newObjectIcon);
		class ListenerItemMenu implements ActionListener{

			public void actionPerformed(ActionEvent e) {
				String userDir = System.getProperty("user.home");
				JFileChooser chooser = new JFileChooser(userDir +"/Desktop");
				chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				chooser.setAcceptAllFileFilterUsed(false);
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Object files", "obj");
				chooser.addChoosableFileFilter(filter);
				chooser.setVisible(true);
				chooser.showOpenDialog(null);
				File file = chooser.getSelectedFile();
				_obj = new ObjectLoader();
				//				try {
				//					_obj.loadFile(file);
				//					vertices = _obj.getVertices();
				//					objloaded = true;
				//				} catch (IOException e1) {
				//					e1.printStackTrace();
				//				}
			}	
		}
		newObject.addActionListener(new ListenerItemMenu());
		return newObject;	
	}
}

