package aed;

public class Ciudad {
    int ganancia;
    int perdida;
    int id;

    public Ciudad(int ganancia, int perdida, int id) {
        this.ganancia = ganancia;
        this.perdida = perdida;
        this.id = id;
    }

    public int getSuperavit() {
        return this.ganancia - this.perdida;
    }

    public int getId() {
        return this.id;
    }
}
