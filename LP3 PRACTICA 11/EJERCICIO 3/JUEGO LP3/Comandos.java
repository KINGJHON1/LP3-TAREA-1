public final class Comandos {
    private Comandos() {}

    public static Comando mover(Juego juego, int desplazamiento) {
        return mover(juego, desplazamiento, 0);
    }

    public static Comando mover(Juego juego, int dx, int dy) {
        return new Comando() {
            public boolean ejecutar() { return juego.mover(dx, dy); }
        };
    }

    public static Comando atacar(Juego juego) {
        return new Comando() {
            public boolean ejecutar() { return juego.atacar(); }
        };
    }

    public static Comando disparar(Juego juego) {
        return new Comando() {
            public boolean ejecutar() { return juego.disparar(); }
        };
    }

    public static Comando defender(Juego juego) {
        return new Comando() {
            public boolean ejecutar() { return juego.defender(); }
        };
    }

    public static Comando furia(Juego juego) {
        return new Comando() {
            public boolean ejecutar() { return juego.usarFuria(); }
        };
    }

    public static Comando habilidad(Juego juego) {
        return new Comando() {
            public boolean ejecutar() { return juego.usarHabilidad(); }
        };
    }

    public static Comando curar(Juego juego) {
        return new Comando() {
            public boolean ejecutar() { return juego.curar(); }
        };
    }

    public static Comando pocion(Juego juego) {
        return new Comando() {
            public boolean ejecutar() { return juego.usarPocion(); }
        };
    }
}

interface Comando {
    boolean ejecutar();
}
