package com.sample;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import java.awt.*;
import java.awt.event.*;

/**
 * This is a sample class to launch a rule.
 */
public class DroolsTest {

    public static final void main(String[] args) {
        try {
            // load up the knowledge base
	        KieServices ks = KieServices.Factory.get();
    	    KieContainer kContainer = ks.getKieClasspathContainer();
        	KieSession kSession = kContainer.newKieSession("ksession-rules");

            // go !
            kSession.fireAllRules();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
    
    public static class Okienko extends Frame implements WindowListener{
    	
    	public static final int NIE = 0;
    	public static final int TAK = 1;
    	public static final int NIEWIEM = 2;
    	
    	private Panel tekst = new Panel();
    	private Panel przyciski = new Panel();
    	private Label pytanie = new Label();
    	private Button lewy = new Button();
    	private Button prawy = new Button();
    	
    	private int odp = NIEWIEM;
    	
    	public int getOdp() {
    		return this.odp;
    	}
    	
    	public void setOdp(int temp) {
    		this.odp = temp;
    	}
    	
    	public String getQuestion() {
    		return pytanie.getText();
    	}
    	
    	public void nextQuestion(String l, String p, String q) {
    		pytanie.setText(q);
    		lewy.setLabel(l);
    		prawy.setLabel(p);
    	}
    	
    	public Okienko(String l, String p, String q) {
    		setLayout(new FlowLayout());
    		pytanie.setText(q);
    		lewy.setLabel(l);
    		prawy.setLabel(p);
    		lewy.setEnabled(true);
    		lewy.addActionListener(new ClickListenerleft());
    		prawy.setEnabled(true);
    		prawy.addActionListener(new ClickListenerright());
    		setTitle("choose_your_job");
    		setSize(250, 100);
    		setVisible(true);
    		add(tekst);
    		add(pytanie);
    		add(przyciski);
    		add(lewy);
    		add(prawy);
    		addWindowListener(this);
    	}
    	
    	public class ClickListenerleft implements ActionListener{
    		@Override
    		public void actionPerformed(ActionEvent evt) {
    			odp = TAK;
    			System.out.println(odp);
    		}
    	}
    	
    	public class ClickListenerright implements ActionListener{
    		@Override
    		public void actionPerformed(ActionEvent evt) {
    			odp = NIE;
    			System.out.println(odp);
    		}
    	}
    	
    	@Override
    	public void windowClosing(WindowEvent evt) {
    	   System.exit(0);  // Terminate the program
    	}
    	
    	@Override public void windowOpened(WindowEvent evt) { }
    	@Override public void windowClosed(WindowEvent evt) { }
    	// For Debugging
    	@Override public void windowIconified(WindowEvent evt) { System.out.println("Window Iconified"); }
    	@Override public void windowDeiconified(WindowEvent evt) { System.out.println("Window Deiconified"); }
    	@Override public void windowActivated(WindowEvent evt) { System.out.println("Window Activated"); }
    	@Override public void windowDeactivated(WindowEvent evt) { System.out.println("Window Deactivated"); }
    }

}
