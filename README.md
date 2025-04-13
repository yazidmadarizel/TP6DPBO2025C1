# TP6DPBO2025C1

Saya Yazid Madarizel dengan NIM 2305328 mengerjakan soal TP 6 dalam mata kuliah Desain dan Pemrograman Berorientasi Objek untuk keberkahanNya maka saya tidak melakukan kecurangan seperti yang telah dispesifikasikan. Aamiin.

---

## Desain Program
![Copy of TP6 drawio](https://github.com/user-attachments/assets/fd47dfee-b358-43b0-926b-f882fd506b88)

Game Flappy Bird ini mengimplementasikan sebuah permainan sederhana menggunakan Java Swing. Berikut adalah penjelasan mengenai desain dan alur program:

### Struktur Kelas
1. **Kelas MainMenu**
   * Merupakan titik awal program yang menampilkan menu utama
   * Menampilkan tombol "Start Game" untuk memulai permainan
   * Menampilkan judul game dan background

2. **Kelas FlappyBird**
   * Kelas utama permainan yang mengatur gameplay dan logika
   * Mengimplementasikan ActionListener untuk game loop
   * Mengimplementasikan KeyListener untuk input pemain
   * Mengelola render grafis dan collision detection

3. **Kelas Player**
   * Merepresentasikan model data untuk karakter burung pemain
   * Menyimpan posisi, dimensi, dan kecepatan
   * Memiliki metode getter dan setter untuk semua atribut

4. **Kelas Pipe**
   * Merepresentasikan model data untuk rintangan pipa
   * Menyimpan posisi, dimensi, kecepatan, dan status
   * Memiliki atribut penanda untuk mekanisme penilaian skor

## Alur Program

### Inisialisasi
1. Program dimulai dengan membuat instance dari kelas MainMenu
2. Main menu menampilkan tombol "Start Game" dan judul
3. Saat tombol ditekan, MainMenu tertutup dan membuka jendela game
4. Permainan diinisialisasi:
   * Menginisialisasi gambar background, bird, dan pipes
   * Membuat player di posisi awal
   * Menyiapkan Timer untuk game loop dan pipa
   * Menginisialisasi label skor

### Game Loop
1. Game loop dijalankan pada 60 frame per detik
2. Pada setiap iterasi:
   * Posisi burung diperbarui berdasarkan gravitasi dan velocity
   * Posisi pipa bergerak dari kanan ke kiri layar
   * Dilakukan pengecekan collision
   * UI diperbarui (score, posisi karakter, dan pipa)

### Mekanisme Pipa
1. Pipa baru dibuat secara berkala dengan Timer
2. Pipa muncul dari sisi kanan layar
3. Pipa bergerak dari kanan ke kiri dengan kecepatan tetap
4. Posisi vertikal pipa diacak untuk menciptakan variasi
5. Celah antara pipa atas dan bawah adalah tempat burung harus melewati

### Kontrol Pemain
1. Pemain menekan SPACE untuk membuat burung melompat
2. Burung dipengaruhi oleh gravitasi ketika tidak melompat
3. Kecepatan vertikal diatur ulang setiap kali burung melompat

### Sistem Skor
1. Skor ditampilkan di pojok kiri atas dengan JLabel
2. Skor bertambah 1 tiap kali pemain berhasil melewati sepasang pipa
3. Sistem menggunakan ID unik untuk mengidentifikasi pasangan pipa agar skor hanya bertambah sekali per pasangan

### Game Over
1. Game over terjadi ketika:
   * Burung menabrak pipa
   * Burung jatuh ke batas bawah layar
2. Ketika game over:
   * Timer untuk gerakan pipa dihentikan
   * Pesan "Game Over" dan skor ditampilkan
   * Perintah untuk restart ditampilkan

### Restart Game
1. Pemain menekan tombol "R" setelah game over
2. Sistem mengatur ulang:
   * Posisi burung kembali ke awal
   * Menghapus semua pipa dari layar
   * Mereset skor menjadi 0
   * Melanjutkan timer pipa dan game loop

## Implementasi Fitur
1. **Game Over Detection**
   * Sistem mendeteksi tabrakan dengan pipa menggunakan rectangular collision detection
   * Deteksi jatuh ke batas bawah layar

2. **Restart Functionality**
   * Sistem memulai ulang permainan dengan menekan tombol "R"
   * Reset kondisi semua objek game dan timer

3. **Score Display**
   * JLabel untuk menampilkan skor pemain
   * Update score real-time saat melewati pipa

4. **Score Incrementation**
   * Implementasi sistem ID untuk duplikasi pipa
   * Penambahan skor hanya saat berhasil melewati pipa

5. **Main Menu**
   * Antarmuka awal yang menampilkan tombol start
   * Transisi halus dari menu ke permainan
  
## Dokumentasi Program

https://github.com/user-attachments/assets/e79dcb9a-3ce5-4807-a67a-9d0987aa59cd


