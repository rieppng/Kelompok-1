package com.mycompany.aplikasiklinik.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.util.List;
import com.mycompany.aplikasiklinik.model.entity.Bill;

/**
 * Class CashierPanel
 * * Panel untuk Kasir.
 * Menampilkan daftar tagihan, rincian biaya, dan aksi pembayaran/cetak kwitansi.
 */
public class CashierPanel extends JPanel {
    
    // Tabel Daftar Tagihan
    private final JTable tableAllBills = new JTable();
    private final DefaultTableModel allBillsModel;
    private final JButton btnRefresh = new JButton("Refresh Data");

    // Area Detail & Pembayaran
    private final JTextArea txtBillDetails = new JTextArea();
    private final JLabel lblTotalAmount = new JLabel("Total: Rp 0", SwingConstants.CENTER);
    private final JButton btnPay = new JButton("Proses Pembayaran");
    private final JButton btnPrintReceipt = new JButton("Cetak Kwitansi");

    /**
     * Konstruktor CashierPanel.
     * Membagi layar menjadi dua: Daftar Tagihan (Kiri) dan Detail Pembayaran (Kanan).
     */
    public CashierPanel() {
        setLayout(new GridLayout(1, 2));

        // --- Panel Kiri: Daftar Tagihan ---
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBorder(BorderFactory.createTitledBorder("Daftar Tagihan"));

        allBillsModel = new DefaultTableModel(new Object[]{"ID Tagihan", "Pasien", "Dokter", "Total Biaya", "Status"}, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        tableAllBills.setModel(allBillsModel);
        tableAllBills.getTableHeader().setReorderingAllowed(false);
        
        TableColumnModel cm = tableAllBills.getColumnModel();
        cm.getColumn(0).setPreferredWidth(60);  // ID
        cm.getColumn(0).setMaxWidth(80);
        cm.getColumn(1).setPreferredWidth(150); // Pasien
        cm.getColumn(2).setPreferredWidth(150); // Dokter
        cm.getColumn(3).setPreferredWidth(100); // Total
        cm.getColumn(4).setPreferredWidth(100); // Status

        leftPanel.add(new JScrollPane(tableAllBills), BorderLayout.CENTER);
        leftPanel.add(btnRefresh, BorderLayout.SOUTH); 

        // --- Panel Kanan: Detail & Aksi ---
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBorder(BorderFactory.createTitledBorder("Detail Tagihan & Pembayaran"));

        txtBillDetails.setEditable(false); 
        lblTotalAmount.setFont(new Font("Arial", Font.BOLD, 24)); 

        JPanel actionPanel = new JPanel(new GridLayout(2, 1, 5, 5)); 
        actionPanel.add(btnPay);
        actionPanel.add(btnPrintReceipt);

        rightPanel.add(new JScrollPane(txtBillDetails), BorderLayout.CENTER); 
        rightPanel.add(lblTotalAmount, BorderLayout.NORTH); 
        rightPanel.add(actionPanel, BorderLayout.SOUTH); 

        add(leftPanel);
        add(rightPanel);
    }
    
    /**
     * Mengatur status tombol berdasarkan status pembayaran tagihan yang dipilih.
     * Jika lunas, tombol bayar mati dan cetak hidup, begitu sebaliknya.
     */
    public void updateActionButtons(boolean isPaid) {
        btnPay.setEnabled(!isPaid);
        btnPrintReceipt.setEnabled(isPaid);
    }
    
    /**
     * Membersihkan area detail tagihan.
     */
    public void clearBillDetails() {
        txtBillDetails.setText("");
        lblTotalAmount.setText("Total: Rp 0");
        updateActionButtons(false); 
    }

    // --- GETTERS & SETTERS ---

    public JTable getTableAllBills() { return tableAllBills; }
    public DefaultTableModel getAllBillsModel() { return allBillsModel; }
    public JButton getBtnRefresh() { return btnRefresh; }
    public JTextArea getTxtBillDetails() { return txtBillDetails; }
    public void setTotalLabel(double amount) { lblTotalAmount.setText("Total: Rp " + amount); }
    public JButton getBtnPay() { return btnPay; }
    public JButton getBtnPrintReceipt() { return btnPrintReceipt; }
    
    // Placeholder untuk kompatibilitas jika dipanggil controller lama
    public void populateAllBillsTable(List<Bill> bills) { }
}