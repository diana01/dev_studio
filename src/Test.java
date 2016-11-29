import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.nio.FloatBuffer;

import com.jogamp.opengl.DebugGL3;
import com.jogamp.opengl.GL3;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLJPanel;
import com.jogamp.opengl.fixedfunc.GLMatrixFunc;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.util.Animator;
import com.jogamp.opengl.util.PMVMatrix;

public class Test implements GLEventListener {
    private final JFrame frame;
    private final Animator animator;
    private final GLJPanel panel;
    private final Dimension dim = new Dimension(1024, 768);
    private double t0 = System.currentTimeMillis();
    private float theta;

    private int shaderProgram;
    private int vertShader;
    private int fragShader;
    private int modelViewProjectionMatrixLocation;
    private int transformMatrixLocation;

    private static final int locPos = 1;
    private static final int locCol = 2;
    private static final boolean useVao = false;
//  private static final boolean useVao = true;

    private int[] vbo;
    private int[] vao;

    public static void main(String[] s){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Test();
            }
        });
    }

    public Test() {
        frame = new JFrame(this.getClass().getSimpleName());
        frame.setLayout(new BorderLayout());
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                animator.stop();
                System.exit(0);
            }
        });

        GLProfile profile = GLProfile.get(GLProfile.GL3);
            panel = new GLJPanel(new GLCapabilities(profile));
        panel.addGLEventListener(this);
        panel.setPreferredSize(dim);
        frame.add(panel, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
        animator = new Animator(panel);
        animator.start();
    }

    @Override
    public void init(GLAutoDrawable drawable) {
        GL3 gl = drawable.getGL().getGL3();
        drawable.setGL(new DebugGL3(gl));

        gl.glClearColor(1, 1, 1, 1);
        gl.glClearDepth(1.0f);

        System.err.println("Chosen GLCapabilities: " + drawable.getChosenGLCapabilities());
        System.err.println("INIT GL IS: " + gl.getClass().getName());
        System.err.println("GL_VENDOR: " + gl.glGetString(GL3.GL_VENDOR));
        System.err.println("GL_RENDERER: " + gl.glGetString(GL3.GL_RENDERER));
        System.err.println("GL_VERSION: " + gl.glGetString(GL3.GL_VERSION));

        vertShader = gl.glCreateShader(GL3.GL_VERTEX_SHADER);
        fragShader = gl.glCreateShader(GL3.GL_FRAGMENT_SHADER);

        String[] vlines = new String[] { vertexShaderString };
        int[] vlengths = new int[] { vlines[0].length() };
        gl.glShaderSource(vertShader, vlines.length, vlines, vlengths, 0);
        gl.glCompileShader(vertShader);

        int[] compiled = new int[1];
        gl.glGetShaderiv(vertShader, GL3.GL_COMPILE_STATUS, compiled, 0);
        if(compiled[0] != 0) {
            System.out.println("Vertex shader compiled");
        } else {
            int[] logLength = new int[1];
            gl.glGetShaderiv(vertShader, GL3.GL_INFO_LOG_LENGTH, logLength, 0);

            byte[] log = new byte[logLength[0]];
            gl.glGetShaderInfoLog(vertShader, logLength[0], (int[])null, 0, log, 0);

            System.err.println("Error compiling the vertex shader: " + new String(log));
            System.exit(1);
        }

        String[] flines = new String[] { fragmentShaderString };
        int[] flengths = new int[] { flines[0].length() };
        gl.glShaderSource(fragShader, flines.length, flines, flengths, 0);
        gl.glCompileShader(fragShader);

        gl.glGetShaderiv(fragShader, GL3.GL_COMPILE_STATUS, compiled, 0);
        if(compiled[0] != 0){
            System.out.println("Fragment shader compiled.");
        } else {
            int[] logLength = new int[1];
            gl.glGetShaderiv(fragShader, GL3.GL_INFO_LOG_LENGTH, logLength, 0);

            byte[] log = new byte[logLength[0]];
            gl.glGetShaderInfoLog(fragShader, logLength[0], (int[])null, 0, log, 0);

            System.err.println("Error compiling the fragment shader: " + new String(log));
            System.exit(1);
        }

        shaderProgram = gl.glCreateProgram();
        gl.glAttachShader(shaderProgram, vertShader);
        gl.glAttachShader(shaderProgram, fragShader);

        gl.glBindAttribLocation(shaderProgram, locPos, "VertexPosition");
        gl.glBindAttribLocation(shaderProgram, locCol, "VertexColor");

        gl.glLinkProgram(shaderProgram);

        modelViewProjectionMatrixLocation = gl.glGetUniformLocation(shaderProgram, "uniform_Projection");
        System.out.println("modelViewProjectionMatrixLocation:" + modelViewProjectionMatrixLocation);
        transformMatrixLocation = gl.glGetUniformLocation(shaderProgram, "uniform_Transform");
        System.out.println("transformMatrixLocation:" + transformMatrixLocation);

        FloatBuffer interleavedBuffer = Buffers.newDirectFloatBuffer(vertices.length + colors.length);
        for(int i = 0; i < vertices.length/3; i++) {
            for(int j = 0; j < 3; j++) {
                interleavedBuffer.put(vertices[i*3 + j]);
            }
            for(int j = 0; j < 4; j++) {
                interleavedBuffer.put(colors[i*4 + j]);
            }
        }
        interleavedBuffer.flip();

        vao = new int[1];
        gl.glGenVertexArrays(1, vao , 0); // was 2
        gl.glBindVertexArray(vao[0]);
        vbo = new int[1];
        gl.glGenBuffers(1, vbo, 0); //was 2
        gl.glBindBuffer(GL3.GL_ARRAY_BUFFER, vbo[0]);
        gl.glBufferData(GL3.GL_ARRAY_BUFFER, interleavedBuffer.limit() * Buffers.SIZEOF_FLOAT, interleavedBuffer, GL3.GL_STATIC_DRAW);

        gl.glEnableVertexAttribArray(locPos);
        gl.glEnableVertexAttribArray(locCol);

            // 
        int stride = Buffers.SIZEOF_FLOAT * (3+4);

        long arg0 = Buffers.SIZEOF_FLOAT * 3;

            //suspicious 
        gl.glVertexAttribPointer( locPos, 3, GL3.GL_FLOAT, false, stride, 0);
        gl.glVertexAttribPointer( locCol, 4, GL3.GL_FLOAT, false, stride, arg0); 

        if(!useVao) { //added
          gl.glDisableVertexAttribArray(locPos);
          gl.glDisableVertexAttribArray(locCol);
        }
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        System.out.println("Window resized to width=" + width + " height=" + height);
        GL3 gl = drawable.getGL().getGL3();
        gl.glViewport(0, 0, width, height);
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        double t1 = System.currentTimeMillis();
        theta += (t1-t0)*0.005f;
        t0 = t1;

        GL3 gl = drawable.getGL().getGL3();
        gl.glClear(GL3.GL_COLOR_BUFFER_BIT | GL3.GL_DEPTH_BUFFER_BIT);

        gl.glUseProgram(shaderProgram);

        float[] instanceTransform0 = new float[16];
        float[] instanceTransform1 = new float[16];
        //I use PMVMatrix class in order to calculate model-view matrix.
        PMVMatrix mat0 = new PMVMatrix();
        mat0.glMatrixMode(GLMatrixFunc.GL_MODELVIEW);
        mat0.glLoadIdentity();
        mat0.glTranslatef(0.25f, 0f, 0f);
        mat0.glRotatef(15f*theta, 0.0f, 0.0f, 1.0f);
        mat0.glScalef(0.8f, 0.8f, 1f);
        mat0.glGetFloatv(GLMatrixFunc.GL_MODELVIEW, instanceTransform0, 0);

        PMVMatrix mat1 = new PMVMatrix();
        mat1.glMatrixMode(GLMatrixFunc.GL_MODELVIEW);
        mat1.glLoadIdentity();
        mat1.glTranslatef(-0.25f, 0f, 0f);
        mat1.glRotatef(10f*theta, 0.0f, 0.0f, 1.0f);
        mat1.glScalef(0.5f, 0.5f, 1f);
        mat1.glGetFloatv(GLMatrixFunc.GL_MODELVIEW, instanceTransform1, 0);

        //concatnate 2 float arrays.
        float[] instanceTransformConcatnated = concatnateArrays(instanceTransform0, instanceTransform1);

        gl.glUniformMatrix4fv(transformMatrixLocation, instanceTransformConcatnated.length, false, instanceTransformConcatnated , 0);

        if(useVao) {
            gl.glBindVertexArray(vao[0]);
        } else {
            gl.glEnableVertexAttribArray(locPos);
            gl.glEnableVertexAttribArray(locCol);
        }
        gl.glDrawArraysInstanced(GL3.GL_TRIANGLES, 0, 3, 2);
        if(useVao) {
            //EDIT: after removing the next line, the exception disappears.
            //gl.glBindVertexArray(0);
        } else {
            gl.glDisableVertexAttribArray(locPos);
            gl.glDisableVertexAttribArray(locCol);
        }
        gl.glUseProgram(0);
    }

    @Override
    public void dispose(GLAutoDrawable drawable){
        GL3 gl = drawable.getGL().getGL3();
        gl.glUseProgram(0);
        gl.glDeleteBuffers(2, vbo, 0);
        gl.glDetachShader(shaderProgram, vertShader);
        gl.glDeleteShader(vertShader);
        gl.glDetachShader(shaderProgram, fragShader);
        gl.glDeleteShader(fragShader);
        gl.glDeleteProgram(shaderProgram);
    }

    private float[] concatnateArrays(float[] arg0, float[] arg1) {
        float[] result = new float[arg0.length + arg1.length];
        System.arraycopy(arg0, 0, result, 0, arg0.length);
        System.arraycopy(arg1, 0, result, arg0.length, arg1.length);
        return result;
    }

    private final String vertexShaderString =
            "#version 330 \n" +
                    "\n" +
                    "uniform mat4 uniform_Projection; \n" + //not used
                    "uniform mat4 uniform_Transform[2]; \n" +
                    "in vec4  VertexPosition; \n" +
                    "in vec4  VertexColor; \n" +
                    "out vec4 tmpColor; \n" +
                    "void main(void) \n" +
                    "{ \n" +
                    "  tmpColor = VertexColor; \n" +
                    //"  gl_Position = uniform_Projection * VertexPosition; \n" +
                    "  gl_Position = uniform_Transform[gl_InstanceID] * VertexPosition; \n" +
                    "} ";

    private final String fragmentShaderString =
            "#version 330\n" +
                    "\n" +
                    "in vec4    tmpColor; \n" +
                    "out vec4   outColor; \n" +
                    "void main (void) \n" +
                    "{ \n" +
                    "  outColor = tmpColor; \n" +
                    "} ";

    private final float[] vertices = {
            1.0f, 0.0f, 0,
            -0.5f, 0.866f, 0,
            -0.5f, -0.866f, 0
    };

    private final float[] colors = {
            1.0f, 0.0f, 0.0f, 1.0f,
            0.0f, 1.0f, 0.0f, 1.0f,
            0f, 0f, 1.0f, 1f
    };

}