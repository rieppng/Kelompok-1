package com.mycompany.aplikasiklinik.controller;

import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.TextAlignment;
import com.mycompany.aplikasiklinik.model.dao.*;
import com.mycompany.aplikasiklinik.model.entity.*;
import com.mycompany.aplikasiklinik.view.CashierPanel;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;

/**
 * Controller untuk menangani fitur Kasir.
 * Mengelola daftar tagihan, pemrosesan pembayaran (tunai),
 * dan pencetakan kwitansi dalam format PDF.
 */
public class CashierController {

    private final CashierPanel cashierPanel;
    private final BillDAO billDAO;
    private final PatientDAO patientDAO;
    private final UserDAO userDAO;
    private final AppointmentDAO appointmentDAO;

    private Bill selectedBill;
    private Timer refreshTimer;
    private final int REFRESH_INTERVAL_MS = 8000;

    public CashierController(CashierPanel cashierPanel) {
        this.cashierPanel = cashierPanel;
        this.billDAO = new BillDAO();
        this.patientDAO = new PatientDAO();
        this.userDAO = new UserDAO();
        this.appointmentDAO = new AppointmentDAO();

        loadAllBills();

        // Setup Listener
        this.cashierPanel.getBtnRefresh().addActionListener(e -> loadAllBills());
        this.cashierPanel.getBtnPay().addActionListener(e -> processPayment());
        this.cashierPanel.getBtnPrintReceipt().addActionListener(e -> printReceiptAsPdf());
        this.cashierPanel.getTableAllBills().getSelectionModel().addListSelectionListener(this::tableSelectionChanged);

        startAutoRefresh();
    }

    private void startAutoRefresh() {
        refreshTimer = new Timer(REFRESH_INTERVAL_MS, e -> loadAllBills());
        refreshTimer.start();
    }

    public void stopAutoRefresh() {
        if (refreshTimer != null) refreshTimer.stop();
    }

    /**
     * Memuat ulang semua tagihan dari database ke tabel.
     * Mempertahankan seleksi baris (row selection) agar tidak hilang saat refresh otomatis terjadi.
     */
    public void loadAllBills() {
        // 1. Simpan ID yang sedang dipilih sebelum refresh
        int selectedRow = cashierPanel.getTableAllBills().getSelectedRow();
        int selectedBillId = -1;
        
        if (selectedRow != -1) {
            try {
                selectedBillId = (int) cashierPanel.getAllBillsModel().getValueAt(selectedRow, 0);
            } catch (Exception e) {
                selectedBillId = -1;
            }
        }

        DefaultTableModel model = cashierPanel.getAllBillsModel();
        model.setRowCount(0);

        List<Bill> allBills = billDAO.getAllBills(); 
        
        for (Bill bill : allBills) {
            Appointment app = appointmentDAO.getAppointmentById(bill.getAppointmentId());
            String patientName = "Unknown";
            String doctorName = "Unknown";

            if (app != null) {
                Patient patient = patientDAO.getPatientById(app.getPatientId());
                User doctor = userDAO.getUserById(app.getDoctorId());
                patientName = (patient != null) ? patient.getName() : "Error (ID: " + app.getPatientId() + ")";
                doctorName = (doctor != null) ? doctor.getFullName() : "Error (ID: " + app.getDoctorId() + ")";
            }

            String statusStr = bill.isPaid() ? "LUNAS" : "BELUM DIBAYAR";

            model.addRow(new Object[]{
                bill.getId(),
                patientName,
                doctorName,
                bill.getTotalAmount(),
                statusStr 
            });
        }

        // 2. Kembalikan seleksi baris setelah refresh selesai
        if (selectedBillId != -1) {
            for (int i = 0; i < model.getRowCount(); i++) {
                int idInTable = (int) model.getValueAt(i, 0);
                if (idInTable == selectedBillId) {
                    cashierPanel.getTableAllBills().setRowSelectionInterval(i, i);
                    cashierPanel.getTableAllBills().scrollRectToVisible(
                        cashierPanel.getTableAllBills().getCellRect(i, 0, true)
                    );
                    break; 
                }
            }
        }
    }

    /**
     * Menangani perubahan seleksi pada tabel tagihan.
     * Menampilkan detail tagihan yang dipilih di panel detail.
     */
    private void tableSelectionChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting() && cashierPanel.getTableAllBills().getSelectedRow() != -1) {
            int row = cashierPanel.getTableAllBills().getSelectedRow();
            int billId = (int) cashierPanel.getAllBillsModel().getValueAt(row, 0); 

            selectedBill = billDAO.getBillById(billId); 

            if (selectedBill != null) {
                Appointment app = appointmentDAO.getAppointmentById(selectedBill.getAppointmentId());
                Patient patient = null;
                User doctor = null;

                if (app != null) {
                    patient = patientDAO.getPatientById(app.getPatientId());
                    doctor = userDAO.getUserById(app.getDoctorId());
                }

                String details = buildBillDetailsString(selectedBill, patient, doctor);
                cashierPanel.getTxtBillDetails().setText(details);
                cashierPanel.setTotalLabel(selectedBill.getTotalAmount());

                cashierPanel.updateActionButtons(selectedBill.isPaid()); 
            }
        }
    }

    /**
     * Memproses pembayaran tagihan.
     * Meminta input uang tunai, validasi jumlah, menghitung kembalian,
     * dan memperbarui status tagihan menjadi PAID di database.
     */
    private void processPayment() {
        if (selectedBill == null) {
            JOptionPane.showMessageDialog(cashierPanel, "Pilih tagihan yang akan dibayar dari tabel!");
            return;
        }

        if (selectedBill.isPaid()) {
            JOptionPane.showMessageDialog(cashierPanel, "Tagihan ini sudah lunas.");
            return;
        }

        double totalAmount = selectedBill.getTotalAmount();

        // 1. Input Jumlah Uang
        String inputStr = JOptionPane.showInputDialog(cashierPanel, 
                "Total Tagihan: Rp " + String.format("%.0f", totalAmount) + "\n\nMasukkan Jumlah Uang Pasien (Rp):", 
                "Input Pembayaran", JOptionPane.QUESTION_MESSAGE);

        if (inputStr != null && !inputStr.trim().isEmpty()) {
            try {
                double moneyTendered = Double.parseDouble(inputStr);

                // 2. Validasi Nominal
                if (moneyTendered < totalAmount) {
                    JOptionPane.showMessageDialog(cashierPanel, 
                            "Uang pembayaran kurang! \nKurang: Rp " + String.format("%.0f", (totalAmount - moneyTendered)), 
                            "Pembayaran Gagal", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                // 3. Konfirmasi & Hitung Kembalian
                double change = moneyTendered - totalAmount;
                int confirm = JOptionPane.showConfirmDialog(cashierPanel,
                        "Total: Rp " + String.format("%.0f", totalAmount) + 
                        "\nBayar: Rp " + String.format("%.0f", moneyTendered) + 
                        "\n\nKEMBALIAN: Rp " + String.format("%.0f", change) + 
                        "\n\nProses transaksi ini?",
                        "Konfirmasi Pembayaran", JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    if (billDAO.markAsPaid(selectedBill.getId())) {
                        JOptionPane.showMessageDialog(cashierPanel, 
                                "Transaksi Berhasil!\n\nKembalian: Rp " + String.format("%.0f", change), 
                                "Sukses", JOptionPane.INFORMATION_MESSAGE);
                        
                        selectedBill = null; 
                        cashierPanel.clearBillDetails(); 
                        loadAllBills(); 
                    } else {
                        JOptionPane.showMessageDialog(cashierPanel, "Gagal update database.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(cashierPanel, "Masukkan nominal angka yang valid!", "Error Input", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Mencetak kwitansi pembayaran ke dalam file PDF.
     * Menggunakan library iTextPDF.
     */
    private void printReceiptAsPdf() {
        if (selectedBill == null) {
            JOptionPane.showMessageDialog(cashierPanel, "Pilih tagihan lunas dari tabel untuk dicetak.");
            return;
        }

        if (!selectedBill.isPaid()) {
            JOptionPane.showMessageDialog(cashierPanel, "Tagihan belum dibayar. Tidak bisa dicetak.");
            return;
        }

        Appointment app = appointmentDAO.getAppointmentById(selectedBill.getAppointmentId());
        Patient patient = (app != null) ? patientDAO.getPatientById(app.getPatientId()) : null;
        User doctor = (app != null) ? userDAO.getUserById(app.getDoctorId()) : null;

        // Persiapan Nama File
        String patientName = (patient != null) ? patient.getName() : "Pasien_Tidak_Ditemukan";
        String patientNik = (patient != null) ? patient.getNik() : "NIK_Tidak_Ditemukan";
        String paymentDateStr = selectedBill.getPaymentDate() != null ? selectedBill.getPaymentDate().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) : "Tanggal_Error";
        
        String fileName = patientName.replaceAll("[^a-zA-Z0-9.-]", "_") + "_" + 
                          patientNik.replaceAll("[^a-zA-Z0-9.-]", "_") + "_" + 
                          paymentDateStr + ".pdf";

        // Dialog Simpan File
        javax.swing.JFileChooser fileChooser = new javax.swing.JFileChooser();
        fileChooser.setDialogTitle("Simpan Kwitansi sebagai PDF");
        fileChooser.setSelectedFile(new java.io.File(fileName));
        int userSelection = fileChooser.showSaveDialog(cashierPanel);

        if (userSelection == javax.swing.JFileChooser.APPROVE_OPTION) {
            java.io.File fileToSave = fileChooser.getSelectedFile();
            String filePath = fileToSave.getAbsolutePath();
            if (!filePath.toLowerCase().endsWith(".pdf")) filePath += ".pdf";

            try (PdfWriter writer = new PdfWriter(filePath);
                 PdfDocument pdfDoc = new PdfDocument(writer);
                 Document document = new Document(pdfDoc)) {

                document.add(new Paragraph("KWITANSI PEMBAYARAN")
                        .setTextAlignment(TextAlignment.CENTER)
                        .setFontSize(18)
                        .setBold());
                document.add(new Paragraph("\n")); 
                document.add(new Paragraph(buildBillDetailsString(selectedBill, patient, doctor)));

                JOptionPane.showMessageDialog(cashierPanel, "Kwitansi berhasil disimpan sebagai PDF di:\n" + filePath, "Berhasil", JOptionPane.INFORMATION_MESSAGE);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(cashierPanel, "Gagal menyimpan file PDF: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }
    }

    /**
     * Helper untuk menyusun string detail tagihan yang akan ditampilkan di layar atau dicetak.
     */
    private String buildBillDetailsString(Bill bill, Patient patient, User doctor) {
        StringBuilder sb = new StringBuilder();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        sb.append("=== KWITANSI PEMBAYARAN ===\n\n");
        sb.append("ID Tagihan: ").append(bill.getId()).append("\n");

        if (bill.isPaid() && bill.getPaymentDate() != null) {
            sb.append("Tanggal Pembayaran: ").append(bill.getPaymentDate().format(formatter)).append("\n\n");
        }

        if (patient != null) {
            sb.append("--- DETAIL PASIEN ---\n");
            sb.append("Nama: ").append(patient.getName()).append("\n");
            sb.append("NIK: ").append(patient.getNik()).append("\n");
            sb.append("Alamat: ").append(patient.getAddress()).append("\n");
            sb.append("No. HP: ").append(patient.getPhoneNumber()).append("\n\n");
        }

        if (doctor != null) {
            sb.append("--- DETAIL DOKTER ---\n");
            sb.append("Nama Dokter: ").append(doctor.getFullName()).append("\n\n");
        }

        sb.append("--- RINCIAN BIAYA ---\n");
        sb.append("Biaya Obat: Rp ").append(String.format("%.0f", bill.getTotalMedicationCost())).append("\n");
        sb.append("Biaya Tindakan: Rp ").append(String.format("%.0f", bill.getTotalServiceCost())).append("\n");
        sb.append("Biaya Konsultasi: Rp ").append(String.format("%.0f", bill.getConsultationFee())).append("\n");
        sb.append("-------------------------\n");
        sb.append("TOTAL: Rp ").append(String.format("%.0f", bill.getTotalAmount())).append("\n\n");
        sb.append("Status Pembayaran: ").append(bill.isPaid() ? "LUNAS" : "BELUM DIBAYAR");

        return sb.toString();
    }
}