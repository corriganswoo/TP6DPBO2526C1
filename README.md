# TP6DPBO2526C1

TUGAS PRAKTIKUM 6 DPBO GAMEDEV 

# Flappy Bird (Java Swing GUI Project)

Proyek ini merupakan implementasi dan pengembangan dari permainan klasik Flappy Bird berbasis Java Swing. Aplikasi ini menerapkan konsep dasar Pemrograman Berbasis Objek seperti pemisahan logika dan antarmuka (MVC pattern), penanganan event (*Action* & *Key Listener*), simulasi fisika gravitasi, serta pencetakan elemen visual kustom dengan `JPanel` dan `Timer`.

## Janji

Saya Irsyad Afif Musyaffa dengan NIM 2508023 mengerjakan Tugas Praktikum 7 dalam mata kuliah Desain dan Pemrograman Berorientasi Objek untuk keberkahanNya maka saya tidak melakukan kecurangan seperti yang telah dispesifikasikan. Aamiin.

## Fitur Utama

- Gameplay Flappy Bird dengan mekanisme fisika gravitasi dan daya angkat lompatan.
- Fitur Standby Mode (permainan tidak langsung berjalan saat masuk layar game, melainkan menunggu pemain menekan tombol Space).
- Kemunculan rintangan pipa dinamis dengan ketinggian acak.
- Deteksi tabrakan (AABB Collision) antara burung dengan pipa atas, pipa bawah, maupun batas bawah layar.
- Sistem perhitungan skor real-time menggunakan `JLabel` yang bertambah (+1) setelah melewati sepasang pipa.
- Fitur *Quick Restart* dengan menekan tombol **R** ketika kondisi Game Over.
- Main Menu awal berbasis `CardLayout` yang dilengkapi dengan opsi **Start Game** dan **Exit**.
- Efek suara (audio playback) saat melompat, mendapat skor, dan mengalami tabrakan.

## Kontrol Pemain

| Input | Fungsi |
| --- | --- |
| **Space** | Memulai permainan dari mode standby & membuat burung melompat |
| **R** | Melakukan restart/reset permainan (hanya aktif saat Game Over) |
| **Tombol Start** | Berpindah dari Main Menu ke tampilan permainan |
| **Tombol Exit** | Keluar dan menutup aplikasi |

## Deskripsi Kelas

### 1. `App.java`
**Fungsi:** Titik masuk utama (*entry point*) aplikasi.
- Menginisialisasi jendela utama `JFrame`.
- Memanfaatkan `CardLayout` pada panel utama untuk menangani perpindahan tampilan antara `MainMenu` dan layar permainan.
- Menempatkan `JLabel` skor di bagian atas antarmuka game.

### 2. `MainMenu.java`
**Fungsi:** Panel antarmuka menu awal sebelum masuk ke permainan.
- Menyediakan komponen visual judul game dan dua tombol interaktif (`Start Game` dan `Exit`).
- Menghubungkan listener tombol untuk navigasi layar maupun penghentian program (`System.exit(0)`).

### 3. `Logic.java`
**Fungsi:** Pengendali utama seluruh alur dan logika permainan (*Controller & Engine*).
Mengimplementasikan interface `ActionListener` untuk perulangan game dan `KeyListener` untuk menerima input tombol keyboard.

**Atribut Utama:**
- `player` (`Player`): Objek karakter burung.
- `pipes` (`ArrayList<Pipe>`): List penampung objek pipa yang aktif di layar.
- `gameStarted` (`boolean`): Status penanda permainan sudah dimulai atau belum.
- `gameOver` (`boolean`): Penanda status permainan berakhir (kalah).
- `score` (`double`): Menyimpan nilai skor permainan.
- `scoreLabel` (`JLabel`): Komponen untuk memperbarui skor pada layar.
- `gameLoop` (`Timer`): Timer utama pergerakan game (60 FPS).
- `pipesCooldown` (`Timer`): Timer interval pemunculan pipa baru.

**Metode Utama:**
- `move()`: Memperbarui posisi burung, menggeser pipa, menghitung skor, dan mendeteksi benturan.
- `placePipes()`: Membuat sepasang pipa baru (atas dan bawah) dengan posisi acak.
- `checkCollision()`: Fungsi pendeteksi tabrakan fisik antar objek.
- `triggerGameOver()`: Menghentikan loop permainan dan memicu kondisi Game Over.
- `restartGame()`: Mengembalikan seluruh data game ke kondisi awal.
- `playSound()`: Memutar aset audio (.wav).

### 4. `View.java`
**Fungsi:** Panel rendering grafis kustom (`JPanel`).
- Menggambar ulang visual permainan berdasarkan data status dari `Logic`.

**Metode Utama:**
- `paintComponent(Graphics g)`: Metode penggambaran utama Swing.
- `draw(Graphics g)`: Merender gambar background, burung, list pipa, teks instruksi standby, serta pesan Game Over.

### 5. `Player.java`
**Fungsi:** Kelas data (*Model*) untuk menyimpan informasi karakter burung.
- Menyimpan koordinat posisi (`posX`, `posY`), ukuran (`width`, `height`), kecepatan vertikal (`velocityY`), dan gambar sprite burung.

### 6. `Pipe.java`
**Fungsi:** Kelas data (*Model*) untuk menyimpan informasi rintangan pipa.
- Menyimpan koordinat posisi, ukuran, gambar sprite pipa (atas/bawah), kecepatan pergerakan horizontal (`velocityX`), dan flag `passed`.

## Alur Program

1. **Inisialisasi Aplikasi**
   - File `App.java` dijalankan.
   - Window `JFrame` terbentuk dan menampilkan komponen `MainMenu`.
   - Aplikasi menunggu input aksi dari pengguna.

2. **Perpindahan ke Layar Permainan**
   - Pengguna menekan tombol **Start Game**.
   - Sistem `CardLayout` mengganti tampilan utama ke container permainan (`View` & `scoreLabel`).
   - Metode `Logic.startGame()` dipanggil untuk menempatkan posisi awal burung dalam kondisi standby.

3. **Memulai Pergerakan Karakter**
   - Pengguna menekan tombol **Space** untuk pertama kali.
   - Status `gameStarted` berubah menjadi `true` dan `pipesCooldown` mulai berjalan.
   - Setiap tombol **Space** ditekan saat bermain, burung mendapatkan nilai kecepatan vertikal negatif (`player.setVelocityY(-10)`) untuk melompat.

4. **Game Loop Utama**
   - `gameLoop` memanggil `actionPerformed()` di `Logic` sebanyak 60 kali per detik (~16ms).
   - Metode `move()` terus memperbarui posisi burung (efek gravitasi), menggeser pipa ke kiri, menambah skor saat pipa berhasil dilewati, dan mengecek benturan.
   - Fungsi `view.repaint()` memperbarui tampilan antarmuka visual.

5. **Kondisi Game Over**
   - Jika burung menabrak pipa atau menyentuh dasar layar, metode `triggerGameOver()` dipanggil.
   - Timer `gameLoop` dan `pipesCooldown` dihentikan.
   - `View` merender teks overlay **GAME OVER** dan petunjuk untuk melakukan restart.

6. **Proses Restart**
   - Pengguna menekan tombol **R** saat Game Over.
   - Fungsi `restartGame()` dipanggil untuk mereset posisi burung, mengosongkan pipa, mengembalikan skor ke 0, dan menjalankan kembali timer loop.

## Tampilan Program

![Demo Gameplay](Documentation/FlappyBirdVid.mp4)
