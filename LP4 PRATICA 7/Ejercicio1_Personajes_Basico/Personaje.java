// Representa UN personaje del juego (version basica, sin nivel).
public class Personaje {
    private String nombre;
    private int vida;
    private int ataque;
    private int defensa;
    private int alcance;

    public Personaje(String nombre, int vida, int ataque, int defensa, int alcance) {
        // Todos los numeros deben ser enteros mayores que cero
        if (vida <= 0 || ataque <= 0 || defensa <= 0 || alcance <= 0) {
            throw new IllegalArgumentException("Todos los valores deben ser mayores que cero.");
        }
        this.nombre = nombre;
        this.vida = vida;
        this.ataque = ataque;
        this.defensa = defensa;
        this.alcance = alcance;
    }

    public String getNombre() { return nombre; }
    public int getVida() { return vida; }
    public int getAtaque() { return ataque; }
    public int getDefensa() { return defensa; }
    public int getAlcance() { return alcance; }

    // Linea para guardar en el archivo: nombre;vida;ataque;defensa;alcance
    public String toLinea() {
        return nombre + ";" + vida + ";" + ataque + ";" + defensa + ";" + alcance;
    }

    @Override
    public String toString() {
        return nombre + " | Vida: " + vida + " | Ataque: " + ataque
                + " | Defensa: " + defensa + " | Alcance: " + alcance;
    }
}
