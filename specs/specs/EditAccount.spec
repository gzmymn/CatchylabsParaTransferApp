CatchyLabs - Edit Account
=========================

* Kullanici giris sayfasina gidilir
* Kullanici bilgileri girilir
* Login butonu tiklanir
* Open Money Transfer butonu tiklanir
* Edit Account butonu tiklanir

//1 BUG - Sadece sayi karakterleri ile guncelleme yapilabiliyor
Hesap Adi icin yalnizca sayi degeri icerecek sekilde guncelleme yapilamamasi kontrolu
--------------
* Account name alanina "777777777" degeri girilir
* Update Account butonu tiklanir
* Hesap adinin yalnizca sayi icermedigi dogrulanir

//2
Hesap Adi alanini bos iken guncelleme yapilamamasi kontrolu
--------------
* Account Name alani temizlenir
* Update Account butonunun disabled oldugu kontrol edilir
