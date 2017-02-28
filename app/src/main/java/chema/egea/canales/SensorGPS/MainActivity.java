package chema.egea.canales.SensorGPS;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity implements LocationListener {
    private LocationManager locationManager;
    private boolean gpsActivo;
    Location location; //Variable para almacenar coordenadas

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onProviderDisabled(String provider)
    {
        /******** Se llama a este metodo cuando el usuario apaga el gps *********/
        Toast.makeText(getBaseContext(), "El GPS está apagado", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onProviderEnabled(String provider) {

        /******** Se llama a este metodo cuando el usuario enciende el GPS  *********/

        Toast.makeText(getBaseContext(), "El GPS está encendido", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub

    }



    //metodo para actualizar las coordenadas
    public void getLocation()
    {
        try {

            locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
            // Determinamos si el GPS esta o no activo
            gpsActivo = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

            if(gpsActivo)
            {
                //Log.d("GPS Enabled", "GPS Enabled");
                if (locationManager != null)
                {
                    //System.out.println("Vamos a obtener las coordenadas de la ultima localizacion conocida");
                    location = getLastKnownLocation();
                    if (location != null)
                    {
                        double latitud = location.getLatitude();
                        double longitud = location.getLongitude();

                        //Actualizamos los valores en pantalla
                        TextView texto = (TextView)findViewById(R.id.textoUbicacion);
                        texto.setText("Coordenadas: " + latitud + ", " + longitud);
                    }
                }
            }
            else
            {
                TextView texto = (TextView)findViewById(R.id.textoUbicacion);
                texto.setText("Debes activar el GPS");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //metodo auxiliar para calcular las coordenadas actuales, usamos este metodo porque aparentemente el locationManager nos da null si llamamos a su getLastKnownLocation de otra forma
    private Location getLastKnownLocation()
    {
        locationManager = (LocationManager)getApplicationContext().getSystemService(LOCATION_SERVICE);
        List<String> providers = locationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            Location l = locationManager.getLastKnownLocation(provider);
            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                // Found best last known location: %s", l);
                bestLocation = l;
            }
        }
        return bestLocation;
    }

    //boton de obtener la ubicacion, cada vez que lo pulsamos, nos la da
    public void obtenerUbicacion(View view)
    {
        getLocation();
    }


}
