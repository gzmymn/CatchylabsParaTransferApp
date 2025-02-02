Cathcylabs - Add Money
======================

* Kullanici giris sayfasina gidilir
* Kullanici bilgileri girilir
* Login butonu tiklanir
* Open Money Transfer butonu tiklanir
* Para ekle butonu tiklanir

//1
Bos gecilemeyen alanlarin kontrol edilmesi
----------
* Ekle butonu tiklanir
* Kart Numarasi alani icin "Required" hata kontrolu yapilir
* Kart Sahibi alani icin "Required" hata kontrolu yapilir
* Son Kullanma Tarihi alani icin "Required" hata kontrolu yapilir
* CVV alani icin "Required" hata kontrolu yapilir
* Miktar alani icin "Required" hata kontrolu yapilir

//2
Kart Numarasi alanina 8 karakterden az deger girilememesi kontrolu
--------------
* Kart Numarasi alanina 8 karakterden az deger girilir
* Kart Numarasi alani icin "Too Short!" hata kontrolu yapilir

//3
Kart Numarasi alanina 14 karakterden uzun deger girilememesi kontrolu
--------------
* Kart Numarasi alanina 14 karakterden fazla deger girilir
* Kart Numarasi alani icin "Too Long!" hata kontrolu yapilir

//4
Kart Sahibi alanina 5 karakterden az deger girilememesi kontrolu
--------------
* Kart Sahibi alanina 5 karakterden az deger girilir
* Kart Sahibi alani icin "Too Short!" hata kontrolu yapilir

//5
Kart Sahibi alanina cok uzun karakterde deger girilememesi kontrolu
--------------
* Kart Sahibi alanina cok uzun karakterde deger girilir
* Kart Sahibi alani icin "Too Long!" hata kontrolu yapilir

//6  BUG - Hata vermiyor islemi gerceklestiriyor
Gecersiz Son Kullanma Tarihi Ile Hatali Islem Kontrolu
--------------
* Son Kulanma Tarihi hatali ornek kart bilgileri girilir
* Ekle butonu tiklanir
* Son Kullanma Tarihi alani icin "Invalid!" hata kontrolu yapilir

//7 BUG - Hata vermiyor islemi gerceklestiriyor
Gecersiz CVV Ile Hatali Islem Kontrolu
--------------
* CVV hatali ornek kart bilgileri girilir
* Ekle butonu tiklanir
* CVV alani icin "Invalid!" hata kontrolu yapilir

//8 BUG - hesaptaki para miktari artisi hatali oluyor
Basarili Karttan Hesaba Para Gonderim Islemi
--------------
* Ornek kart bilgileri girilir
* "10" tutarinda deger amount alanina girilir
* Ekle butonu tiklanir
* Element var mi kontrol et "TransactionsFieldTitle"
