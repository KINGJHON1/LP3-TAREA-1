// Esta clase representa UN personaje del juego.
public class Personaje {
    private String nombre;
    private int vida;
    private int ataque;
    private int defensa;
    private int alcance;
    private int nivel;

    // Constructor: se ejecuta cuando hacemos "new Personaje(...)"
    public Personaje(String nombre, int vida, int ataque, int defensa, int alcance, int nivel) {
        // Todos los numeros deben ser mayores que cero
        if (vida <= 0 || ataque <= 0 || defensa <= 0 || alcance <= 0 || nivel <= 0) {
            throw new IllegalArgumentException("Todos los valores deben ser enteros mayores que cero.");
        }
        this.nombre = nombre;
        this.vida = vida;
        this.ataque = ataque;
        this.defensa = defensa;
        this.alcance = alcance;
        this.nivel = nivel;
    }

    // Getters: sirven para LEER los atributos
    public String getNombre() { return nombre; }
    public int getVida() { return vida; }
    public int getAtaque() { return ataque; }
    public int getDefensa() { return defensa; }
    public int getAlcance() { return alcance; }
    public int getNivel() { return nivel; }

    // Setters: sirven para CAMBIAR los atributos (validando que sean > 0)
    public void setVida(int vida) { validar(vida); this.vida = vida; }
    public void setAtaque(int ataque) { validar(ataque); this.ataque = ataque; }
    public void setDefensa(int defensa) { validar(defensa); this.defensa = defensa; }
    public void setAlcance(int alcance) { validar(alcance); this.alcance = alcance; }

    private void validar(int valor) {
        if (valor <= 0) {
            throw new IllegalArgumentException("El valor debe ser mayor que cero.");
        }
    }

    // Al subir de nivel, los atributos mejoran
    public void subirNivel() {
        nivel++;
        vida += 10;
        ataque += 2;
        defensa += 2;
        alcance += 1;
    }

    // Convierte el personaje en una linea de texto para guardarlo en el archivo
    // Formato: nombre;vida;ataque;defensa;alcance;nivel
    public String toLinea() {
        return nombre + ";" + vida + ";" + ataque + ";" + defensa + ";" + alcance + ";" + nivel;
    }

    // Para mostrar el personaje en pantalla
    @Override
    public String toString() {
        return nombre + " | Vida: " + vida + " | Ataque: " + ataque + " | Defensa: " + defensa
                + " | Alcance: " + alcance + " | Nivel: " + nivel;
    }
}
