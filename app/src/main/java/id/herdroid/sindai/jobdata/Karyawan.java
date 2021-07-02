package id.herdroid.sindai.jobdata;

public class Karyawan {
    private String key;
    private String nama;
    private String nip;
    private String kelas;
    private String jabatan;


    public Karyawan() {

    }
    public void setKey(String key){
        this.key = key;
    }

    public String getKey(){
        return key;
    }

    public void setName(String nama){
        this.nama = nama;
    }

    public String getName(){
        return nama;
    }

    public void setNip(String nip){
        this.nip = nip;
    }

    public String getNip(){
        return nip;
    }

    public void setKelas(String kelas){
        this.kelas = kelas;
    }

    public String getKelas(){
        return kelas;
    }

    public void setJabatan(String jabatan){
        this.jabatan = jabatan;
    }

    public String getJabatan(){
        return jabatan;
    }


    public Karyawan(String namaKaryawan, String nipKaryawan, String kelasKaryawan, String jabatanKaryawan){
        nama = namaKaryawan;
        nip = nipKaryawan;
        kelas = kelasKaryawan;
        jabatan = jabatanKaryawan;

    }
}