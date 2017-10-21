/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Driver;

/**
 *
 * @author Someone
 */
public class Data {

    private String berita;
    private int emosi;
    private int provokasi;
    private int hoax; //0=tidak, 1=iya

    public Data(String berita, int emosi, int provokasi) {
        this.berita = berita;
        this.emosi = emosi;
        this.provokasi = provokasi;
    }

    public Data(String berita, int emosi, int provokasi, int hoax) {
        this.berita = berita;
        this.emosi = emosi;
        this.provokasi = provokasi;
        this.hoax = hoax;
    }

    public String getBerita() {
        return this.berita;
    }

    public int getEmosi() {
        return this.emosi;
    }

    public int getProvokasi() {
        return this.provokasi;
    }

    public int getHoax() {
        return this.hoax;
    }

    public void setHoax(int hoax) {
        this.hoax = hoax;
    }
        
}
