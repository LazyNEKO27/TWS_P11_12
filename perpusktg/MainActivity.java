package com.LZNeko.perpusktg;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;
import android.os.*;
import android.graphics.*;
import android.app.ActionBar.LayoutParams;
import android.content.DialogInterface;
@SuppressLint("InlinedApi") public class MainActivity extends Activity implements OnClickListener {
BiodataActivity biodataActivity = new BiodataActivity();
TableLayout tableLayout;
Button buttonTambahBiodata;
ArrayList<Button>buttonEdit = new ArrayList<Button>();
ArrayList<Button>buttonDelete = new ArrayList<Button>();
JSONArray arrayBiodata;
@SuppressLint("NewApi") @Override
protected void onCreate(Bundle savedInstanceState) {
super.onCreate(savedInstanceState);
setContentView(R.layout.activity_main);
// Jika SDK Android diatas API Ver.9
if (android.os.Build.VERSION.SDK_INT > 9) {
StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
.permitAll().build();
StrictMode.setThreadPolicy(policy);
}
// Mendapatkan data widget dari XML Activity melalui ID
tableLayout = (TableLayout) findViewById(R.id.tableBiodata);
buttonTambahBiodata = (Button) findViewById(R.id.buttonTambahBiodata);
buttonTambahBiodata.setOnClickListener(this);
//menambah baris untuk tabel
TableRow barisTabel = new TableRow(this);
barisTabel.setBackgroundColor(Color.CYAN);
// Menambahkan tampilan teks untuk judul pada tabel
TextView viewHeaderId = new TextView(this);
TextView viewHeaderNama = new TextView(this);
TextView viewHeaderAction = new TextView(this);
viewHeaderId.setText("ID");
viewHeaderNama.setText("Nama");
viewHeaderAction.setText("Action");
viewHeaderId.setPadding(5, 1, 5, 1);
viewHeaderNama.setPadding(5, 1, 5, 1);
viewHeaderAction.setPadding(5, 1, 5, 1);
// Menampilkan tampilan TextView ke dalam tabel
barisTabel.addView(viewHeaderId);
barisTabel.addView(viewHeaderNama);
barisTabel.addView(viewHeaderAction);
// Menyusun ukuran dari tabel
tableLayout.addView(barisTabel, new
TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
try {
// Mengubah data dari BiodataActivity yang berupa String menjadi array
arrayBiodata = new JSONArray(biodataActivity.tampilBiodata());
for (int i = 0; i < arrayBiodata.length(); i++) {
JSONObject jsonChildNode = arrayBiodata.getJSONObject(i);
String kategori = jsonChildNode.optString("kategori");
String id = jsonChildNode.optString("id");
System.out.println("Nama : " + kategori );
System.out.println("ID : " + id);
barisTabel = new TableRow(this);
// Memberi warna pada baris tabel
if (i % 2 == 0) {
barisTabel.setBackgroundColor(Color.LTGRAY);
}
TextView viewId = new TextView(this);
viewId.setText(id);
viewId.setPadding(5, 1, 5, 1);
barisTabel.addView(viewId);
TextView viewNama = new TextView(this);
viewNama.setText(kategori);
viewNama.setPadding(5, 1, 5, 1);
barisTabel.addView(viewNama);
// Menambahkan button Edit
buttonEdit.add(i, new Button(this));
buttonEdit.get(i).setId(Integer.parseInt(id));
buttonEdit.get(i).setTag("Edit");
buttonEdit.get(i).setText("Edit");
buttonEdit.get(i).setOnClickListener(this);
barisTabel.addView(buttonEdit.get(i));
// Menambahkan tombol Delete
buttonDelete.add(i, new Button(this));
buttonDelete.get(i).setId(Integer.parseInt(id));
buttonDelete.get(i).setTag("Delete");
buttonDelete.get(i).setText("Delete");
buttonDelete.get(i).setOnClickListener(this);
barisTabel.addView(buttonDelete.get(i));
tableLayout.addView(barisTabel, new TableLayout.LayoutParams
(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
}
}
catch (JSONException e) {
e.printStackTrace();
} }
public void onClick (View view) {
if (view.getId() == R.id.buttonTambahBiodata) {
tambahBiodata();
}
else {
for (int i= 0; i < buttonEdit.size(); i++) {
// Jika ingin mengedit data pada biodata
if (view.getId() == buttonEdit.get(i).getId() &&
view.getTag().toString().trim().equals("Edit")) {
Toast.makeText(MainActivity.this, "Edit : " + buttonEdit.get(i).getId(),
Toast.LENGTH_SHORT).show();
int id = buttonEdit.get(i).getId();
getDataByID(id);
}
// Menghapus data di Tabel
else if (view.getId() == buttonDelete.get(i).getId() &&
view.getTag().toString().trim().equals("Delete")){
Toast.makeText(MainActivity.this, "Delete : " +
buttonDelete.get(i).getId(), Toast.LENGTH_SHORT).show();
int id = buttonDelete.get(i).getId();
deleteBiodata(id);
}
}
}
}
public void deleteBiodata (int id) {
biodataActivity.deleteBiodata(id);
finish();
startActivity(getIntent());
}
// Mendapatkan Biodata melalui ID
public void getDataByID (int id) {
String namaEdit = null;
JSONArray arrayPersonal;
try {
arrayPersonal = new JSONArray(biodataActivity.getBiodataById(id));
for (int i = 0; i < arrayPersonal.length(); i++) {
JSONObject jsonChildNode = arrayPersonal.getJSONObject(i);
namaEdit = jsonChildNode.optString("kategori");
}
}
catch (JSONException e) {
e.printStackTrace();
}
LinearLayout layoutInput = new LinearLayout(this);
layoutInput.setOrientation(LinearLayout.VERTICAL);
// Membuat id tersembunyi pada AlertDialog
final TextView viewId = new TextView(this);
viewId.setText(String.valueOf(id));
viewId.setTextColor(Color.TRANSPARENT);
layoutInput.addView(viewId);
final EditText editNama = new EditText(this);
editNama.setText(namaEdit);
layoutInput.addView(editNama);
// Membuat AlertDialog untuk mengubah data di Biodata
AlertDialog.Builder builderEditBiodata = new AlertDialog.Builder(this);
//builderEditBiodata.setIcon(R.drawable.webse);
builderEditBiodata.setTitle("Update Biodata");
builderEditBiodata.setView(layoutInput);
builderEditBiodata.setPositiveButton("Update", new DialogInterface.OnClickListener()
{
@Override
public void onClick(DialogInterface dialog, int which) {
String kategori = editNama.getText().toString();
System.out.println("Nama : " + kategori);
String laporan = biodataActivity.updateBiodata(viewId.getText().toString(),
editNama.getText().toString());
Toast.makeText(MainActivity.this, laporan, Toast.LENGTH_SHORT).show();
finish();
startActivity(getIntent());
}
});
// Jika tidak ingin mengubah data pada Biodata
builderEditBiodata.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
{
@Override
public void onClick(DialogInterface dialog, int which) {
dialog.cancel();
}
});
builderEditBiodata.show();
}
public void tambahBiodata() {
LinearLayout layoutInput = new LinearLayout(this);
layoutInput.setOrientation(LinearLayout.VERTICAL);
final EditText editNama = new EditText(this);
editNama.setHint("Nama");
layoutInput.addView(editNama);
// Membuat AlertDialog untuk menambahkan data pada Biodata
AlertDialog.Builder builderInsertBiodata= new AlertDialog.Builder(this);
//builderInsertBiodata.setIcon(R.drawable.webse);
builderInsertBiodata.setTitle("Insert Biodata");
builderInsertBiodata.setView(layoutInput);
builderInsertBiodata.setPositiveButton("Insert", new
DialogInterface.OnClickListener() {
@Override
public void onClick(DialogInterface dialog, int which) {
String kategori = editNama.getText().toString();
System.out.println("Nama : " + kategori);
String laporan = biodataActivity.insertBiodata(kategori);
Toast.makeText(MainActivity.this, laporan, Toast.LENGTH_SHORT).show();
finish();
startActivity(getIntent());
}
});
builderInsertBiodata.setNegativeButton("Cancel", new
DialogInterface.OnClickListener() {
@Override
public void onClick(DialogInterface dialog, int which) {
dialog.cancel();
}
});
builderInsertBiodata.show();
}
@Override
public boolean onCreateOptionsMenu(Menu menu) {
//Inflate the menu; this adds items to the action bar if it is present.
getMenuInflater().inflate(R.menu.main, menu);
return true;
}
}