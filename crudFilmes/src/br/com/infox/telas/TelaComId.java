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
public class TelaComId extends javax.swing.JFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    /**
     * Creates new form TelaComId
     */
    public TelaComId() {
        initComponents();
        conexao = ModuloConexao.conector();
        //lblCod.setVisible(false);
        listar();

        //System.out.println(conexao);
        lblCod.setVisible(false);
        txtNome.grabFocus();
        colunas();
    }

    public void cadastrar() {
        if (txtNome.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Preencha todos os campos");
        } else if (txtGenero.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Preencha todos os campos");
        } else {
            conexao = ModuloConexao.conector();
            try {
                String sql = "insert into tb_filmes_id (nome, genero) values (?,?)";
                pst = conexao.prepareStatement(sql);
                pst.setString(1, txtNome.getText());
                pst.setString(2, txtGenero.getText());
                pst.execute();
                pst.close();
                JOptionPane.showMessageDialog(null, "Cadastrado com sucesso");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erro ao cadastrar");
            }
        }
    }

    public void listar() {
        DefaultTableModel model = (DefaultTableModel) tb_filmes_id.getModel();

        model.setNumRows(0);
        conexao = ModuloConexao.conector();

        try {
            String sql = "select * from tb_filmes_id";
            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery();

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getString("genero"),
                    rs.getString("data_cadastro")
                });
            }
            pst.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao listar");
        }

    }
    
    //Início Ordenar
    public void ordenar() {
        DefaultTableModel model = (DefaultTableModel) tb_filmes_id.getModel();
        
        model.setNumRows(0);
        conexao = ModuloConexao.conector();
        
        String item = (String) ordenar.getSelectedItem();
        //Ordenar por id
        if((item.equals("ID"))){
            try {
                String sql = "select * from tb_filmes_id order by id asc";
                pst = conexao.prepareStatement(sql);
                rs = pst.executeQuery();
                
                while (rs.next()) {
                    model.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("genero"),
                        rs.getString("data_cadastro")
                    });
                }
                pst.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erro ao listar");
            }
        }

        //ordenar por nome
        else if (item.equals("Nome")) {
            try {
                String sql = "select * from tb_filmes_id order by nome asc";
                pst = conexao.prepareStatement(sql);
                rs = pst.executeQuery();
                
                while (rs.next()) {
                    model.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("genero"),
                        rs.getString("data_cadastro")
                    });
                }
                pst.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erro ao listar");
            }
        }
        //ordenar por gênero
        else if((item.equals("Gênero"))){
            try {
                String sql = "select * from tb_filmes_id order by genero asc";
                pst = conexao.prepareStatement(sql);
                rs = pst.executeQuery();
                
                while (rs.next()) {
                    model.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("genero"),
                        rs.getString("data_cadastro")
                    });
                }
                pst.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erro ao listar");
            }
        }
        //Ordenar por data de cadastro
        else if((item.equals("Data"))){
            try {
                String sql = "select * from tb_filmes_id order by data_cadastro asc";
                pst = conexao.prepareStatement(sql);
                rs = pst.executeQuery();
                
                while (rs.next()) {
                    model.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("genero"),
                        rs.getString("data_cadastro")
                    });
                }
                pst.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erro ao listar");
            }
        }
    } //FIM organizar

    public void selecionar() {
        //lblCod.setText(tb_filmes_id.getValueAt(tb_filmes_id.getSelectedRow(), 0).toString()); este comando é necessário caso o método 'excluir' peça para ler o id a partir deste label. O mais prático é fazer o comando para o 'excluir' ler direto o valor do id descrito a tabela
        txtNome.setText(tb_filmes_id.getValueAt(tb_filmes_id.getSelectedRow(), 1).toString());
        txtGenero.setText(tb_filmes_id.getValueAt(tb_filmes_id.getSelectedRow(), 2).toString());
    }

    public void alterar() {
        if (JOptionPane.showConfirmDialog(null, "Deseja alterar?", "warning",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            conexao = ModuloConexao.conector();
            try {
                String sql = "update tb_filmes_id set nome = ?, genero = ? where id = ?";
                pst = conexao.prepareStatement(sql);

                pst.setString(1, txtNome.getText());
                pst.setString(2, txtGenero.getText());
                pst.setString(3, tb_filmes_id.getValueAt(tb_filmes_id.getSelectedRow(), 0).toString()); //esse comando é mais prático porque lê direto o id na tabela
                pst.execute();
                pst.close();
                JOptionPane.showMessageDialog(null, "Alterado com sucesso");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erro ao alterar");
            }
        }
    }

    public void excluir() {
        if (JOptionPane.showConfirmDialog(null, "Deseja excluir?", "warning",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            conexao = ModuloConexao.conector();
            try {
                String sql = "delete from tb_filmes_id where id = ?";
                pst = conexao.prepareStatement(sql);
                //pst.setString(1, lblCod.getText()); Este comando faz com que o 'excluir' precise de um label com o id para excluir
                pst.setString(1, tb_filmes_id.getValueAt(tb_filmes_id.getSelectedRow(), 0).toString()); //esse comando é mais prático poque lê direto o id na tabela
                pst.execute();
                pst.close();
                JOptionPane.showMessageDialog(null, "Excluído com sucesso");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erro ao excluir");
            }
            //inserindo na tabela de filmes excluidos:
            conexao = ModuloConexao.conector();
            try {
                String sql = "insert into tb_excluidos (id, nome, genero) values (?,?,?)";
                pst = conexao.prepareStatement(sql);
                pst.setString(1, tb_filmes_id.getValueAt(tb_filmes_id.getSelectedRow(), 0).toString());
                pst.setString(2, tb_filmes_id.getValueAt(tb_filmes_id.getSelectedRow(), 1).toString());
                pst.setString(3, tb_filmes_id.getValueAt(tb_filmes_id.getSelectedRow(), 2).toString());
                pst.execute();
                pst.close();
                JOptionPane.showMessageDialog(null, "Movido para os excluidos");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Falha ao mover para os excluidos");
            }//FIM
        }
    }

    public void limpar() {
        txtNome.setText("");
        txtGenero.setText("");
    }

    public void sair() {
        if (JOptionPane.showConfirmDialog(null, "Deseja sair?", "warning",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    public void focar() {
        txtNome.grabFocus();
    }

    //Método para alterar o tamanho de algumas colunas. Executado no ini
    public void colunas() {
        tb_filmes_id.getColumnModel().getColumn(0).setPreferredWidth(1);
        tb_filmes_id.getColumnModel().getColumn(1).setPreferredWidth(150);
        tb_filmes_id.getColumnModel().getColumn(2).setPreferredWidth(110);
        tb_filmes_id.getColumnModel().getColumn(3).setPreferredWidth(140);

    }//FIM

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        txtNome = new javax.swing.JTextField();
        txtGenero = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        lblCod = new javax.swing.JLabel();
        btnSair = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();
        btnAlterar = new javax.swing.JButton();
        btnLimpar = new javax.swing.JButton();
        btnInserir = new javax.swing.JButton();
        btnListar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tb_filmes_id = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        btnExcluidos = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        ordenar = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setName(""); // NOI18N

        txtNome.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N

        txtGenero.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Liberation Sans", 0, 18)); // NOI18N
        jLabel2.setText("Nome:");

        jLabel4.setFont(new java.awt.Font("Liberation Sans", 0, 18)); // NOI18N
        jLabel4.setText("Gênero:");

        jLabel1.setFont(new java.awt.Font("Liberation Sans Narrow", 1, 36)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Cadastro de filmes");

        lblCod.setText("jLabel3");

        btnSair.setFont(new java.awt.Font("Liberation Sans", 0, 14)); // NOI18N
        btnSair.setText("Sair");
        btnSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSairActionPerformed(evt);
            }
        });

        btnExcluir.setFont(new java.awt.Font("Liberation Sans", 0, 18)); // NOI18N
        btnExcluir.setText("Excluir");
        btnExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirActionPerformed(evt);
            }
        });

        btnAlterar.setFont(new java.awt.Font("Liberation Sans", 0, 18)); // NOI18N
        btnAlterar.setText("Alterar");
        btnAlterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAlterarActionPerformed(evt);
            }
        });

        btnLimpar.setFont(new java.awt.Font("Liberation Sans", 0, 18)); // NOI18N
        btnLimpar.setText("Limpar");
        btnLimpar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimparActionPerformed(evt);
            }
        });

        btnInserir.setFont(new java.awt.Font("Liberation Sans", 0, 18)); // NOI18N
        btnInserir.setText("Cadastrar");
        btnInserir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInserirActionPerformed(evt);
            }
        });

        btnListar.setFont(new java.awt.Font("Liberation Sans", 0, 18)); // NOI18N
        btnListar.setText("Listar");
        btnListar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnListarActionPerformed(evt);
            }
        });

        jScrollPane1.setForeground(new java.awt.Color(0, 0, 0));
        jScrollPane1.setName(""); // NOI18N

        tb_filmes_id.setFont(new java.awt.Font("Liberation Sans", 0, 18)); // NOI18N
        tb_filmes_id.setModel(new javax.swing.table.DefaultTableModel(
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
                "ID", "Nome", "Gênero", "Data de cadastro"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tb_filmes_id.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tb_filmes_idMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tb_filmes_id);

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/icon-cine2.png"))); // NOI18N

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/icon-cadastrar2.png"))); // NOI18N

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/icon-listar2.png"))); // NOI18N

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/icon-alterar2.png"))); // NOI18N

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/icon-excluir2.png"))); // NOI18N
        jLabel8.setToolTipText("");

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/icon-limpar2.png"))); // NOI18N

        btnExcluidos.setFont(new java.awt.Font("Liberation Sans", 0, 12)); // NOI18N
        btnExcluidos.setText("Excluidos");
        btnExcluidos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluidosActionPerformed(evt);
            }
        });

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/icon-sair.png"))); // NOI18N

        jLabel11.setText("Ordenar por");

        ordenar.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ID", "Nome", "Gênero", "Data" }));
        ordenar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ordenarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel9))
                    .addComponent(jLabel8)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnAlterar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnListar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnExcluir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnLimpar, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)
                    .addComponent(btnInserir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 630, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(133, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(10, 10, 10)
                                .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(54, 54, 54)
                                .addComponent(jLabel4)
                                .addGap(3, 3, 3)
                                .addComponent(txtGenero, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel3)
                                .addGap(61, 61, 61)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(80, 80, 80)
                                .addComponent(lblCod)
                                .addGap(36, 36, 36)
                                .addComponent(btnSair)
                                .addGap(4, 4, 4)
                                .addComponent(jLabel10))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(ordenar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(btnExcluidos, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtGenero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)
                            .addComponent(jLabel2)
                            .addComponent(jLabel11)
                            .addComponent(ordenar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(lblCod)
                            .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnSair, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                        .addGap(45, 45, 45)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel5)
                            .addComponent(btnInserir))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnListar)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel7)
                            .addComponent(btnAlterar))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnExcluir)
                            .addComponent(jLabel8))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnLimpar))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addComponent(jLabel9)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnExcluidos, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void tb_filmes_idMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb_filmes_idMouseClicked
        // TODO add your handling code here:
        selecionar();
    }//GEN-LAST:event_tb_filmes_idMouseClicked

    private void btnListarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnListarActionPerformed
        // TODO add your handling code here:
        listar();
        limpar();
    }//GEN-LAST:event_btnListarActionPerformed

    private void btnInserirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInserirActionPerformed
        // TODO add your handling code here:
        //validacao();
        cadastrar();
        listar();
        limpar();
        focar();
    }//GEN-LAST:event_btnInserirActionPerformed

    private void btnLimparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimparActionPerformed
        // TODO add your handling code here:
        limpar();
        focar();
    }//GEN-LAST:event_btnLimparActionPerformed

    private void btnAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAlterarActionPerformed
        alterar();
        listar();
        limpar();
        focar();
    }//GEN-LAST:event_btnAlterarActionPerformed

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed
        // TODO add your handling code here:
        excluir();
        listar();
        limpar();
        focar();
    }//GEN-LAST:event_btnExcluirActionPerformed

    private void btnSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSairActionPerformed
        // TODO add your handling code here:
        //new Login().setVisible(true);
        sair();
    }//GEN-LAST:event_btnSairActionPerformed

    private void btnExcluidosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluidosActionPerformed
        // TODO add your handling code here:
        new TelaExcluidos().setVisible(true);
        this.dispose();

    }//GEN-LAST:event_btnExcluidosActionPerformed

    private void ordenarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ordenarActionPerformed
        // TODO add your handling code here:
        ordenar();
    }//GEN-LAST:event_ordenarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {
            UIManager.setLookAndFeel("com.jtattoo.plaf.graphite.GraphiteLookAndFeel");
        } catch (Exception e) {
        }
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                //Nas aspas vazias abaixo, o padrão era a palavra "nimbus"
                if ("".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TelaComId.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaComId.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaComId.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaComId.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaComId().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAlterar;
    private javax.swing.JButton btnExcluidos;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnInserir;
    private javax.swing.JButton btnLimpar;
    private javax.swing.JButton btnListar;
    private javax.swing.JButton btnSair;
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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCod;
    private javax.swing.JComboBox<String> ordenar;
    private javax.swing.JTable tb_filmes_id;
    private javax.swing.JTextField txtGenero;
    private javax.swing.JTextField txtNome;
    // End of variables declaration//GEN-END:variables
}
