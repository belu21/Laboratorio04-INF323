package com.belen.laboratorio04;

public class Cuaternion {
    float w, x, y, z;
    Cuaternion() {
        w = 0;
        x = 0;
        y = 0;
        z = 0;
    }
    Cuaternion(float w, float x, float y, float z) {
        this.w = w;
        this.x = x;
        this.y = y;
        this.z = z;
    }
    Cuaternion(float w, Vector3 v) {
        this.w = w;
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
    }
    // norma^2 = w^2 + x^2 + y^2 + z^2
    float norma2() {
        return (w * w + x * x + y * y + z * z);
    }
    // Conjugado
    Cuaternion conjugado() {
        x = -this.x;
        y = -this.y;
        z = -this.z;
        return this;
    }
    // Cuaternion q = Cuaternion a . Cuaternion b
    static Cuaternion multiplica(Cuaternion a, Cuaternion b) {
        Cuaternion q = new Cuaternion();
        q.w = a.w * b.w - a.x * b.x - a.y * b.y - a.z * b.z;
        q.x = a.w * b.x + a.x * b.w + a.y * b.z - a.z * b.y;
        q.y = a.w * b.y - a.x * b.z + a.y * b.w + a.z * b.x;
        q.z = a.w * b.z + a.x * b.y - a.y * b.x + a.z * b.w;
        return q;
    }
    // Cuaternion q = Cuaternion a . b
    static Cuaternion multiplica(Cuaternion a, float b) {
        Cuaternion q = new Cuaternion();
        q.w = a.w * b;
        q.x = a.x * b;
        q.y = a.y * b;
        q.z = a.z * b;
        return q;
    }
    // q' = q . p . q^(-1)
    public static Vector3 rota(Cuaternion q, Vector3 p) {
        Cuaternion p_homogeneo = new Cuaternion(0, p);
        Cuaternion p_prima = multiplica(q, multiplica(p_homogeneo, q.inverso()));
        return (new Vector3(p_prima.x, p_prima.y, p_prima.z));
    }
    // q' = q . p . q*
    public static Vector3 rota1(Cuaternion q, Vector3 p) {
        Cuaternion p_homogeneo = new Cuaternion(0, p);
        Cuaternion p_prima = multiplica(q, multiplica(p_homogeneo, q.conjugado()));
        return (new Vector3(p_prima.x, p_prima.y, p_prima.z));
    }
    // q = q^(-1)
    public Cuaternion inverso() {
        Cuaternion q = new Cuaternion();
// normal^2 = a . b
        float n = norma2();
        if (n <= 1e-8)
            System.out.println("INVERSO: Error");
        q = multiplica(new Cuaternion(w, -x, -y, -z), 1 / n);
        return q;
    }
    /* Convierte el cuaternión a una matriz de rotación */
    public static void rota(float a[], Cuaternion q) {
        float d, s;
        d = (q.x * q.x) + (q.y * q.y) + (q.z * q.z) + (q.w * q.w);
        s = (d > 0.0f) ? (2.0f / d) : 0.0f;
        a[0] = 1.0f - (q.y*q.y + q.z*q.z) * s; a[4] = (q.x*q.y - q.w*q.z) * s; a[8] = (q.x*q.z + q.w*q.y) * s; a[12] = 0f;
        a[1] = (q.x*q.y + q.w*q.z) * s; a[5] = 1.0f - (q.x*q.x + q.z*q.z) * s; a[9] = (q.y*q.z - q.w*q.x) * s; a[13] = 0f;
        a[2] = (q.x*q.z - q.w*q.y) * s; a[6] = (q.y*q.z + q.w*q.x) * s; a[10] = 1.0f - (q.x*q.x + q.y*q.y) * s; a[14] = 0f;
        a[3] = 0f; a[7] = 0f; a[11] = 0f; a[15] = 1f;
    }
    @Override
    public String toString() {
        return String.format(" w = %5.2f x = %5.2f y = %5.2f z = %5.2f", w, x, y, z);
    }
}