**Dokumentasi Aplikasi Antrean PWA**

### Instalasi

#### Frontend (Angular + Tailwind)

1. Pastikan Node.js dan npm sudah terinstal di sistem Anda.

2. Buka terminal dan arahkan ke direktori frontend aplikasi.

3. Jalankan perintah berikut untuk menginstal dependensi:
   ```bash
   npm install
   ```

4. Setelah selesai, jalankan aplikasi dengan perintah:
   ```bash
   ng serve
   ```

5. Buka browser dan akses `http://localhost:4200` untuk melihat antarmuka aplikasi.

#### Backend (Java + Quarkus)

1. Pastikan JDK (Java Development Kit) sudah terinstal di sistem Anda.

2. Buka terminal dan arahkan ke direktori backend aplikasi.

3. Jalankan perintah berikut untuk membangun dan menjalankan aplikasi:
   ```bash
   ./mvnw clean compile quarkus:dev
   ```

4. Backend akan berjalan di `http://localhost:8080`.

### Penggunaan

1. Buka aplikasi antrean melalui browser dengan mengakses `http://localhost:4200`.

2. Daftar atau masuk sesuai kebutuhan.

3. Jelajahi antarmuka untuk membuat antrian atau bergabung dengan antrian yang ada.

4. Pastikan backend berjalan untuk mendukung fungsionalitas penuh aplikasi.

### Konfigurasi

- **Frontend:**
  - Konfigurasi lingkungan dapat diatur dalam file `.env`.
  - Sesuaikan pengaturan API endpoint jika diperlukan.

- **Backend:**
  - Konfigurasi koneksi database dan pengaturan lainnya dapat diatur dalam file `application.properties`.

### Catatan Penting

- Pastikan koneksi internet saat pertama kali menjalankan aplikasi untuk mengunduh dependensi.

- Lihat dokumentasi Angular (https://angular.io/docs) dan Quarkus (https://quarkus.io/guides) untuk informasi lebih lanjut.

Selamat menggunakan aplikasi antrean PWA! Jangan ragu untuk menghubungi tim dukungan jika ada pertanyaan atau masalah.
