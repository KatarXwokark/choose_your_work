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

            // go !
        	new Okienko("Solo", "Team", "Would you rather work solo or with a team?", kContainer);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
    
    public static class Odpowiedz{
    	private String tekst;
    	private int odp;
    	
    	public Odpowiedz(String tekst, int odp) {
    		this.tekst = tekst;
    		this.odp = odp;
    	}
    	
    	public String gettekst() {
    		return this.tekst;
    	}
    	
    	public int getodp() {
    		return this.odp;
    	}
    	
    	public String toString() {
    		return tekst + " " + odp;
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
    	
    	private KieContainer kContainer;
    	
    	private Okienko to = this;
    	
    	public Okienko(String l, String p, String q, KieContainer k) {
    		setLayout(new FlowLayout());
    		this.pytanie.setText(q);
    		setTitle("choose_your_job");
    		setSize(500, 150);
    		add(this.tekst);
    		add(this.pytanie);
    		add(this.przyciski);
        	this.lewy.setLabel(l);
        	this.prawy.setLabel(p);
        	this.lewy.setEnabled(true);
        	this.lewy.addActionListener(new ClickListenerleft());
        	this.prawy.setEnabled(true);
        	this.prawy.addActionListener(new ClickListenerright());
    		add(this.lewy);
    		add(this.prawy);
    		addWindowListener(this);
    		setVisible(true);
    		this.kContainer = k;
    	}
    	
    	public int getodp() {
    		return this.odp;
    	}
    	
    	public String getQuestion() {
    		return this.pytanie.getText();
    	}
    	
    	public void nextQuestion(String l, String p, String q, boolean end) {
    		this.pytanie.setText(q);
    		if(end) {
    			this.lewy.addActionListener(new Terminator());
    			this.prawy.addActionListener(new Terminator());
        		this.prawy.setLabel(" ");
            	this.prawy.setEnabled(false);
        		this.lewy.setLabel("End");
    		} else {
        		this.lewy.setLabel(l);
        		this.prawy.setLabel(p);
    		}
    		this.odp = NIEWIEM;
    	}
    	
    	public class ClickListenerleft implements ActionListener{
    		@Override
    		public void actionPerformed(ActionEvent evt) {
    			KieSession kSession = kContainer.newKieSession("ksession-rules");
    			odp = TAK;
    			System.out.println(pytanie.getText() + " " + odp);
    			kSession.insert(new Odpowiedz(pytanie.getText(), odp));
    			kSession.insert(to);
        		kSession.fireAllRules();
    		}
    	}
    	
    	public class ClickListenerright implements ActionListener{
    		@Override
    		public void actionPerformed(ActionEvent evt) {
    			KieSession kSession = kContainer.newKieSession("ksession-rules");
    			odp = NIE;
    			System.out.println(pytanie.getText() + " " + odp);
    			kSession.insert(new Odpowiedz(pytanie.getText(), odp));
    			kSession.insert(to);
        		kSession.fireAllRules();
    		}
    	}
    	public class Terminator implements ActionListener{
    		@Override
    		public void actionPerformed(ActionEvent evt) {
    			System.exit(0);
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
