package com.facci.ingresodevotantebc;

import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivityBC extends AppCompatActivity {

    DBHelper dbSQLITE;

    EditText editTextNombre, editTextApellido, editTextRecintoElectoral, editTextAñoNacimiento, editTextID;

    Button buttonInsertar,buttonModificar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity_bc);

        dbSQLITE = new DBHelper(this);
    }

    public void insertarClickView(View v){
        editTextNombre = (EditText) findViewById(R.id.editTextNombre);
        editTextApellido= (EditText) findViewById(R.id.editTextApellido);
        editTextRecintoElectoral = (EditText) findViewById(R.id.editTextRecintoElectoral);
        editTextAñoNacimiento = (EditText) findViewById(R.id.editTextAñoNacimiento);

        boolean estaInsertado = dbSQLITE.insertar(editTextNombre.getText().toString(), editTextApellido.getText().toString(), editTextRecintoElectoral.getText().toString(), Integer.parseInt(editTextAñoNacimiento.getText().toString()));

        if (estaInsertado )
            Toast.makeText(MainActivityBC.this,"Datos Ingresado",Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(MainActivityBC.this,"Lo sentimos ocurrio un error",Toast.LENGTH_SHORT).show();

    }

    public void Buscartodo(View v){

        Cursor res = dbSQLITE.selectBuscarTodo();
        if (res.getCount() == 0 ) {
            mostrarMensaje("Error","No se encuentra registrado");
            return;
        }

        StringBuffer buffer = new StringBuffer();

        while(res.moveToNext()){
            buffer.append("id : "+ res.getString(0)+"/a");
            buffer.append("Nombre : "+ res.getString(1)+"/a");
            buffer.append("Apellido : "+ res.getString(2)+"/a");
            buffer.append("Recinto Electoral : "+ res.getString(3)+"/a");
            buffer.append("AñoNacimiento: "+ res.getInt(4)+"/a");

        }

        mostrarMensaje("Registros",buffer.toString());

    }

    public void mostrarMensaje(String titulo , String mensaje){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(titulo);
        builder.setMessage(mensaje);
        builder.show();
    }

    public void modificarRegistroClick(View v){

        editTextNombre = (EditText) findViewById(R.id.editTextNombre);
        editTextApellido= (EditText) findViewById(R.id.editTextApellido);
        editTextRecintoElectoral = (EditText) findViewById(R.id.editTextRecintoElectoral);
        editTextAñoNacimiento = (EditText) findViewById(R.id.editTextAñoNacimiento);
        editTextID = (EditText)findViewById(R.id.editTextID);

        boolean estadoActualizado = dbSQLITE.modificarRegistro(editTextID.getText().toString(),editTextNombre.getText().toString(),editTextApellido.getText().toString(),editTextRecintoElectoral.getText().toString(), Integer.parseInt(editTextAñoNacimiento.getText().toString()));

        if (estadoActualizado == true){
            Toast.makeText(MainActivityBC.this, "Registro Actualizado", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(MainActivityBC.this, "Error : Registro NO Actualizado", Toast.LENGTH_SHORT).show();
        }

    }

    public void eliminarRegistroClick(View v){

        editTextID = (EditText) findViewById(R.id.editTextID);

        Integer registroEliminados = dbSQLITE.eliminarRegistro(editTextID.getText().toString());

        if (registroEliminados > 0 ){
            Toast.makeText(MainActivityBC.this,"Registro(s) Eliminado(s)",Toast.LENGTH_SHORT);
        }else{
            Toast.makeText(MainActivityBC.this,"ERROR : Registro no eliminado",Toast.LENGTH_SHORT);
        }
    }

}
