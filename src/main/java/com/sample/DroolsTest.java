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
    	private KieSession kSession;
    	
    	private Okienko to = this;
    	
    	public Okienko(String l, String p, String q, KieContainer k) {
    		setLayout(new FlowLayout());
    		pytanie.setText(q);
    		lewy.setLabel(l);
    		prawy.setLabel(p);
    		lewy.setEnabled(true);
    		lewy.addActionListener(new ClickListenerleft());
    		prawy.setEnabled(true);
    		prawy.addActionListener(new ClickListenerright());
    		setTitle("choose_your_job");
    		setSize(500, 150);
    		add(tekst);
    		add(pytanie);
    		add(przyciski);
    		add(lewy);
    		add(prawy);
    		addWindowListener(this);
    		setVisible(true);
    		this.kContainer = k;
    		this.kSession = kContainer.newKieSession("ksession-rules");
    	}
    	
    	public int getodp() {
    		return this.odp;
    	}
    	
    	public String getQuestion() {
    		return this.pytanie.getText();
    	}
    	
    	public void nextQuestion(String l, String p, String q) {
    		pytanie.setText(q);
    		lewy.setLabel(l);
    		prawy.setLabel(p);
    	}
    	
    	public class ClickListenerleft implements ActionListener{
    		@Override
    		public void actionPerformed(ActionEvent evt) {
    			odp = TAK;
    			System.out.println(odp);
    			kSession.insert(new Odpowiedz(pytanie.getText(), odp));
    			kSession.insert(to);
        		kSession.fireAllRules();
    		}
    	}
    	
    	public class ClickListenerright implements ActionListener{
    		@Override
    		public void actionPerformed(ActionEvent evt) {
    			odp = NIE;
    			System.out.println(odp);
    			kSession.insert(new Odpowiedz(pytanie.getText(), odp));
    			kSession.insert(to);
        		kSession.fireAllRules();
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
