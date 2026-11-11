package com.example.data

import android.content.Context
import android.content.SharedPreferences
import com.example.model.GradeLevel
import com.example.model.WordCategory

class WordRepository(context: Context) {

    private val prefs: SharedPreferences = context.getSharedPreferences("kelime_balonu_prefs", Context.MODE_PRIVATE)

    val wordBank: List<WordItem> = listOf(
        // GRADE 1 - 3 to 4 letters
        WordItem("g1_1", "EV", "İçinde ailemizle yaşadığımız sıcacık yuva.", "Evlerimizi temiz tutmak sağlığımız için çok önemlidir.", GradeLevel.GRADE_1, WordCategory.ALL),
        WordItem("g1_2", "KUŞ", "Kanatları olan ve gökyüzünde neşeyle cıvıldayan sevimli hayvan.", "Bazı kuşlar kış gelince sıcak ülkelere göç eder.", GradeLevel.GRADE_1, WordCategory.ANIMALS),
        WordItem("g1_3", "KEDİ", "Miyav diyen, yumuşacık tüylü evcil dostumuz.", "Kediler mutlu olduklarında tatlı tatlı mırıldarlar.", GradeLevel.GRADE_1, WordCategory.ANIMALS),
        WordItem("g1_4", "TOP", "Bahçede ayakla ya da elle oynadığımız yuvarlak oyuncak.", "Futbol topu genellikle siyah ve beyaz altıgenlerden oluşur.", GradeLevel.GRADE_1, WordCategory.GAMES),
        WordItem("g1_5", "SÜT", "Büyümek ve kemiklerimizin güçlenmesi için içtiğimiz beyaz içecek.", "Sütten yoğurt, peynir ve lezzetli dondurmalar yapılır.", GradeLevel.GRADE_1, WordCategory.FOOD),
        WordItem("g1_6", "GÜL", "Mis kokulu, yaprakları kırmızı veya pembe olan güzel bir çiçek.", "Gülün sapında onu koruyan küçük dikenleri vardır.", GradeLevel.GRADE_1, WordCategory.NATURE),
        WordItem("g1_7", "BAL", "Çalışkan arıların çiçeklerden toplayıp kovanlarında yaptığı tatlı besin.", "Bal binlerce yıl bozulmadan durabilen mucizevi bir gıdadır.", GradeLevel.GRADE_1, WordCategory.FOOD),
        WordItem("g1_8", "ELMA", "Kırmızı veya yeşil renkli, sulu ve vitamin dolu kış meyvesi.", "Günde bir elma yemek bizi hastalıklardan korur!", GradeLevel.GRADE_1, WordCategory.FOOD),
        WordItem("g1_9", "AYI", "Ormanlarda yaşayan, balı çok seven kocaman sevimli hayvan.", "Ayılar kışın soğuk günlerinde uzun bir kış uykusuna yatarlar.", GradeLevel.GRADE_1, WordCategory.ANIMALS),
        WordItem("g1_10", "OKUL", "Öğretmenlerimiz ve arkadaşlarımızla yeni bilgiler öğrendiğimiz yer.", "Okula gitmek bizi geleceğe en güzel şekilde hazırlar.", GradeLevel.GRADE_1, WordCategory.SCHOOL),
        WordItem("g1_11", "AĞAÇ", "Kökleri toprakta, dalları göğe uzanan, bize temiz hava veren bitki.", "Ağaçlar havayı temizler ve kuşlara güvenli yuva olur.", GradeLevel.GRADE_1, WordCategory.NATURE),
        WordItem("g1_12", "GÖZ", "Çevremizdeki renkleri ve güzellikleri görmemizi sağlayan organımız.", "Kitap okurken gözlerimizi yeterli ışıkta tutmalıyız.", GradeLevel.GRADE_1, WordCategory.ALL),
        WordItem("g1_13", "ÇAM", "Kışın da yapraklarını dökmeyen, iğne yapraklı orman ağacı.", "Çam ağaçları doğaya nefis bir çam kokusu yayar.", GradeLevel.GRADE_1, WordCategory.NATURE),
        WordItem("g1_14", "KÖK", "Bitkilerin toprağın altındaki suyu ve mineralleri çeken kısmı.", "Kökler ağacın rüzgarda dimdik ayakta durmasını sağlar.", GradeLevel.GRADE_1, WordCategory.NATURE),
        WordItem("g1_15", "MAVİ", "Denizlerin ve açık gökyüzünün dinlendirici rengi.", "Mavi doğada huzur ve ferahlık simgesidir.", GradeLevel.GRADE_1, WordCategory.COLORS),
        WordItem("g1_16", "SARI", "Pırıl pırıl parlayan güneşin ve olgunlaşmış muzun rengi.", "Sarı renk neşeyi ve canlılığı temsil eder.", GradeLevel.GRADE_1, WordCategory.COLORS),
        WordItem("g1_17", "KARE", "Dört kenarı ve dört köşesi birbirine eşit olan geometrik şekil.", "Satranç tahtası 64 adet küçük kareden oluşur.", GradeLevel.GRADE_1, WordCategory.COLORS),

        // GRADE 2 - 5 to 6 letters
        WordItem("g2_1", "KİTAP", "İçinde resimler, masallar ve bilgiler bulunan sayfalar demeti.", "Kitap okumak hayal gücümüzü ve kelime dağarcığımızı zenginleştirir.", GradeLevel.GRADE_2, WordCategory.SCHOOL),
        WordItem("g2_2", "KALEM", "Defterimize güzel yazılar yazmak veya resim çizmek için kullandığımız araç.", "İlk kurşun kalemler asırlar önce keşfedilmiştir.", GradeLevel.GRADE_2, WordCategory.SCHOOL),
        WordItem("g2_3", "ORMAN", "İçinde binlerce ağaç, kuş ve sevimli hayvanın yaşadığı yeşil alan.", "Ormanlar dünyamızın oksijen üreten akciğerleridir.", GradeLevel.GRADE_2, WordCategory.NATURE),
        WordItem("g2_4", "TAVŞAN", "Uzun kulaklı, havuç yemeyi seven ve zıp zıp zıplayan sevimli hayvan.", "Tavşanların kulakları tehlikeleri çok uzaktan bile duyabilir.", GradeLevel.GRADE_2, WordCategory.ANIMALS),
        WordItem("g2_5", "GÜNEŞ", "Dünyamızı ısıtan ve aydınlatan kocaman sarı yıldızımız.", "Güneş olmasaydı dünyamızda bitkiler ve yaşam var olamazdı.", GradeLevel.GRADE_2, WordCategory.NATURE),
        WordItem("g2_6", "BALIK", "Denizlerde, göllerde ve nehirlerde yüzgeçleriyle yüzen canlı.", "Balıklar solungaçlarıyla suyun altında nefes alırlar.", GradeLevel.GRADE_2, WordCategory.ANIMALS),
        WordItem("g2_7", "ÇİÇEK", "Mis kokulu, rengarenk taç yaprakları olan sevimli bitki.", "Arılar ve kelebekler çiçeklerin en yakın dostudur.", GradeLevel.GRADE_2, WordCategory.NATURE),
        WordItem("g2_8", "YAĞMUR", "Bulutlardan yeryüzüne su damlacıkları halinde düşen bereket.", "Yağmur yağdıktan sonra havada taze bir toprak kokusu oluşur.", GradeLevel.GRADE_2, WordCategory.NATURE),
        WordItem("g2_9", "ŞAPKA", "Güneşten veya soğuktan korunmak için başımıza taktığımız giysi.", "Kışın yünlü bere, yazın ise güneş şapkası takarız.", GradeLevel.GRADE_2, WordCategory.ALL),
        WordItem("g2_10", "ÖRDEK", "Vak vak diye ses çıkaran, suda yüzebilen perdeli ayaklı kuş.", "Ördeklerin tüyleri suya girseler bile ıslanmaz.", GradeLevel.GRADE_2, WordCategory.ANIMALS),
        WordItem("g2_11", "KÖPEK", "Sadık, dost canlısı ve hav hav diye seslenen evcil dostumuz.", "Köpeklerin koku alma duyusu insanlardan 40 kat daha güçlüdür.", GradeLevel.GRADE_2, WordCategory.ANIMALS),
        WordItem("g2_12", "SİLGİ", "Kalemle yanlış yazdığımız yerleri temizleyen yumuşak kırtasiye eşyası.", "Silgiler kauçuk ağacının doğal özünden yapılır.", GradeLevel.GRADE_2, WordCategory.SCHOOL),
        WordItem("g2_13", "ÇANTA", "Kitap, defter ve kalemlerimizi taşımak için omzumuza taktığımız eşya.", "Çantamızı her akşam ders programımıza göre düzenlemeliyiz.", GradeLevel.GRADE_2, WordCategory.SCHOOL),
        WordItem("g2_14", "DÜNYA", "Üzerinde yaşadığımız mavi ve yeşil renkli güzel gezegenimiz.", "Dünyamızın dörtte üçü okyanuslar ve denizlerle kaplıdır.", GradeLevel.GRADE_2, WordCategory.NATURE),
        WordItem("g2_15", "BULUT", "Gökyüzünde pamuk gibi süzülen beyaz su buharı kümesi.", "Bulutlar hafif görünse de binlerce ton su damlacığı taşır.", GradeLevel.GRADE_2, WordCategory.NATURE),
        WordItem("g2_16", "ÇİLEK", "Kırmızı renkli, üzerinde minik çekirdekleri olan tatlı yaz meyvesi.", "Çilek tohumları meyvenin dış yüzeyinde olan tek meyvedir.", GradeLevel.GRADE_2, WordCategory.FOOD),
        WordItem("g2_17", "MAYMUN", "Ağaçtan ağaca sallanan, muzu çok seven sevimli ve hareketli hayvan.", "Maymunlar ellerini ve ayaklarını çok ustaca kullanırlar.", GradeLevel.GRADE_2, WordCategory.ANIMALS),
        WordItem("g2_18", "ÜÇGEN", "Üç kenarı ve üç köşesi olan sevimli geometrik şekil.", "Piramitlerin yan yüzleri birer üçgendir.", GradeLevel.GRADE_2, WordCategory.COLORS),

        // GRADE 3 - 6 to 7 letters
        WordItem("g3_1", "KELEBEK", "Tırtılken kozasından çıkıp rengarenk kanatlanan sevimli böcek.", "Kelebekler tat alma duyularını ayaklarıyla kullanırlar!", GradeLevel.GRADE_3, WordCategory.ANIMALS),
        WordItem("g3_2", "PENGUEN", "Kutuplarda yaşayan, uçamayan ama buz gibi suda harika yüzen kuş.", "Penguenler soğuktan korunmak için birbirlerine sımsıkı sokulurlar.", GradeLevel.GRADE_3, WordCategory.ANIMALS),
        WordItem("g3_3", "ŞEMSİYE", "Yağmurlu günlerde ıslanmamak için açtığımız kubbe şeklindeki koruyucu.", "İlk şemsiyeler yağmur için değil, güneşten korunmak için icat edilmiştir.", GradeLevel.GRADE_3, WordCategory.ALL),
        WordItem("g3_4", "ASTRONOT", "Uzay elbisesi giyerek roketle uzaya ve aya giden bilim insanı.", "Uzayda yerçekimi olmadığı için astronotlar havada süzülürler.", GradeLevel.GRADE_3, WordCategory.NATURE),
        WordItem("g3_5", "ÇİKOLATA", "Kakao çekirdeklerinden yapılan, tatlı ve lezzetli kahverengi atıştırmalık.", "Çikolata eski çağlarda krallara özel bir içecek olarak tüketilirdi.", GradeLevel.GRADE_3, WordCategory.FOOD),
        WordItem("g3_6", "PORTAKAL", "Turuncu renkli, kışın bol bol C vitamini sağlayan sulu meyve.", "Portakal kabuğundan çıkan hoş koku odamızı ferahlatır.", GradeLevel.GRADE_3, WordCategory.FOOD),
        WordItem("g3_7", "BİSİKLET", "İki tekerlekli, pedallarını çevirerek hareket ettirdiğimiz eğlenceli araç.", "Bisiklet sürmek hem çok eğlenceli hem de doğa dostu bir spordur.", GradeLevel.GRADE_3, WordCategory.GAMES),
        WordItem("g3_8", "KANGURU", "Karnındaki kesesinde yavrusunu taşıyan ve güçlü bacaklarıyla sıçrayan hayvan.", "Kangurular geriye doğru yürüyemezler, sadece ileriye zıplarlar!", GradeLevel.GRADE_3, WordCategory.ANIMALS),
        WordItem("g3_9", "UÇURTMA", "İnce çıtalar ve renkli kağıtlardan yapılıp rüzgarlı havada uçurulan oyuncak.", "Uçurtmayı gökyüzünde dengede tutmak için uzun ve püsküllü bir kuyruğu vardır.", GradeLevel.GRADE_3, WordCategory.GAMES),
        WordItem("g3_10", "TEBEŞİR", "Tahtaya yazı yazmak veya seksek çizmek için kullanılan beyaz ya da renkli araç.", "Tebeşir kireç taşının işlenmesiyle elde edilir.", GradeLevel.GRADE_3, WordCategory.SCHOOL),
        WordItem("g3_11", "YUNUS", "Denizlerde neşeyle zıplayan, insan dostu, zeki ve nefes alan memeli canlı.", "Yunuslar birbirleriyle özel ıslık sesleriyle iletişim kurarlar.", GradeLevel.GRADE_3, WordCategory.ANIMALS),
        WordItem("g3_12", "ZÜRAFA", "Uzun boynu sayesinde en yüksek ağaç dallarındaki taze yaprakları yiyen hayvan.", "Zürafaların dili 45 santimetre kadar uzun olabilir!", GradeLevel.GRADE_3, WordCategory.ANIMALS),
        WordItem("g3_13", "DONDURMA", "Süt ve meyvelerle yapılan, külah içinde yenen soğuk ve tatlı lezzet.", "Dünyanın en çok sevilen dondurma aroması vanilyadır.", GradeLevel.GRADE_3, WordCategory.FOOD),

        // GRADE 4 - 7 to 10 letters
        WordItem("g4_1", "CUMHURİYET", "Egemenliğin kayıtsız şartsız millete ait olduğu yönetim biçimi.", "Mustafa Kemal Atatürk Cumhuriyetimizi çocuklara ve gençlere emanet etmiştir.", GradeLevel.GRADE_4, WordCategory.ALL),
        WordItem("g4_2", "BİLGİSAYAR", "Bilgileri işleyen, araştırma yapmamızı ve oyun oynamamızı sağlayan elektronik cihaz.", "İlk üretilen bilgisayarlar kocaman bir oda büyüklüğündeydi!", GradeLevel.GRADE_4, WordCategory.SCHOOL),
        WordItem("g4_3", "KÜTÜPHANE", "İçinde binlerce kitabın bulunduğu, sessizce ders çalışılıp kitap okunan yer.", "Dünyanın en eski kütüphaneleri kilden yapılmış tabletlerden oluşuyordu.", GradeLevel.GRADE_4, WordCategory.SCHOOL),
        WordItem("g4_4", "MERHAMET", "Zor durumda olan canlılara yardım etme ve onları sevip koruma duygusu.", "Canlılara merhamet göstermek iyi ve asil bir kalbin en büyük işaretidir.", GradeLevel.GRADE_4, WordCategory.ALL),
        WordItem("g4_5", "TELESKOP", "Geceleri uzaydaki yıldızları, gezegenleri ve ayı yakından inceleyen optik alet.", "Galileo teleskopla uzaya bakan ilk bilim insanlarından biridir.", GradeLevel.GRADE_4, WordCategory.NATURE),
        WordItem("g4_6", "ARKADAŞLIK", "Birbirine güvenen, birlikte oyun oynayıp paylaşan çocukların sıcacık bağı.", "Gerçek bir arkadaş sevinçlerimizi ikiye katlar, üzüntülerimizi hafifletir.", GradeLevel.GRADE_4, WordCategory.ALL),
        WordItem("g4_7", "DENİZALTI", "Denizin derinliklerinde batmadan ve su almadan ilerleyebilen özel gemi.", "Denizaltılar yönlerini bulmak için sonar adı verilen ses dalgalarını kullanır.", GradeLevel.GRADE_4, WordCategory.NATURE),
        WordItem("g4_8", "HELİKOPTER", "Tepesindeki dönen pervaneleri sayesinde dikey olarak havalanabilen hava aracı.", "Helikopterler havada tek bir noktada sabit kalarak bekleyebilirler.", GradeLevel.GRADE_4, WordCategory.NATURE),
        WordItem("g4_9", "YANARDAĞ", "Yerin altındaki sıcak lav, gaz ve külleri dışarı püskürten dağ.", "Dünyada okyanusların derin suları altında da binlerce aktif yanardağ vardır.", GradeLevel.GRADE_4, WordCategory.NATURE),
        WordItem("g4_10", "DOKTORLUK", "Hastaları iyileştirmek ve sağlıklarını korumak için çalışan fedakar meslek.", "Doktorlar kalbimizin atışlarını dinlemek için stetoskop kullanırlar.", GradeLevel.GRADE_4, WordCategory.ALL),
        WordItem("g4_11", "YERÇEKİMİ", "Dünyanın üzerindeki her şeyi kendine doğru çeken görünmez doğa kuvveti.", "İsaac Newton başının üstüne düşen elma sayesinde yerçekimi kanununu formüle etmiştir.", GradeLevel.GRADE_4, WordCategory.NATURE),
        WordItem("g4_12", "GÖKKUŞAĞI", "Güneşli yağmurlu havada gökyüzünde beliren 7 renkli muhteşem kemer.", "Gökkuşağında kırmızı, turuncu, sarı, yeşil, mavi, lacivert ve mor renkler bulunur.", GradeLevel.GRADE_4, WordCategory.NATURE),
        WordItem("g4_13", "KAPLUMBAĞA", "Sırtında sert kabuğu olan, sabırla yürüyen ve çok uzun yaşayan sürüngen.", "Bazı dev kara kaplumbağaları yüz elli yıldan fazla yaşayabilirler.", GradeLevel.GRADE_4, WordCategory.ANIMALS)
    )

    fun getWordsFor(grade: GradeLevel, category: WordCategory): List<WordItem> {
        return wordBank.filter { item ->
            item.gradeLevel == grade && (category == WordCategory.ALL || item.category == category)
        }.ifEmpty {
            wordBank.filter { it.gradeLevel == grade }
        }
    }

    fun getRandomWord(grade: GradeLevel, category: WordCategory, excludeId: String? = null): WordItem {
        val pool = getWordsFor(grade, category)
        val filtered = if (pool.size > 1 && excludeId != null) {
            pool.filter { it.id != excludeId }
        } else pool
        return filtered.randomOrNull() ?: wordBank.first()
    }

    fun markWordSolved(wordId: String) {
        val solvedSet = getSolvedWordIds().toMutableSet()
        solvedSet.add(wordId)
        prefs.edit().putStringSet("solved_words", solvedSet).apply()
    }

    fun getSolvedWordIds(): Set<String> {
        return prefs.getStringSet("solved_words", emptySet()) ?: emptySet()
    }

    fun getSolvedWords(): List<WordItem> {
        val solvedIds = getSolvedWordIds()
        return wordBank.filter { solvedIds.contains(it.id) }
    }

    fun getHighScore(): Int = prefs.getInt("high_score", 0)

    fun saveHighScore(score: Int) {
        if (score > getHighScore()) {
            prefs.edit().putInt("high_score", score).apply()
        }
    }

    fun getStars(): Int = prefs.getInt("user_stars", 10)

    fun saveStars(stars: Int) {
        prefs.edit().putInt("user_stars", stars.coerceAtLeast(0)).apply()
    }
}
