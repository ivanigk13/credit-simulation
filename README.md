# Credit Simulator

Console application untuk menghitung simulasi kredit kendaraan berdasarkan jenis kendaraan, kondisi kendaraan, tahun kendaraan, jumlah pinjaman, tenor, dan uang muka (DP).

## Daftar Isi

- [Requirement](#requirement)
- [Build](#build)
- [Menjalankan Aplikasi](#menjalankan-aplikasi)
- [Menu](#menu)
- [Fitur](#fitur)
- [Input dari File](#input-dari-file)
- [Aturan Validasi](#aturan-validasi)
- [Rumus Perhitungan](#rumus-perhitungan)
- [Struktur Project](#struktur-project)
- [Asumsi](#asumsi)
- [Catatan Web Service](#catatan-web-service)
- [Unit Test](#unit-test)

## Requirement

- Java 8 atau lebih tinggi
- Maven 3.x
- Dependency: Jackson Databind (untuk parsing JSON pada fitur Load)

## Build

Clone project:

```bash
git clone <repository-url>
cd credit-simulator
```

Build project:

```bash
mvn clean package
```

Hasil build:

```text
target/credit-simulator.jar
```

## Menjalankan Aplikasi

### Mode interaktif (menu)

```bash
java -jar target/credit-simulator.jar
```

atau

```bash
./credit_simulator
```

### Mode file input

Jika argumen diberikan, aplikasi akan membaca file input, menghitung, menampilkan hasil, lalu selesai tanpa menampilkan menu.

```bash
java -jar target/credit-simulator.jar input.txt
```

Format file dijelaskan di bagian [Input dari File](#input-dari-file).

## Menu

```text
=== CREDIT SIMULATOR ===
1. Calculate Credit
2. Save Sheet
3. Display All Sheet
4. Search Sheet
5. Load
0. Exit
```

## Fitur

### 1. Calculate Credit

Melakukan simulasi kredit kendaraan dengan input berikut:

| Input | Keterangan |
|-------|------------|
| Jenis kendaraan | `Mobil` / `Motor` (case-insensitive) |
| Kondisi kendaraan | `Baru` / `Bekas` (case-insensitive) |
| Tahun kendaraan | Angka tahun, contoh `2025` |
| Total pinjaman | Harga kendaraan, maksimal Rp1.000.000.000 |
| Tenor | 1 - 6 tahun |
| Jumlah DP | Sesuai ketentuan minimal DP |

Contoh output (Mobil Baru, harga Rp1.000.000.000, DP Rp500.000.000, tenor 6 tahun):

```text
Kendaraan: Mobil Baru
Harga Kendaraan: Rp. 1,000,000,000.00
tahun 1 : Rp. 7,500,000.00/bln , Suku Bunga : 8.0%
tahun 2 : Rp. 8,107,500.00/bln , Suku Bunga : 8.1%
tahun 3 : Rp. 8,804,745.00/bln , Suku Bunga : 8.6%
tahun 4 : Rp. 9,570,757.82/bln , Suku Bunga : 8.7%
tahun 5 : Rp. 10,451,267.53/bln , Suku Bunga : 9.2%
tahun 6 : Rp. 11,423,235.41/bln , Suku Bunga : 9.3%
Rata-rata perbulan: Rp. 9,309,584.29
```

Jika validasi gagal, aplikasi menampilkan pesan error dan simulasi tidak dibuat.

Hasil simulasi yang berhasil disimpan sementara dan siap disimpan ke sheet melalui menu **Save Sheet**.

---

### 2. Save Sheet

Menyimpan hasil simulasi terakhir (dari **Calculate Credit** atau **Load**) ke sebuah sheet.

```text
Sheet name: sheet1
```

Ketentuan:

- Harus ada simulasi yang baru dibuat. Jika belum ada, muncul pesan `Tidak ada simulasi credit`.
- Nama sheet tidak boleh kosong.
- Nama sheet harus unik (case-sensitive). Jika sudah dipakai, akan diminta input ulang.
- Setelah disimpan, simulasi aktif dikosongkan. Untuk menyimpan sheet lain, lakukan simulasi baru terlebih dahulu.

---

### 3. Display All Sheet

Menampilkan seluruh sheet yang telah disimpan.

```text
Nama sheet: sheet1
Kendaraan: Mobil Baru
Harga Kendaraan: Rp. 1,000,000,000.00
tahun 1 : Rp. 7,500,000.00/bln , Suku Bunga : 8.0%
tahun 2 : Rp. 8,107,500.00/bln , Suku Bunga : 8.1%
tahun 3 : Rp. 8,804,745.00/bln , Suku Bunga : 8.6%
tahun 4 : Rp. 9,570,757.82/bln , Suku Bunga : 8.7%
tahun 5 : Rp. 10,451,267.53/bln , Suku Bunga : 9.2%
tahun 6 : Rp. 11,423,235.41/bln , Suku Bunga : 9.3%
Rata-rata perbulan: Rp. 9,309,584.29

Nama sheet: sheet2
Kendaraan: Mobil Baru
...
```

Jika belum ada sheet, akan muncul pesan `Tidak ada sheet yang disimpan`.

---

### 4. Search Sheet

Menampilkan hasil simulasi berdasarkan nama sheet (exact match, case-sensitive).

```text
Sheet name: sheet1
```

Jika sheet tidak ditemukan, akan muncul pesan `Sheet tidak ditemukan`.

---

### 5. Load

Mengambil data simulasi dari web service, lalu menghitung dan menampilkan hasil simulasi secara otomatis. Hasilnya juga dapat disimpan ke sheet melalui **Save Sheet**.

Format response yang diharapkan:

```json
{
  "vehicleType": "Mobil",
  "vehicleCondition": "Baru",
  "vehicleYear": 2025,
  "totalLoanAmount": 1000000000,
  "loanTenure": 6,
  "downPayment": 500000000
}
```

Lihat juga [Catatan Web Service](#catatan-web-service).

---

### 0. Exit

Keluar dari aplikasi. Data sheet hanya disimpan di memori, sehingga hilang saat aplikasi ditutup.

## Input dari File

File input berupa teks dengan format `key=value`, satu property per baris. Baris kosong dan baris tanpa tanda `=` diabaikan.

Contoh `input.txt`:

```text
vehicleType=Mobil
vehicleCondition=Baru
vehicleYear=2025
totalLoanAmount=1000000000
loanTenure=6
downPayment=500000000
```

Semua property wajib diisi. Jika ada yang kosong atau tidak ditemukan, aplikasi menampilkan pesan `Property '<nama>' tidak ditemukan`.

## Aturan Validasi

### Tahun Kendaraan

Kendaraan dengan kondisi `Baru` harus memiliki tahun kendaraan minimal:

```text
tahun sekarang - 1
```

Kendaraan `Bekas` tidak dibatasi pada validator.

### Tenor Pinjaman

```text
1 - 6 tahun
```

### Total Pinjaman

```text
Lebih dari Rp0 dan maksimal Rp1.000.000.000
```

### Uang Muka (DP)

| Kondisi | Minimal DP |
|---------|-----------|
| Baru | 35% dari total pinjaman |
| Bekas | 25% dari total pinjaman |

## Rumus Perhitungan

### Suku bunga

Suku bunga dasar tahun pertama:

| Jenis | Bunga tahun 1 |
|-------|---------------|
| Mobil | 8% |
| Motor | 9% |

Kenaikan bunga di tahun berikutnya:

- Tahun genap: naik **0,1%** dari tahun sebelumnya
- Tahun ganjil: naik **0,5%** dari tahun sebelumnya

| Tahun | Mobil | Motor |
|-------|-------|-------|
| 1 | 8.0% | 9.0% |
| 2 | 8.1% | 9.1% |
| 3 | 8.6% | 9.6% |
| 4 | 8.7% | 9.7% |
| 5 | 9.2% | 10.2% |
| 6 | 9.3% | 10.3% |

### Cicilan

```text
pokok awal         = total pinjaman - DP

Untuk setiap tahun:
  total hutang     = pokok + (pokok x suku bunga tahun tsb)
  sisa tenor       = tenor - tahun + 1
  cicilan per tahun = total hutang / sisa tenor
  cicilan per bulan = cicilan per tahun / 12
  pokok tahun berikutnya = total hutang - cicilan per tahun

rata-rata per bulan = total semua cicilan tahunan / (tenor x 12)
```

Seluruh perhitungan menggunakan `BigDecimal` dengan pembulatan `HALF_UP` 2 digit desimal.

## Struktur Project

```text
src/main/java/com/credit/simulator
├── Main.java
├── constants/
│   └── CommonConstant.java
├── enums/
│   ├── VehicleCondition.java
│   └── VehicleType.java
├── model/
│   ├── CreditRequest.java
│   ├── CreditResponse.java
│   ├── SheetHolder.java
│   └── YearlyCreditDetail.java
├── service/
│   ├── CreditService.java
│   ├── CreditServiceImpl.java
│   ├── FileService.java
│   └── FileTxtService.java
├── utils/
│   ├── FormatUtils.java
│   └── StringUtils.java
├── validator/
│   └── CreditValidator.java
└── view/
    ├── CommonView.java
    ├── MainView.java
    ├── SimulateView.java
    ├── SaveSheetView.java
    ├── SearchSheetView.java
    └── LoadView.java
```

### Komponen Utama

| Komponen | Peran |
|----------|-------|
| `CreditService` / `CreditServiceImpl` | Perhitungan simulasi, menampilkan hasil, dan load data dari web service |
| `FileService` / `FileTxtService` | Membaca request kredit dari file teks |
| `CreditValidator` | Validasi aturan bisnis (tahun, tenor, pinjaman, DP) |
| `SheetHolder` | Menyimpan simulasi aktif dan kumpulan sheet di memori |
| `MainView` | Menu utama dan routing ke view lain |
| `SimulateView` | Input interaktif dan proses Calculate Credit |
| `SaveSheetView` / `SearchSheetView` | Menyimpan dan mencari sheet |
| `LoadView` | Alur fitur Load |

Interface (`CreditService`, `FileService`, `CommonView`) dipakai agar implementasi mudah diganti dan diuji.

## Asumsi

Berdasarkan contoh pada soal:

```text
Tahun 1 : 8%
Tahun 2 : 8.1%
Tahun 3 : 8.6%
Tahun 4 : 8.7%
Tahun 5 : 9.2%
Tahun 6 : 9.3%
```

Pola kenaikan bunga (genap +0,1%, ganjil +0,5%) dan bunga dasar Motor 9% merupakan interpretasi terhadap contoh output tersebut.

## Catatan Web Service

Endpoint yang disediakan pada soal saat pengerjaan sudah tidak dapat diakses (404 Not Found).

Untuk menjaga fitur Load tetap berjalan, aplikasi menampilkan pesan `Endpoint tidak dapat diakses, menggunakan data sample.` lalu memakai fallback sample response yang mengikuti spesifikasi soal:

```text
Mobil, Baru, 2025, pinjaman 1.000.000.000, tenor 6, DP 500.000.000
```

## Unit Test

Menjalankan seluruh unit test:

```bash
mvn test
```