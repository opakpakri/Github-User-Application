## Github-User-Application
Projek ini dibuat sebagai syarat penilaian submision akhir course Belajar Fundamental Aplikasi Android pada platform [Dicoding](https://www.dicoding.com). Bahasa yang digunakan adalah Kotlin.
Seperti namanya Submission ini adalah sebuah aplikasi yang bertemakan GitHub User.

### Attention!!
> PERHATIAN: Repository ini dihadirkan sebagai sumber referensi untuk Kelas Dicoding [Belajar Fundamental Aplikasi Android](https://www.dicoding.com/academies/14).
Tujuannya adalah untuk membantu teman-teman dalam menyelesaikan tugas submission. Ingatlah untuk tidak sekadar menyalin dan menempelkan kode tanpa memahami prinsip-prinsip pemrogramannya.

### Preview
<img src="https://github.com/opakpakri/Github-User-Application/assets/129014865/d5156ab2-a4f3-40cf-af5e-b518a738ed9a">
<img src="https://github.com/opakpakri/Github-User-Application/assets/129014865/9a7d0d09-13f6-4db5-a886-eadc9c12373d">

### Run Program
jika anda ingin mencoba menjalankan programnya anda harus mengubah github personal token milik anda terlebih dahulu didalam build.gradle(app).
berikut arahan untuk mengubah github personal token pada build.gradle(app):
```
buildTypes {
        release {
           ...
            buildConfigField("String", "BASE_URL", "\"https://api.github.com/\"")
            buildConfigField("String", "BASE_TOKEN", "\"(ubah sesuai token milik anda)\"")
        }

        debug {
            buildConfigField("String", "BASE_URL", "\"https://api.github.com/\"")
            buildConfigField("String", "BASE_TOKEN", "\"(ubah sesuai token milik anda)\"")
        }
    }
```

### Certificate
[Download Certificate](https://www.dicoding.com/certificates/EYX40D1Y5PDL)

### Closing Sentence 
Sebelumnya terimakasih sudah berkunjung di halaman ini semoga dapat membantu untuk teman-teman disana yang sedang mengerjakan tugas submission atau bahkan yang sedang berlatih untuk menjadi mobile develpoer.
mohon maaf juga apabila pada halaman ini masih ada kekurangan entah itu programnya yang berantakan, program eror ketika di jalankan, pemborosan kode, dan mungkin masi banyak lainnya.
