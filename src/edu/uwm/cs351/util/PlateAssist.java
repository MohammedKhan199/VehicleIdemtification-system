package edu.uwm.cs351.util;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import javax.swing.*;
import javax.swing.event.*;

public class PlateAssist {
	
	private JFrame frame;
	private JPanel panel;
	private JMenuBar bar;
	private JMenuItem find;
	private JTextField query;
	private JLabel owner,exp,make,model,year,results;
	private MyHashTable<String, Registration> table;
	
	public PlateAssist(){
		loadTable();
		createContainers();
		createContents();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	private void createContainers(){
		frame = new JFrame("Find Registration...");
		panel = new JPanel();
		frame.setSize(300, 200);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel.setLayout(new GridBagLayout());
		frame.add(panel);
	}
	
	private void createContents(){
		//create menu
		bar = new JMenuBar();
		find = new JMenuItem("Find");
		find.addActionListener( (ae) -> {find.setEnabled(false);updateEntry(query.getText());});
		query = new JTextField();
		query.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) {}
			public void insertUpdate(DocumentEvent e) {find.setEnabled(true);}
			public void removeUpdate(DocumentEvent e) {find.setEnabled(true);}});
		query.addActionListener( (ae) -> {find.doClick();});
		bar.add(new JLabel("   Enter plate: "));bar.add(query);bar.add(find);
		bar.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 50));
		frame.setJMenuBar(bar);
		
		//create mutable labels
		results = new JLabel("No Results Found.");
		results.setOpaque(true);results.setBackground(Color.DARK_GRAY);
		results.setForeground(Color.WHITE);
		results.setHorizontalAlignment(JLabel.CENTER);
		owner = new JLabel("-");owner.setHorizontalAlignment(JLabel.CENTER);
		make = new JLabel("-");make.setHorizontalAlignment(JLabel.CENTER);
		model = new JLabel("-");model.setHorizontalAlignment(JLabel.CENTER);
		year = new JLabel("-");year.setHorizontalAlignment(JLabel.CENTER);
		exp = new JLabel("-");exp.setHorizontalAlignment(JLabel.CENTER);
		exp.setOpaque(true);
		
		JPanel left = new JPanel();
		JPanel right = new JPanel();
		left.setLayout(new GridLayout(5,1));
		right.setLayout(new GridLayout(5,1));
		
		GridBagConstraints c = new GridBagConstraints();
		c.fill=GridBagConstraints.BOTH;
		c.weighty=0.1;
		c.gridx=0;c.gridy=0;c.gridwidth=3;
		panel.add(results,c);
		c.weightx = c.weighty = 1.0;
		c.gridx = 0;c.gridy = 1;c.gridwidth=1;
		panel.add(left,c);
		c.gridx = 1;c.gridwidth=2;
		panel.add(right,c);
		c.gridx=2;c.gridwidth=1;
		c.insets = new Insets(0, 0, 0, 300/3);
		panel.add(new JPanel(),c);
		
		JLabel l0 = new JLabel("Owner:   ");l0.setHorizontalAlignment(JLabel.RIGHT);left.add(l0);right.add(owner);
		JLabel l1 = new JLabel("Make:   ");l1.setHorizontalAlignment(JLabel.RIGHT);left.add(l1);right.add(make);
		JLabel l2 = new JLabel("Model:   ");l2.setHorizontalAlignment(JLabel.RIGHT);left.add(l2);right.add(model);
		JLabel l3 = new JLabel("Year:   ");l3.setHorizontalAlignment(JLabel.RIGHT);left.add(l3);right.add(year);
		JLabel l4 = new JLabel("Expiration:   ");l4.setHorizontalAlignment(JLabel.RIGHT);left.add(l4);right.add(exp);
	}
	
	private void updateEntry(String plate){
		Registration r = table.get(plate);
		if (r == null){
			results.setText("No Results Found.");
			owner.setText("-");
			exp.setText("-");exp.setBackground(panel.getBackground());
			make.setText("-");
			model.setText("-");
			year.setText("-");
		}
		else{
			results.setText("Results");
			owner.setText(r.owner);
			exp.setText(r.expiration.format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));
			exp.setBackground(r.expiration.isBefore(LocalDate.now())?new Color(255,127,102):new Color(153,255,102));
			make.setText(r.make);
			model.setText(r.model);
			year.setText(""+r.year);
		}
	}
	
	private void loadTable(){
		table = new MyHashTable<>();
		try (Scanner s = new Scanner(new File("./plate_register.txt"))){
			String line, plate;
			String[] tokens;
			Registration r;
			LocalDate exp;
			while (s.hasNextLine()){
				line = s.nextLine();
				tokens = line.split("/");
				
				plate = tokens[0];
				exp = LocalDate.parse(tokens[2], DateTimeFormatter.ISO_LOCAL_DATE);

				r = new Registration(tokens[1], exp, Integer.parseInt(tokens[3]), tokens[4], tokens[5]);
				table.put(plate, r);
			}
			System.out.println("Successfully built table with "+table.size()+" entries!");
			System.out.println("Try querying for license plates from plate_register.txt.");
		} catch (FileNotFoundException e) {System.out.println("Couldn't find file: ./plate_register.txt");}
	}
	
	private class Registration {
		private String make, model, owner;
		private int year;
		private LocalDate expiration;
		public Registration(String owner, LocalDate expiration, int year, String make, String model) {
			this.make = make;
			this.model = model;
			this.year = year;
			this.owner = owner;
			this.expiration = expiration;}
	}
	
	
	public static void main(String[] args){new PlateAssist();}
}
