/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fp_sc;

import java.awt.Color;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import org.neuroph.nnet.MultiLayerPerceptron;

/**
 *
 * @author Adnan
 */
public class apps extends javax.swing.JFrame {

    Double txAge, txSx, txTb, txDb, txAp, txAm, txAa, txTp, txAl, txRa = 0.0;
    String Sex = "";
    String result, say, gen = "";
    
    static String[] labels = { "Positive", "Negative"};
    
    public apps() {
        initComponents();
        setLocationRelativeTo(this);
        
        tx_error.setVisible(false);
    }
    
    public void check(){
        Sex = (String)cb_sex.getSelectedItem();
        
        if (Sex=="Male") {
            txSx = 1.0;
        }
        else if (Sex=="Female") {
            txSx = 2.0;
        }
        else {
            //do nothing
        }
        
        txAge = Double.parseDouble(tx_age.getText());
        txTb = Double.parseDouble(tx_tb.getText());
        txDb = Double.parseDouble(tx_db.getText());
        txAp = Double.parseDouble(tx_ap.getText());
        txAm = Double.parseDouble(tx_am.getText());
        txAa = Double.parseDouble(tx_aa.getText());
        txTp = Double.parseDouble(tx_tp.getText());
        txAl = Double.parseDouble(tx_al.getText());
        txRa = Double.parseDouble(tx_ra.getText());
        //txSx = Double.parseDouble(Sex);
        
        MultiLayerPerceptron neuralNet = null;
	MaxMinParameters     params    = null;
		
	// Now we read (deserialize) the multilayer perceptron
	// and the max-min normalizing parameters from the .ser files
	try {
			
            // Deserializing the parameters
            FileInputStream   fisParams = new FileInputStream("ser_NormalizingParameters.ser");
            ObjectInputStream oisParams = new ObjectInputStream(fisParams);
			
            params = (MaxMinParameters) oisParams.readObject();
            fisParams.close();
            oisParams.close();
			
            // Deserializing the trained neural network
            FileInputStream   fisNN = new FileInputStream("ser_TrainedModel.ser");
            ObjectInputStream oisNN = new ObjectInputStream(fisNN);
			
            neuralNet = (MultiLayerPerceptron) oisNN.readObject();
            fisNN.close();
            oisNN.close();
			
	} catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
	} catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
	}
		
	// Now, let's test our newly-read parameters
	/*
	for (double max : params.maxIn) {
			System.out.println("Max In: " + max);
		}
		for (double min : params.minIn) {
			System.out.println("Min In: " + min);
		}
		*/
		
		// Let's test our trained neural network
		// Supposed we have a data as follows:
		// sepal-length = 7.1
		// sepal-width  = 3.1
		// petal-length = 4.8
		// petal-width  = 1.5
		double[] input = { txAge,txSx,txTb,txDb,txAp,txAm,txAa,txTp,txAl,txRa };
                //double[] input = { 72,1,2.7,1.3,260,31,56,7.4,3,0.6,1 };
		
		// Before inputting into the model, don't forget to normalize the input
		double[] normalizedInput = new double[input.length];
		
		for (int i = 0; i < input.length; i++) {
			// Normalize the input according to the max-min normalizing formula
			normalizedInput[i] = (input[i] - params.minIn[i]) / (params.maxIn[i] - params.minIn[i]);
		}
		
		neuralNet.setInput(normalizedInput);
		neuralNet.calculate();
                		
		// The following is the classification result:
		double[] prediction = neuralNet.getOutput();
		for (double p : prediction) {
			System.out.print(p + " ");
		}
		// As it is, it's not immediately clear that the result is "versicolor",
		// but if you see closely, the prediction most closely resemble { 0, 1, 0 }
		// which is the one-hot encoding of Iris Versicolor
		
		/*
		 * Just to make it more illustrative, let's make a function to interpret the prediction result
		 */
		interpret(prediction);
                
                result = labels[maxOutput(prediction)];
                
                if (result=="Positive") {
                    jPanel1.setBackground(Color.red);
                    if (Sex=="Male") {
                        gen = "He";
                    }
                    else if (Sex=="Female") {
                        gen = "She";
                    }
                    say = "We are sorry.. "+gen+" POSITIVE had a liver disease.";
                }
                else if (result=="Negative") {
                    jPanel1.setBackground(Color.green);
                    if (Sex=="Male") {
                        gen = "He";
                    }
                    else if (Sex=="Female") {
                        gen = "She";
                    }
                    say = "Yeay.. "+gen+" is healthy for now.";
                }
                else {
                    say = "ERROR!";
                }
                        
                label_check.setText(say);
        }
	
    public static void interpret(double[] prediction) {	
        
	System.out.println("\nThe patient's data is predicted as " + labels[maxOutput(prediction)]);
    }
	
    public static int maxOutput(double[] array) {
        double max = array[0];
        int index = 0;

        for (int i = 0; i < array.length; i++) {
            if (array[i] > max) {
                index = i;
                max = array[i];
            }
        }
        return index;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jRadioButton1 = new javax.swing.JRadioButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        tx_age = new javax.swing.JTextField();
        tx_ap = new javax.swing.JTextField();
        tx_aa = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        cb_sex = new javax.swing.JComboBox<String>();
        tx_tb = new javax.swing.JTextField();
        tx_db = new javax.swing.JTextField();
        tx_tp = new javax.swing.JTextField();
        tx_al = new javax.swing.JTextField();
        tx_ra = new javax.swing.JTextField();
        btn_check = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        label_check = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        tx_am = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        tx_error = new javax.swing.JLabel();

        jRadioButton1.setText("jRadioButton1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Age");

        jLabel2.setText("Gender");

        jLabel3.setText("Total Bliburin");

        jLabel4.setText("Direct Bliburin");

        jLabel5.setText("Alkaline Phosphotase");

        tx_aa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tx_aaActionPerformed(evt);
            }
        });

        jLabel8.setText("Aspartate Aminotransferase");

        jLabel9.setText("Total Proteins");

        jLabel10.setText("Albumin");

        jLabel11.setText("Ratio Albumin & Globumin");

        cb_sex.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Male", "Female" }));
        cb_sex.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cb_sexActionPerformed(evt);
            }
        });

        tx_al.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tx_alActionPerformed(evt);
            }
        });

        btn_check.setText("Check");
        btn_check.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_checkMouseClicked(evt);
            }
        });
        btn_check.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_checkActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel6.setText("Liver Patient Confirmation");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel7.setText("Patient's diagnosis");

        jPanel1.setBackground(java.awt.Color.cyan);

        label_check.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        label_check.setText("Patient's Status");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(label_check)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(26, Short.MAX_VALUE)
                .addComponent(label_check)
                .addContainerGap(31, Short.MAX_VALUE))
        );

        jLabel12.setText("Alamine Aminotransferase");

        tx_am.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tx_amActionPerformed(evt);
            }
        });

        jButton1.setText("Reset");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        tx_error.setForeground(new java.awt.Color(255, 0, 0));
        tx_error.setText("please fill data completely");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel6)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jButton1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btn_check, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel5)
                                            .addComponent(jLabel4)
                                            .addComponent(jLabel3))
                                        .addGap(10, 10, 10)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(tx_age, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(cb_sex, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(tx_tb, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(tx_db, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(tx_ap, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel7))
                                .addGap(62, 109, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel10)
                                            .addComponent(jLabel11)
                                            .addComponent(jLabel9)
                                            .addComponent(jLabel8))
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(tx_aa, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(tx_tp, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(tx_ra)
                                                .addComponent(tx_al, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel12)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(tx_am, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addContainerGap())))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(tx_error)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel6)
                .addGap(18, 18, 18)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(tx_age, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel12)
                        .addComponent(tx_am, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(cb_sex, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tx_tb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tx_db, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tx_ap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tx_aa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tx_tp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9))
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tx_al, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tx_ra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
                .addComponent(tx_error)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_check, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_checkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_checkActionPerformed
        // TODO add your handling code here:
        if (tx_age.getText().equals("")||tx_tb.getText().equals("")||tx_db.getText().equals("")||
                tx_ap.getText().equals("")||tx_am.getText().equals("")||tx_aa.getText().equals("")||
                tx_tp.getText().equals("")||tx_al.getText().equals("")||tx_ra.getText().equals("")) {
            tx_error.setVisible(true);
        } else {
            tx_error.setVisible(false);
            check();
        }
    }//GEN-LAST:event_btn_checkActionPerformed

    private void tx_aaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tx_aaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tx_aaActionPerformed

    private void tx_alActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tx_alActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tx_alActionPerformed

    private void cb_sexActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cb_sexActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cb_sexActionPerformed

    private void btn_checkMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_checkMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_checkMouseClicked

    private void tx_amActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tx_amActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tx_amActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        tx_age.setText("");
        tx_tb.setText("");
        tx_db.setText("");
        tx_ap.setText("");
        tx_am.setText("");
        tx_aa.setText("");
        tx_tp.setText("");
        tx_al.setText("");
        tx_ra.setText("");
        
        tx_error.setVisible(false);
        
        jPanel1.setBackground(Color.cyan);
        label_check.setText("Patient's Status");
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(apps.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(apps.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(apps.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(apps.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new apps().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_check;
    private javax.swing.JComboBox<String> cb_sex;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JLabel label_check;
    private javax.swing.JTextField tx_aa;
    private javax.swing.JTextField tx_age;
    private javax.swing.JTextField tx_al;
    private javax.swing.JTextField tx_am;
    private javax.swing.JTextField tx_ap;
    private javax.swing.JTextField tx_db;
    private javax.swing.JLabel tx_error;
    private javax.swing.JTextField tx_ra;
    private javax.swing.JTextField tx_tb;
    private javax.swing.JTextField tx_tp;
    // End of variables declaration//GEN-END:variables
}
