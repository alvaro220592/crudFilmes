/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.infox.telas;

import br.com.infox.dal.ModuloConexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;



/**
 *
 * @author usuario
 */
public class TelaExcluidos extends javax.swing.JFrame {
        Connection conexao = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
    /**
     * Creates new form Excluidos
     */
    public TelaExcluidos() {
        conexao = ModuloConexao.conector();
        initComponents();listarExcluidos();
        colunasExcluidos();
        txtNomeExcluidos.grabFocus();
    }

    
    public void listarExcluidos(){
        DefaultTableModel model = (DefaultTableModel) tb_excluidos.getModel();
        
        model.setNumRows(0);
        conexao = ModuloConexao.conector();
        
        try {
            String sql = "select * from tb_excluidos";
            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery();
            
            while(rs.next()){
                model.addRow(new Object[]{
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getString("genero"),
                    rs.getString("data_exclusao")
                });
            }
            pst.close();
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, "Erro ao listar");
        }
        
      }
    
    public void selecionarExcluidos(){
        //lblCod.setText(tb_filmes_id.getValueAt(tb_filmes_id.getSelectedRow(), 0).toString()); este comando é necessário caso o método 'excluir' peça para ler o id a partir deste label. O mais prático é fazer o comando para o 'excluir' ler direto o valor do id descrito a tabela
        txtNomeExcluidos.setText(tb_excluidos.getValueAt(tb_excluidos.getSelectedRow(), 1).toString());
        txtGeneroExcluidos.setText(tb_excluidos.getValueAt(tb_excluidos.getSelectedRow(), 2).toString());
    }
    
    public void resgatar(){
        //Apagando da tabela atual
        if(JOptionPane.showConfirmDialog(null, "Deseja mover para a lista  de cadastro?", "warning",
                JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
            conexao = ModuloConexao.conector();
            try{
                String sql = "delete from tb_excluidos where id = ?";
                pst = conexao.prepareStatement(sql);
                //pst.setString(1, lblCod.getText()); Este comando faz com que o 'excluir' precise de um label com o id para excluir
                pst.setString(1, tb_excluidos.getValueAt(tb_excluidos.getSelectedRow(), 0).toString()); //esse comando é mais prático poque lê direto o id na tabela
                pst.execute();
                pst.close();
                
            }catch (Exception e){
                
            }//FIM
            
            //Resgatando para a tabela "Cadastro de Filmes":
            //conexao = ModuloConexao.conector();
            try{
                String sql = "insert into tb_filmes_id (id, nome, genero) values (?,?,?)";
                pst = conexao.prepareStatement(sql);
                pst.setString(1, tb_excluidos.getValueAt(tb_excluidos.getSelectedRow(), 0).toString());
                pst.setString(2, tb_excluidos.getValueAt(tb_excluidos.getSelectedRow(), 1).toString());
                pst.setString(3, tb_excluidos.getValueAt(tb_excluidos.getSelectedRow(), 2).toString());
                pst.execute();
                pst.close();
                JOptionPane.showMessageDialog(null, "Resgatado com sucesso");
            }catch(Exception e){
                JOptionPane.showMessageDialog(null, "Falha ao resgatar");
            }//FIM
        }
    }
    
    public void alterarExcluidos(){
        if(JOptionPane.showConfirmDialog(null, "Deseja alterar?", "warning", JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
            conexao = ModuloConexao.conector();
            try {
                String sql = "update tb_excluidos set nome = ?, genero = ? where id = ?";
                pst = conexao.prepareStatement(sql);
                pst.setString(1, txtNomeExcluidos.getText());
                pst.setString(2, txtGeneroExcluidos.getText());
                pst.setString(3, tb_excluidos.getValueAt(tb_excluidos.getSelectedRow(), 0).toString());
                pst.execute();
                pst.close();
                JOptionPane.showMessageDialog(null, "Alterado com sucesso");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erro ao alterar");
            }
            
        }
    }
    
    public void limparExcluidos(){
        txtNomeExcluidos.setText("");
        txtGeneroExcluidos.setText("");
    }
    
    public void destruir(){
        if(JOptionPane.showConfirmDialog(null, "Deseja excluir este filme permanentemente?", "warning", JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
            conexao = ModuloConexao.conector();
            try {
                String sql = "delete from tb_excluidos where id = ?";
                pst = conexao.prepareStatement(sql);
                pst.setString(1, tb_excluidos.getValueAt(tb_excluidos.getSelectedRow(), 0).toString());
                pst.execute();
                pst.close();
                JOptionPane.showMessageDialog(null, "Excluído com sucesso");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Falha ao ecluir");
            }
        }
    }
    
    public void focarExcluidos(){
        txtNomeExcluidos.grabFocus();
    }
    
    //Método para alterar o tamanho de algumas colunas. Executado no ini
    public void colunasExcluidos(){
    tb_excluidos.getColumnModel().getColumn(0).setPreferredWidth(1);
    tb_excluidos.getColumnModel().getColumn(1).setPreferredWidth(150);
    tb_excluidos.getColumnModel().getColumn(2).setPreferredWidth(110);
    tb_excluidos.getColumnModel().getColumn(3).setPreferredWidth(140);
    
    }//FIM
    
    public void sair(){
        if(JOptionPane.showConfirmDialog(null, "Deseja sair?", "warning",
                JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
        System.exit(0);    
        }
    }
    
   
    
   
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        btnLimparExcluidos = new javax.swing.JButton();
        btnDestruirExcluidos = new javax.swing.JButton();
        btnAlterarExcluidos = new javax.swing.JButton();
        btnListarExcluidos = new javax.swing.JButton();
        btnResgatar = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tb_excluidos = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        btnSair = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        txtNomeExcluidos = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtGeneroExcluidos = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        btnVoltar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setPreferredSize(new java.awt.Dimension(839, 310));

        btnLimparExcluidos.setFont(new java.awt.Font("Liberation Sans", 0, 18)); // NOI18N
        btnLimparExcluidos.setText("Limpar");
        btnLimparExcluidos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimparExcluidosActionPerformed(evt);
            }
        });

        btnDestruirExcluidos.setFont(new java.awt.Font("Liberation Sans", 0, 18)); // NOI18N
        btnDestruirExcluidos.setText("Destruir");
        btnDestruirExcluidos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDestruirExcluidosActionPerformed(evt);
            }
        });

        btnAlterarExcluidos.setFont(new java.awt.Font("Liberation Sans", 0, 18)); // NOI18N
        btnAlterarExcluidos.setText("Alterar");
        btnAlterarExcluidos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAlterarExcluidosActionPerformed(evt);
            }
        });

        btnListarExcluidos.setFont(new java.awt.Font("Liberation Sans", 0, 18)); // NOI18N
        btnListarExcluidos.setText("Listar");
        btnListarExcluidos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnListarExcluidosActionPerformed(evt);
            }
        });

        btnResgatar.setFont(new java.awt.Font("Liberation Sans", 0, 18)); // NOI18N
        btnResgatar.setText("Resgatar");
        btnResgatar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResgatarActionPerformed(evt);
            }
        });

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/icon-cadastrar2.png"))); // NOI18N

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/icon-listar2.png"))); // NOI18N

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/icon-alterar2.png"))); // NOI18N

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/icon-excluir2.png"))); // NOI18N
        jLabel8.setToolTipText("");

        tb_excluidos.setFont(new java.awt.Font("Liberation Sans", 0, 18)); // NOI18N
        tb_excluidos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID", "Nome", "Gênero", "Data de exclusão"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tb_excluidos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tb_excluidosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tb_excluidos);

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/icon-limpar2.png"))); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btnDestruirExcluidos, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnAlterarExcluidos, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnListarExcluidos, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnResgatar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)
                    .addComponent(btnLimparExcluidos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                    .addComponent(btnResgatar)
                                                    .addComponent(jLabel5))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnListarExcluidos))
                                            .addComponent(jLabel6))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnAlterarExcluidos))
                                    .addComponent(jLabel7))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnDestruirExcluidos))
                            .addComponent(jLabel8))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnLimparExcluidos)
                            .addComponent(jLabel9)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 17, Short.MAX_VALUE))
        );

        jLabel1.setFont(new java.awt.Font("Liberation Sans Narrow", 1, 36)); // NOI18N
        jLabel1.setText("Filmes excluídos");

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/icon-lixo2.png"))); // NOI18N

        btnSair.setFont(new java.awt.Font("Liberation Sans", 0, 12)); // NOI18N
        btnSair.setText("Sair");
        btnSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSairActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Liberation Sans", 0, 18)); // NOI18N
        jLabel3.setText("Nome");

        txtNomeExcluidos.setFont(new java.awt.Font("Liberation Sans", 0, 18)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Liberation Sans", 0, 18)); // NOI18N
        jLabel4.setText("Gênero");

        txtGeneroExcluidos.setFont(new java.awt.Font("Liberation Sans", 0, 18)); // NOI18N

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/icon-sair.png"))); // NOI18N

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/icon-voltar2.png"))); // NOI18N

        btnVoltar.setFont(new java.awt.Font("Liberation Sans", 0, 12)); // NOI18N
        btnVoltar.setText("voltar");
        btnVoltar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVoltarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel11)
                        .addGap(2, 2, 2)
                        .addComponent(btnVoltar)
                        .addGap(105, 105, 105)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txtNomeExcluidos, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(39, 39, 39)
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtGeneroExcluidos, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel2)
                                .addGap(69, 69, 69)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 112, Short.MAX_VALUE)
                        .addComponent(btnSair)
                        .addGap(3, 3, 3)
                        .addComponent(jLabel10))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addComponent(btnSair)
                    .addComponent(jLabel11)
                    .addComponent(btnVoltar)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtNomeExcluidos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)
                            .addComponent(txtGeneroExcluidos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(14, 14, 14)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 320, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnResgatarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResgatarActionPerformed
        resgatar();
        listarExcluidos();
    }//GEN-LAST:event_btnResgatarActionPerformed

    private void btnVoltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVoltarActionPerformed
        // TODO add your handling code here:
        new TelaComId().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnVoltarActionPerformed

    private void btnListarExcluidosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnListarExcluidosActionPerformed
        listarExcluidos();
    }//GEN-LAST:event_btnListarExcluidosActionPerformed

    private void btnAlterarExcluidosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAlterarExcluidosActionPerformed
        alterarExcluidos();
        limparExcluidos();
        listarExcluidos();
    }//GEN-LAST:event_btnAlterarExcluidosActionPerformed

    private void btnLimparExcluidosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimparExcluidosActionPerformed
        limparExcluidos();
    }//GEN-LAST:event_btnLimparExcluidosActionPerformed

    private void btnDestruirExcluidosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDestruirExcluidosActionPerformed
        destruir();
        limparExcluidos();
        listarExcluidos();
        focarExcluidos();
    }//GEN-LAST:event_btnDestruirExcluidosActionPerformed

    private void tb_excluidosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb_excluidosMouseClicked
        selecionarExcluidos();
    }//GEN-LAST:event_tb_excluidosMouseClicked

    private void btnSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSairActionPerformed
        sair();
    }//GEN-LAST:event_btnSairActionPerformed

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
            java.util.logging.Logger.getLogger(TelaExcluidos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaExcluidos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaExcluidos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaExcluidos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaExcluidos().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAlterarExcluidos;
    private javax.swing.JButton btnDestruirExcluidos;
    private javax.swing.JButton btnLimparExcluidos;
    private javax.swing.JButton btnListarExcluidos;
    private javax.swing.JButton btnResgatar;
    private javax.swing.JButton btnSair;
    private javax.swing.JButton btnVoltar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tb_excluidos;
    private javax.swing.JTextField txtGeneroExcluidos;
    private javax.swing.JTextField txtNomeExcluidos;
    // End of variables declaration//GEN-END:variables
}
