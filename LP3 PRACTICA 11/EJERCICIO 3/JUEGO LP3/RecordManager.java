import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

final class RecordManager {
    private static final int MAX_RECORDS = 10;

    private RecordManager() {}

    static void registrar(int puntos, ClasePersonaje personaje, Dificultad dificultad)
            throws IOException {
        List<Puntaje> puntajes = cargar();
        puntajes.add(new Puntaje(puntos, personaje, dificultad));
        puntajes.sort((a, b) -> Integer.compare(b.puntos, a.puntos));
        if (puntajes.size() > MAX_RECORDS) {
            puntajes = new ArrayList<>(puntajes.subList(0, MAX_RECORDS));
        }
        guardar(puntajes);
    }

    static String resumen() throws IOException {
        List<Puntaje> puntajes = cargar();
        if (puntajes.isEmpty()) return "Aún no hay récords.";

        StringBuilder texto = new StringBuilder("Mejores puntuaciones:\n\n");
        for (int i = 0; i < puntajes.size(); i++) {
            Puntaje puntaje = puntajes.get(i);
            texto.append(i + 1).append(". ")
                .append(puntaje.puntos).append(" puntos — ")
                .append(puntaje.personaje).append(" / ")
                .append(puntaje.dificultad).append('\n');
        }
        return texto.toString();
    }

    private static List<Puntaje> cargar() throws IOException {
        Path archivo = Paths.get(Main.RUTA_RECORDS);
        if (!Files.exists(archivo)) return new ArrayList<>();

        Properties datos = new Properties();
        try (InputStream entrada = Files.newInputStream(archivo)) {
            datos.load(entrada);
        } catch (IllegalArgumentException ex) {
            throw new IOException("El archivo de récords tiene un formato inválido.", ex);
        }

        try {
            int cantidad = Integer.parseInt(datos.getProperty("cantidad", "0"));
            if (cantidad < 0 || cantidad > MAX_RECORDS) {
                throw new IllegalArgumentException("Cantidad de récords inválida.");
            }
            List<Puntaje> puntajes = new ArrayList<>();
            for (int i = 0; i < cantidad; i++) {
                int puntos = Integer.parseInt(datos.getProperty("record." + i + ".puntos"));
                ClasePersonaje personaje =
                    ClasePersonaje.valueOf(datos.getProperty("record." + i + ".personaje"));
                Dificultad dificultad =
                    Dificultad.valueOf(datos.getProperty("record." + i + ".dificultad"));
                if (puntos < 0) throw new IllegalArgumentException("Puntuación negativa.");
                puntajes.add(new Puntaje(puntos, personaje, dificultad));
            }
            puntajes.sort((a, b) -> Integer.compare(b.puntos, a.puntos));
            return puntajes;
        } catch (IllegalArgumentException | NullPointerException ex) {
            throw new IOException("El archivo de récords está incompleto o contiene datos inválidos.", ex);
        }
    }

    private static void guardar(List<Puntaje> puntajes) throws IOException {
        Path destino = Paths.get(Main.RUTA_RECORDS).toAbsolutePath();
        Path carpeta = destino.getParent();
        Files.createDirectories(carpeta);
        Path temporal = Files.createTempFile(carpeta, "records-", ".tmp");
        boolean movido = false;
        try {
            Properties datos = new Properties();
            datos.setProperty("cantidad", Integer.toString(puntajes.size()));
            for (int i = 0; i < puntajes.size(); i++) {
                Puntaje puntaje = puntajes.get(i);
                datos.setProperty("record." + i + ".puntos", Integer.toString(puntaje.puntos));
                datos.setProperty("record." + i + ".personaje", puntaje.personaje.name());
                datos.setProperty("record." + i + ".dificultad", puntaje.dificultad.name());
            }
            try (OutputStream salida = Files.newOutputStream(temporal)) {
                datos.store(salida, "Récords de Héroe vs Monstruos");
            }
            try {
                Files.move(temporal, destino, StandardCopyOption.REPLACE_EXISTING,
                    StandardCopyOption.ATOMIC_MOVE);
            } catch (AtomicMoveNotSupportedException ex) {
                Files.move(temporal, destino, StandardCopyOption.REPLACE_EXISTING);
            }
            movido = true;
        } finally {
            if (!movido) Files.deleteIfExists(temporal);
        }
    }

    private static final class Puntaje {
        final int puntos;
        final ClasePersonaje personaje;
        final Dificultad dificultad;

        Puntaje(int puntos, ClasePersonaje personaje, Dificultad dificultad) {
            this.puntos = puntos;
            this.personaje = personaje;
            this.dificultad = dificultad;
        }
    }
}
