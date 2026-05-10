public class Menu {
    String nama;
    double harga;
    String kategori;

    public Menu(String nama, double harga, String kategori) {
        this.nama = nama;
        this.harga = harga;
        this.kategori = kategori;
    }

    public String getNama() {
        return nama;
    }

    public double getHarga() {
        return harga;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getKategori() {
        return kategori;
    }
}
