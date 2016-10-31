package hello.cachaca.com.hello;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {

    private EditText objEdtMil;
    private EditText objEdtValor;
    private EditText objEdtTotal;
    private EditText objEdtAlcool;
    private  Spinner objSpnMarca;

    private ArrayList<String> objArrayListbebidas;
    private ListView objListViewBebidas;

    private ArrayAdapter<String> arrayAdapterMarca;
    private ArrayAdapter<String> arrayAdapterTotais;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        objEdtMil          = (EditText) findViewById(R.id.edtML);
        objEdtValor        = (EditText) findViewById(R.id.edtPreco);
        objEdtTotal        = (EditText) findViewById(R.id.editTotal);
        objEdtAlcool       = (EditText) findViewById(R.id.edtAlcool);

        objSpnMarca        = (Spinner) findViewById(R.id.spnMarca);

        objArrayListbebidas = new ArrayList<>();
        objListViewBebidas = (ListView) findViewById(R.id.listView);

        arrayAdapterMarca = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
        arrayAdapterMarca.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
              objSpnMarca.setAdapter(arrayAdapterMarca);

        arrayAdapterMarca.add("Brahma");
        arrayAdapterMarca.add("Skol");
        arrayAdapterMarca.add("Antartica");
        arrayAdapterMarca.add("Kaiser");
        arrayAdapterMarca.add("OUTRA");
        arrayAdapterMarca.add("Bavaria");
        arrayAdapterMarca.add("Nova Schin");

        arrayAdapterTotais = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, objArrayListbebidas);
        objListViewBebidas.setAdapter(arrayAdapterTotais);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    public void onClickCalcular(View view) {
        String msg;
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;

        double total = 0;
        int mili = 0;
        double preco = 0;
        double alcool = 0;

        try {
            mili = Integer.valueOf(objEdtMil.getText().toString());
            preco = Double.valueOf(objEdtValor.getText().toString());
            alcool = objEdtAlcool.getText().toString().equals("") ? 0 : Double.valueOf(objEdtAlcool.getText().toString());

            if (objEdtAlcool.getText().toString().equals("")) {
                total = (preco / mili) * 1000;
                objEdtTotal.setText(String.format("Preço por litro: %.2f", total));
            } else {
                total = (preco / ((alcool * mili) / 100)) * 1000;
                objEdtTotal.setText(String.format("Preço por litro de alcool: %.2f", total));
            }
            msg = String.valueOf(total);
        } catch (Exception e) {
            msg = "Este calculo não pode ser feito";
        }



        Toast toast = Toast.makeText(context, msg, duration);
        toast.show();

        AdicionarBebida(objArrayListbebidas, total, mili, preco, alcool);

        objListViewBebidas.setAdapter(arrayAdapterTotais);

    }

    private void AdicionarBebida(ArrayList<String> objArrayListbebidas, Double resultado, int mili, Double preco, Double alcool) {
        if (resultado != 0){
            objArrayListbebidas.add(
                    String.format("%s - R$ %.2f %s(%dml) - (R$ %.2f)",
                            objSpnMarca.getSelectedItem(),
                            resultado,
                            System.getProperty("line.separator"),
                            mili,
                            preco));
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
