package currency_convertor.example.practice;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Double rounded_Value;
    Double CAD;
    Double USD;
    Double EUR;
    Double JPY;
    Double AUD;
    Double HKD;
    Double CNY;
    Double CHF;
    Double INR;
    Double NZD;
    Double KRW;



    public class DownloadTask extends AsyncTask<String, Void, String>{


        @Override
        protected String doInBackground(String... urls) {
            String result = "";
            URL url;
            HttpURLConnection urlConnection = null;

            try{
                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();

                while (data != -1){
                    char current = (char) data;
                    result += current;
                    data = reader.read();
                }

                return result;

            }   catch(Exception e){
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                JSONObject jsonObject = new JSONObject(s);


                CAD = Double.parseDouble(jsonObject.getJSONObject("rates").getString("CAD"));
                USD = Double.parseDouble(jsonObject.getJSONObject("rates").getString("USD"));
                EUR = Double.parseDouble(jsonObject.getJSONObject("rates").getString("EUR"));
                JPY = Double.parseDouble(jsonObject.getJSONObject("rates").getString("JPY"));
                AUD = Double.parseDouble(jsonObject.getJSONObject("rates").getString("AUD"));
                HKD = Double.parseDouble(jsonObject.getJSONObject("rates").getString("HKD"));
                CNY = Double.parseDouble(jsonObject.getJSONObject("rates").getString("CNY"));
                CHF = Double.parseDouble(jsonObject.getJSONObject("rates").getString("CHF"));
                INR = Double.parseDouble(jsonObject.getJSONObject("rates").getString("INR"));
                NZD = Double.parseDouble(jsonObject.getJSONObject("rates").getString("NZD"));
                KRW = Double.parseDouble(jsonObject.getJSONObject("rates").getString("KRW"));

            }catch(Exception e){
                e.printStackTrace();
            }

        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DownloadTask task = new DownloadTask();
        task.execute("https://api.exchangeratesapi.io/latest?base=CAD");

        final Spinner spinner = (Spinner) findViewById(R.id.spinner);
        final Spinner mySpinner = (Spinner) findViewById(R.id.spinner2);
        final EditText first_Number_Value = (EditText) findViewById(R.id.first_Value);

        final TextView final_Value = (TextView) findViewById(R.id.final_Value);

        final Button button = (Button) findViewById(R.id.button);

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.names));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(myAdapter);
        mySpinner.setAdapter(myAdapter);



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String first_Conversion = spinner.getSelectedItem().toString();
                String second_Conversion = mySpinner.getSelectedItem().toString();
                final String first_Number = first_Number_Value.getText().toString();

                Toast.makeText(MainActivity.this, first_Conversion + " to " + second_Conversion, Toast.LENGTH_SHORT).show();



                // Makes sure the user is inputting a valid number
                try{
                    Double check_for_Number = Double.parseDouble(first_Number);
                    String string_Final = String.valueOf(converter(first_Conversion, second_Conversion, first_Number));
                    final_Value.setText(string_Final);
                } catch(Exception e){
                    final_Value.setText("Please put a number");
                }

            }
        });

    }

    // Actual conversion function
    public Double converter(String first_Currency, String second_Currency, String first_Value){
        double first_Number = Double.parseDouble(first_Value);


        if(first_Currency.equals("Australian Dollar")){
            return Rounder(AUD(second_Currency, first_Number));
        }

        else if(first_Currency.equals("Canadian Dollar")){
            return Rounder(CAD(second_Currency, first_Number));
        }

        else if(first_Currency.equals("Chinese Yuan")){
            return Rounder(CNY(second_Currency, first_Number));
        }

        else if(first_Currency.equals("Euro")){
            return Rounder(EUR(second_Currency, first_Number));
        }

        else if(first_Currency.equals("Hong Kong Dollar")){
            return Rounder(HKD(second_Currency, first_Number));
        }

        else if(first_Currency.equals("Indian Rupee")){
            return Rounder(INR(second_Currency, first_Number));
        }

        else if(first_Currency.equals("Japanese Yen")){
            return Rounder(JPY(second_Currency, first_Number));
        }

        else if(first_Currency.equals("South Korean won")){
            return Rounder(KRW(second_Currency, first_Number));
        }

        else if(first_Currency.equals("New Zealand Dollar")){
            return Rounder(NZD(second_Currency, first_Number));
        }

        else if(first_Currency.equals("Swiss Franc")){
            return Rounder(CHF(second_Currency, first_Number));
        }

        else if(first_Currency.equals("United States Dollar")){
            return Rounder(USD(second_Currency, first_Number));
        }

        else{
            return 0.00;
        }
    }

    // method that rounds the output to 2 decimal places
    public Double Rounder(double before_Rounded){
        rounded_Value = Math.round(before_Rounded * 100.0) / 100.0;
        return rounded_Value;
    }

    public Double AUD(String second_Currency, double inputted_Number){

        if(second_Currency.equals("Canadian Dollar")){
            return inputted_Number / AUD;
        }
        else if(second_Currency.equals("Chinese Yuan")){
            return (inputted_Number / AUD) * CNY;
        }
        else if(second_Currency.equals("Euro")){
            return (inputted_Number / AUD) * EUR;
        }
        else if(second_Currency.equals("Hong Kong Dollar")){
            return (inputted_Number / AUD) * HKD;
        }
        else if(second_Currency.equals("Indian Rupee")){
            return (inputted_Number / AUD) * INR;
        }
        else if(second_Currency.equals("Japanese Yen")){
            return (inputted_Number / AUD) * JPY;
        }
        else if(second_Currency.equals("South Korean won")){
            return (inputted_Number / AUD) * KRW;
        }
        else if(second_Currency.equals("New Zealand Dollar")){
            return (inputted_Number / AUD) * NZD;
        }
        else if(second_Currency.equals("Swiss Franc")){
            return (inputted_Number / AUD) * CHF;
        }
        else if(second_Currency.equals("United States Dollar")){
            return (inputted_Number / AUD) * USD;
        }

        else{ return inputted_Number;
        }

    }


    // method for converting to canadian to other currency
    public Double CAD(String second_Currency, double inputted_Number){
        if(second_Currency.equals("Australian Dollar")){
            return inputted_Number * AUD;
        }
        else if(second_Currency.equals("Chinese Yuan")){
            return inputted_Number * CNY;
        }
        else if(second_Currency.equals("Euro")){
            return inputted_Number * EUR;
        }
        else if(second_Currency.equals("Hong Kong Dollar")){
            return inputted_Number * HKD;
        }
        else if(second_Currency.equals("Indian Rupee")){
            return inputted_Number * INR;
        }
        else if(second_Currency.equals("Japanese Yen")){
            return inputted_Number * JPY;
        }
        else if(second_Currency.equals("South Korean won")){
            return inputted_Number * KRW;
        }
        else if(second_Currency.equals("New Zealand Dollar")){
            return inputted_Number * NZD;
        }
        else if(second_Currency.equals("Swiss Franc")){
            return inputted_Number * CHF;
        }
        else if(second_Currency.equals("United States Dollar")){
            return inputted_Number * USD;
        }

        else{
            return inputted_Number;
        }
    }

    public Double CNY(String second_Currency, double inputted_Number){

        if(second_Currency.equals("Australian Dollar")){
            return (inputted_Number / CNY) * AUD;
        }
        else if(second_Currency.equals("Canadian Dollar")){
            return inputted_Number / CNY;
        }
        else if(second_Currency.equals("Euro")){
            return (inputted_Number / CNY) * EUR;
        }
        else if(second_Currency.equals("Hong Kong Dollar")){
            return (inputted_Number / CNY) * HKD;
        }
        else if(second_Currency.equals("Indian Rupee")){
            return (inputted_Number / CNY) * INR;
        }
        else if(second_Currency.equals("Japanese Yen")){
            return (inputted_Number / CNY) * JPY;
        }
        else if(second_Currency.equals("South Korean won")){
            return (inputted_Number / CNY) * KRW;
        }
        else if(second_Currency.equals("New Zealand Dollar")){
            return (inputted_Number / CNY) * NZD;
        }
        else if(second_Currency.equals("Swiss Franc")){
            return (inputted_Number / CNY) * CHF;
        }
        else if(second_Currency.equals("United States Dollar")){
            return (inputted_Number / CNY) * USD;
        }

        else{ return inputted_Number;
        }

    }

    public Double EUR(String second_Currency, double inputted_Number){

        if(second_Currency.equals("Australian Dollar")){
            return (inputted_Number / EUR) * AUD;
        }
        else if(second_Currency.equals("Canadian Dollar")){
            return inputted_Number / CAD;
        }
        else if(second_Currency.equals("Chinese Yuan")){
            return (inputted_Number / EUR) * CNY;
        }
        else if(second_Currency.equals("Hong Kong Dollar")){
            return (inputted_Number / EUR) * HKD;
        }
        else if(second_Currency.equals("Indian Rupee")){
            return (inputted_Number / EUR) * INR;
        }
        else if(second_Currency.equals("Japanese Yen")){
            return (inputted_Number / EUR) * JPY;
        }
        else if(second_Currency.equals("South Korean won")){
            return (inputted_Number / EUR) * KRW;
        }
        else if(second_Currency.equals("New Zealand Dollar")){
            return (inputted_Number / EUR) * NZD;
        }
        else if(second_Currency.equals("Swiss Franc")){
            return (inputted_Number / EUR) * CHF;
        }
        else if(second_Currency.equals("United States Dollar")){
            return (inputted_Number / EUR) * USD;
        }

        else{ return inputted_Number;
        }
    }


    public Double HKD(String second_Currency, double inputted_Number){

        if(second_Currency.equals("Australian Dollar")){
            return (inputted_Number / HKD) * AUD;
        }
        else if(second_Currency.equals("Canadian Dollar")){
            return inputted_Number / HKD;
        }
        else if(second_Currency.equals("Chinese Yuan")){
            return (inputted_Number / HKD) * CNY;
        }
        else if(second_Currency.equals("Euro")){
            return (inputted_Number / HKD) * EUR;
        }
        else if(second_Currency.equals("Indian Rupee")){
            return (inputted_Number / HKD) * INR;
        }
        else if(second_Currency.equals("Japanese Yen")){
            return (inputted_Number / HKD) * JPY;
        }
        else if(second_Currency.equals("South Korean won")){
            return (inputted_Number / HKD) * KRW;
        }
        else if(second_Currency.equals("New Zealand Dollar")){
            return (inputted_Number / HKD) * NZD;
        }
        else if(second_Currency.equals("Swiss Franc")){
            return (inputted_Number / HKD) * CHF;
        }
        else if(second_Currency.equals("United States Dollar")){
            return (inputted_Number / HKD) * USD;
        }

        else{ return inputted_Number;
        }

    }

    public Double INR(String second_Currency, double inputted_Number){

        if(second_Currency.equals("Australian Dollar")){
            return (inputted_Number / INR) * AUD;
        }
        else if(second_Currency.equals("Canadian Dollar")){
            return inputted_Number / INR;
        }
        else if(second_Currency.equals("Chinese Yuan")){
            return (inputted_Number / INR) * CNY;
        }
        else if(second_Currency.equals("Euro")){
            return (inputted_Number / INR) * EUR;
        }
        else if(second_Currency.equals("Hong Kong Dollar")){
            return (inputted_Number / INR) * HKD;
        }
        else if(second_Currency.equals("Japanese Yen")){
            return (inputted_Number / INR) * JPY;
        }
        else if(second_Currency.equals("South Korean won")){
            return (inputted_Number / INR) * KRW;
        }
        else if(second_Currency.equals("New Zealand Dollar")){
            return (inputted_Number / INR) * NZD;
        }
        else if(second_Currency.equals("Swiss Franc")){
            return (inputted_Number / INR) * CHF;
        }
        else if(second_Currency.equals("United States Dollar")){
            return (inputted_Number / INR) * USD;
        }

        else{ return inputted_Number;
        }

    }

    public Double JPY(String second_Currency, double inputted_Number){

        if(second_Currency.equals("Australian Dollar")){
            return (inputted_Number / JPY) * AUD;
        }
        else if(second_Currency.equals("Canadian Dollar")){
            return inputted_Number / JPY;
        }
        else if(second_Currency.equals("Chinese Yuan")){
            return (inputted_Number / JPY) * CNY;
        }
        else if(second_Currency.equals("Euro")){
            return (inputted_Number / JPY) * EUR;
        }
        else if(second_Currency.equals("Hong Kong Dollar")){
            return (inputted_Number / JPY) * HKD;
        }
        else if(second_Currency.equals("Indian Rupee")){
            return (inputted_Number / JPY) * INR;
        }
        else if(second_Currency.equals("South Korean won")){
            return (inputted_Number / JPY) * KRW;
        }
        else if(second_Currency.equals("New Zealand Dollar")){
            return (inputted_Number / JPY) * NZD;
        }
        else if(second_Currency.equals("Swiss Franc")){
            return (inputted_Number / JPY) * CHF;
        }
        else if(second_Currency.equals("United States Dollar")){
            return (inputted_Number / JPY) * USD;
        }

        else{ return inputted_Number;
        }

    }

    public Double KRW(String second_Currency, double inputted_Number){

        if(second_Currency.equals("Australian Dollar")){
            return (inputted_Number / KRW) * AUD;
        }
        if(second_Currency.equals("Canadian Dollar")){
            return inputted_Number / KRW;
        }
        else if(second_Currency.equals("Chinese Yuan")){
            return (inputted_Number / KRW) * CNY;
        }
        else if(second_Currency.equals("Euro")){
            return (inputted_Number / KRW) * EUR;
        }
        else if(second_Currency.equals("Hong Kong Dollar")){
            return (inputted_Number / KRW) * HKD;
        }
        else if(second_Currency.equals("Indian Rupee")){
            return (inputted_Number / KRW) * INR;
        }
        else if(second_Currency.equals("Japanese Yen")){
            return (inputted_Number / KRW) * JPY;
        }
        else if(second_Currency.equals("New Zealand Dollar")){
            return (inputted_Number / KRW) * NZD;
        }
        else if(second_Currency.equals("Swiss Franc")){
            return (inputted_Number / KRW) * CHF;
        }
        else if(second_Currency.equals("United States Dollar")){
            return (inputted_Number / KRW) * USD;
        }

        else{ return inputted_Number;
        }

    }

    public Double NZD(String second_Currency, double inputted_Number){

        if(second_Currency.equals("Australian Dollar")){
            return (inputted_Number / NZD) * AUD;
        }
        if(second_Currency.equals("Canadian Dollar")){
            return inputted_Number / NZD;
        }
        else if(second_Currency.equals("Chinese Yuan")){
            return (inputted_Number / NZD) * CNY;
        }
        else if(second_Currency.equals("Euro")){
            return (inputted_Number / NZD) * EUR;
        }
        else if(second_Currency.equals("Hong Kong Dollar")){
            return (inputted_Number / NZD) * HKD;
        }
        else if(second_Currency.equals("Indian Rupee")){
            return (inputted_Number / NZD) * INR;
        }
        else if(second_Currency.equals("Japanese Yen")){
            return (inputted_Number / NZD) * JPY;
        }
        else if(second_Currency.equals("South Korean won")){
            return (inputted_Number / NZD) * KRW;
        }
        else if(second_Currency.equals("Swiss Franc")){
            return (inputted_Number / NZD) * CHF;
        }
        else if(second_Currency.equals("United States Dollar")){
            return (inputted_Number / NZD) * USD;
        }

        else{ return inputted_Number;
        }

    }

    public Double CHF(String second_Currency, double inputted_Number){

        if(second_Currency.equals("Australian Dollar")){
            return (inputted_Number / CHF) * AUD;
        }
        if(second_Currency.equals("Canadian Dollar")){
            return inputted_Number / CHF;
        }
        else if(second_Currency.equals("Chinese Yuan")){
            return (inputted_Number / CHF) * CNY;
        }
        else if(second_Currency.equals("Euro")){
            return (inputted_Number / CHF) * EUR;
        }
        else if(second_Currency.equals("Hong Kong Dollar")){
            return (inputted_Number / CHF) * HKD;
        }
        else if(second_Currency.equals("Indian Rupee")){
            return (inputted_Number / CHF) * INR;
        }
        else if(second_Currency.equals("Japanese Yen")){
            return (inputted_Number / CHF) * JPY;
        }
        else if(second_Currency.equals("South Korean won")){
            return (inputted_Number / CHF) * KRW;
        }
        else if(second_Currency.equals("New Zealand Dollar")){
            return (inputted_Number / CHF) * NZD;
        }
        else if(second_Currency.equals("United States Dollar")){
            return (inputted_Number / CHF) * USD;
        }

        else{ return inputted_Number;
        }

    }

    public Double USD(String second_Currency, double inputted_Number){

        if(second_Currency.equals("Australian Dollar")){
            return (inputted_Number / USD) * AUD;
        }
        if(second_Currency.equals("Canadian Dollar")){
            return inputted_Number / USD;
        }
        else if(second_Currency.equals("Chinese Yuan")){
            return (inputted_Number / USD) * CNY;
        }
        else if(second_Currency.equals("Euro")){
            return (inputted_Number / USD) * EUR;
        }
        else if(second_Currency.equals("Hong Kong Dollar")){
            return (inputted_Number / USD) * HKD;
        }
        else if(second_Currency.equals("Indian Rupee")){
            return (inputted_Number / USD) * INR;
        }
        else if(second_Currency.equals("Japanese Yen")){
            return (inputted_Number / USD) * JPY;
        }
        else if(second_Currency.equals("South Korean won")){
            return (inputted_Number / USD) * KRW;
        }
        else if(second_Currency.equals("New Zealand Dollar")){
            return (inputted_Number / USD) * NZD;
        }
        else if(second_Currency.equals("Swiss Franc")){
            return (inputted_Number / USD) * CHF;
        }

        else{ return inputted_Number;
        }

    }


    @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String text = parent.getItemAtPosition(position).toString();

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

    }
}
