package com.LZNeko.perpusktg;
public class BiodataActivity extends KoneksiActivity {
// sourcecode untuk URL -> URL menggunakan IP address default eclipse
//	urlnya pastikan sama dengan lokasi file php kalian ya
	String URL = "http://10.0.2.2/P11_12/server.php";
	String url = "";
	String response = "";
	//menampilkan biodata dari database
	public String tampilBiodata() {
		try{
			url = URL + "?operasi=view";
			System.out.println("URL Tampil Biodata : " + url);
			response = call(url);
		}
		catch(Exception e) {
		}
		return response;
	}
	//memasukan biodata baru ke dalam database
	public String insertBiodata(String kategori) {
		try{
			url = URL + "?operasi=insert&kategori=" + kategori;
			System.out.println("URL Insert Biodata : " + url);
			response = call(url);
		}
		catch (Exception e){
		}
		return response;
	}
	//melihat biodata berdasarkan ID
	public String getBiodataById (int id) {
		try{
			url=URL + "?operasi=get_biodata_by_id&id=" + id;
			System.out.println("URL Insert Biodata : " + url);
			response = call(url);
		}
		catch(Exception e) {
		}
		return response;
	}
	//mengubah isi biodata
	public String updateBiodata(String id, String kategori) {
		try{
			url=URL + "?operasi=update&id=" + id + "&kategori=" + kategori;
			System.out.println("URL Update Biodata : " + url);
			response = call(url);
		}
		catch(Exception e){
		}
		return response;
	}
	//coding hapus
	public String deleteBiodata (int id) {
		try{
			url = URL + "?operasi=delete&id=" + id;
			System.out.println("URL Hapus Biodata : " + url);
			response = call(url);
		}
		catch(Exception e){
		}
		return response;
	}
}
