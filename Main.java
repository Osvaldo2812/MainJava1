import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Inisialisasi array menu (4 makanan + 4 minuman) menggunakan objek Menu
        Menu[] menus = {
            new Menu("Nasi Padang", 25000, "makanan"),
            new Menu("Ayam Goreng", 20000, "makanan"),
            new Menu("Sate Ayam", 30000, "makanan"),
            new Menu("Soto Ayam", 15000, "makanan"),
            new Menu("Es Teh", 5000, "minuman"),
            new Menu("Jus Jeruk", 10000, "minuman"),
            new Menu("Es Mactha", 13000, "minuman"),
            new Menu("Air Mineral", 3000, "minuman")
        };

        tampilMenu(menus); // Proses: Tampilkan daftar menu ke layar

        try (// Proses: Input pesanan dari user (maks 4 item, format "Nama = Jumlah")
        Scanner sc = new Scanner(System.in)) {
            String[] pesanan = new String[4]; // Array untuk menyimpan nama pesanan
            int[] jumlah = new int[4]; // Array untuk menyimpan jumlah pesanan
            int count = 0; // Counter untuk jumlah pesanan yang valid
            System.out.println("\nMasukkan pesanan (format: Nama = Jumlah). Ketik 'selesai' untuk berhenti:");
            while (count < 4) {
                String input = sc.nextLine();
                if (input.equalsIgnoreCase("selesai")) break; // Jika input "selesai", hentikan loop
                String[] parts = input.split(" = "); // Split input berdasarkan " = "
                if (parts.length == 2) {
                    pesanan[count] = parts[0].trim(); // Simpan nama menu
                    try {
                        jumlah[count] = Integer.parseInt(parts[1].trim()); // Parse jumlah ke int
                        count++; // Increment counter jika valid
                    } catch (NumberFormatException e) {
                        System.out.println("Format salah. Coba lagi."); // Error handling jika parse gagal
                    }
                } else {
                    System.out.println("Format: Nama = Jumlah"); // Pesan error jika format salah
                }
            }

            // Proses: Hitung total biaya berdasarkan pesanan, pajak, diskon, dll.
            double[] hasil = hitungTotalBiaya(menus, pesanan, jumlah, count);
            // Proses: Cetak struk pembayaran ke layar
            cetakStruk(menus, pesanan, jumlah, count, hasil);
        }
    }

    // Fungsi: Tampilkan menu berdasarkan kategori (tanpa loop)
    static void tampilMenu(Menu[] menus) {
        System.out.println("=== DAFTAR MENU ===");
        System.out.println("\nMAKANAN:");
        System.out.println(menus[0].getNama() + " - Rp " + menus[0].getHarga());
        System.out.println(menus[1].getNama() + " - Rp " + menus[1].getHarga());
        System.out.println(menus[2].getNama() + " - Rp " + menus[2].getHarga());
        System.out.println(menus[3].getNama() + " - Rp " + menus[3].getHarga());
        System.out.println("\nMINUMAN:");
        System.out.println(menus[4].getNama() + " - Rp " + menus[4].getHarga());
        System.out.println(menus[5].getNama() + " - Rp " + menus[5].getHarga());
        System.out.println(menus[6].getNama() + " - Rp " + menus[6].getHarga());
        System.out.println(menus[7].getNama() + " - Rp " + menus[7].getHarga());
    }

    // Fungsi: Hitung subtotal, pajak, diskon, penawaran, total akhir (menggunakan if-else)
    static double[] hitungTotalBiaya(Menu[] menus, String[] pesanan, int[] jumlah, int count) {
        double subtotal = 0, hargaMinumanTermurah = Double.MAX_VALUE; // Inisialisasi subtotal dan harga minuman
        int totalMinuman = 0; // Counter jumlah minuman

        // Proses: Hitung subtotal per pesanan, cek kategori minuman
        if (count > 0) {
            subtotal += cariHarga(menus, pesanan[0]) * jumlah[0];
            if (cariKategori(menus, pesanan[0]).equals("minuman")) {
                totalMinuman += jumlah[0];
                hargaMinumanTermurah = Math.min(hargaMinumanTermurah, cariHarga(menus, pesanan[0]));
            }
        }
        if (count > 1) {
            subtotal += cariHarga(menus, pesanan[1]) * jumlah[1];
            if (cariKategori(menus, pesanan[1]).equals("minuman")) {
                totalMinuman += jumlah[1];
                hargaMinumanTermurah = Math.min(hargaMinumanTermurah, cariHarga(menus, pesanan[1]));
            }
        }
        if (count > 2) {
            subtotal += cariHarga(menus, pesanan[2]) * jumlah[2];
            if (cariKategori(menus, pesanan[2]).equals("minuman")) {
                totalMinuman += jumlah[2];
                hargaMinumanTermurah = Math.min(hargaMinumanTermurah, cariHarga(menus, pesanan[2]));
            }
        }
        if (count > 3) {
            subtotal += cariHarga(menus, pesanan[3]) * jumlah[3];
            if (cariKategori(menus, pesanan[3]).equals("minuman")) {
                totalMinuman += jumlah[3];
                hargaMinumanTermurah = Math.min(hargaMinumanTermurah, cariHarga(menus, pesanan[3]));
            }
        }

        // Proses: Hitung pajak, biaya pelayanan, diskon, penawaran 
        double pajak = subtotal * 0.10; // Pajak 10% dari subtotal
        double biayaPelayanan = 20000; // Biaya pelayanan tetap
        double diskon = (subtotal > 100000) ? subtotal * 0.1 : 0; // Diskon 10% jika subtotal > 100k
        double penawaran = (subtotal > 50000 && totalMinuman >= 2) ? hargaMinumanTermurah : 0; // Penawaran gratis minuman jika kondisi terpenuhi
        double totalAkhir = subtotal + pajak + biayaPelayanan - diskon - penawaran; // Total akhir

        return new double[]{subtotal, pajak, biayaPelayanan, diskon, penawaran, totalAkhir}; // Kembalikan array hasil
    }

    // Fungsi: Cari harga menu berdasarkan nama (if-else )
    static double cariHarga(Menu[] menus, String nama) {
        if (nama.equals(menus[0].getNama())) return menus[0].getHarga();
        if (nama.equals(menus[1].getNama())) return menus[1].getHarga();
        if (nama.equals(menus[2].getNama())) return menus[2].getHarga();
        if (nama.equals(menus[3].getNama())) return menus[3].getHarga();
        if (nama.equals(menus[4].getNama())) return menus[4].getHarga();
        if (nama.equals(menus[5].getNama())) return menus[5].getHarga();
        if (nama.equals(menus[6].getNama())) return menus[6].getHarga();
        if (nama.equals(menus[7].getNama())) return menus[7].getHarga();
        return 0; // Jika tidak ditemukan
    }

    // Fungsi: Cari kategori menu berdasarkan nama (if-else )
    static String cariKategori(Menu[] menus, String nama) {
        if (nama.equals(menus[0].getNama())) return menus[0].getKategori();
        if (nama.equals(menus[1].getNama())) return menus[1].getKategori();
        if (nama.equals(menus[2].getNama())) return menus[2].getKategori();
        if (nama.equals(menus[3].getNama())) return menus[3].getKategori();
        if (nama.equals(menus[4].getNama())) return menus[4].getKategori();
        if (nama.equals(menus[5].getNama())) return menus[5].getKategori();
        if (nama.equals(menus[6].getNama())) return menus[6].getKategori();
        if (nama.equals(menus[7].getNama())) return menus[7].getKategori();
        return ""; // Jika tidak ditemukan
    }

    // Fungsi: Cetak struk pembayaran (tampilkan detail pesanan dan total)
    static void cetakStruk(Menu[] menus, String[] pesanan, int[] jumlah, int count, double[] hasil) {
        System.out.println("\n=== STRUK PEMBAYARAN ===");
        System.out.println("Item yang dipesan:");
        // Proses: Tampilkan detail pesanan per item
        if (count > 0) {
            double harga = cariHarga(menus, pesanan[0]);
            System.out.println(pesanan[0] + " x " + jumlah[0] + " = Rp " + (harga * jumlah[0]));
        }
        if (count > 1) {
            double harga = cariHarga(menus, pesanan[1]);
            System.out.println(pesanan[1] + " x " + jumlah[1] + " = Rp " + (harga * jumlah[1]));
        }
        if (count > 2) {
            double harga = cariHarga(menus, pesanan[2]);
            System.out.println(pesanan[2] + " x " + jumlah[2] + " = Rp " + (harga * jumlah[2]));
        }
        if (count > 3) {
            double harga = cariHarga(menus, pesanan[3]);
            System.out.println(pesanan[3] + " x " + jumlah[3] + " = Rp " + (harga * jumlah[3]));
        }

        // Proses: Tampilkan subtotal, pajak, biaya pelayanan, diskon/penawaran jika ada, total akhir
        System.out.println("\nSubtotal: Rp " + hasil[0]);
        System.out.println("Pajak (10%): Rp " + hasil[1]);
        System.out.println("Biaya Pelayanan: Rp " + hasil[2]);
        if (hasil[3] > 0) System.out.println("Diskon (10%): -Rp " + hasil[3]);
        if (hasil[4] > 0) System.out.println("Penawaran Minuman: -Rp " + hasil[4]);
        System.out.println("Total Akhir: Rp " + hasil[5]);
    }
}
