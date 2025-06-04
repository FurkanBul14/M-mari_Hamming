# Hamming SEC-DED Simülatörü

Bu proje, Hamming kodu kullanarak tek hata düzeltme ve çift hata tespit (SEC-DED) işlemini gerçekleştiren bir Java tabanlı grafik kullanıcı arayüzü (GUI) uygulamasıdır. Kullanıcı, binary veri girebilir, bu veriyi Hamming kodu ile kodlayabilir, belirli bir konumda hata simüle edebilir ve ardından hatayı düzelterek orijinal veriyi çözebilir.

## Özellikler
- **Binary Veri Kodlama**: Girilen binary veriyi Hamming kodu ile kodlar.
- **Hata Simülasyonu**: Kodlanmış veride belirli bir konumda hata oluşturur (0'ı 1'e veya 1'i 0'a çevirir).
- **Hata Düzeltme**: Bozuk veriyi analiz eder, hatayı tespit eder ve düzelterek orijinal veriyi geri getirir.
- **Kullanıcı Dostu Arayüz**: Swing ile oluşturulmuş, basit ve anlaşılır bir GUI.
- **Renk Teması**: Python Tkinter uygulamasından uyarlanmış renk teması (#d0f0c0 arkaplan, #228B22 buton rengi, beyaz yazı rengi).

## Gereksinimler
- **Java Development Kit (JDK)**: Sürüm 8 veya üstü (örneğin, OpenJDK veya Oracle JDK).
- Java standart kütüphaneleri (Swing ve AWT) dışında ek bir kütüphane gerekmez.

## Kurulum
1. **Kodu İndirin**:
   - `HammingSECDED.java` dosyasını bilgisayarınıza kaydedin.
2. **Java Ortamını Hazırlayın**:
   - JDK'nın yüklü olduğundan emin olun. Terminalde `java -version` komutunu çalıştırarak kontrol edebilirsiniz.
3. **Kodu Derleyin**:
   - Terminal veya komut istemcisinde, dosyanın bulunduğu dizine gidin.
   - Aşağıdaki komutu çalıştırın:
     ```bash
     javac HammingSECDED.java
